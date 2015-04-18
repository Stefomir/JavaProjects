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

import com.library.data.BooksBean;
import com.library.data.Books;
import com.library.data.BprefBean;
import com.library.data.Bpref;
import com.library.data.Person;
import com.library.data.PersonBean;

@WebServlet("/TakenBookRef")
public class TakenBookRef extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TakenBookRef.class);

	@EJB
	BprefBean bprefBean;
	@EJB
	BooksBean booksBean;
	@EJB
	PersonBean personBean;

	public TakenBookRef() {
		super();

	}

	// Referencia na vzetite knigi v bibliotekata
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
		out.println("<center><h3><p>TakenBooks</p></h3></center>");

		try {

			out.println("<table style=width:100% border=5>");
			out.println("<tr>");
			out.println("<td><b>Function</b></td>");
			out.println("<td><b>BookTitle</b></td>");
			out.println("<td><b>BookAuthor</b></td>");
			out.println("<td><b>DateTaken</b></td>");
			out.println("<td><b>TakenBy</b></td>");
			out.println("</tr>");

			// List s obekti ot Bpref, Books i Person
			List<Object[]> lst = null;
			lst = bprefBean.getFromAllTables('Y');

			// prazen li e lista
			if (!lst.isEmpty()) {
				// izvlichane ot bazata
				for (Object o[] : lst) {
					Bpref bp = (Bpref) o[0];
					Books b = (Books) o[1];
					Person p = (Person) o[2];

					String idbpref = bp.getIdBpref().toString();
					String user_fknumber = p.getFknumber();
					String book_title = b.getName();
					String book_author = b.getAuthor();
					String dateTaken = bp.getDatetaken().toString();

					out.println("<tr>");
					out.println("<td width=120><a href='/Library/DeleteTakenBook?id111="
							+ idbpref + "'>Delete</a>&nbsp;&nbsp;");
					out.println("<td>" + book_title + "</td>");
					out.println("<td>" + book_author + "</td>");
					out.println("<td>" + dateTaken + "</td>");
					out.println("<td>" + user_fknumber + "</td>");
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

		} catch (Exception e1) {
			out.println("Syntaxis error with MySql " + e1.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
