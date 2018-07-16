package com.mistifler.demo.springbootaop.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mistifler on 2018/7/16.
 */

@Aspect
@Component
public class ControllerAop {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAop.class);

    @Pointcut("execution(public * com.mistifler.demo.springbootaop.controller.*Controller.*(..))")
    public void pointCutMethod() {
        //
    }

    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        HttpServletResponse response = sra.getResponse();

        String uri = request.getRequestURI();
        String method = request.getMethod();

        long startTime = System.currentTimeMillis();

        String queryString = request.getQueryString();
        logger.info("Request begin, method: {}, uri: {}, queryString: {}", method, uri, queryString);

        try {
            Object result = pjp.proceed();
            long executeTime = System.currentTimeMillis() - startTime;
            logger.info("Request begin，cost: {}, uri: {}, controller return: {}",
                    executeTime, uri, JSON.toJSONString(result));
            return result;
        } catch (Throwable throwable) {
            logger.info(
                    "Request catch Exception, method: {}, uri: {}, queryString: {},  cost: {}, exception details{}...\n",
                    method, uri, queryString, (System.currentTimeMillis() - startTime), throwable.getMessage());
            throw throwable;
        } finally {
            logger.info("aop finish");
        }
    }

}
