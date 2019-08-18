package com.rest.auxilium.dto;


import com.rest.auxilium.domain.Transaction;
import com.rest.auxilium.domain.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Long id;

    private UserDto ownerDto;

    private UserDto serviceProviderDto;

    private ServicesDto servicesDto;

    private TransactionStatus transactionStatus;

    public TransactionDto(UserDto ownerDto, ServicesDto servicesDto) {
        this.ownerDto = ownerDto;
        this.servicesDto = servicesDto;
    }

    public TransactionDto(Long id, UserDto ownerDto, ServicesDto servicesDto, TransactionStatus transactionStatus) {
        this.id = id;
        this.ownerDto = ownerDto;
        this.servicesDto = servicesDto;
        this.transactionStatus = transactionStatus;
    }

    public static Transaction mapToTransation(final TransactionDto transactionDto){
        if(transactionDto.getId() == null){
            Transaction transaction = new Transaction(UserDto.mapToUser(transactionDto.getOwnerDto()), ServicesDto.mapToServices(transactionDto.getServicesDto()));
            return transaction;
        } else {
            Transaction transaction = new Transaction(transactionDto.getId(), UserDto.mapToUser(transactionDto.getOwnerDto())
                    , UserDto.mapToUser(transactionDto.getServiceProviderDto()), ServicesDto.mapToServices(transactionDto.getServicesDto())
                    , transactionDto.getTransactionStatus());
            return transaction;
        }
    }

    public static List<Transaction> mapToTransationList(final List<TransactionDto> transactionsDto){
        return transactionsDto.stream()
                .map(t -> mapToTransation(t))
                .collect(Collectors.toList());

    }
}
