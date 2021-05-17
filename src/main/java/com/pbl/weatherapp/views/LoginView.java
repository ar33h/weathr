//Login to Weathr Account
package com.pbl.weatherapp.views;

import com.pbl.weatherapp.controller.AuthenticationService;

import com.vaadin.annotations.Title;
import com.vaadin.server.ClassResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path="/LoginView")

@Title("Login | Weathr")
public class LoginView extends UI
{
    @Autowired
    private AuthenticationService authService;

    private VerticalLayout authLayout;
    private TextField emailField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button createAccButton;

    Label empty;

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {
        mainLayout();
        setLogo();
        loginForm();
        setFooter();
    }

    private void mainLayout()
    {
        authLayout = new VerticalLayout();
        authLayout.setWidth("100%");
        authLayout.setSpacing(true);
        authLayout.setMargin(true);
        authLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //Sets Default View as authLayout
        setContent(authLayout);
    }

    //Weathr Logo
    private void setLogo()
    {
        HorizontalLayout logo = new HorizontalLayout();
        logo.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Image img = new Image(null, new ClassResource("/static/logo.png"));
        img.setWidth("240px");
        img.setHeight("173.2px");

        logo.addComponent(img);
        authLayout.addComponent(logo);
    }

    private void loginForm()
    {
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        formLayout.setSpacing(true);
        formLayout.setMargin(true);

        //Email
        emailField = new TextField("Email");
        emailField.setWidth("35%");
        emailField.setRequiredIndicatorVisible(true);
        formLayout.addComponent(emailField);

        //Password
        passwordField = new PasswordField("Password");
        passwordField.setWidth("35%");
        passwordField.setRequiredIndicatorVisible(true);
        formLayout.addComponent(passwordField);

        empty = new Label();

        //Login Button
        loginButton = new Button("Login");
        loginButton.setWidth("120px");
        loginButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        loginButton.addClickListener(clickEvent -> {
        try {
            if (emailField.getValue().equals(""))
            {
                Notification.show("Enter your Email!");
            }
            else if (passwordField.getValue().equals(""))
            {
                Notification.show("Enter your Password!");
            }
            else
            {
                authService.setEmailLogin(emailField.getValue());
                authService.setPasswordLogin(passwordField.getValue());
                if(authService.login())
                {
                    getUI().getPage().open("http://localhost:8080/MainView", "_self");
                }
                else
                {
                    Notification.show("Invalid Credentials!");
                }
            }

        } catch (Exception e){
            Notification.show("Server Error!");
        }
        });
        formLayout.addComponents(empty, loginButton);

        authLayout.addComponent(formLayout);
    }

    private void setFooter()
    {
        VerticalLayout header = new VerticalLayout();
        header.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label title = new Label("New to Weathr? Register Now!");
        title.setStyleName(ValoTheme.LABEL_BOLD);

        createAccButton = new Button("Create an Account");
        createAccButton.addClickListener(clickEvent -> {
            getUI().getPage().open("http://localhost:8080/RegisterView", "_self");

        });
        header.addComponents(title, createAccButton);

        authLayout.addComponent(header);
    }

}
