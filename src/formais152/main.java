/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formais152;

import javax.swing.JOptionPane;

import formais152.Modelo.Automato;
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

    static void automato() {
        String text = "";
        while (text.length() == 0) {
            text = JOptionPane.showInputDialog(menuAutomato);
        }
        char option = text.charAt(0);

        switch (option) {
            case 'a': {
                automato = InputOutput.criarAutomato("/home/decker/formais/automato.in");
                System.out.println(automato.toString());
                break;
            }
            case 'b': {
                automato = automato.removerEpsilonTransicoes();
                automato = automato.determinizar();

                InputOutput.writeToFile(automato.toString(), "/home/decker/formais/automato.out");
                System.out.println("\n\n\n" + automato.toString());
                break;
            }
            case 'c': {
                gramatica = automato.transformaEmGramatica();
                InputOutput.writeToFile(gramatica.toString(), "/home/decker/formais/gramatica.out");
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
                expressao = InputOutput.criarExpressao("/home/decker/formais/expressao.in");
                break;
            }
            case 'b': {
                try {
                    automato = expressao.obterAutomato();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                InputOutput.writeToFile(automato.toString(), "/home/decker/formais/automato.out");
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
                gramatica = InputOutput.criarGramatica("/home/decker/formais/gramatica.in");
                break;
            }
            case 'b': {
                automato = gramatica.transformaEmAutomato();
                InputOutput.writeToFile(automato.toString(), "/home/decker/formais/automato.out");
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

    public static void main(String[] args) {
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
