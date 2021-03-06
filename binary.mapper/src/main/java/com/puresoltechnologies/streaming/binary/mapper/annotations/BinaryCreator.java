package com.puresoltechnologies.streaming.binary.mapper.annotations;

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
public @interface BinaryCreator {

}
