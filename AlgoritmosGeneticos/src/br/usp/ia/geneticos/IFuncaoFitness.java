package br.usp.ia.geneticos;

public interface IFuncaoFitness {

	/**
	 * Retornar o nome da funcao, para exibir em tela
	 * 
	 * @return String nome da funcao 
	 */
	
	public String getNomeFuncao();
	 
	/**
	 * Retornar o nome do arquivo a ser gravado
	 * 
	 * @return String nome do arquivo 
	 */
	
	public String getNomeArquivo();
	
	/**
	 * Retornar a quantidade de chamadas realizadas da funcao
	 *  
	 * @return Integer Quantidade de chamadas
	 */
	public Integer getQtdChamadas();
	
	/**
	 * Zerar a quantidade de chamadas realizadas da funcao
	 *  
	 */
	public void zerarQtdChamadas();

	/**
	 * Calcular o fitness da funcao
	 * 
	 * @param Float x (eixo)
	 * @param Float y (eixo) 
	 * @return O valor do fitness
	 */
	public Double calcularFitness(Float x, Float y);
	
	/**
	 * Retornar o valor minimo do intervalo da funcao
	 * 
	 * @return Float valor minimo da funcao
	 */
	
	public Float getMin();
	
	/**
	 * Retornar o valor maximo do intervalo da funcao
	 * 
	 * @return Float valor maximo da funcao
	 */
	
	public Float getMax();
	
	/**
	 * Retorna o objetivo da funcao, seja ele maximizar ou minimizar
	 * 
	 * @return String objetivo da funcao
	 */
	
	public String getObjetivo();
}
