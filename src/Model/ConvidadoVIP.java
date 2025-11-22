package Model;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

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
