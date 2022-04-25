package com.chess.flagquizapp;


// Kopyalanan veritabanindan "verileri cekmeye" yarayacak olan class

// 5 sorudan olusan bir uygulama bu
// Rastgele 5 soru getir ve 4 sikkin da 3'u yanlis olsun
// seklinde 2 tane "sorgu" yaratacagiz

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Bayraklardao {

    // dao (Data Acces Object Class)'i yalnizca verileri cekmeye yarayan metotlardan olusur.

    // Metotlar her zaman kullanacagi veritabanini parametre olarak alir.

    // Rastgele 5 tane soruyu getirmeye yarayan algoritma
    public ArrayList<Bayraklar> rastgele5Soru(Veritabani vt){

        ArrayList<Bayraklar> bayraklarList = new ArrayList<>();

        // Veritabani class'i uzerinde kullanacagimiz veritabanini olusturmustuk
        // Bu veritabanini "hem yazilabilir hem okunabilir" sekilde SqLiteDatabase nesnesine atiyoruz.
        SQLiteDatabase db = vt.getWritableDatabase();

        // cursor(imlec) nesnesi ile belirtilen veritabanindaki ifadeyi "SELECT sorgusu" ile aliyoruz.
        // bayraklar tablosundaki tum verilerden random siralanmis sekilde 5 tanesini getir.
        // 5 tane bayrak id'si, ismi ve resmi random sekilde getirilmis olur bu sayede
        Cursor cursor = db.rawQuery("SELECT * FROM bayraklar ORDER BY RANDOM() LIMIT 5",null);

        // Sonrasinda SELECT sorgusu ile cekilen bu veriler gezilerek icerigine ulasiliyor
        // 5 tane Random satir alindigi icin, 5 kere bu dongu doner.
        while(cursor.moveToNext()){

            // Veritabanindaki satirlara ulasildikca
            // Bayraklar class'imizdaki veri modelimize bu verileri kaydedecegiz
            Bayraklar b = new Bayraklar(cursor.getInt(cursor.getColumnIndexOrThrow("bayrak_id")),
                                   cursor.getString(cursor.getColumnIndexOrThrow("bayrak_ad")),
                                   cursor.getString(cursor.getColumnIndexOrThrow("bayrak_resim")));
            // Veriler cursor nesnesi sayesinde Bayraklar class'i da kullanilarak alinmistir.
            // Artik bu veriler ArrayList'imiz icerisine atanara depolanir.
            bayraklarList.add(b);
        }
        // Verilerimiz return edildi.
        return bayraklarList;
    }

    // Rastgele 3 tane yanlis secenek getirmeye yarayan algoritma
    // Rastgele 3 tane yanlis birakmak icin 1 tane dogruyu bulmak yeterlidir
    // Bu dogruyu da bayrak_id'yi parametre alarak bulabiliriz.
    public ArrayList<Bayraklar> rastgele3YanlisGetir(Veritabani vt,int bayrak_id){

        ArrayList<Bayraklar> bayraklarList = new ArrayList<>();
        SQLiteDatabase db = vt.getWritableDatabase();
        // bayraklar tablosu icerisindeki tum verilerden bayrak_id sutunu != parametre olarak alinan bayrak_id olacak sekilde 3 tane satir alinir
        // Bu sorgu sayesinde 3 tane uyusmayan bayrak bilgisi alinir ve bunlar da yanlis cevaplara tekamul eder.
        Cursor c = db.rawQuery("SELECT * FROM bayraklar WHERE bayrak_id != "+bayrak_id+" ORDER BY RANDOM() LIMIT 3",null);

        // Son olarak sorguya uygun sekilde gelen veriler Cursor nesnesi ile gezilir
        // ve gerrekli veriler alinmis olur.
        while(c.moveToNext()){
            Bayraklar bayraklar = new Bayraklar(c.getInt(c.getColumnIndexOrThrow("bayrak_id")),
                                c.getString(c.getColumnIndexOrThrow("bayrak_ad")),
                                c.getString(c.getColumnIndexOrThrow("bayrak_resim")));
            bayraklarList.add(bayraklar);
        }


        return bayraklarList;
    }

}
