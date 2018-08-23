package br.usp.ia.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
public class Conversor {

	/*
	 * 
	 * A conversao e realizada atraves da propria classe do Java "Double",
	 * com apoio do String.format(), o qual define um array de 64 bytes para
	 * a representacao, a funcao replace complementa os "0s" a esquerda do
	 * binario.
	 * 
	 */
	public static String converterDecimalParaBinario (Float valorDecimal){
		
		int intBits = Float.floatToIntBits(valorDecimal);
		return String.format("%32s", Integer.toBinaryString(intBits))
				.replace(" ", "0");
				
	}
	
	/*
	 * 
	 * A conversao e realizada atraves da propria classe do Java "Float",
	 * metodo intBitsToFloat, que recebe uma cadeia de string e em qual formato
	 * esta a cadeia, neste caso, base 2, ou seja, binaria.
	 * 
	 */
	public static Float converterBinarioParaDecimal (String valorEmmBinario){
		
		int intBits = new BigInteger(valorEmmBinario, 2).intValue();
		return Float.intBitsToFloat(intBits);
	}
	
	/*
	 * Formatar um Float para exibicao no arquivo de saida
	 */
	public static Double formatarFloat(Float numero){
		if (!numero.isNaN() && !numero.isInfinite()){
			BigDecimal bigDecimal = new BigDecimal(numero).setScale(16, RoundingMode.UP);
			return bigDecimal.doubleValue();
		}else return null;

	}
}

