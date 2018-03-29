package com.sidneynguyen.otjava;

public class DummyTransformer extends Transformer {

	@Override
	Pair transform(Operation leftOperation, Operation rightOperation) {
		return new Pair(new Operation(), new Operation());
	}
}
