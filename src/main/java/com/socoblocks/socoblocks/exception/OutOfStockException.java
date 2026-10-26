package com.socoblocks.socoblocks.exception;

public class OutOfStockException extends RuntimeException {
    private final int disponibility;
    public OutOfStockException(String productName, int disponibility) {
        super(disponibility <= 0 ? productName + " está agotado" : "Solo hay " + disponibility + " unidad(es) disponibles de " + productName);
        this.disponibility = Math.max(disponibility, 0);
    }

    public int getDisponibility() { return disponibility; }
}
