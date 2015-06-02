package com.calculator;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserReg")
public class UserReg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String strUsername;
	String strPassword;
	String strFullname;
	String strEmail;

	public UserReg() {
		super();

	}

	// Method that sends Error message to the JSP file
	protected void sendError(HttpServletRequest request,
			HttpServletResponse response, String strMsg)
			throws ServletException, IOException {

		request.setAttribute("Error", strMsg);
		sendParameters(request, response, strUsername, strPassword,
				strFullname, strEmail);
	}

	// Method that sends Parameters to the JSP file
	protected void sendParameters(HttpServletRequest request,
			HttpServletResponse response, String strPUsername,
			String strPPassword, String strPFullname, String strPEmail)
			throws ServletException, IOException {

		request.setAttribute("Username", strPUsername);
		request.setAttribute("Password", strPPassword);
		request.setAttribute("Fullname", strPFullname);
		request.setAttribute("Email", strPEmail);
		RequestDispatcher view = request.getServletContext()
				.getRequestDispatcher("/UserReg.jsp");
		view.forward(request, response);
	}

	// metod for saving the user info
	protected void userSave(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// check if the typed name is real
		try {
			Integer.parseInt(strFullname);
			sendError(request, response,
					"You can't type number for your Full Name!");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// check if the user is filled all the lines
		if ((strUsername.equals("")) || strPassword.equals("")
				|| strFullname.equals("") || strEmail.equals("")) {
			sendError(request, response, "Please, fill the lines with *");
			return;
		}
		// Check for valid email
		try {
			String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
			Boolean b = strEmail.matches(emailregex);
			if (b == false) {
				sendError(request, response, "Invalid Email");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		MySql sql = new MySql();
		// check if there is another user registered with the same name and
		// email
		if (sql.DublicateUsernameEmail(strUsername, strEmail)) {
			sendError(request, response,
					"There is another person with the same Name and Email!");
			return;
		}
		sql.insertUserInfo(strUsername, strPassword, strFullname, strEmail);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		strUsername = request.getParameter("user_name");
		strPassword = request.getParameter("user_password");
		strFullname = request.getParameter("user_fullname");
		strEmail = request.getParameter("user_email");

		userSave(request, response);
		response.sendRedirect("Login.jsp");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}