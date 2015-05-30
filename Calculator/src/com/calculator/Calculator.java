package com.calculator;

import java.util.EmptyStackException;
import java.util.Stack;

//@author locphan

public class Calculator {
	private String unprocessed;
	private String infix;
	private String postfix;

	private final String VALID_SYMBOLS = "()1234567890/*+-\t ";
	private final String VALID_NUMBERS = "1234567890";
	private final String OPS = "/*+-()";

	private Stack<Character> opstack;
	private StringBuilder postfixBuilder;
	private Stack<Character> parenthesis;
	private Stack<BinaryTree<String>> binstack;

	public Calculator(String exp) {
		unprocessed = exp;
		infix = "";
		postfix = "";
		binstack = new Stack<BinaryTree<String>>();
		opstack = new Stack<Character>();
		parenthesis = new Stack<Character>();
	}

	public String getOriginal() {
		return unprocessed;
	}

	public String getInfix() {
		return infix;
	}

	public String getPostfix() {
		return postfix;
	}

	public String preprocessor() {
		StringBuffer buffer = new StringBuffer();
		unprocessed = unprocessed.replaceAll(" ", "");
		boolean error = false;
		String err = "";

		for (int i = 0; i < unprocessed.length(); i++) {
			char token = unprocessed.charAt(i);
			char nexttoken = 0;
			boolean lastchar = false;
			boolean isTokenDigit = false;
			boolean isNextTokenDigit = false;

			if (i == unprocessed.length() - 1) {
				lastchar = true;
			} else {
				nexttoken = unprocessed.charAt(i + 1);
			}

			if (VALID_SYMBOLS.indexOf(token) < 0) {
				err = "Illegal Character!";
				error = true;
				break;
			}

			if (!lastchar) {
				isTokenDigit = Character.isDigit(token);
				isNextTokenDigit = Character.isDigit(nexttoken);
				if ((isTokenDigit && nexttoken == '(')
						|| (token == ')' && isNextTokenDigit) || (token == ')')
						&& (nexttoken == '(')
						|| (token == ')' && nexttoken == '^')) {
					buffer.append(token + "*" + nexttoken);
					i++;
				} else {
					buffer.append(token);
				}
			} else if (lastchar) {
				buffer.append(token);
			}
		}
		if (error)
			infix = postfix = "NA";
		else
			infix = buffer.toString();

		return err;
	}

	private int precedence(char token) {
		int result = 0;
		if (token == '*' || token == '/') {
			result = 2;
		} else if (token == '+' || token == '-') {
			result = 1;
		}

		return result;
	}

	private void processOp(char op) {
		if (opstack.empty() || op == '(') {
			opstack.push(op);
		} else {
			char topop = opstack.peek();
			if (precedence(op) > precedence(topop)) {
				opstack.push(op);
			} else {
				while (!opstack.empty() && precedence(op) <= precedence(topop)) {
					opstack.pop();
					if (topop == '(') {
						break;
					}
					postfixBuilder.append(' ');
					postfixBuilder.append(topop);
					if (!opstack.empty()) {
						topop = opstack.peek();
					}
				}
				if (op != ')')
					opstack.push(op);
			}
		}
	}

	private boolean isOp(char op) {
		return OPS.indexOf(op) != -1;
	}

	public String infix2postfix() {
		boolean error = false;
		String err = "";

		if (!infix.equals("NA")) {
			postfixBuilder = new StringBuilder();

			for (int i = 0; i < infix.length(); i++) {
				char token = infix.charAt(i);

				try {
					if (token == '(') {
						parenthesis.push(token);
					}
					if (token == ')') {
						parenthesis.pop();
					}
				} catch (EmptyStackException e) {
					err = "Unbalanced parenthesis!";
					postfix = "NA";
					error = true;
					break;
				}

				if (VALID_NUMBERS.indexOf(token) >= 0) {
					postfixBuilder.append(token);
				} else if (isOp(token)) {
					processOp(token);
					postfixBuilder.append(' ');
				}

			}
			if (!parenthesis.empty()) {
				err = "Unbalanced parenthesis!";
				postfix = "NA";
				error = true;

			}
			if (!error) {
				while (!opstack.empty()) {
					char op = opstack.pop();
					if (op == '(') {

					}
					postfixBuilder.append(' ');
					postfixBuilder.append(op);

				}

				postfix = postfixBuilder.toString().trim()
						.replaceAll("  ", " ").replaceAll("   ", " ");
			}
		}
		return err;
	}

	public BinaryTree<String> buildExpressionTree() {
		if (!postfix.equals("NA")) {
			String[] elements = postfix.split(" ");

			for (String token : elements) {
				if (token.isEmpty()) {

				} else if (Character.isDigit(token.charAt(0))) {
					BinaryTree<String> element = new BinaryTree<String>(token,
							null, null);
					binstack.push(element);
				} else {
					BinaryTree<String> right = binstack.pop();
					BinaryTree<String> left = binstack.pop();
					BinaryTree<String> element = new BinaryTree<String>(token,
							left, right);
					binstack.push(element);
				}
			}
			return binstack.pop();

		} else
			return null;

	}

	public double evalExpressionTree(BinaryTree<String> eTree) {
		if (eTree == null)
			return 0;
		else if (eTree.isLeaf())
			return Double.parseDouble(eTree.root.data);
		else {
			char op = eTree.root.data.charAt(0);

			double left = evalExpressionTree(eTree.getLeftSubtree());
			double right = evalExpressionTree(eTree.getRightSubtree());

			return evaluate(op, left, right);
		}
	}

	private double evaluate(char op, double left, double right) {
		double result = 0;

		switch (op) {
		case '+':
			result = left + right;
			break;
		case '-':
			result = left - right;
			break;
		case '*':
			result = left * right;
			break;
		case '/':
			result = left / right;
			break;
		}
		return result;
	}

}