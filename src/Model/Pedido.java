package Model;

public class Pedido {
    private double valor;
    private String item;
    private Convidado convidado;

    public Pedido(double valor, String item, Convidado convidado) {
        this.valor = valor;
        this.item = item;
        this.convidado = convidado;
    }

    public double getValor() {
        return valor;
    }

    public String getItem() {
        return item;
    }

    public Convidado getConvidado() {
        return convidado;
    }

    public String toString(){
        return  "Convidado: " + convidado.getNome() + "\n(Item: " + item +
                ", Valor: )" + valor;
    }
}
