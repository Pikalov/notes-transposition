package org.example.exception;

/**
 * Intentionally used unchecked exception to avoid specifying all the exceptions in all methods' signatures.
 */
public abstract class TranspositionServiceException extends RuntimeException {

    public TranspositionServiceException(String msg) {
        super(msg);
    }
}
