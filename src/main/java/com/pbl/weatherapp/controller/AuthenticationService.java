//User Authentication (Login/Signup) using MySQL
package com.pbl.weatherapp.controller;

import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class AuthenticationService
{
    private String name;
    private String email;
    private String password;

    private String emailLogin;
    private String passwordLogin;

    Connection conn;
    Statement st;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmailLogin() {
        return emailLogin;
    }

    public void setEmailLogin(String emailLogin) {
        this.emailLogin = emailLogin;
    }

    public String getPasswordLogin() {
        return passwordLogin;
    }

    public void setPasswordLogin(String passwordLogin) {
        this.passwordLogin = passwordLogin;
    }

    public void connection()
    {
        try
        {
            //Database Connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weathr", "root", "071220");

            //For Sending SQL Statements to Database
            st = conn.createStatement();

            if(conn!=null)
            {
                System.out.println("Database Connection Established!");
            }
            else
            {
                System.out.println("Connection could not be Established!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Connection could not be Established!");
            e.printStackTrace();
        }
    }

    //Registering a New user to the Weathr Application
    public void register()
    {
        connection();
        String str = "INSERT into user values('"+getName()+"', '"+getEmail()+"', '"+getPassword()+"')";
        try
        {
            st.executeUpdate(str);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    //Login to Weathr Account
    public boolean login()
    {
        try
        {
            connection();
            int count=0;
            String str = "SELECT name from user where (email = '"+getEmailLogin()+"' AND password = '"+getPasswordLogin()+"')";
            ResultSet rset = st.executeQuery(str);
            while(rset.next())
            {
                count = rset.getRow();
            }
            if(count==1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
