/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formais152;

import javax.swing.JOptionPane;

import formais152.Modelo.Automato;

import formais152.Modelo.Estado;
import formais152.Modelo.Expressao;
import formais152.Modelo.Gramatica;
import formais152.Modelo.InputOutput;

public class main {

    static String menu = "a) opera��es de automatos finito\nb) opera��es de express�o regular\nc) opera��es de gramaticas\n\nDigite a op��o";
    static String menuAutomato = "a)Ler automato\nb)determinizar\nc)Converter em gramatica\nd)Voltar";
    static String menuExpressao = "a)Ler expressao\nb)transformar em Automato\nc)voltar";
    static String menuGramatica = "a)Ler gramatica\nb)transformar em Automato\nc)voltar";
    static Automato automato;
    static Expressao expressao;
    static Gramatica gramatica;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            Automato a = new Automato();
            a.addSimbolo("a");
            a.addSimbolo("b");
            a.addEstado("q0");
            a.addEstadoFinal("q1");
            a.addEstadoFinal("q2");
            a.addTransicao("q0", "a", "q1");
            a.addTransicao("q0", "b", "q2");
            a.setEstadoInicial("q0");

            Automato b = new Automato();
            b.addSimbolo("a");
            b.addSimbolo("c");
            b.addEstado("w0");
            b.addEstado("w1");
            b.addEstadoFinal("w2");
            b.addTransicao("w0", "a", "w1");
            b.addTransicao("w1", "c", "w2");
            b.setEstadoInicial("w0");
            System.out.println(a);
            System.out.println(b);

            Automato c = a.concatenacao(b);
            System.out.println(c);
            System.out.println(a);
            System.out.println(b);
            Automato d = b.concatenacao(a);
            System.out.println(d);

            System.out.println("\n\nTestando remoção de &\n");
            System.out.println(d.removerEpsilonTransicoes());
            System.out.println("\n\n\n");
            System.out.println(c.removerEpsilonTransicoes());
        } catch (Exception e) {
        }

        /*
        
            
            
            
         Automato aut = new Automato();
		

         aut.addEstado("S");
         aut.addEstado("A");
         aut.addEstado("B");
         aut.addEstado("C");
         aut.addEstadoFinal("S");
         aut.addEstadoFinal("C");
		
         try {
         aut.setEstadoInicial("S");
         aut.addTransicao("S", "a", "A");
         aut.addTransicao("A", "a", "S");
         aut.addTransicao("S", "b", "B");
         aut.addTransicao("A", "b", "C");
         aut.addTransicao("B", "b", "S");
         aut.addTransicao("B", "a", "C");
         aut.addTransicao("C", "a", "B");
         aut.addTransicao("C", "b", "A");
         } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         }
		
         System.out.println(aut);
		
         Gramatica gr2 = aut.transformaEmGramatica();
         gr2.retiraSimboloInicialDasProducoesADireita();
         System.out.println(gr2);
		
         System.out.println("\n");
		
         Gramatica gr = new Gramatica();
		
         gr.adicionaProducao("*S", "aA");
         gr.adicionaProducao("S", "bB");
         gr.adicionaProducao("S", "&");
         gr.adicionaProducao("A", "aS");
         gr.adicionaProducao("A", "bC");
         gr.adicionaProducao("A", "a");
         gr.adicionaProducao("A", "b");
         gr.adicionaProducao("B", "bS");
         gr.adicionaProducao("B", "aC");
         gr.adicionaProducao("B", "a");
         gr.adicionaProducao("B", "b");
         gr.adicionaProducao("C", "aB");
         gr.adicionaProducao("C", "bA");
		
		
         gr.retiraSimboloInicialDasProducoesADireita();
		
         Automato aut2 = gr.transformaEmAutomato();
		
         */
        //       System.out.println(aut2);
//		System.out.println(gr);
//		
//		Automato aut2 = gr.transformaEmAutomato();
//		
//		System.out.println(aut2);
//		
//		System.out.println(aut2.determinizar());
    }

    static void automato() {
        String text = "";
        while (text.length() == 0) {
            text = JOptionPane.showInputDialog(menuAutomato);
        }
        char option = text.charAt(0);

        switch (option) {
            case 'a': {
                automato = InputOutput.criarAutomato("automato.in");
                break;
            }
            case 'b': {
                automato = automato.removerEpsilonTransicoes();
                automato = automato.determinizar();
                automato = automato.obterAutomatoMinimo();

                InputOutput.writeToFile(automato.toString(), "automato.out");
                System.out.println(automato.toString());
                break;
            }
            case 'c': {
                gramatica = automato.transformaEmGramatica();
                InputOutput.writeToFile(gramatica.toString(), "gramatica.out");
                System.out.println(gramatica.toString());

                break;
            }
            case 'd': {
                main(null);
                return;
            }
            default:
                break;
        }
        automato();

    }

    static void expressao() {
        String text = "";
        while (text.length() == 0) {
            text = JOptionPane.showInputDialog(menuExpressao);
        }
        char option = text.charAt(0);

        switch (option) {
            case 'a': {
                expressao = InputOutput.criarExpressao("expressao.in");
                break;
            }
            case 'b': {
                try {
                    automato = expressao.obterAutomato();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                InputOutput.writeToFile(automato.toString(), "automato.out");
                System.out.println(automato.toString());

                break;
            }
            case 'c': {
                main(null);
                return;

            }
            default:
                break;
        }

        expressao();
    }

    static void gramatica() {
        String text = "";
        while (text.length() == 0) {
            text = JOptionPane.showInputDialog(menuGramatica);
        }
        char option = text.charAt(0);

        switch (option) {
            case 'a': {
                gramatica = InputOutput.criarGramatica("gramatica.in");
                break;
            }
            case 'b': {
                automato = gramatica.transformaEmAutomato();
                InputOutput.writeToFile(automato.toString(), "automato.out");
                System.out.println(automato.toString());
                break;
            }
            case 'c': {
                main(null);
                return;
            }
            default:
                break;
        }
        gramatica();

    }

    public static void main2(String[] args) {
        String text = "";
        while (text.length() == 0) {
            text = JOptionPane.showInputDialog(menu);
        }
        char option = text.charAt(0);
        switch (option) {
            case 'a': {
                automato();
                break;
            }
            case 'b': {
                expressao();
                break;
            }
            case 'c': {
                gramatica();
                break;
            }

            default:
                break;
        }

    }

}
