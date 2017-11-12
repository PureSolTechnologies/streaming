package com.puresoltechnologies.streaming.common.mapper;

import java.util.ArrayList;
import java.util.List;

public class MappingDefinition<T> {

    private final Class<T> clazz;
    private final List<ElementDefinition> elementDefinitions = new ArrayList<>();

    public MappingDefinition(Class<T> clazz) {
	super();
	this.clazz = clazz;
    }

    public void addElement(ElementDefinition definition) {
	elementDefinitions.add(definition);
    }

}
