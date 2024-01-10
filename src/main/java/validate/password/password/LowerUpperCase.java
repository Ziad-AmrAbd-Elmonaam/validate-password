package validate.password.password;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class LowerUpperCase extends AbstractVerticle {

  public static void main(String[] args) {


  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.eventBus().consumer("password.validate.lowerUpperCase", message -> {
      String password = message.body().toString();
      if (password.matches("(?=.*[a-z])(?=.*[A-Z]).*")) {
        System.out.println("Password is valid for lowercase and uppercase");
        vertx.eventBus().send("password.validate.specialCharacters", password );
      } else {
    vertx.eventBus().send("lowerUpperCaseError" , "Password must contain at least one lowercase and uppercase") ;
      }
    });
  }
}

