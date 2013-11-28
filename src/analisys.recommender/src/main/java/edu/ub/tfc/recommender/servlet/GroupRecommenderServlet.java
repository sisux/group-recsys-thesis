package edu.ub.tfc.recommender.servlet;

import static edu.ub.tfc.recommender.utils.Utils.escribirLog;
import static edu.ub.tfc.recommender.utils.Utils.streamBinaryData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.GenericJDBCDataModel;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.ub.tfc.recommender.bean.GRSAnalyserResult;
import edu.ub.tfc.recommender.bean.Resultado;
import edu.ub.tfc.recommender.groups.GRSAnalyser;
import edu.ub.tfc.recommender.services.RecommenderService;
import edu.ub.tfc.recommender.services.impl.RecommenderServiceType;

/**
 * Servlet
 * @author nmargall
 */
public class GroupRecommenderServlet extends HttpServlet {

	/* ****************************
				CONSTANTS
	 * *************************** */
	
	/* *** BEANS *********** */
	private static final String BEAN_TRAINMODEL = "trainModel";
	private static final String BEAN_TESTMODEL = "testModel";
	private static final String BEAN_NOMBREFICHEROCSV = "nombreFicheroCsv";
	private static final String BEAN_PATHFICHEROCSV = "nombreFicheroCsv";

	/* *** JSP PARAMETERS *********** */
	private static final String PARAMETER_ACTION = "action";
	private static final String VALUE_ACTION_TEST = "test";
	private static final String VALUE_ACTION_DOWNLOAD = "download";
	
	private static final String PARAMETER_MAXGROUPS = "maxGroups";
	private static final String PARAMETER_MAXRECOMMENDEDITEMSPERGROUP = "maxRecommendedItemsPerGroup";
	private static final String PARAMETER_GROUPTYPE = "groupType";
	private static final String PARAMETER_GROUPLENGTH = "groupLength";
		
	/* *** JSP ATTRIBUTES *********** */
	public static final String ATTRIBUTE_TESTCASE = "testCase";
		
	private static final String GROUP_EUCLIDEAN = "gEuclidean";
	private static final String GROUP_AVERAGE = "gAverage";
	
	private static final String CONFIGURACION_JSP = "/WEB-INF/views/GroupConfiguracion.jsp";
	private static final String RESULTADOS_JSP = "/WEB-INF/views/GroupResultados.jsp";
	private static final long serialVersionUID = 1L;

	/* ****************************
			PUBLIC METHODS
	 * *************************** */
	
	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final String action = request.getParameter(PARAMETER_ACTION);

		if (action != null) {
			final WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		
			if (action.equals(VALUE_ACTION_TEST)) {
				doGet_test(request, response, springContext);
			} else if (action.equals(VALUE_ACTION_DOWNLOAD)) {
				doGet_download(request, response, springContext);
			}
		} else {
			doGet_redirect(request, response);
		}
	}

	/* ****************************
			PRIVATE METHODS
	 * *************************** */
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doGet_redirect(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(CONFIGURACION_JSP);
		dispatcher.forward(request, response);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param springContext
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doGet_test(final HttpServletRequest request, final HttpServletResponse response, final WebApplicationContext springContext) throws ServletException, IOException {
		String nombreFicheroCsv = (String) springContext.getBean(BEAN_NOMBREFICHEROCSV);
		String pathFicheroCsv = (String) springContext.getBean(BEAN_PATHFICHEROCSV);
		GenericJDBCDataModel trainModel = (GenericJDBCDataModel) springContext.getBean(BEAN_TRAINMODEL);
		GenericJDBCDataModel testModel = (GenericJDBCDataModel) springContext.getBean(BEAN_TESTMODEL);

		final Integer tmpMaxGroups = Integer.valueOf(request.getParameter(PARAMETER_MAXGROUPS));
		final Integer tmpMaxRecommendedItemsPerGroup = Integer.valueOf(request.getParameter(PARAMETER_MAXRECOMMENDEDITEMSPERGROUP));
		final GroupType tmpGroupType = GroupType.valueOf(GroupType.class, request.getParameter(PARAMETER_GROUPTYPE));
		final String tmpGroupLength = "all".equalsIgnoreCase(request.getParameter(PARAMETER_GROUPLENGTH)) ? "*" : request.getParameter(PARAMETER_GROUPLENGTH);

		List<String> serviceNames = new ArrayList<String>();
		if (request.getParameter(GROUP_EUCLIDEAN) != null) {
			escribirLog("EVALUACION EUCLIDEAN");
			serviceNames.add(RecommenderServiceType.GROUP_EUCLIDEAN_SERVICE.toString());
		}
		if (request.getParameter(GROUP_AVERAGE) != null) {
			escribirLog("EVALUACION AVERAGE");
			serviceNames.add(RecommenderServiceType.GROUP_AVERAGE_SERVICE.toString());
		}
		final Map<String, RecommenderService> recommenderServices = getServices(serviceNames, springContext);

		try {
			GRSAnalyser tmpGRSAnalyser = new GRSAnalyser(trainModel, testModel);
			tmpGRSAnalyser.setNombreFicheroCsv(nombreFicheroCsv);
			tmpGRSAnalyser.setPathFicheroCsv(pathFicheroCsv);
			
			GRSAnalyserResult testCases = tmpGRSAnalyser.performAnalisys(recommenderServices, tmpMaxGroups, tmpMaxRecommendedItemsPerGroup);
			request.setAttribute(ATTRIBUTE_TESTCASE, testCases);
		} catch (TasteException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(RESULTADOS_JSP);
		dispatcher.forward(request, response);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param springContext
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doGet_download(final HttpServletRequest request, final HttpServletResponse response, final WebApplicationContext springContext) throws ServletException, IOException {
		final String nombreFicheroCsv = (String) springContext.getBean(BEAN_NOMBREFICHEROCSV);
		final String pathFicheroCsv = (String) springContext.getBean(BEAN_PATHFICHEROCSV);
		
		final ServletOutputStream sOutStream = response.getOutputStream();
		streamBinaryData(pathFicheroCsv, nombreFicheroCsv, sOutStream, response);
	}
	
	/**
	 * Obtiene los servicios de recomendación a partir de sus nombres
	 * @param serviceNames
	 * @param springContext
	 * @return
	 */
	private Map<String, RecommenderService> getServices(
			final List<String> serviceNames,
			final WebApplicationContext springContext) {
		escribirLog("CARGANDO SERVICIOS");
		final Map<String, RecommenderService> recommenderServices = new HashMap<String, RecommenderService>();
		for (final String serviceName : serviceNames) {
			final RecommenderService recommenderService = (RecommenderService) springContext.getBean(serviceName);
			recommenderServices.put(serviceName, recommenderService);
		}
		return recommenderServices;
	}
}
