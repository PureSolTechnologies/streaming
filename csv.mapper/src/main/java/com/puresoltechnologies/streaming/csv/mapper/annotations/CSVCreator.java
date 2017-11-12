package com.puresoltechnologies.streaming.csv.mapper.annotations;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify the constructor which is used to create
 * the object when read.
 * 
 * @author Rick-Rainer Ludwig
 */
@Retention(RUNTIME)
@Target(CONSTRUCTOR)
public @interface CSVCreator {

    /**
     * Defines the name of the field in the class and with that the getter and
     * setter methods to use.
     * 
     * @return A {@link String} is returned containing the name.
     */
    String name();

}
