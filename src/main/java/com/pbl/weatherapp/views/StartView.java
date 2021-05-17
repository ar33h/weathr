//First Screen
package com.pbl.weatherapp.views;

import com.vaadin.annotations.Title;
import com.vaadin.server.ClassResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

@SpringUI(path="/StartView")

@Title("Weathr")

public class StartView extends UI
{
    private VerticalLayout startLayout;
    private Button login;
    private Button register;
    Label empty;

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {
        mainLayout();
        setLogo();
        setButtons();
        setFooter();
    }

    private void mainLayout()
    {
        startLayout = new VerticalLayout();
        startLayout.setWidth("100%");
        startLayout.setSpacing(true);
        startLayout.setMargin(true);
        startLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        setContent(startLayout);
    }

    private void setLogo()
    {
        VerticalLayout logo = new VerticalLayout();
        logo.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Image img = new Image(null, new ClassResource("/static/startImage.png"));
        img.setWidth("448px");
        img.setHeight("294px");
        empty = new Label();
        empty.setHeight("10px");

        logo.addComponents(img, empty);
        startLayout.addComponent(logo);
    }

    private void setButtons()
    {
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        login = new Button("Login");
        login.setHeight("55px");
        login.setWidth("200px");

        login.addClickListener(clickEvent -> {
            getUI().getPage().open("http://localhost:8080/LoginView", "_self");
        });

        register = new Button("Register");
        register.setHeight("55px");
        register.setWidth("200px");
        register.addClickListener(clickEvent -> {
            getUI().getPage().open("http://localhost:8080/RegisterView", "_self");
        });

        buttons.addComponents(login, register);
        startLayout.addComponent(buttons);
    }

    private void setFooter()
    {
        VerticalLayout footerText = new VerticalLayout();
        footerText.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Image footer = new Image(null, new ClassResource("/static/developed.png"));
        footer.setWidth("170px");
        footer.setHeight("34px");

        empty = new Label();
        empty.setHeight("80px");

        footerText.addComponents(empty,footer);
        startLayout.addComponents(footerText);
    }

}
