package com.naman.lms.exception;

import com.naman.lms.model.ErrorResponseModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class AllExceptionAdvice {
	Logger logger = LoggerFactory.getLogger(AllExceptionAdvice.class);

    @ExceptionHandler(BookException.class)
    public ResponseEntity<ErrorResponseModel> handelBookNotFoundException(BookException e, WebRequest wr) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel();
        errorResponseModel.setErrorCode(404);
        errorResponseModel.setErrorMessage(e.getMessage());
        logger.error("error:{}",errorResponseModel);
        return new ResponseEntity<ErrorResponseModel>(errorResponseModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseModel> handelBookNotFoundException(NotFoundException e, WebRequest wr) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel();
        errorResponseModel.setErrorCode(404);
        errorResponseModel.setErrorMessage(e.getMessage());
        logger.error("error:{}",errorResponseModel);
        return new ResponseEntity<ErrorResponseModel>(errorResponseModel, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseModel> handelValidationException(ValidationException e, WebRequest wr) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel();
        errorResponseModel.setErrorCode(404);
        errorResponseModel.setErrorMessage(e.getMessage());
        logger.error("error:{}",errorResponseModel);
        return new ResponseEntity<ErrorResponseModel>(errorResponseModel, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponseModel> handelMemberException(MemberException e, WebRequest wr) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel();
        errorResponseModel.setErrorCode(404);
        errorResponseModel.setErrorMessage(e.getMessage());
        logger.error("error:{}",errorResponseModel);
        return new ResponseEntity<ErrorResponseModel>(errorResponseModel, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(WrongDateException.class)
    public ResponseEntity<ErrorResponseModel> handleWrongDateException(WrongDateException e, WebRequest wr) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel();
        errorResponseModel.setErrorCode(404);
        errorResponseModel.setErrorMessage(e.getMessage());
        logger.error("error:{}",errorResponseModel);
        return new ResponseEntity<ErrorResponseModel>(errorResponseModel, HttpStatus.NOT_FOUND);
    }
}
