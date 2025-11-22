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

        while (true) {
            exibirMenu();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarConvidado();
                    break;
                case 2:
                    designarConvidadoMesa();
                    break;
                case 3:
                    fazerPedido();
                    break;
                case 4:
                    fecharContaMesa();
                    break;
                case 5:
                    emitirRelatorio();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    return;
                default:
                    System.err.println("Opção inválida! Tente novamente.");
            }
        }
    }
    private static void exibirMenu() {
        System.out.println("\n--- Eventos VIP ---");
        System.out.println("1. Cadastrar Convidado");
        System.out.println("2. Designar Convidado à Mesa");
        System.out.println("3. Fazer Pedido");
        System.out.println("4. Fechar Conta da Mesa");
        System.out.println("5. Emitir Relatório Final do Evento");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Erro: Por favor, digite apenas números.");
            return -1; // Retorna uma opção inválida
        }
    }
}


