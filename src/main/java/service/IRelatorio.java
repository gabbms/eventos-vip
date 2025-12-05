package service;
import model.Evento;
public interface IRelatorio {
    String gerarRelatorio(Evento evento)throws Exception;
}
