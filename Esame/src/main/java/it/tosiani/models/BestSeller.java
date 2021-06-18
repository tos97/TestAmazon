package it.tosiani.models;

public class BestSeller {
    private String titolo;
    private String img;

    public BestSeller(String titolo, String img) {
        this.titolo = titolo;
        this.img = img;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getImg() {
        return img;
    }
}
