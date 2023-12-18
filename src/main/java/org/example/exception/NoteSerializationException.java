package org.example.exception;

public class NoteSerializationException extends TranspositionServiceException {

    public static final String MSG = "%s\n Caused by: %s";

    public NoteSerializationException(String msg, Throwable throwable) {
        super(String.format(MSG, msg, throwable.getMessage()));
    }

}
