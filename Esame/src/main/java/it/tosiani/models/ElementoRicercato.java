package it.tosiani.models;

public class ElementoRicercato {
    private String titolo = "";
    private String prezzo = "";
    private String img = "";
    private int posizione = 0;

    public ElementoRicercato(String titolo, String prezzo, String img, int posizione) {
        this.titolo = titolo;
        this.prezzo = prezzo;
        this.img = img;
        this.posizione = posizione;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public String getImg() {
        return img;
    }

    public int getPosizione() {
        return posizione;
    }
}
