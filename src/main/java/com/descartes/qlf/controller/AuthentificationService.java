package com.descartes.qlf.controller;

public class AuthentificationService {

    public AuthentificationService(){
    }
    public static String AuthValidation(String username, String password)
    {
        if (username.equals("bon")&&password.equals("1234"))
        {
            return "okay";
        }
        else{
            return "pas okay";
        }
    }
}
