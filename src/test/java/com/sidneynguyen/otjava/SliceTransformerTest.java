package com.sidneynguyen.otjava;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Iterator;

public class SliceTransformerTest extends TestCase{
    public void testMultipleInsertVsDelete() {
        SliceTransformer sliceTransformer = new SliceTransformer();
        ArrayList<Component> left = new ArrayList<Component>();
        ArrayList<Component> right = new ArrayList<Component>();
        left.add(new Component(Component.Type.RETAIN, 3, null));
        left.add(new Component(Component.Type.INSERT, 3, "xyz"));
        left.add(new Component(Component.Type.RETAIN, 3, null));
        right.add(new Component(Component.Type.RETAIN, 3, null));
        right.add(new Component(Component.Type.DELETE, 2, null));
        right.add(new Component(Component.Type.RETAIN, 1, null));
        Operation leftOp = new Operation(left);
        Operation rightOp = new Operation(right);
        ArrayList<Component> leftPrimeAns = new ArrayList<Component>();
        ArrayList<Component> rightPrimeAns = new ArrayList<Component>();
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.DELETE, 1, null));
        leftPrimeAns.add(new Component(Component.Type.DELETE, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "x"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "y"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "z"));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        Pair primePair = sliceTransformer.transform(leftOp, rightOp);
        Iterator leftPrimeIt = primePair.getLeftPrime().getComponentList().iterator();
        Iterator rightPrimeIt = primePair.getRightPrime().getComponentList().iterator();
        Iterator leftPrimeAnsIt = leftPrimeAns.iterator();
        Iterator rightPrimeAnsIt = rightPrimeAns.iterator();
        while(leftPrimeIt.hasNext() && leftPrimeAnsIt.hasNext())
        {
            assertEquals(((Component)leftPrimeIt.next()).getType(), ((Component)leftPrimeAnsIt.next()).getType());
        }
        if(leftPrimeIt.hasNext() || leftPrimeAnsIt.hasNext())
        {
            assert false;
        }
        while(rightPrimeIt.hasNext() && rightPrimeAnsIt.hasNext())
        {
            assertEquals(((Component)rightPrimeIt.next()).getType(), ((Component)rightPrimeAnsIt.next()).getType());
        }
        if(leftPrimeIt.hasNext() || leftPrimeAnsIt.hasNext())
        {
            assert false;
        }
    }

    public void testEndingWithInsert() {
        SliceTransformer sliceTransformer = new SliceTransformer();
        ArrayList<Component> left = new ArrayList<Component>();
        ArrayList<Component> right = new ArrayList<Component>();
        left.add(new Component(Component.Type.RETAIN, 3, null));
        left.add(new Component(Component.Type.INSERT, 3, "xyz"));
        right.add(new Component(Component.Type.RETAIN, 3, null));
        Operation leftOp = new Operation(left);
        Operation rightOp = new Operation(right);
        ArrayList<Component> leftPrimeAns = new ArrayList<Component>();
        ArrayList<Component> rightPrimeAns = new ArrayList<Component>();
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "x"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "y"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "z"));
        Pair primePair = sliceTransformer.transform(leftOp, rightOp);
        Iterator leftPrimeIt = primePair.getLeftPrime().getComponentList().iterator();
        Iterator rightPrimeIt = primePair.getRightPrime().getComponentList().iterator();
        Iterator leftPrimeAnsIt = leftPrimeAns.iterator();
        Iterator rightPrimeAnsIt = rightPrimeAns.iterator();
        while(leftPrimeIt.hasNext() && leftPrimeAnsIt.hasNext())
        {
            assertEquals(((Component)leftPrimeIt.next()).getType(), ((Component)leftPrimeAnsIt.next()).getType());
        }
        if(leftPrimeIt.hasNext() || leftPrimeAnsIt.hasNext())
        {
            assert false;
        }
        while(rightPrimeIt.hasNext() && rightPrimeAnsIt.hasNext())
        {
            assertEquals(((Component)rightPrimeIt.next()).getType(), ((Component)rightPrimeAnsIt.next()).getType());
        }
        if(leftPrimeIt.hasNext() || leftPrimeAnsIt.hasNext())
        {
            assert false;
        }
    }

    public void testInsertCausingNonMatchingLengths() {
        SliceTransformer sliceTransformer = new SliceTransformer();
        ArrayList<Component> left = new ArrayList<Component>();
        ArrayList<Component> right = new ArrayList<Component>();
        left.add(new Component(Component.Type.RETAIN, 3, null));
        left.add(new Component(Component.Type.INSERT, 3, "xyz"));
        left.add(new Component(Component.Type.RETAIN, 3, null));
        left.add(new Component(Component.Type.INSERT, 2, "ab"));
        right.add(new Component(Component.Type.RETAIN, 6, null));
        Operation leftOp = new Operation(left);
        Operation rightOp = new Operation(right);
        ArrayList<Component> leftPrimeAns = new ArrayList<Component>();
        ArrayList<Component> rightPrimeAns = new ArrayList<Component>();
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "x"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "y"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "z"));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "a"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "b"));
        Pair primePair = sliceTransformer.transform(leftOp, rightOp);
        Iterator leftPrimeIt = primePair.getLeftPrime().getComponentList().iterator();
        Iterator rightPrimeIt = primePair.getRightPrime().getComponentList().iterator();
        Iterator leftPrimeAnsIt = leftPrimeAns.iterator();
        Iterator rightPrimeAnsIt = rightPrimeAns.iterator();
        while(leftPrimeIt.hasNext() && leftPrimeAnsIt.hasNext())
        {
            assertEquals(((Component)leftPrimeIt.next()).getType(), ((Component)leftPrimeAnsIt.next()).getType());
        }
        if(leftPrimeIt.hasNext() || leftPrimeAnsIt.hasNext())
        {
            assert false;
        }
        while(rightPrimeIt.hasNext() && rightPrimeAnsIt.hasNext())
        {
            assertEquals(((Component)rightPrimeIt.next()).getType(), ((Component)rightPrimeAnsIt.next()).getType());
        }
        if(leftPrimeIt.hasNext() || leftPrimeAnsIt.hasNext())
        {
            assert false;
        }
    }

    public void testMultipleInsertVsDeleteFlipped() {
        SliceTransformer sliceTransformer = new SliceTransformer();
        ArrayList<Component> left = new ArrayList<Component>();
        ArrayList<Component> right = new ArrayList<Component>();
        left.add(new Component(Component.Type.RETAIN, 3, null));
        left.add(new Component(Component.Type.INSERT, 3, "xyz"));
        left.add(new Component(Component.Type.RETAIN, 3, null));
        right.add(new Component(Component.Type.RETAIN, 3, null));
        right.add(new Component(Component.Type.DELETE, 2, null));
        right.add(new Component(Component.Type.RETAIN, 1, null));
        Operation leftOp = new Operation(left);
        Operation rightOp = new Operation(right);
        ArrayList<Component> leftPrimeAns = new ArrayList<Component>();
        ArrayList<Component> rightPrimeAns = new ArrayList<Component>();
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.DELETE, 1, null));
        leftPrimeAns.add(new Component(Component.Type.DELETE, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "x"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "y"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "z"));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        Pair primePair = sliceTransformer.transform(rightOp, leftOp);
        Iterator leftPrimeIt = primePair.getRightPrime().getComponentList().iterator();
        Iterator rightPrimeIt = primePair.getLeftPrime().getComponentList().iterator();
        Iterator leftPrimeAnsIt = leftPrimeAns.iterator();
        Iterator rightPrimeAnsIt = rightPrimeAns.iterator();
        while(leftPrimeIt.hasNext() && leftPrimeAnsIt.hasNext())
        {
            assertEquals(((Component)leftPrimeIt.next()).getType(), ((Component)leftPrimeAnsIt.next()).getType());
        }
        if(leftPrimeIt.hasNext() || leftPrimeAnsIt.hasNext())
        {
            assert false;
        }
        while(rightPrimeIt.hasNext() && rightPrimeAnsIt.hasNext())
        {
            assertEquals(((Component)rightPrimeIt.next()).getType(), ((Component)rightPrimeAnsIt.next()).getType());
        }
        if(leftPrimeIt.hasNext() || leftPrimeAnsIt.hasNext())
        {
            assert false;
        }
    }

    public void testEndingWithInsertFlipped() {
        SliceTransformer sliceTransformer = new SliceTransformer();
        ArrayList<Component> left = new ArrayList<Component>();
        ArrayList<Component> right = new ArrayList<Component>();
        left.add(new Component(Component.Type.RETAIN, 3, null));
        left.add(new Component(Component.Type.INSERT, 3, "xyz"));
        right.add(new Component(Component.Type.RETAIN, 3, null));
        Operation leftOp = new Operation(left);
        Operation rightOp = new Operation(right);
        ArrayList<Component> leftPrimeAns = new ArrayList<Component>();
        ArrayList<Component> rightPrimeAns = new ArrayList<Component>();
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "x"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "y"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "z"));
        Pair primePair = sliceTransformer.transform(rightOp, leftOp);
        Iterator leftPrimeIt = primePair.getRightPrime().getComponentList().iterator();
        Iterator rightPrimeIt = primePair.getLeftPrime().getComponentList().iterator();
        Iterator leftPrimeAnsIt = leftPrimeAns.iterator();
        Iterator rightPrimeAnsIt = rightPrimeAns.iterator();
        while(leftPrimeIt.hasNext() && leftPrimeAnsIt.hasNext())
        {
            assertEquals(((Component)leftPrimeIt.next()).getType(), ((Component)leftPrimeAnsIt.next()).getType());
        }
        if(leftPrimeIt.hasNext() || leftPrimeAnsIt.hasNext())
        {
            assert false;
        }
        while(rightPrimeIt.hasNext() && rightPrimeAnsIt.hasNext())
        {
            assertEquals(((Component)rightPrimeIt.next()).getType(), ((Component)rightPrimeAnsIt.next()).getType());
        }
        if(leftPrimeIt.hasNext() || leftPrimeAnsIt.hasNext())
        {
            assert false;
        }
    }

    public void testInsertCausingNonMatchingLengthsFlipped() {
        SliceTransformer sliceTransformer = new SliceTransformer();
        ArrayList<Component> left = new ArrayList<Component>();
        ArrayList<Component> right = new ArrayList<Component>();
        left.add(new Component(Component.Type.RETAIN, 3, null));
        left.add(new Component(Component.Type.INSERT, 3, "xyz"));
        left.add(new Component(Component.Type.RETAIN, 3, null));
        left.add(new Component(Component.Type.INSERT, 2, "ab"));
        right.add(new Component(Component.Type.RETAIN, 6, null));
        Operation leftOp = new Operation(left);
        Operation rightOp = new Operation(right);
        ArrayList<Component> leftPrimeAns = new ArrayList<Component>();
        ArrayList<Component> rightPrimeAns = new ArrayList<Component>();
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        leftPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "x"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "y"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "z"));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.RETAIN, 1, null));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "a"));
        rightPrimeAns.add(new Component(Component.Type.INSERT, 1, "b"));
        Pair primePair = sliceTransformer.transform(rightOp, leftOp);
        Iterator leftPrimeIt = primePair.getRightPrime().getComponentList().iterator();
        Iterator rightPrimeIt = primePair.getLeftPrime().getComponentList().iterator();
        Iterator leftPrimeAnsIt = leftPrimeAns.iterator();
        Iterator rightPrimeAnsIt = rightPrimeAns.iterator();
        while(leftPrimeIt.hasNext() && leftPrimeAnsIt.hasNext())
        {
            assertEquals(((Component)leftPrimeIt.next()).getType(), ((Component)leftPrimeAnsIt.next()).getType());
        }
        if(leftPrimeIt.hasNext() || leftPrimeAnsIt.hasNext())
        {
            assert false;
        }
        while(rightPrimeIt.hasNext() && rightPrimeAnsIt.hasNext())
        {
            assertEquals(((Component)rightPrimeIt.next()).getType(), ((Component)rightPrimeAnsIt.next()).getType());
        }
        if(leftPrimeIt.hasNext() || leftPrimeAnsIt.hasNext())
        {
            assert false;
        }
    }
}
