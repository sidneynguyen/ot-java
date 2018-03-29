package com.sidneynguyen.otjava;

import org.junit.Test;

import static com.sidneynguyen.otjava.Component.Type.DELETE;
import static com.sidneynguyen.otjava.Component.Type.INSERT;
import static com.sidneynguyen.otjava.Component.Type.RETAIN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ComponentTest {

    @Test
    public void equalsTest() {
        Component r1 = new Component(RETAIN, 5, null);
        Component r2 = new Component(RETAIN, 5, "test");
        assertEquals(r1, r2);

        Component r3 = new Component(RETAIN, 4, null);
        assertNotEquals(r1, r3);

        Component i1 = new Component(INSERT, 5, "test");
        assertNotEquals(r2, i1);

        Component i2 = new Component(INSERT, 5, "test");
        assertEquals(i1, i2);

        Component i3 = new Component(INSERT, 4, "test");
        assertNotEquals(i1, i3);

        Component i4 = new Component(INSERT, 5, "tset");
        assertNotEquals(i1, i4);

        Component i5 = new Component(INSERT, 5, null);
        assertNotEquals(i1, i5);
        assertNotEquals(r1, i5);

        Component d1 = new Component(DELETE, 5, null);
        assertNotEquals(r1, d1);
        assertNotEquals(i5, d1);

        Component d2 = new Component(DELETE, 5, "test");
        assertEquals(d1, d2);

        Component d3 = new Component(DELETE, 4, null);
        assertNotEquals(d1, d3);
    }
}
