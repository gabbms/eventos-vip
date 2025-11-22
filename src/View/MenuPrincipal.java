package View;

import Exception.*;
import Model.*;
import Service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MenuPrincipal {
    private static Scanner sc = new Scanner(System.in);
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
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Erro: Por favor, digite apenas números.");
            return -1; // Retorna uma opção inválida
        }
    }

    private static void cadastrarConvidado() {
        try {
            System.out.print("Nome do convidado: ");
            String nome = sc.nextLine();
            System.out.print("Tipo (1-Regular, 2-VIP): ");
            int tipo = Integer.parseInt(sc.nextLine());

            String tipoStr = (tipo == 2) ? "VIP" : "Regular";

            int idGerado = eventoService.cadastrarConvidado(nome, tipoStr);

            System.out.println("Convidado cadastrado com sucesso!");
            System.out.println(">>> O ID do convidado " + nome + " é: " + idGerado + " <<<");

        } catch (NumberFormatException e) {
            System.err.println("Erro de formato: O tipo deve ser 1 ou 2.");
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar: " + e.getMessage());
        }
    }
    private static void designarConvidadoMesa() {
        // Bloco try-catch para capturar exceções
        try {
            System.out.print("Código (ID) do convidado: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Número da mesa: ");
            int numMesa = Integer.parseInt(sc.nextLine());

            // Chama o service, que pode lançar (throw) exceções
            eventoService.designarConvidadoMesa(id, numMesa);

        } catch (NumberFormatException e) {
            System.err.println("Erro de formato: O ID e o N° da mesa devem ser números.");
        } catch (Exception e) { // Captura (catch) as exceções
            System.err.println("Erro ao designar mesa: " + e.getMessage());
        }
    }
    private static void fazerPedido() {
        try {
            System.out.print("ID do Convidado que está pedindo: ");
            int idConvidado = Integer.parseInt(sc.nextLine());

            System.out.print("Número da Mesa: ");
            int idMesa = Integer.parseInt(sc.nextLine());

            List<String> nomesItens = new ArrayList<>();
            System.out.println("Digite os itens (ou 'fim' para encerrar):");
            while (true) {
                String nomeItem = sc.nextLine();
                if (nomeItem.equalsIgnoreCase("fim")) {
                    break;
                }
                nomesItens.add(nomeItem);
            }

            if (nomesItens.isEmpty()) {
                System.out.println("Nenhum item adicionado. Pedido cancelado.");
                return;
            }

            // Chama o service para processar a lógica
            pedidoService.criarPedido(idMesa, idConvidado, nomesItens);
            System.out.println("Pedido criado com sucesso!");

        } catch (NumberFormatException e) {
            System.err.println("Erro de formato: IDs devem ser números.");
        } catch (Exception e) {
            // Trata múltiplas exceções de negócio
            System.err.println("Erro ao fazer pedido: " + e.getMessage());
        }
    }


}


