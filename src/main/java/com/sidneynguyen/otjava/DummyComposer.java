package com.sidneynguyen.otjava;

import java.util.List;

public class DummyComposer extends Composer {

	@Override
	Operation compose(List<Operation> operationList) {
		return operationList.get(0);
	}
	
}
