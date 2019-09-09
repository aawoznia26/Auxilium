package com.rest.auxilium.controllers;


import com.rest.auxilium.domain.Transaction;
import com.rest.auxilium.dto.TransactionDto;
import com.rest.auxilium.dto.UserDto;
import com.rest.auxilium.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(method = RequestMethod.POST, value = "/transaction")
    public TransactionDto createTransaction(@RequestBody TransactionDto transactionDto) {
        return Transaction.mapToTransationDto(transactionService.createTransaction(TransactionDto.mapToTransation(transactionDto)));
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/transaction/assign/{serviceId}")
    public TransactionDto assignTransaction(@RequestBody UserDto userDto, @PathVariable Long serviceId) {
        return Transaction.mapToTransationDto(transactionService.assignTransaction(serviceId, UserDto.mapToUser(userDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/transaction/accept/{serviceId}")
    public TransactionDto acceptTransaction(@PathVariable Long serviceId) {
        return Transaction.mapToTransationDto(transactionService.acceptTransaction(serviceId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transaction")
    public List<TransactionDto> getAllPublishedTransactions() {
        return Transaction.mapToTransactionDtoList(transactionService.getAllPublishedTransactions());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transaction/owned/{uuid}")
    public List<TransactionDto> getAllTransactionsOwnedByUser(@PathVariable String uuid) {
        List<Transaction> list = transactionService.getAllTransactionsOwnedByUser(uuid);
        return Transaction.mapToTransactionDtoList(list);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transaction/serviced/{uuid}")
    public List<TransactionDto> getAllTransactionsServicedByUser(@PathVariable String uuid) {
        return Transaction.mapToTransactionDtoList(transactionService.getAllTransactionsServicedByUser(uuid));
    }

}
