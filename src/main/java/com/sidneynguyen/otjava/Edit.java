package com.sidneynguyen.otjava;

public abstract class Edit {
	abstract String getEdit();

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Edit)) {
            return false;
        }
        return getEdit().equals(((Edit) obj).getEdit());
    }
}
