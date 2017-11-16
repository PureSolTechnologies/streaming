package com.puresoltechnologies.streaming.common.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MappingDefinition<T> {

    private final Class<T> clazz;
    private final List<ElementDefinition<?>> elementDefinitions = new ArrayList<>();

    public MappingDefinition(Class<T> clazz) {
	super();
	this.clazz = clazz;
    }

    public void addElement(ElementDefinition<?> definition) {
	elementDefinitions.add(definition);
	Collections.sort(elementDefinitions, new Comparator<ElementDefinition<?>>() {
	    @Override
	    public int compare(ElementDefinition<?> o1, ElementDefinition<?> o2) {
		return Integer.compare(o1.getPosition(), o2.getPosition());
	    }
	});
    }

    public List<ElementDefinition<?>> getElementDefinitionsOrdered() {
	return Collections.unmodifiableList(elementDefinitions);
    }

}
