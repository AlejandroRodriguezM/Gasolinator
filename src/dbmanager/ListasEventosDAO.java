package dbmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import EventoManagement.Evento;
import funcionesAuxiliares.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Esta clase maneja las operaciones relacionadas con la gestión de listas y
 * eventos en la base de datos.
 */
public class ListasEventosDAO {

	// Listas de eventos
	public static List<Evento> listaEventos = new ArrayList<>();

	public static List<Evento> listaEventosCheck = new ArrayList<>();
	public static List<Evento> listaEventosLimpia = new ArrayList<>();
	public static ObservableList<Evento> eventosImportados = FXCollections.observableArrayList();

	// Listas de autocompletado y otras categorías
	public static List<String> listaID = new ArrayList<>();
	public static List<String> listaNombreEvento = new ArrayList<>();
	public static List<String> listaFechaEvento = new ArrayList<>();
	public static List<String> listaMusicoEvento = new ArrayList<>();
	public static List<String> listaMusicos = new ArrayList<>();
	public static List<String> listaConciertos = new ArrayList<>();
	public static List<List<String>> itemsList = Arrays.asList(listaNombreEvento, listaFechaEvento, listaMusicoEvento,
			listaMusicos, listaConciertos);
	public static List<List<String>> listaOrdenada = Arrays.asList(listaNombreEvento, listaFechaEvento, listaMusicos);

	/**
	 * Realiza llamadas para inicializar listas de autocompletado.
	 *
	 * @throws SQLException si ocurre un error al acceder a la base de datos.
	 */
	public static void listasAutoCompletado() {
		listaID = DBUtilidades.obtenerValoresColumnaEvento("idEvento");
		listaNombreEvento = DBUtilidades.obtenerValoresColumnaEvento("indentificarConcierto");
		listaFechaEvento = DBUtilidades.obtenerValoresColumnaEvento("fechaConcierto");
		listaMusicoEvento = DBUtilidades.obtenerValoresColumnaEvento("codigoMusico");
		listaMusicos = DBUtilidades.obtenerValoresColumnaMusicos("nombreMusico");

		listaID = ordenarLista(listaID);

		itemsList = Arrays.asList(listaID, listaNombreEvento, listaFechaEvento, listaMusicoEvento, listaMusicos);

	}

	/**
	 * Busca un cómic por su ID en una lista de cómics.
	 *
	 * @param comics  La lista de cómics en la que se realizará la búsqueda.
	 * @param idComic La ID del cómic que se está buscando.
	 * @return El cómic encontrado por la ID, o null si no se encuentra ninguno.
	 */
	public static Evento buscarEventoPorID(List<Evento> eventos, String idEvento) {
		for (Evento e : eventos) {
			if (e.getIdEvento().equals(idEvento)) {
				return e; // Devuelve el cómic si encuentra la coincidencia por ID
			}
		}
		return null; // Retorna null si no se encuentra ningún cómic con la ID especificada
	}

	/**
	 * Elimina el último evento importado de la lista de eventos.
	 */
	public static void eliminarUltimaEventoImportada() {
		if (!eventosImportados.isEmpty()) {
			eventosImportados.remove(eventosImportados.size() - 1);
		}
	}

	/**
	 * Verifica si un ID de evento ya existe en la lista de eventos importados.
	 * 
	 * @param id el ID del evento a verificar.
	 * @return true si el ID ya existe, false en caso contrario.
	 */
	public static boolean verificarIDExistente(String id) {
		return id != null && !id.isEmpty()
				&& eventosImportados.stream().anyMatch(evento -> id.equalsIgnoreCase(evento.getIdEvento()));
	}

	/**
	 * Devuelve un evento basado en su ID de la lista de eventos importados.
	 * 
	 * @param id el ID del evento a buscar.
	 * @return el evento correspondiente o null si no se encuentra.
	 */
	public static Evento devolverEventoLista(String id) {
		return eventosImportados.stream().filter(evento -> evento.getIdEvento().equalsIgnoreCase(id)).findFirst()
				.orElse(null);
	}

	/**
	 * Actualiza los datos de autocompletado basados en una sentencia SQL dada.
	 * 
	 * @param sentenciaSQL la sentencia SQL para ejecutar.
	 */
	public static void actualizarDatosAutoCompletado(String sentenciaSQL) {
		try (Connection conn = ConectManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sentenciaSQL, ResultSet.TYPE_FORWARD_ONLY,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery()) {

			List<List<String>> listaOrdenada = new ArrayList<>();
			while (rs.next()) {
				List<String> nombreEventoSet = new ArrayList<>();
				List<String> fechaEventoSet = new ArrayList<>();
				List<String> musicoEventoSet = new ArrayList<>();

				do {
					nombreEventoSet.add(rs.getString("tituloEvento").trim());
					fechaEventoSet.add(rs.getString("fechaEvento").trim());
					musicoEventoSet.add(rs.getString("musicoEvento").trim());
				} while (rs.next());

				// Eliminar duplicados y ordenar
				nombreEventoSet = listaArregladaAutoComplete(nombreEventoSet);
				fechaEventoSet = listaArregladaAutoComplete(fechaEventoSet);
				musicoEventoSet = listaArregladaAutoComplete(musicoEventoSet);

				listaOrdenada.add(nombreEventoSet);
				listaOrdenada.add(fechaEventoSet);
				listaOrdenada.add(musicoEventoSet);

				ListasEventosDAO.itemsList = listaOrdenada;
			}
		} catch (SQLException e) {
			Utilidades.manejarExcepcion(e);
		}
	}

	/**
	 * Ordena una lista de Strings que representan números en orden ascendente.
	 * 
	 * @param listaStrings la lista de Strings a ordenar.
	 * @return la lista ordenada.
	 */
	public static List<String> ordenarLista(List<String> listaStrings) {
		return listaStrings.stream().map(Integer::parseInt).sorted().map(String::valueOf).toList();
	}

	/**
	 * Procesa datos para autocompletado eliminando elementos duplicados y vacíos.
	 * 
	 * @param lista la lista de datos a procesar.
	 */
	public static void procesarDatosAutocompletado(List<String> lista) {
		List<String> nombresProcesados = new ArrayList<>();
		for (String cadena : lista) {
			String[] nombres = cadena.split("-");
			for (String nombre : nombres) {
				nombre = nombre.trim();
				if (!nombre.isEmpty()) {
					nombresProcesados.add(nombre);
				}
			}
		}
		lista.clear();
		lista.addAll(nombresProcesados);
	}

	/**
	 * Limpia todas las listas utilizadas para autocompletado.
	 */
	public static void limpiarListasAutocompletado() {
		listaID.clear();
		listaNombreEvento.clear();
		listaFechaEvento.clear();
		listaMusicoEvento.clear();
	}

	/**
	 * Reinicia la lista de eventos.
	 */
	public static void reiniciarListaEventos() {
		listaEventos.clear();
		listaEventosCheck.clear();
		listaEventosLimpia.clear();
		eventosImportados.clear();
	}

	/**
	 * Verifica si una lista de eventos está vacía.
	 * 
	 * @param listaEvento la lista de eventos a verificar.
	 * @return true si la lista está vacía, false si contiene elementos.
	 */
	public boolean checkList(List<Evento> listaEvento) {
		return listaEvento.isEmpty();
	}

	/**
	 * Ordena un HashMap en orden ascendente por valor.
	 * 
	 * @param map el HashMap a ordenar.
	 * @return una lista de entradas ordenadas por valor ascendente.
	 */
	public static List<Map.Entry<Integer, Integer>> sortByValueInt(Map<Integer, Integer> map) {
		return map.entrySet().stream().sorted(Map.Entry.comparingByValue()).toList();
	}

	/**
	 * Guarda datos para autocompletado en una lista desde la base de datos.
	 * 
	 * @param sentenciaSQL la sentencia SQL para obtener los datos.
	 * @param columna      el nombre de la columna que contiene los datos para
	 *                     autocompletado.
	 * @return una lista de cadenas con los datos para autocompletado.
	 */
	public static List<String> guardarDatosAutoCompletado(String sentenciaSQL, String columna) {
		List<String> listaAutoCompletado = new ArrayList<>();
		try (Connection conn = ConectManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sentenciaSQL);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				String datosAutocompletado = rs.getString(columna);
				if (datosAutocompletado != null) {
					listaAutoCompletado.addAll(Arrays.asList(datosAutocompletado.split("-")));
				}
			}
			return listaArregladaAutoComplete(listaAutoCompletado);

		} catch (SQLException e) {
			Utilidades.manejarExcepcion(e);
			return listaAutoCompletado;
		}
	}

	/**
	 * Devuelve una lista única con elementos ordenados y sin duplicados.
	 * 
	 * @param lista la lista de elementos a procesar.
	 * @return una lista de elementos únicos y ordenados.
	 */
	public static <T extends Comparable<? super T>> List<T> listaArregladaAutoComplete(List<T> lista) {
		return lista.stream().distinct().sorted().toList();
	}
}
