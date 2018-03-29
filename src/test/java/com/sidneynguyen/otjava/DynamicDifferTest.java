package com.sidneynguyen.otjava;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DynamicDifferTest extends TestCase{
    public void test1() {
        DynamicDiffer dynamicDiffer = new DynamicDiffer();
        String old = "hello";
        String curr = "Helllo";
        Operation op = dynamicDiffer.diff(old, curr);
        ArrayList<Component> realAns = new ArrayList<Component>();
        realAns.add(new Component(Component.Type.DELETE, 1, null));
        realAns.add(new Component(Component.Type.INSERT, 1, new SimpleEdit("H")));
        realAns.add(new Component(Component.Type.RETAIN, 1, null));
        realAns.add(new Component(Component.Type.RETAIN, 1, null));
        realAns.add(new Component(Component.Type.RETAIN, 1, null));
        realAns.add(new Component(Component.Type.INSERT, 1, new SimpleEdit("l")));
        realAns.add(new Component(Component.Type.RETAIN, 1, null));
        List<Component> opAns = (List<Component>) op.getComponentList();
        Iterator realAnsIt = realAns.iterator();
        Iterator opAnsIt = opAns.iterator();
        while(realAnsIt.hasNext() && opAnsIt.hasNext()){
            Component realComp = ((Component)realAnsIt.next());
            Component opComp = ((Component)opAnsIt.next());
            assertEquals(realComp.getType(), opComp.getType());
            if(realComp.getType() == Component.Type.INSERT){
                assertEquals(realComp.getData().getEdit().charAt(0), opComp.getData().getEdit().charAt(0));
            }

        }
        if(realAnsIt.hasNext() || opAnsIt.hasNext()) {
            assert false;
        }
    }

    public void testShortenWord() {
        DynamicDiffer dynamicDiffer = new DynamicDiffer();
        String old = "hello";
        String curr = "Hl";
        Operation op = dynamicDiffer.diff(old, curr);
        ArrayList<Component> realAns = new ArrayList<Component>();
        realAns.add(new Component(Component.Type.DELETE, 1, null));
        realAns.add(new Component(Component.Type.DELETE, 1, null));
        realAns.add(new Component(Component.Type.INSERT, 1, new SimpleEdit("H")));
        realAns.add(new Component(Component.Type.RETAIN, 1, null));
        realAns.add(new Component(Component.Type.DELETE, 1, null));
        realAns.add(new Component(Component.Type.DELETE, 1, null));
        List<Component> opAns = (List<Component>) op.getComponentList();
        Iterator realAnsIt = realAns.iterator();
        Iterator opAnsIt = opAns.iterator();
        while(realAnsIt.hasNext() && opAnsIt.hasNext()){
            Component realComp = ((Component)realAnsIt.next());
            Component opComp = ((Component)opAnsIt.next());
            assertEquals(realComp.getType(), opComp.getType());
            if(realComp.getType() == Component.Type.INSERT){
                assertEquals(realComp.getData().getEdit().charAt(0), opComp.getData().getEdit().charAt(0));
            }

        }
        if(realAnsIt.hasNext() || opAnsIt.hasNext()) {
            assert false;
        }
    }
}
