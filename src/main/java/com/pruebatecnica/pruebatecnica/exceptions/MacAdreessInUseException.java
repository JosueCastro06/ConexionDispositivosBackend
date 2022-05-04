package com.pruebatecnica.pruebatecnica.exceptions;

/**
 * MacAdreessInUseException
 */
public class MacAdreessInUseException extends Exception{

    public MacAdreessInUseException(String message) {
        super(message);
    }

    public MacAdreessInUseException(String message, Throwable cause) {
        super(message, cause);
    }   

}