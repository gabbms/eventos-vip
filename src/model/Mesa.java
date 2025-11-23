package model;
import Exception.MesaLotadaException;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
    private int numero;
    private List<Convidado> convidados;
    private List<Pedido> pedidos;
    private Garcom garcomAssociado;
    private static final int LIMITE_CONVIDADOS = 8;

    public Mesa(int numero, Garcom garcom) {
        this.numero = numero;
        this.garcomAssociado = garcom;
        this.convidados = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public void adicionarConvidado(Convidado c) throws MesaLotadaException {
        if (convidados.size() >= LIMITE_CONVIDADOS) {
            throw new MesaLotadaException("Mesa " + numero + " está lotada. Limite de " + LIMITE_CONVIDADOS + " convidados.");
        }
        convidados.add(c);
        System.out.println(c.getNome() + " (" + c.getTipo() + ") adicionado à mesa " + numero);
    }
    public void adicionarPedido(Pedido p) {
        this.pedidos.add(p);
    }
    public List<Convidado> getConvidados() { return convidados; }
    public List<Pedido> getPedidos() { return pedidos; }
    public int getNumero() { return numero; }
    public Garcom getGarcomAssociado() { return garcomAssociado; }

}
