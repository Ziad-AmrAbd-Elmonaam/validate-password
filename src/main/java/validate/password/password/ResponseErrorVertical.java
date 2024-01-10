package validate.password.password;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class ResponseErrorVertical extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();

    // Listen for count errors
    vertx.eventBus().consumer("countError", message -> {
      String errorMessage = message.body().toString();
      System.out.println(errorMessage);
    });

    vertx.eventBus().consumer("lowerUpperCaseError", message -> {
      String errorMessage = message.body().toString();
      System.out.println(errorMessage);

    });
    vertx.eventBus().consumer("specialCharactersError", message -> {
      String errorMessage = message.body().toString();
      System.out.println(errorMessage);

    });
  }
}
