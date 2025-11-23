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

    // NOVO: Serviço de persistência para salvar arquivos
    private static PersistenciaService persistenciaService = new PersistenciaService();

    public static void main(String[] args){
        // Injeção de dependências
        pedidoService = new PedidoService(eventoService);
        relatorioService = new RelatorioService(pagamentoService);

        // --- CARREGAR DADOS (PERSISTÊNCIA) ---
        try {
            System.out.println("Tentando carregar dados salvos...");
            List<Convidado> dadosSalvos = persistenciaService.carregarConvidados();

            // Se houver dados salvos, atualiza o serviço
            if (!dadosSalvos.isEmpty()) {
                eventoService.setConvidadosCadastrados(dadosSalvos);
                System.out.println("Dados carregados com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("Nenhum arquivo de dados encontrado. Iniciando limpo.");
        }
        // -------------------------------------

        eventoService.criarEvento("Gala de Tecnologia");

        // Dados iniciais (Hardcoded para teste)
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
                    // --- SALVAR DADOS AO SAIR ---
                    System.out.println("Salvando dados em arquivo...");
                    persistenciaService.salvarConvidados(eventoService.getConvidadosCadastrados());
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
        System.out.println("0. Sair (Salvar dados)");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Erro: Por favor, digite apenas números.");
            return -1;
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
        try {
            System.out.print("Código (ID) do convidado: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Número da mesa: ");
            int numMesa = Integer.parseInt(sc.nextLine());

            eventoService.designarConvidadoMesa(id, numMesa);
            System.out.println("Convidado adicionado à mesa com sucesso!");

        } catch (NumberFormatException e) {
            // CORREÇÃO AQUI: NumberFormatException vem ANTES de Exception
            System.err.println("Erro de formato: O ID e o N° da mesa devem ser números.");
        } catch (Exception e) {
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

            pedidoService.criarPedido(idMesa, idConvidado, nomesItens);
            System.out.println("Pedido criado com sucesso!");

        } catch (NumberFormatException e) {
            System.err.println("Erro de formato: IDs devem ser números.");
        } catch (Exception e) {
            System.err.println("Erro ao fazer pedido: " + e.getMessage());
        }
    }

    private static void fecharContaMesa() {
        try {
            System.out.print("Número da mesa para fechar a conta: ");
            int numMesa = Integer.parseInt(sc.nextLine());

            Mesa mesa = eventoService.buscarMesa(numMesa);
            pagamentoService.calcularContaMesa(mesa);

        } catch (NumberFormatException e) {
            System.err.println("Erro de formato: O número da mesa deve ser um número.");
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void emitirRelatorio() {
        System.out.println("Gerando relatório completo do evento...");
        try {
            String relatorio = relatorioService.gerarRelatorio(eventoService.getEventoAtual());
            System.out.println(relatorio);

        } catch (Exception e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}