package com.github.daentech.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import br.usp.ia.geneticos.AlgoritmoGeneticoController;
import br.usp.ia.geneticos.CromossomoCVRP;
import br.usp.ia.geneticos.PopulacaoCVRP;
import br.usp.ia.util.DadosCVRP;
import br.usp.ia.util.GravadorArquivo;

public class SimpleGA {

	final static int DIMENSION = DadosCVRP.DIMENSION;

	  final static int CAPACITY = DadosCVRP.CAPACITY;

	  final static double MUTATION_RATE = AlgoritmoGeneticoController.TAXA_MUTACAO;

	  public static void swapPosition(int gene1, int gene2, Integer[] baby) {
	    int pos1 = 0;
	    int pos2 = 0;
	    for(int i=0; i<baby.length; i++) {
	      if(baby[i] == gene1) {
	        pos1 = i;
	      }
	      if(baby[i] == gene2) {
	        pos2 = i;
	      }
	    }
	    baby[pos1] = gene2;
	    baby[pos2] = gene1;
	  }

	  /**
	   * PMX - Partially matched crossover
	   */
	  static Integer[][] PMX(Integer[] mum, Integer[] dad, int begin, int end) {
	    Integer[][] baby = new Integer[2][DIMENSION];
	    baby[0] = Arrays.copyOf(mum, mum.length);
	    baby[1] = Arrays.copyOf(dad, dad.length);
	    for(int pos=begin; pos<end; pos++) {
	      int gene1 = mum[pos];
	      int gene2 = dad[pos];
	      swapPosition(gene1, gene2, baby[0]);
	      swapPosition(gene1, gene2, baby[1]);
	    }
	    return baby;
	  }

	  /* PMX Crossover*/
	  static CromossomoCVRP[] pmxCrossover(CromossomoCVRP mum, CromossomoCVRP dad) {
	    Random random = new Random();
	    int begin = random.nextInt(DIMENSION-2) + 1;
	    int end = random.nextInt(DIMENSION-1-begin) + begin;
	    Integer[][] baby = PMX(mum.getGenes(), dad.getGenes(), begin, end);
	    CromossomoCVRP[] babies = new CromossomoCVRP[2];
	    babies[0] = new CromossomoCVRP(baby[0]);
	    babies[1] = new CromossomoCVRP(baby[1]);
	    return babies;
	  }

	  static CromossomoCVRP crossover(CromossomoCVRP parent1, CromossomoCVRP parent2) {
	    Random random = new Random();
	    int begin = random.nextInt(DIMENSION-2) + 1;
	    int end = random.nextInt(DIMENSION-1-begin) + begin;
	    Integer[] mum = parent1.getGenes();
	    Integer[] dad = parent2.getGenes();
	    /* Keep track of added cities */
	    int[] added = new int[DIMENSION];
	    Integer[] baby = new Integer[DIMENSION];
	    /* Select a subset from the first parent */
	    for(int i=begin; i<end; i++) {
	      baby[i] = mum[i];
	      added[baby[i]] = 1;
	    }
	    /* Set start point */
	    baby[0] = 0;
	    /* Get genes from the other parent */
	    int j =1;
	    for(int i=1; i<dad.length; i++) {
	      if(j == begin) {
	        j = end;
	      }
	      if(added[dad[i]] == 0) {
	        baby[j++] = dad[i];
	        added[dad[i]] = 1;
	      }
	    }
	    return new CromossomoCVRP(baby);
	  }

	  static CromossomoCVRP orderedCrossover(CromossomoCVRP parent1, CromossomoCVRP parent2) {
	    Random random = new Random();
	    int begin = random.nextInt(DIMENSION-2) + 1;
	    int end = random.nextInt(DIMENSION-1-begin) + begin;
	    Integer[] mum = parent1.getGenes();
	    Integer[] dad = parent2.getGenes();
	    /* Keep track of added cities */
	    int[] added = new int[DIMENSION];
	    Integer[] baby = new Integer[DIMENSION];
	    /* Select a subset from the first parent */
	    for(int i=begin; i<end; i++) {
	      baby[i] = mum[i];
	      added[baby[i]] = 1;
	    }
	    /* Set start point */
	    baby[0] = 0;
	    /* Get genes from the other parent */
	    int j = end;
	    for(int i=1; i<dad.length; i++) {
	      if(j == dad.length) {
	        j = 1;
	      }
	      if(added[dad[i]] == 0) {
	        baby[j++] = dad[i];
	        added[dad[i]] = 1;
	      }
	    }
	    return new CromossomoCVRP(baby);
	  }

	  /*
	   *  Mutacao por "swap", ou seja, seleciona dois genes de maneira aleatoria e
	   *  troca as suas posicoes.
	   */
	  
	  static CromossomoCVRP swapMutation(CromossomoCVRP x) {
	    Random random = new Random();
	    int a = random.nextInt(DIMENSION-1) + 1;
	    int b = random.nextInt(DIMENSION-1) + 1;
	    Integer[] genes = x.getGenes();
	    int temp_gene = genes[a];
	    genes[a] = genes[b];
	    genes[b] = temp_gene;
	    return new CromossomoCVRP(genes);
	  }

	  /*
	   *  Mutacao por insercao, ou seja, remove de maneira aleatoria um gene e
	   *  o insere em uma nova posicao, tambem aleatoria.
	   */
	  static CromossomoCVRP insertMutation(CromossomoCVRP x) {
	    Random random = new Random();
	    int pos = random.nextInt(DIMENSION-1) + 1;
	    int gene = random.nextInt(DIMENSION-1) + 1;
	    Integer[] genes = x.getGenes();
	    int gene_pos = 0;
	    for(int i=1; i<DIMENSION; i++) {
	      if(genes[i] == gene) {
	        gene_pos = i;
	        break;
	      }
	    }
	    if(gene_pos < pos) {
	      for(int i=gene_pos; i<pos; i++) {
	        genes[i] = genes[i+1];
	      }
	      genes[pos] = gene;
	    } else {
	      for(int i=gene_pos; i>pos; i--) {
	        genes[i] = genes[i-1];
	      }
	      genes[pos] = gene;
	    }
	    return new CromossomoCVRP(genes);
	  }

	  /* Check if mutation should be applied. */
	  static boolean shouldMutate() {
	    if(Math.random() < MUTATION_RATE) {
	      return true;
	    }
	    return false;
	  }

	  /*
	   * Tournament selection
	   * n - PopulacaoCVRP size
	   * Generate k numbers between 0 and n and return the lowest.
	   * Returns the position of the parent in the PopulacaoCVRP array.
	   */
	  static int getParentTournament(int n, int k) {
	    Random random = new Random();
	    int min = Integer.MAX_VALUE;
	    for(int i=0; i<k; i++) {
	      int r = random.nextInt(n-1);
	      if(r < min) {
	        min = r;
	      }
	    }
	    return min;
	  }

	  /*
	   * Roulette Wheel selection
	   */
	  static int getParentRouletteWheel(Integer[] wheel) {
	    Random random = new Random();
	    int random_index = random.nextInt(wheel.length - 1);
	    return wheel[random_index];
	  }

	  /* Get next generation using Roulette Wheel Selection and crossover */
	  static PopulacaoCVRP getNextGenerationRW(PopulacaoCVRP PopulacaoCVRP) {
	    CromossomoCVRP[] initial = PopulacaoCVRP.getCromossomoCVRPs();
	    int size = initial.length;
	    List<CromossomoCVRP> list = new ArrayList<CromossomoCVRP>();

	    /* Crossover = Roulette Wheel Selection + Crossover */
	    for(int i=0; i<2*size; i++) {
	      int p1 = getParentRouletteWheel(PopulacaoCVRP.getRouletteWheel());
	      int p2 = getParentRouletteWheel(PopulacaoCVRP.getRouletteWheel());
	      while(p1 == p2) {
	        p2 = getParentRouletteWheel(PopulacaoCVRP.getRouletteWheel());
	      }
	      CromossomoCVRP parent1 = initial[p1];
	      CromossomoCVRP parent2 = initial[p2];

	      //list.add(orderedCrossover(parent1, parent2));
	    	if (AlgoritmoGeneticoController.IS_CROSSOVER_CLASSICO){
		    	list.add(crossover(parent1, parent2));
	    	}else if(AlgoritmoGeneticoController.IS_CROSSOVER_PMX){
		    	CromossomoCVRP[] babies = pmxCrossover(parent1, parent2);
		    	list.add(babies[0]);
		    	list.add(babies[1]);
	    	}
	    }

	    /* Mutation */
	    for(int i=0; i<list.size(); i++) {
	      if(shouldMutate()) {
	    	  if (AlgoritmoGeneticoController.IS_MUTACAO_INSERTION){
	  	        CromossomoCVRP mutated_CromossomoCVRP = insertMutation(list.get(i));
		        list.set(i, mutated_CromossomoCVRP);
	    	  }else if (AlgoritmoGeneticoController.IS_MUTACAO_SWAP){
	  	        CromossomoCVRP mutated_CromossomoCVRP = swapMutation(list.get(i));
		        list.set(i, mutated_CromossomoCVRP);
	    	  }
	      }
	    }

	    int count = 0;
	    for(CromossomoCVRP x: initial) {
	      if(count >= 50) {
	        break;
	      }
	      list.add(x);
	      count++;
	    }

	    return new PopulacaoCVRP(list.toArray(new CromossomoCVRP[list.size()]), size);
	  }

	  /* Get next generation using Tournament Selection and crossover */
	  static PopulacaoCVRP getNextGenerationTS(PopulacaoCVRP PopulacaoCVRP) {
	    CromossomoCVRP[] initial = PopulacaoCVRP.getCromossomoCVRPs();
	    int size = initial.length;
	    List<CromossomoCVRP> list = new ArrayList<CromossomoCVRP>();
	    int k = 1;

	    /* Crossover = Tournament Selection + Crossover */
	    for(int i=0; i<size; i++) {
	    	CromossomoCVRP parent1 = initial[getParentTournament(size, k)];
	    	CromossomoCVRP parent2 = initial[getParentTournament(size, k)];
	    	if (AlgoritmoGeneticoController.IS_CROSSOVER_CLASSICO){
		    	list.add(crossover(parent1, parent2));
	    	}else if(AlgoritmoGeneticoController.IS_CROSSOVER_PMX){
		    	CromossomoCVRP[] babies = pmxCrossover(parent1, parent2);
		    	list.add(babies[0]);
		    	list.add(babies[1]);
	    	}
	    }

	    /* Mutation */
	    for(int i=0; i<list.size(); i++) {
    	  if (AlgoritmoGeneticoController.IS_MUTACAO_INSERTION){
	  	        CromossomoCVRP mutated_CromossomoCVRP = insertMutation(list.get(i));
		        list.set(i, mutated_CromossomoCVRP);
    	  }else if (AlgoritmoGeneticoController.IS_MUTACAO_SWAP){
	  	        CromossomoCVRP mutated_CromossomoCVRP = swapMutation(list.get(i));
		        list.set(i, mutated_CromossomoCVRP);
    	  }
	    }

	    return new PopulacaoCVRP(list.toArray(new CromossomoCVRP[list.size()]), size);
	  }

	  /* */
	  public static Integer[] runGA(String nomeArquivo) {
		int size = AlgoritmoGeneticoController.TAMANHO_POPULACAO;
		int generations = AlgoritmoGeneticoController.MAX_GERACOES;
	    int GA_rounds = 1;

	    PopulacaoCVRP best = null;
	    double cost = Double.MAX_VALUE;

	    if (AlgoritmoGeneticoController.IS_SELECAO_ROLETA){
		    /* Selecao por roleta */
		    for(int i=0; i<GA_rounds; i++) {
		      PopulacaoCVRP populacaoCVRP = new PopulacaoCVRP(size);
		      int similar = 0;
		      double last_cost = populacaoCVRP.getMinDistance();
		      for(int j=0; j<generations; j++) {
		        populacaoCVRP = getNextGenerationRW(populacaoCVRP);
		        if(last_cost == populacaoCVRP.getMinDistance()) {
		          similar++;
		        } else {
		          last_cost = populacaoCVRP.getMinDistance();
		          similar = 0;
		        }
		        if(similar == 20) {
		          break;
		        }
			    
			    // Armazenar a populacao em arquivo para analise
			    GravadorArquivo gravadorArquivo = new GravadorArquivo();
			    gravadorArquivo.gravarArquivoCVRP(populacaoCVRP, j, nomeArquivo);
			    
		      }
		      if(populacaoCVRP.getMinDistance() < cost) {
		        cost = populacaoCVRP.getMinDistance();
		        best = populacaoCVRP;
		      }
		    }
	    }else if (AlgoritmoGeneticoController.IS_SELECAO_TORNEIO){
		    /* Selecao por torneio */
		    for(int i=0; i<GA_rounds; i++) {
		      PopulacaoCVRP PopulacaoCVRP = new PopulacaoCVRP(size);
		      for(int j=0; j<generations; j++) {
		        PopulacaoCVRP = getNextGenerationTS(PopulacaoCVRP);
			    // Armazenar a populacao em arquivo para analise
			    GravadorArquivo gravadorArquivo = new GravadorArquivo();
			    gravadorArquivo.gravarArquivoCVRP(PopulacaoCVRP, j, nomeArquivo);
		      }
		      if(PopulacaoCVRP.getMinDistance() < cost) {
		        cost = PopulacaoCVRP.getMinDistance();
		        best = PopulacaoCVRP;
		      }
		    }
	    }
	    
	    return best.getCromossomoCVRPs()[0].getGenes();
	    
	  }
}
