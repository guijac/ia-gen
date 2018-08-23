package br.usp.ia.main;

import com.github.daentech.CVRP;

import br.usp.ia.funcoes.*;
import br.usp.ia.geneticos.*;
import br.usp.ia.util.GravadorArquivo;
import br.usp.ia.util.OperacoesEmGeneticos; 
public class ExecucaoGeneticos {

	/*
	 * 
	 * EP - IA - Otimizacao de funcoes com algoritmos geneticos
	 * 
	 * Autores:
	 * Decio Júnior n.º USP: 7693880
	 * Guilherme Jorge n.º USP: 7712229
	 * Victor Miguel F. Rodrigues n.º USP: 7136752
	 * 
	 * 
	 * A execucao do programa espera os seguintes parametros de entrada:
	 * 	
	 *  0. Funcao a ser otimizada;
	 * 	Porcentagens para execucao de parametros:
	 * 		1. Taxa de mutacao
	 * 		2. Taxa de crossover;
	 *  3. Numero de geracoes
	 *  4. Numero de cromossomos na populacao
	 *  
	 * Sendo assim uma chamada neste formato: java ExecucaoGeneticos A 0.03 0.50 150 50
	 *  
	 */ 
	public static void main(String[] args) {
		
		FuncaoFitness funcaoExecutada;
		GravadorArquivo gravadorArquivo = new GravadorArquivo();
		OperacoesEmGeneticos operacaoEmGeneticos = new OperacoesEmGeneticos();
		
		if (args.length == 5){

			try {
				AlgoritmoGeneticoController.NOME_FUNCAO = String.valueOf(args[0]);
				AlgoritmoGeneticoController.TAXA_MUTACAO = Float.valueOf(args[1]);
				AlgoritmoGeneticoController.TAXA_CROSSOVER = Float.valueOf(args[2]);
				AlgoritmoGeneticoController.MAX_GERACOES = Integer.valueOf(args[3]);
				AlgoritmoGeneticoController.TAMANHO_POPULACAO = Integer.valueOf(args[4]);
			} catch (Exception e) {
				System.err.println("O valor do parametro 0 deve ser do tipo caractere!");
				System.err.println("Os valores dos parametros 1, 2, 3 e 4 devem ser do tipo numerico!");
			}

			/*
			 * Funcoes validas:
			 * 	1. "G" - Gold
			 * 	2. "B" - Bump
			 *  3. "R" - Rastrigin
			 *  
			 *  4. "C" - CVRP (Capacitated Vehicle Routing Problem)
			 */
			switch (args[0]) {
			
			case "B":
				
				funcaoExecutada = new Bump();
				gravadorArquivo.criarArquivo(funcaoExecutada.getNomeArquivo());
				int geracaoBump = 0;
				
				// Criar uma populacao inicial
				Populacao populacaoBumpInicial = new Populacao(AlgoritmoGeneticoController.TAMANHO_POPULACAO
						, funcaoExecutada.getMin(), funcaoExecutada.getMax());
				
				while (geracaoBump <= AlgoritmoGeneticoController.MAX_GERACOES){
					
					int index = 0;
					for (Individuo individuo : populacaoBumpInicial.getPopulacao()) {
						individuo.setFitness(funcaoExecutada.calcularFitness(individuo.getX(), individuo.getY()));
						populacaoBumpInicial.getPopulacao().set(index, individuo);
						index++;						
					}
					
					/*
					 *  Ordernar a populacao, mantendo o melhor fitness sempre a frente, 
					 *  respeitando o objetivo da funcao (maximizar ou minimizar)
					 */		
					populacaoBumpInicial.ordenarPopulacao(funcaoExecutada.getObjetivo());
					
					gravadorArquivo.gravarArquivo(populacaoBumpInicial, geracaoBump, funcaoExecutada.getNomeArquivo());
					geracaoBump++;
					
					populacaoBumpInicial = operacaoEmGeneticos.novaGeracao(populacaoBumpInicial, false, funcaoExecutada);

				}
				
				break;
				
			case "G":
				
				funcaoExecutada = new Gold();
				gravadorArquivo.criarArquivo(funcaoExecutada.getNomeArquivo());
				int geracaoGold = 0;
				
				// Criar uma populacao inicial
				Populacao populacaoGoldInicial = new Populacao(AlgoritmoGeneticoController.TAMANHO_POPULACAO
						, funcaoExecutada.getMin(), funcaoExecutada.getMax());
				
				while (geracaoGold <= AlgoritmoGeneticoController.MAX_GERACOES){
					
					int index = 0;
					for (Individuo individuo : populacaoGoldInicial.getPopulacao()) {
						individuo.setFitness(funcaoExecutada.calcularFitness(individuo.getX(), individuo.getY()));
						populacaoGoldInicial.getPopulacao().set(index, individuo);
						index++;						
					}
					
					/*
					 *  Ordernar a populacao, mantendo o melhor fitness sempre a frente, 
					 *  respeitando o objetivo da funcao (maximizar ou minimizar)
					 */		
					populacaoGoldInicial.ordenarPopulacao(funcaoExecutada.getObjetivo());
					
					gravadorArquivo.gravarArquivo(populacaoGoldInicial, geracaoGold, funcaoExecutada.getNomeArquivo());
					geracaoGold++;
					
					populacaoGoldInicial = operacaoEmGeneticos.novaGeracao(populacaoGoldInicial, false, funcaoExecutada);

				}
				
				break;
				
			case "R":
				
				funcaoExecutada = new Rastrigin();
				gravadorArquivo.criarArquivo(funcaoExecutada.getNomeArquivo());
				int geracaoRastring = 0;
				
				// Criar uma populacao inicial
				Populacao populacaoRastringInicial = new Populacao(AlgoritmoGeneticoController.TAMANHO_POPULACAO
						, funcaoExecutada.getMin(), funcaoExecutada.getMax());
				
				while (geracaoRastring <= AlgoritmoGeneticoController.MAX_GERACOES){
					
					int index = 0;
					for (Individuo individuo : populacaoRastringInicial.getPopulacao()) {
						individuo.setFitness(funcaoExecutada.calcularFitness(individuo.getX(), individuo.getY()));
						populacaoRastringInicial.getPopulacao().set(index, individuo);
						index++;						
					}
					
					/*
					 *  Ordernar a populacao, mantendo o melhor fitness sempre a frente, 
					 *  respeitando o objetivo da funcao (maximizar ou minimizar)
					 */					
					populacaoRastringInicial.ordenarPopulacao(funcaoExecutada.getObjetivo());
					
					gravadorArquivo.gravarArquivo(populacaoRastringInicial, geracaoRastring, funcaoExecutada.getNomeArquivo());
					geracaoRastring++;
					
					populacaoRastringInicial = operacaoEmGeneticos.novaGeracao(populacaoRastringInicial, false, funcaoExecutada);

				}
				
				break;
				

			case "C":
				
				/*
				 * A implementacao do algoritmo CVRP foi baseada nos fontes de GILBERT, Dan e 
				 * DUMITRAS, Ana Disponiveis em 
				 * <https://github.com/daentech/CVRP>
				 * <https://github.com/an4/CVRP>
				 * 
				 * Os valores passados na execucao do programa serao mantidos, portanto 
				 * para executar o CRVP a chamada deve ter o formato: java ExecucaoGeneticos C 0.03 0.5 200 200
				 * 
				 * */
				
				// Determinando que execute com operadores "classicos"
				AlgoritmoGeneticoController.IS_SELECAO_TORNEIO = false;
				AlgoritmoGeneticoController.IS_CROSSOVER_CLASSICO = false;
				AlgoritmoGeneticoController.IS_MUTACAO_INSERTION = false;

				// Determinando que execute com operadores "orientados"
				AlgoritmoGeneticoController.IS_SELECAO_ROLETA = true;
				AlgoritmoGeneticoController.IS_CROSSOVER_PMX = true;
				AlgoritmoGeneticoController.IS_MUTACAO_SWAP = true;
				
				// Definindo o arquivo a ser gravado
				String nomeArquivo = "SetP2G" + ".txt";
				
				CVRP cvrp = new CVRP();
				cvrp.executarCVRP(nomeArquivo);
				
				break;
				
			default:
				System.out.println("Funcao invalida!");				
			}
			
		}else{
			System.out.println("Comando para execucao invalido! "
					+ "\nA chamada deve ser no formato formato: java ExecucaoGeneticos A 0.03 0.5 200 200");
		}

	}

}