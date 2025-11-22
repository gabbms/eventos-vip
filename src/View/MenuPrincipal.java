package View;

import Exception.*;
import Model.*;
import Service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MenuPrincipal {
    private static Scanner scanner = new Scanner(System.in);
    private static EventoService eventoService = new EventoService();
    private static PagamentoService pagamentoService = new PagamentoService();
    private static PedidoService pedidoService;
    private static IRelatorio relatorioService;

    public static void main(String[] args){
        pedidoService = new PedidoService(eventoService);
        relatorioService = new RelatorioService(pagamentoService);

        eventoService.criarEvento("Gala de Tecnologia");



    }
}
