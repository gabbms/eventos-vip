package View;

import Exception.*;
import Model.*;
import Service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MenuPrincipal {
    private static Scanner scanner = new Scanner(System.in);
    private static EventoService eventoService = new EventoService();
    private static PagamentoService pagamentoService = new PagamentoService();
    private static PedidoService pedidoService;
    private static IRelatorio relatorioService;

    public static void main(String[] args){
        pedidoService = new PedidoService(eventoService);
        relatorioService = new RelatorioService(pagamentoService);

        eventoService.criarEvento("Gala de Tecnologia");

        Garcom g1 = eventoService.cadastrarGarcom("Carlos");
        Garcom g2 = eventoService.cadastrarGarcom("Ana");
        Mesa m1 = eventoService.cadastrarMesa(1, g1);
        Mesa m2 = eventoService.cadastrarMesa(2, g2);

        eventoService.adicionarItemCardapio("Água", 5.00, false);
        eventoService.adicionarItemCardapio("Refrigerante", 8.00, false);
        eventoService.adicionarItemCardapio("Prato Principal", 50.00, false);
        eventoService.adicionarItemCardapio("Lagosta", 150.00, true);

        System.out.println("\n--- Sistema de Eventos VIP iniciado ---");
    }
}
