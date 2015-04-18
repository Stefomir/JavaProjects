package com.library;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);

	String user, pas;

	// metod za konstruirane na stranicata za login
	protected void login(HttpServletRequest request,
			HttpServletResponse response, String Msg) throws ServletException,
			IOException {

		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession(true);
		session.invalidate();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Library</title>");
		out.println("</head>");
		out.println("<body background='/Library/background.jpg'>");
		out.println("<form action=/Library/Login method=post>");
		out.println("<center><h3>Library Software</h3></center>");
		out.println("<br>");
		out.println("<center>");

		// uslovie za izkarvane na error message
		if (!(Msg.equals(""))) {
			out.println("<br><strong><font color='red'>" + Msg
					+ "</strong></font><br>");
		}

		out.println("<br>");
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
		out.println("</td></tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}

	// metod za generirane na Control Panel stranicata i validirane na login
	// infoto
	protected void mainForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String userID = new String("Login");

		// Sesii
		HttpSession session = request.getSession(true);
		userID = (String) session.getAttribute(userID);

		if (userID == null)
			userID = "0";

		// Logvane s Admin
		if (!userID.equals("1")) {
			if (session.isNew()) {

				if (user == null)
					user = "";

				if (pas == null)
					pas = "";

				// Proverka za validnost na informaciata za Admin acaunta
				if (user.equals("") && pas.equals("")) {
					login(request, response,
							"Please, enter accaunt and password!");
					return;
				}

				if (user.equals("")) {
					login(request, response, "Please, enter accaunt!");
					return;
				}

				if (pas.equals("")) {
					login(request, response, "Please, enter password!");
					return;
				}

				if (!user.equals("1")) {
					login(request, response, "No such account!");
					return;
				}

				if (!pas.equals("1")) {
					login(request, response, "No such account!");
					return;
				}

				session.setAttribute("Login", "1");
			}
		}

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Library</title>");
		out.println("<style>");
		out.println("#container{");
		out.println("width:500px;");
		out.println("margin-left:auto;");
		out.println("margin-right:auto;");
		out.println("}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body background='/Library/background.jpg'>");
		out.println("<center>");
		out.println("<br>");
		out.println("<br>");
		out.println("<br>");
		out.println("<br>");
		out.println("<br>");
		out.println("<div id=container>");
		out.println("<table style='width:500px; table-layout:fixed'>");
		out.println("<tr style='height: 30px'>");
		out.println("<th ALIGN=CENTER BGCOLOR=#DCDCDC colspan='3'>");
		out.println("<strong>Library Control Panel</strong>");
		out.println("</th>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='width: 33%; padding-right:10px'><form action=/Library/BookReg method=post>"
				+ "<button type=submit style='width:100%; height:30px'>BookReg</button></form></td>");
		out.println("<td style='width: 33%; padding-right:5px; padding-left:5px'><form action=/Library/BookRegDB method=post>"
				+ "<button type=submit style='width:100%; height:30px'>BookRef</button></form></td>");
		out.println("<td style='width: 33%; padding-left:10px'><form action=/Library/BookTake method=post>"
				+ "<button type=submit style='width:100%; height:30px'>TakeBook</button></form></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='width: 33%; padding-right:10px'><form action=/Library/UserReg method=post>"
				+ "<button type=submit style='width:100%; height:30px'>UserReg</button></form></td>");
		out.println("<td style='width: 33%; padding-right:5px; padding-left:5px'><form action=/Library/UserRef method=post>"
				+ "<button type=submit style='width:100%; height:30px'>UserRef</button></form></td>");
		out.println("<td style='width: 33%; padding-left:10px'><form action=/Library/BookReturn method=post>"
				+ "<button type=submit style='width:100%; height:30px'>ReturnBook</button></form></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='width: 33%; padding-right:10px'></td>");
		out.println("<td style='width: 33%; padding-right:5px; padding-left:5px'><form action=/Library/TakenBookRef method=post>"
				+ "<button type=submit style='width:100%; height:30px'>TakenBookRef</button></form></td>");
		out.println("<td style='width: 33%; padding-right:10px'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='width: 33%; padding-right:10px'></td>");
		out.println("<td style='width: 33%; padding-right:5px; padding-left:5px'></td>");
		out.println("<td style='width: 33%; padding-right:10px'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='width: 33%; padding-right:10px'></td>");
		out.println("<td style='width: 33%; padding-right:5px; padding-left:5px'></td>");
		out.println("<td style='width: 33%; padding-left:10px'><form action=/Library/Login method=post>"
				+ "<button type=submit style='width:100%; height:30px'>Exit</button></form></td>");
		out.println("</tr>");
		out.println("<br>");
		out.println("</table>");
		out.println("</div>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}

	public Login() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// opcia za kirilica
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// izvlichane na parametri ot html
		user = request.getParameter("login_username");
		pas = request.getParameter("secretkey");

		if (user != null && pas != null)
			mainForm(request, response);
		else
			login(request, response, "");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
