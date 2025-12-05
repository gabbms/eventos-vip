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
    private int proximoIdConvidado = 1;
    private int proximoIdGarcom = 1;
    public EventoService() {
        this.convidadosCadastrados = new ArrayList<>();
        this.mesasCadastradas = new ArrayList<>();
        this.garconsCadastrados = new ArrayList<>();
    }
    public void criarEvento(String tema) {
        this.eventoAtual = new Evento(tema);
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
    public Garcom cadastrarGarcom(String nome) {
        Garcom g = new Garcom(nome, proximoIdGarcom++);
        this.garconsCadastrados.add(g);
        System.out.println("Garçom " + nome + " (ID: " + g.getId() + ") cadastrado.");
        return g;
    }
    public Mesa cadastrarMesa(int numero, int idGarcom) throws Exception {
        Garcom garcom = buscarGarcom(idGarcom);
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
        return novoConvidado.getId();
    }
    public void designarConvidadoMesa(int idConvidado, int numeroMesa)
            throws Exception, MesaLotadaException {
        Convidado convidado = buscarConvidado(idConvidado);
        Mesa mesa = buscarMesa(numeroMesa);
        mesa.adicionarConvidado(convidado);
    }
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
    public List<Convidado> getConvidadosCadastrados() {
        return convidadosCadastrados;
    }
    public void setConvidadosCadastrados(List<Convidado> convidados) {
        this.convidadosCadastrados = convidados;
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