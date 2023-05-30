/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.client;

import java.io.Serializable;

/**
 *
 * 
 */
public class ResponseData implements Serializable {

    private Boolean success = true;
    private String error = null;
    private Object response;

    public ResponseData(Boolean success, String error, String response) {
        this.success = success;
        this.error = error;
        this.response = response;
    }

    public ResponseData() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
    
    

}
