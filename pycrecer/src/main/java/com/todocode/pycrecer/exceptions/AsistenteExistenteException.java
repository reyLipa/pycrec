package com.todocode.pycrecer.exceptions;

public class AsistenteExistenteException extends RuntimeException {
    public AsistenteExistenteException() {
    }

    public AsistenteExistenteException(String message) {
        super(message);
    }

    public AsistenteExistenteException(String message, Throwable cause) {
        super(message, cause);
    }
}
