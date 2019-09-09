package com.rest.auxilium.domain;

import com.rest.auxilium.dto.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {


    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "OWNER_ID")
    private User owner;

    @ManyToOne()
    @JoinColumn(name = "PROVIDER_ID")
    private User serviceProvider;

    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL)
    private Services service;

    @Enumerated(EnumType.STRING)
    private ServicesTransactionStatus servicesTransactionStatus;

    public Transaction(User owner, Services service) {
        this.owner = owner;
        this.service = service;
    }

    public Transaction(User owner, Services service, ServicesTransactionStatus servicesTransactionStatus) {
        this.owner = owner;
        this.service = service;
        this.servicesTransactionStatus = servicesTransactionStatus;
    }

    public static TransactionDto mapToTransationDto(final Transaction transaction){
        if(transaction.getServiceProvider() == null){
            TransactionDto transactionDto = new TransactionDto(transaction.getId(), User.mapToUserDto(transaction.getOwner())
                    , Services.mapToServicesDto(transaction.getService()), transaction.getServicesTransactionStatus());
            return transactionDto;
        } else {
            TransactionDto transactionDto = new TransactionDto(transaction.getId(), User.mapToUserDto(transaction.getOwner()),User.mapToUserDto(transaction.getServiceProvider())
                    , Services.mapToServicesDto(transaction.getService()), transaction.getServicesTransactionStatus());
            return transactionDto;
        }
    }

    public static List<TransactionDto> mapToTransactionDtoList(final List<Transaction> transactions){
        return transactions.stream()
                .map(t -> mapToTransationDto(t))
                .collect(Collectors.toList());

    }


}
