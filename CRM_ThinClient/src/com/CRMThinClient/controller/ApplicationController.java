package com.CRMThinClient.controller;

import com.CRMThinClient.model.Domain.Credentials;

public class ApplicationController implements Controller {
    Credentials cred;

    @Override
    public void start() {
        LoginController loginController = new LoginController();
        loginController.start();
        cred = loginController.getCred();

        if(cred.getRole() == null) {
            throw new RuntimeException("Invalid credentials");
        }

        switch(cred.getRole()) {
            case OPERATORE : OperatoreController.setId(cred.getUsername());
            				 new OperatoreController().start();
            				 break;
            case SEGRETERIA : new SegreteriaController().start();
            				  break;
            default : throw new RuntimeException("Invalid credentials");
        }
    }
}
