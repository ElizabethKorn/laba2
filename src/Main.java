import java.lang.*;
import java.util.Objects;
import java.util.Scanner;
import java.lang.String;

/**
 * Main class
 */
public class Main {
    /**
     * Main method
     * @param args main arguments
     */
    public static void main(String[] args) {
        boolean k; String code;
        Scanner in = new Scanner(System.in);
        do {
            System.out.print("Enter the expression: ");
            String str = in.nextLine();

            Calculator exp = new Calculator(str);
            k = exp.calculate();

            if (!k) {
                System.out.print("The expression is incorrect.");
            } else {
                System.out.print(str + " = ");
                System.out.println(exp);
            }
            System.out.println("Press '0' to exit.");
            code = in.nextLine();
        }
        while (!Objects.equals(code, "0"));
    }
}