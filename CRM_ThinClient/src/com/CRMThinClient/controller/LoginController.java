package com.CRMThinClient.controller;

import java.io.IOException;
import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.DAO.LoginProcedureDAO;
import com.CRMThinClient.model.Domain.Credentials;
import com.CRMThinClient.view.LoginView;

public class LoginController implements Controller {
    Credentials cred = null;

    @Override
    public void start() {
        try {
            cred = LoginView.authenticate();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        try {
            cred = new LoginProcedureDAO().execute(cred.getUsername(), cred.getPassword());
        } catch(DAOException e) {
            throw new RuntimeException(e);
        }
    }

    public Credentials getCred() {
        return cred;
    }
}

