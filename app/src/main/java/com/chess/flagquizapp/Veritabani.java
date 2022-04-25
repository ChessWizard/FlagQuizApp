package com.chess.flagquizapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Veritabani modelimiz olsuturuluyor.

public class Veritabani extends SQLiteOpenHelper {

    public Veritabani(@Nullable Context context) {
        super(context, "bayrakquiz.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // flags tablomuz yaratildi
        //  IF NOT EXISTS -> copydatabase eklerken kullanilir, eger database veya bir sey olursa bu tablo tekrardan kod ile olusturulsun.
        db.execSQL("CREATE TABLE IF NOT EXISTS\"bayraklar\" (\n" +
                "\t`bayrak_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`bayrak_ad`\tTEXT,\n" +
                "\t`bayrak_resim`\tTEXT\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Tablo guncellenirse, onceki hali silinsin
        // IF EXISTS -> Ortada ancak bir tablo varsa silinebilir. Bunun kontrolunu yapar
        db.execSQL("DROP TABLE IF EXISTS bayraklar");
        // Tablo silindikten sonra yeni doldurulacak hali icin silinmis hali yaratma kismina gonderilir.
        onCreate(db);
    }
}
