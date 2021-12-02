package com.darkbeast0106.apidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String method;
        String content;

        public PerformNetworkRequest(String method){
            this.method = method;
            this.content = "";
        }

        public PerformNetworkRequest(String method, String content){
            this.method = method;
            this.content = content;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                if (method.equals("GET")){
                    data = Request.getData();
                } else {
                    data = Request.postData(content);
                }
            } catch (IOException e) {
                data = e.getMessage();
            }

            frissitMuvelet();
            return data;
        }
    }

    String data = "";

    TextView textView;
    EditText gyarto, modell, uzembe;
    Button felvesz, frissit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        textView.setMovementMethod(new ScrollingMovementMethod());
        listaz();
        frissit.setOnClickListener(v -> listaz());
        felvesz.setOnClickListener(v -> {
            String gyarto = this.gyarto.getText().toString().trim();
            String modell = this.modell.getText().toString().trim();
            String uzembe = this.uzembe.getText().toString().trim();
            String json =
                    "{ \"gyarto\" : \""+gyarto+"\", \"modell\" : \""+modell+
                            "\", \"uzembehelyezes\" : \""+uzembe+"\" }";
            PerformNetworkRequest request = new PerformNetworkRequest("POST", json);
            request.execute();
        });

    }

    private void listaz(){
        PerformNetworkRequest request = new PerformNetworkRequest("GET");
        request.execute();
    }

    private void init(){
        textView = findViewById(R.id.textEredemeny);
        gyarto = findViewById(R.id.gyarto);
        modell = findViewById(R.id.modell);
        uzembe = findViewById(R.id.uzembe);
        felvesz = findViewById(R.id.felvesz);
        frissit = findViewById(R.id.frissit);

    }

    public void frissitMuvelet(){
        this.runOnUiThread(frissites);
    }

    private Runnable frissites = new Runnable() {
        @Override
        public void run() {
            textView.setText(data);
        }
    };
}