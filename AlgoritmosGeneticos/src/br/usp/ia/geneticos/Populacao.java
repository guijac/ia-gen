package br.usp.ia.geneticos;
import java.util.ArrayList;
import java.util.Collections;

/*
 * 
 * Classe que representa uma populacao do problema
 * 
 * Baseado no fonte de COLLARES, Paulo Disponivel em 
 * <http://www.paulocollares.com.br/2013/05/algoritimo-genetico-classico-em-java-hello-world/>
 * 
 */
public class Populacao {
	
	// variaveis da classe
	private ArrayList<Individuo> listaIndividuos = new ArrayList<Individuo>();
	private Integer tamanhoPopulacao;

	// cria populacao de individuos geneticamente aleatoria
	public Populacao(int tamanhoPopulacao, float min, float max){
		this.tamanhoPopulacao = tamanhoPopulacao;
		for (int i = 0; i < tamanhoPopulacao; i++) {
			listaIndividuos.add(new Individuo (min, max));
		}
	}
	
    //cria uma população com indivíduos nulos, para ser composta posteriormente
    public Populacao(int tamanhoPopulacao) {
        this.tamanhoPopulacao = tamanhoPopulacao;
    }
	
    // Ordenar conforme maximizacao ou minimizacao
	public void ordenarPopulacao(String operacao){
		if (operacao.equals("MIN")){
			Collections.sort(this.listaIndividuos);
		}else if (operacao.equals("MAX")){
			Collections.sort(this.listaIndividuos);
			Collections.reverse(this.listaIndividuos);
		}
		
	}
	
    //coloca um indivíduo em uma determinada posição da população
    public void setIndividuo(Individuo individuo, int posicao) {
        listaIndividuos.set(posicao, individuo);
    }
    
    //coloca um indivíduo na próxima posição disponível da população
    public void setIndividuo(Individuo individuo) {
    	listaIndividuos.add(individuo);
    }

	public ArrayList<Individuo> getPopulacao(){
		return listaIndividuos;
	}

	public Integer getTamanhoPopulacao() {
		return tamanhoPopulacao;
	}

	public void setTamanhoPopulacao(Integer tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
	}
	
	// Quantidade de individuos validos em uma populacao
    public int getQuantidadeIndividuos() {
        int num = 0;
		for (int i = 0; i < tamanhoPopulacao; i++) {
			if (listaIndividuos != null) num++;
		}
        return num;
    }
}
