package view;

import Exception.*;
import model.*;
import service.*;
import model.Mesa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private static Scanner sc = new Scanner(System.in);

    // Serviços principais
    private static EventoService eventoService = new EventoService();
    private static PagamentoService pagamentoService = new PagamentoService();
    private static PedidoService pedidoService;
    private static IRelatorio relatorioService;

    // Serviços extras (Persistência e PDF)
    private static PersistenciaService persistenciaService = new PersistenciaService();
    private static RelatorioPDFService relatorioPDFService = new RelatorioPDFService();

    public static void main(String[] args){
        // Injeção de dependências
        pedidoService = new PedidoService(eventoService);
        relatorioService = new RelatorioService(pagamentoService);

        // --- 1. CARREGAR DADOS SALVOS (PERSISTÊNCIA) ---
        try {
            System.out.println("Tentando carregar convidados salvos...");
            List<Convidado> dadosSalvos = persistenciaService.carregarConvidados();

            if (!dadosSalvos.isEmpty()) {
                eventoService.setConvidadosCadastrados(dadosSalvos);
                System.out.println("Sucesso: " + dadosSalvos.size() + " convidados recuperados.");
            } else {
                System.out.println("Nenhum dado anterior encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Aviso: Iniciando com base de dados limpa.");
        }

        // --- 2. CONFIGURAÇÃO INICIAL DO EVENTO ---
        eventoService.criarEvento("Gala de Tecnologia");

        // Cadastra Garçons e Mesas (Dados de teste recriados a cada execução)
        Garcom g1 = eventoService.cadastrarGarcom("Carlos");
        Garcom g2 = eventoService.cadastrarGarcom("Ana");

        eventoService.cadastrarMesa(1, g1);
        eventoService.cadastrarMesa(2, g1);
        eventoService.cadastrarMesa(3, g2);
        eventoService.cadastrarMesa(4, g2);

        // Itens do Cardápio
        eventoService.adicionarItemCardapio("Água", 5.00, false);
        eventoService.adicionarItemCardapio("Refrigerante", 8.00, false);
        eventoService.adicionarItemCardapio("Prato Principal", 50.00, false);
        eventoService.adicionarItemCardapio("Lagosta", 150.00, true); // VIP

        System.out.println("\n--- Sistema de Eventos VIP iniciado ---");

        // --- 3. LOOP DO MENU ---
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
                    emitirRelatorioConsole();
                    break;
                case 6:
                    gerarRelatorioPDF(); // Nova funcionalidade
                    break;
                case 0:
                    // Salva os dados antes de sair
                    System.out.println("Salvando dados...");
                    persistenciaService.salvarConvidados(eventoService.getConvidadosCadastrados());
                    System.out.println("Saindo do sistema. Até logo!");
                    return;
                default:
                    System.err.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n--- MENU EVENTOS VIP ---");
        System.out.println("1. Cadastrar Convidado");
        System.out.println("2. Designar Convidado à Mesa");
        System.out.println("3. Fazer Pedido");
        System.out.println("4. Fechar Conta da Mesa");
        System.out.println("5. Relatório no Console");
        System.out.println("6. Gerar PDF do Evento (Desafio)");
        System.out.println("0. Sair e Salvar");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Erro: Digite apenas números.");
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
            System.out.println(">>> GUARDE ESTE ID: " + idGerado + " <<<");

        } catch (NumberFormatException e) {
            System.err.println("Erro de formato: Digite 1 ou 2 para o tipo.");
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    private static void designarConvidadoMesa() {
        try {
            System.out.print("ID do convidado: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Número da mesa: ");
            int numMesa = Integer.parseInt(sc.nextLine());

            eventoService.designarConvidadoMesa(id, numMesa);
            System.out.println("Sucesso: Convidado adicionado à mesa " + numMesa);

        } catch (NumberFormatException e) {
            System.err.println("Erro de formato: ID e Mesa devem ser números.");
        } catch (Exception e) {
            System.err.println("Erro ao designar: " + e.getMessage());
        }
    }

    private static void fazerPedido() {
        try {
            System.out.print("ID do Convidado que pede: ");
            int idConvidado = Integer.parseInt(sc.nextLine());

            System.out.print("Número da Mesa: ");
            int idMesa = Integer.parseInt(sc.nextLine());

            List<String> nomesItens = new ArrayList<>();
            System.out.println("--- Adicionando Itens ---");

            while (true) {
                System.out.print("Nome do item (ou 'fim' para encerrar): ");
                String nomeItem = sc.nextLine();
                if (nomeItem.equalsIgnoreCase("fim")) {
                    break;
                }
                nomesItens.add(nomeItem);
            }

            if (nomesItens.isEmpty()) {
                System.out.println("Pedido cancelado (vazio).");
                return;
            }

            pedidoService.criarPedido(idMesa, idConvidado, nomesItens);
            System.out.println("Pedido registrado e enviado para a cozinha!");

        } catch (NumberFormatException e) {
            System.err.println("Erro: IDs devem ser números.");
        } catch (Exception e) {
            System.err.println("Erro no pedido: " + e.getMessage());
        }
    }

    private static void fecharContaMesa() {
        try {
            System.out.print("Número da mesa para fechar: ");
            int numMesa = Integer.parseInt(sc.nextLine());

            Mesa mesa = eventoService.buscarMesa(numMesa);
            pagamentoService.calcularContaMesa(mesa);

        } catch (NumberFormatException e) {
            System.err.println("Erro: Digite um número válido.");
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void emitirRelatorioConsole() {
        System.out.println("\n--- RELATÓRIO PARCIAL ---");
        try {
            String relatorio = relatorioService.gerarRelatorio(eventoService.getEventoAtual());
            System.out.println(relatorio);
        } catch (Exception e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    private static void gerarRelatorioPDF() {
        System.out.println("Gerando PDF do evento...");
        try {
            // Caminho do arquivo
            String nomeArquivo = "G:\\Meu Drive\\EventosVIP\\Relatorio_Evento_VIP.pdf";

            relatorioPDFService.gerarRelatorioPdf(eventoService.getEventoAtual(), nomeArquivo);

        } catch (Exception e) {
            System.err.println("Erro ao criar PDF (Verifique as bibliotecas iText): " + e.getMessage());
        }
    }
}