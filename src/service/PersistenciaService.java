package service;

import model.Convidado;
import model.ConvidadoRegular;
import model.ConvidadoVIP;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaService {

    private static final String ARQUIVO_CONVIDADOS = "convidados.csv";

    // 1. Salvar a lista de convidados no arquivo
    public void salvarConvidados(List<Convidado> convidados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CONVIDADOS))) {
            for (Convidado c : convidados) {
                // Formato: ID;NOME;TIPO
                String linha = c.getId() + ";" + c.getNome() + ";" + c.getTipo();
                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Dados salvos com sucesso em " + ARQUIVO_CONVIDADOS);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    // 2. Carregar a lista do arquivo para o sistema
    public List<Convidado> carregarConvidados() {
        List<Convidado> listaRecuperada = new ArrayList<>();
        File arquivo = new File(ARQUIVO_CONVIDADOS);

        if (!arquivo.exists()) {
            return listaRecuperada; // Retorna lista vazia se o arquivo não existir
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 3) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    String tipo = partes[2]; // Você precisará ajustar o getTipo() no Model para retornar algo padronizado como "VIP" ou "Regular"

                    Convidado c;
                    if (tipo.toUpperCase().contains("VIP")) {
                        c = new ConvidadoVIP();
                    } else {
                        c = new ConvidadoRegular();
                    }
                    c.setId(id);
                    c.setNome(nome);
                    listaRecuperada.add(c);
                }
            }
            System.out.println("Dados carregados: " + listaRecuperada.size() + " convidados.");
        } catch (IOException e) {
            System.err.println("Erro ao ler dados: " + e.getMessage());
        }
        return listaRecuperada;
    }
}