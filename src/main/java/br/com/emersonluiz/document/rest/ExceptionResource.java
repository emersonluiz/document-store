package br.com.emersonluiz.document.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import br.com.emersonluiz.document.exception.FailureReason;
import br.com.emersonluiz.document.exception.NotFoundException;

public class ExceptionResource {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionResource.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    FailureReason handleNotFoundException(HttpServletRequest req, Exception e) {
        logger.error("Not Found Exception");
        return new FailureReason(req.getRequestURL().toString(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseBody
    FailureReason handleBadRequestException(HttpServletRequest req, Exception e) {
        logger.error("Bad Request Exception");
        return new FailureReason(req.getRequestURL().toString(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    FailureReason handleException(HttpServletRequest req, Exception e) {
        logger.error("Internal Server Error Exception");
        return new FailureReason(req.getRequestURL().toString(), e.getMessage());
    }

}
