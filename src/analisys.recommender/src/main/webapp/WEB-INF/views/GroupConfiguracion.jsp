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
		<h2>1. Test Configuraci&oacute;n</h2>
		
		<h3>1.1. Data selection</h3>
		Group Type: <select name="groupType">
			<option value="all">all</option>
			<option value="similar" selected>similar</option>
			<option value="dissimilar">dissimilar</option>
		</select>
				
		Group Length: <select name="groupLength">
			<option value="all">all</option>
			<option value="length_3" selected>3</option>
			<option value="length_5">5</option>
			<option value="length_7">7</option>
		</select>
		
		Max Groups: <select name="maxGroups">
			<option value="<%=Integer.MAX_VALUE %>">all</option>
		<%
			for (int i = 1; i <= 840; i++) {
				if(i == 5) {
		
		%>
				<option value="<%=i%>" selected><%=i%></option>
		<%
				} else {
		%>
					<option value="<%=i%>"><%=i%></option>
		<%			
				}
			}
		%>			
		</select>
		
		<h3>1.2. Group Recommendation</h3>
		
		Max Recommended items per Group: <select name="maxRecommendedItemsPerGroup">
		<%
			for (int i = 5; i <= 20; i++) {
		%>
			<option value="<%=i%>"><%=i%></option>
		<%
			}
		%>			
		</select>
		<br /><br />
		Seleccione los algoritmos que desee evaluar:
		
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
			<tr>
				<th align="center" style="background-color: gray;">Multiplicative</th>
				<td align="center" style="background-color: yellow;"><input type="checkbox" name="gMultiplicative" value="gMultiplicative" checked/></td>
			</tr>			
		</table>
		<br />
		<div style="text-align:right; width: 450px;">
			<input type="submit" name="button" value="Ejecutar prueba"> 
		</div>
	</form>
</body>
</html>