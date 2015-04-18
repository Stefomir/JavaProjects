package com.library;

import java.io.IOException;
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
import com.library.data.BooksBean;
import com.library.data.BprefBean;

@WebServlet("/DeleteTakenBook")
public class DeleteTakenBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DeleteTakenBook.class);

	@EJB
	BprefBean bprefBean;
	@EJB
	BooksBean booksBean;

	public DeleteTakenBook() {
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

		try {
			String book_credit = null;
			String book_flow = null;
			String book_id = null;
			Integer newBook_credit = null;
			Integer newBook_flow = null;
			
			// izvlichane na parametar ot html
			String selecttt = request.getParameter("id111");

			//List s obekti ot Bpref, Books, Person
			List<Object[]> resultList = null;
			resultList = bprefBean.getEditBpref(Long.parseLong(selecttt));
			
			// proverka dali ima zapis
			if (selecttt != null && !selecttt.trim().isEmpty()) {
				//izvlichane ot bazata
				if(!resultList.isEmpty()){
					for(Object o[] :resultList){
						
						Books b =(Books)o[1];
						
						book_id = b.getIdBooks().toString();
						book_credit = b.getCredit();
						book_flow = b.getFlow();
						
						newBook_credit = Integer.parseInt(book_credit);
						newBook_flow = Integer.parseInt(book_flow);
						newBook_credit--;
						newBook_flow++;
					}
				}
				// trie zapisa
				bprefBean.delBpref(Long.parseLong(selecttt));
				//updeitva nalichieto na knigite
				booksBean.updateBookViaFlowAndCredit(Long.parseLong(book_id), newBook_flow.toString(), newBook_credit.toString());
			}

		} catch (Exception e1) {
		}
		// prepratka kam servlet za referencia na vzetite knigi v bibliotekata
		response.sendRedirect("/Library/TakenBookRef");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
