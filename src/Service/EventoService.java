package Service;

import Exception.ItemNaoEncontradoException;
import Exception.MesaLotadaException;
import Model.*;

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

    // --- Métodos de Configuração do Evento ---

    public void criarEvento(String tema) {
        this.eventoAtual = new Evento(tema);
        this.eventoAtual.setMesas(this.mesasCadastradas);
        this.eventoAtual.setGarcons(this.garconsCadastrados);
        this.eventoAtual.setCardapio(new ArrayList<>());
        System.out.println("Evento '" + tema + "' criado.");
    }

    public void adicionarItemCardapio(String nome, double preco, boolean exclusivoVIP) {
        ItemCardapio item = new ItemCardapio(nome, preco, exclusivoVIP);
        this.eventoAtual.getCardapio().add(item);
        System.out.println("Item '" + nome + "' adicionado ao cardápio.");
    }


    public Garcom cadastrarGarcom(String nome) {
        Garcom g = new Garcom();
        g.setNome(nome);
        g.setId(proximoIdGarcom++);
        this.garconsCadastrados.add(g);
        System.out.println("Garçom " + nome + " (ID: " + g.getId() + ") cadastrado.");
        return g;
    }

    public Mesa cadastrarMesa(int numero, Garcom garcom) {
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

    // --- Métodos de Lógica/Orquestração ---

    public void designarConvidadoMesa(int idConvidado, int numeroMesa)
            throws Exception, MesaLotadaException {

        Convidado convidado = buscarConvidado(idConvidado);
        Mesa mesa = buscarMesa(numeroMesa);

        // A lógica de "pode adicionar?" está encapsulada na Mesa (model/Mesa.java)
        mesa.adicionarConvidado(convidado);
    }

    // --- Métodos de Busca (Encapsulamento da lógica) ---

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
}
