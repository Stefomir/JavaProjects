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

@WebServlet("/UserReg")
public class UserReg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(UserReg.class);

	@EJB
	PersonBean personBean;

	String str_id_persons;
	String str_user_name;
	String str_user_fknumber;

	// metod za generirane na stranicata za reg na user
	protected void stage1(HttpServletRequest request,
			HttpServletResponse response, String Msg) throws ServletException,
			IOException {

		PrintWriter out = response.getWriter();

		// izvlichane na parametri ot html
		String selectt = request.getParameter("id11");

		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='/Library/background.jpg'>");
		out.println("<center>");

		String strUser_name = null; // ime na person
		String strUser_fknumber = null; // fakulteten nomer na person

		if (selectt == null) {

			strUser_name = "";
			strUser_fknumber = "";

			out.println("<form action=/Library/UserReg method=post>");
		} else {
			try {
				// List ot obekti ot Person
				List<Person> resultList = null;
				resultList = personBean.getPersonViaId(Long.parseLong(selectt));

				// izvlichane ot bazata
				if (!resultList.isEmpty()) {
					for (Person p : resultList) {
						strUser_name = p.getName();
						strUser_fknumber = p.getFknumber();
					}
				}

			} catch (Exception e) {
				out.println("There is a problem with the ExecuteQuery :"+e.getMessage());
			}

			out.println("<form action=/Library/UserReg method=post>");
			out.println("<input type='hidden' name='id11' value='" + selectt
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
		out.println("<td ALIGN=left>*User Name:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_name' value='"
				+ strUser_name + "'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*User FKnumber:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_fknumber' value='"
				+ strUser_fknumber + "'></td>");
		out.println("</tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left>"
				+ "<button type=submit style='width:80%; height:29px'>Save</button></form></td></tr>");
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

	// metod za redaktirane na zapisite na registriranite useri
	protected void userEdit(HttpServletRequest request,
			HttpServletResponse response, String Msg) throws ServletException,
			IOException {

		PrintWriter out = response.getWriter();

		// izvlichane na parametri ot html
		String selectt = request.getParameter("id11");

		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='/Library/background.jpg'>");
		out.println("<center>");

		String strUser_name = null;
		String strUser_fknumber = null;

		if (selectt == null) {

			strUser_name = "";
			strUser_fknumber = "";

			out.println("<form action=/Library/UserReg method=post>");
		} else {
			try {

				// List ot obekti ot Person
				List<Person> resultList = null;
				resultList = personBean.getPersonViaId(Long.parseLong(selectt));

				if (resultList.isEmpty()) {
					strUser_name = "";
					strUser_fknumber = "";
				}
				// Izvlichane ot bazata
				else {
					strUser_name = resultList.get(0).getName();
					strUser_fknumber = resultList.get(0).getFknumber();
				}
			} catch (Exception e) {
				out.println("There is a problem with the ExecuteQuery");
			}

			out.println("<form action=/Library/UserReg method=post>");
			out.println("<input type='hidden' name='id11' value='" + selectt
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
		out.println("<td ALIGN=left>*User Name:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_name' value='"
				+ strUser_name + "'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*User FKnumber:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_fknumber' value='"
				+ strUser_fknumber + "'></td>");
		out.println("</tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left>"
				+ "<button type=submit style='width:80%; height:29px'>Save</button></form></td></tr>");
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

	// metod za zapazvane na info na registrirania user
	protected void userSave(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// izvlichane na parametri ot html
		String selectt = request.getParameter("id11");
		String user_Name = request.getParameter("user_name");
		String user_Fknumber = request.getParameter("user_fknumber");

		// proverka e vavedeno chislo v poleto za user name
		try {
			Integer.parseInt(user_Name);
			stage1(request, response, "You can't enter a number for UserName!");
			return;
		} catch (Exception e) {
		}

		// proverka dali poletata name i fknumber sa prazni
		if ((user_Name.equals("")) || user_Fknumber.equals("")) {
			stage1(request, response, "Please fill the lines with *!");
			return;
		}

		try {
			boolean flag = false;
			// proverka dali usera e s unikalen fknumber
			if (selectt == null)
				flag = personBean.checkForNewPerson(user_Fknumber);
			// proverka dali nqma veche registriran takav user
			else
				flag = personBean.checkForNewPersonWithoutId(user_Fknumber,
						Long.parseLong(selectt));

			if (flag == false) {
				stage1(request, response,
						"There is another person with the same Fknumber!");
				return;
			}
			// ako nqma takova ID se pravi zapis na usera
			if (selectt == null) {
				Person p = new Person();
				p.setName(user_Name);
				p.setFknumber(user_Fknumber);
				personBean.addPerson(p);
			}
			// ako li ne se updeitva informaciata v usera s tova ID
			else {
				personBean.updateRecord(Long.parseLong(selectt), user_Name,
						user_Fknumber);
			}
		} catch (Exception e1) {
		}
	}

	public UserReg() {
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
		str_id_persons = request.getParameter("id11");
		str_user_name = request.getParameter("user_name");
		str_user_fknumber = request.getParameter("user_fknumber");

		// usloviq za executvane na metodite
		if (str_id_persons != null && str_user_name == null
				&& str_user_fknumber == null) {
			userEdit(request, response, "");
		} else {
			if (str_user_name == null && str_user_fknumber == null) {
				stage1(request, response, "");

			} else {
				userSave(request, response);
				response.sendRedirect("/Library/UserRef");
			}
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
