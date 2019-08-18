package com.rest.auxilium.repository;


import com.rest.auxilium.domain.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {

    List<Services> findAllByCity (String city);
    List<Services> findAllByName (String name);
    List<Services> findAllByCityAndName (String city, String name);

}
