package Model;

public class Garcom {
    private String nome;

    public Garcom(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString(){
        return "Garçom: " + nome;
    }
}
