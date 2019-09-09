package com.rest.auxilium.repository;

import com.rest.auxilium.domain.Transaction;
import com.rest.auxilium.domain.ServicesTransactionStatus;
import com.rest.auxilium.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByServicesTransactionStatus(ServicesTransactionStatus servicesTransactionStatus);
    List<Transaction> findAllByOwner(User user);
    List<Transaction> findAllByServiceProvider(User user);
    Transaction findFirstByService_Id(Long id);
}
