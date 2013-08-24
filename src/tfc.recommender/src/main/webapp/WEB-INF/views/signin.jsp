<%@page import="edu.ub.tfc.recommender.domain.User"%>
<%@page import="java.util.List"%>
<%@page import="edu.ub.tfc.recommender.model.Pelicula"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inicial</title>
</head>
<body bgcolor="#F2F2F2">
	<div style="float:right;">Conectado como: <%= ((User)session.getAttribute("user")).getName() %></div>
	<br clear="all"/>
	<form action="/tfc.recommender/webapp" method="post">
		<input type="hidden" name="action" value="search"/>
		<div style="float:right;">Buscar: <input type="text" name="name"/></div>
	</form>
	<div>
		<table width="100%">
			<tr width="100%">
		<% 
			int i = 0;
			for (Pelicula pelicula : (List<Pelicula>)request.getAttribute("peliculas")) {
				if (i % 3 == 0 && i != 0) {
					%>
						</tr><tr width="100%">
					<%
				}
		%>
			<td width="33%"><a href="/tfc.recommender/webapp?action=select&filmId=<%= pelicula.getId() %>"><img width="100" src="<%= pelicula.getUrl() %>" alt="<%= pelicula.getTitulo() %>"/></a><br />
				<span style="font-weight:bold;">Título: </span><%= pelicula.getTitulo() %><br />
				<span style="font-weight:bold;">Género: </span><%= pelicula.getGenero() %></td>
		<% 
				i++;
			} 
		%>
			</tr>
		</table>
	</div>
</body>
</html>