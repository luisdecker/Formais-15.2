import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import formais152.Modelo.Gramatica;
import formais152.Modelo.Excecoes.ProducaoMalFormadaException;
import formais152.Modelo.Excecoes.SimboloQueProduzEpsilonADireitaException;

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
	public void maisDeUmaProducaoDerivandoEpsilon(){
		
		Gramatica gr = new Gramatica();

		Assert.assertEquals(true, gr.adicionaProducao("A", "&"));
		Assert.assertEquals(true, gr.adicionaProducao("B", "&"));
	}
	


}
