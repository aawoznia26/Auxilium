package com.rest.auxilium.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Points {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long id;

    private int value;

    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    private PointStatus pointStatus;

    @ManyToOne()
    @JoinColumn(name = "USER_ID")
    private User user;

    public Points(int value, User user) {
        this.value = value;
        this.user = user;
    }

    public Points(int value, LocalDate expirationDate, PointStatus pointStatus, User user) {
        this.value = value;
        this.expirationDate = expirationDate;
        this.pointStatus = pointStatus;
        this.user = user;
    }

}
