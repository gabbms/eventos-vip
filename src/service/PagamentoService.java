package service;

import model.Convidado;
import model.Mesa;
import model.Pedido;
import java.util.List;

public class PagamentoService {

    public double calcularContaMesa(Mesa mesa) {
        double totalMesa = 0.0;
        List<Pedido> pedidosDaMesa = mesa.getPedidos();

        System.out.println("--- Fechamento da Mesa " + mesa.getNumero() + " ---");

        // Itera sobre os convidados da mesa
        for (Convidado c : mesa.getConvidados()) {
            double totalConvidado = 0.0;

            // Soma todos os pedidos FEITOS POR ESSE CONVIDADO
            for (Pedido p : pedidosDaMesa) {
                if (p.getConvidado().getId() == c.getId()) {
                    totalConvidado += p.calcularTotal();
                }
            }

            double desconto = c.getPercentualDesconto();
            double valorFinal = totalConvidado * (1.0 - desconto);
            totalMesa += valorFinal;

            System.out.printf("Convidado: %s (%s) | Consumo: R$ %.2f | Desconto: %.0f%% | Final: R$ %.2f\n",
                    c.getNome(), c.getTipo(), totalConvidado, desconto * 100, valorFinal);
        }

        if (totalMesa == 0.0 && !mesa.getConvidados().isEmpty()) {
            System.out.println("Nenhum pedido registrado para os convidados desta mesa.");
        } else if (mesa.getConvidados().isEmpty()) {
            System.out.println("Mesa vazia.");
        }

        System.out.printf(">>> TOTAL DA MESA: R$ %.2f\n", totalMesa);
        return totalMesa;
    }
}

