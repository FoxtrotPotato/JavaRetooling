package com.foxtrotpotato.chickentest.aspect;

import com.foxtrotpotato.chickentest.entity.Farm;
import com.foxtrotpotato.chickentest.entity.Log;
import com.foxtrotpotato.chickentest.entity.User;
import com.foxtrotpotato.chickentest.service.FarmService;
import com.foxtrotpotato.chickentest.service.LogService;
import com.foxtrotpotato.chickentest.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private final LogService logService;
    private final FarmService farmService;
    private final UserService userService;

    @Autowired
    public LoggingAspect(LogService logService, FarmService farmService, UserService userService) {
        this.logService = logService;
        this.farmService = farmService;
        this.userService = userService;
    }

    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.foxtrotpotato.chickentest.repository.ChickenRepository.save(..))")
    private void forChickenSave() {}
    @Pointcut("execution(* com.foxtrotpotato.chickentest.repository.ChickenRepository.deleteById(..))")
    private void forChickenDelete() {}
    @Pointcut("forChickenSave() || forChickenDelete()")
    private void forChickenRepository() {}

    @Pointcut("execution(* com.foxtrotpotato.chickentest.repository.EggRepository.save(..))")
    private void forEggSave() {}
    @Pointcut("execution(* com.foxtrotpotato.chickentest.repository.EggRepository.deleteById(..))")
    private void forEggDelete() {}
    @Pointcut("forEggSave() || forEggDelete()")
    private void forEggRepository() {}

    @Pointcut("execution(* com.foxtrotpotato.chickentest.repository.ParameterRepository.save(..))")
    private void forParameterRepository() {}

    @Pointcut("execution(* com.foxtrotpotato.chickentest.repository.ProductRepository.save(..))")
    private void forProductRepository() {}

    @Pointcut("execution(* com.foxtrotpotato.chickentest.repository.TransactionRepository.save(..))")
    private void forTransactionRepository() {}

    @Pointcut("execution(* com.foxtrotpotato.chickentest.repository.TransactionDetailRepository.save(..))")
    private void forTransactionDetailRepository() {}

    @Pointcut("forChickenRepository() || forEggRepository() || forParameterRepository() || forProductRepository() || forTransactionRepository() || forTransactionDetailRepository()")
    private void forDAOPackage() {
    }

    @AfterReturning(pointcut = "forDAOPackage()")
    public void afterReturning(JoinPoint theJoinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User theUser = userService.findByUserName(authentication.getName());
        Farm theFarm = farmService.getFarmByLoggedUser();

        String signature = theJoinPoint.getSignature().getName();
        Object[] args = theJoinPoint.getArgs();
        String entityName = args[0].getClass().getSimpleName();

        String logDetail = entityName + " - " + signature;
        Log theLog = new Log(logDetail, LocalDateTime.now(), theUser, theFarm);

        logger.info("//// @AfterReturning: " + entityName + " - " + signature);

        logService.save(theLog);
    }

}


