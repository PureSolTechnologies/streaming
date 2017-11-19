package com.puresoltechnologies.streaming.binary.mapper;

import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryCreator;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinarySignedInt;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryUnsignedInt;

public class IntExample {

    private final long ui;
    private final int si;

    @BinaryCreator
    public IntExample(@BinaryUnsignedInt("ui") long ui, @BinarySignedInt("si") int si) {
	this.ui = ui;
	this.si = si;
    }

    public long getUi() {
	return ui;
    }

    public int getSi() {
	return si;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + si;
	result = prime * result + (int) (ui ^ (ui >>> 32));
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
	IntExample other = (IntExample) obj;
	if (si != other.si)
	    return false;
	if (ui != other.ui)
	    return false;
	return true;
    }

}