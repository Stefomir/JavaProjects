<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript" language="Javascript">
	var rootURL = "http://213.169.35.9/CalculatorProject/rest/calc/";

	function validate() {
		if (document.getElementById("user_name").value === ""
				&& document.getElementById("user_password").value === ""
				&& document.getElementById("user_fullname").value === ""
				&& document.getElementById("user_email").value === "") {
			alert("Please, fill the fields with *");
			return;
		}
		if (document.getElementById("user_name").value === "") {
			alert("Please, enter a Username");
			return;
		}
		if (document.getElementById("user_password").value === "") {
			alert("Please, enter a password");
			return;
		}
		if (document.getElementById("user_fullname").value === "") {
			alert("Please, enter your fullname");
			return;
		}
		if (document.getElementById("user_email").value === "") {
			alert("Please, enter your email");
			return;
		}
	}

	function renderList(data) {
		var str = "";
		str = data.toString();
		if (data.indexOf("Error:") == 0) {
			alert(str);
		} else {
			alert("Success!");
			window.location.replace("/CalculatorProject");
		}
	}

	function e() {
		validate();
		$.ajax({
			type : 'POST',
			url : rootURL + document.getElementById("user_name").value + "/"
					+ document.getElementById("user_password").value + "/"
					+ document.getElementById("user_fullname").value + "/"
					+ document.getElementById("user_email").value,
			dataType : "text",
			success : renderList
		});
	}
</script>

</head>
<body bgcolor="tan">
	<%!private String getFields(String strFieldName, HttpServletRequest request) {
		String strVal = (String) request.getAttribute(strFieldName);
		if (strVal == null)
			return "";
		else
			return strVal;

	}%>
	<center>
		<table cols=1>
			<tr>
				<td ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Register</strong></td>
			</tr>
			<tr>
				<td><table cols=4 width=90%>
						</td>
						</tr>
						<tr>
							<td ALIGN=left>*User Name:</td>
						</tr>
						<tr>
							<td ALIGN=left><input type=text id='user_name'
								value='<%=getFields("Username", request)%>'></td>
						</tr>

						<tr>

							<td ALIGN=left>*User Password:</td>
						</tr>

						<tr>

							<td ALIGN=left><input type=text id='user_password'
								value='<%=getFields("Password", request)%>'></td>
						</tr>

						<tr>

							<td ALIGN=left>*Full Name:</td>
						</tr>

						<tr>

							<td ALIGN=left><input type=text id='user_fullname'
								value='<%=getFields("Fullname", request)%>'></td>
						</tr>

						<tr>

							<td ALIGN=left>*Email:</td>
						</tr>

						<tr>

							<td ALIGN=left><input type=text id='user_email'
								value='<%=getFields("Email", request)%>'></td>
						</tr>

						<tr>
							<td><br></td>
						</tr>

						<tr>
							<td style='width: 33%; padding-left: 20px' ALIGN=left><input
								type="button" style='width: 80%; height: 29px' onclick='e()'
								value='Save'></td>
						</tr>

						<tr>
							<td style='width: 33%; padding-left: 20px' ALIGN=left><form
									action=/CalculatorProject/Login.jsp method=get>
									<button type=submit style='width: 80%; height: 29px'>Back</button>
								</form></td>
						</tr>
					</table>
		</table>
	</center>
</body>
</html>
