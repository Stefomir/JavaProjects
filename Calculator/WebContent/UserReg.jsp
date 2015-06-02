<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="tan">
	<%! private String getFields(String strFieldName, HttpServletRequest request){
	String strVal = (String)request.getAttribute(strFieldName);
	if(strVal == null)
		return "";
	else
		return strVal;
	
}%>
	<center>
		<form action=/Calculator/UserReg method=post>

			<%// Prints error msg 
			String strMsg = (String)request.getAttribute("Error");
			if(strMsg != null){
			if (!(strMsg.equals(""))) { 
				out.println("<br><strong><font color='red'>" + strMsg + "</strong></font><br>");
			}
			}%>
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
								<td ALIGN=left><input type=text name='user_name'
									value='<%= getFields("Username", request)%>'></td>
							</tr>

							<tr>

								<td ALIGN=left>*User Password:</td>
							</tr>

							<tr>

								<td ALIGN=left><input type=text name='user_password'
									value='<%= getFields("Password", request)%>'></td>
							</tr>

							<tr>

								<td ALIGN=left>*Full Name:</td>
							</tr>

							<tr>

								<td LIGN=left><input type=text name='user_fullname'
									value='<%= getFields("Fullname", request)%>'></td>
							</tr>

							<tr>

								<td ALIGN=left>*Email:</td>
							</tr>

							<tr>

								<td LIGN=left><input type=text name='user_email'
									value='<%= getFields("Email", request)%>'></td>
							</tr>

							<tr>
								<td><br></td>
							</tr>

							<tr>
								<td style='width: 33%; padding-left: 20px' ALIGN=left><button
										type=submit style='width: 80%; height: 29px'>Save</button>
									</form></td>
							</tr>

							<tr>
								<td style='width: 33%; padding-left: 20px' ALIGN=left><form
										action=/Calculator/Login.jsp method=get>
										<button type=submit style='width: 80%; height: 29px'>Back</button>
									</form></td>
							</tr>

						</table>
						</center>
</body>

</html>
