package service;


import exception.*;
import model.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private EventoService eventoService; // Depende do EventoService para buscar dados
    private int proximoIdPedido = 1;

    // Injeção de Dependência via construtor
    public PedidoService(EventoService eventoService) {
        this.eventoService = eventoService;
    }


    public void criarPedido(int idMesa, int idConvidado, List<String> nomesItens)
            throws Exception, PermissaoException, ItemNaoEncontradoException {

        // Buscar as entidades
        Mesa mesa = eventoService.buscarMesa(idMesa);
        Convidado convidado = eventoService.buscarConvidado(idConvidado);
        Garcom garcom = mesa.getGarcomAssociado();

        List<ItemCardapio> itensDoPedido = new ArrayList<>();

        // Validar os itens e permissões
        for (String nomeItem : nomesItens) {
            ItemCardapio item = eventoService.buscarItemCardapio(nomeItem);

            // REGRA DE NEGÓCIO: Verifica se o item é VIP
            if (item.isExclusivoVIP() && !(convidado instanceof ConvidadoVIP)) {
                throw new PermissaoException("O item '" + item.getNome() + "' é exclusivo para convidados VIP.");
            }
            itensDoPedido.add(item);
        }

        /* Criar o Pedido */
        Pedido pedido = new Pedido(proximoIdPedido++, mesa, convidado, garcom, itensDoPedido);

        //  Registrar o pedido na mesa
        mesa.adicionarPedido(pedido);

        // Notificar o Garçom
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