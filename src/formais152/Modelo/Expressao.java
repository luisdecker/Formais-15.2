package formais152.Modelo;

import java.util.Map;

public class Expressao {

	public String expressao;

	public Expressao(String exp) {
		expressao = exp.trim();

	}

	public Automato obterAutomato() {
		String express = expressao;
		Automato auto = new Automato();
		String result = "(";
		for (int i = 0; i < express.length(); i++) {
			char at = express.charAt(i);
			if (at == '|') {
				result += ')';
				result += at;
				result += '(';
			} else {
				if (at == '(') {
					result += '(';
				}
				if (at == ')') {
					result += ')';
				}
				if (at != ' ')
					result += at;
			}
		}
		result += ')';
		System.out.println(result);
		return auto;
	}

	private boolean isMod(char a) {
		return (a == '+' || a == '*' || a == '?');
	}

	public Automato obterAutomato(String express, char mod) {
		int vn = 0;
		Automato auto = new Automato();
		boolean start = false;

		for (int i = 0; i < express.length(); i++) {
			char at = express.charAt(i);
			if (at == '(') {
				/**se encontrar um começo de uma expressao
				 * ele procura o final dela e chama o metodo recursivamente
				 * concatenando o resultado com esse e passando adiante
				 * 
				 * tambem é verificado se tem um modificador depois
				 */
				
				int end = express.indexOf(')', i);
				if (end == -1) {
					System.exit(-1);
				}

				String sub = express.substring(i + 1, end);
				char submod = express.charAt(i + 1);

				if (isMod(submod)) {
					end++;
				} else {
					submod = 0;
				}

				auto = auto.concatenacao(obterAutomato(sub, submod));
				i = end - 1;
				continue;
			}
			if (at == '|') {
				String sub = express.substring(i + 1);

				char submod = express.charAt(i + 1);

				auto = auto.uniao(obterAutomato(sub, (char) 0));
			}

		}

		return auto;
	}
}
