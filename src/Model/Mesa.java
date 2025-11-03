package Model;
import Exception.MesaLotadaException;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
    private int numero;
    private Garcom garcom;
    private List<Convidado> convidados = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();
    private static final int LIMITE_CONVIDADOS = 8;

    public Mesa(int numero, Garcom garcom){
        this.numero = numero;
        this.garcom = garcom;
    }

    public void adicionarConvidado(Convidado convidado) {
        if (convidados.size() >= LIMITE_CONVIDADOS){
            throw new MesaLotadaException();
        }
        convidados.add(convidado);
    }

    public void adicionarPedido(Pedido pedido){
        pedidos.add(pedido);
    }

    public int getNumero() {
        return numero;
    }

    public Garcom getGarcom() {
        return garcom;
    }

    public List<Convidado> getConvidados() {
        return convidados;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public String toString(){
        return "Mesa: " + numero +  " - Garçom: " + garcom.getNome();
    }
}
