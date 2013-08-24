<%@page import="edu.ub.tfc.recommender.domain.User"%>
<%@page import="java.util.List"%>
<%@page import="edu.ub.tfc.recommender.model.Pelicula"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Elección</title>
</head>
<body bgcolor="#F2F2F2">
	<div style="float:right;">Conectado como: <%= ((User)session.getAttribute("user")).getName() %></div>
	<br clear="all"/>
	<form action="/tfc.recommender/webapp" method="post">
		<input type="hidden" name="action" value="search"/>
		<div style="float:right;">Buscar: <input type="text" name="name"/></div>
	</form>
	<div>
		<% Pelicula pelicula = (Pelicula)request.getAttribute("pelicula"); %>
		<div style="float:left;width:160px;">
			<img src="<%= pelicula.getUrl() %>" />
		</div>
		<div style="float:left;width:300px;">
			<span style="font-weight:bold;">Título: </span><%= pelicula.getTitulo() %><br />
			<span style="font-weight:bold;">Género: </span><%= pelicula.getGenero() %><br />
			<% if (pelicula.getValoracion() < 0) {%>
			<form>
				<input type="hidden" name="action" value="rate"/>
				<input type="hidden" name="filmId" value="<%= pelicula.getId() %>"/>
				<span style="font-weight:bold;">Valorar: </span>
				<select name="rate"><option value="1">1</option>
					
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
				</select><br />
				<input type="submit" value="Valorar"/>
			</form>
			<% } else { %>
			<span style="font-weight:bold;">Tu valoración: </span><%= pelicula.getValoracion() %><br />
			<% } %>
		</div>
	</div>
	<div style="clear:both;">&nbsp;</div>
	<div>
		<table width="100%">
		<tr width="100%">
		<% for (Pelicula p : (List<Pelicula>)request.getAttribute("peliculas")) { %>
			<td width="25%">
				<a href="/tfc.recommender/webapp?action=select&filmId=<%= p.getId() %>"><img width="100" src="<%= p.getUrl() %>" alt="<%= p.getTitulo() %>"/></a><br />
				<span style="font-weight:bold;">Título: </span><%= p.getTitulo() %><br />
				<span style="font-weight:bold;">Género: </span><%= p.getGenero() %>
			</td>			
		<% } %>
		</tr>
		</table>
	</div>
</body>
</html>