package com.puresoltechnologies.streaming.common.mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * This class contains the definition of a single type.
 * 
 * @author Rick-Rainer Ludwig
 */
public class ElementDefinition<T> {

    private final Class<?> clazz;
    private final int position;
    private final String name;
    private final Class<T> type;
    private final Class<? extends Annotation> annotation;
    private final Method getter;

    ElementDefinition(Class<?> clazz, int position, String name, Class<T> type, Class<? extends Annotation> annotation)
	    throws NoSuchMethodException {
	this.clazz = clazz;
	this.position = position;
	this.name = name;
	this.type = type;
	this.annotation = annotation;
	this.getter = findGetter(name, type);
    }

    private Method findGetter(String name, Class<T> type) throws NoSuchMethodException {
	String getterName = "get" + name.substring(0, 1).toUpperCase() + (name.length() > 1 ? name.substring(1) : "");
	return clazz.getMethod(getterName);
    }

    public int getPosition() {
	return position;
    }

    public String getName() {
	return name;
    }

    public Class<T> getType() {
	return type;
    }

    public Class<? extends Annotation> getAnnotation() {
	return annotation;
    }

    public Method getGetter() {
	return getter;
    }

}
