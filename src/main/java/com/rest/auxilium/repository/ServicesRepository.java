package com.rest.auxilium.repository;


import com.rest.auxilium.domain.Services;
import com.rest.auxilium.domain.ServicesTransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {

    List<Services> findAllByServicesTransactionStatus(ServicesTransactionStatus servicesTransactionStatus);
    List<Services> findAllByCityAndServicesTransactionStatus (String city, ServicesTransactionStatus servicesTransactionStatus);
    List<Services> findAllByNameAndServicesTransactionStatus (String name, ServicesTransactionStatus servicesTransactionStatus);
    List<Services> findAllByCityAndNameAndServicesTransactionStatus (String city, String name, ServicesTransactionStatus servicesTransactionStatus);
}
