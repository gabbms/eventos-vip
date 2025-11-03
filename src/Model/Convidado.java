package Model;

public class Convidado extends Pessoa{
    private String tipo; // VIP ou Regular

    public Convidado(String nome, int id, String tipo){
        super(nome, id);
        this.tipo = tipo;
    }



    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString(){
        return "Nome: " + getNome() +
                "Tipo: " + tipo;
    }

}
