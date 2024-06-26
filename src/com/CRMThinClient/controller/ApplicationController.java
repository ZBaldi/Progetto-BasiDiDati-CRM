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
            case OPERATORE -> new OperatoreController().start();
            case SEGRETERIA -> new SegreteriaController().start();
            default -> throw new RuntimeException("Invalid credentials");
        }
    }
}
