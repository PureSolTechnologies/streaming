package com.puresoltechnologies.streaming.binary.mapper;

import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryCreator;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinarySignedLong;

public class LongExample {

    private final long sl;

    @BinaryCreator
    public LongExample(@BinarySignedLong("sl") long sl) {
	this.sl = sl;
    }

    public long getSl() {
	return sl;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (sl ^ (sl >>> 32));
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
	LongExample other = (LongExample) obj;
	if (sl != other.sl)
	    return false;
	return true;
    }

}