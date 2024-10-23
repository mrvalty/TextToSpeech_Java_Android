package com.example.deneme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int SPEECH_REQUEST_CODE = 10;
    private TextToSpeech textToSpeech;

    private Button btnStartListening;
    private EditText textMetin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartListening = findViewById(R.id.button);
        textMetin = findViewById(R.id.txtMetin);


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if( status == textToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.UK);
                    textToSpeech.setLanguage(new Locale("tr","TR"));
                }
            }
        });



        btnStartListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String metin = textMetin.getText().toString().toUpperCase(Locale.ROOT);
                tellStory(metin);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = result.get(0).toLowerCase();  // İlk sonucu alıyoruz ve küçük harfe çeviriyoruz

            if (spokenText.contains("masal anlat")) {
                //tellStory();  // Eğer komut masal anlatmaksa masalı anlat
            } else {
                Toast.makeText(this, "Masal anlatmam için komut verin!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  <string> void tellStory(String text) {
        String story = null;

        if(text.equals("NABER") || text.equals("NASILSIN")){
            story = "İyiyim teşekkür ederim sen nasılsın";
            //Speak(story);
        }
        else if(text.equals("MASAL")){
            story = " Bir varmış bir yokmuş evvel zaman içinde kalbur saman içinde, pireler berber iken develer tellal iken";
            //Speak(story);
        }
        else if(text.contains("GÜN") || text.contains("GEÇTI")){
            story ="Yoğun bir şekilde çalıştım";
            //Speak(story);
        }
        else if(text.contains("HAVA")){

            AsyncTaskManager manager = new AsyncTaskManager();
            manager.execute();
        }
        else{
            story ="Lütfen komut giriniz.";
            //Speak(story);
        }
        textToSpeech.setSpeechRate(1.0f);
        textToSpeech.setPitch(0.8f);
        textToSpeech.speak(story, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public void Speak(String request){

    }

    public void HavaDurumu(String request){

         String result = (String) ("Bugün ankarada hava "+request+" derece");
         System.out.println(result);
         //Speak(result);
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}