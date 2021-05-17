//Fetching Weather Data from OpenWeatherMap API
package com.pbl.weatherapp.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherService {

    //Sending & receiving HTTP Network requests
    private OkHttpClient client;
    private Response response;
    private String city;
    String unit;

    private String APIKey = "6f90906eabe8b24eac503e0ada6e54ee";


    //Function to import Weather Data from API Generated JSON
    public JSONObject getWeather() throws IOException {
        client = new OkHttpClient();

        //Creates a request to the API Link
        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/weather?q="+getCity()+"&units="+getUnit()+"&appid="+APIKey)
                .build();

        try
        {
            //Handling Responses
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        }
         catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    //Function to import Advanced Forecast Data from API Generated JSON
    public JSONObject getAdvWeather() throws IOException {
        client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/forecast?q="+getCity()+"&units="+getUnit()+"&appid="+APIKey)
                .build();

        try
        {
            //Handling Responses
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    //API Units: Imperial = F, Metric = C
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    //Weather Description: Cloudy, Sunny, Haze...
    public JSONArray weathrDescrip() throws IOException, JSONException
    {
        JSONArray descrip = getWeather().getJSONArray("weather");
        return descrip;
    }

    //Temperature (Min, Max)
    public JSONObject cityTemperature() throws IOException, JSONException
    {
        JSONObject temp = getWeather().getJSONObject("main");
        return temp;
    }

    //Wind Speed (mps)
    public JSONObject windSpeed() throws IOException, JSONException
    {
        JSONObject wind = getWeather().getJSONObject("wind");
        return wind;
    }

    //Country
    public JSONObject country() throws IOException, JSONException
    {
        JSONObject coun = getWeather().getJSONObject("sys");
        return coun;
    }



    //------------------------------Advanced Forecast Functions
    public JSONArray getList() throws IOException, JSONException
    {
        JSONArray list = getAdvWeather().getJSONArray("list");
        return list;
    }

}

