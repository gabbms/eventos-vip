package service;
import model.Convidado;
import model.ConvidadoRegular;
import model.ConvidadoVIP;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class PersistenciaService {
    private static final String ARQUIVO_CONVIDADOS = "convidados.csv";
    public void salvarConvidados(List<Convidado> convidados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CONVIDADOS))) {
            for (Convidado c : convidados) {
                String linha = c.getId() + ";" + c.getNome() + ";" + c.getTipo();
                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Dados salvos com sucesso em " + ARQUIVO_CONVIDADOS);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
    public List<Convidado> carregarConvidados() {
        List<Convidado> listaRecuperada = new ArrayList<>();
        File arquivo = new File(ARQUIVO_CONVIDADOS);
        if (!arquivo.exists()) {
            return listaRecuperada;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 3) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    String tipo = partes[2];
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