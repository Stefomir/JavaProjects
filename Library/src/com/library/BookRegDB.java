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

import com.library.BookRegDB;
import com.library.data.BooksBean;
import com.library.data.Books;

@WebServlet("/BookRegDB")
public class BookRegDB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BookRegDB.class);

	@EJB
	BooksBean booksBean;

	public BookRegDB() {
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

		// opcii za kirilica
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='/Library/background.jpg'>");
		out.println("<center><h3><p>RegisteredBooks</p></h3></center>");

		try {

			out.println("<table style=width:100% border=5>");
			out.println("<tr>");
			out.println("<td><b>Function</b></td>");
			out.println("<td><b>BookTitle</b></td>");
			out.println("<td><b>BookAuthor</b></td>");
			out.println("<td><b>Stock</b></td>");
			out.println("<td><b>Taken</b></td>");
			out.println("</tr>");

			// List s obekti ot Books
			List<Books> resultList = null;
			resultList = booksBean.getAllBooks();

			// izvlichane ot bazata
			if (!resultList.isEmpty()) {
				for (Books b : resultList) {
					long idBooks = b.getIdBooks();
					String name = b.getName();
					String author = b.getAuthor();
					String flow = b.getFlow();
					String credit = b.getCredit();

					out.println("<tr>");
					out.println("<td width=120><a href='/Library/DeleteBook?id1="
							+ idBooks + "'>Delete</a>&nbsp;&nbsp;");
					out.println("<a href=/Library//BookReg?id1=" + idBooks
							+ ">Edit</a>&nbsp;&nbsp;");
					out.println("<td>" + name + "</td>");
					out.println("<td>" + author + "</td>");
					out.println("<td>" + flow + "</td>");
					out.println("<td>" + credit + "</td>");
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
			out.println("There is a problem with the execution Query");
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
