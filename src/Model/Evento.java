package Model;

import java.util.ArrayList;
import java.util.List;

public class Evento {
    private String tema;
    private List<Mesa> mesas;
    private List<Garcom> garcons;
    private List<ItemCardapio> cardapio;

    public Evento(String tema) {
        this.tema = tema;
        this.mesas = new ArrayList<>();
        this.garcons = new ArrayList<>();
        this.cardapio = new ArrayList<>();
    }

    // Getters e Setters
    public String getTema() { return tema; }
    public List<Mesa> getMesas() { return mesas; }
    public void setMesas(List<Mesa> mesas) { this.mesas = mesas; }
    public List<Garcom> getGarcons() { return garcons; }
    public void setGarcons(List<Garcom> garcons) { this.garcons = garcons; }
    public List<ItemCardapio> getCardapio() { return cardapio; }
    public void setCardapio(List<ItemCardapio> cardapio) { this.cardapio = cardapio; }
}