package com.chess.flagquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.chess.flagquizapp.databinding.ActivityMain2Binding;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    // Rastgele gelecek 5 soru ve 4 siktan 3'unu yanlis oldugu bilgisini tutacak ArrayList'ler.
    private ArrayList<Bayraklar> rastgele5Soru;
    private ArrayList<Bayraklar> rastgele3Yanlis;

    // Sayaclar ile degeri degisecek olan dogruSoru nesnesi
    private Bayraklar dogruSoru;

    // Veritabani nesnesi
    private Veritabani vt;

    // Soru, dogru ve yanlis sayilarini tutacak sayaclar
    private int soruSayisi = 0;
    private int dogruSayisi = 0;
    private int yanlisSayisi = 0;

    //Soru secenkleri ile ilgili islemler

    //Bayraklar nesnelerini random sekilde karistirmaya yarayacak
    // NOT: HashSet listesi verileri "karma sekilde" siraladigindan
    // verileri random olarak siralamak icin ayrica bir algoritmaya ihtiyac duymayacagiz.
    private HashSet<Bayraklar> secenekleriKaristirmaListe = new HashSet<>();
    // Karistirilan secenekleri de tekrar bir siraya sokmaliyiz ki sorulara gore kullanabilelim
    private ArrayList<Bayraklar> seceneklerListe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Veritabani modelimiz tanimlaniyor
        vt = new Veritabani(this);

        // Soru listemizi aliyoruz
        rastgele5Soru = new Bayraklardao().rastgele5Soru(vt);

        //
        soruYukle();

        // Tiklanacak olan siklar etkinlestiriliyor

        binding.option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(binding.option1Text);
                sayacKontrol();
            }
        });

        binding.option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(binding.option2Text);
                sayacKontrol();
            }
        });

        binding.option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(binding.option3Text);
                sayacKontrol();
            }
        });

        binding.option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(binding.option4Text);
                sayacKontrol();
            }
        });



    }

    public void soruYukle(){

        // Soru yuklendikce soru sayaci 1 artsin.
        // NOT: Ilk durumda 1'den baslamasi gerektigi ve sonrasinda da birer birer artacagi icin
        // sayacin ilk degeri 0 yapilir ki dogru siralamada devam etsin.
        binding.questionText.setText(soruSayisi+1 + ". SORU");
        // dogru ve yanlis cevaplar icin sayaclar
        binding.wrongText.setText("Yanlış: " + yanlisSayisi);
        binding.trueText.setText("Doğru: " + dogruSayisi);

        //

        //
        dogruSoru = rastgele5Soru.get(soruSayisi);

        //
        rastgele3Yanlis = new Bayraklardao().rastgele3YanlisGetir(vt,dogruSoru.getBayrak_id());

        // Tasarimimizdaki bayrak resmini degistiriyoruz gelen veriye gore
        // getResources().getIdentifier(resim,resmin bulundugu dosya,bulundugu paket)
        // bu sekilde drawable dosyasindaki verilere dinamik sekilde erisebiliriz.
        // ÖR: veritabaninda bayrak ismi turkiye ise, drawable dosyasinda da turkiye isminde olan resim alinir ve resim onunla degistirilir.
        binding.flagImage.setImageResource(getResources().getIdentifier(dogruSoru.getBayrak_resim(),"drawable",getPackageName()));

        // Oncelikle her defasinda secenkleri silelim, tekrardan karistirilmasini saglayalim.
        secenekleriKaristirmaListe.clear();
        // Dogru soru eklendi
        secenekleriKaristirmaListe.add(dogruSoru);
        // Yanlis cevaplar eklendi
        // Sirayla 0,1 ve 2. indisler eklenmis gibi duruyor ama
        // HashSet tipinde oldugu icin karmasik sekilde goruntulenir siklar.
        secenekleriKaristirmaListe.add(rastgele3Yanlis.get(0));
        secenekleriKaristirmaListe.add(rastgele3Yanlis.get(1));
        secenekleriKaristirmaListe.add(rastgele3Yanlis.get(2));

        // Sonrasinda da karisik olarak gelen siklari duzenli bir listeye koyuyoruz.
        // Bu sayede duzenli sekilde goruntuleyebilecegiz
        // Bu listemizi de temizliyoruz ki her soru geldiginde sadece o soruya ait veriler gelsin
        seceneklerListe.clear();

        for(Bayraklar b:secenekleriKaristirmaListe){
            // Karistirilan secenekler gecici ArrayList'imize geldigi sirasiyla eklenir.
            // NOT: Gecici listeye atmamizin sebebi, rastgele gelen verilerin indislerine ulasmamiz gerektigindendir
            // HashSet yapisinda ise indis olmadigindan bu verilere teker teker ulasamazdik.
            seceneklerListe.add(b);
        }
        // Sirasiyla artik butonlar, gelen ulke isimleri ile doldurulur.
        binding.option1Text.setText(seceneklerListe.get(0).getBayrak_isim());
        binding.option2Text.setText(seceneklerListe.get(1).getBayrak_isim());
        binding.option3Text.setText(seceneklerListe.get(2).getBayrak_isim());
        binding.option4Text.setText(seceneklerListe.get(3).getBayrak_isim());
    }

    // Dogruluk kontrolunu yapacak metot
    public void dogruKontrol(TextView text){

        String buttonYazi = text.getText().toString();
        String dogruCevap = dogruSoru.getBayrak_isim();

        // Eger buton uzerinde yazan yazi dogru cevaba esitse
        if(buttonYazi.equals(dogruCevap)){
            // Dogru sayaci 1 artsin
            dogruSayisi++;
        }
        // Eger yanlis cevap verildiyse
        else{
            // Yanlis sayaci 1 artsin
            yanlisSayisi++;
        }

        // dogru ve yanlis cevaplar icin sayaclar
        binding.wrongText.setText("Yanlış: " + yanlisSayisi);
        binding.trueText.setText("Doğru: " + dogruSayisi);
    }

    // Sayac kontrolu yapan metot(5 tane soru doldugunda 3. ekrana gecilecek)
    public void sayacKontrol(){
        // Soru sayaci 1 artar
        soruSayisi++;

        // Eger 5. soruya gelinmediyse
        // yeni soru yuklensin, yuklenmeye devam etsin
        if(soruSayisi != 5){
            soruYukle();
        }

        // Eger 5. soruya ulasildiysa
        // Basarim sonucumuzu gormek uzere son sayfaya erisilsin.
        else{
            Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
            // Dogru ve yanlis sayilari goruntulenmek uzere son ekrana gonderiliyor
            intent.putExtra("dogruSayisi",dogruSayisi);
            intent.putExtra("yanlisSayisi",yanlisSayisi);

            startActivity(intent);
            // Back Stack'den bu sayfa siliniyor ki sonuc sayfasindan geri gidilince ana sayfaya donulebilsin.
            finish();
        }
    }

}