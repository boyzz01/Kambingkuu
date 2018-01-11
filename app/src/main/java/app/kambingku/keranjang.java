package app.kambingku;

/**
 * Created by Win 10 on 1/7/2018.
 */

public class keranjang {
    private int id_ternak;
    String foto;
    String gender;
    int harga;
    boolean cart=false;

    public boolean isCart() {
        return cart;
    }

    public void setCart(boolean cart) {
        this.cart = cart;
    }

    public int getId_ternak() {
        return id_ternak;
    }

    public void setId_ternak(int id_ternak) {
        this.id_ternak = id_ternak;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
