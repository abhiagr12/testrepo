package com.valtech.test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
//import java.util.Locale;

public class NumberTest {
	
	public static final String DECIMALS_2 = "#.00";
	public static final String DECIMALS_5 = "#.00000";
	public static final Double DOUBLE_ZERO = new Double(0);
	
	public static void main(String[] agr){
		numberFormat(12.3648524);
		//stringUtil();
		//currencyFormater();
	}
	
	public static void numberFormat(double value){
		System.out.println(Double.valueOf(Math.round(value * 1000) / 1000.0));
	}
	
	public static void stringUtil(){
		if(!"abhi".equals(null)){
			System.out.println("!\"abhi\".equals(null)");
		}
	}
	
	public static void currencyFormater(){
		final DecimalFormat currencyFormat = (DecimalFormat) NumberFormat.getCurrencyInstance();
		
		//adjustDigits(currencyFormat, currency);
		//adjustSymbol(currencyFormat, currency);
		int digits = 3;
		currencyFormat.setMaximumFractionDigits(digits);
		currencyFormat.setMinimumFractionDigits(digits);
		if (digits == 0)
		{
			currencyFormat.setDecimalSeparatorAlwaysShown(false);
		}
		
		//currencyFormat.
		//currencyFormat.format(123.36636);
		System.out.println(currencyFormat.format(123.36666));
		
	}
	
	public static Double formatValueToTwoDecimalPlaces(final Double value)
	{
		if (isInvalidValue(value))
		{
			return DOUBLE_ZERO;
		}
		final DecimalFormat df = new DecimalFormat(DECIMALS_2);
		return Double.valueOf(df.format(value.doubleValue()));
	}
	
	public static boolean isInvalidValue(final Double value)
	{
		if (value == null || Double.isNaN(value) || Double.isInfinite(value))
		{
			return true;
		}
		return false;
	}
}