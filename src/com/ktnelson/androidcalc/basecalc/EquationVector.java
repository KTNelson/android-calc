package com.ktnelson.androidcalc.basecalc;

import java.util.Vector;

public class EquationVector {
	private Vector<EquationStep> equationVector;
	
	public EquationVector(){
		equationVector = new Vector<EquationStep>();
	}
	
	public void addEquation(EquationStep newStep){
		equationVector.add(newStep);
	}
	
	public EqNumber getEquationResultAt(int index){
		return equationVector.elementAt(index).getResult();
	}
	
	public EqNumber equate(){
		EqNumber finalResult = new EqNumber("0");
		for(EquationStep step : equationVector)
		{
			step.execute();
			finalResult = step.getResult();
		}
		
		return finalResult;
	}
	
	public int getSize(){
		return equationVector.size();
	}
	
}
