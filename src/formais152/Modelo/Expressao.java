package formais152.Modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import formais152.Modelo.Excecoes.ParenteseMismatchException;

public class Expressao {

    public String expressao;

    public Expressao(String exp) {
        expressao = exp.trim();

    }

    private String getInicial(Automato a) throws Exception {
        Set<Estado> set = a.getEstados();
        for (Estado e : set) {
            String nome = e.getNome();
            if (e.isInicial()) {
                return e.getNome();
            }
        }
        throw new Exception("sem estado inicial");

    }

    private String createNewEnd(Automato a) throws Exception {
        Set<Estado> set = a.getEstados();
        ArrayList<String> names = new ArrayList<String>();
        Set<Estado> finais = new HashSet<Estado>();

        for (Estado e : set) {
            if (e.isTerminal()) {
                finais.add(e);
            }

            names.add(e.getNome());
        }
        if (set.isEmpty()) {
            throw new Exception("Sem estado final");
        }

        String nomeFinal = "SS0";
        int i = 0;
        while (names.contains(nomeFinal)) {
            i++;
            nomeFinal = "SS";
            nomeFinal += i;
        }

        a.addEstado(nomeFinal);
        a.addEstadoFinal(nomeFinal);

        for (Estado e : finais) {
            a.addTransicao(e.getNome(), "&", nomeFinal);
        }

        return nomeFinal;

    }

    public Automato obterAutomato() throws Exception {
        String express = expressao;
        Automato auto = new Automato();
        String result = "";

        for (int i = 0; i < express.length(); i++) {
            char at = express.charAt(i);
            if (at != ' ') {
                result += at;
            }
        }

        return obterAutomato(result, '\0');
    }

    private boolean isMod(char a) {
        return (a == '+' || a == '*' || a == '?');
    }

    private Automato obterAutomato(String express, char mod) {
        int vn = 0;
        Automato auto = new Automato();
        boolean start = false;
        auto.addEstado("S0");

        try {
            auto.setEstadoInicial("S0");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String lastEstate = "S0";
        String estadoInicial = "S0";

        String estadoFinal = lastEstate;

        for (int i = 0; i < express.length(); i++) {
            char at = express.charAt(i);
            if (at == '(') {
                /**
                 * se encontrar um come�o de uma expressao ele procura o final
                 * dela e chama o metodo recursivamente concatenando o resultado
                 * com esse e passando adiante
                 *
                 * tambem � verificado se tem um modificador depois
                 */
                int end = -1;
                /**
                 * verifica��o de multiplos parenteses
                 *
                 */
                for (int j = i + 1, cont = 1; j < express.length(); j++) {
                    char c = express.charAt(j);
                    if (c == ')') {
                        cont--;
                    }
                    if (cont == 0) {
                        end = j;
                        break;
                    }
                    if (c == '(') {
                        cont++;
                    }
                }
                if (end == -1) {
                    throw new ParenteseMismatchException("Parentese n�o encontrado");
                }

                String sub = express.substring(i + 1, end);

                char submod = '\0';
                if (end < express.length() - 1) {
                    submod = express.charAt(end + 1);
                }

                if (isMod(submod)) {
                    end++;
                } else {
                    submod = 0;
                }

                auto.addEstadoFinal(lastEstate);

                try {
                    auto = auto.concatenacao(obterAutomato(sub, submod));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                end++;
                if (end < express.length()) {
                    if (express.charAt(end) == '|') {
                        sub = express.substring(end + 1);
                        return auto.uniao(obterAutomato(sub, '\0'));

                    } else {
                        sub = express.substring(end);
                        try {
                            return auto.concatenacao(obterAutomato(sub, '\0'));
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

                if (isMod(mod)) {
                    try {
                        estadoInicial = getInicial(auto);
                        estadoFinal = createNewEnd(auto);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                } else {
                    return auto;
                }

            }
            if (at == '|') {
                /**
                 * Caso encontrado um operador | � feito uniao do automato atual
                 * com o restante da expressao
                 */
                String sub = express.substring(i + 1);

                auto.addEstadoFinal(lastEstate);

                auto = auto.uniao(obterAutomato(sub, (char) 0));

                if (isMod(mod)) {
                    try {
                        estadoInicial = getInicial(auto);
                        estadoFinal = createNewEnd(auto);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                } else {
                    return auto;
                }

            }

            /**
             * Verifica-se se ha algum modificador como *,+,? depois do simbolo
             * atual
             */
            char cmod = '\0';
            if (i < express.length() - 1) {
                cmod = express.charAt(i + 1);
            }

            if (isMod(cmod)) {
                i++;
            }

            String estadoAtual = lastEstate;
            vn++;
            String estadoTransicao = "S" + vn;

            auto.addEstado(estadoTransicao);
            String producao = "";
            producao += at;

            lastEstate = estadoTransicao;

            try {
                switch (cmod) {
                    case '+': {
                        auto.addTransicao(estadoAtual, producao, estadoTransicao);
                        auto.addTransicao(estadoTransicao, producao, estadoTransicao);
                        break;
                    }
                    case '*': {
                        auto.addTransicao(estadoAtual, "&", estadoTransicao);
                        auto.addTransicao(estadoTransicao, producao, estadoTransicao);
                        break;
                    }
                    case '?': {
                        auto.addTransicao(estadoAtual, producao, estadoTransicao);
                        auto.addTransicao(estadoAtual, "&", estadoTransicao);
                        break;
                    }

                    default: {
                        auto.addTransicao(estadoAtual, producao, estadoTransicao);
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            estadoFinal = lastEstate;
        }

        /**
         * Agora lidando com algum modificador que influencia em toda expressao
         *
         */
        try {
            auto.addEstadoFinal(lastEstate);

            switch (mod) {
                case '+': {
                    auto.addTransicao(estadoFinal, "&", estadoInicial);
                    break;
                }
                case '*': {
                    auto.addTransicao(estadoInicial, "&", estadoFinal);
                    auto.addTransicao(estadoFinal, "&", estadoInicial);
                    break;
                }
                case '?': {
                    auto.addTransicao(estadoInicial, "&", estadoFinal);
                    break;
                }

                default: {

                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return auto;
    }
}
