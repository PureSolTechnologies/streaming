package com.puresoltechnologies.streaming.binary.mapper;

import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryCreator;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryDouble;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryFloat;

public class FloatingPointExample {

    private final float f;
    private final double d;

    @BinaryCreator
    public FloatingPointExample(@BinaryFloat("f") float f, @BinaryDouble("d") double d) {
	this.f = f;
	this.d = d;
    }

    public float getF() {
	return f;
    }

    public double getD() {
	return d;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(d);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + Float.floatToIntBits(f);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FloatingPointExample other = (FloatingPointExample) obj;
	if (Double.doubleToLongBits(d) != Double.doubleToLongBits(other.d))
	    return false;
	if (Float.floatToIntBits(f) != Float.floatToIntBits(other.f))
	    return false;
	return true;
    }

}