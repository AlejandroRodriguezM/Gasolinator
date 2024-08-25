package funcionesManagment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import EventoManagement.Evento;
import alarmas.AlarmaList;
import dbmanager.DBUtilidades;
import dbmanager.DBUtilidades.TipoBusqueda;
import dbmanager.ListasEventosDAO;
import dbmanager.SelectManager;
import funcionesAuxiliares.Utilidades;
import funcionesInterfaz.AccionControlUI;
import funcionesInterfaz.FuncionesTableView;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class AccionSeleccionar {

	private static AccionReferencias referenciaVentana = getReferenciaVentana();

	private static AccionControlUI accionRellenoDatos = new AccionControlUI();

	/**
	 * Método para seleccionar y mostrar detalles de un cómic en la interfaz
	 * gráfica. Si la lista de cómics importados no está vacía, utiliza la
	 * información de la lista; de lo contrario, consulta la base de datos para
	 * obtener la información del cómic.
	 * 
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	public static void seleccionarEventos(boolean esPrincipal) {

		FuncionesTableView.nombreColumnas();
		Utilidades.comprobacionListaEventos();
		Evento newSelection = getReferenciaVentana().getTablaBBDD().getSelectionModel().getSelectedItem();

		Scene scene = getReferenciaVentana().getTablaBBDD().getScene();
		@SuppressWarnings("unchecked")
		final List<Node>[] elementos = new ArrayList[1];
		elementos[0] = new ArrayList<>();

		if (!esPrincipal) {
			elementos[0] = AccionControlUI.modificarInterfazAccion(AccionFuncionesComunes.getTipoAccion());
		}

		if (scene != null) {
			scene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
				if (!getReferenciaVentana().getTablaBBDD().isHover()) {
					if (esPrincipal) {
						getReferenciaVentana().getTablaBBDD().getSelectionModel().clearSelection();
					} else {

						if (!"aniadir".equals(AccionFuncionesComunes.TIPO_ACCION)) {
							getReferenciaVentana().getTablaBBDD().getSelectionModel().clearSelection();
						}

						if ("modificar".equals(AccionFuncionesComunes.TIPO_ACCION)) {
							AccionControlUI.mostrarOpcion(AccionFuncionesComunes.TIPO_ACCION);

							Utilidades.cambiarVisibilidad(elementos[0], true);

						}

						if (!getReferenciaVentana().getIdEventoTratarTextField().getText().isEmpty()) {
							Utilidades.cambiarVisibilidad(elementos[0], false);
						}
						
						// Borrar cualquier mensaje de error presente
						AccionFuncionesComunes.borrarErrores();
						AccionControlUI.validarCamposClave(true);
					}
				}
			});
		}
		

		// Verificar si idRow es nulo antes de intentar acceder a sus métodos
		if (newSelection != null) {
			String idEvento = newSelection.getIdEvento();
			mostrarEvento(idEvento, esPrincipal);
			Utilidades.cambiarVisibilidad(elementos[0], false);
		}

	}

	public static void actualizarRefrenciaClick(AccionReferencias referenciaFXML) {
		Scene scene = getReferenciaVentana().getTablaBBDD().getScene();

		if (scene != null) {
			scene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
				setReferenciaVentana(referenciaFXML);
			});
		}
	}

	public static void mostrarEvento(String idEvento, boolean esPrincipal) {

		Evento eventoTemp = null;
		AlarmaList.detenerAnimacion();
		String mensaje = "";

		if (!ListasEventosDAO.eventosImportados.isEmpty() && !esPrincipal) {
			eventoTemp = ListasEventosDAO.buscarEventoPorID(ListasEventosDAO.eventosImportados, idEvento);

		} else if (EventoManagerDAO.comprobarIdentificadorEvento(idEvento)) {
			eventoTemp = EventoManagerDAO.eventoDatos(idEvento);
		}

		if (idEvento == null || idEvento.isEmpty() || eventoTemp == null) {
			AccionControlUI.limpiarAutorellenos(esPrincipal);
			return;
		}
		if (!esPrincipal) {
			accionRellenoDatos.setAtributosDesdeTabla(eventoTemp);
			AccionControlUI.validarCamposClave(false);

			Utilidades.setDatePickerValue(getReferenciaVentana().getDataPickFechaP(), eventoTemp.getFechaGradeo());
			
			if (AccionFuncionesComunes.TIPO_ACCION.equals("modificar")) {
				AccionControlUI.mostrarOpcion(AccionFuncionesComunes.TIPO_ACCION);
			}
		}

		getReferenciaVentana().getProntInfoTextArea().setOpacity(1);

		if (!ListasEventosDAO.eventosImportados.isEmpty() && EventoManagerDAO.comprobarIdentificadorEvento(idEvento)) {
			mensaje = EventoManagerDAO.eventoDatos(idEvento).toString().replace("[", "").replace("]", "");
		} else {
			mensaje = eventoTemp.toString().replace("[", "").replace("]", "");
		}
		getReferenciaVentana().getProntInfoTextArea().clear();
		getReferenciaVentana().getProntInfoTextArea().setText(mensaje);

	}

	public static void verBasedeDatos(boolean completo, boolean esAccion, Evento evento) {

		ListasEventosDAO.reiniciarListaEventos();
		getReferenciaVentana().getTablaBBDD().refresh();
		getReferenciaVentana().getProntInfoTextArea().setOpacity(0);
		getReferenciaVentana().getProntInfoTextArea().setText(null);
		getReferenciaVentana().getProntInfoTextArea().clear();

		FuncionesTableView.nombreColumnas();
		FuncionesTableView.actualizarBusquedaRaw();

		if (EventoManagerDAO.countRows() > 0) {
			if (completo) {

				String sentenciaSQL = DBUtilidades.construirSentenciaSQL(TipoBusqueda.COMPLETA);

				List<Evento> listaEventos = EventoManagerDAO.verLibreria(sentenciaSQL);

				FuncionesTableView.tablaBBDD(listaEventos);

			} else {

				List<Evento> listaParametro = listaPorParametro(evento, esAccion);

				FuncionesTableView.tablaBBDD(listaParametro);

				if (!esAccion) {
					getReferenciaVentana().getBusquedaGeneralTextField().setText("");
				}

			}
		} else {
			String mensaje = "ERROR. No hay datos en la base de datos";

			AlarmaList.mostrarMensajePront(mensaje, false, getReferenciaVentana().getProntInfoTextArea());
		}
	}

	/**
	 * Funcion que comprueba segun los datos escritos en los textArea, que evento
	 * estas buscando.
	 * 
	 * @throws SQLException
	 */
	public static List<Evento> listaPorParametro(Evento datos, boolean esAccion) {
		String busquedaGeneralTextField = "";

		if (!esAccion) {
			busquedaGeneralTextField = getReferenciaVentana().getBusquedaGeneralTextField().getText();
		}

		List<Evento> listEvento = FXCollections
				.observableArrayList(SelectManager.busquedaParametro(datos, busquedaGeneralTextField));

		if (!listEvento.isEmpty()) {
			getReferenciaVentana().getProntInfoTextArea().setOpacity(1);
			getReferenciaVentana().getProntInfoTextArea().setStyle("-fx-text-fill: black;"); // Reset the text color to
																								// black
			getReferenciaVentana().getProntInfoTextArea()
					.setText("El número de cómics donde aparece la búsqueda es: " + listEvento.size() + "\n \n \n");
		} else if (listEvento.isEmpty() && esAccion) {
			getReferenciaVentana().getProntInfoTextArea().setOpacity(1);
			// Show error message in red when no search fields are specified
			getReferenciaVentana().getProntInfoTextArea().setStyle("-fx-text-fill: red;");
			getReferenciaVentana().getProntInfoTextArea().setText("Error. No existen con dichos parametros.");
		}

		return listEvento;
	}

	public static AccionReferencias getReferenciaVentana() {
		return referenciaVentana;
	}

	public static void setReferenciaVentana(AccionReferencias referenciaVentana) {
		AccionSeleccionar.referenciaVentana = referenciaVentana;
	}

}
