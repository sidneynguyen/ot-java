package com.sidneynguyen.otjava;

import org.junit.Test;

import java.util.Iterator;

import static com.sidneynguyen.otjava.Component.Type.DELETE;
import static com.sidneynguyen.otjava.Component.Type.INSERT;
import static com.sidneynguyen.otjava.Component.Type.RETAIN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class OperationTest {

    @Test
    public void equalsTest() {
        Operation op1 = new Operation();
        op1.add(new Component(RETAIN, 3, null));
        op1.add(new Component(INSERT, 2, "ab"));
        op1.add(new Component(DELETE, 4, null));
        Operation op2 = new Operation();
        op2.add(new Component(RETAIN, 3, null));
        op2.add(new Component(INSERT, 2, "ab"));
        op2.add(new Component(DELETE, 4, null));
        assertEquals(op1, op2);

        Operation op3 = new Operation();
        op3.add(new Component(RETAIN, 2, null));
        op3.add(new Component(RETAIN, 1, null));
        op3.add(new Component(INSERT, 1, "a"));
        op3.add(new Component(INSERT, 1, "b"));
        op3.add(new Component(DELETE, 2, null));
        op3.add(new Component(DELETE, 2, null));
        assertEquals(op1, op3);


        Operation op4 = new Operation();
        op4.add(new Component(RETAIN, 3, null));
        op4.add(new Component(INSERT, 2, "ab"));
        op4.add(new Component(DELETE, 4, null));
        op4.add(new Component(RETAIN, 1, null));
        assertNotEquals(op1, op4);

        Operation op5 = new Operation();
        op5.add(new Component(RETAIN, 3, null));
        op5.add(new Component(INSERT, 2, "ab"));
        op5.add(new Component(DELETE, 2, null));
        op5.add(new Component(RETAIN, 1, null));
        assertNotEquals(op1, op5);
    }

    @Test
    public void iteratorTest() {
        Operation op1 = new Operation();
        Component c1 = new Component(RETAIN, 3, null);
        Component c2 = new Component(INSERT, 2, "ab");
        Component c3 = new Component(DELETE, 4, null);
        op1.add(c1);
        op1.add(c2);
        op1.add(c3);
        Iterator<Component> it = op1.iterator();
        assertEquals(c1, it.next());
        assertEquals(c2, it.next());
        assertEquals(c3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void applyOperationTest() {
        Operation op1 = new Operation();
        op1.add(new Component(RETAIN, 3, null));
        op1.add(new Component(INSERT, 2, "xy"));
        op1.add(new Component(DELETE, 4, null));
        String s1 = "abcdefg";
        assertEquals("abcxy", Operation.applyOperation(s1, op1));

        Operation op2 = new Operation();
        op2.add(new Component(RETAIN, 3, null));
        op2.add(new Component(INSERT, 2, "xy"));
        op2.add(new Component(DELETE, 4, null));
        op2.add(new Component(INSERT, 5, "ijklm"));
        op2.add(new Component(DELETE, 1, null));
        op2.add(new Component(RETAIN, 2, null));
        String s2 = "abcdefg123";
        assertEquals("abcxyijklm23", Operation.applyOperation(s2, op2));
    }
}
