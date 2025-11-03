package Model;

import java.util.ArrayList;
import java.util.List;

public class Evento {
    private String tema;
    private List<Mesa> mesas = new ArrayList<>();

    public Evento(String tema) {
        this.tema = tema;
    }

    public void adicionarMesa(Mesa mesa){
        mesas.add(mesa);
    }

    public String getTema() {
        return tema;
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public String toString(){
        return "Evento: " + tema + " | Total de mesas: " + mesas.size();
    }
}
