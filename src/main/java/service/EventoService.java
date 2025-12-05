package service;

import exception.ItemNaoEncontradoException;
import exception.MesaLotadaException;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class EventoService {
    private Evento eventoAtual;
    private List<Convidado> convidadosCadastrados;
    private List<Mesa> mesasCadastradas;
    private List<Garcom> garconsCadastrados;

    // Contadores para gerar IDs únicos
    private int proximoIdConvidado = 1;
    private int proximoIdGarcom = 1;

    public EventoService() {
        this.convidadosCadastrados = new ArrayList<>();
        this.mesasCadastradas = new ArrayList<>();
        this.garconsCadastrados = new ArrayList<>();
    }

    // Métodos de Configuração do Evento

    public void criarEvento(String tema) {
        this.eventoAtual = new Evento(tema);
        // Associa as listas do serviço ao objeto evento
        this.eventoAtual.setMesas(this.mesasCadastradas);
        this.eventoAtual.setGarcons(this.garconsCadastrados);
        this.eventoAtual.setCardapio(new ArrayList<>());
        System.out.println("Evento '" + tema + "' criado.");
    }

    public void adicionarItemCardapio(String nome, double preco, boolean exclusivoVIP) {
        if (this.eventoAtual == null) {
            System.out.println("Erro: Crie um evento antes de adicionar itens.");
            return;
        }
        ItemCardapio item = new ItemCardapio(nome, preco, exclusivoVIP);
        this.eventoAtual.getCardapio().add(item);
        System.out.println("Item '" + nome + "' adicionado ao cardápio.");
    }

    // Métodos de Cadastro

    public Garcom cadastrarGarcom(String nome) {
        Garcom g = new Garcom(nome, proximoIdGarcom++);
        this.garconsCadastrados.add(g);
        System.out.println("Garçom " + nome + " (ID: " + g.getId() + ") cadastrado.");
        return g;
    }

    public Mesa cadastrarMesa(int numero, int idGarcom) throws Exception {
        Garcom garcom = buscarGarcom(idGarcom);
        
        // Validação: Verifica se a mesa já existe
        for (Mesa m : mesasCadastradas) {
            if (m.getNumero() == numero) {
                throw new Exception("Mesa de número " + numero + " já cadastrada.");
            }
        }
        
        Mesa m = new Mesa(numero, garcom);
        this.mesasCadastradas.add(m);
        System.out.println("Mesa " + numero + " cadastrada e associada ao garçom " + garcom.getNome());
        return m;
    }

    public int cadastrarConvidado(String nome, String tipo) {
        Convidado novoConvidado;

        if ("VIP".equalsIgnoreCase(tipo)) {
            novoConvidado = new ConvidadoVIP();
        } else {
            novoConvidado = new ConvidadoRegular();
        }

        novoConvidado.setNome(nome);
        novoConvidado.setId(proximoIdConvidado++);
        this.convidadosCadastrados.add(novoConvidado);

        return novoConvidado.getId(); // Retorna o ID para o usuário
    }

    // Métodos de Lógica/Orquestração

    public void designarConvidadoMesa(int idConvidado, int numeroMesa)
            throws Exception, MesaLotadaException {

        Convidado convidado = buscarConvidado(idConvidado);
        Mesa mesa = buscarMesa(numeroMesa);

        // A lógica de validação está dentro da classe Mesa
        mesa.adicionarConvidado(convidado);
    }

    // Métodos de Busca

    public Garcom buscarGarcom(int id) throws Exception {
        for (Garcom g : garconsCadastrados) {
            if (g.getId() == id) {
                return g;
            }
        }
        throw new Exception("Garçom com ID " + id + " não encontrado.");
    }

    public Convidado buscarConvidado(int id) throws Exception {
        for (Convidado c : convidadosCadastrados) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new Exception("Convidado com ID " + id + " não encontrado.");
    }

    public Mesa buscarMesa(int numero) throws Exception {
        for (Mesa m : mesasCadastradas) {
            if (m.getNumero() == numero) {
                return m;
            }
        }
        throw new Exception("Mesa de número " + numero + " não encontrada.");
    }

    public ItemCardapio buscarItemCardapio(String nome) throws ItemNaoEncontradoException {
        if (this.eventoAtual == null || this.eventoAtual.getCardapio() == null) {
            throw new ItemNaoEncontradoException("Cardápio vazio ou evento não iniciado.");
        }
        for (ItemCardapio item : this.eventoAtual.getCardapio()) {
            if (item.getNome().equalsIgnoreCase(nome)) {
                return item;
            }
        }
        throw new ItemNaoEncontradoException("Item '" + nome + "' não encontrado no cardápio.");
    }

    public Evento getEventoAtual() {
        return eventoAtual;
    }

    //  MÉTODOS PARA PERSISTÊNCIA

    // Permite que o PersistenciaService pegue a lista para salvar
    public List<Convidado> getConvidadosCadastrados() {
        return convidadosCadastrados;
    }

    // Permite que o Menu carregue a lista do arquivo para cá
    public void setConvidadosCadastrados(List<Convidado> convidados) {
        this.convidadosCadastrados = convidados;

        // Atualiza o contador de IDs para não repetir IDs antigos ao cadastrar novos
        if (!convidados.isEmpty()) {
            int maiorId = 0;
            for (Convidado c : convidados) {
                if (c.getId() > maiorId) {
                    maiorId = c.getId();
                }
            }
            this.proximoIdConvidado = maiorId + 1;
        }
    }
}