/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formais152;

import formais152.Modelo.Automato;
import formais152.Modelo.Gramatica;

/**
 *
 * @author decker
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       /* try {
            System.out.println("AAAAAAAAA");
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
        } catch (Exception e) {

        }*/
        
            
            
            
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
		
		
		
  //       System.out.println(aut2);
//		System.out.println(gr);
//		
//		Automato aut2 = gr.transformaEmAutomato();
//		
//		System.out.println(aut2);
//		
//		System.out.println(aut2.determinizar());
    }

}
