package com.tonydpadua.payment.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMessage> erros = new ArrayList<>();

    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }

    public List<FieldMessage> getErrors(){
        return erros;
    }

    public void addError(String fieldMessage,String message){
        erros.add(new FieldMessage(fieldMessage,message));
    }
}
