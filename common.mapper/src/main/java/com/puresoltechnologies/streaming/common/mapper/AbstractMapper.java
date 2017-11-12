package com.puresoltechnologies.streaming.common.mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapper<T extends Annotation> {

    private final Class<T> creatorAnnotation;
    private final List<Class<? extends Annotation>> elementAnnotations = new ArrayList<>();

    private final Map<Class<?>, MappingDefinition<?>> definitions = new HashMap<>();

    public AbstractMapper(Class<T> creatorAnnotation, Collection<Class<? extends Annotation>> elementAnnotations) {
	super();
	this.creatorAnnotation = creatorAnnotation;
	this.elementAnnotations.addAll(elementAnnotations);
    }

    protected <C> MappingDefinition<C> generateMappingDefinition(Class<C> clazz) throws MappingException {
	@SuppressWarnings("unchecked")
	MappingDefinition<C> definition = (MappingDefinition<C>) definitions.get(clazz);
	if (definition == null) {
	    definition = createDefinition(clazz);
	}
	return definition;
    }

    protected <C> MappingDefinition<C> createDefinition(Class<C> clazz) throws MappingException {
	try {
	    Constructor<?> binaryCreator = findCreator(clazz);
	    return createMappingDefinition(clazz, binaryCreator);
	} catch (MappingException e) {
	    throw new MappingException("Could not create mapping definition.", e);
	}
    }

    protected <C> Constructor<?> findCreator(Class<C> clazz) throws MappingException {
	Constructor<?> binaryCreator = null;
	Constructor<?> defaultConstructor = null;
	for (Constructor<?> constructor : clazz.getConstructors()) {
	    if (constructor.getParameterTypes().length == 0) {
		defaultConstructor = constructor;
	    }
	    T[] binaryCreators = constructor.getAnnotationsByType(creatorAnnotation);
	    if (binaryCreators.length == 0) {
		continue;
	    } else if (binaryCreators.length > 1) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("@");
		stringBuilder.append(creatorAnnotation.getSimpleName());
		stringBuilder.append(" annotation is only allowed once.");
		throw new MappingException(stringBuilder.toString());
	    }
	    if (binaryCreator != null) {
		throw new MappingException("Only one constructor can be binary creator.");
	    }
	    binaryCreator = constructor;
	}
	if (binaryCreator.equals(defaultConstructor)) {
	    throw new MappingException("Defining default constructor as creator is meaningless.");
	}
	return binaryCreator;
    }

    protected <C> MappingDefinition<C> createMappingDefinition(Class<C> clazz, Constructor<?> binaryCreator)
	    throws MappingException {
	try {
	    MappingDefinition<C> mappingDefinition = new MappingDefinition<>(clazz);
	    Parameter[] parameters = binaryCreator.getParameters();
	    for (int parameterId = 0; parameterId < parameters.length; parameterId++) {
		Parameter parameter = parameters[parameterId];
		Class<?> elementType = parameter.getType();
		ElementDefinition<?> definition = null;
		for (Annotation annotation : parameter.getAnnotations()) {
		    Class<? extends Annotation> annotationType = annotation.annotationType();
		    if (elementAnnotations.contains(annotationType)) {
			String name = (String) annotationType.getMethod("name").invoke(annotation);
			int position = (int) annotationType.getMethod("position").invoke(annotation);
			if (definition != null) {
			    throw new MappingException("Multiple definitions are not allowed on parameter " + name
				    + " in " + clazz.getSimpleName() + "'s creator constructor.");
			}
			if (position != -1) {
			    throw new MappingException("No position definition allowed for parameter " + name + " in "
				    + clazz.getSimpleName() + "'s creator constructor.");
			}
			position = parameterId;
			definition = new ElementDefinition<>(position, name, elementType);
		    }
		}
		mappingDefinition.addElement(definition);
	    }
	    return mappingDefinition;
	} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
		| SecurityException e) {
	    throw new MappingException("Could not create mapping definition for class " + clazz.getSimpleName() + ".");
	}
    }
}
