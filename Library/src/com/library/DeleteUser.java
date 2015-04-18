package com.library;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.library.data.PersonBean;

@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DeleteUser.class);

	@EJB
	PersonBean personBean;

	public DeleteUser() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
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

		try {
			// izvlichane na parametar ot html
			String selectt = request.getParameter("id11");

			// proverka dali ima zapis
			if (selectt != null && !selectt.trim().isEmpty()) {
				// iztrivane na zapisa
				personBean.delPerson(Long.parseLong(selectt));
			}

		} catch (Exception e1) {
		}
		// prepratka kam servlet za referencia na registriranite useri v
		// bibliotekata
		response.sendRedirect("/Library/UserRef");

	}

}