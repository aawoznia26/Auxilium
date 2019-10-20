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
        LOGGER.info("Transaction creation is starting");

        if(servicesRepository.findOne(transaction.getService().getId()) != null && userRepository.findFirstByUuid(transaction.getOwner().getUuid()) != null){
            Services service = servicesRepository.findOne(transaction.getService().getId());
            LOGGER.info("Service found: " + service.toString());
            User user = userRepository.findFirstByUuid(transaction.getOwner().getUuid());
            LOGGER.info("User found: " + user.toString());

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
        LOGGER.info("Transaction assignment is starting");
        if(transactionRepository.findFirstByService_Id(servicesId) != null && servicesRepository.getOne(servicesId) != null){
            Transaction transactionToAssign = transactionRepository.findFirstByService_Id(servicesId);
            LOGGER.info("Transaction found: " + transactionToAssign.toString());

            Services serviceToAssign= servicesRepository.getOne(servicesId);
            LOGGER.info("Service found: " + serviceToAssign.toString());

            User userToAssign = userRepository.findFirstByUuid(user.getUuid());
            LOGGER.info("User found: " + userToAssign.toString());

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
        LOGGER.info("Transaction acceptance is starting");
        if(transactionRepository.findFirstByService_Id(serviceId) != null &&  servicesRepository.getOne(serviceId) != null){
            Transaction transactionToAccept= transactionRepository.findFirstByService_Id(serviceId);
            LOGGER.info("Transaction found: " + transactionToAccept.toString());

            Services serviceToAccept= servicesRepository.getOne(serviceId);
            LOGGER.info("Services found: " + serviceToAccept.toString());

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
        LOGGER.info("Getting all published transactions");
        return transactionRepository.findAllByServicesTransactionStatus(ServicesTransactionStatus.PUBLISHED);
    }

    public List<Transaction> getAllTransactionsOwnedByUser(String uuid){
        LOGGER.info("Getting all transactions owned by user");
        return Optional.ofNullable(userRepository.findFirstByUuid(uuid))
                .map(user -> transactionRepository.findAllByOwner(user)).orElse(new ArrayList<Transaction>());

    }

    public List<Transaction> getAllTransactionsServicedByUser(String uuid){
        LOGGER.info("Getting all transactions serviced by user");
        return Optional.ofNullable(userRepository.findFirstByUuid(uuid))
                .map(user -> transactionRepository.findAllByServiceProvider(user)).orElse(new ArrayList<Transaction>());
    }

}
