package Model;

public class Convidado {
    private String nome;
    private String tipo; // VIP ou Regular

    public Convidado(String nome, String tipo){
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString(){
        return "Nome: " + nome +
                "Tipo: " + tipo;
    }

}
