package com.socoblocks.socoblocks.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String productName) {
        super("Not enough stock for product: " + productName);
    }
}
