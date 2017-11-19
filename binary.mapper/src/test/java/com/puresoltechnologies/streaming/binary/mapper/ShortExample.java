package com.puresoltechnologies.streaming.binary.mapper;

import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryCreator;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinarySignedShort;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryUnsignedShort;

public class ShortExample {

    private final int us;
    private final short ss;

    @BinaryCreator
    public ShortExample(@BinaryUnsignedShort("us") int us, @BinarySignedShort("ss") short ss) {
	this.us = us;
	this.ss = ss;
    }

    public int getUs() {
	return us;
    }

    public short getSs() {
	return ss;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ss;
	result = prime * result + us;
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
	ShortExample other = (ShortExample) obj;
	if (ss != other.ss)
	    return false;
	if (us != other.us)
	    return false;
	return true;
    }

}