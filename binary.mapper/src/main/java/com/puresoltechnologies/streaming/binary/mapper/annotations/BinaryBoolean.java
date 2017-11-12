package com.puresoltechnologies.streaming.binary.mapper.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is used to define a single element of the binary structure.
 * This annotation is used on fields, getter methods and constructor parameters,
 * but should never be mixed.
 * 
 * @author Rick-Rainer Ludwig
 */
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER })
public @interface BinaryBoolean {

    /**
     * Defines the name of the field in the class and with that the getter and
     * setter methods to use.
     * 
     * @return A {@link String} is returned containing the name.
     */
    String name();

    /**
     * 
     * This position element defines on which position the element is located inside
     * the structure. The position starts with zero, has to be unique and should be
     * counting without skipping a number.
     * 
     * @return The position is returned.
     */
    int position() default -1;

}
