package com.aplikacja.covidprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Rankingsql extends AppCompatActivity {

    EditText usr, pas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankingsql);

        usr = (EditText) findViewById(R.id.username);
        pas = (EditText) findViewById(R.id.password);
    }

    public void LoginBtn(View view) {
    String user = usr.getText().toString();
    String pass = pas.getText().toString();

    Background1 bg = new Background1(this);
    bg.execute(user,pass);
    }
}