package com.rest.auxilium.service;


import com.rest.auxilium.domain.Email;
import com.rest.auxilium.domain.ServicesTransactionStatus;
import com.rest.auxilium.repository.PointsRepository;
import com.rest.auxilium.repository.UserRepository;
import com.rest.auxilium.vipDecorator.BasicVipPrize;
import com.rest.auxilium.vipDecorator.VipBasedOnPointsDecorator;
import com.rest.auxilium.vipDecorator.VipBasedOnTransactionsNumberDecorator;
import com.rest.auxilium.vipDecorator.VipPrize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class VipService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointsRepository pointsRepository;

    @Autowired
    private MailService mailService;

    public void assignPointsCoupons(){
        userRepository.findAll().stream().forEach( u ->{
                Integer pointsSum = pointsRepository.findAllByUser(u).stream()
                        .map(p -> p.getValue())
                        .collect(Collectors.summingInt(Integer::intValue));

                if(pointsSum >= 2000 && !u.isRewardedForPoints()){
                    VipPrize vipPrize = new BasicVipPrize();
                    vipPrize = new VipBasedOnPointsDecorator(vipPrize);
                    u.setRewardedForPoints(true);
                    Email email = new Email(u.getEmail(), vipPrize.getName(), vipPrize.getDescription()
                            + ". Kod kuponu: " + vipPrize.generateCode(), u.getName());
                    mailService.send(email);
                }
        });

    }

    public void assignTransactionsCoupons(){
        userRepository.findAll().stream().forEach( u ->{
            long transactionsSCount = u.getProviderTransaction().stream()
                    .filter(t -> t.getServicesTransactionStatus() == ServicesTransactionStatus.ACCEPTED).count();

            if(transactionsSCount >= 15 && !u.isRewardedForTransactions()){
                VipPrize vipPrize = new BasicVipPrize();
                vipPrize = new VipBasedOnTransactionsNumberDecorator(vipPrize);
                u.setRewardedForTransactions(true);
                Email email = new Email(u.getEmail(), vipPrize.getName(), vipPrize.getDescription()
                        + ". Kod kuponu: " + vipPrize.generateCode(), u.getName());
                mailService.send(email);
            }
        });
    }
}
