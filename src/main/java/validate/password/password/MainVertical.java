package validate.password.password;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVertical extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVertical());
    vertx.deployVerticle(new ValidatePasswordNumber());
    vertx.deployVerticle(new LowerUpperCase());
    vertx.deployVerticle(new ResponseErrorVertical());
    vertx.deployVerticle(new CharactersValidate());
  }

  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);

    // Define a route for POST requests to "/validatePassword"
    router.post("/validatePassword").handler(this::handlePasswordValidation);

    vertx.createHttpServer().requestHandler(router).listen(8080, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8080");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

  private void handlePasswordValidation(RoutingContext routingContext) {
    routingContext.request().bodyHandler(body -> {
      JsonObject bodyAsJson = body.toJsonObject();
      String password = bodyAsJson.getString("password");
      vertx.eventBus().request("password.validate.count", password, reply -> {
        if (reply.succeeded()) {
          // If validation is successful, parse the reply and send an OK response
          JsonObject response = new JsonObject(reply.result().body().toString());
          routingContext.response()
            .putHeader("content-type", "application/json")
            .end(response.encode());
        } else {
          JsonObject errorResponse = new JsonObject(reply.cause().getMessage());
          routingContext.response()
            .putHeader("content-type", "application/json")
            .setStatusCode(400)
            .end(errorResponse.encode());
        }
      });
    });
  }
}
