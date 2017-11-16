package com.puresoltechnologies.streaming.binary.mapper;

import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryCreator;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinarySignedByte;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryUnsignedByte;

public class ByteExample {

    private final int ub;
    private final byte sb;

    @BinaryCreator
    public ByteExample(@BinaryUnsignedByte("ub") int ub, @BinarySignedByte("sb") byte sb) {
	this.ub = ub;
	this.sb = sb;
    }

    public int getUb() {
	return ub;
    }

    public byte getSb() {
	return sb;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + sb;
	result = prime * result + ub;
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
	ByteExample other = (ByteExample) obj;
	if (sb != other.sb)
	    return false;
	if (ub != other.ub)
	    return false;
	return true;
    }

}