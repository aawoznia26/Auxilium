package com.rest.auxilium.service;


import com.rest.auxilium.domain.Points;
import com.rest.auxilium.domain.Transaction;
import com.rest.auxilium.domain.TransactionStatus;
import com.rest.auxilium.domain.User;
import com.rest.auxilium.repository.ServicesRepository;
import com.rest.auxilium.repository.TransactionRepository;
import com.rest.auxilium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Transaction createTransaction(Transaction transaction){
        Transaction transactionToSave = new Transaction(userRepository.findFirstByUuid(transaction.getOwner().getUuid())
                , servicesRepository.findOne(transaction.getService().getId()), TransactionStatus.PUBLISHED);
        return  transactionRepository.save(transactionToSave);
    }

    public Transaction assignTransaction(Long transactionId, User user){
        Transaction transactionToAssign = transactionRepository.findOne(transactionId);
        User userToAssign = userRepository.findFirstByUuid(user.getUuid());
        transactionToAssign.setServiceProvider(userToAssign);
        transactionToAssign.setTransactionStatus(TransactionStatus.ASSIGNED);

        return transactionRepository.save(transactionToAssign);
    }

    public Transaction acceptTransaction(Transaction transaction){
        Transaction transactionToAccept = transactionRepository.findOne(transaction.getId());
        transactionToAccept.setTransactionStatus(TransactionStatus.ACCEPTED);
        Points points = new Points(transaction.getService().getPoints(), transaction.getServiceProvider());
        pointsService.issuePointsToUser(points);
        return  transactionRepository.save(transactionToAccept);
    }

    public void deleteTransaction(Long transactionId){
        Transaction transactionToDelete = transactionRepository.findOne(transactionId);
        transactionRepository.delete(transactionToDelete);
    }

    public List<Transaction> getAllPublishedTransactions(){
        return transactionRepository.findAllByTransactionStatusIs(TransactionStatus.PUBLISHED);
    }

    public List<Transaction> getAllTransactionsOwnedByUser(String uuid){
        return transactionRepository.findAllByOwner(userRepository.findFirstByUuid(uuid));
    }

    public List<Transaction> getAllTransactionsServicedByUser(String uuid){
        return transactionRepository.findAllByServiceProvider(userRepository.findFirstByUuid(uuid));
    }

}
