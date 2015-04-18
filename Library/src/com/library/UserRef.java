package com.library;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.library.data.Person;
import com.library.data.PersonBean;

@WebServlet("/UserRef")
public class UserRef extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRef.class);

	@EJB
	PersonBean personBean;

	public UserRef() {
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

		// opciq za kirilica
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='/Library/background.jpg'>");
		out.println("<center><h3><p>RegisteredUsers</p></h3></center>");
		out.println("<form method=get>");

		try {

			out.println("<table style=width:100% border=5>");
			out.println("<tr>");
			out.println("<td><b>Function</b></td>");
			out.println("<td><b>UserName</b></td>");
			out.println("<td><b>UserFKnumber</b></td>");
			out.println("</tr>");

			// List s obekti ot Person
			List<Person> resultList = null;
			resultList = personBean.getAllPerson();

			// Izvlichane ot bazata
			if (!resultList.isEmpty()) {
				for (Person p : resultList) {
					long idPersons = p.getIdPerson();
					String name = p.getName();
					String fknumber = p.getFknumber();

					out.println("<tr>");
					out.println("<td width=140><a href='/Library/DeleteUser?id11="
							+ idPersons + "'>Delete</a>&nbsp;");
					out.println("<a href=/Library//UserReg?id11=" + idPersons
							+ ">Edit</a>&nbsp;");
					out.println("<a href=/Library//UserHistory?id11="
							+ idPersons + "&usrname=" + name + "&usrfk="
							+ fknumber + ">History</a>");

					out.println("<td>" + name + "</td>");
					out.println("<td>" + fknumber + "</td>");
					out.println("</tr>");
				}
			}

			out.println("</table>");
			out.println("<br>");
			out.println("</form>");
			out.println("<form action=/Library/MainForm method=get>"
					+ "<button type=submit style='width:10%; height:29px'>Back</button></form>");
			out.println("<form action=/Library/Login method=get>"
					+ "<button type=submit style='width:10%; height:29px'>Exit</button></form>");
			out.println("</body>");
			out.println("</html>");

			out.close();
		} catch (Exception e1) {
			out.println("Error :" + e1.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
