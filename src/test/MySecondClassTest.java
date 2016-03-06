package test;

import org.junit.Test;
import sample.MySecondClass;

import static org.junit.Assert.*;

public class MySecondClassTest {

    @Test
    public void testMethodTest() throws Exception {
        MySecondClass mySecondClass = new MySecondClass();
        assertEquals(mySecondClass.MethodTest(), true);
    }
}