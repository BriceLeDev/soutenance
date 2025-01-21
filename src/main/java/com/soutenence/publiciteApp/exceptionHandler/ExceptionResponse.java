package com.soutenence.publiciteApp.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.hibernate.tool.schema.spi.SchemaTruncator;

import java.util.Map;
import java.util.Set;
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {

    private Integer busynesErrorCode;
    private String bussnessErrorDescription;
    private String error;
    private Set<String> validationError;
    private Map<String,String> errors;

    public ExceptionResponse() {
    }

    public ExceptionResponse(Integer busynesErrorCode, String bussnessErrorDescription, String error, Set<String> validationError, Map<String, String> errors) {
        this.busynesErrorCode = busynesErrorCode;
        this.bussnessErrorDescription = bussnessErrorDescription;
        this.error = error;
        this.validationError = validationError;
        this.errors = errors;
    }

    public Integer getBusynesErrorCode() {
        return busynesErrorCode;
    }

    public void setBusynesErrorCode(Integer busynesErrorCode) {
        this.busynesErrorCode = busynesErrorCode;
    }

    public String getBussnessErrorDescription() {
        return bussnessErrorDescription;
    }

    public void setBussnessErrorDescription(String bussnessErrorDescription) {
        this.bussnessErrorDescription = bussnessErrorDescription;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Set<String> getValidationError() {
        return validationError;
    }

    public void setValidationError(Set<String> validationError) {
        this.validationError = validationError;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
