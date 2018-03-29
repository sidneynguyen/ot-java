package com.sidneynguyen.otjava;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DynamicDiffer extends Differ {

	@Override
	Operation diff(String old, String curr) {
        int oldSize = old.length();
        int currSize = curr.length();
        int[][] mat = new int[oldSize + 1][currSize + 1];
        LinkedList<Component> result = new LinkedList<Component>();
        mat[0][0] = 0;
        for(int i = 0; i < oldSize; ++i) {

            mat[i + 1][0] = i + 1;
        }
        for(int i = 0; i < currSize; ++i) {
            mat[0][i + 1] = i + 1;
        }
        int topLeft;
        int left;
        int top;
        for(int i = 0; i < oldSize; ++i){
            for(int j = 0; j < currSize; ++j){
                topLeft = mat[i][j];
                left = mat[i + 1][j];
                top = mat[i][j + 1];
                if(topLeft <= left && topLeft <= top){
                    mat[i + 1][j + 1] = topLeft + ((old.charAt(i) == curr.charAt(j)) ? 0 : 2);
                }
                else if(left < top) {
                    mat[i + 1][j + 1] = left + 1;
                }
                else {
                    mat[i + 1][j + 1] = top + 1;
                }
            }
        }
        int i = oldSize;
        int j = currSize;
        int nexti;
        int nextj;
        while(i > 0 || j > 0)
        {
            nexti = i - 1;
            nextj = j - 1;
            if(nextj >=0 && nextj >= 0 && mat[nexti][nextj] <= mat[i][nextj] && mat[nexti][nextj] <= mat[nexti][j]){
                if(mat[i][j] - mat[nexti][nextj] == 2){
                    result.addFirst(new Component(Component.Type.INSERT, 1, new SimpleEdit(String.valueOf(curr.charAt(j - 1)))));
                    result.addFirst(new Component(Component.Type.DELETE, 1, null));
                }
                else {
                    result.addFirst(new Component(Component.Type.RETAIN, 1, null));
                }
                i = nexti;
                j = nextj;
            }
            else if(nexti < 0 || nextj >= 0 && mat[i][nextj] < mat[nexti][j]) {
                result.addFirst(new Component(Component.Type.INSERT, 1, new SimpleEdit(String.valueOf(curr.charAt(j - 1)))));
                i = i;
                j = nextj;
            }
            else{
                result.addFirst(new Component(Component.Type.DELETE, 1, null));
                i = nexti;
                j = j;
            }
        }
		return new Operation(result);
	}

    /*Operation diff(String old, String curr) {
        int oldSize = old.length();
        int currSize = curr.length();
        Operation[][] mat = new Operation[oldSize + 1][currSize + 1];
        mat[0][0] = new Operation();
        for(int i = 0; i < oldSize; ++i) {

            mat[i + 1][0] = new Operation(mat[i][0]);
            mat[i + 1][0].addComponent(new Component(Component.Type.INSERT, 1, new SimpleEdit(String.valueOf(old.charAt(i)))));
        }
        for(int i = 0; i < currSize; ++i) {
            mat[0][i + 1] = new Operation(mat[0][i]);
            mat[0][i + 1].addComponent(new Component(Component.Type.DELETE, 1, null));;
        }
        for(int i = 0; i < oldSize; ++i){
            for(int j = 0; j < currSize; ++j){
                List topLeft = mat[i][j].getComponentList();
                List left = mat[i + 1][j].getComponentList();
                List top = mat[i][j + 1].getComponentList();
                if(topLeft.size() <= left.size() && topLeft.size() <= top.size()){
                    mat[i + 1][j + 1] = new Operation(mat[i][j]);
                }
                else if(left.size() < top.size()) {
                    mat[i + 1][j + 1] = new Operation(mat[i + 1][j]);
                }
                else {
                    mat[i + 1][j + 1] = new Operation(mat[i][j + 1]);
                }

                if(old.charAt(i) == curr.charAt(j)){
                    mat[i + 1][j + 1].addComponent(new Component(Component.Type.RETAIN, 1, null));
                }
                else if(i == j){
                    mat[i + 1][j + 1].addComponent(new Component(Component.Type.DELETE, 1, null));
                    mat[i + 1][j + 1].addComponent(new Component(Component.Type.INSERT, 1, new SimpleEdit(String.valueOf(curr.charAt(i)))));
                }
                else if(i < j){
                    mat[i + 1][j + 1].addComponent(new Component(Component.Type.INSERT, 1, new SimpleEdit(String.valueOf(curr.charAt(i)))));
                }
                else{
                    mat[i + 1][j + 1].addComponent(new Component(Component.Type.DELETE, 1, null));
                }
            }
        }
        return mat[oldSize][currSize];
    }*/

}
