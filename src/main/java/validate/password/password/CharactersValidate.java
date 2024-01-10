package validate.password.password;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class CharactersValidate extends AbstractVerticle {
  public static void main(String[] args) {

  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.eventBus().consumer("password.validate.specialCharacters", message -> {
      String password = message.body().toString();
      if (password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*")) {
        System.out.println("Password is valid for special characters");
        vertx.eventBus().send("password.validate.success", password );
      } else {
        vertx.eventBus().send("specialCharactersError" , "Password must contain at least one special character") ;
      }
    });
  }
}
