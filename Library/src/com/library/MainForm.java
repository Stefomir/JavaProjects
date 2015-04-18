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

@WebServlet("/MainForm")
public class MainForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MainForm.class);

	public MainForm() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// sesii
		HttpSession session = request.getSession(true);
		if (session.isNew()) {
			response.sendRedirect("/Library/Login");
			return;
		}

		String userID = new String("Login");
		userID = (String) session.getAttribute(userID);

		if (userID == null) {
			response.sendRedirect("/Library/Login");
			return;
		}

		if (!userID.equals("1")) {
			response.sendRedirect("/Library/Login");
			return;
		}
		// opcia za kirilica
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

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
		out.println("<td style='width: 33%; padding-right:10px'><form action=/Library/BookReg method=get>"
				+ "<button type=submit style='width:100%; height:30px'>BookReg</button></form></td>");
		out.println("<td style='width: 33%; padding-right:5px; padding-left:5px'><form action=/Library/BookRegDB method=get>"
				+ "<button type=submit style='width:100%; height:30px'>BookRef</button></form></td>");
		out.println("<td style='width: 33%; padding-left:10px'><form action=/Library/BookTake method=get>"
				+ "<button type=submit style='width:100%; height:30px'>TakeBook</button></form></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='width: 33%; padding-right:10px'><form action=/Library/UserReg method=get>"
				+ "<button type=submit style='width:100%; height:30px'>UserReg</button></form></td>");
		out.println("<td style='width: 33%; padding-right:5px; padding-left:5px'><form action=/Library/UserRef method=get>"
				+ "<button type=submit style='width:100%; height:30px'>UserRef</button></form></td>");
		out.println("<td style='width: 33%; padding-left:10px'><form action=/Library/BookReturn method=get>"
				+ "<button type=submit style='width:100%; height:30px'>ReturnBook</button></form></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='width: 33%; padding-right:10px'></td>");
		out.println("<td style='width: 33%; padding-right:5px; padding-left:5px'><form action=/Library/TakenBookRef method=get>"
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
		out.println("<td style='width: 33%; padding-left:10px'><form action=/Library/Login method=get>"
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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response); 
	}

}
