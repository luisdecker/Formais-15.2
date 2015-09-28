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
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		Automato aut = new Automato();
		
		/**testes rápidos que eu fiz, vou passar pro JUnit, pode ser apagado*/
		

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
		
		Gramatica gr = new Gramatica();
		
		gr.adicionaProducao("S", "aA");
		gr.adicionaProducao("S", "&");
		gr.adicionaProducao("A", "aS");
		gr.adicionaProducao("A", "a");
		gr.retiraSimboloInicialDasProducoesADireita();
		
//		System.out.println(gr);
//		
//		Automato aut2 = gr.transformaEmAutomato();
//		
//		System.out.println(aut2);
//		
//		System.out.println(aut2.determinizar());

	}

}
