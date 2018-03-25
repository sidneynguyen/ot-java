package com.sidneynguyen.otjava;

public abstract class Differ {
	abstract Operation diff(String old, String curr);
}
