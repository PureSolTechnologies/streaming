package com.puresoltechnologies.streaming.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * This interface is used to create new input streams on demand. This is used
 * for classes like {@link SeekableInputStream} and
 * {@link MultiStreamSeekableInputStream} to generate new input streams in case
 * needed.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <I>
 *            is the type of the create input stream.
 */
@FunctionalInterface
public interface InputStreamCreator<I extends InputStream> {

    /**
     * This method returns newly created input streams. The streams need to be open.
     * 
     * @return An {@link InputStream} is returned.
     * @throws IOException
     *             is thrown in case the input stream cannot be opened.
     */
    public I create() throws IOException;

}