package model;

public class ConvidadoVIP extends Convidado{
    private final double DESCONTO_VIP=0.15;

    @Override
    public double getPercentualDesconto(){
        return DESCONTO_VIP;
    }
    @Override
    public String getTipo(){
        return "Convidado VIP";
    }
}
