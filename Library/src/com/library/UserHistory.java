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

import com.library.data.Books;
import com.library.data.BprefBean;
import com.library.data.Bpref;

@WebServlet("/UserHistory")
public class UserHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserHistory.class);

	@EJB
	BprefBean bprefBean;

	public UserHistory() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='/Library/background.jpg'>");
		out.println("<center><h3><p>UserHistory</p></h3></center>");

		try {
			// izvlichane na parametri ot html
			String idPerson = request.getParameter("id11");
			String usrname = request.getParameter("usrname");
			String usrfk = request.getParameter("usrfk");

			out.println("<center><b>UserName: " + usrname + " UserFkNumber: "
					+ usrfk + "</b></center>");
			out.println("<table style=width:100% border=5>");
			out.println("<tr>");
			out.println("<td><b>BookTitle</b></td>");
			out.println("<td><b>BookAuthor</b></td>");
			out.println("<td><b>DateTaken</b></td>");
			out.println("<td><b>DateReturned</b></td>");
			out.println("</tr>");

			// List s obekti ot Bpref i Books
			List<Object[]> resultList = null;
			resultList = bprefBean.getHistory(Long.parseLong(idPerson));

			// Izvlichane ot bazata
			if (!resultList.isEmpty()) {
				for (Object o[] : resultList) {
					Bpref bp = (Bpref) o[0];
					Books b = (Books) o[1];

					String name = b.getName();
					String author = b.getAuthor();
					String datetaken = bp.getDatetaken();
					String datereturned = bp.getDatereturned();

					if (datereturned == null)
						datereturned = "Not yet returned";

					out.println("<tr>");
					out.println("<td>" + name + "</td>");
					out.println("<td>" + author + "</td>");
					out.println("<td>" + datetaken + "</td>");
					out.println("<td>" + datereturned + "</td>");
					out.println("</tr>");
				}
			}

			out.println("</table>");
			out.println("<br>");
			out.println("<form action=/Library/MainForm method=get>"
					+ "<button type=submit style='width:10%; height:29px'>Back</button></form>");
			out.println("<form action=/Library/Login method=get>"
					+ "<button type=submit style='width:10%; height:29px'>Exit</button></form>");
			out.println("</body>");
			out.println("</html>");

			out.close();
		} catch (Exception e1) {
			out.println("Syntaxis error with MySql");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
