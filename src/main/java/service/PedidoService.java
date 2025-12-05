package service;
import exception.*;
import model.*;
import java.util.ArrayList;
import java.util.List;
public class PedidoService {
    private EventoService eventoService;
    private int proximoIdPedido = 1;
    public PedidoService(EventoService eventoService) {
        this.eventoService = eventoService;
    }
    public void criarPedido(int idMesa, int idConvidado, List<String> nomesItens)
            throws Exception, PermissaoException, ItemNaoEncontradoException {
        Mesa mesa = eventoService.buscarMesa(idMesa);
        Convidado convidado = eventoService.buscarConvidado(idConvidado);
        Garcom garcom = mesa.getGarcomAssociado();
        List<ItemCardapio> itensDoPedido = new ArrayList<>();
        for (String nomeItem : nomesItens) {
            ItemCardapio item = eventoService.buscarItemCardapio(nomeItem);
            if (item.isExclusivoVIP() && !(convidado instanceof ConvidadoVIP)) {
                throw new PermissaoException("O item '" + item.getNome() + "' é exclusivo para convidados VIP.");
            }
            itensDoPedido.add(item);
        }
        Pedido novoPedido = new Pedido(proximoIdPedido++, mesa, convidado, garcom, itensDoPedido);
        mesa.adicionarPedido(novoPedido);
    }
}
