package com.puresoltechnologies.streaming.common.mapper;

/**
 * This class contains the definition of a single type.
 * 
 * @author Rick-Rainer Ludwig
 */
class ElementDefinition<T> {

    private final int position;
    private final String name;
    private final Class<T> type;

    public ElementDefinition(int position, String name, Class<T> type) {
	this.position = position;
	this.name = name;
	this.type = type;
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

}
