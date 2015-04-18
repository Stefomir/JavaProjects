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

@WebServlet("/BookTake")
public class BookTake extends HttpServlet {
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
	String str_name_student;
	String str_id_books;
	String str_id_persons;
	String str_user_fknumber;
	String str_book_title;
	String str_book_author;

	// Metod za generirane na stranicata za vzimane na knigi
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
		out.println("<form action=/Library/BookTake method=post>");

		// uslovie za izkarvane na error message
		if (!(MsgError.equals(""))) {
			out.println("<br><strong><font color='red'>" + MsgError
					+ "</strong></font><br>");
		}

		out.println("<table cols=1>");
		out.println("<tr>");
		out.println("<td ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Book Take</strong></td>");
		out.println("</tr>");
		out.println("<tr><td><table cols=4 width=90%></td></tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>User Name:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_name' value='"
				+ str_name_student + "'></td>");
		out.println("</tr>");
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

	// metod za validirane na usera,koito ste vzima kniga i generirane na
	// stranicata za izbor na kniga
	protected void State2(HttpServletResponse response,
			HttpServletRequest request, String Msg) throws ServletException,
			IOException {

		try {

			// List s obekti ot Person
			List<Person> resultList = null;
			resultList = personBean.getIdPersonViaFknumber(str_user_fknumber);

			// izvlichane ot bazata
			if (!resultList.isEmpty()) {
				str_id_persons = resultList.get(0).getIdPerson().toString();
				str_user_fknumber = resultList.get(0).getFknumber();
				str_name_student = resultList.get(0).getName();
			} else {
				str_id_persons = "";
				str_user_fknumber = null;
				str_name_student = null;
			}

			// proverka za podavane na prazen argument
			if (str_id_persons == "") {
				State1(response, request, "Missing Client with such Info!");
				return;
			}

		} catch (Exception e) {
		}

		// koda ste se izpalni samo ako tezi poleta ne sa NULL
		if ((str_user_fknumber != null) && (str_name_student != null)) {

			String usrname; // ime na person
			String usrfk; // fakulteten nomer na person

			// izvlichane na parametrite ot html
			usrname = request.getParameter("user_name");
			usrfk = request.getParameter("user_fknumber");

			// proverka za ne sastestvuvast user
			if (!usrname.equals(str_name_student)
					|| !usrfk.equals(str_user_fknumber)) {
				State1(response, request,
						"Missing client with such info,please try again!");
				return;
			}

			if (str_book_title == null)
				str_book_title = "";
			if (str_book_author == null)
				str_book_author = "";

			PrintWriter out = response.getWriter();

			out.println("<html>");
			out.println("<head><title>Library</title></head>");
			out.println("<body background='/Library/background.jpg'>");
			out.println("<center>");

			// uslovie za izkarvane na error message
			if (!(Msg.equals(""))) {
				out.println("<br><strong><font color='red'>" + Msg
						+ "</strong></font><br>");
			}

			out.println("FkNumber: <strong>" + str_user_fknumber
					+ "</strong> Name: <strong>" + str_name_student
					+ "</strong>");
			out.println("<form action=/Library/BookTake method=post>");
			out.println("<input type='hidden' name='id_persons' value='"
					+ str_id_persons + "'>");
			out.println("<input type='hidden' name='user_fknumber' value='"
					+ str_user_fknumber + "'>");
			out.println("<table cols=1>");
			out.println("<tr>");
			out.println("<td ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Book Take</strong></td>");
			out.println("</tr>");
			out.println("<tr><td><table cols=4 width=90%></td></tr>");
			out.println("<tr>");
			out.println("<td ALIGN=left>Book Title:</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td ALIGN=left><input type=text name='book_title' value='"
					+ str_book_title + "'></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td ALIGN=left>Book Author:</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td ALIGN=left><input type=text name='book_author' value='"
					+ str_book_author + "'></td>");
			out.println("</tr>");
			out.println("<tr><td><br></td></tr>");
			out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left>"
					+ "<button type=submit style='width:80%; height:29px'>Submit</button></form></td></tr>");
			out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/MainForm method=get>"
					+ "<button type=submit style='width:80%; height:29px'>Back</button></form></td></tr>");
			out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/Login method=get>"
					+ "<button type=submit style='width:80%; height:29px'>Exit</button></form></td></tr>");
			out.println("</table>");
			out.println("</center>");
			out.println("</body>");
			out.println("</html>");

			out.close();
		} else {
			// ako poletata po gore sa null - prepratka kam metod state1 za
			// popalvane na tezi poleta
			State1(response, request, "Please, enter FkNumber!");
		}
	}

	// metod za zapisvane na podadenata ot klienta informacia
	protected void Stage3(HttpServletResponse response,
			HttpServletRequest request) throws ServletException, IOException {

		try {

			String ibookCredit = "0";
			String ibookFlow = "0";

			// List s obekti ot Books
			List<Books> resultList = null;
			resultList = booksBean.getBooksViaNameAndAuthor(str_book_title,
					str_book_author);

			int flow_v = 0;
			int credit_v = 0;

			// izvlichane ot bazata
			if (!resultList.isEmpty()) {
				for (Books b : resultList) {
					str_book_title = b.getName();
					str_book_author = b.getAuthor();
					str_id_books = b.getIdBooks().toString();
					ibookCredit = b.getCredit(); // +1
					credit_v = Integer.parseInt(ibookCredit);
					credit_v++;
					ibookFlow = b.getFlow();
					flow_v = Integer.parseInt(ibookFlow);
				}
			}

			String bkname; // ime na kniga
			String bkauthor; // avtor na kniga

			// Izvlichane na parametri ot html
			bkname = request.getParameter("book_title");
			bkauthor = request.getParameter("book_author");

			// proverka za podavane na prazni poleta
			if ((bkname.equals("")) || (bkauthor.equals(""))) {
				State1(response, request, "Please, fill the lines with *");
				return;
			}

			// proverka za ne sastestvuvashta kniga
			if (!bkname.equals(str_book_title)
					|| !bkauthor.equals(str_book_author)) {
				State1(response, request,
						"There is no such Book registered in the Library!");
				return;
			}

			// proverka za nalichnost na knigata
			if (flow_v <= 0) {
				// ako nqma nalichna kniga se pravi prepratka kam metoda za
				// generirane na stranicata za vzemane na knigi
				State1(response, request,
						"This book is not in stock at the moment!");
				return;
			}
			// ako ima takava nalichnost se dekrementira sled vzemaneto
			flow_v--;

			if (str_id_books != null) {
				booksBean.updateBookViaFlowAndCredit(
						Long.parseLong(str_id_books), String.valueOf(flow_v),
						String.valueOf(credit_v));
			}

			// izvlichane na parametar ot html
			str_user_fknumber = request.getParameter("str_fknumber");

			// List s obekti ot Person
			List<Person> resultList2 = null;
			resultList2 = personBean.getIdPersonViaFknumber(str_user_fknumber);

			// izvlichane ot bazata
			if (!resultList2.isEmpty()) {
				str_id_persons = "";
				for (Person p : resultList2) {
					str_id_persons = p.getIdPerson().toString();
				}
			}

			// zapis v bazata
			if (str_id_books != null && str_id_persons != null) {
				Bpref bp = new Bpref();
				bp.setIdBooks(Long.parseLong(str_id_books));
				bp.setIdPerson(Long.parseLong(str_id_persons));
				bp.setDatetaken(new Date().toString());
				bprefBean.addBpref(bp);
			}

		} catch (Exception e) {
		}
	}

	public BookTake() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		// Sesii
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
		str_id_books = request.getParameter("id_books");
		str_user_fknumber = request.getParameter("user_fknumber");
		str_book_title = request.getParameter("book_title");
		str_book_author = request.getParameter("book_author");
		str_name_student = request.getParameter("user_name");

		// usloviq za executvane na metodite
		if (str_idbpref != null) {
		} else if (str_id_persons == null) {
			if (str_user_fknumber == null) {
				State1(response, request, "");
			} else {
				State2(response, request, "");
			}
		} else if (str_id_books == null) {
			Stage3(response, request);
			response.sendRedirect("/Library/MainForm");
		} else
			response.sendRedirect("/Library/BookTake");
		return;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
