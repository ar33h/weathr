//Register for a Weathr Account
package com.pbl.weatherapp.views;

import com.pbl.weatherapp.controller.AuthenticationService;

import com.vaadin.annotations.Title;
import com.vaadin.server.ClassResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path="/RegisterView")

@Title("Register | Weathr")
public class RegisterView extends UI
{
    @Autowired
    private AuthenticationService authService;

    private VerticalLayout authLayout;
    public TextField nameField;
    public TextField emailField;
    public PasswordField passwordField;
    private Button registerButton;
    private Button loginButton;

    Label empty;

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {
        mainLayout();
        setLogo();
        registerForm();
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


    private void registerForm()
    {
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        formLayout.setSpacing(true);
        formLayout.setMargin(true);

        //Name
        nameField = new TextField("Name");
        nameField.setWidth("35%");
        nameField.setRequiredIndicatorVisible(true);
        formLayout.addComponent(nameField);

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

        //Register Button
        registerButton = new Button("Register");
        registerButton.setWidth("120px");
        registerButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        registerButton.addClickListener(clickEvent -> {

            try
            {
                if(nameField.getValue().equals(""))
                {
                    Notification.show("Enter your Name!");
                }
                else if(emailField.getValue().equals(""))
                {
                    Notification.show("Enter your Email!");
                }
                else if(passwordField.getValue().equals(""))
                {
                    Notification.show("Create a Password!");
                }
                else {

                    authService.connection();
                    authService.setName(nameField.getValue());
                    authService.setEmail(emailField.getValue());
                    authService.setPassword(passwordField.getValue());
                    authService.register();

                    getUI().getPage().open("http://localhost:8080/MainView", "_self");
                }
            }
            catch(Exception e)
            {
                Notification.show("Server Error!");
                e.printStackTrace();
            }
        });
        empty = new Label();
        formLayout.addComponents(empty, registerButton);

        authLayout.addComponent(formLayout);
    }

    private void setFooter()
    {
        VerticalLayout header = new VerticalLayout();
        header.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label title = new Label("Already have an Account?");

        title.setStyleName(ValoTheme.LABEL_BOLD);

        loginButton = new Button("Login");
        loginButton.setDisableOnClick(true);
        loginButton.addClickListener(clickEvent -> {
            getUI().getPage().open("http://localhost:8080/LoginView", "_self");

        });
        header.addComponents(title, loginButton);
        authLayout.addComponent(header);
    }

}
