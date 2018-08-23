package br.usp.ia.geneticos;

import java.util.Arrays;
import java.util.Collections;

import com.github.daentech.algorithms.SimpleGA;

import br.usp.ia.util.DadosCVRP;

public class CromossomoCVRP {
	final static int DIMENSION = DadosCVRP.DIMENSION;
	final static int CAPACITY = DadosCVRP.CAPACITY;

	private Integer[] genes = new Integer[DIMENSION];
	private Double distance = 0.0;
	private Double fitness = null;
	private Integer cells = 0;
	// Para fins de comparativos utilizaremos o "custo" de cada caminho
	private Double cost = 0.0;

	public CromossomoCVRP() {
		this.genes = getPermutation();
		this.distance = computeDistance(this.genes);
		this.cost = computeRouteCost(this.genes);
	}

	public CromossomoCVRP(Integer[] genes) {
		this.genes = Arrays.copyOf(genes, genes.length);
		this.distance = computeDistance(genes);
		this.cost = computeRouteCost(this.genes);
	}

	/* Total distance of all CromossomoCVRPs / distance of current CromossomoCVRP */
	public Double getFitness() {
		return this.fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public Integer[] getGenes() {
		return this.genes;
	}

	public Double getDistance() {
		return this.distance;
	}

	public void setCells(int n) {
		this.cells = n;
	}

	public Integer getCells() {
		return this.cells;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public static double computeDistance(Integer[] genes) {
		double distance = 0.0;
		for(int i=1; i<genes.length; i++) {
			distance += DadosCVRP.EDM[genes[i]][genes[i-1]];
		}
		return distance + DadosCVRP.EDM[genes[genes.length-1]][0];
	}

	static Integer[] getPermutation() {
		Integer[] array = new Integer[DIMENSION];
		for(int i=0; i<DIMENSION; i++) {
			array[i] = i;
		}
		Collections.shuffle(Arrays.asList(array));
		if(array[0] != 0) {
			SimpleGA.swapPosition(0, array[0], array);
		}
		return array;
	}

	/*
	 * Baseado no metodo getSimpleSolution da classe CVRP, utilizado para calcular
	 * o custo de cada individuo gerado, para afericao posterior.
	 */
	public static double computeRouteCost(Integer[] route) {
		int capacity = CAPACITY;
		double cost = 0;
		int i=0;
		for(i=1; i<DIMENSION; i++) {
			if(capacity - DadosCVRP.demand[route[i]] > 0) {
				capacity -= DadosCVRP.demand[route[i]];
				cost += DadosCVRP.EDM[route[i]][route[i-1]];
			} else {
				capacity = CAPACITY - DadosCVRP.demand[route[i]];
				cost += DadosCVRP.EDM[route[i-1]][0];
				cost += DadosCVRP.EDM[0][route[i]];
			}
		}

		cost += DadosCVRP.EDM[route[i-1]][0];

		return cost;

	}

}
