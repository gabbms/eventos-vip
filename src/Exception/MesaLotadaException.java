package Exception;

public class MesaLotadaException extends RuntimeException{
    public MesaLotadaException(String s){
        super("A mesa atigiu o limite máximo de 8 convidados");
    }

}
