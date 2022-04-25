package com.chess.flagquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chess.flagquizapp.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Hazir veritabanimiz kopyalaniyor
        copyDatabase();

        // 2. sayfaya gecis yapiyoruz play butonuna tiklayinca

        binding.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });




    }

    public void copyDatabase(){

        DatabaseCopyHelper copy = new DatabaseCopyHelper(this);

        try {
            copy.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        copy.openDataBase();
    }
}