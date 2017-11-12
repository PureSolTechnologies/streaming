package com.puresoltechnologies.streaming.binary.mapper;

/**
 * This is the base exception for all binary mapping exceptions.
 * 
 * @author Rick-Rainer Ludwig
 */
public class BinaryMappingException extends Exception {
    private static final long serialVersionUID = 565774988834281448L;

    public BinaryMappingException(String message, Throwable cause) {
	super(message, cause);
    }

    public BinaryMappingException(String message) {
	super(message);
    }

}
