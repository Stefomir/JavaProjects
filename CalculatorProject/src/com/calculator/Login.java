package com.calculator;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String strUsername, strPassword;

	protected void sendError(HttpServletRequest request,
			HttpServletResponse response, String strMsg)
			throws ServletException, IOException {

		request.setAttribute("Error", strMsg);
		RequestDispatcher view = request.getServletContext()
				.getRequestDispatcher("/Login.jsp");
		view.forward(request, response);
	}

	// Method for validation the user info
	protected void validation(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer idUser = null;
		if (strUsername == null)
			strUsername = "";
		if (strPassword == null)
			strPassword = "";
		// Chech if the user's info is valid
		if (strUsername.equals("") && strPassword.equals("")) {
			sendError(request, response, "Please, enter accaunt and password!");
			return;
		}
		if(strUsername.equals("")){
			sendError(request, response, "Please, enter accaunt!");
			return;
		}
		if(strPassword.equals("")){
			sendError(request, response, "Please, enter password!");
			return;
		}
		MySql sql = new MySql();
		// check if this user exist
		idUser = sql.loginValidation(strUsername, strPassword);
		if (idUser == -1) {
			sendError(request, response, "There is no such account!");
			return;
		}
		// Create session and sets the userID
		HttpSession session = request.getSession(true);
		session.setAttribute("Login", idUser.toString());
		response.sendRedirect("/CalculatorProject/Calculate.jsp");
		
	}

	public Login() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		strUsername = request.getParameter("login_username");
		strPassword = request.getParameter("secretkey");

		if (strUsername != null && strPassword != null)
			validation(request, response);
		else {
			response.sendRedirect("/Login.jsp");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}