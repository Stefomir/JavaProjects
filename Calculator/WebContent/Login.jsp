<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Calculator</title>
</head>
<body bgcolor="tan">
	<form action=/Calculator/Login method=post>
		<center>
			<h3>Calculator</h3>
		</center>
		<center>
			<%
				session.invalidate();
				String strMsg = (String) request.getAttribute("Error");
				if (strMsg != null) {
					// Prints error msg
					if (!(strMsg.equals(""))) {
						out.println("<strong><font color='red'>" + strMsg
								+ "</strong></font><br>");
					}
				}
			%>
			<table cols=1 bgcolor="">
				<tr>
					<TD ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Identification</strong></TD>
				</tr>
				<tr>
					<td><table cols=4 width=100%>
							<tr>
								<td ALIGN=left>Account</td>
							</tr>
							<tr>
								<td ALIGN=left><input type=text name='login_username'
									value=''></td>
							</tr>
							<tr>
								<td ALIGN=left>Password</td>
							</tr>
							<tr>
								<td ALIGN=left><input type=password name='secretkey'></td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td><br>
						<center>
							<input type=submit style='width: 50%; height: 29px' value=Login>
						</center>
						<center>
							<a href=/Calculator/UserReg.jsp>Registration</a>
						</center></td>
				</tr>
			</table>
		</center>
	</form>
</body>
</html>