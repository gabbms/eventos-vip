package Service;

import Model.Evento;
import Model.Mesa;
import Model.Pedido;

/**
 * Implementação concreta da interface IRelatorio.
 * [cite_start]Sabe como calcular e formatar o relatório de faturamento do evento. [cite: 20, 26]
 */
public class RelatorioService implements IRelatorio {

    private PagamentoService pagamentoService;

    // Recebe o PagamentoService para poder calcular o total das mesas
    public RelatorioService(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @Override
    public String gerarRelatorio(Evento evento) throws Exception {
        if (evento == null) {
            throw new Exception("Nenhum evento foi iniciado.");
        }

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("========================================\n");
        relatorio.append("   RELATÓRIO DE FATURAMENTO DO EVENTO\n");
        relatorio.append("========================================\n");
        relatorio.append("Tema do Evento: ").append(evento.getTema()).append("\n");
        relatorio.append("----------------------------------------\n");

        double faturamentoTotal = 0.0;
        int totalPedidos = 0;

        // Itera por todas as mesas cadastradas no evento
        for (Mesa mesa : evento.getMesas()) {
            relatorio.append("\nResumo da Mesa: ").append(mesa.getNumero()).append("\n");

            // Reutiliza o PagamentoService para calcular o total da mesa
            // O PagamentoService já imprime o detalhe da mesa
            double totalMesa = pagamentoService.calcularContaMesa(mesa);
            faturamentoTotal += totalMesa;

            // Coleta estatísticas adicionais
            totalPedidos += mesa.getPedidos().size();
            relatorio.append("Nº de Convidados na Mesa: ").append(mesa.getConvidados().size()).append("\n");
            relatorio.append("Total de Pedidos na Mesa: ").append(mesa.getPedidos().size()).append("\n");
        }

        // Resumo final
        relatorio.append("\n----------------------------------------\n");
        relatorio.append("          RESUMO GERAL DO EVENTO\n");
        relatorio.append("----------------------------------------\n");
        relatorio.append(String.format("Total de Mesas: %d\n", evento.getMesas().size()));
        relatorio.append(String.format("Total de Garçons: %d\n", evento.getGarcons().size()));
        relatorio.append(String.format("Total de Pedidos Registrados: %d\n", totalPedidos));
        relatorio.append(String.format("\nFATURAMENTO TOTAL (Recebimentos): R$ %.2f\n", faturamentoTotal));
        relatorio.append("========================================\n");

        return relatorio.toString();
    }
}
