<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	HttpSession mySession = request.getSession(true);
		if (mySession.isNew()) {
	RequestDispatcher view = request.getServletContext()
			.getRequestDispatcher("/Login.jsp");
	view.forward(request, response);
		}

		String userID = new String("Login");
		userID = (String) session.getAttribute(userID);
		if (userID == null) {
			RequestDispatcher view = request.getServletContext()
					.getRequestDispatcher("/Login.jsp");
			view.forward(request, response);
		}
%>

<%!private String getFields(String strFieldName, HttpServletRequest request) {
		String strVal = (String) request.getAttribute(strFieldName);
		if (strVal == null)
			return "";
		else
			return strVal;

	}%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/Calculator/Mycss.css" />
<script type="text/javascript" language="Javascript">
	function c(val) {
		document.getElementById("d").value = val;
	}
	function v(val) {
		document.getElementById("d").value += val;
	}
	function e() {
		document.myform.submit();
	}
	function b() {
		document.getElementById("d").value = document.getElementById("d").value.slice(0,-1);
	}
</script>

<title>Calculator</title>
</head>
<body>
	<a href=/Calculator/Login.jsp>LogOut</a>
	<a href=/Calculator/Calculate?delHistory=1>Clear History</a>
	<form name="myform" action=/Calculator/Calculate method=post>
		<center>
			User's calculation history<br>
			<%@ page import="com.calculator.MySql, java.util.List"%>
			<%
				MySql sql = new MySql();
				List<String> myresult = sql.selectHistoryCalculate(userID);
				out.println("<textarea readonly style='background-color: #bccd95' name=area cols=100% rows=10>");
				for (String strLogHistory : myresult)
					out.println(strLogHistory);
			%>

			</textarea>
			<%
				String msgError = getFields("Error", request);
				if (!msgError.equals(""))
					out.println("<br><strong><font color='red'>" + msgError
							+ "</strong></font>");
			%>
		</center>

		<div class="box">
			<div class="display">
				<input type="text" readonly name="result"
					value="<%=getFields("Input", request)%>" size="18" id="d">
			</div>
			<div class="keys">
				<p>
					<input type="button" class="button gray" value="(" onclick='v("(")'>
					<input type="button" class="button gray" value=")" onclick='v(")")'>
					<input type="button" class="button pink" value="/" onclick='v("/")'>
					<input type="button" class="button gray" value="<=" onclick='b("")'>
				</p>
				<p>
					<input type="button" class="button black" value="7"
						onclick='v("7")'> <input type="button"
						class="button black" value="8" onclick='v("8")'> <input
						type="button" class="button black" value="9" onclick='v("9")'>
					<input type="button" class="button pink" value="*" onclick='v("*")'>
				</p>
				<p>
					<input type="button" class="button black" value="4"
						onclick='v("4")'> <input type="button"
						class="button black" value="5" onclick='v("5")'> <input
						type="button" class="button black" value="6" onclick='v("6")'>
					<input type="button" class="button pink" value="-" onclick='v("-")'>
				</p>
				<p>
					<input type="button" class="button black" value="1"
						onclick='v("1")'> <input type="button"
						class="button black" value="2" onclick='v("2")'> <input
						type="button" class="button black" value="3" onclick='v("3")'>
					<input type="button" class="button pink" value="+" onclick='v("+")'>
				</p>
				<p>
					<input type="button" class="button black" value="0"
						onclick='v("0")'> <input type="button"
						class="button black" value="C" onclick='c("")'> <input
						type="button" class="button orange" value="=" onclick='e()'>
				</p>
			</div>
		</div>

	</form>
</body>
</html>