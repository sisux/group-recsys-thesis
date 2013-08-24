<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Configuración</title>
</head>
<body bgcolor="#F2F2F2">
	<h1>Lanzador de pruebas</h1>
	<form action="servlet" method="post">
		<input type="hidden" name="action" value="test"/>
		<h2>Configuraci&oacute;n</h2>
		Usuarios: <select name="totalIterations">
		<%
			for (int i = 1; i <= 943; i++) {
		%>
			<option value="<%=i%>"><%=i%></option>
		<%
			}
		%>			
		</select>
		Elementos a estimar: <select name="itemsEstimados">
		<%
			for (int i = 1; i <= 50; i++) {
		%>
			<option value="<%=i%>"><%=i%></option>
		<%
			}
		%>			
		</select>
		<h2>Seleccione los algoritmos que desee evaluar:</h2>
		
		<table cellspacing="0">
			<tr>
				<th align="center" width="150"></th>
				<th align="center" style="background-color: gray;" width="150">User</th>
				<th align="center" style="background-color: gray;" width="150">Item</th>
			</tr>
			<tr>
				<th align="center" style="background-color: gray;">Pearson</th>
				<td align="center" style="background-color: yellow;"><input type="checkbox" name="userPearson" value="userPearson" /></td>
				<td align="center" style="background-color: blue;"><input type="checkbox" name="itemPearson" value="itemPearson" /></td>
			</tr>
			<tr>
				<th align="center" style="background-color: gray;">Cosine</th>
				<td align="center" style="background-color: yellow;"><input type="checkbox" name="userCosine" value="userCosine" /></td>
				<td align="center" style="background-color: blue;"><input type="checkbox" name="itemCosine" value="itemCosine" /></td>
			</tr>
			<tr>
				<th align="center" style="background-color: gray;">Euclidean</th>
				<td align="center" style="background-color: yellow;"><input type="checkbox" name="userEuclidean" value="userEuclidean" /></td>
				<td align="center" style="background-color: blue;"><input type="checkbox" name="itemEuclidean" value="itemEuclidean" /></td>
			</tr>
			<tr>
				<th align="center" style="background-color: gray;">Spearman</th>
				<td align="center" style="background-color: yellow;"><input type="checkbox" name="userSpearman" value="userSpearman" /></td>
				<td align="center" style="background-color: blue;"><!-- <input type="checkbox" name="itemSpearman" value="itemSpearman" /> --></td>
			</tr>
		</table>
		<br />
		<div style="text-align:right; width: 450px;">
			<input type="submit" name="button" value="Ejecutar prueba"> 
		</div>
	</form>
</body>
</html>