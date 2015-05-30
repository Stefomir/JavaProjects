package com.calculator;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String strUsername, strPassword;

	// method for generating the login page
	protected void login(HttpServletRequest request,
			HttpServletResponse response, String strMsg) throws ServletException,
			IOException {

		HttpSession session = request.getSession(true);
		//destroys the session
		session.invalidate();
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Calculator</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"tan\">");
		out.println("<form action=/Calculator/Login method=post>");
		out.println("<center><h3>Calculator</h3></center>");
		out.println("<center>");
		// Prints error msg
		if (!(strMsg.equals(""))) {
			out.println("<strong><font color='red'>" + strMsg
					+ "</strong></font><br>");
		}
		out.println("<table cols=1 bgcolor=\"\">");
		out.println("<tr>");
		out.println("<TD ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Identification</strong></TD>");
		out.println("</tr>");
		out.println("<tr><td><table cols=4 width=100%>");
		out.println("<tr>");
		out.println("<td ALIGN=left>Account</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='login_username' value=''></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>Password</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=password name='secretkey'></td>");
		out.println("</tr>");
		out.println("</table></td></tr>");
		out.println("<tr><td>");
		out.println("<br>");
		out.println("<center><input type=submit style='width:50%; height:29px' value=Login></center>");
		out.println("<center><a href=/Calculator/UserReg>Registration</a></center>");
		out.println("</td></tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	//Method for validation the user info
	protected void validation(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer idUser = null;
		if (strUsername == null)
			strUsername = "";
		if (strPassword == null)
			strPassword = "";
		// Chech if the user's info is valid
		if (strUsername.equals("") || strPassword.equals("")) {
			login(request, response, "Please, enter accaunt and password!");
			return;
		}
		MySql sql = new MySql();
		//check if this user exist
		idUser = sql.loginValidation(strUsername, strPassword);
		if (idUser == -1) {
			login(request, response, "There is no such account!");
			return;
			}
		//Create session and sets the userID
		HttpSession session = request.getSession(true);
		session.setAttribute("Login", idUser.toString());
		response.sendRedirect("/Calculator/Calculate");
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
		else
			login(request, response, "");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}