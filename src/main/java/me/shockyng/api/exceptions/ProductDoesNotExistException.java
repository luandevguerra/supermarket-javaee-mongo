package me.shockyng.api.exceptions;

public class ProductDoesNotExistException extends Exception {

    public ProductDoesNotExistException(String message) {
        super(message);
    }
}
