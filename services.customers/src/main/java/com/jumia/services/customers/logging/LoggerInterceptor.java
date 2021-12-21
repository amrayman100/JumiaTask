package com.jumia.services.customers.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@Aspect
@Configuration
public class LoggerInterceptor {
    enum MethodState {
        ENTERING, EXITING, EXCEPTION
    }

    private static Logger classLogger = LoggerFactory.getLogger(LoggerInterceptor.class);

    private static final String ENTERING_LOG_FORMAT = "state: {} - method: {} - args: {}";
    private static final String EXITING_LOG_FORMAT = "state: {} - method: {} - execTime: {} - returnValue: {}";
    private static final String EXCEPTION_LOG_FORMAT = "state: {} - method: {} - message {} - execTime: {}";
    private long start = 0;
    private long end = 0;
    private static Map<Throwable, ?> loggedThrowables = new WeakHashMap<>();

    @Pointcut("within(@com.jumia.services.customers.logging.Loggable *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the
        // advices.
    }

    @Before(value = "springBeanPointcut()")
    public void before(JoinPoint jp) {
        try {
            ObjectWriter ow = getObjectMapper().writer();
            String json = ow.writeValueAsString(jp.getArgs());
            this.start = System.currentTimeMillis();
            String methodName = jp.getSignature().getName();
            String targetClassName = jp.getSignature().getDeclaringTypeName();
            Logger logger = LoggerFactory.getLogger(!StringUtils.isEmpty(targetClassName) ? targetClassName : "UnknownClassLogger");
            logEnteringMethod(logger, methodName, json);
        } catch (Exception e) {
            classLogger.error("caught failure in before", e);
        }
    }

    @AfterReturning(pointcut = "springBeanPointcut()", returning = "retVal")
    public void after(JoinPoint jp, Object retVal) {
        try {
            ObjectWriter ow = getObjectMapper().writer();
            String json = ow.writeValueAsString(retVal);
            this.end = System.currentTimeMillis();
            String methodName = jp.getSignature().getName();
            String targetClassName = jp.getSignature().getDeclaringTypeName();
            Logger logger = LoggerFactory.getLogger(!StringUtils.isEmpty(targetClassName) ? targetClassName : "UnknownClassLogger");
            logExitingMethod(logger, methodName, json, end - start);
        } catch (Exception e) {
            classLogger.error("caught failure in after", e);
        }
    }

    @AfterThrowing(value = "springBeanPointcut()", throwing = "t")
    public void throwing(JoinPoint jp, Throwable t) {
        try {
            this.end = System.currentTimeMillis();
            String methodName = jp.getSignature().getName();
            String targetClassName = jp.getSignature().getDeclaringTypeName();
            Logger logger = LoggerFactory.getLogger(!StringUtils.isEmpty(targetClassName) ? targetClassName : "UnknownClassLogger");
            logExceptionInMethod(logger, methodName, t, end - start);
        } catch (Exception e1) {
            classLogger.error("caught failure in throwing", t);
        }

    }

    private ObjectMapper getObjectMapper(){
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        timeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(timeModule);
        return objectMapper;
    }

    Object[] pickArgsToSerialize(Object... objects) {
        List<Object> args = new ArrayList<>();
        try {
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] != null && objects[i].getClass() != null && objects[i].getClass().getPackage() != null) {
                    args.add(objects[i]);
                }
            }

        } catch (Exception ex) {
            classLogger.error("caught failure in auditMethod:failed to serialize due to:{}", ex.getMessage());
        }
        return args.toArray();
    }


    public static void logEnteringMethod(Logger log, String methodName, Object... args) {
        log.info(ENTERING_LOG_FORMAT, MethodState.ENTERING,  methodName , args);
    }

    public static void logExitingMethod(Logger log, String methodName, Object returnValue, long execTime) {
        log.info(EXITING_LOG_FORMAT, MethodState.EXITING ,methodName, execTime, returnValue);
    }

    public static void logExceptionInMethod(Logger log, String methodName, Throwable ex, long execTime) {
        log.error(EXCEPTION_LOG_FORMAT, MethodState.EXCEPTION, methodName, ex.getMessage(), execTime, ex);
    }

}
