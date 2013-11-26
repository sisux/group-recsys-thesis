package edu.ub.tfc.recommender.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet
 * 
 * @author David Gil
 */
public class WebAppServlet extends HttpServlet {

	// Constantes
	private static final String ACTION = "action";
	private static final String SELECT_ACTION = "select";
	private static final String SEARCH_ACTION = "search";
	private static final String SIGN_IN_ACTION = "signIn";
	private static final String GET_COVER_ACTION = "getCover";
	private static final String RATE_ACTION = "rate";
	private static final String LOGIN_JSP = "/WEB-INF/views/login.jsp";
	private static final String SIGN_IN_JSP = "/WEB-INF/views/signin.jsp";
	private static final String SEARCH_JSP = "/WEB-INF/views/resultados.jsp";
	private static final String SELECT_JSP = "/WEB-INF/views/eleccion.jsp";

	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			final WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
			
			// Si se trata de la primera carga de la aplicacion, aparecera la pantalla Login.
			if (request.getParameterMap().size() == 0) {
				Actions.login(springContext, request, response);
				final RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(LOGIN_JSP);
				dispatcher.forward(request, response);

			// Carga la caratula de la pelicula seleccionada.
			} else if (StringUtils.equals(request.getParameter(ACTION),GET_COVER_ACTION)) {
				Actions.getCover(springContext, request, response);

			// Inicia la sesion de un usuario.
			} else if (StringUtils.equals(request.getParameter(ACTION),SIGN_IN_ACTION)) {
				Actions.signIn(springContext, request, response);
				final RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(SIGN_IN_JSP);
				dispatcher.forward(request, response);
			
			// Muestra los resultados de una bœsqueda de pel’cula.
			} else if (StringUtils.equals(request.getParameter(ACTION),SEARCH_ACTION)) {
				Actions.search(springContext, request, response);
				final RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(SEARCH_JSP);
				dispatcher.forward(request, response);
				
			// Muestra la pelicula elegida, su informaci—n y una serie de recomendaciones en base a la pelicula seleccionada.
			} else if (StringUtils.equals(request.getParameter(ACTION),SELECT_ACTION)) {
				Actions.select(springContext, request, response);
				final RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(SELECT_JSP);
				dispatcher.forward(request, response);
				
			// Valora la pel’cula seleccionada.
			} else if (StringUtils.equals(request.getParameter(ACTION),RATE_ACTION)) {
				Actions.rate(springContext, request, response);
				Actions.select(springContext, request, response);
				final RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(SELECT_JSP);
				dispatcher.forward(request, response);
			}
		} catch (final TasteException e) {
			System.out.println("Error");
		}
	}
}
