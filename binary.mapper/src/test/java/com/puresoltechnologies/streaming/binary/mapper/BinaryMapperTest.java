package com.puresoltechnologies.streaming.binary.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import com.puresoltechnologies.streaming.binary.BinaryInputStream;
import com.puresoltechnologies.streaming.binary.BinaryOutputStream;

public class BinaryMapperTest {

	static class Parameters implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			List<Arguments> arguments = Arrays.asList( //
					Arguments.of(ByteExample.class, new ByteExample(250, (byte) -42)), //
					Arguments.of(ShortExample.class, new ShortExample(65000, (short) -32000)), //
					Arguments.of(IntExample.class, new IntExample(4000000000l, -2000000000)), //
					Arguments.of(LongExample.class, new LongExample(123456789000l)), //
					Arguments.of(FloatingPointExample.class, new FloatingPointExample((float) 1.234e12, 1.23456e135)), //
					Arguments.of(BooleanStringExample.class, new BooleanStringExample(true, "Hello, world!")) //
			);
			return arguments.stream();
		}

	}

	@ParameterizedTest(name = "#{index}: [{arguments}]")
	@ArgumentsSource(Parameters.class)
	public void testDeserialization(Class<?> clazz, Object original) throws BinaryMappingException, IOException {
		BinaryMapper mapper = new BinaryMapper();
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				BinaryOutputStream binaryOutputStream = new BinaryOutputStream(byteArrayOutputStream,
						ByteOrder.LITTLE_ENDIAN)) {
			mapper.write(binaryOutputStream, original);
			try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					byteArrayOutputStream.toByteArray());
					BinaryInputStream binaryInputStream = new BinaryInputStream(byteArrayInputStream,
							ByteOrder.LITTLE_ENDIAN)) {
				Object deserialized = mapper.read(binaryInputStream, clazz);
				assertNotNull(deserialized);
				assertEquals(original, deserialized);
			}
		}
	}

}
