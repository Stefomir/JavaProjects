package com.library;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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

@WebServlet("/BookReturn")
public class BookReturn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BookTake.class);

	@EJB
	PersonBean personBean;
	@EJB
	BooksBean booksBean;
	@EJB
	BprefBean bprefBean;

	String str_idbpref;
	String str_id_persons;
	String str_user_fknumber;
	String str_name_student;
	String str_book_title;	
	String idBooksBpref;
	String str_book_author;
	String str_datetaken;
	String idBooks;
	String idBpref;

	// metod za generirane na stranicata za vrastane na kniga
	protected void State1(HttpServletResponse response,
			HttpServletRequest request, String MsgError)
			throws ServletException, IOException {

		if (str_user_fknumber == null)
			str_user_fknumber = "";
		if (str_name_student == null)
			str_name_student = "";

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='/Library/background.jpg'>");
		out.println("<center>");
		out.println("<form action=/Library/BookReturn method=post>");

		// uslovie za izkarvane na error message
		if (!(MsgError.equals(""))) {
			out.println("<br><strong><font color='red'>" + MsgError
					+ "</strong></font><br>");
		}

		out.println("<table cols=1>");
		out.println("<tr>");
		out.println("<td ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Book Return</strong></td>");
		out.println("</tr>");
		out.println("<tr><td><table cols=4 width=100%></td></tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>User FkNumber:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_fknumber' value='"
				+ str_user_fknumber + "'></td>");
		out.println("</tr>");
		out.println("<tr><td><br></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left>"
				+ "<button type=submit style='width:80%; height:29px'>Next</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/MainForm method=get>"
				+ "<button type=submit style='width:80%; height:29px'>Back</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/Login method=get>"
				+ "<button type=submit style='width:80%; height:29px'>Exit</button></form></td></tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}

	// metod za validirane na info ot state1 i pokazvane na spisaka s vzeti
	// knigi ot tozi user
	protected void State2(HttpServletResponse response,
			HttpServletRequest request) throws ServletException, IOException {

		try {

			// List s obekti ot Person
			List<Person> resultList = null;
			resultList = personBean.getPersonViaFknumber(str_user_fknumber);

			str_id_persons = null; // id na person
			str_name_student = null; // ime na person

			// izvlichane ot bazata
			if (!resultList.isEmpty()) {
				str_id_persons = resultList.get(0).getIdPerson().toString();
				str_name_student = resultList.get(0).getName();
			}

			// proverka sastestvuva takav person
			if (str_id_persons == null) {
				State1(response, request,
						"Missing client with such info, please try again!");
				return;
			}

			PrintWriter out = response.getWriter();

			out.println("<html>");
			out.println("<head><title>Library</title></head>");
			out.println("<meta hhtp-equiv='Content-Type' content='text/html;charset=windows-1251'>");
			out.println("<body background='/Library/background.jpg'>");
			out.println("<center>");
			out.println("FkNumber: <strong>" + str_user_fknumber
					+ "</strong> Name: <strong>" + str_name_student
					+ "</strong>");
			out.println("<br>");
			out.println("<table style=width:100% border=5>");
			out.println("<tr>");
			out.println("<td><b>Function</b></td>");
			out.println("<td><b>BookTitle</b></td>");
			out.println("<td><b>BookAuthor</b></td>");
			out.println("<td><b>DateTaken</b></td>");
			out.println("</tr>");

			idBpref = "";
			idBooks = "";

			// List s obekti ot Bpref i Books
			List<Object[]> lst = null;
			lst = bprefBean.getBookTaken(Long.parseLong(str_id_persons));

			
			// izvlichane ot bazata
			if (!lst.isEmpty()) {
				for (Object o[] : lst) {
					Bpref bp = (Bpref) o[0];
					Books b = (Books) o[1];

					str_book_title = b.getName();
					str_book_author = b.getAuthor();
					str_datetaken = bp.getDatetaken().toString();
					idBpref = bp.getIdBpref().toString();
					idBooks = bp.getIdBooks().toString();
					out.println("<tr>");
					out.println("<td width=120><a href='/Library/BookReturn?id_bpref="
							+ idBpref
							+ "&user_fknumber="
							+ str_user_fknumber
							+ "&idbooksss="
							+ idBooks
							+ "'>ReturnBook</a>&nbsp;&nbsp;");

					out.println("<td>" + str_book_title + "</td>");
					out.println("<td>" + str_book_author + "</td>");
					out.println("<td>" + str_datetaken + "</td>");
					out.println("</tr>");
				}
			}

			out.println("</table>");
			out.println("<br>");
			out.println("<form action=/Library/MainForm method=get>"
					+ "<button type=submit style='width:10%; height:29px'>Back</button></form>");
			out.println("<form action=/Library/Login method=get"
					+ "><button type=submit style='width:10%; height:29px'>Exit</button></form>");
			out.println("</body>");
			out.println("</html>");

			out.close();

		} catch (Exception e) {
		}
	}

	// metod za zapazvane na info ot varnatata kniga
	protected void SaveReturnBook(HttpServletResponse response,
			HttpServletRequest request) throws ServletException, IOException {

		try {

			idBooksBpref = request.getParameter("idbooksss");

			// List s obekti ot Books
			List<Books> resultList = null;
			resultList = booksBean.getBooksViaId(Long.parseLong(idBooksBpref));

			String book_credit = null; // vzeta kniga
			String book_flow = null; // nalichnost na kniga

			if (!resultList.isEmpty()) {
				// izvlichane ot bazata
				book_credit = resultList.get(0).getCredit();
				book_flow = resultList.get(0).getFlow();
				Integer newFlow = Integer.parseInt(book_flow);
				Integer newCredit = Integer.parseInt(book_credit);
				newFlow++;
				newCredit--;

				// updeitvane na zapisanata kniga
				booksBean.updateBookViaFlowAndCredit(
						Long.parseLong(idBooksBpref), newFlow.toString(),
						newCredit.toString());
				bprefBean.updateBookDate(Long.parseLong(str_idbpref),
						new Date().toString(), 'N');
			}
			// prepratka kam metod
			response.sendRedirect("/Library/MainForm");
		} catch (Exception e) {
		}
	}

	public BookReturn() {
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

		// izvlichane na parametri ot html
		str_idbpref = request.getParameter("id_bpref");
		str_id_persons = request.getParameter("id_persons");
		str_user_fknumber = request.getParameter("user_fknumber");
		str_name_student = request.getParameter("name_student");

		// uslovie za executvane na metodite
		if (str_idbpref != null) {
			SaveReturnBook(response, request);
			return;
		} else if (str_id_persons == null) {
			if (str_user_fknumber == null) {
				State1(response, request, "");
			} else {
				State2(response, request);
			}
		} else
			response.sendRedirect("/Library/BookReturn");

		return;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
