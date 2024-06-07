package com.CRMThinClient.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.CRMThinClient.model.Domain.Credentials;

public class LoginView {
    public static Credentials authenticate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("username: ");
        String username = reader.readLine();
        System.out.print("password: ");
        String password = reader.readLine();

        return new Credentials(username, password, null);
    }
}
