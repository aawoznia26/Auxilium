package com.rest.auxilium.service;


import com.rest.auxilium.domain.*;
import com.rest.auxilium.repository.ServicesRepository;
import com.rest.auxilium.repository.TransactionRepository;
import com.rest.auxilium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private PointsService pointsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    public Transaction createTransaction(Transaction transaction){

        if(servicesRepository.findOne(transaction.getService().getId()) != null && userRepository.findFirstByUuid(transaction.getOwner().getUuid()) != null){
            Services service = servicesRepository.findOne(transaction.getService().getId());
            User user = userRepository.findFirstByUuid(transaction.getOwner().getUuid());
            Transaction transactionToSave = new Transaction(user, service, ServicesTransactionStatus.PUBLISHED);
            service.setTransaction(transactionToSave);
            LOGGER.info("Transaction has been created");
            return  transactionRepository.save(transactionToSave);
        } else {
            LOGGER.error("Transaction has not been created. Wrong service or user data");
            return new Transaction();
        }
    }

    public Transaction assignTransaction(Long servicesId, User user){

        if(transactionRepository.findFirstByService_Id(servicesId) != null && servicesRepository.getOne(servicesId) != null){
            Transaction transactionToAssign = transactionRepository.findFirstByService_Id(servicesId);
            Services serviceToAssign= servicesRepository.getOne(servicesId);
            User userToAssign = userRepository.findFirstByUuid(user.getUuid());
            transactionToAssign.setServiceProvider(userToAssign);
            transactionToAssign.setServicesTransactionStatus(ServicesTransactionStatus.ASSIGNED);
            serviceToAssign.setServicesTransactionStatus(ServicesTransactionStatus.ASSIGNED);

            LOGGER.info("Transaction has been assigned");
            return transactionRepository.save(transactionToAssign);

        } else {

            LOGGER.error("Transaction has not been assigned. Wrong service or user data");
            return new Transaction();
        }

    }

    public Transaction acceptTransaction(Long serviceId){
        if(transactionRepository.findFirstByService_Id(serviceId) != null &&  servicesRepository.getOne(serviceId) != null){
            Transaction transactionToAccept= transactionRepository.findFirstByService_Id(serviceId);
            Services serviceToAccept= servicesRepository.getOne(serviceId);
            transactionToAccept.setServicesTransactionStatus(ServicesTransactionStatus.ACCEPTED);
            serviceToAccept.setServicesTransactionStatus(ServicesTransactionStatus.ACCEPTED);
            Points points = new Points(transactionToAccept.getService().getPoints(), transactionToAccept.getServiceProvider());
            pointsService.issuePointsToUser(points);

            LOGGER.info("Transaction has been accepted");
            return  transactionRepository.save(transactionToAccept);
        } else {

            LOGGER.error("Transaction has not been accepted. Wrong service or user data");
            return new Transaction();
        }

    }

    public List<Transaction> getAllPublishedTransactions(){
        return transactionRepository.findAllByServicesTransactionStatus(ServicesTransactionStatus.PUBLISHED);
    }

    public List<Transaction> getAllTransactionsOwnedByUser(String uuid){
        return Optional.ofNullable(userRepository.findFirstByUuid(uuid))
                .map(user -> transactionRepository.findAllByOwner(user)).orElse(new ArrayList<Transaction>());

    }

    public List<Transaction> getAllTransactionsServicedByUser(String uuid){
        return Optional.ofNullable(userRepository.findFirstByUuid(uuid))
                .map(user -> transactionRepository.findAllByServiceProvider(user)).orElse(new ArrayList<Transaction>());
    }

}
