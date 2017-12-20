package com.puresoltechnologies.streaming.streams;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Test;

public class OptimizedFileInputStreamTest {

    @Test
    public void testBufferSizeProperyName()
	    throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
	Class<OptimizedFileInputStream> clazz = OptimizedFileInputStream.class;
	Field propertyNameField = clazz.getDeclaredField("BUFFER_SIZE_PROPERTY_NAME");
	propertyNameField.setAccessible(true);
	String defindedName = (String) propertyNameField.get(null);
	Field bufferSizeField = clazz.getDeclaredField("bufferSize");
	String expectedName = bufferSizeField.getDeclaringClass().getName() + "." + bufferSizeField.getName();
	assertEquals(expectedName, defindedName);
    }

}
