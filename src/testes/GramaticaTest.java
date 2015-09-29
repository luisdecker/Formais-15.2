import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import formais152.Modelo.Gramatica;
import formais152.Modelo.Excecoes.ProducaoMalFormadaException;

/**
 * 
 */

/**
 * @author cesar
 *
 */
public class GramaticaTest {
	
	@Test
	public void deveAdicionarProducoes() {

		Gramatica gr = new Gramatica();

		Assert.assertEquals(true, gr.adicionaProducao("S", "0"));
		Assert.assertEquals(true, gr.adicionaProducao("S", "(A"));
		Assert.assertEquals(true, gr.adicionaProducao("S", "&"));
		Assert.assertEquals(true, gr.adicionaProducao("*S", "e"));
		Assert.assertEquals(true, gr.adicionaProducao("*S", "aA"));

	}
	
	@Test(expected=ProducaoMalFormadaException.class)
	public void cabecaDaProducaoMinuscula(){
		
		Gramatica gr = new Gramatica();

		Assert.assertEquals(true, gr.adicionaProducao("a", "b"));
		
	}
	
	@Test(expected=ProducaoMalFormadaException.class)
	public void cabecaDaProducaoComMaisDeUmSimbolo(){
		
		Gramatica gr = new Gramatica();

		Assert.assertEquals(true, gr.adicionaProducao("Aa", "b"));
		
	}
	
	@Test(expected=ProducaoMalFormadaException.class)
	public void corpoDaProducaoComMaisDeDoisSimbolos(){
		
		Gramatica gr = new Gramatica();

		Assert.assertEquals(true, gr.adicionaProducao("A", "aAa"));
		
	}
	
	@Test(expected=ProducaoMalFormadaException.class)
	public void cabecaDaProducaoComSimboloInicialETresCaracteres(){
		
		Gramatica gr = new Gramatica();

		Assert.assertEquals(true, gr.adicionaProducao("*AA", "a"));
		
	}
	
	@Test(expected=ProducaoMalFormadaException.class)
	public void cabecaDaProducaoComAsteriscoEmOrdemInversa(){
		
		Gramatica gr = new Gramatica();

		Assert.assertEquals(true, gr.adicionaProducao("A*", "a"));
		
	}
	
	@Test(expected=ProducaoMalFormadaException.class)
	public void maisDeUmaProducaoDerivandoEpsilon(){
		
		Gramatica gr = new Gramatica();

		Assert.assertEquals(true, gr.adicionaProducao("A", "&"));
		Assert.assertEquals(true, gr.adicionaProducao("B", "&"));
	}
	


}
