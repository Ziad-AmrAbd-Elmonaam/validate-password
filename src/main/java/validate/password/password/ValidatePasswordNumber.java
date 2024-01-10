package validate.password.password;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;


public class ValidatePasswordNumber extends AbstractVerticle {


  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.eventBus().consumer("password.validate.count", message -> {
      String password = message.body().toString();
      if (password.length() >= 8) {
        System.out.println("Password is valid for number");
        vertx.eventBus().send("password.validate.lowerUpperCase", password );
      } else {
        vertx.eventBus().send("countError" , "Password must contain at least 8 numbers") ;
      }
    });
  }
}
