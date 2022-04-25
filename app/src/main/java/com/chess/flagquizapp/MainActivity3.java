package com.chess.flagquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chess.flagquizapp.databinding.ActivityMain2Binding;
import com.chess.flagquizapp.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {

    private ActivityMain3Binding binding;

    private int dogruSayisi;
    private int yanlisSayisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // 2. sayfadan gelen intent verileri aliniyor.
        Intent intent = getIntent();

        dogruSayisi = intent.getIntExtra("dogruSayisi",0);
        binding.dogruSayaci.setText("Doğru: " + dogruSayisi);
        yanlisSayisi = intent.getIntExtra("yanlisSayisi",0);
        binding.yanlisSayaci.setText("Yanlış: " + yanlisSayisi);
        binding.basariYuzdesi.setText("% " + ((dogruSayisi*100)/5) + " Başarı");



        // Tekrar dene butonuna basilinca olacaklar

        binding.buttonTekrarDene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity3.this,MainActivity2.class));
                // 2. sayfaya geri donuldugunde geri tusuna basilirsa tekrar geldigi yer olan 3. sayfaya degil de
                // 1. sayfaya geri donsun istedigimizden dolayi finish() metodu ile aktiviteyi Back Stack'den siliyoruz.
                finish();
            }
        });

    }
}