/**
 * 
 */
package edu.ub.tfc.recommender.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.web.context.WebApplicationContext;

import edu.ub.tfc.recommender.dao.FilmDao;
import edu.ub.tfc.recommender.dao.UserDao;
import edu.ub.tfc.recommender.domain.Film;
import edu.ub.tfc.recommender.domain.User;
import edu.ub.tfc.recommender.model.Pelicula;
import edu.ub.tfc.recommender.services.RecommenderInterface;

/**
 * @author David Gil
 * 
 */
public class Actions {
	private static final String IMAGE_URL = "/tfc.recommender/webapp?action=getCover&filmId=";
	private final static int IMAGE_WIDTH = 150;
	private final static int IMAGE_HEIGHT = 220;

	/**
	 * Muestra la pantalla de login para elegir el usuario con que se quiere iniciar sesión.
	 * @param springContext Contexto de Spring
	 * @param request Petición HTTP
	 * @param response Respuesta HTTP
	 * @throws ServletException Excepción
	 * @throws IOException Excepción de I/O
	 */
	public static void login(final WebApplicationContext springContext, final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException {
		
		final UserDao dao = (UserDao) springContext.getBean("userDao");

		/* DESCOMENTAR LA PRIMERA VEZ QUE SE ARRANQUE, LA TABLA USERS DEBE ESTAR VACIA
		for (int i = 1; i <= 6040; i++)
			dao.insertUser(new User(i, "Usuario_" + i));
		*/
		final List<User> users = dao.selectAllUsers();
		request.setAttribute("users", users);
	}

	/**
	 * Genera la imagen que corresponde a la carátula de la película seleccionada.
	 * @param springContext Contexto de Spring
	 * @param request Petición HTTP
	 * @param response Respuesta HTTP
	 * @throws ServletException Excepción
	 * @throws IOException Excepción de I/O
	 */
	public static void getCover(final WebApplicationContext springContext, final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException {
		
		final FilmDao dao = (FilmDao) springContext.getBean("filmDao");

		final int filmId = Integer.valueOf(request.getParameter("filmId"));
		final Film film = dao.selectFilmById(filmId);
		final String filmName = film.getName();

		final BufferedImage bufferedImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2d = bufferedImage.createGraphics();
		final RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		final GradientPaint gp = new GradientPaint(0, 0, Color.black, 0, IMAGE_HEIGHT / 2, Color.black, true);

		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setFont(new Font("Georgia", Font.BOLD, 18));
		g2d.setPaint(gp);
		g2d.setRenderingHints(rh);
		g2d.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
		g2d.setColor(new Color(255, 153, 0));

		final char[] sAr = filmName.toCharArray();
		int start = 0;
		int j = 100;
		for (int i = 13; i < sAr.length; i++) {
			if (sAr[i] == ' ') {
				final String substring = filmName.substring(start, i);
				g2d.drawString(substring, 10, j);
				start = i + 1;
				i += 13;
				j = j + 20;
			}
		}
		g2d.drawString(filmName.substring(start), 10, j);
		g2d.dispose();

		response.setContentType("image/png");
		final OutputStream os = response.getOutputStream();
		ImageIO.write(bufferedImage, "png", os);
		os.close();
	}

	/**
	 * Inicia la sesión de un usuario y le recomienda películas en base a su perfil de usuario.
	 * El algoritmo de recomendación utilizado es el basado en usuario con la técnica de similitud Correlación de Pearson.
	 * @param springContext Contexto de Spring
	 * @param request Petición HTTP
	 * @param response Respuesta HTTP
	 * @throws ServletException Excepión
	 * @throws IOException Excepción de I/O
	 * @throws TasteException Excepción del recomendador
	 */
	public static void signIn(final WebApplicationContext springContext, final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException, TasteException {
		
		final Long userId = Long.valueOf(request.getParameter("userId"));
		final FilmDao filmDao = (FilmDao) springContext.getBean("filmDao");
		final UserDao userDao = (UserDao) springContext.getBean("userDao");

		final User user = userDao.selectUser(userId.intValue());

		final RecommenderInterface recommenderService = (RecommenderInterface) springContext.getBean("userPearsonService");
		final GenericUserBasedRecommender recommender = (GenericUserBasedRecommender) recommenderService.getRecommender();
		
		final List<Pelicula> list = new ArrayList<Pelicula>();

		// Se ejecutan las recomendaciones para el usuario registrado.
		final List<RecommendedItem> recommend = recommender.recommend(userId, 9);
		
		// Para cada recomendación, se busca su información en la base de datos. 
		for (final RecommendedItem item : recommend) {
			final Long itemId = new Long(item.getItemID());
			final int filmId = itemId.intValue();

			final Film film = filmDao.selectFilmById(filmId);

			final String coverUrl = IMAGE_URL+film.getFilmId();
			final Pelicula pelicula = new Pelicula(film.getFilmId(), film.getName(), film.getFilmGenre(), coverUrl);
			list.add(pelicula);
		}

		request.setAttribute("peliculas", list);
		request.getSession().setAttribute("user", user);
	}

	/**
	 * Muestra los resultados de una búsqueda de película.
	 * @param springContext Contexto de Spring
	 * @param request Petición HTTP
	 * @param response Respuesta http
	 * @throws ServletException Excepción
	 * @throws IOException Excepción de I/O
	 * @throws TasteException Excepción del recomendador
	 */
	public static void search(final WebApplicationContext springContext, final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException, TasteException {
		
		final FilmDao dao = (FilmDao) springContext.getBean("filmDao");
		final String name = request.getParameter("name");

		final List<Pelicula> list = new ArrayList<Pelicula>();

		final List<Film> films = dao.selectFilmByName(name);
		for (final Film film: films) {
			final String coverUrl = IMAGE_URL+film.getFilmId();
			final Pelicula pelicula = new Pelicula(film.getFilmId(), film.getName(), film.getFilmGenre(), coverUrl);
			list.add(pelicula);
		}
		request.setAttribute("peliculas", list);
	}

	/**
	 * Muestra la pelicula elegida, su información y una serie de recomendaciones en base a la pelicula seleccionada.
	 * El algoritmo de recomendación es el basado en ítem y la técnica de similitud es la Correlación de Pearson.
	 * @param springContext Contexto de Spring
	 * @param request Petición HTTP
	 * @param response Respuesta HTTP
	 * @throws ServletException Excepción
	 * @throws IOException Excepción de I/O
	 * @throws TasteException Excepción del recomendador
	 */
	public static void select(final WebApplicationContext springContext, final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException, TasteException {
		
		final FilmDao dao = (FilmDao) springContext.getBean("filmDao");
		final Long selectedFilmId = Long.valueOf(request.getParameter("filmId"));

		final RecommenderInterface recommenderService = (RecommenderInterface) springContext.getBean("itemPearsonService");
		final GenericItemBasedRecommender recommender = (GenericItemBasedRecommender) recommenderService.getRecommender();

		final List<Pelicula> list = new ArrayList<Pelicula>();

		// Se ejecutan las recomendaciones para la película seleccionada.
		final List<RecommendedItem> recommend = recommender.mostSimilarItems(selectedFilmId, 4);
		
		// Para cada recomendación, se busca su información en la base de datos.
		for (final RecommendedItem item : recommend) {
			final Long itemId = new Long(item.getItemID());
			final int filmId = itemId.intValue();
			final Film film = dao.selectFilmById(filmId);

			final String coverUrl = IMAGE_URL+film.getFilmId();
			final Pelicula pelicula = new Pelicula(film.getFilmId(), film.getName(), film.getFilmGenre(), coverUrl);
			list.add(pelicula);
		}

		request.setAttribute("peliculas", list);

		// Si la película seleccionada ya ha sido valorada por el usuario registrado, también se muestra la valoración.
		final int filmId = selectedFilmId.intValue();
		final Film film = dao.selectFilmById(filmId);

		final String coverUrl = IMAGE_URL+film.getFilmId();
		final Pelicula pelicula = new Pelicula(film.getFilmId(), film.getName(), film.getFilmGenre(), coverUrl);

		final User user = (User)request.getSession().getAttribute("user");
		final Float preferenceValue = recommender.getDataModel().getPreferenceValue(user.getUserId(), filmId);
		if (preferenceValue != null)
			pelicula.setValoracion(preferenceValue.intValue());
		else
			pelicula.setValoracion(-1);

		request.setAttribute("pelicula", pelicula);
	}

	/**
	 * Valora la película seleccionada.
	 * @param springContext Contexto de Spring
	 * @param request Petición HTTP
	 * @param response Respuesta HTTP
	 * @throws ServletException Excepción
	 * @throws IOException Excepción de I/O
	 * @throws TasteException Excepción del recomendador
	 */
	public static void rate(final WebApplicationContext springContext, final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException, TasteException {
		
		final Long filmId = Long.valueOf(request.getParameter("filmId"));
		final Long rate = Long.valueOf(request.getParameter("rate"));
		final User user = (User)request.getSession().getAttribute("user");

		final RecommenderInterface recommenderService = (RecommenderInterface) springContext.getBean("itemPearsonService");
		final GenericItemBasedRecommender recommender = (GenericItemBasedRecommender) recommenderService.getRecommender();

		recommender.setPreference(user.getUserId(), filmId, rate);
	}
}
