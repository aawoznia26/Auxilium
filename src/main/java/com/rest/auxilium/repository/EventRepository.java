package com.rest.auxilium.repository;

import com.rest.auxilium.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
