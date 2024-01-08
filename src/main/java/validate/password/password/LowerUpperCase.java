package validate.password.password;

import io.vertx.core.Vertx;

public class LowerUpperCase {
  public LowerUpperCase(Vertx vertx) {
    vertx.eventBus().consumer("password.validate", message -> {
      String password = message.body().toString();
      if (password.matches("(?=.*[a-z])(?=.*[A-Z]).*")) {
        System.out.println("Password is valid for lowercase and uppercase");
        // Further actions or event bus messages can be sent here if needed
      } else {
        System.out.println("Password must contain at least one lowercase and one uppercase");
      }
    });
  }
}
