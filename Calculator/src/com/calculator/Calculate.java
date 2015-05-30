package com.calculator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

	String script = "<script type=\"text/javascript\" language=\"Javascript\">"
			+ "function c(val){document.getElementById(\"d\").value=val;}"
			+ "function v(val){document.getElementById(\"d\").value+=val;}"
			+ "function e(){document.myform.submit();}" + "</script>";

	String htmlCalculator = "<div class=\"box\">"
			+ "<div class=\"display\"><input type=\"text\" readonly name =\"result\" size=\"18\" id=\"d\"></div>"
			+ "<div class=\"keys\">" + "<p>"
			+ "<input type=\"button\" class=\"button gray\" "
			+ "value=\"(\" onclick='v(\"(\")'>"
			+ "<input type=\"button\" class=\"button gray\" value=\""
			+ ")\" onclick='v(\")\")'>"
			+ "<input type=\"button\" class=\"button pink\""
			+ "value=\"/\" onclick='v(\"/\")'></p>"
			+ "<p><input type=\"button\" class=\"button black\""
			+ "value=\"7\" onclick='v(\"7\")'><input type=\"button\""
			+ "class=\"button black\" value=\"8\" onclick='v(\"8\")'>"
			+ "<input type=\"button\" class=\"button black\" value=\"9\""
			+ "onclick='v(\"9\")'><input type=\"button\""
			+ "class=\"button pink\" value=\"*\" onclick='v(\"*\")'></p>"
			+ "<p><input type=\"button\" class=\"button black\""
			+ "value=\"4\" onclick='v(\"4\")'><input type=\"button\""
			+ "class=\"button black\" value=\"5\" onclick='v(\"5\")'>"
			+ "<input type=\"button\" class=\"button black\" value=\"6\""
			+ "onclick='v(\"6\")'><input type=\"button\""
			+ "class=\"button pink\" value=\"-\" onclick='v(\"-\")'></p>"
			+ "<p><input type=\"button\" class=\"button black\""
			+ "value=\"1\" onclick='v(\"1\")'><input type=\"button\""
			+ "class=\"button black\" value=\"2\" onclick='v(\"2\")'>"
			+ "<input type=\"button\" class=\"button black\" value=\"3\""
			+ "onclick='v(\"3\")'><input type=\"button\""
			+ "class=\"button pink\" value=\"+\" onclick='v(\"+\")'></p>"
			+ "<p><input type=\"button\" class=\"button black\""
			+ "value=\"0\" onclick='v(\"0\")'>"
			+ "<input type=\"button\" class=\"button black\" value=\"C\""
			+ "onclick='c(\"\")'><input type=\"button\""
			+ "class=\"button orange\" value=\"=\" onclick='e()'></p>"
			+ "</div>" + "</div>";

	public Calculate() {
		super();
	}
	//method for generating the calculating page
	protected void stage1(HttpServletRequest request,
			HttpServletResponse response, String strEqua, String msgError)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"/Calculator/Mycss.css\"/>");
		out.println(script);
		out.println("<title>Calculator</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<a href=/Calculator/Login>LogOut</a>");
		out.println("<a href=/Calculator/Calculate?delHistory=1>Clear History</a>");
		out.println("<form name=\"myform\" action=/Calculator/Calculate method=post>");
		out.println("<center>");
		out.println("User's calculation history<br>");
		out.println("<textarea readonly style=\"background-color: #bccd95\" name=area cols=100% rows=10>");
		
		MySql sql = new MySql();
		//Load the last 10 actions of the user realized throw generics
		List<String> myresult = sql.selectHistoryCalculate(userID);
		for (String strLogHistory : myresult) out.println(strLogHistory);

		out.println("</textarea>");
		// Prints error msg
		if (!msgError.equals(""))
			out.println("<br><strong><font color='red'>" + msgError
					+ "</strong></font>");
		out.println("</center>");
		out.println(htmlCalculator);
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
	//method for calculating the user's operations
	protected void stage2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String strError = "";
		//Creating object for calculating and passing it the operation value
		Calculator calc = new Calculator(strInput);
		strError = calc.preprocessor();
		//check if there is an error and displays it
		if (!strError.equals("")) {
			stage1(request, response, strInput, strError);
		} else
		//check if there is an error and displays it
		if (calc.getInfix().equals("NA")) {
			stage1(request, response, strInput, "Wrong Parameters!");
		} else {
			strError = calc.infix2postfix();
			//check if there is an error and displays it
			if (!strError.equals("")) {
				stage1(request, response, strInput, strError);
			} else {
				//Building tree object for calculation
				BinaryTree eTree;
				try {
					eTree = calc.buildExpressionTree();
				} catch (Exception e) {
					//check if there is an error and displays it
					stage1(request, response, strInput,
							"Invalid math operacia!");
					return;
				}
				String strTrac;
				//Retrieves the result of the calculation
				strTrac = Double.toString(calc.evalExpressionTree(eTree));
				//check if there is an error and displays it
				if (strTrac.equals("Infinity")) {
					stage1(request, response, strInput,
							"You cant divide to zero!");
					return;
				}
				//Forming String with the original request + result operation
				String endResult = calc.getOriginal() + " = " + strTrac;

				MySql sql = new MySql();
				//Saving the finished result
				sql.insertHistoryCalculate(userID, endResult);
				endResult = "";
				stage1(request, response, endResult, "");

			}

		}
	}
	//method for deleting the user history
	protected void clearUserHistory(String strIdUser) {
		MySql sql = new MySql();
		//delete all user history
		sql.delUserHistory(strIdUser);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(true);
		if (session.isNew()) {
			response.sendRedirect("/Calculator/Login");
			return;
		}

		userID = new String("Login");
		userID = (String) session.getAttribute(userID);
		if (userID == null) {
			response.sendRedirect("/Calculator/Login");
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
			stage1(request, response, "", "");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
