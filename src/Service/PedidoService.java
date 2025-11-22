package Service;

import Exception.ItemNaoEncontradoException;
import Exception.PermissaoException;
import Model.*;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private EventoService eventoService; // Depende do EventoService para buscar dados
    private int proximoIdPedido = 1;

    // Injeção de Dependência via construtor
    public PedidoService(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    /**
     * Orquestra a criação de um pedido.
     */
    public void criarPedido(int idMesa, int idConvidado, List<String> nomesItens)
            throws Exception, PermissaoException, ItemNaoEncontradoException {

        // 1. Buscar as entidades
        Mesa mesa = eventoService.buscarMesa(idMesa);
        Convidado convidado = eventoService.buscarConvidado(idConvidado);
        Garcom garcom = mesa.getGarcomAssociado();

        List<ItemCardapio> itensDoPedido = new ArrayList<>();

        // 2. Validar os itens e permissões
        for (String nomeItem : nomesItens) {
            ItemCardapio item = eventoService.buscarItemCardapio(nomeItem);

            // REGRA DE NEGÓCIO: Verifica se o item é VIP [cite: 11]
            if (item.isExclusivoVIP() && !(convidado instanceof ConvidadoVIP)) {
                throw new PermissaoException("O item '" + item.getNome() + "' é exclusivo para convidados VIP.");
            }
            itensDoPedido.add(item);
        }

        // 3. Criar o Pedido
        Pedido pedido = new Pedido(proximoIdPedido++, mesa, convidado, garcom, itensDoPedido);

        // 4. Registrar o pedido na mesa
        mesa.adicionarPedido(pedido);

        // 5. Notificar o Garçom
        notificarGarcom(garcom, pedido);
    }

    private void notificarGarcom(Garcom garcom, Pedido pedido) {
        System.out.println("\n========================================");
        System.out.println("[NOTIFICAÇÃO PARA GARÇOM: " + garcom.getNome() + "]");
        System.out.println("Novo pedido (ID: " + pedido.getId() + ") para a Mesa " + pedido.getMesa().getNumero());
        for (ItemCardapio item : pedido.getItens()) {
            System.out.println("  - " + item.getNome());
        }
        System.out.println("========================================\n");
    }
}