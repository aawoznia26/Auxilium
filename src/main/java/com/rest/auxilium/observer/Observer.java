package com.rest.auxilium.observer;

import com.rest.auxilium.domain.Email;
import com.rest.auxilium.domain.Points;

public interface Observer {

    Email update(Points points);
}
