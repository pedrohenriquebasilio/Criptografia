package com.desafiotech.cryptography.entity;

import com.desafiotech.cryptography.service.CryptoService;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_transactions")
public class TransactionEntity {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;

    @Column(name = "user_document")
    private String encryptedUserDocument;


    @Column(name = "credit_card_token")
    private String encryptedCreditCardToken;

    @Column(name = "transaction_value")
    private long transactionValue;

    @Transient
    private String rawUserDocument;

    @Transient
    private String rawCreditCardToken;


    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getEncryptedUserDocument() {
        return encryptedUserDocument;
    }

    public void setEncryptedUserDocument(String encryptedUserDocument) {
        this.encryptedUserDocument = encryptedUserDocument;
    }

    public String getEncryptedCreditCardToken() {
        return encryptedCreditCardToken;
    }

    public void setEncryptedCreditCardToken(String encryptedCreditCardToken) {
        this.encryptedCreditCardToken = encryptedCreditCardToken;
    }

    public long getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(long transactionValue) {
        this.transactionValue = transactionValue;
    }

    public String getRawUserDocument() {
        return rawUserDocument;
    }

    public void setRawUserDocument(String rawUserDocument) {
        this.rawUserDocument = rawUserDocument;
    }

    public String getRawCreditCardToken() {
        return rawCreditCardToken;
    }

    public void setRawCreditCardToken(String rawCreditCardToken) {
        this.rawCreditCardToken = rawCreditCardToken;
    }

    @PrePersist
    public void prePersist(){
       this.encryptedUserDocument = CryptoService.encrypt(rawUserDocument);
       this.encryptedCreditCardToken = CryptoService.encrypt(rawCreditCardToken);
    }

    @PostLoad

    public void PostLoad(){

        this.rawCreditCardToken = CryptoService.decrypt(encryptedCreditCardToken);
        this.rawUserDocument = CryptoService.decrypt(encryptedUserDocument);
    }
}
