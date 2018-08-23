package br.usp.ia.funcoes;

import br.usp.ia.geneticos.IFuncaoFitness;

public class Gold  extends FuncaoFitness implements IFuncaoFitness { 

	private static final String NOME = "Funcao Gold";
	private static final String NOME_ARQUIVO = "gold.txt";
	private static final Float MIN = (float) -2.0;
	private static final Float MAX = (float) 2.0;
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
		
		Double fitness = 0.0;
		
		Double a = (1 + (((Math.pow(x + y + 1, 2.0))))
				* (19 - (14 * (Math.pow(x, 2.0))) - 14 * y + 6 * (x * y)
				+ 3 * (Math.pow(y, 2.0))));
		
		Double b = (30 + (((Math.pow(2 * x - 3 * y, 2.0))))
				* (18 - 32 * x + (12 * (Math.pow(x, 2.0))) + 48 * y - 36
				* (x * y) + 27 * (Math.pow(y, 2.0))));		
		
		fitness = a * b;
		
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
