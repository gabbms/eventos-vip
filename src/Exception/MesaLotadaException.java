package Exception;

public class MesaLotadaException extends RuntimeException{
    public MesaLotadaException(){
        super("A mesa atigiu o limite máximo de 8 convidados");
    }

}
