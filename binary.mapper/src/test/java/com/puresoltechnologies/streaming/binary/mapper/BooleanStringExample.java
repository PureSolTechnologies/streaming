package com.puresoltechnologies.streaming.binary.mapper;

import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryBoolean;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryCreator;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryNulTerminateString;

public class BooleanStringExample {

    private final boolean b;
    private final String s;

    @BinaryCreator
    public BooleanStringExample(@BinaryBoolean("b") boolean b, @BinaryNulTerminateString("s") String s) {
	this.b = b;
	this.s = s;
    }

    public boolean getB() {
	return b;
    }

    public String getS() {
	return s;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (b ? 1231 : 1237);
	result = prime * result + ((s == null) ? 0 : s.hashCode());
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
	BooleanStringExample other = (BooleanStringExample) obj;
	if (b != other.b)
	    return false;
	if (s == null) {
	    if (other.s != null)
		return false;
	} else if (!s.equals(other.s))
	    return false;
	return true;
    }

}