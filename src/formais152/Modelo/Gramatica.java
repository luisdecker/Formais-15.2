/**
 * 
 */
package formais152.Modelo;

import java.util.HashMap;
import java.util.HashSet;
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

	public void setSimboloInicial(String naoTerminal) {
		simboloInicial = naoTerminal;
	}

	public boolean adicionaProducao(String naoTerminal, String producao) {

		if (naoTerminal.length() == 1
				&& Character.isUpperCase(naoTerminal.charAt(0))) {

			if (producao.equals("&")) {

				if (simbolosTerminais.contains(producao)) {
					throw new ProducaoMalFormadaException(
							"Já existe uma produção que deriva epsilon");
				}
				
				if(existeProducaoADireita(naoTerminal)){
					throw new SimboloQueProduzEpsilonADireitaException("Já existe uma produção com símbolo "+naoTerminal+ "a direita");
				}
				registraProducao(naoTerminal, producao);

				setSimboloInicial(naoTerminal);

				return true;

			} else if (producao.length() == 1
					&& Character.isLowerCase(producao.charAt(0))) {
				registraProducao(naoTerminal, producao);
				return true;

			} else if (producao.length() == 2
					&& Character.isLowerCase(producao.charAt(0))
					&& Character.isUpperCase(producao.charAt(1))) {

				if (simbolosTerminais.contains(producao) && simboloInicial.equals(producao.substring(1, 2))) {
					throw new SimboloQueProduzEpsilonADireitaException(
							"A produção contém o símbolo que deriva epsilon à direita");
				}

				registraProducao(naoTerminal, producao);
				return true;
			}

		}

		throw new ProducaoMalFormadaException("Produção inválida");

	}

	private void registraProducao(String naoTerminal, String producao) {

		if (!producoes.containsKey(naoTerminal)) {
			HashSet<String> cadeia = new HashSet<String>();
			cadeia.add(producao);
			producoes.put(naoTerminal, cadeia);
			simbolosNaoTerminais.add(naoTerminal);
		} else {

			producoes.get(naoTerminal).add(producao);
		}

		if (producao.length() == 1) {

			simbolosTerminais.add(producao);

		} else {

			simbolosTerminais.add(producao.substring(0, 1));

		}

	}

	public Automato transformaEmAutomato() {
		Automato automato = new Automato();

		for (String c : simbolosNaoTerminais) {
			automato.addEstado(c);
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
				} else {
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

			res += nt + " -> ";

			for (String t : transicoes) {
				res += t + " | ";
			}

			res += "\n";
		}

		return res;
	}

}
