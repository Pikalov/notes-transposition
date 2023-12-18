package org.example.exception;

public class InvalidInputException extends TranspositionServiceException {

    public static final String MSG = "Invalid input parameters. %s. First parameter should be an absolute path to source JSON file, " +
            "second parameter should be an integer number of semitones to transpose.";

    public InvalidInputException(String msg) {
        super(String.format(MSG, msg));
    }
}
