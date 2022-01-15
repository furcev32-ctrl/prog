package edu.kit.informatik.entity;

public class Vector {
    private final int[] values;

    public Vector(int[] values) {
        this.values = values;
    }


    public void add(final Vector otherVector) {
        assert otherVector != null;
        assert this.values.length == otherVector.values.length;
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] + otherVector.values[i];
        }
    }

    public void sub(final Vector otherVector) {
        assert otherVector != null;
        assert this.values.length == otherVector.values.length;
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] - otherVector.values[i];
        }
    }

    public int getSumOfValues() {
        int result = 0;

        for (int value : values) {
            result = value;
        }
        return result;
    }

}
