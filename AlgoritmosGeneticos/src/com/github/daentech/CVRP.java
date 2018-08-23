package com.github.daentech;

import java.util.ArrayList;

import com.github.daentech.algorithms.SimpleGA;
import br.usp.ia.util.DadosCVRP;

public class CVRP {

	final static int DIMENSION = DadosCVRP.DIMENSION;

	final static int CAPACITY = DadosCVRP.CAPACITY;

	/* Simple solution for spliting the best TSP route into smaller routes from CVRP */
	public static void getSimpleSolution(Integer[] best_route) {
		int capacity = CAPACITY;
		double cost = 0;
		StringBuilder br = new StringBuilder();
		ArrayList<ArrayList<Integer>> routes = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> route = new ArrayList<Integer>();
		br.append("1->");
		route.add(0);
		int i=0;
		for(i=1; i<DIMENSION; i++) {
			if(capacity - DadosCVRP.demand[best_route[i]] > 0) {
				capacity -= DadosCVRP.demand[best_route[i]];
				cost += DadosCVRP.EDM[best_route[i]][best_route[i-1]];
				br.append((best_route[i]+1) + "->");
				route.add(best_route[i]);
			} else {
				capacity = CAPACITY - DadosCVRP.demand[best_route[i]];
				cost += DadosCVRP.EDM[best_route[i-1]][0];
				cost += DadosCVRP.EDM[0][best_route[i]];
				br.append("1\n");
				route.add(0);
				routes.add(route);
				route = new ArrayList<Integer>();
				route.add(0);
				route.add(best_route[i]);
				br.append("1->" + (best_route[i]+1) + "->");
			}
		}
		cost += DadosCVRP.EDM[best_route[i-1]][0];
		br.append("1");
		route.add(0);
		routes.add(route);

		printSolution(br.toString(), cost);
	}

	public static double getRouteCost(ArrayList<Integer> route) {
		double cost = 0.0;
		for(int i=1; i<route.size(); i++) {
			cost += DadosCVRP.EDM[route.get(i-1)][route.get(i)];
		}
		return cost;
	}

	public static void printSolution(String routes, Double cost) {
		System.out.println("Genetic Algorithm with multiple crossover operators and multiple mutation techniques");
		System.out.println("cost " + cost);
		System.out.println(routes);
	}

	public  void executarCVRP(String nomeArquivo) {

		Integer[] best = SimpleGA.runGA(nomeArquivo);
		getSimpleSolution(best);

	}

}
