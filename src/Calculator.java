import java.util.Objects;
import java.util.Stack;

/**
 * A class that evaluates the value of an expression.
 */
public class Calculator {

    /**
     * Expression containing numbers, operators (+,-,*,/), brackets, spaces, other characters.
     */
    private String Expression;

    /**
     * Constructor of the Calculator class
     * @param str Expression
     */
    Calculator(String str) {
        this.Expression = str;
    }

    /**
     * method for removing spaces from an expression
     */
    private void delSpace()
    {
        StringBuilder newstr = new StringBuilder();
        for (int pos = 0; pos < Expression.length(); pos++)
            if (Expression.charAt(pos) != ' ')
                newstr.append(Expression.charAt(pos));
        Expression = newstr.toString();
    }

    /**
     * method of checking the expression for correctness
     * @return True if the expression is true, otherwise false
     */
    private boolean correctness()
    {
        if (Expression.isEmpty())
            return false;
        else {
            delSpace();
            int bracket = 0;

            for (int pos = 0; pos < Expression.length(); pos++) {

                if (bracket >= 0) {

                    switch (Expression.charAt(pos)) {

                        case '+': case '-': case '*': case '/': {
                            // На концах выражения не может быть знаков операций
                            if(pos == 0 || pos == Expression.length() - 1)
                                return false;
                                // Недопустимы две операции подряд (и операция перед закрывающей скобкой)
                            else
                            if (Expression.charAt(pos+1) == '+' || Expression.charAt(pos+1) == '-'
                                    || Expression.charAt(pos+1) == '*' || Expression.charAt(pos+1) == '/'
                                    || Expression.charAt(pos+1) == ')')
                                return false;
                            break;
                        }

                        case '(': {
                            bracket++;
                            // Недопустима операция после открывающей скобки (и закрывающая сразу после открывающей)
                            if (Expression.charAt(pos+1) == '+' || Expression.charAt(pos+1) == '-'
                                    || Expression.charAt(pos+1) == '*' || Expression.charAt(pos+1) == '/'
                                    ||  Expression.charAt(pos+1) == ')')
                                return false;
                                // Еще 1 проверка на операцию в конце выражения
                            else if (pos == Expression.length() - 1)
                                return false;
                            break;
                        }

                        case ')': {
                            bracket--;
                            // Выражение не может начинаться с закрывающей скобки
                            if (pos == 0)
                                return false;
                                // Проверка некорректного символа перед закрывающей скобкой
                            else if (Expression.charAt(pos-1) == '+' || Expression.charAt(pos-1) == '-'
                                    || Expression.charAt(pos-1) == '*' || Expression.charAt(pos-1) == '/'
                                    || Expression.charAt(pos-1) == '(' )
                                return false;
                            break;
                        }

                        default:
                            if (Expression.charAt(pos) >= '0' && Expression.charAt(pos) <= '9') {
                                if (pos != 0)
                                    // Перед цифрой не должна стоять закрывающая скобка
                                    if (Expression.charAt(pos - 1) == ')')
                                        return false;
                                if (pos != Expression.length() - 1)
                                    // Перед цифрой не должна стоять открывающая скобка
                                    if (Expression.charAt(pos + 1) == '(')
                                        return false;
                            }
                            else
                                return false;
                    }
                }
                // bracket < 0 : Если условие баланса скобок было нарушено в цикле
                else
                    return false;
            }
            // Проверяем соблюдение баланса после цикла
            return bracket == 0;
        }
    }

    /**
     * method that determines the operator's priority.
     * @param ch Symbol (operator, bracket)
     * @return Symbol priority (-1...3)
     */
    private int priority(char ch) {
        if (ch == '*' || ch == '/')
            return 3;
        else if (ch == '+' || ch == '-')
            return 2;
        else if (ch == '(')
            return 1;
        else if (ch == ')')
            return -1;
        return 0;
    }

    /**
     * method that overwrites an expression into a postfix form.
     * @return True if it was possible to write, otherwise false.
     */
    private boolean postfixNotation() {

        if (!correctness() || Expression.isEmpty())
            return false;

        else {
            Stack<Character> charstack = new Stack<Character>();
            StringBuilder newstr = new StringBuilder();

            for (int pos = 0; pos < Expression.length(); pos++) {
                int typeoper = priority(Expression.charAt(pos));

                if (typeoper == 0) newstr.append(Expression.charAt(pos));

                else if (typeoper == 1) charstack.push(Expression.charAt(pos));

                else if (typeoper > 1) {
                    newstr.append(' ');

                    while (!charstack.empty()) {
                        if (priority(charstack.peek()) >= typeoper)
                            newstr.append(charstack.pop());
                        else break;
                    }

                    charstack.push(Expression.charAt(pos));
                }

                else if (typeoper == -1) {
                    newstr.append(' ');

                    while (priority(charstack.peek()) != 1)
                        newstr.append(charstack.pop());

                    charstack.pop();
                }
            }

            while (!charstack.empty()) newstr.append(charstack.pop());
            Expression = newstr.toString();
            return true;
        }
    }

    /**
     * method that reads the value of an expression written in postfix form and writes the result in the field str.
     * @return True if it was calculated, otherwise false.
     */
    public boolean calculate() {

        boolean t = postfixNotation();
        if (!t) return false;
        else {

            StringBuilder res = new StringBuilder();
            Stack<Double> st = new Stack<Double>();

            for (int pos = 0;pos < Expression.length(); pos++) {

                if (Expression.charAt(pos) == ' ') continue;

                if (priority(Expression.charAt(pos)) == 0){

                    while (Expression.charAt(pos) != ' ' && priority(Expression.charAt(pos)) == 0) {
                        res.append(Expression.charAt(pos++));
                        if (pos == Expression.length()) break;
                    }

                    st.push(Double.parseDouble(res.toString()));
                    res = new StringBuilder();
                }

                if (priority(Expression.charAt(pos)) > 1) {

                    double num1 = st.pop();
                    double num2 = st.pop();

                    if (Expression.charAt(pos) == '+')
                        st.push(num2 + num1);

                    if (Expression.charAt(pos) == '-')
                        st.push(num2 - num1);

                    if (Expression.charAt(pos) == '*')
                        st.push(num2 * num1);

                    if (Expression.charAt(pos) == '/')
                        st.push(num2 / num1);
                }
            }
            Expression = Double.toString(st.pop());
            return true;
        }
    }

    /**
     * The override toStrig() method that displays the value of the field str on the screen.
     */
    @Override
    public String toString() {
        return Expression;
    }

    /**
     * The override equals method
     * @param obj - the object with which they compare
     * @return result
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Calculator that = (Calculator) obj;
        return Objects.equals(Expression, that.Expression);
    }

    /**
     * The override hashCOde method
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(Expression);
    }

}