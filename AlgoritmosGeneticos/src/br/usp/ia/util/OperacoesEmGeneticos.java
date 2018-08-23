package br.usp.ia.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import br.usp.ia.funcoes.FuncaoFitness;
import br.usp.ia.geneticos.AlgoritmoGeneticoController;
import br.usp.ia.geneticos.Individuo;
import br.usp.ia.geneticos.Populacao;

public class OperacoesEmGeneticos {

	/*
	 * Realiza uma mutação simples neste individuo. A mutação consiste em
	 * escolher um gene aleatório no cromossomo, e substitui-lo (também de forma
	 * aleatória) por outro valor (0 ou 1)
	 */

	public static Individuo mutacaoSimples(Individuo individuo) {

		Random r = new Random();
		char[] cromossomo = individuo.getCromossomoBinario().toCharArray();
		int gene = r.nextInt(cromossomo.length - 1);
		int valorGene = r.nextInt(2);
		cromossomo[gene] = String.valueOf(valorGene).toCharArray()[0];
		individuo.setCromossomoBinario(String.valueOf(cromossomo));
		
		ValoresFuncao valoresFuncao = quebrarCromossomoEmGenes(String.valueOf(cromossomo));
		
		individuo.setX(valoresFuncao.getX());
		individuo.setY(valoresFuncao.getY());

		return individuo;
		
	}

	/*
	 * 
	 * Realiza o crossover de dois individuos a partir de um ponto de corte
	 * escolhido aleatoriamente.
	 * 
	 * Baseado no fonte de COLLARES, Paulo Disponivel em 
	 * <http://www.paulocollares.com.br/2013/05/algoritimo-genetico-classico-em-java-hello-world/>
	 * 
	 */
    private ArrayList<Individuo> crossover(Individuo individuo1, Individuo individuo2) {

        Random r = new Random();

        // Sorteia o ponto de corte
        int pontoCorte1 = r.nextInt((individuo1.getCromossomoBinario().length()/2) -2) + 1;
        int pontoCorte2 = r.nextInt((individuo1.getCromossomoBinario().length()/2) -2) 
        		+ individuo1.getCromossomoBinario().length()/2;

        ArrayList<Individuo> listaFilhos = new ArrayList<Individuo>();

        // Obtem os genes dos pais
        String genePai1 = individuo1.getCromossomoBinario();
        String genePai2 = individuo2.getCromossomoBinario();

        String cromossomoFilho1;
        String cromossomoFilho2;
        Individuo filho1 = new Individuo();
        Individuo filho2 = new Individuo();
        
        // Realiza o corte
        cromossomoFilho1 = genePai1.substring(0, pontoCorte1);
        cromossomoFilho1 += genePai2.substring(pontoCorte1, pontoCorte2);
        cromossomoFilho1 += genePai1.substring(pontoCorte2, genePai1.length());
        
        cromossomoFilho2 = genePai2.substring(0, pontoCorte1);
        cromossomoFilho2 += genePai1.substring(pontoCorte1, pontoCorte2);
        cromossomoFilho2 += genePai2.substring(pontoCorte2, genePai2.length());

        filho1.setCromossomoBinario(cromossomoFilho1);
        filho2.setCromossomoBinario(cromossomoFilho2);
        
        // Transformar os cromossomos cruzados em decimais para posterior calculo
		ValoresFuncao valoresFuncaoFilho1 = quebrarCromossomoEmGenes(String.valueOf(cromossomoFilho1));
		
		filho1.setX(valoresFuncaoFilho1.getX());
		filho1.setY(valoresFuncaoFilho1.getY());
        
		ValoresFuncao valoresFuncaoFilho2 = quebrarCromossomoEmGenes(String.valueOf(cromossomoFilho1));
		
		filho2.setX(valoresFuncaoFilho2.getX());
		filho2.setY(valoresFuncaoFilho2.getY());
		
        // Cria os novos indivíduos com os genes dos pais
        listaFilhos.add(filho1);
        listaFilhos.add(filho2);

        return listaFilhos;
    	
    }
    
	/*
	 * 
	 * Mesclar os genes gerados, criando assim a representacao do cromossomo do problema. 
	 * Cada numero decimal e' representado por uma String de 32 bits, logo, nosso cromossomo 
	 * sera representado pela uniao de ambos, formando assim uma String de 64 bits.
	 * 
	 */
	public static String mesclarGenes (String geneEmBinario1, String geneEmBinario2){
		return geneEmBinario1 + geneEmBinario2;
	}
	
	/*
	 * 
	 * Realiza a "quebra" do cromossomo binario para representar dois numeros decimais
	 * O cromossomo e' uma String de 64 bits, com um numero sendo representado de 0 a 31 
	 * e outro de 32 a 64
	 */
	public static ValoresFuncao quebrarCromossomoEmGenes(String cromossomo){
		String geneEmBinario1 = cromossomo.substring(0, 31);
		String geneEmBinario2 = cromossomo.substring(32, 64);
		
		ValoresFuncao valoresFuncao = new ValoresFuncao();
		valoresFuncao.setX(Conversor.converterBinarioParaDecimal(geneEmBinario1));
		valoresFuncao.setY(Conversor.converterBinarioParaDecimal(geneEmBinario2));
		
		return valoresFuncao;
		
	}
	
	/*
	 * 
	 * Cria uma nova geracao da populacao a partir de uma determinada origem
	 * 
	 * Baseado no fonte de COLLARES, Paulo Disponivel em 
	 * <http://www.paulocollares.com.br/2013/05/algoritimo-genetico-classico-em-java-hello-world/>
	 * 
	 */
	public Populacao novaGeracao(Populacao populacao, Boolean elitismo, FuncaoFitness funcao){
        Random r = new Random();
        //nova população do mesmo tamanho da antiga
        Populacao novaPopulacao = new Populacao(populacao.getTamanhoPopulacao());

        //se tiver elitismo, mantém o melhor indivíduo da geração atual
        if (elitismo) {
            novaPopulacao.setIndividuo(populacao.getPopulacao().get(0));
        }

        //insere novos indivíduos na nova população, até atingir o tamanho máximo
        while (novaPopulacao.getPopulacao().size() < novaPopulacao.getTamanhoPopulacao()) {
            //seleciona os 2 pais por torneio
        	ArrayList<Individuo> listaIndividuosPais = selecaoPorTorneio(populacao);

        	ArrayList<Individuo> listaIndividuosFilhos =  new ArrayList<Individuo>();

            /*
             * verifica a taxa de crossover, se sim realiza o crossover, se não, 
             * mantém os pais selecionados para a próxima geração
             */
            if (r.nextDouble() <= AlgoritmoGeneticoController.TAXA_CROSSOVER) {
            	listaIndividuosFilhos.addAll(this.crossover(listaIndividuosPais.get(0), listaIndividuosPais.get(1)));
            	if (!isIntervaloValido(listaIndividuosFilhos.get(0), funcao)){
            		listaIndividuosFilhos.remove(listaIndividuosFilhos.get(0));
                	listaIndividuosFilhos.add(0, listaIndividuosPais.get(0));
            	}
        		if (!isIntervaloValido(listaIndividuosFilhos.get(1), funcao)){
            		listaIndividuosFilhos.remove(listaIndividuosFilhos.get(1));
                	listaIndividuosFilhos.add(1, listaIndividuosPais.get(1));
            	}
            }
            
            /*
             * verifica a taxa de mutacao, se sim realiza a mutacao, se não, 
             * mantém os pais selecionados para a próxima geração
             */
        	if (r.nextDouble() <= AlgoritmoGeneticoController.TAXA_MUTACAO) {
            	listaIndividuosFilhos.add(OperacoesEmGeneticos.mutacaoSimples(listaIndividuosPais.get(0)));
            	listaIndividuosFilhos.add(OperacoesEmGeneticos.mutacaoSimples(listaIndividuosPais.get(1)));
            	if (!isIntervaloValido(listaIndividuosFilhos.get(0), funcao)){
            		listaIndividuosFilhos.remove(listaIndividuosFilhos.get(0));
                	listaIndividuosFilhos.add(0, listaIndividuosPais.get(0));
            	}
        		if (!isIntervaloValido(listaIndividuosFilhos.get(1), funcao)){
            		listaIndividuosFilhos.remove(listaIndividuosFilhos.get(1));
                	listaIndividuosFilhos.add(1, listaIndividuosPais.get(1));
            	}
        	}

        	if (!listaIndividuosFilhos.isEmpty()){
                //adiciona os filhos na nova geração
                novaPopulacao.setIndividuo(listaIndividuosFilhos.get(0));
                novaPopulacao.setIndividuo(listaIndividuosFilhos.get(1));
        	}

        }
        
        return novaPopulacao;
	}
	
	/*
	 * Realiza a selecao dos dois melhores cromossomos pai de uma determinada amostra de individuos
	 * 
	 * Baseado no fonte de COLLARES, Paulo Disponivel em 
	 * <http://www.paulocollares.com.br/2013/05/algoritimo-genetico-classico-em-java-hello-world/>
	 */
	private ArrayList<Individuo> selecaoPorTorneio(Populacao populacao) {

		ArrayList<Individuo> listaAmostraIndividuos = new ArrayList<Individuo>();
		ArrayList<Individuo> listaIndividuosSelecionados = new ArrayList<Individuo>();
		
		// Embaralhar a lista da populacao para selecionar 3 pais aleatoriamente
		Collections.shuffle(populacao.getPopulacao());
		
		// Atribuir 3 pais na lista
		for (int i = 0; i < 3; i++) {
			listaAmostraIndividuos.add(populacao.getPopulacao().get(i));
		}
		
		// Ordenar do melhor para o pior
		Collections.sort(listaAmostraIndividuos);
		
		// Recupera os dois melhores pais apos a ordenacao
		listaIndividuosSelecionados.add(listaAmostraIndividuos.get(0));
		listaIndividuosSelecionados.add(listaAmostraIndividuos.get(1));
		
		return listaIndividuosSelecionados;
    }
	
	/*
	 * 
	 * Valida se o individuo gerado pela mutacao ou crossover e valido dentro do
	 * intervalo da funcao executada, ou seja, trata as possiveis infactibilidades
	 * 
	 */
	private boolean isIntervaloValido(Individuo individuo, FuncaoFitness funcao){
		if ((individuo.getX() >= funcao.getMin() &&
				individuo.getX() <= funcao.getMax()) &&
				(individuo.getY() >= funcao.getMin() &&
				individuo.getY() <= funcao.getMax())){
			return true;
		}else{
			return false;
		}
	}
}
