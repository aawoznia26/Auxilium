package com.rest.auxilium.service;

import com.rest.auxilium.domain.PointStatus;
import com.rest.auxilium.domain.Points;
import com.rest.auxilium.repository.PointsRepository;
import com.rest.auxilium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class PointsService {

    @Autowired
    private PointsRepository pointsRepository;

    @Autowired
    private UserRepository userRepository;

    public Points issuePointsToUser(Points points){
        LocalDate expirationDate = LocalDate.now().plusDays(60);
        Points newPoints = new Points(points.getValue(), expirationDate, PointStatus.ISSUED, points.getUser());
        return pointsRepository.save(newPoints);
    }

    public Points expirePoints(Points points){
        Points foundPoints = pointsRepository.findOne(points.getId());
        foundPoints.setPointStatus(PointStatus.EXPIRED);
        return pointsRepository.save(foundPoints);
    }

    public Points usePoints(Points points){
        Points foundPoints = pointsRepository.findOne(points.getId());
        foundPoints.setPointStatus(PointStatus.USED);
        return pointsRepository.save(foundPoints);
    }

    public Long getAllUserPoints(String uuid){
        Long sum = pointsRepository.findAllByUser(userRepository.findFirstByUuid(uuid)).stream()
                .filter(p -> p.getPointStatus()==PointStatus.ISSUED)
                .mapToLong(p -> p.getValue())
                .sum();
        return sum;
    }

}
