package view;
import model.Convidado;
import model.ItemCardapio;
import model.Mesa;
import model.Evento;
import model.Garcom;
import service.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class MenuPrincipal {
    private static Scanner sc = new Scanner(System.in);
    private static EventoService eventoService = new EventoService();
    private static PagamentoService pagamentoService = new PagamentoService();
    private static PedidoService pedidoService;
    private static IRelatorio relatorioService;
    private static PersistenciaService persistenciaService = new PersistenciaService();
    private static RelatorioPDFService relatorioPDFService = new RelatorioPDFService();
    public static void main(String[] args){
        pedidoService = new PedidoService(eventoService);
        relatorioService = new RelatorioService(pagamentoService);
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
        eventoService.criarEvento("Gala de Tecnologia");
        System.out.println("\n--- Sistema de Eventos VIP iniciado ---");
        while (true) {
            exibirMenu();
            int opcao = lerOpcao();
	            switch (opcao) {
	                case 1:
	                    configurarTemaPersonalizacao();
	                    break;
	                case 2:
	                    cadastrarGarcom();
	                    break;
	                case 3:
	                    cadastrarMesa();
	                    break;
	                case 4:
	                    cadastrarConvidado();
	                    break;
	                case 5:
	                    cadastrarItemCardapio();
	                    break;
	                case 6:
	                    designarConvidadoMesa();
	                    break;
	                case 7:
	                    fazerPedido();
	                    break;
	                case 8:
	                    fecharContaMesa();
	                    break;
	                case 9:
	                    emitirRelatorioConsole();
	                    break;
	                case 10:
	                    gerarRelatorioPDF();
	                    break;
	                case 0:
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
	        System.out.println("1. Configurar Tema e Personalização");
	        System.out.println("2. Cadastrar Garçom");
	        System.out.println("3. Cadastrar Mesa");
	        System.out.println("4. Cadastrar Convidado");
	        System.out.println("5. Cadastrar Item do Cardápio");
	        System.out.println("6. Designar Convidado à Mesa");
	        System.out.println("7. Fazer Pedido");
	        System.out.println("8. Fechar Conta da Mesa");
	        System.out.println("9. Relatório no Console");
	        System.out.println("10. Gerar PDF do Evento");
	        System.out.println("0. Sair e Salvar");
	        System.out.print("Escolha uma opção: ");
	    }
    private static int lerOpcao() {
        return lerInteiro();
    }
    private static int lerInteiro() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    private static void cadastrarConvidado() {
        try {
            System.out.print("Nome do convidado: ");
            String nome = sc.nextLine();
            if (nome.matches(".*\\d.*")) {
                throw new Exception("O nome não pode conter números.");
            }
            if (nome.trim().isEmpty()) {
                throw new Exception("O nome não pode ser vazio.");
            }
            System.out.print("Tipo (1-Regular, 2-VIP): ");
            int tipo = lerInteiro();
            if (tipo != 1 && tipo != 2) {
                throw new Exception("Tipo de convidado inválido. Digite 1 para Regular ou 2 para VIP.");
            }
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
            System.out.println("\n--- MESAS DISPONÍVEIS ---");
            List<Mesa> mesas = eventoService.getEventoAtual().getMesas();
            if (mesas.isEmpty()) {
                System.out.println("Nenhuma mesa cadastrada.");
                return;
            }
            for (Mesa mesa : mesas) {
                String status = mesa.getConvidados().size() < 8 ? "Livre" : "Cheia";
                System.out.printf("Mesa %d (Garçom: %s, Convidados: %d/8, Status: %s)\n",
                        mesa.getNumero(), mesa.getGarcomAssociado().getNome(), mesa.getConvidados().size(), status);
            }
            System.out.println("--------------------------\n");
            System.out.print("ID do convidado: ");
            int id = lerInteiro();
            if (id <= 0) throw new Exception("O ID deve ser positivo.");
            System.out.print("Número da mesa: ");
            int numMesa = lerInteiro();
            if (numMesa <= 0) throw new Exception("O número da mesa deve ser positivo.");
            if (numMesa == -1) throw new Exception("Entrada inválida. Esperado um número para o número da mesa.");
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
	            System.out.println("\n--- CARDÁPIO DISPONÍVEL ---");
	            for (ItemCardapio item : eventoService.getEventoAtual().getCardapio()) {
	                String tipo = item.isExclusivoVIP() ? " (VIP)" : "";
	                System.out.printf("- %s (R$ %.2f)%s\n", item.getNome(), item.getPreco(), tipo);
	            }
	            System.out.println("---------------------------\n");
	            System.out.print("ID do Convidado que pede: ");
            int idConvidado = lerInteiro();
            System.out.print("Número da Mesa: ");
            int idMesa = lerInteiro();
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
            int numMesa = lerInteiro();
            if (numMesa <= 0) throw new Exception("Número da mesa inválido.");
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
	            String tema = eventoService.getEventoAtual().getTema().replaceAll("\\s+", "_");
	            String nomeArquivo = "Relatorio_" + tema + ".pdf";
	            relatorioPDFService.gerarRelatorioPdf(eventoService.getEventoAtual(), nomeArquivo);
	            System.out.println("PDF gerado com sucesso! Arquivo salvo em: " + nomeArquivo);
	        } catch (Exception e) {
	            System.err.println("Erro ao criar PDF (Verifique as bibliotecas iText): " + e.getMessage());
	        }
	    }
	    private static void cadastrarItemCardapio() {
	        try {
	            System.out.print("Nome do Item: ");
	            String nome = sc.nextLine();
	            if (nome.trim().isEmpty()) {
	                throw new Exception("O nome do item não pode ser vazio.");
	            }
	            if (nome.matches(".*\\d.*")) {
	                throw new Exception("O nome do item não pode conter números.");
	            }
	            System.out.print("Preço (ex: 10.50): ");
	            double preco = Double.parseDouble(sc.nextLine());
	            if (preco <= 0) {
	                throw new Exception("O preço deve ser um valor positivo.");
	            }
	            System.out.print("Exclusivo VIP? (S/N): ");
	            String isVipStr = sc.nextLine();
	            boolean isVip = isVipStr.equalsIgnoreCase("S");
	            eventoService.adicionarItemCardapio(nome, preco, isVip);
	            System.out.println("Item '" + nome + "' cadastrado com sucesso!");
	        } catch (NumberFormatException e) {
	            System.err.println("Erro de formato: O preço deve ser um número.");
	        } catch (Exception e) {
	            System.err.println("Erro ao cadastrar item: " + e.getMessage());
	        }
	    }
	    private static void cadastrarGarcom() {
        try {
            System.out.print("Nome do Garçom: ");
            String nome = sc.nextLine();
            if (nome.matches(".*\\d.*")) {
                throw new Exception("O nome não pode conter números.");
            }
            if (nome.trim().isEmpty()) {
                throw new Exception("O nome não pode ser vazio.");
            }
            Garcom garcom = eventoService.cadastrarGarcom(nome);
            System.out.println("Garçom cadastrado com sucesso! ID: " + garcom.getId());
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar Garçom: " + e.getMessage());
        }
    }
    private static void configurarTemaPersonalizacao() {
        try {
            System.out.println("\n--- CONFIGURAÇÃO DE TEMA E PERSONALIZAÇÃO ---");
            Evento evento = eventoService.getEventoAtual();
            System.out.println("Tema atual: " + evento.getTema());
            System.out.print("Novo Tema (deixe em branco para manter): ");
            String novoTema = sc.nextLine();
            if (!novoTema.trim().isEmpty()) {
                evento.setTema(novoTema);
            }
            System.out.println("Personalização de Tema/Mesa Padrão atual: " + evento.getPersonalizacaoTema());
	            System.out.print("Nova Personalização de Tema/Mesa Padrão (deixe em branco para manter) [Ex: Toalhas Brancas, Arranjos Simples]: ");
            String novaPersonalizacaoTema = sc.nextLine();
            if (!novaPersonalizacaoTema.trim().isEmpty()) {
                evento.setPersonalizacaoTema(novaPersonalizacaoTema);
            }
            System.out.println("Personalização de Mesa VIP atual: " + evento.getPersonalizacaoMesaVIP());
	            System.out.print("Nova Personalização de Mesa VIP (deixe em branco para manter) [Ex: Luzes de Neon, Garrafa de Champanhe]: ");
            String novaPersonalizacaoVIP = sc.nextLine();
            if (!novaPersonalizacaoVIP.trim().isEmpty()) {
                evento.setPersonalizacaoMesaVIP(novaPersonalizacaoVIP);
            }
            System.out.println("Configuração atualizada com sucesso!");
	        } catch (Exception e) {
	            System.err.println("Erro ao configurar tema e personalização: " + e.getMessage());
	        }
	    }
	
	    private static void cadastrarMesa() {
	        try {
	            System.out.print("Número da mesa: ");
	            int numMesa = lerInteiro();
	            if (numMesa == -1) throw new Exception("Entrada inválida. Esperado um número para o número da mesa.");
	            if (numMesa <= 0) throw new Exception("O número da mesa deve ser positivo.");
	            System.out.print("ID do Garçom responsável: ");
	            int idGarcom = lerInteiro();
	            if (idGarcom == -1) throw new Exception("Entrada inválida. Esperado um número para o ID do Garçom.");
	            if (idGarcom <= 0) throw new Exception("O ID do Garçom deve ser positivo.");
	            eventoService.cadastrarMesa(numMesa, idGarcom);
	            System.out.println("Mesa " + numMesa + " cadastrada com sucesso!");
	        } catch (Exception e) {
	            System.err.println("Erro ao cadastrar mesa: " + e.getMessage());
	        }
	    }
}
