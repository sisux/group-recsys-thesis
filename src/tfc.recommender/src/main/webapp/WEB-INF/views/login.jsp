<%@page import="java.util.List"%>
<%@page import="edu.ub.tfc.recommender.domain.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body bgcolor="#F2F2F2">
	<form action="/tfc.recommender/webapp" method="post">
		<input type="hidden" name="action" value="signIn"/>
		<select name="userId">
			<% 
			int i = 0;
			for (User user : (List<User>)request.getAttribute("users")) {
				%><option value="<%= user.getUserId() %>"><%= user.getName() %></option><%
			}
			%>
		</select>
		<input type="submit" value="Login"></input>
	</form>
</body>
</html>