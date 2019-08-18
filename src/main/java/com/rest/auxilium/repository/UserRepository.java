package com.rest.auxilium.repository;

import com.rest.auxilium.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
      User findFirstByEmail(String email);
      User findFirstByUuid(String uuid);
      User findFirstByEmailAndPassword(String email, String password);


}
