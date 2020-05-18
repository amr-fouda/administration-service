package org.fouda.administration.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @AfterThrowing(value = "execution(* org.fouda.administration.services.UserServiceFacade.*(..))", throwing = "exception")
    public void before(Exception exception) {
        log.error("Business layer exception", exception);
    }
}