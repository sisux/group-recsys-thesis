<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="edu.ub.tfc.recommender.servlet.GroupRecommenderServlet"%>
<%@page import="edu.ub.tfc.recommender.bean.Resultado"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Group Resultados Test</title>
</head>
<body bgcolor="#F2F2F2">
	<h1>Group Resultados de las pruebas:</h1>
	<%
		Map<Long, Resultado> testCase = (Map<Long, Resultado>)request.getAttribute(GroupRecommenderServlet.ATTRIBUTE_TESTCASE);
		List<Long> keys = new ArrayList<Long>(testCase.keySet());
		Collections.sort(keys);
	%>
		<div style="float:left;width:100%">
			<table cellpadding="5" cellspacing="0">
				<tr>
					<th style="background-color: gray;"></th>
					<th style="background-color: gray;"></th>
					<th colspan="3" style="background-color: gray;">User Pearson</th>
					<th colspan="3" style="background-color: gray;">User Cosine</th>
					<th colspan="3" style="background-color: gray;">User Euclidean</th>
					<th colspan="3" style="background-color: gray;">User Spearman</th>
					<th colspan="3" style="background-color: gray;">Item Pearson</th>
					<th colspan="3" style="background-color: gray;">Item Cosine</th>
					<th colspan="3" style="background-color: gray;">Item Euclidean</th>
					<th colspan="3" style="background-color: gray;">Item Spearman</th>
				</tr>
				<tr>
					<th style="background-color: gray;">Iteración</th>
					<th style="background-color: gray;">Grupo</th>
					<th style="background-color: gray;">MAE</th>
					<th style="background-color: gray;">RMSE</th>
					<th style="background-color: gray;">Tiempo (ms)</th>
					<th style="background-color: gray;">MAE</th>
					<th style="background-color: gray;">RMSE</th>
					<th style="background-color: gray;">Tiempo (ms)</th>
					<th style="background-color: gray;">MAE</th>
					<th style="background-color: gray;">RMSE</th>
					<th style="background-color: gray;">Tiempo (ms)</th>
					<th style="background-color: gray;">MAE</th>
					<th style="background-color: gray;">RMSE</th>
					<th style="background-color: gray;">Tiempo (ms)</th>
					<th style="background-color: gray;">MAE</th>
					<th style="background-color: gray;">RMSE</th>
					<th style="background-color: gray;">Tiempo (ms)</th>
					<th style="background-color: gray;">MAE</th>
					<th style="background-color: gray;">RMSE</th>
					<th style="background-color: gray;">Tiempo (ms)</th>
					<th style="background-color: gray;">MAE</th>
					<th style="background-color: gray;">RMSE</th>
					<th style="background-color: gray;">Tiempo (ms)</th>
					<th style="background-color: gray;">MAE</th>
					<th style="background-color: gray;">RMSE</th>
					<th style="background-color: gray;">Tiempo (ms)</th>
				</tr>
			<%
				int i = 0;
					for (Long key : keys) {
						Resultado evaluation = testCase.get(key);
						i++;
			%>
				<tr>
					<td style="text-align: center;background-color: gray;"><%= i %></td>
					<td style="text-align: center;background-color: gray;"><%= key %></td>
					<td style="text-align: right;background-color: yellow;"><%= evaluation.getUserPearsonMae() %></td>
					<td style="text-align: right;background-color: yellow;"><%= evaluation.getUserPearsonRmse() %></td>
					<td style="text-align: right;background-color: yellow;"><%= evaluation.getUserPearsonTime() %></td>
					<td style="text-align: right;background-color: blue;"><%= evaluation.getUserCosineMae() %></td>
					<td style="text-align: right;background-color: blue;"><%= evaluation.getUserCosineRmse() %></td>
					<td style="text-align: right;background-color: blue;"><%= evaluation.getUserCosineTime() %></td>
					<td style="text-align: right;background-color: yellow;"><%= evaluation.getUserEuclideanMae() %></td>
					<td style="text-align: right;background-color: yellow;"><%= evaluation.getUserEuclideanRmse() %></td>
					<td style="text-align: right;background-color: yellow;"><%= evaluation.getUserEuclideanTime() %></td>
					<td style="text-align: right;background-color: blue;"><%= evaluation.getUserSpearmanMae() %></td>
					<td style="text-align: right;background-color: blue;"><%= evaluation.getUserSpearmanRmse() %></td>
					<td style="text-align: right;background-color: blue;"><%= evaluation.getUserSpearmanTime() %></td>					
					<td style="text-align: right;background-color: yellow;"><%= evaluation.getItemPearsonMae() %></td>
					<td style="text-align: right;background-color: yellow;"><%= evaluation.getItemPearsonRmse() %></td>
					<td style="text-align: right;background-color: yellow;"><%= evaluation.getItemPearsonTime() %></td>
					<td style="text-align: right;background-color: blue;"><%= evaluation.getItemCosineMae() %></td>
					<td style="text-align: right;background-color: blue;"><%= evaluation.getItemCosineRmse() %></td>
					<td style="text-align: right;background-color: blue;"><%= evaluation.getItemCosineTime() %></td>
					<td style="text-align: right;background-color: yellow;"><%= evaluation.getItemEuclideanMae() %></td>
					<td style="text-align: right;background-color: yellow;"><%= evaluation.getItemEuclideanRmse() %></td>
					<td style="text-align: right;background-color: yellow;"><%= evaluation.getItemEuclideanTime() %></td>
					<td style="text-align: right;background-color: blue;"><%= evaluation.getItemSpearmanMae() %></td>
					<td style="text-align: right;background-color: blue;"><%= evaluation.getItemSpearmanRmse() %></td>
					<td style="text-align: right;background-color: blue;"><%= evaluation.getItemSpearmanTime() %></td>
				</tr>	
				<%
			}
				%>
				<tr>
					<td align="left"><a href="servlet">Volver</a></td>
					<td colspan="13" align="right"><a href="servlet?action=download">Descargar</a></td>
				</tr>
			</table>
		</div>
	
</body>
</html>