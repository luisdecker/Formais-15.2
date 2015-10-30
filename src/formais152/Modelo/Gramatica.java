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

    public Gramatica() {
        producoes = new HashMap<String, HashSet<String>>();
        simbolosNaoTerminais = new HashSet<String>();
        simbolosTerminais = new HashSet<String>();
    }

    public String getSimboloInicial() {
        return simboloInicial;
    }

    public void setSimboloInicial(String cabecaProducao) {
        simboloInicial = cabecaProducao;
    }

    public void retiraSimboloInicialDasProducoesADireita() {

        if (simbolosTerminais.contains("&")) {
            HashSet<String> producoesIniciais = new HashSet<String>(
                    producoes.get(simboloInicial));

            producoesIniciais.remove("&");
            String cabecaNovaProducao = produzNovoSimbolo();

            for (String prod : producoesIniciais) {
                adicionaProducao(cabecaNovaProducao, prod);
            }

            for (String nt : simbolosNaoTerminais) {
                ArrayList<String> transicoes = new ArrayList<String>(
                        producoes.get(nt));

                for (String t : transicoes) {
                    if (t.length() == 2 && t.substring(1, 2).equals(simboloInicial)) {

                        String novaProducao = t.substring(0, 1)
                                + cabecaNovaProducao;

                        producoes.get(nt).remove(t);
                        producoes.get(nt).add(novaProducao);

                    }
                }

            }
        }

    }

    private String formataCabecaProducao(String cabeca) {

        if (cabeca.length() == 1 && Character.isUpperCase(cabeca.charAt(0))) {
            return cabeca;
        } else if (cabeca.length() == 2 && cabeca.substring(0, 1).equals("*")
                && Character.isUpperCase(cabeca.charAt(1))) {

            cabeca = cabeca.substring(1, 2);

            setSimboloInicial(cabeca);

            return cabeca;
        }

        return null;

    }

    private boolean corpoDaProducaoEhValido(String corpo) {

        if (corpo.equals("&") && simbolosTerminais.contains(corpo)) {
            throw new ProducaoMalFormadaException(
                    "Já existe uma produção que deriva epsilon");
        }

        return (corpo.length() == 1 && !Character.isUpperCase(corpo.charAt(0)))
                || (corpo.length() == 2
                && !Character.isUpperCase(corpo.charAt(0)) && Character
                .isUpperCase(corpo.charAt(1)));

    }

    public boolean adicionaProducao(String cabecaProducao, String corpoProducao) {

        cabecaProducao = formataCabecaProducao(cabecaProducao);

        if (cabecaProducao != null && corpoDaProducaoEhValido(corpoProducao)) {

            registraProducao(cabecaProducao, corpoProducao);

            return true;
        }

        throw new ProducaoMalFormadaException("Produção " + cabecaProducao
                + " -> " + corpoProducao + " inválida");

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

        if (producoes.get(simboloInicial).contains("&")) {
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
                } else if (t.length() == 2) {
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

    private boolean existeProducaoADireita(String simbolo) {

        Set<String> naoTerminais = producoes.keySet();

        for (String nt : naoTerminais) {
            HashSet<String> transicoes = producoes.get(nt);

            for (String t : transicoes) {
                if (t.length() == 2 && t.substring(1, 2).equals(simbolo)) {
                    return true;
                }
            }

        }

        return false;

    }

    public String produzNovoSimbolo() {
        char novoSimbolo = 'A';
        boolean encontrouNovo = false;

        while (!encontrouNovo) {

            if (!simbolosNaoTerminais.contains(String.valueOf(novoSimbolo))) {
                encontrouNovo = true;
            } else {
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

            res += (nt == getSimboloInicial()) ? "*" + nt + " -> " : nt
                    + " -> ";

            for (String t : transicoes) {
                res += t + " | ";
            }

            res += "\n";
        }

        return res;
    }

}
