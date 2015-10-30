package formais152.Modelo;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Estado implements Comparable<Estado>, Serializable {

    private static final long serialVersionUID = 3425239595937603665L;

    private String nome;

    private boolean terminal = false;

    private boolean inicial = false;

    private Map<String, List<Estado>> transicoes = new TreeMap<>();

    private List<Estado> epsilonFecho = new ArrayList<>();

    public Estado(String nome) {
        super();
        this.nome = nome;
    }

    public Estado(String nome, boolean terminal) {
        super();
        this.nome = nome;
        this.terminal = terminal;
    }

    public void apagarTransicoes() {
        transicoes.clear();
    }

    public List<Estado> getEpsilonFecho() {
        if (epsilonFecho.isEmpty()) {
            calculaEpsilonFecho();
        }
        return epsilonFecho;
    }

    public void calculaEpsilonFecho() {
        epsilonFecho.add(this);
        List<Estado> fila = new ArrayList<>();
        if (!transicoes.containsKey("&")) {
            return;
        }
        List<Estado> epsilonTrans = transicoes.get("&");
        for (Estado e : epsilonTrans) {
            fila.add(e);
            epsilonFecho.add(e);
        }
        while (!fila.isEmpty()) {
            Estado aVer = fila.remove(0);
            if (!(aVer.transicoes.get("&") == null)) {
                for (Estado e : aVer.transicoes.get("&")) {
                    if (!epsilonFecho.contains(e)) {
                        fila.add(e);
                        epsilonFecho.add(e);
                    }
                }
            }
        }

    }

    public Estado(Estado estado) {
        super();
        this.nome = estado.nome;
        this.terminal = estado.terminal;
        this.inicial = estado.inicial;
        this.transicoes = estado.transicoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    public boolean isInicial() {
        return inicial;
    }

    public void setInicial(boolean inicial) {
        this.inicial = inicial;
    }

    public Map<String, List<Estado>> getTransicoes() {
        return transicoes;
    }

    public void addTransicao(String simbolo, Estado estado) {
        List<Estado> estados = transicoes.get(simbolo);
        if (estados == null) {
            estados = new ArrayList<Estado>();
            estados.add(estado);
            transicoes.put(simbolo, estados);
        } else {
            if (!estados.contains(estado)) {
                estados.add(estado);
            }
        }
    }

    public void remTransicao(String simbolo, Estado estado) {
        if (transicoes.containsKey(simbolo)) {
            transicoes.get(simbolo).remove(estado);
        }
    }

    public List<Estado> transitar(String simbolo) {
        return transicoes.get(simbolo);
    }

    public boolean isDeterministico() {
        for (List<Estado> estados : transicoes.values()) {
            if (estados.size() > 1) {
                return false;
            }
        }
        return true;
    }

    public boolean possuiEpsilonTransicao() {
        return transicoes.containsKey("&");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Estado other = (Estado) obj;
        if (nome == null) {
            if (other.nome != null) {
                return false;
            }
        } else if (!nome.equals(other.nome)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String valor = "{" + (inicial ? "->" : "") + (terminal ? "*" : "") + nome + " = ";
        String sep2 = "";
        for (Entry<String, List<Estado>> entrada : transicoes.entrySet()) {
            String estadosSTR = "";
            String sep = "";
            for (Estado estado : entrada.getValue()) {
                estadosSTR += sep + estado.getNome();
                sep = ", ";
            }
            valor += sep2 + entrada.getKey() + " -> [" + estadosSTR + "]";
            sep2 = ", ";
        }
        return valor + "}";
    }

    @Override
    public int compareTo(Estado o) {
        return nome.compareTo(o.nome);
    }
}
