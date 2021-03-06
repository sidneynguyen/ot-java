package com.sidneynguyen.otjava;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.sidneynguyen.otjava.Component.Type.DELETE;
import static com.sidneynguyen.otjava.Component.Type.INSERT;
import static com.sidneynguyen.otjava.Component.Type.RETAIN;
import static junit.framework.TestCase.assertEquals;

public class ComposerTest {
    @Test
    public void singleOperationTest() {
    		Composer composer = new SliceComposer();
        Operation op1 = new Operation();
        Operation op2 = new Operation();
        op1.add(new Component(RETAIN, 3, null));
        op1.add(new Component(INSERT, 1, "a"));
        op1.add(new Component(RETAIN, 1, null));

        op2.add(new Component(RETAIN, 2, null));
        op2.add(new Component(DELETE, 2, null));
        op2.add(new Component(RETAIN, 1, null));
        op2.add(new Component(INSERT, 2, "bc"));

        Operation expected = new Operation();
        expected.add(new Component(RETAIN, 2, null));
        expected.add(new Component(DELETE, 1, null));
        expected.add(new Component(RETAIN, 1, null));
        expected.add(new Component(INSERT, 2, "bc"));

        List<Operation> ops = new ArrayList<>();
        ops.add(op1);
        ops.add(op2);
        Operation res = composer.compose(ops);

        assertEquals(expected, res);
    }
    
    @Test
    public void multipleOperationTest() {
    		Composer composer = new SliceComposer();
        Operation op1 = new Operation();
        Operation op2 = new Operation();
        Operation op3 = new Operation();
        // 1234
        // 123a4
        // 124bc
        // 12dbc
        op1.add(new Component(RETAIN, 3, null));
        op1.add(new Component(INSERT, 1, "a"));
        op1.add(new Component(RETAIN, 1, null));

        op2.add(new Component(RETAIN, 2, null));
        op2.add(new Component(DELETE, 2, null));
        op2.add(new Component(RETAIN, 1, null));
        op2.add(new Component(INSERT, 2, "bc"));
        
        op3.add(new Component(RETAIN, 2, null));
        op3.add(new Component(DELETE, 1, null));
        op3.add(new Component(INSERT, 1, "d"));
        op3.add(new Component(RETAIN, 2, null));

        Operation expected = new Operation();
        expected.add(new Component(RETAIN, 2, null));
        expected.add(new Component(DELETE, 2, null));
        expected.add(new Component(INSERT, 3, "dbc"));

        List<Operation> ops = new ArrayList<>();
        ops.add(op1);
        ops.add(op2);
        ops.add(op3);
        Operation res = composer.compose(ops);

        assertEquals(expected, res);
    }
}
