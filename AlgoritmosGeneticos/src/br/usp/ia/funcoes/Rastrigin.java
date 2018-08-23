package br.usp.ia.funcoes;

import br.usp.ia.geneticos.IFuncaoFitness;

public class Rastrigin  extends FuncaoFitness implements IFuncaoFitness { 

	private static final String NOME = "Funcao Rastring";
	private static final String NOME_ARQUIVO = "rastring.txt";
	private static final Float MIN = (float) -5.0;
	private static final Float MAX = (float) 5.0;
	private static final String OBJETIVO = "MAX";
	private static Integer QTD_CHAMADAS = 0;
	 
	@Override
	public String getNomeFuncao() {
		return NOME;
	}

	@Override
	public Integer getQtdChamadas() {
		return QTD_CHAMADAS;
	}

	@Override
	public void zerarQtdChamadas() {
		QTD_CHAMADAS = 0;
	}

	@Override
	public Double calcularFitness(Float x, Float y) {

		Double fitness = 0.0;
		
		fitness += Math.pow(x, 2.0) - 10.0 * Math.cos(2 * Math.PI * x);
		fitness += Math.pow(y, 2.0) - 10.0 * Math.cos(2 * Math.PI * y);
		
		QTD_CHAMADAS++;
		
		// Multiplicado por 10 para facilitar a selecao e impressao
		return -1 * (fitness  * 10);
		
	}

	@Override
	public Float getMin() {
		return MIN;
	}

	@Override
	public Float getMax() {
		return MAX;
	}

	@Override
	public String getNomeArquivo() {
		return NOME_ARQUIVO;
	}

	@Override
	public String getObjetivo() {
		return OBJETIVO;
	}
}
