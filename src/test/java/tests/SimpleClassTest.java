package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleClassTest {

    @Test
    void testStringOutput() {
        String str = "Nalini";
        assertEquals("Nalini", str);
        System.out.print("Finally");
    }
}