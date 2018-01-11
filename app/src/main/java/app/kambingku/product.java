package app.kambingku;

import java.util.Date;

/**
 * Created by Win 10 on 1/3/2018.
 */

public class product {

    private int id_ternak;
    String gender;
    int berat;
    int harga;
    int rotasi;
    int status;
    String foto;
    String dob;
    boolean cart=false;
    public boolean isCart() {
        return cart;
    }

    public void setCart(boolean cart) {
        this.cart = cart;
    }



    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    int jlh_anak;



    public int getId_ternak() {
        return id_ternak;
    }

    public void setId_ternak(int id_ternak) {
        this.id_ternak = id_ternak;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getBerat() {
        return berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getRotasi() {
        return rotasi;
    }

    public void setRotasi(int rotasi) {
        this.rotasi = rotasi;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getJlh_anak() {
        return jlh_anak;
    }

    public void setJlh_anak(int jlh_anak) {
        this.jlh_anak = jlh_anak;
    }
}
