package com.sidneynguyen.otjava;

import java.util.ArrayList;
import java.util.List;

public class Operation {
	private List<Component> componentList;
	
	public Operation(List<Component> componentList) {
		this.componentList = componentList;
	}
	
	public Operation() {
		componentList = new ArrayList<Component>();
	}
}
