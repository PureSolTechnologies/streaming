package com.puresoltechnologies.streaming.csv.mapper;

/**
 * This is the base exception for all binary mapping exceptions.
 * 
 * @author Rick-Rainer Ludwig
 */
public class CSVMappingException extends Exception {
    private static final long serialVersionUID = 565774988834281448L;

    public CSVMappingException(String message, Throwable cause) {
	super(message, cause);
    }

    public CSVMappingException(String message) {
	super(message);
    }

}
