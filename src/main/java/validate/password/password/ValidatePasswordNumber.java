package validate.password.password;

import io.vertx.core.Vertx;

import java.util.Scanner;

public class ValidatePasswordNumber {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
    new LowerUpperCase(vertx); // Initialize LowerUpperCase class

    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter password: ");
    String password = scanner.nextLine();
    System.out.println("Password is: " + password);
    int passwordLength = password.length();
    if (passwordLength >= 8) {
      System.out.println("Password is valid for length");
      vertx.eventBus().send("password.validate", password);
    } else {
      System.out.println("Password must be at least 8 characters long");
    }
  }
}
