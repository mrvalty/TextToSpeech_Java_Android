package com.example.deneme;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskManager extends AsyncTask<String, Void, String> {

    String apiKey = ""; // OpenWeatherMap API anahtarınızı buraya girin
    String city = "Ankara"; // Şehir ismi
    String urlString = "https://api.openweathermap.org/data/2.5/weather?lat=39.9199&lon=32.8543&appid="+apiKey;
    int result_sicaklik = 0;
    MainActivity manager = new MainActivity();

    @Override
    protected String doInBackground(String... strings) {


        try{
            // URL'yi oluştur
            URL url = new URL(urlString);
            // HTTP bağlantısı oluştur
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if(responseCode != 200){
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }
            else{
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                // JSON verisini işleme
                JSONObject obj = new JSONObject(content.toString());

                // Hava durumu bilgilerini al
                JSONObject main = obj.getJSONObject("main");
                double sicaklik = main.getDouble("temp");

                result_sicaklik = (int)(sicaklik-273);

                //System.out.println("Şehir: " + city);
                //System.out.println("Sıcaklık: " + result_sicaklik + "°C");

                manager.HavaDurumu(Integer.toString(result_sicaklik));
            }

        }catch (Exception e) {
            Log.e("Hata", "Bir hata oluştu", e);
        }
        return null;
    }

}
