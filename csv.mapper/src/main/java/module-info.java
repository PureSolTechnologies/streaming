module com.puresoltechnologies.streaming.csv.mapper {

    requires transitive com.puresoltechnologies.streaming.common.mapper;
    requires transitive com.puresoltechnologies.streaming.csv;

    exports com.puresoltechnologies.streaming.csv.mapper;
    exports com.puresoltechnologies.streaming.csv.mapper.annotations;

    opens com.puresoltechnologies.streaming.csv.mapper;
}
