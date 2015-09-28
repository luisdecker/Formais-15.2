/**
 * 
 */
package formais152.Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import formais152.Modelo.Excecoes.*;

/**
 * @author cesar
 *
 */
public class Gramatica {

	private HashMap<String, HashSet<String>> producoes;
	private HashSet<String> simbolosTerminais;
	private HashSet<String> simbolosNaoTerminais;
	private String simboloInicial;
	
	public Gramatica(){
		producoes = new HashMap<String, HashSet<String>>();
		simbolosNaoTerminais = new HashSet<String>();
		simbolosTerminais = new HashSet<String>();
	}
	
	public String getSimboloInicial(){
		return simboloInicial;
	}

	public void setSimboloInicial(String cabecaProducao) {
		simboloInicial = cabecaProducao;
	}
	
	public void retiraSimboloInicialDasProducoesADireita(){
		
		HashSet<String> producoesIniciais = new HashSet<String>(producoes.get(simboloInicial));
		
		producoesIniciais.remove("&");
		String cabecaNovaProducao = produzNovoSimbolo();
		
		for(String prod : producoesIniciais){
			adicionaProducao(cabecaNovaProducao, prod);
		}
		
		for (String nt : simbolosNaoTerminais) {
			ArrayList<String> transicoes = new ArrayList<String>(producoes.get(nt));

			for (String t : transicoes) {
				if(t.length()==2 && t.substring(1, 2).equals(simboloInicial)){
					
					String novaProducao = t.substring(0, 1) + cabecaNovaProducao;
					
					producoes.get(nt).remove(t);
					producoes.get(nt).add(novaProducao);
					
				}
			}

		}
		
	}
	

	public boolean adicionaProducao(String cabecaProducao, String corpoProducao) {

		if (cabecaProducao.length() == 1
				&& Character.isUpperCase(cabecaProducao.charAt(0))) {

			if (corpoProducao.equals("&")) {

				if (simbolosTerminais.contains(corpoProducao)) {
					throw new ProducaoMalFormadaException(
							"Já existe uma produção que deriva epsilon");
				}
				
				registraProducao(cabecaProducao, corpoProducao);

				setSimboloInicial(cabecaProducao);

				return true;

			} else if (corpoProducao.length() == 1
					&& !Character.isUpperCase(corpoProducao.charAt(0))) {
				registraProducao(cabecaProducao, corpoProducao);

				return true;

			} else if (corpoProducao.length() == 2
					&& !Character.isUpperCase(corpoProducao.charAt(0))
					&& Character.isUpperCase(corpoProducao.charAt(1))) {

				registraProducao(cabecaProducao, corpoProducao);
				return true;
			}

		}

		throw new ProducaoMalFormadaException("Produção inválida");

	}

	private void registraProducao(String cabecaProducao, String corpoProducao) {

		if (!producoes.containsKey(cabecaProducao)) {
			HashSet<String> cadeia = new HashSet<String>();
			cadeia.add(corpoProducao);
			producoes.put(cabecaProducao, cadeia);
			simbolosNaoTerminais.add(cabecaProducao);
		} else {

			producoes.get(cabecaProducao).add(corpoProducao);
		}

		if (corpoProducao.length() == 1) {

			simbolosTerminais.add(corpoProducao);

		} else {

			simbolosTerminais.add(corpoProducao.substring(0, 1));

		}

	}

	public Automato transformaEmAutomato() {
		Automato automato = new Automato();

		for (String c : simbolosNaoTerminais) {
			automato.addEstado(c);
		}
		try {
			automato.setEstadoInicial(getSimboloInicial());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		automato.addEstadoFinal("qFinal");
		
		if(producoes.get(simboloInicial).contains("&")){
			automato.addEstadoFinal(simboloInicial);
		}

		Set<String> naoTerminais = producoes.keySet();

		for (String nt : naoTerminais) {
			HashSet<String> transicoes = producoes.get(nt);

			for (String t : transicoes) {
				if (t.length() == 1 && !t.equals("&")) {
					try {
						automato.addTransicao(nt, t, "qFinal");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(t.length() == 2){
					try {
						automato.addTransicao(nt, t.substring(0, 1),
								t.substring(1, 2));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		return automato;
	}
	
	private boolean existeProducaoADireita(String simbolo){
		
		Set<String> naoTerminais = producoes.keySet();

		for (String nt : naoTerminais) {
			HashSet<String> transicoes = producoes.get(nt);

			for (String t : transicoes) {
				if(t.length()==2 && t.substring(1, 2).equals(simbolo)){
					return true;
				}
			}

		}
		
		return false;
		
	}
	
	public String produzNovoSimbolo(){
		char novoSimbolo = 'A';
		boolean encontrouNovo = false;
		
		while(!encontrouNovo){		
			
			if(!simbolosNaoTerminais.contains(String.valueOf(novoSimbolo))){
				encontrouNovo = true;
			}else{
				novoSimbolo++;
			}	
			
		}
		
		return String.valueOf(novoSimbolo);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		String res = "";

		Set<String> naoTerminais = producoes.keySet();

		for (String nt : naoTerminais) {
			HashSet<String> transicoes = producoes.get(nt);

			res += (nt == getSimboloInicial()) ? "*"+nt + " -> " : nt + " -> ";

			for (String t : transicoes) {
				res += t + " | ";
			}

			res += "\n";
		}

		return res;
	}

}
