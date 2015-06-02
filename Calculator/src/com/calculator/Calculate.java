package com.calculator;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Calculate")
public class Calculate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String strInput;
	String userID = null;

	public Calculate() {
		super();
	}

	protected void sendError(HttpServletRequest request,
			HttpServletResponse response, String strIn, String strMsg)
			throws ServletException, IOException {

		request.setAttribute("Error", strMsg);
		request.setAttribute("Input", strIn);
		RequestDispatcher view = request.getServletContext()
				.getRequestDispatcher("/Calculate.jsp");
		view.forward(request, response);
	}

	// method for calculating the user's operations
	protected void stage2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String strError = "";
		// Creating object for calculating and passing it the operation value
		Calculator calc = new Calculator(strInput);
		strError = calc.preprocessor();
		// check if there is an error and displays it
		if (!strError.equals("")) {
			sendError(request, response, strInput, strError);
		} else
		// check if there is an error and displays it
		if (calc.getInfix().equals("NA")) {
			sendError(request, response, strInput, "Wrong Parameters!");
		} else {
			strError = calc.infix2postfix();
			// check if there is an error and displays it
			if (!strError.equals("")) {
				sendError(request, response, strInput, strError);
			} else {
				// Building tree object for calculation
				BinaryTree eTree;
				try {
					eTree = calc.buildExpressionTree();
				} catch (Exception e) {
					// check if there is an error and displays it
					sendError(request, response, strInput,
							"Invalid math operacia!");
					return;
				}
				String strTrac;
				// Retrieves the result of the calculation
				strTrac = Double.toString(calc.evalExpressionTree(eTree));
				// check if there is an error and displays it
				if (strTrac.equals("Infinity")) {
					sendError(request, response, strInput,
							"You cant divide to zero!");
					return;
				}
				// Forming String with the original request + result operation
				String endResult = calc.getOriginal() + " = " + strTrac;

				MySql sql = new MySql();
				// Saving the finished result
				sql.insertHistoryCalculate(userID, endResult);
				endResult = "";
				sendError(request, response, endResult, "");

			}

		}
	}

	// method for deleting the user history
	protected void clearUserHistory(String strIdUser) {
		MySql sql = new MySql();
		// delete all user history
		sql.delUserHistory(strIdUser);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(true);
		if (session.isNew()) {
			response.sendRedirect("/Calculator/Login.jsp");
			return;
		}

		userID = new String("Login");
		userID = (String) session.getAttribute(userID);
		if (userID == null) {
			response.sendRedirect("/Calculator/Login.jsp");
			return;
		}

		String strDelHistory = request.getParameter("delHistory");
		if (strDelHistory != null)
			if (strDelHistory.equals("1"))
				clearUserHistory(userID);

		strInput = request.getParameter("result");

		if (strInput != null && !strInput.equals(""))
			stage2(request, response);
		else
			sendError(request, response, "", "");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
