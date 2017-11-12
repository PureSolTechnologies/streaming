package com.puresoltechnologies.streaming.common.mapper;

/**
 * This is the base exception for all mapping exceptions.
 * 
 * @author Rick-Rainer Ludwig
 */
public class MappingException extends Exception {
    private static final long serialVersionUID = 565774988834281448L;

    public MappingException(String message, Throwable cause) {
	super(message, cause);
    }

    public MappingException(String message) {
	super(message);
    }

}
