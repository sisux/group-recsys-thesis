<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Group Configuración</title>
</head>
<body bgcolor="#F2F2F2">
	<h1>Lanzador de pruebas</h1>
	<form action="servlet" method="post">
		<input type="hidden" name="action" value="test"/>
		<h2>Group Configuraci&oacute;n</h2>
		
		Group Type: <select name="groupType">
			<option value="all">all</option>
			<option value="similar" selected>similar</option>
			<option value="dissimilar">dissimilar</option>
		</select>
				
		Group Length: <select name="groupLength">
			<option value="all">all</option>
			<option value="3" selected>3</option>
			<option value="5">5</option>
			<option value="7">7</option>
		</select>
		
		Groups: <select name="maxGroups">
		<%
			for (int i = 1; i <= 840; i++) {
		%>
			<option value="<%=i%>"><%=i%></option>
		<%
			}
		%>			
		</select>
		Max Recommended items per Group: <select name="maxRecommendedItemsPerGroup">
		<%
			for (int i = 5; i <= 20; i++) {
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
				<th align="center" style="background-color: gray;" width="150">Stretegy</th>
			</tr>
			<tr>
				<th align="center" style="background-color: gray;">Average</th>
				<td align="center" style="background-color: yellow;"><input type="checkbox" name="gAverage" value="gAverage" checked/></td>
			</tr>
			<tr>
				<th align="center" style="background-color: gray;">Euclidean</th>
				<td align="center" style="background-color: yellow;"><input type="checkbox" name="gEuclidean" value="gEuclidean" /></td>
			</tr>
		</table>
		<br />
		<div style="text-align:right; width: 450px;">
			<input type="submit" name="button" value="Ejecutar prueba"> 
		</div>
	</form>
</body>
</html>