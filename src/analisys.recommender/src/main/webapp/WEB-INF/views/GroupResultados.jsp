<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="edu.ub.tfc.recommender.servlet.GroupRecommenderServlet"%>
<%@page import="edu.ub.tfc.recommender.utils.JspUtils"%>
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
		
		List<String> tmpDataElementsToPresent = Arrays.asList(GroupEvaluation.GROUP_ID, GroupEvaluation.GROUP_DESCRIPTION, GroupEvaluation.RECOMMENDATION_SERVICE, GroupEvaluation.ELICITATION_STRATEGY, GroupEvaluation.NUM_OF_ITEMS_TO_RECOMMEND, GroupEvaluation.MAE_METRIC_NAME, GroupEvaluation.RMSE_METRIC_NAME);
	%>
		<div style="float:left;width:100%">
			<table cellpadding="5" cellspacing="0">
				<tr>
					<th style="background-color: gray;">Iteración</th>
				<%
					for (String tmpDataElement : tmpDataElementsToPresent) {
				%>
					<th style="background-color: gray;"><%= tmpDataElement %></th>					
				<% 
					}
				%>
				</tr>
			<%
				int i = 0;	
				for (Long theGroupId : tmpGroupIds) {
					List<GroupEvaluation> tmpGroupEvaluations = testCases.getAllGroupEvaluationsByGroupId(theGroupId);
					i++;
					for(GroupEvaluation tmpGroupEvaluation : tmpGroupEvaluations) {
			%>
				<tr>
					<td style="text-align: center;background-color: gray;"><%= i %></td>
			<%
						for (String tmpDataElement : tmpDataElementsToPresent) {
			%>
					<td style="text-align: center;background-color: gray;"><%= JspUtils.getStringForHtmlCell(tmpGroupEvaluation.getItemValue(tmpDataElement)) %></td>
			<% 
						}
			%>
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