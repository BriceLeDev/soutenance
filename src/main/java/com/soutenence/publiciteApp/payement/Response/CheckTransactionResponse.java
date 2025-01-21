package com.soutenence.publiciteApp.payement.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckTransactionResponse {
    private String code;
    private String message;
    private DataTransResp data;
    private String api_response_id;

    // Getters and setters
  /*  public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getApi_response_id() {
        return api_response_id;
    }

    public void setApi_response_id(String api_response_id) {
        this.api_response_id = api_response_id;
    }*/

}
