package com.calculator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserReg")
public class UserReg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserReg() {
		super();

	}

	String strUsername;
	String strPassword;
	String strFullname;
	String strEmail;

	// metod for generating the reg page
	protected void stage1(HttpServletRequest request,
			HttpServletResponse response, String strMsg) throws ServletException,
			IOException {

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Calculator</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"tan\">");
		out.println("<center>");
		out.println("<form action=/Calculator/UserReg method=post>");
		// Prints error msg
		if (!(strMsg.equals(""))) {
			out.println("<br><strong><font color='red'>" + strMsg
					+ "</strong></font><br>");
		}
		out.println("<table cols=1>");
		out.println("<tr>");
		out.println("<td ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Register</strong></td>");
		out.println("</tr>");
		out.println("<tr><td><table cols=4 width=90%></td></tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*User Name:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_name' value='"+strUsername+"'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*User Password:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_password' value='"+strPassword+"'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Full Name:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td LIGN=left><input type=text name='user_fullname' value='"+strFullname+"'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Email:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td LIGN=left><input type=text name='user_email' value='"+strEmail+"'></td>");
		out.println("</tr>");
		out.println("<tr><td><br></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><button type=submit style='width:80%; height:29px'>Save</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Calculator/Login method=get><button type=submit style='width:80%; height:29px'>Back</button></form></td></tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	// metod for saving the user info
	protected void userSave(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// check if the typed name is real
		try {
			Integer.parseInt(strFullname);
			stage1(request, response,
					"You can't type number for your Full Name!");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// check if the user is filled all the lines
		if ((strUsername.equals("")) || strPassword.equals("")
				|| strFullname.equals("") || strEmail.equals("")) {
			stage1(request, response, "Please, fill the lines with *");
			return;
		}
		// Check for valid email
		try {
			String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
			Boolean b = strEmail.matches(emailregex);
			if (b == false) {
				stage1(request, response, "Invalid Email!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		MySql sql = new MySql();
		//check if there is another user registered with the same name and email
		if (sql.DublicateUsernameEmail(strUsername, strEmail)) {
			stage1(request, response,
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
		
		if (strUsername == null && strPassword == null && strFullname == null
				&& strEmail == null) {
			if(strUsername == null)
				strUsername = "";
			if(strPassword == null)
				strPassword = "";
			if(strFullname == null)
				strFullname = "";
			if(strEmail == null)
				strEmail = "";	
			stage1(request, response, "");
		} else {
			userSave(request, response);
			response.sendRedirect("/Calculator/Login");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}