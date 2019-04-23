module com.puresoltechnologies.streaming.binary.mapper {

	requires transitive com.puresoltechnologies.streaming.binary;
	requires transitive com.puresoltechnologies.streaming.common.mapper;

	exports com.puresoltechnologies.streaming.binary.mapper;
	exports com.puresoltechnologies.streaming.binary.mapper.annotations;

	opens com.puresoltechnologies.streaming.binary.mapper;
}
