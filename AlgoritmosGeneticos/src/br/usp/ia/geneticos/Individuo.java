package br.usp.ia.geneticos;
import java.util.Random;
import br.usp.ia.util.Conversor;
import br.usp.ia.util.OperacoesEmGeneticos;
import br.usp.ia.util.ValoresFuncao;
public class Individuo implements Comparable<Individuo>{

	private String cromossomoBinario;
	private Float x;
	private Float y;
	private Double fitness;
	Random ran = new Random();

	// gera um individuo com genes aleatorios dentro de um intervalo
	public Individuo(float min, float max){
		this.x = ran.nextFloat() * (max - min) + min;
		this.y = ran.nextFloat() * (max - min) + min;
		String gene1 = Conversor.converterDecimalParaBinario(x);
		String gene2 = Conversor.converterDecimalParaBinario(y);
		this.cromossomoBinario = OperacoesEmGeneticos.mesclarGenes(gene1, gene2);
	}

	// mantendo o construtor padrao
	public Individuo(){

	}

	// gera um individuo com um cromossomo pre-determinado
	public Individuo(String cromossomo){
		this.cromossomoBinario = cromossomo;
		ValoresFuncao valoresFuncao = OperacoesEmGeneticos.quebrarCromossomoEmGenes(cromossomo);
		this.x = valoresFuncao.getX();
		this.y = valoresFuncao.getY();
	}

	public void setAptidao(double fitness){
		this.fitness = fitness;
	}

	public Double getFitness(){
		return fitness;
	}

	public String getCromossomoBinario(){
		return cromossomoBinario;
	}

	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}
	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}
	public void setCromossomoBinario(String cromossomoBinario) {
		this.cromossomoBinario = cromossomoBinario;
	}

	// Sobrecarga do metodo compareTo para ordernar pelo melhor fitness
	@Override
	public int compareTo(Individuo outroIndividuo) {
		if (this.fitness < outroIndividuo.getFitness()) 
			return -1;
		else if (this.fitness > outroIndividuo.getFitness()) 
			return 1;
		else
			return 0;
		
	}

}
