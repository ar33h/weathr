//Weather Service Home Page
package com.pbl.weatherapp.views;

import com.pbl.weatherapp.controller.AuthenticationService;
import com.pbl.weatherapp.controller.WeatherService;

import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

@SpringUI(path="/MainView")

@Title("Weathr")

public class MainView extends UI {

    //Connects UI with Backend WeatherService Java File
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private AuthenticationService authService;


    private VerticalLayout mainLyout;
    private HorizontalLayout dashboard;
    private HorizontalLayout mainDescrip;
    private NativeSelect<String> unitSelect;
    private TextField cityTextField;
    private Button searchButton;
    private Button logOut;
    private Label location;
    private Label currentTemp;
    private Label weathrDescrip;
    private Label max;
    private Label min;
    private Label humidity;
    private Label pressure;
    private Label sea;
    private Label wind;
    private Image iconImage;
    private Image iconImage2;
    private Image img;
    private Button advWeathr;
    String city;

    Label empty;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Notification.show("Login Successful!");
        mainLayout();
        setHeader();
        dashboardTitle();
        dashboardDetails();

        //Checks if City Field is Empty
        searchButton.addClickListener(clickEvent -> {
            if(!cityTextField.getValue().equals(""))
            {
                try
                {
                    updateUI();
                    Page.getCurrent().setTitle(cityTextField.getValue()+" | Weathr");
                }
                catch (IOException | JSONException e)
                {
                    Notification.show("Invalid City Name!");
                    e.printStackTrace();
                }
            }
            else
            {
                Notification.show("Enter a City!");
            }
        });
    }

    //Main Layout of our Weathr Page
    private void mainLayout()
    {
        iconImage = new Image();
        mainLyout = new VerticalLayout();
        mainLyout.setWidth("100%");
        mainLyout.setSpacing(true);
        mainLyout.setMargin(true);
        mainLyout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //Sets Default View as mainLyout
        setContent(mainLyout);
    }


    private void setHeader()
    {
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        formLayout.setSpacing(true);
        formLayout.setMargin(true);

        img = new Image(null, new ClassResource("/static/logo.png"));
        img.setWidth("180px");
        img.setHeight("124px");
        formLayout.addComponents(img);

        empty = new Label();
        empty.setWidth("50px");

        //City Text Field
        cityTextField = new TextField();
        cityTextField.setWidth("500px");

        formLayout.addComponents(empty, cityTextField);

        //Selection Component (Temperature Units)
        unitSelect = new NativeSelect<>();
        ArrayList<String> items = new ArrayList<>();
        items.add("C");
        items.add("F");

        unitSelect.setItems(items);
        unitSelect.setValue(items.get(0));      //Default Unit is Celsius

        formLayout.addComponent(unitSelect);

        //Search Button
        searchButton = new Button();
        searchButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        searchButton.setIcon(VaadinIcons.SEARCH);
        formLayout.addComponent(searchButton);

        empty = new Label();
        empty.setWidth("50px");

        //Logout Button
        logOut = new Button("Logout");
        logOut.setStyleName(ValoTheme.BUTTON_DANGER);
        logOut.setIcon(VaadinIcons.SIGN_OUT);
        logOut.addClickListener(clickEvent -> {

            getUI().getPage().open("http://localhost:8080/LoginView", "_self");
        });
        formLayout.addComponents(empty, logOut);

        mainLyout.addComponents(formLayout);
    }

    //Weathr Dashboard
    private void dashboardTitle()
    {
        dashboard = new HorizontalLayout();
        dashboard.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //City Name
        location = new Label("Currently in ");
        location.addStyleName(ValoTheme.LABEL_H2);
        location.addStyleName(ValoTheme.LABEL_LIGHT);

        //Current Temperature
        currentTemp = new Label("");
        currentTemp.setStyleName(ValoTheme.LABEL_BOLD);
        currentTemp.setStyleName(ValoTheme.LABEL_H1);

        dashboard.addComponents(location, iconImage, currentTemp);

    }

    private void dashboardDetails()
    {
        mainDescrip = new HorizontalLayout();
        mainDescrip.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //Temperature Description
        VerticalLayout descrip = new VerticalLayout();
        descrip.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        weathrDescrip = new Label("Description: ");
        weathrDescrip.setStyleName(ValoTheme.LABEL_SUCCESS);
        descrip.addComponent(weathrDescrip);

        //Minimum Temperature
        min = new Label("Minimum Temperature: ");
        descrip.addComponent(min);

        //Maximum Temperature
        max = new Label("Maximum Temperature: ");
        descrip.addComponent(max);

        //Other Forecast Descripion
        VerticalLayout pressureLayout = new VerticalLayout();
        pressureLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //Humidity
        humidity = new Label("Humidity: ");
        pressureLayout.addComponent(humidity);

        //Sea Level
        sea = new Label("Sea Level: ");
        pressureLayout.addComponent(sea);

        //Pressure
        pressure = new Label("Pressure: ");
        pressureLayout.addComponent(pressure);

        //Wind Speed
        wind = new Label("Wind Speed: ");
        pressureLayout.addComponent(wind);

        mainDescrip.addComponents(descrip, pressureLayout);
    }

    public void advancedWeathr() throws IOException, JSONException {
        HorizontalLayout iconLayout = new HorizontalLayout();
        HorizontalLayout descripLayout = new HorizontalLayout();
        for(int i=0; i<5; i++)
        {
            JSONObject weather = weatherService.getList().getJSONObject(i);

            String iconCodeAdv = null;
            String weatherDescriptionAdv = null;

            JSONArray weathrArray = weather.getJSONArray("weather");
            for(int j=0; j<weathrArray.length(); j++)
            {
                JSONObject weatherObject = weathrArray.getJSONObject(j);
                iconCodeAdv = weatherObject.getString("icon");
                weatherDescriptionAdv = weatherObject.getString("description");
            }
            iconImage2 = new Image();
            iconImage2.setSource(new ExternalResource("http://openweathermap.org/img/wn/"+iconCodeAdv+"@2x.png"));

            Label description = new Label();
            description.setStyleName(ValoTheme.LABEL_BOLD);
            description.setValue(weatherDescriptionAdv);

            iconLayout.addComponents(iconImage2, empty, empty);
            empty = new Label();
            descripLayout.addComponents(description, empty,empty);
        }
        mainLyout.addComponents(iconLayout, empty, descripLayout);
    }


    private void updateUI() throws JSONException, IOException {
        city = cityTextField.getValue();
        String unit;

        weatherService.setCity(city);

        if(unitSelect.getValue().equals("F"))
        {
            weatherService.setUnit("Imperials");
            unitSelect.setValue("F");
            unit = " \u00B0"+"F";                        //Degree symbol
        }
        else
        {
            weatherService.setUnit("Metric");
            unitSelect.setValue("C");
            unit = " \u00B0"+"C";
        }

        //Country
        JSONObject countryObject = weatherService.country();
        String country = countryObject.getString("country");
        location.setValue("Currently in "+city+", "+country);


        JSONObject tempObject = weatherService.cityTemperature();
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        float temp = (float) tempObject.getDouble("temp");
        df.format(temp);
        currentTemp.setValue(temp +unit);

        //Getting Weathr Icon & Description from API
        String iconCode = null;
        String weatherDescription = null;

        JSONArray weathrArray = weatherService.weathrDescrip();
        for(int i=0; i<weathrArray.length(); i++)
        {
            JSONObject weatherObject = weathrArray.getJSONObject(i);
            iconCode = weatherObject.getString("icon");
            weatherDescription = weatherObject.getString("main");
        }

        iconImage.setSource(new ExternalResource("http://openweathermap.org/img/wn/"+iconCode+"@2x.png"));

        weathrDescrip.setValue("Description: "+weatherDescription);

        min.setValue("Minimum Temperature: "+weatherService.cityTemperature().getDouble("temp_min")+" \u00B0"+unitSelect.getValue());
        max.setValue("Maximum Temperature: "+weatherService.cityTemperature().getDouble("temp_max")+" \u00B0"+unitSelect.getValue());

        pressure.setValue("Pressure: "+weatherService.cityTemperature().getInt("pressure")+" hPa");


        try {
            sea.setValue("Sea Level: "+weatherService.cityTemperature().getInt("sea_level"));
        }
        catch (JSONException e)
        {
            //Sea Level for some cities is not available
            sea.setValue("Sea Level: --");
        }


        humidity.setValue("Humidity: "+weatherService.cityTemperature().getInt("humidity")+"%");

        wind.setValue("Wind Speed: "+weatherService.windSpeed().getInt("speed")+" m/sec");

        empty = new Label();

        advWeathr = new Button("Advanced Forecast");
        advWeathr.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        advWeathr.setWidth("220px");
        advWeathr.setDisableOnClick(true);
        advWeathr.addClickListener(clickEvent -> {
            try {
                advancedWeathr();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });

        mainLyout.addComponents(dashboard, mainDescrip, empty, advWeathr,empty);

    }


}
