package br.usp.ia.util; 

import br.usp.ia.geneticos.AlgoritmoGeneticoController;
import br.usp.ia.geneticos.CromossomoCVRP;
import br.usp.ia.geneticos.Individuo;
import br.usp.ia.geneticos.Populacao;
import br.usp.ia.geneticos.PopulacaoCVRP;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class GravadorArquivo {

	public void criarArquivo(String nomeArquivo){
		FileWriter arquivo;
		try {
			arquivo = new FileWriter(nomeArquivo);
		} catch (IOException e) {
			System.err.println("Erro na gravacao do arquivo! " + e.getMessage());
		}

	}

	public void gravarArquivo(Populacao populacao, int geracaoAtual, String nomeArquivo) {

		ArrayList<Individuo> listaIndividuos = populacao.getPopulacao();
		
		// Garantir a ordenacao no arquivo
		Collections.sort(listaIndividuos);

		FileWriter arquivo;
		try {
			arquivo = new FileWriter(nomeArquivo, true);

			PrintWriter gravarArquivo = new PrintWriter(arquivo);
			
			if (geracaoAtual == 0){
				gravarArquivo.printf("-----------------------------------------------------------------------\n");
				gravarArquivo.printf("PARAMETROS UTILIZADOS: \n");
				gravarArquivo.printf("Funcao otimizada: " + AlgoritmoGeneticoController.NOME_FUNCAO + "\n");
				gravarArquivo.printf("Taxa de crossover: " + AlgoritmoGeneticoController.TAXA_CROSSOVER + "\n");
				gravarArquivo.printf("Taxa de mutacao: " + AlgoritmoGeneticoController.TAXA_MUTACAO + "\n");
				gravarArquivo.printf("Numero de geracoes: " + AlgoritmoGeneticoController.MAX_GERACOES + "\n");
				gravarArquivo.printf("Numero de cromossomos (Tam. pop.): " + AlgoritmoGeneticoController.TAMANHO_POPULACAO + "\n");
				gravarArquivo.printf("-----------------------------------------------------------------------\n");
				gravarArquivo.println();
				gravarArquivo.printf("Fitness ; Geracao");
				gravarArquivo.println();
			}
			
			for (Individuo individuo : listaIndividuos) {
				
				gravarArquivo.printf(individuo.getFitness() + ";" + geracaoAtual);
				gravarArquivo.println();
			}

			arquivo.close();
			
		} catch (IOException e) {
			System.err.println("Erro na gravacao do arquivo! " + e.getMessage());
		}
	}
	
	public void gravarArquivoCVRP(PopulacaoCVRP populacao, int geracaoAtual, String nomeArquivo) {

		FileWriter arquivo;
		try {
			arquivo = new FileWriter(nomeArquivo, true);

			PrintWriter gravarArquivo = new PrintWriter(arquivo);
			
			CromossomoCVRP[] listaIndividuos = populacao.getCromossomoCVRPs();
			
			if (geracaoAtual == 0){
				gravarArquivo.printf("-----------------------------------------------------------------------\n");
				gravarArquivo.printf("PARAMETROS UTILIZADOS: \n");
				gravarArquivo.printf("SELECAO TORNEIO: " + AlgoritmoGeneticoController.IS_SELECAO_TORNEIO + "\n");
				gravarArquivo.printf("SELECAO ROLETA: " + AlgoritmoGeneticoController.IS_SELECAO_ROLETA + "\n");
				gravarArquivo.printf("CROSSOVER CLASSICO: " + AlgoritmoGeneticoController.IS_CROSSOVER_CLASSICO + "\n");
				gravarArquivo.printf("CROSSOVER PMX: " + AlgoritmoGeneticoController.IS_CROSSOVER_PMX + "\n");
				gravarArquivo.printf("MUTACAO INSERTION: " + AlgoritmoGeneticoController.IS_MUTACAO_INSERTION + "\n");
				gravarArquivo.printf("MUTACAO SWAP: " + AlgoritmoGeneticoController.IS_MUTACAO_SWAP + "\n");
				gravarArquivo.printf("TX. CROSSOVER: " + AlgoritmoGeneticoController.TAXA_CROSSOVER + "\n");
				gravarArquivo.printf("TX. MUTACAO: " + AlgoritmoGeneticoController.TAXA_MUTACAO + "\n");
				gravarArquivo.printf("N. GERACOES: " + AlgoritmoGeneticoController.MAX_GERACOES + "\n");
				gravarArquivo.printf("N. CROMOSSOMOS (TAM. POP.): " + AlgoritmoGeneticoController.TAMANHO_POPULACAO + "\n");
				gravarArquivo.printf("-----------------------------------------------------------------------\n");
				gravarArquivo.println();
				gravarArquivo.printf("Custo ; Geracao ");
				gravarArquivo.println();
			}
			
			for (int i = 0; i < listaIndividuos.length; i++) {
				gravarArquivo.printf(listaIndividuos[i].getCost() + ";" + geracaoAtual);
				gravarArquivo.println();
			}

			arquivo.close();
			
		} catch (IOException e) {
			System.err.println("Erro na gravacao do arquivo! " + e.getMessage());
		}
	}

}
