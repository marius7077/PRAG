package com.descartes.qlf.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class
AuthentificationService {

    @Autowired
    private DataSource dataSource;

    public AuthentificationService(){
    }
    public String AuthValidation(String username, String password)
    {
        Boolean sql = null;
        System.out.println("Before getConnection");
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("After getConnection");
            Statement stmt1 = connection.createStatement();
            sql = stmt1.execute("SELECT email FROM consommateur WHERE email = 'simon.weber98@gmail.com'");
        } catch (Exception e) {
            System.out.println("Catch : " + e.getMessage());
            return "crud";
        }
        if(sql==true){return "sql true";}
        else{return "sql false";}
        /*if (username.equals("bon")&&password.equals("1234"))
        {
            return "okay";
        }
        else{
            return "pas okay";
        }*/
    }
}
