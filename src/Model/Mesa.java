package Model;


import java.util.ArrayList;
import java.util.List;

public class Mesa {
    private int numero;
    private Garcom garcom;
    private List<Convidado> convidados = new ArrayList<>();
    //private List<Pedido> pedido = new ArrayList<>();
    private static final int LIMITE_CONVIDADOS = 8;

    public Mesa(int numero, Garcom garcom){
        this.numero = numero;
        this.garcom = garcom;
    }

    public void adicionarConvidado(Convidado convidado) {
        if (convidados.size() >= LIMITE_CONVIDADOS){
            //throw new MesaLotadaException();
        }
        convidados.add(convidado);
    }
}
