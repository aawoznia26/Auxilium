package com.rest.auxilium.service;


import com.rest.auxilium.domain.Email;
import com.rest.auxilium.domain.EmailGroup;
import com.rest.auxilium.domain.ServicesTransactionStatus;
import com.rest.auxilium.repository.PointsRepository;
import com.rest.auxilium.repository.UserRepository;
import com.rest.auxilium.vipDecorator.BasicVipPrize;
import com.rest.auxilium.vipDecorator.VipBasedOnPointsDecorator;
import com.rest.auxilium.vipDecorator.VipBasedOnTransactionsNumberDecorator;
import com.rest.auxilium.vipDecorator.VipPrize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class VipService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointsRepository pointsRepository;

    @Autowired
    private MailService mailService;

    public void assignPointsCoupons(){
        LOGGER.info("Preparation to assign points coupons");
        userRepository.findAll().stream().forEach( u ->{
                Integer pointsSum = pointsRepository.findAllByUser(u).stream()
                        .map(p -> p.getValue())
                        .collect(Collectors.summingInt(Integer::intValue));

                if(pointsSum >= 2000 && !u.isRewardedForPoints()){
                    VipPrize vipPrize = new BasicVipPrize();
                    vipPrize = new VipBasedOnPointsDecorator(vipPrize);
                    u.setRewardedForPoints(true);
                    Email email = new Email(u.getEmail(), vipPrize.getName(), vipPrize.getDescription()
                            + ". Kod kuponu: " + vipPrize.generateCode(), u.getName(), EmailGroup.VIP);
                    mailService.send(email);
                }
        });
        LOGGER.info("Points coupons assignment finished");

    }

    public void assignTransactionsCoupons(){
        LOGGER.info("Preparation to assign transaction coupons");
        userRepository.findAll().stream().forEach( u ->{
            long transactionsSCount = u.getProviderTransaction().stream()
                    .filter(t -> t.getServicesTransactionStatus() == ServicesTransactionStatus.ACCEPTED).count();

            if(transactionsSCount >= 15 && !u.isRewardedForTransactions()){
                VipPrize vipPrize = new BasicVipPrize();
                vipPrize = new VipBasedOnTransactionsNumberDecorator(vipPrize);
                u.setRewardedForTransactions(true);
                Email email = new Email(u.getEmail(), vipPrize.getName(), vipPrize.getDescription()
                        + ". Kod kuponu: " + vipPrize.generateCode(), u.getName(), EmailGroup.VIP);
                mailService.send(email);
            }
        });
        LOGGER.info("Transactions coupons assignment finished");
    }
}
