package Service;

import Model.Evento;

public interface IRelatorio {
    String gerarRelatorio(Evento evento)throws Exception;
}
