package com.desafiotech.cryptography.service;
import com.desafiotech.cryptography.controller.dto.CreateTransactionRequest;
import com.desafiotech.cryptography.controller.dto.TransactionResponse;
import com.desafiotech.cryptography.entity.TransactionEntity;
import com.desafiotech.cryptography.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }


    public void create(CreateTransactionRequest request){
        var entity =  new TransactionEntity();
        entity.setRawCreditCardToken(request.CreditCardToken());
        entity.setRawUserDocument(request.userDocument());
        entity.setTransactionValue(request.value());

        repository.save(entity);
    }


    public Page<TransactionResponse> listALL(int page, int pageSize){
        var content = repository.findAll(PageRequest.of(page, pageSize));

        return content.map(TransactionResponse::fromEntity);
    }
}
