import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    public void calculate_test1() {
        String str = "445+989*(768-3)";
        Calculator exp = new Calculator(str);
        assertTrue(exp.calculate());
        assertEquals("757030.0",exp.toString());
    }
    @Test
    public void calculate_test2() {
        String str = "45+99**(68-3)";
        Calculator exp = new Calculator(str);
        assertFalse(exp.calculate());
    }
    @Test
    public void calculate_test3() {
        String str = "5+765)*(8-5";
        Calculator exp = new Calculator(str);
        assertFalse(exp.calculate());
    }
    @Test
    public void calculate_test4() {
        String str = "5+765)*((8-5";
        Calculator exp = new Calculator(str);
        assertFalse(exp.calculate());
    }

    @Test
    void testEquals() {
        Calculator expr1 = new Calculator("boba");
        Calculator expr2 = new Calculator("boba");
        assertTrue(expr1.equals(expr2));
        assertFalse(expr1.equals(null));
    }
}