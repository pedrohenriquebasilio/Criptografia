package com.desafiotech.cryptography.controller.dto;

public record CreateTransactionRequest(String userDocument, String CreditCardToken, Long value ) {
}
