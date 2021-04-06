package com.yzz.exception;

import org.springframework.util.StringUtils;

public class EntityExistException extends RuntimeException{
    public EntityExistException(Class clazz, String field, String val) {
        super(EntityExistException.generateMessage(clazz.getSimpleName(), field, val));
    }

    public EntityExistException(String mes) {
        super(mes);
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity)
                + " with " + field + " " + val + " existed";
    }
}
