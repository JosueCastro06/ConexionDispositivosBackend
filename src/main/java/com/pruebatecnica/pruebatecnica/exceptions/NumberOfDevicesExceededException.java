package com.pruebatecnica.pruebatecnica.exceptions;

/**
 * Esta excepción se genera en caso de que la cantidad de dispositivos asginados a una conexión supere
 * el valor coonfigurado.
 */
public class NumberOfDevicesExceededException extends Exception {

    public NumberOfDevicesExceededException(String message) {
        super(message);
    }

    public NumberOfDevicesExceededException(String message, Throwable cause) {
        super(message, cause);
    }

}
