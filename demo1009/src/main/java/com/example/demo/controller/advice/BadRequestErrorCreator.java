package com.example.demo.controller.advice;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;


import com.example.demo.model.BadRequestError;
import com.example.demo.model.InvalidParam;

public class BadRequestErrorCreator {

	public static BadRequestError from(MethodArgumentNotValidException ex) {
        var invalidParamList = createInvalidParamList(ex)
        ;
        var error = new BadRequestError();
        error.setInvalidParams(invalidParamList);
		return error;
	}

    private static List<InvalidParam> createInvalidParamList(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors().stream().map(BadRequestErrorCreator::createInvalidParam)
        .collect(Collectors.toList());
    }

    private static InvalidParam createInvalidParam(FieldError fieldError) {
        var invalidParam = new InvalidParam();
        invalidParam.setName(fieldError.getField());
        invalidParam.setReason(fieldError.getDefaultMessage());
        return invalidParam;
    }

    public static BadRequestError from(ConstraintViolationException ex) {
        var invalidParamList = createInvalidParamList(ex);
        
        var error = new BadRequestError();
        error.setInvalidParams(invalidParamList);
        return error;
    }

    private static List<InvalidParam> createInvalidParamList(ConstraintViolationException ex) {
        return ex.getConstraintViolations()
        .stream()
        .map(violation -> createInvalidParam(violation))
        .collect(Collectors.toList());
    }

    private static InvalidParam createInvalidParam(ConstraintViolation<?> violation) {
        var parameterOpt = StreamSupport.stream(violation.getPropertyPath().spliterator(),false)
        .filter(node -> node.getKind().equals(ElementKind.PARAMETER))
        .findFirst();
        var invalidParam = new InvalidParam();
        parameterOpt.ifPresent(p -> invalidParam.setName(p.getName()));
        invalidParam.setReason(violation.getMessage());
        return invalidParam;
    }

}
