package model;

import java.util.ArrayList;
import java.util.List;

public class Evento {
    private String tema;
    private String personalizacaoTema; // Adicionado para personalização de tema/mesa
    private String personalizacaoMesaVIP; // Adicionado para personalização de mesa VIP
    private List<Mesa> mesas;
    private List<Garcom> garcons;
    private List<ItemCardapio> cardapio;

    public Evento(String tema) {
        this.tema = tema;
        this.personalizacaoTema = "Decoração Padrão";
        this.personalizacaoMesaVIP = "Decoração VIP Padrão";
        this.mesas = new ArrayList<>();
        this.garcons = new ArrayList<>();
        this.cardapio = new ArrayList<>();
    }

    // Getters e Setters
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public String getPersonalizacaoTema() { return personalizacaoTema; }
    public void setPersonalizacaoTema(String personalizacaoTema) { this.personalizacaoTema = personalizacaoTema; }

    public String getPersonalizacaoMesaVIP() { return personalizacaoMesaVIP; }
    public void setPersonalizacaoMesaVIP(String personalizacaoMesaVIP) { this.personalizacaoMesaVIP = personalizacaoMesaVIP; }
    public List<Mesa> getMesas() { return mesas; }
    public void setMesas(List<Mesa> mesas) { this.mesas = mesas; }
    public List<Garcom> getGarcons() { return garcons; }
    public void setGarcons(List<Garcom> garcons) { this.garcons = garcons; }
    public List<ItemCardapio> getCardapio() { return cardapio; }
    public void setCardapio(List<ItemCardapio> cardapio) { this.cardapio = cardapio; }
}