package it.tosiani.models;

public class BestSeller {
    private String titolo;
    private String img;
    private String prezzo;

    public BestSeller(String titolo, String img) {
        this.titolo = titolo;
        this.img = img;
    }

    public BestSeller(String titolo, String img, String prezzo) {
        this.titolo = titolo;
        this.img = img;
        this.prezzo = prezzo;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getImg() {
        return img;
    }

    public String getPrezzo() {
        return prezzo;
    }
}
