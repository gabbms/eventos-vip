package model;
public class ConvidadoRegular extends Convidado {
    @Override
    public double getPercentualDesconto(){
        return 0.0;
    }
    @Override
    public String getTipo(){
        return "regular";
    }
}
