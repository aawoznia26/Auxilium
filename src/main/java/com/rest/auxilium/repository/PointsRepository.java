package com.rest.auxilium.repository;


import com.rest.auxilium.domain.Points;
import com.rest.auxilium.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointsRepository extends JpaRepository<Points, Long> {
    List<Points> findAllByUser(User user);
}
