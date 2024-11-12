package com.desafiotech.cryptography.controller;
import com.desafiotech.cryptography.controller.dto.CreateTransactionRequest;
import com.desafiotech.cryptography.controller.dto.TransactionResponse;
import com.desafiotech.cryptography.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

    private final TransactionService service;


    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateTransactionRequest request){
        service.create(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> ListAll(@RequestParam(name = "page", defaultValue = "0")Integer page,@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize ){
        var body = service.listALL(page, pageSize);
        return ResponseEntity.ok(body);
    }
}
