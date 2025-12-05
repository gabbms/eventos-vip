package service;
import model.Convidado;
import model.Evento;
import model.Mesa;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.File;
import java.io.FileNotFoundException;
public class RelatorioPDFService {
    public void gerarRelatorioPdf(Evento evento, String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);
        if (arquivo.getParentFile() != null) {
            arquivo.getParentFile().mkdirs();
        }
        try {
            PdfWriter writer = new PdfWriter(caminhoArquivo);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph("Relatório Final: " + evento.getTema())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20)
                    .setBold());
            document.add(new Paragraph("Tema: " + evento.getTema()));
            document.add(new Paragraph("Personalização Padrão: " + evento.getPersonalizacaoTema()));
            document.add(new Paragraph("Personalização VIP: " + evento.getPersonalizacaoMesaVIP()));
            document.add(new Paragraph("\n--------------------------------------------------\n"));
            PagamentoService pagamentoService = new PagamentoService();
            double totalGeral = 0;
            for (Mesa mesa : evento.getMesas()) {
                document.add(new Paragraph("Mesa " + mesa.getNumero()).setBold().setFontSize(14));
                double totalMesa = pagamentoService.calcularContaMesa(mesa);
                totalGeral += totalMesa;
                document.add(new Paragraph("Garçom: " + mesa.getGarcomAssociado().getNome()));
                document.add(new Paragraph("Convidados: " + mesa.getConvidados().size()));
                for(Convidado c : mesa.getConvidados()){
                    document.add(new Paragraph(" - " + c.getNome() + " (" + c.getTipo() + ")"));
                }
                document.add(new Paragraph(String.format("Total da Mesa: R$ %.2f", totalMesa)));
                document.add(new Paragraph("\n"));
            }
            document.add(new Paragraph("--------------------------------------------------"));
            document.add(new Paragraph(String.format("FATURAMENTO TOTAL: R$ %.2f", totalGeral))
                    .setBold().setFontSize(16));
            document.close();
            System.out.println("PDF gerado com sucesso em: " + caminhoArquivo);
        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo aberto ou caminho inválido.");
        } catch (Exception e) {
            System.err.println("Erro ao gerar PDF: " + e.getMessage());
        }
    }
}
