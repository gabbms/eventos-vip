package model;

public class ItemCardapio {
    private String nome;
    private double preco;
    private boolean exclusivoVIP;

    public ItemCardapio(String nome, double preco, boolean exclusivoVIP) {
        this.nome = nome;
        this.preco = preco;
        this.exclusivoVIP = exclusivoVIP;
    }


    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public boolean isExclusivoVIP() {
        return exclusivoVIP;
    }
}
