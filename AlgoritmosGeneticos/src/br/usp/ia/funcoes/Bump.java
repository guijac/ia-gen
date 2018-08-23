package br.usp.ia.funcoes;

import br.usp.ia.geneticos.IFuncaoFitness;

public class Bump extends FuncaoFitness implements IFuncaoFitness { 

	private static final String NOME = "Funcao Bump";
	private static final String NOME_ARQUIVO = "bump.txt";
	private static final Float MIN = (float) 0.0;
	private static final Float MAX = (float) 10.0;
	private static final String OBJETIVO = "MIN";
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
		
		Double xCos = Math.cos(x);
		Double yCos = Math.cos(y);
		
		Double temp0 = Math.pow(xCos, 4) + Math.pow(yCos, 4);
		Double temp1 = 2 * ( (xCos * xCos) * (yCos * yCos) );
		Double temp2 = Math.sqrt(x*x + 2*(y*y));
		
		Double fitness = Math.abs((temp0 - temp1) / temp2 );
		
		QTD_CHAMADAS++;
	 	
		// Multiplicado por 10 para facilitar a selecao e impressao
		return fitness * 10;
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
