package Model;

import java.util.List;

public class Pedido {
    private int id;
    private List<ItemCardapio> itens;
    private Convidado convidado;
    private Garcom garcom;
    private Mesa mesa;

    public Pedido(int id, Mesa mesa, Convidado convidado, Garcom garcom, List<ItemCardapio> itens) {
        this.id = id;
        this.mesa = mesa;
        this.convidado = convidado;
        this.garcom = garcom;
        this.itens = itens;
    }

    // Calcula o valor bruto do pedido (antes dos descontos do convidado).
    public double calcularTotal() {
        double total = 0;
        for (ItemCardapio item : itens) {
            total += item.getPreco();
        }
        return total;
    }

    public int getId() {
        return id;
    }

    public Convidado getConvidado() {
        return convidado;
    }

    public Garcom getGarcom() {
        return garcom;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public List<ItemCardapio> getItens() {
        return itens;
    }

}

