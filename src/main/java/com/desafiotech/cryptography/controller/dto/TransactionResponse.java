package com.desafiotech.cryptography.controller.dto;

import com.desafiotech.cryptography.entity.TransactionEntity;

public record TransactionResponse(Long id, String userDocument, String creditCardToken, long value) {



    public static TransactionResponse fromEntity(TransactionEntity entity){

        return new TransactionResponse(
                entity.getTransactionId(),
                entity.getRawUserDocument(),
                entity.getRawCreditCardToken(),
                entity.getTransactionValue()

        );
    }
}
