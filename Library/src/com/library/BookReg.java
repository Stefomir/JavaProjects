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

import com.library.BookReg;
import com.library.data.BooksBean;
import com.library.data.Books;

@WebServlet("/BookReg")
public class BookReg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(BookReg.class);

	@EJB
	BooksBean booksBean;

	String str_id_books;
	String str_book_title;
	String str_book_author;

	// metod za generirane na stranicata za reg na knigi
	protected void stage1(HttpServletRequest request,
			HttpServletResponse response, String Msg) throws ServletException,
			IOException {

		PrintWriter out = response.getWriter();

		// izvlichane na parametri ot html
		String select = request.getParameter("id1");

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Library</title>");
		out.println("</head>");
		out.println("<body background='/Library/background.jpg'>");
		out.println("<center>");

		String strBook_title = null; // ime na kniga
		String strBook_author = null; // avtor na kniga
		String strbook_flow = "0"; // nalichnost na kniga

		if (select == null) {
			strBook_title = "";
			strBook_author = "";
			strbook_flow = "0";
			out.println("<form action=/Library/BookReg method=post>");
		} else {
			// list s obekti ot Books
			List<Books> resultList = null;
			resultList = booksBean.getBooksViaId(Long.parseLong(select));

			// izvlichane ot bazata
			if (!resultList.isEmpty()) {
				for (Books b : resultList) {
					strBook_title = b.getName();
					strBook_author = b.getAuthor();
					strbook_flow = b.getFlow();
				}
			}

			out.println("<form action=/Library/BookReg method=post>");
			out.println("<input type='hidden' name='id1' value='" + select
					+ "'>");
		}

		// uslovie za izkarvane na error message
		if (!(Msg.equals(""))) {
			out.println("<br><strong><font color='red'>" + Msg
					+ "</strong></font><br>");
		}
		out.println("<table cols=1>");
		out.println("<tr>");
		out.println("<td ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Register</strong></td>");
		out.println("</tr>");
		out.println("<tr><td><table cols=4 width=90%></td></tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Book Title:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='book_title' value='"
				+ strBook_title + "'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Author:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='book_author' value='"
				+ strBook_author + "'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Stock:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td LIGN=left><input type=text name='book_flow' value='"
				+ strbook_flow + "'></td>");
		out.println("</tr>");
		out.println("<tr><td><br></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left>"
				+ "<button type=submit style='width:80%; height:29px'>Save</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/MainForm method=get>"
				+ "<button type=submit style='width:80%; height:29px'>Back</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/Login method=get>"
				+ "<button type=submit style='width:80%; height:29px' >Exit</button></form></td></tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");

		out.close();

	}

	// metod za redaktirane na zapisite na knigite
	protected void editBook(HttpServletRequest request,
			HttpServletResponse response, String Msg) throws ServletException,
			IOException {

		PrintWriter out = response.getWriter();

		// izvlichane na parametri ot html
		String select = request.getParameter("id1");

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Library</title>");
		out.println("</head>");
		out.println("<body background='/Library/background.jpg'>");
		out.println("<center>");

		String strBook_title = null; // ime na kniga
		String strBook_author = null; // avtor na kniga
		String strbook_flow = "0"; // nalichnost na kniga

		if (select == null) {
			strBook_title = "";
			strBook_author = "";
			strbook_flow = "0";

			out.println("<form action=/Library/BookSave method=post>");
		} else {

			try {

				// list s obekti ot Books
				List<Books> resultlist = null;
				resultlist = booksBean.getBooksViaId(Long.parseLong(select));

				if (resultlist.isEmpty()) {
					strBook_title = "";
					strBook_author = "";
					strbook_flow = "0";
				} else {

					// izvlichane ot bazata
					strBook_title = resultlist.get(0).getName();
					strBook_author = resultlist.get(0).getAuthor();
					strbook_flow = resultlist.get(0).getFlow();
				}

			} catch (Exception e) {
				out.println("There is a problem with the execution Query"
						+ e.getMessage());
			}

			out.println("<form action=/Library/BookReg method=post>");
			out.println("<input type='hidden' name='id1' value='" + select
					+ "'>");
		}

		// uslovie za izkarvane na error message
		if (!(Msg.equals(""))) {
			out.println("<br><strong><font color='red'>" + Msg
					+ "</strong></font><br>");
		}

		out.println("<table cols=1>");
		out.println("<tr>");
		out.println("<td ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Update</strong></td>");
		out.println("</tr>");
		out.println("<tr><td><table cols=4 width=90%></td></tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Book Title:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='book_title' value='"
				+ strBook_title + "'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Author:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='book_author' value='"
				+ strBook_author + "'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Stock:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td LIGN=left><input type=text name='book_flow' value='"
				+ strbook_flow + "'></td>");
		out.println("</tr>");
		out.println("<tr><td><br></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left>"
				+ "<button type=submit style='width:80%; height:29px'>Save</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/MainForm method=get>"
				+ "<button type=submit style='width:80%; height:29px'>Back</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/Login method=get>"
				+ "<button type=submit style='width:80%; height:29px' >Exit</button></form></td></tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}

	// metod za zapazvane na informaciata,vavedena ot usera, v bazata
	protected void bookSave(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// opcia za kirilica
//response.setContentType("text/html;charset=UTF-8");
//response.setCharacterEncoding("UTF-8");
//request.setCharacterEncoding("UTF-8");

		// izvlichane na parametri ot html
		String select = request.getParameter("id1");
		String bookName = request.getParameter("book_title");
		String bookAuthor = request.getParameter("book_author");
		String bookFlow = request.getParameter("book_flow");

		// proverka dali e vavedeno nesto razlichno ot int
		try {
			Integer.parseInt(bookFlow);
		} catch (Exception e) {
			stage1(request, response, "You must type a holl number for Stock!");
			return;
		}
		// proverka dali e vavedeno chislo,vmesto string
		try {
			Integer.parseInt(bookName);
			Integer.parseInt(bookAuthor);
			stage1(request, response,
					"You can't type numbers in Title or Author!");
			return;

		} catch (Exception e) {
		}

		// proverka dali usera e popalnil poletata
		if ((bookName.equals("")) || bookAuthor.equals("")) {
			stage1(request, response, "Please, fill the lines with *");
			return;
		}
		
		try {
			boolean flag = false;
			// proverka dali knigata e s unikalno ime i avtor
			if (select == null)
				flag = booksBean.checkForNewBook(bookName, bookAuthor);
			// proverka dali ima sastata kniga(po ime,avtor i ID)
			else
				flag = booksBean.checkForNewBookWithoutId(bookName, bookAuthor,
						Long.parseLong(select));
			// proverka dali tazi kniga se povtarq
			if (flag == false) {
				stage1(request, response,
						"There is another book with the same Title and Author!");
				return;
			}

			// ako usera ne popalni poleto za kolichesto,to avtomatichno se
			// setva na 1
			if (Integer.parseInt(bookFlow) <= 0)
				bookFlow = "1";
			// zapis na kniga
			if (select == null) {
				Books b = new Books();
				b.setName(bookName);
				b.setAuthor(bookAuthor);
				b.setFlow(bookFlow);
				b.setCredit("0");
				booksBean.addBook(b);
			}
			// update na kniga
			else {
				booksBean.updateRecord(Long.parseLong(select), bookName,
						bookAuthor, bookFlow, "0");
			}
		} catch (Exception e) {
		}
	}

	public BookReg() {
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

		// izvlichane na parametri ot html
		str_id_books = request.getParameter("id1");
		str_book_title = request.getParameter("book_title");
		str_book_author = request.getParameter("book_author");

		// usloviq za executvane na metodite
		if (str_id_books != null && str_book_title == null
				&& str_book_author == null) {
			editBook(request, response, "");
		} else {
			if (str_book_title == null && str_book_author == null) {
				stage1(request, response, "");
			} else {
				bookSave(request, response);
				response.sendRedirect("/Library/BookRegDB");
			}
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
