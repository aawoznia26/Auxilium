package com.rest.auxilium.service;

import com.rest.auxilium.domain.PointStatus;
import com.rest.auxilium.domain.Points;
import com.rest.auxilium.domain.User;
import com.rest.auxilium.repository.PointsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PointsService{

    @Autowired
    private PointsRepository pointsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PointsService.class);

    public Points issuePointsToUser(Points points){
        LOGGER.info("Issuing points started");
        LocalDate expirationDate = LocalDate.now().plusDays(60);
        Points newPoints = new Points(points.getValue(), expirationDate, PointStatus.ISSUED, points.getUser());
        if(points.getUser().isNotifyAboutPoints()){
            LOGGER.info("Prepare to send notification about points issuance to user uuid: " + newPoints.getUser().getUuid());
            mailService.send(points.getUser().update(newPoints));
        }
        LOGGER.info("Issuing points" + points.toString() + " finished");
        return pointsRepository.save(newPoints);
    }

    public void expirePoints(LocalDate expirationDate){
        LOGGER.info("Expiring points started");
        List<Points> foundPoints = pointsRepository.findAllByExpirationDate(expirationDate);
        LOGGER.info(LocalDate.now() + " " + foundPoints.size() + " points to expire.");
        foundPoints.stream().forEach(p -> {
                p.setPointStatus(PointStatus.EXPIRED);
                if(p.getUser().isNotifyAboutPoints()){
                    LOGGER.info("Prepare to send notification about points expiration to user uuid: " + p.getUser().getUuid());
                    mailService.send(p.getUser().update(p));
                }
        });
        LOGGER.info("Expiring points finished");

    }

    public Points usePoints(Points points){
        LOGGER.info("Using points started");
        Points foundPoints = pointsRepository.findOne(points.getId());
        foundPoints.setPointStatus(PointStatus.USED);
        LOGGER.info("Points " + points.toString() + " marked as used");
        if(foundPoints.getUser().isNotifyAboutPoints()){
            LOGGER.info("Prepare to send notification about points usage to user uuid: " + foundPoints.getUser().getUuid());
            mailService.send(foundPoints.getUser().update(points));
        }
        LOGGER.info("Using points finished");
        return pointsRepository.save(foundPoints);
    }

    public Long getAllUserPoints(String uuid){
        LOGGER.info("Getting all user points with status issued");
        User foundUser = userService.findUserByUUID(uuid);
        if(foundUser != null){
            Long sumIssued = pointsRepository.findAllByUser(foundUser).stream()
                    .filter(p -> p.getPointStatus()==PointStatus.ISSUED)
                    .mapToLong(p -> p.getValue())
                    .sum();
            LOGGER.info("User " + foundUser.getUuid() + " has " + sumIssued);
            return sumIssued;

        }
        LOGGER.info("0 issued points on user uuid " + foundUser.getUuid() + " account");
        return 0L;
    }

    public Points savePoints(Points points){
        LOGGER.info("Saving points");
        return pointsRepository.save(points);

    }

}
