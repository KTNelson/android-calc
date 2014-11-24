package com.ktnelson.androidcalc.basecalc;

import java.math.BigDecimal;

public class EquationStep {
	
	public enum eOperator{
		eOperator_Plus,
		eOperator_Subtract,
		eOperator_Multiply,
		eOperator_Divide
	}
	
	private EqNumber lhs;
	private EqNumber rhs;
	private eOperator operator;
	private EqNumber result;
	
	public EquationStep(){
		lhs = new EqNumber("0");
		rhs = new EqNumber("0");
		result = new EqNumber("0");
		operator = null;
	}
	
	public EquationStep(EqNumber leftHandSide, eOperator op, EqNumber rightHandSide){
		lhs = leftHandSide;
		rhs = rightHandSide;
		operator = op;
		result = new EqNumber("0");
	}
	
	public void setLeftHandValue(EqNumber leftHandSide) {
		lhs = leftHandSide;
	}

	public void setRightHandValue(EqNumber rightHandSide){
		rhs = rightHandSide;
	}

	public void setOperator(eOperator op){
		operator = op;
	}

	public void execute(){
		switch(operator)
		{
		case eOperator_Plus: this.setResult(lhs.getValue().add(rhs.getValue()));
			break;
		case eOperator_Subtract: this.setResult(lhs.getValue().subtract(rhs.getValue()));
			break;
		case eOperator_Divide: this.setResult(lhs.getValue().divide(rhs.getValue()));
			break;
		case eOperator_Multiply: this.setResult(lhs.getValue().multiply(rhs.getValue()));
		}
	}
	
	public EqNumber getResult(){
		return result;
	}
	
	public void setResult(BigDecimal inNum){
		result.setValue(inNum);
	}
}
