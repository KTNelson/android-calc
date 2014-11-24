package com.ktnelson.androidcalc.basecalc;

public class StringParser {
	
	private String m_input;
	private EquationVector eqVec;
	
	public StringParser(String in){
		m_input = in;
	}
	
	public EquationVector createEquationVector(){
		eqVec = new EquationVector();
		
		while(inputContainsBrackets())
		{
			eqVec.addEquation(readStep(m_input));
		}
		
		eqVec.addEquation(readStep(m_input));
		return eqVec;
	}
		
	public EquationStep readStep(String input){
		
		EqNumber lhs = null;
		EqNumber rhs = null;
		EquationStep.eOperator op = null;
		
		int rhsStart = 0;
		int lhsStart = 0;
		String potentialLhs = "";
		String potentialRhs = "";
		
		//lhs and operator
		for (int i = lhsStart; i < input.length(); i++){
			char toCheck = input.charAt(i);
			if(isNum(toCheck)){
				potentialLhs += input.charAt(i);
			}else {
				if(isOperator(toCheck))
				{
					if(lhs == null){
						lhs = new EqNumber(potentialLhs);
					}
					op = setOperator(toCheck);
					rhsStart = i+1;
					break;
				}
				else if(isOpeningBracket(toCheck)){
					continue;
				}
				else if(isEquationReference(toCheck)){
					String refString = String.valueOf(input.charAt(i+1));
					lhs = eqVec.getEquationResultAt(Integer.valueOf(refString));
					i++;
				}
			}
		}
		
		//rhs
		for(int i = rhsStart; i < input.length(); i++){
			char toCheck = input.charAt(i);
			if(isNum(toCheck)){
				potentialRhs += toCheck;
			}
			else if(isOpeningBracket(toCheck)){
				return readStep(input.substring(i));
			}
			else if(isClosingBracket(toCheck)){
				break;
			}
			else if(isEquationReference(toCheck)){
				String refString = String.valueOf(input.charAt(i+1));
				rhs = eqVec.getEquationResultAt(Integer.valueOf(refString));
				break;
			}
		}
		if(rhs == null){
			rhs = new EqNumber(potentialRhs);
		}
		
		removeStringSection(m_input, m_input.indexOf(input), input.length());
		return new EquationStep(lhs, op, rhs);
	}
		
	private boolean isNum(char c){
		if(!isSpace(c) && !isOperator(c) && !isOpeningBracket(c) && !isClosingBracket(c) && !isEquationReference(c)){
			return true;
		}
		return false;
	}
	
	private boolean isSpace(char c){
		if(c == ' '){
			return true;
		}
		return false;
	}
	
	private boolean isOperator(char c){
		if(c == '+' || c == '-' || c == '/' || c == '*'){
			return true;
		}
		return false;
	}
	
	private boolean isOpeningBracket(char c){
		if(c == '('){
			return true;
		}
		return false;
	}
	private boolean isClosingBracket(char c){
		if(c == ')'){
			return true;
		}
		return false;
	}
	
	private boolean isEquationReference(char c){
		if(c == '!'){
			return true;
		}
		return false;
	}
	
	private boolean inputContainsBrackets(){
		if(m_input.contains("(") || m_input.contains(")")){
			return true;
		}
		return false;
	}
	
	private EquationStep.eOperator setOperator(char op){
		EquationStep.eOperator newOp = null;
		switch(op)
		{
		case '+': newOp = EquationStep.eOperator.eOperator_Plus;
			break;
		case '-': newOp = EquationStep.eOperator.eOperator_Subtract;
			break;
		case '/': newOp = EquationStep.eOperator.eOperator_Divide;
			break;
		case '*': newOp = EquationStep.eOperator.eOperator_Multiply;
		}
		return newOp;
	}
	
	private void removeStringSection(String inputCopy, int startPos, int length){
		int closingBracketIndex = inputCopy.indexOf(')') + 1;
		String firstPart = inputCopy.substring(0, startPos);
		String ref = "!" + Integer.toString(eqVec.getSize());
		String endPart = inputCopy.substring(closingBracketIndex);
		
		m_input = firstPart + ref + endPart;
	}
}
