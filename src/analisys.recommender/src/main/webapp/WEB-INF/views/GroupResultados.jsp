<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="edu.ub.tfc.recommender.servlet.GroupRecommenderServlet"%>
<%@page import="edu.ub.tfc.recommender.bean.GRSAnalyserResult"%>
<%@page import="edu.ub.tfc.recommender.bean.GroupEvaluation"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Group Results Test</title>
</head>
<body bgcolor="#F2F2F2">
	<h1>Group Resultados de las pruebas:</h1>
	<%
		GRSAnalyserResult testCases = (GRSAnalyserResult)request.getAttribute(GroupRecommenderServlet.ATTRIBUTE_TESTCASE);
		List<Long> tmpGroupIds = testCases.getAllGroupsId();
		Collections.sort(tmpGroupIds);
	%>
		<div style="float:left;width:100%">
			<table cellpadding="5" cellspacing="0">
				<tr>
					<th style="background-color: gray;">Iteración</th>
					<th style="background-color: gray;"><%= GroupEvaluation.GROUP_DESCRIPTION %></th>
					<th style="background-color: gray;"><%= GroupEvaluation.RECOMMENDATION_SERVICE %></th>
					<th style="background-color: gray;"><%= GroupEvaluation.ELICITATION_STRATEGY %></th>
					<th style="background-color: gray;"><%= GroupEvaluation.MAE_METRIC_NAME %></th>
					<th style="background-color: gray;"><%= GroupEvaluation.RMSE_METRIC_NAME %></th>
				</tr>
			<%
				int i = 0;	
				for (final Long theGroupId : tmpGroupIds) {
					List<GroupEvaluation> tmpGroupEvaluations = testCases.getAllGroupEvaluationsByGroupId(theGroupId);
					for(final GroupEvaluation tmpGroupEvaluation : tmpGroupEvaluations) {
						i++;
			%>
				<tr>
					<td style="text-align: center;background-color: gray;"><%= i %></td>
					<td style="text-align: center;background-color: gray;"><%= tmpGroupEvaluation.getItemValue(GroupEvaluation.GROUP_ID) %></td>
					<td style="text-align: right;background-color: yellow;"><%= tmpGroupEvaluation.getItemValue(GroupEvaluation.GROUP_DESCRIPTION) %></td>
					<td style="text-align: right;background-color: yellow;"><%= tmpGroupEvaluation.getItemValue(GroupEvaluation.RECOMMENDATION_SERVICE) %></td>
					<td style="text-align: right;background-color: yellow;"><%= tmpGroupEvaluation.getItemValue(GroupEvaluation.ELICITATION_STRATEGY) %></td>
					<td style="text-align: right;background-color: blue;"><%= tmpGroupEvaluation.getItemValue(GroupEvaluation.MAE_METRIC_NAME) %></td>
					<td style="text-align: right;background-color: blue;"><%= tmpGroupEvaluation.getItemValue(GroupEvaluation.RMSE_METRIC_NAME) %></td>
				</tr>	
			<%
					}
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