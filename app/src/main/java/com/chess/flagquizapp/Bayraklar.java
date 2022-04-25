package com.chess.flagquizapp;

// Ulke bayraklarinin bilgilerini(tablomuzu) iceren class

public class Bayraklar {

    // Properties
    private int bayrak_id;
    private String bayrak_isim;
    private String bayrak_resim;

    // Constructors
    public Bayraklar() {
    }

    public Bayraklar(int bayrak_id, String bayrak_isim, String bayrak_resim) {
        this.bayrak_id = bayrak_id;
        this.bayrak_isim = bayrak_isim;
        this.bayrak_resim = bayrak_resim;
    }

    // Getter & Setter
    public int getBayrak_id() {
        return bayrak_id;
    }

    public void setBayrak_id(int bayrak_id) {
        this.bayrak_id = bayrak_id;
    }

    public String getBayrak_isim() {
        return bayrak_isim;
    }

    public void setBayrak_isim(String bayrak_isim) {
        this.bayrak_isim = bayrak_isim;
    }

    public String getBayrak_resim() {
        return bayrak_resim;
    }

    public void setBayrak_resim(String bayrak_resim) {
        this.bayrak_resim = bayrak_resim;
    }
}
