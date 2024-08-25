package funcionesManagment;

import java.util.List;

import EventoManagement.Evento;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AccionReferencias {

	private TableColumn<Evento, String> iDColumna;
	private TableColumn<Evento, String> tituloColumna;
	private TableColumn<Evento, String> fechaColumna;
	private TableColumn<Evento, String> resultadoColumna;
	private TableColumn<Evento, String> musicosColumna;
	private TableColumn<Evento, String> artistaColumna;
	private TableColumn<Evento, String> guionistaColumna;
	private TableColumn<Evento, String> varianteColumna;
	private TableColumn<Evento, String> precioColumna;
	private TableColumn<Evento, String> referenciaColumna;

	public TableView<Evento> tablaBBDD;

	private VBox rootVBox;
	private VBox vboxContenido;
	private VBox vboxImage;

	private AnchorPane rootAnchorPane;
	private AnchorPane anchoPaneInfo;

	private ImageView backgroundImage;
	private ImageView cargaImagen;

	private Button botonClonarEvento;
	private Button botonModificar;
	private Button botonIntroducir;
	private Button botonEliminar;
	private Button botonComprimirPortadas;
	private Button botonReCopiarPortadas;
	private Button botonCancelarSubida;
	private Button botonBusquedaCodigo;
	private Button botonBusquedaAvanzada;
	private Button botonLimpiar;
	private Button botonModificarEvento;
	private Button botonIntroducirEvento;
	private Button botonParametroEvento;
	private Button botonbbdd;
	private Button botonGuardarEvento;
	private Button botonGuardarCambioEvento;
	private Button botonEliminarImportadoEvento;
	private Button botonSubidaPortada;
	private Button botonMostrarParametro;
	private Button botonActualizarDatos;
	private Button botonActualizarPortadas;
	private Button botonActualizarSoftware;
	private Button botonActualizarTodo;

	private Button botonDescargarPdf;
	private Button botonDescargarSQL;
	private Button botonNormalizarDB;
	private Button botonGuardarListaEventos;
	private Button botonEliminarImportadoListaEvento;

	private Rectangle barraCambioAltura;

	private Label alarmaConexionInternet;
	private Label labelEditor;
	private Label labelKeyEvento;
	private Label labelNombre;
	private Label labelIdMod;
	private Label labelPortada;
	private Label labelGradeo;
	private Label labelReferencia;
	private Label prontInfoLabel;
	private Label alarmaConexionSql;
	private Label labelComprobar;
	private Label labelVersion;
	private Label labelArtista;
	private Label labelGuionista;
	private Label labelVariante;
	private Label labelfechaG;
	private Label prontInfoEspecial;
	private Label prontInfoPreviews;
	private Label prontInfoPortadas;
	private Label labelAnio;
	private Label labelCodigo;

	private TextField busquedaCodigoTextField;
	private TextField tituloEventoTextField;
	private TextField codigoEventoTextField;
	private TextField nombreEditorTextField;
	private TextField artistaEventoTextField;
	private TextField guionistaEventoTextField;
	private TextField varianteTextField;
	private TextField idEventoTratarTextField;
	private TextField direccionImagenTextField;
	private TextField urlReferenciaTextField;
	private TextField numeroEventoTextField;
	private TextField precioEventoTextField;
	private TextField firmaEventoTextField;

	private TextField codigoEventoTratarTextField;
	private TextField busquedaGeneralTextField;

	private ComboBox<String> tituloEventoCombobox;
	private ComboBox<String> fechaEventoCombobox;
	private ComboBox<String> numeroEventoCombobox;
	private ComboBox<String> nombreTiendaCombobox;
	private ComboBox<String> nombreEditorCombobox;
	private ComboBox<String> nombreFirmaCombobox;
	private ComboBox<String> nombreArtistaCombobox;
	private ComboBox<String> nombreGuionistaCombobox;
	private ComboBox<String> nombreVarianteCombobox;
	private ComboBox<String> comboPreviewsCombobox;
	private ComboBox<String> comboPreviews;
	
	private DatePicker dataPickFechaP;

	private TextArea prontInfoTextArea;
	private TextArea keyEventoData;

	private MenuItem menuImportarFicheroCodigoBarras;
	private MenuItem menuEventoAniadir;
	private MenuItem menuEventoModificar;
	private MenuItem menuEstadisticaEstadistica;
	private MenuItem menuArchivoCerrar;
	private MenuItem menuArchivoDelete;
	private MenuItem menuArchivoDesconectar;
	private MenuItem menuArchivoExcel;
	private MenuItem menuArchivoImportar;
	private MenuItem menuArchivoSobreMi;
	private MenuItem menuEstadisticaSumaTotal;
	private MenuItem menuArchivoAvanzado;

	private Menu navegacionCerrar;
	private Menu navegacionEvento;
	private Menu navegacionEstadistica;

	private MenuBar menuNavegacion;

	private ProgressIndicator progresoCarga;

	private CheckBox checkFirmas;

	private List<Control> controlAccion;

	private static List<ComboBox<String>> listaComboboxes;
	private static List<TableColumn<Evento, String>> listaColumnasTabla;
	private static ObservableList<Control> listaTextFields;
	private static ObservableList<Button> listaBotones;
	private static ObservableList<Node> listaElementosFondo;

	private Stage stageVentana;

	/**
	 * @return the iDColumna
	 */
	public TableColumn<Evento, String> getiDColumna() {
		return iDColumna;
	}

	/**
	 * @return the tituloColumna
	 */
	public TableColumn<Evento, String> getTituloColumna() {
		return tituloColumna;
	}

	/**
	 * @return the firmaColumna
	 */
	public TableColumn<Evento, String> getMusicosColumna() {
		return musicosColumna;
	}

	/**
	 * @return the artistaColumna
	 */
	public TableColumn<Evento, String> getArtistaColumna() {
		return artistaColumna;
	}

	/**
	 * @return the guionistaColumna
	 */
	public TableColumn<Evento, String> getGuionistaColumna() {
		return guionistaColumna;
	}

	/**
	 * @return the varianteColumna
	 */
	public TableColumn<Evento, String> getVarianteColumna() {
		return varianteColumna;
	}

	/**
	 * @return the precioColumna
	 */
	public TableColumn<Evento, String> getPrecioColumna() {
		return precioColumna;
	}

	/**
	 * @return the referenciaColumna
	 */
	public TableColumn<Evento, String> getReferenciaColumna() {
		return referenciaColumna;
	}

	/**
	 * @return the tablaBBDD
	 */
	public TableView<Evento> getTablaBBDD() {
		return tablaBBDD;
	}

	/**
	 * @return the rootVBox
	 */
	public VBox getRootVBox() {
		return rootVBox;
	}

	/**
	 * @return the vboxContenido
	 */
	public VBox getVboxContenido() {
		return vboxContenido;
	}

	/**
	 * @return the vboxImage
	 */
	public VBox getVboxImage() {
		return vboxImage;
	}

	/**
	 * @return the rootAnchorPane
	 */
	public AnchorPane getRootAnchorPane() {
		return rootAnchorPane;
	}

	/**
	 * @return the anchoPaneInfo
	 */
	public AnchorPane getAnchoPaneInfo() {
		return anchoPaneInfo;
	}

	/**
	 * @return the backgroundImage
	 */
	public ImageView getBackgroundImage() {
		return backgroundImage;
	}

	/**
	 * @return the cargaImagen
	 */
	public ImageView getCargaImagen() {
		return cargaImagen;
	}

	/**
	 * @return the botonClonarEvento
	 */
	public Button getBotonClonarEvento() {
		return botonClonarEvento;
	}

	/**
	 * @return the botonModificar
	 */
	public Button getBotonModificar() {
		return botonModificar;
	}

	/**
	 * @return the botonIntroducir
	 */
	public Button getBotonIntroducir() {
		return botonIntroducir;
	}

	/**
	 * @return the botonEliminar
	 */
	public Button getBotonEliminar() {
		return botonEliminar;
	}

	/**
	 * @return the botonComprimirPortadas
	 */
	public Button getBotonComprimirPortadas() {
		return botonComprimirPortadas;
	}

	/**
	 * @return the botonReCopiarPortadas
	 */
	public Button getBotonReCopiarPortadas() {
		return botonReCopiarPortadas;
	}

	/**
	 * @return the botonCancelarSubida
	 */
	public Button getBotonCancelarSubida() {
		return botonCancelarSubida;
	}

	/**
	 * @return the botonBusquedaCodigo
	 */
	public Button getBotonBusquedaCodigo() {
		return botonBusquedaCodigo;
	}

	/**
	 * @return the botonBusquedaAvanzada
	 */
	public Button getBotonBusquedaAvanzada() {
		return botonBusquedaAvanzada;
	}

	/**
	 * @return the botonLimpiar
	 */
	public Button getBotonLimpiar() {
		return botonLimpiar;
	}

	/**
	 * @return the botonModificarEvento
	 */
	public Button getBotonModificarEvento() {
		return botonModificarEvento;
	}

	/**
	 * @return the botonIntroducirEvento
	 */
	public Button getBotonIntroducirEvento() {
		return botonIntroducirEvento;
	}

	/**
	 * @return the botonParametroEvento
	 */
	public Button getBotonParametroEvento() {
		return botonParametroEvento;
	}

	/**
	 * @return the botonbbdd
	 */
	public Button getBotonbbdd() {
		return botonbbdd;
	}

	/**
	 * @return the botonGuardarEvento
	 */
	public Button getBotonGuardarEvento() {
		return botonGuardarEvento;
	}

	/**
	 * @return the botonGuardarCambioEvento
	 */
	public Button getBotonGuardarCambioEvento() {
		return botonGuardarCambioEvento;
	}

	/**
	 * @return the botonEliminarImportadoEvento
	 */
	public Button getBotonEliminarImportadoEvento() {
		return botonEliminarImportadoEvento;
	}

	/**
	 * @return the botonSubidaPortada
	 */
	public Button getBotonSubidaPortada() {
		return botonSubidaPortada;
	}

	/**
	 * @return the botonMostrarParametro
	 */
	public Button getBotonMostrarParametro() {
		return botonMostrarParametro;
	}

	/**
	 * @return the botonActualizarDatos
	 */
	public Button getBotonActualizarDatos() {
		return botonActualizarDatos;
	}

	/**
	 * @return the botonActualizarPortadas
	 */
	public Button getBotonActualizarPortadas() {
		return botonActualizarPortadas;
	}

	/**
	 * @return the botonActualizarSoftware
	 */
	public Button getBotonActualizarSoftware() {
		return botonActualizarSoftware;
	}

	/**
	 * @return the botonActualizarTodo
	 */
	public Button getBotonActualizarTodo() {
		return botonActualizarTodo;
	}

	/**
	 * @return the botonDescargarPdf
	 */
	public Button getBotonDescargarPdf() {
		return botonDescargarPdf;
	}

	/**
	 * @return the botonDescargarSQL
	 */
	public Button getBotonDescargarSQL() {
		return botonDescargarSQL;
	}

	/**
	 * @return the botonNormalizarDB
	 */
	public Button getBotonNormalizarDB() {
		return botonNormalizarDB;
	}

	/**
	 * @return the botonGuardarListaEventos
	 */
	public Button getBotonGuardarListaEventos() {
		return botonGuardarListaEventos;
	}

	/**
	 * @return the botonEliminarImportadoListaEvento
	 */
	public Button getBotonEliminarImportadoListaEvento() {
		return botonEliminarImportadoListaEvento;
	}

	/**
	 * @return the barraCambioAltura
	 */
	public Rectangle getBarraCambioAltura() {
		return barraCambioAltura;
	}

	/**
	 * @return the alarmaConexionInternet
	 */
	public Label getAlarmaConexionInternet() {
		return alarmaConexionInternet;
	}

	/**
	 * @return the labelEditor
	 */
	public Label getLabelEditor() {
		return labelEditor;
	}

	/**
	 * @return the labelKeyEvento
	 */
	public Label getLabelKeyEvento() {
		return labelKeyEvento;
	}

	/**
	 * @return the labelNombre
	 */
	public Label getLabelNombre() {
		return labelNombre;
	}

	/**
	 * @return the labelIdMod
	 */
	public Label getLabelIdMod() {
		return labelIdMod;
	}

	/**
	 * @return the labelPortada
	 */
	public Label getLabelPortada() {
		return labelPortada;
	}

	/**
	 * @return the labelGradeo
	 */
	public Label getLabelGradeo() {
		return labelGradeo;
	}

	/**
	 * @return the labelReferencia
	 */
	public Label getLabelReferencia() {
		return labelReferencia;
	}

	/**
	 * @return the prontInfoLabel
	 */
	public Label getProntInfoLabel() {
		return prontInfoLabel;
	}

	/**
	 * @return the alarmaConexionSql
	 */
	public Label getAlarmaConexionSql() {
		return alarmaConexionSql;
	}

	/**
	 * @return the labelComprobar
	 */
	public Label getLabelComprobar() {
		return labelComprobar;
	}

	/**
	 * @return the labelVersion
	 */
	public Label getLabelVersion() {
		return labelVersion;
	}

	/**
	 * @return the labelArtista
	 */
	public Label getLabelArtista() {
		return labelArtista;
	}

	/**
	 * @return the labelGuionista
	 */
	public Label getLabelGuionista() {
		return labelGuionista;
	}

	/**
	 * @return the labelVariante
	 */
	public Label getLabelVariante() {
		return labelVariante;
	}

	/**
	 * @return the labelfechaG
	 */
	public Label getLabelfechaG() {
		return labelfechaG;
	}

	/**
	 * @return the prontInfoEspecial
	 */
	public Label getProntInfoEspecial() {
		return prontInfoEspecial;
	}

	/**
	 * @return the prontInfoPreviews
	 */
	public Label getProntInfoPreviews() {
		return prontInfoPreviews;
	}

	/**
	 * @return the prontInfoPortadas
	 */
	public Label getProntInfoPortadas() {
		return prontInfoPortadas;
	}

	/**
	 * @return the labelAnio
	 */
	public Label getLabelAnio() {
		return labelAnio;
	}

	/**
	 * @return the labelCodigo
	 */
	public Label getLabelCodigo() {
		return labelCodigo;
	}

	/**
	 * @return the busquedaCodigoTextField
	 */
	public TextField getBusquedaCodigoTextField() {
		return busquedaCodigoTextField;
	}

	/**
	 * @return the tituloEventoTextField
	 */
	public TextField getTituloEventoTextField() {
		return tituloEventoTextField;
	}

	/**
	 * @return the codigoEventoTextField
	 */
	public TextField getCodigoEventoTextField() {
		return codigoEventoTextField;
	}

	/**
	 * @return the nombreEditorTextField
	 */
	public TextField getNombreEditorTextField() {
		return nombreEditorTextField;
	}

	/**
	 * @return the artistaEventoTextField
	 */
	public TextField getArtistaEventoTextField() {
		return artistaEventoTextField;
	}

	/**
	 * @return the guionistaEventoTextField
	 */
	public TextField getGuionistaEventoTextField() {
		return guionistaEventoTextField;
	}

	/**
	 * @return the varianteTextField
	 */
	public TextField getVarianteTextField() {
		return varianteTextField;
	}

	/**
	 * @return the idEventoTratarTextField
	 */
	public TextField getIdEventoTratarTextField() {
		return idEventoTratarTextField;
	}

	/**
	 * @return the direccionImagenTextField
	 */
	public TextField getDireccionImagenTextField() {
		return direccionImagenTextField;
	}

	/**
	 * @return the urlReferenciaTextField
	 */
	public TextField getUrlReferenciaTextField() {
		return urlReferenciaTextField;
	}

	/**
	 * @return the numeroEventoTextField
	 */
	public TextField getNumeroEventoTextField() {
		return numeroEventoTextField;
	}

	/**
	 * @return the precioEventoTextField
	 */
	public TextField getPrecioEventoTextField() {
		return precioEventoTextField;
	}

	/**
	 * @return the firmaEventoTextField
	 */
	public TextField getFirmaEventoTextField() {
		return firmaEventoTextField;
	}

	/**
	 * @return the codigoEventoTratarTextField
	 */
	public TextField getCodigoEventoTratarTextField() {
		return codigoEventoTratarTextField;
	}

	/**
	 * @return the busquedaGeneralTextField
	 */
	public TextField getBusquedaGeneralTextField() {
		return busquedaGeneralTextField;
	}

	/**
	 * @return the tituloEventoCombobox
	 */
	public ComboBox<String> getTituloEventoCombobox() {
		return tituloEventoCombobox;
	}

	/**
	 * @return the numeroEventoCombobox
	 */
	public ComboBox<String> getNumeroEventoCombobox() {
		return numeroEventoCombobox;
	}

	/**
	 * @return the nombreTiendaCombobox
	 */
	public ComboBox<String> getNombreTiendaCombobox() {
		return nombreTiendaCombobox;
	}

	/**
	 * @return the nombreEditorCombobox
	 */
	public ComboBox<String> getNombreEditorCombobox() {
		return nombreEditorCombobox;
	}

	/**
	 * @return the nombreFirmaCombobox
	 */
	public ComboBox<String> getNombreFirmaCombobox() {
		return nombreFirmaCombobox;
	}

	/**
	 * @return the nombreArtistaCombobox
	 */
	public ComboBox<String> getNombreArtistaCombobox() {
		return nombreArtistaCombobox;
	}

	/**
	 * @return the nombreGuionistaCombobox
	 */
	public ComboBox<String> getNombreGuionistaCombobox() {
		return nombreGuionistaCombobox;
	}

	/**
	 * @return the nombreVarianteCombobox
	 */
	public ComboBox<String> getNombreVarianteCombobox() {
		return nombreVarianteCombobox;
	}

	/**
	 * @return the comboPreviewsCombobox
	 */
	public ComboBox<String> getComboPreviewsCombobox() {
		return comboPreviewsCombobox;
	}

	/**
	 * @return the comboPreviews
	 */
	public ComboBox<String> getComboPreviews() {
		return comboPreviews;
	}

	/**
	 * @return the dataPickFechaP
	 */
	public DatePicker getDataPickFechaP() {
		return dataPickFechaP;
	}

	/**
	 * @return the prontInfoTextArea
	 */
	public TextArea getProntInfoTextArea() {
		return prontInfoTextArea;
	}

	/**
	 * @return the keyEventoData
	 */
	public TextArea getKeyEventoData() {
		return keyEventoData;
	}

	/**
	 * @return the menuImportarFicheroCodigoBarras
	 */
	public MenuItem getMenuImportarFicheroCodigoBarras() {
		return menuImportarFicheroCodigoBarras;
	}

	/**
	 * @return the menuEventoAniadir
	 */
	public MenuItem getMenuEventoAniadir() {
		return menuEventoAniadir;
	}

	/**
	 * @return the menuEventoModificar
	 */
	public MenuItem getMenuEventoModificar() {
		return menuEventoModificar;
	}

	/**
	 * @return the menuEstadisticaEstadistica
	 */
	public MenuItem getMenuEstadisticaEstadistica() {
		return menuEstadisticaEstadistica;
	}

	/**
	 * @return the menuArchivoCerrar
	 */
	public MenuItem getMenuArchivoCerrar() {
		return menuArchivoCerrar;
	}

	/**
	 * @return the menuArchivoDelete
	 */
	public MenuItem getMenuArchivoDelete() {
		return menuArchivoDelete;
	}

	/**
	 * @return the menuArchivoDesconectar
	 */
	public MenuItem getMenuArchivoDesconectar() {
		return menuArchivoDesconectar;
	}

	/**
	 * @return the menuArchivoExcel
	 */
	public MenuItem getMenuArchivoExcel() {
		return menuArchivoExcel;
	}

	/**
	 * @return the menuArchivoImportar
	 */
	public MenuItem getMenuArchivoImportar() {
		return menuArchivoImportar;
	}

	/**
	 * @return the menuArchivoSobreMi
	 */
	public MenuItem getMenuArchivoSobreMi() {
		return menuArchivoSobreMi;
	}

	/**
	 * @return the menuEstadisticaSumaTotal
	 */
	public MenuItem getMenuEstadisticaSumaTotal() {
		return menuEstadisticaSumaTotal;
	}

	/**
	 * @return the menuArchivoAvanzado
	 */
	public MenuItem getMenuArchivoAvanzado() {
		return menuArchivoAvanzado;
	}

	/**
	 * @return the navegacionCerrar
	 */
	public Menu getNavegacionCerrar() {
		return navegacionCerrar;
	}

	/**
	 * @return the navegacionEvento
	 */
	public Menu getNavegacionEvento() {
		return navegacionEvento;
	}

	/**
	 * @return the navegacionEstadistica
	 */
	public Menu getNavegacionEstadistica() {
		return navegacionEstadistica;
	}

	/**
	 * @return the menuNavegacion
	 */
	public MenuBar getMenuNavegacion() {
		return menuNavegacion;
	}

	/**
	 * @return the progresoCarga
	 */
	public ProgressIndicator getProgresoCarga() {
		return progresoCarga;
	}

	/**
	 * @return the checkFirmas
	 */
	public CheckBox getCheckFirmas() {
		return checkFirmas;
	}

	/**
	 * @return the controlAccion
	 */
	public List<Control> getControlAccion() {
		return controlAccion;
	}

	/**
	 * @return the listaComboboxes
	 */
	public static List<ComboBox<String>> getListaComboboxes() {
		return listaComboboxes;
	}

	/**
	 * @return the listaColumnasTabla
	 */
	public static List<TableColumn<Evento, String>> getListaColumnasTabla() {
		return listaColumnasTabla;
	}

	/**
	 * @return the listaTextFields
	 */
	public static ObservableList<Control> getListaTextFields() {
		return listaTextFields;
	}

	/**
	 * @return the listaBotones
	 */
	public static ObservableList<Button> getListaBotones() {
		return listaBotones;
	}

	/**
	 * @return the listaElementosFondo
	 */
	public static ObservableList<Node> getListaElementosFondo() {
		return listaElementosFondo;
	}

	/**
	 * @return the stageVentana
	 */
	public Stage getStageVentana() {
		return stageVentana;
	}

	/**
	 * @return the fechaColumna
	 */
	public TableColumn<Evento, String> getFechaColumna() {
		return fechaColumna;
	}

	/**
	 * @return the resultadoColumna
	 */
	public TableColumn<Evento, String> getResultadoColumna() {
		return resultadoColumna;
	}

	/**
	 * @return the fechaEventoCombobox
	 */
	public ComboBox<String> getFechaEventoCombobox() {
		return fechaEventoCombobox;
	}

	/**
	 * @param fechaEventoCombobox the fechaEventoCombobox to set
	 */
	public void setFechaEventoCombobox(ComboBox<String> fechaEventoCombobox) {
		this.fechaEventoCombobox = fechaEventoCombobox;
	}

	/**
	 * @param fechaColumna the fechaColumna to set
	 */
	public void setFechaColumna(TableColumn<Evento, String> fechaColumna) {
		this.fechaColumna = fechaColumna;
	}

	/**
	 * @param resultadoColumna the resultadoColumna to set
	 */
	public void setResultadoColumna(TableColumn<Evento, String> resultadoColumna) {
		this.resultadoColumna = resultadoColumna;
	}

	/**
	 * @param iDColumna the iDColumna to set
	 */
	public void setiDColumna(TableColumn<Evento, String> iDColumna) {
		this.iDColumna = iDColumna;
	}

	/**
	 * @param tituloColumna the tituloColumna to set
	 */
	public void setTituloColumna(TableColumn<Evento, String> tituloColumna) {
		this.tituloColumna = tituloColumna;
	}

	/**
	 * @param firmaColumna the firmaColumna to set
	 */
	public void setMusicosColumna(TableColumn<Evento, String> musicosColumna) {
		this.musicosColumna = musicosColumna;
	}

	/**
	 * @param artistaColumna the artistaColumna to set
	 */
	public void setArtistaColumna(TableColumn<Evento, String> artistaColumna) {
		this.artistaColumna = artistaColumna;
	}

	/**
	 * @param guionistaColumna the guionistaColumna to set
	 */
	public void setGuionistaColumna(TableColumn<Evento, String> guionistaColumna) {
		this.guionistaColumna = guionistaColumna;
	}

	/**
	 * @param varianteColumna the varianteColumna to set
	 */
	public void setVarianteColumna(TableColumn<Evento, String> varianteColumna) {
		this.varianteColumna = varianteColumna;
	}

	/**
	 * @param precioColumna the precioColumna to set
	 */
	public void setPrecioColumna(TableColumn<Evento, String> precioColumna) {
		this.precioColumna = precioColumna;
	}

	/**
	 * @param referenciaColumna the referenciaColumna to set
	 */
	public void setReferenciaColumna(TableColumn<Evento, String> referenciaColumna) {
		this.referenciaColumna = referenciaColumna;
	}

	/**
	 * @param tablaBBDD the tablaBBDD to set
	 */
	public void setTablaBBDD(TableView<Evento> tablaBBDD) {
		this.tablaBBDD = tablaBBDD;
	}

	/**
	 * @param rootVBox the rootVBox to set
	 */
	public void setRootVBox(VBox rootVBox) {
		this.rootVBox = rootVBox;
	}

	/**
	 * @param vboxContenido the vboxContenido to set
	 */
	public void setVboxContenido(VBox vboxContenido) {
		this.vboxContenido = vboxContenido;
	}

	/**
	 * @param vboxImage the vboxImage to set
	 */
	public void setVboxImage(VBox vboxImage) {
		this.vboxImage = vboxImage;
	}

	/**
	 * @param rootAnchorPane the rootAnchorPane to set
	 */
	public void setRootAnchorPane(AnchorPane rootAnchorPane) {
		this.rootAnchorPane = rootAnchorPane;
	}

	/**
	 * @param anchoPaneInfo the anchoPaneInfo to set
	 */
	public void setAnchoPaneInfo(AnchorPane anchoPaneInfo) {
		this.anchoPaneInfo = anchoPaneInfo;
	}

	/**
	 * @param backgroundImage the backgroundImage to set
	 */
	public void setBackgroundImage(ImageView backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	/**
	 * @param cargaImagen the cargaImagen to set
	 */
	public void setCargaImagen(ImageView cargaImagen) {
		this.cargaImagen = cargaImagen;
	}

	/**
	 * @param botonClonarEvento the botonClonarEvento to set
	 */
	public void setBotonClonarEvento(Button botonClonarEvento) {
		this.botonClonarEvento = botonClonarEvento;
	}

	/**
	 * @param botonModificar the botonModificar to set
	 */
	public void setBotonModificar(Button botonModificar) {
		this.botonModificar = botonModificar;
	}

	/**
	 * @param botonIntroducir the botonIntroducir to set
	 */
	public void setBotonIntroducir(Button botonIntroducir) {
		this.botonIntroducir = botonIntroducir;
	}

	/**
	 * @param botonEliminar the botonEliminar to set
	 */
	public void setBotonEliminar(Button botonEliminar) {
		this.botonEliminar = botonEliminar;
	}

	/**
	 * @param botonComprimirPortadas the botonComprimirPortadas to set
	 */
	public void setBotonComprimirPortadas(Button botonComprimirPortadas) {
		this.botonComprimirPortadas = botonComprimirPortadas;
	}

	/**
	 * @param botonReCopiarPortadas the botonReCopiarPortadas to set
	 */
	public void setBotonReCopiarPortadas(Button botonReCopiarPortadas) {
		this.botonReCopiarPortadas = botonReCopiarPortadas;
	}

	/**
	 * @param botonCancelarSubida the botonCancelarSubida to set
	 */
	public void setBotonCancelarSubida(Button botonCancelarSubida) {
		this.botonCancelarSubida = botonCancelarSubida;
	}

	/**
	 * @param botonBusquedaCodigo the botonBusquedaCodigo to set
	 */
	public void setBotonBusquedaCodigo(Button botonBusquedaCodigo) {
		this.botonBusquedaCodigo = botonBusquedaCodigo;
	}

	/**
	 * @param botonBusquedaAvanzada the botonBusquedaAvanzada to set
	 */
	public void setBotonBusquedaAvanzada(Button botonBusquedaAvanzada) {
		this.botonBusquedaAvanzada = botonBusquedaAvanzada;
	}

	/**
	 * @param botonLimpiar the botonLimpiar to set
	 */
	public void setBotonLimpiar(Button botonLimpiar) {
		this.botonLimpiar = botonLimpiar;
	}

	/**
	 * @param botonModificarEvento the botonModificarEvento to set
	 */
	public void setBotonModificarEvento(Button botonModificarEvento) {
		this.botonModificarEvento = botonModificarEvento;
	}

	/**
	 * @param botonIntroducirEvento the botonIntroducirEvento to set
	 */
	public void setBotonIntroducirEvento(Button botonIntroducirEvento) {
		this.botonIntroducirEvento = botonIntroducirEvento;
	}

	/**
	 * @param botonParametroEvento the botonParametroEvento to set
	 */
	public void setBotonParametroEvento(Button botonParametroEvento) {
		this.botonParametroEvento = botonParametroEvento;
	}

	/**
	 * @param botonbbdd the botonbbdd to set
	 */
	public void setBotonbbdd(Button botonbbdd) {
		this.botonbbdd = botonbbdd;
	}

	/**
	 * @param botonGuardarEvento the botonGuardarEvento to set
	 */
	public void setBotonGuardarEvento(Button botonGuardarEvento) {
		this.botonGuardarEvento = botonGuardarEvento;
	}

	/**
	 * @param botonGuardarCambioEvento the botonGuardarCambioEvento to set
	 */
	public void setBotonGuardarCambioEvento(Button botonGuardarCambioEvento) {
		this.botonGuardarCambioEvento = botonGuardarCambioEvento;
	}

	/**
	 * @param botonEliminarImportadoEvento the botonEliminarImportadoEvento to set
	 */
	public void setBotonEliminarImportadoEvento(Button botonEliminarImportadoEvento) {
		this.botonEliminarImportadoEvento = botonEliminarImportadoEvento;
	}

	/**
	 * @param botonSubidaPortada the botonSubidaPortada to set
	 */
	public void setBotonSubidaPortada(Button botonSubidaPortada) {
		this.botonSubidaPortada = botonSubidaPortada;
	}

	/**
	 * @param botonMostrarParametro the botonMostrarParametro to set
	 */
	public void setBotonMostrarParametro(Button botonMostrarParametro) {
		this.botonMostrarParametro = botonMostrarParametro;
	}

	/**
	 * @param botonActualizarDatos the botonActualizarDatos to set
	 */
	public void setBotonActualizarDatos(Button botonActualizarDatos) {
		this.botonActualizarDatos = botonActualizarDatos;
	}

	/**
	 * @param botonActualizarPortadas the botonActualizarPortadas to set
	 */
	public void setBotonActualizarPortadas(Button botonActualizarPortadas) {
		this.botonActualizarPortadas = botonActualizarPortadas;
	}

	/**
	 * @param botonActualizarSoftware the botonActualizarSoftware to set
	 */
	public void setBotonActualizarSoftware(Button botonActualizarSoftware) {
		this.botonActualizarSoftware = botonActualizarSoftware;
	}

	/**
	 * @param botonActualizarTodo the botonActualizarTodo to set
	 */
	public void setBotonActualizarTodo(Button botonActualizarTodo) {
		this.botonActualizarTodo = botonActualizarTodo;
	}

	/**
	 * @param botonDescargarPdf the botonDescargarPdf to set
	 */
	public void setBotonDescargarPdf(Button botonDescargarPdf) {
		this.botonDescargarPdf = botonDescargarPdf;
	}

	/**
	 * @param botonDescargarSQL the botonDescargarSQL to set
	 */
	public void setBotonDescargarSQL(Button botonDescargarSQL) {
		this.botonDescargarSQL = botonDescargarSQL;
	}

	/**
	 * @param botonNormalizarDB the botonNormalizarDB to set
	 */
	public void setBotonNormalizarDB(Button botonNormalizarDB) {
		this.botonNormalizarDB = botonNormalizarDB;
	}

	/**
	 * @param botonGuardarListaEventos the botonGuardarListaEventos to set
	 */
	public void setBotonGuardarListaEventos(Button botonGuardarListaEventos) {
		this.botonGuardarListaEventos = botonGuardarListaEventos;
	}

	/**
	 * @param botonEliminarImportadoListaEvento the botonEliminarImportadoListaEvento to set
	 */
	public void setBotonEliminarImportadoListaEvento(Button botonEliminarImportadoListaEvento) {
		this.botonEliminarImportadoListaEvento = botonEliminarImportadoListaEvento;
	}

	/**
	 * @param barraCambioAltura the barraCambioAltura to set
	 */
	public void setBarraCambioAltura(Rectangle barraCambioAltura) {
		this.barraCambioAltura = barraCambioAltura;
	}

	/**
	 * @param alarmaConexionInternet the alarmaConexionInternet to set
	 */
	public void setAlarmaConexionInternet(Label alarmaConexionInternet) {
		this.alarmaConexionInternet = alarmaConexionInternet;
	}

	/**
	 * @param labelEditor the labelEditor to set
	 */
	public void setLabelEditor(Label labelEditor) {
		this.labelEditor = labelEditor;
	}

	/**
	 * @param labelKeyEvento the labelKeyEvento to set
	 */
	public void setLabelKeyEvento(Label labelKeyEvento) {
		this.labelKeyEvento = labelKeyEvento;
	}

	/**
	 * @param labelNombre the labelNombre to set
	 */
	public void setLabelNombre(Label labelNombre) {
		this.labelNombre = labelNombre;
	}

	/**
	 * @param labelIdMod the labelIdMod to set
	 */
	public void setLabelIdMod(Label labelIdMod) {
		this.labelIdMod = labelIdMod;
	}

	/**
	 * @param labelPortada the labelPortada to set
	 */
	public void setLabelPortada(Label labelPortada) {
		this.labelPortada = labelPortada;
	}

	/**
	 * @param labelGradeo the labelGradeo to set
	 */
	public void setLabelGradeo(Label labelGradeo) {
		this.labelGradeo = labelGradeo;
	}

	/**
	 * @param labelReferencia the labelReferencia to set
	 */
	public void setLabelReferencia(Label labelReferencia) {
		this.labelReferencia = labelReferencia;
	}

	/**
	 * @param prontInfoLabel the prontInfoLabel to set
	 */
	public void setProntInfoLabel(Label prontInfoLabel) {
		this.prontInfoLabel = prontInfoLabel;
	}

	/**
	 * @param alarmaConexionSql the alarmaConexionSql to set
	 */
	public void setAlarmaConexionSql(Label alarmaConexionSql) {
		this.alarmaConexionSql = alarmaConexionSql;
	}

	/**
	 * @param labelComprobar the labelComprobar to set
	 */
	public void setLabelComprobar(Label labelComprobar) {
		this.labelComprobar = labelComprobar;
	}

	/**
	 * @param labelVersion the labelVersion to set
	 */
	public void setLabelVersion(Label labelVersion) {
		this.labelVersion = labelVersion;
	}

	/**
	 * @param labelArtista the labelArtista to set
	 */
	public void setLabelArtista(Label labelArtista) {
		this.labelArtista = labelArtista;
	}

	/**
	 * @param labelGuionista the labelGuionista to set
	 */
	public void setLabelGuionista(Label labelGuionista) {
		this.labelGuionista = labelGuionista;
	}

	/**
	 * @param labelVariante the labelVariante to set
	 */
	public void setLabelVariante(Label labelVariante) {
		this.labelVariante = labelVariante;
	}

	/**
	 * @param labelfechaG the labelfechaG to set
	 */
	public void setLabelfechaG(Label labelfechaG) {
		this.labelfechaG = labelfechaG;
	}

	/**
	 * @param prontInfoEspecial the prontInfoEspecial to set
	 */
	public void setProntInfoEspecial(Label prontInfoEspecial) {
		this.prontInfoEspecial = prontInfoEspecial;
	}

	/**
	 * @param prontInfoPreviews the prontInfoPreviews to set
	 */
	public void setProntInfoPreviews(Label prontInfoPreviews) {
		this.prontInfoPreviews = prontInfoPreviews;
	}

	/**
	 * @param prontInfoPortadas the prontInfoPortadas to set
	 */
	public void setProntInfoPortadas(Label prontInfoPortadas) {
		this.prontInfoPortadas = prontInfoPortadas;
	}

	/**
	 * @param labelAnio the labelAnio to set
	 */
	public void setLabelAnio(Label labelAnio) {
		this.labelAnio = labelAnio;
	}

	/**
	 * @param labelCodigo the labelCodigo to set
	 */
	public void setLabelCodigo(Label labelCodigo) {
		this.labelCodigo = labelCodigo;
	}

	/**
	 * @param busquedaCodigoTextField the busquedaCodigoTextField to set
	 */
	public void setBusquedaCodigoTextField(TextField busquedaCodigoTextField) {
		this.busquedaCodigoTextField = busquedaCodigoTextField;
	}

	/**
	 * @param tituloEventoTextField the tituloEventoTextField to set
	 */
	public void setTituloEventoTextField(TextField tituloEventoTextField) {
		this.tituloEventoTextField = tituloEventoTextField;
	}

	/**
	 * @param codigoEventoTextField the codigoEventoTextField to set
	 */
	public void setCodigoEventoTextField(TextField codigoEventoTextField) {
		this.codigoEventoTextField = codigoEventoTextField;
	}

	/**
	 * @param nombreEditorTextField the nombreEditorTextField to set
	 */
	public void setNombreEditorTextField(TextField nombreEditorTextField) {
		this.nombreEditorTextField = nombreEditorTextField;
	}

	/**
	 * @param artistaEventoTextField the artistaEventoTextField to set
	 */
	public void setArtistaEventoTextField(TextField artistaEventoTextField) {
		this.artistaEventoTextField = artistaEventoTextField;
	}

	/**
	 * @param guionistaEventoTextField the guionistaEventoTextField to set
	 */
	public void setGuionistaEventoTextField(TextField guionistaEventoTextField) {
		this.guionistaEventoTextField = guionistaEventoTextField;
	}

	/**
	 * @param varianteTextField the varianteTextField to set
	 */
	public void setVarianteTextField(TextField varianteTextField) {
		this.varianteTextField = varianteTextField;
	}

	/**
	 * @param idEventoTratarTextField the idEventoTratarTextField to set
	 */
	public void setIdEventoTratarTextField(TextField idEventoTratarTextField) {
		this.idEventoTratarTextField = idEventoTratarTextField;
	}

	/**
	 * @param direccionImagenTextField the direccionImagenTextField to set
	 */
	public void setDireccionImagenTextField(TextField direccionImagenTextField) {
		this.direccionImagenTextField = direccionImagenTextField;
	}

	/**
	 * @param urlReferenciaTextField the urlReferenciaTextField to set
	 */
	public void setUrlReferenciaTextField(TextField urlReferenciaTextField) {
		this.urlReferenciaTextField = urlReferenciaTextField;
	}

	/**
	 * @param numeroEventoTextField the numeroEventoTextField to set
	 */
	public void setNumeroEventoTextField(TextField numeroEventoTextField) {
		this.numeroEventoTextField = numeroEventoTextField;
	}

	/**
	 * @param precioEventoTextField the precioEventoTextField to set
	 */
	public void setPrecioEventoTextField(TextField precioEventoTextField) {
		this.precioEventoTextField = precioEventoTextField;
	}

	/**
	 * @param firmaEventoTextField the firmaEventoTextField to set
	 */
	public void setFirmaEventoTextField(TextField firmaEventoTextField) {
		this.firmaEventoTextField = firmaEventoTextField;
	}

	/**
	 * @param codigoEventoTratarTextField the codigoEventoTratarTextField to set
	 */
	public void setCodigoEventoTratarTextField(TextField codigoEventoTratarTextField) {
		this.codigoEventoTratarTextField = codigoEventoTratarTextField;
	}

	/**
	 * @param busquedaGeneralTextField the busquedaGeneralTextField to set
	 */
	public void setBusquedaGeneralTextField(TextField busquedaGeneralTextField) {
		this.busquedaGeneralTextField = busquedaGeneralTextField;
	}

	/**
	 * @param tituloEventoCombobox the tituloEventoCombobox to set
	 */
	public void setTituloEventoCombobox(ComboBox<String> tituloEventoCombobox) {
		this.tituloEventoCombobox = tituloEventoCombobox;
	}

	/**
	 * @param numeroEventoCombobox the numeroEventoCombobox to set
	 */
	public void setNumeroEventoCombobox(ComboBox<String> numeroEventoCombobox) {
		this.numeroEventoCombobox = numeroEventoCombobox;
	}

	/**
	 * @param nombreTiendaCombobox the nombreTiendaCombobox to set
	 */
	public void setNombreTiendaCombobox(ComboBox<String> nombreTiendaCombobox) {
		this.nombreTiendaCombobox = nombreTiendaCombobox;
	}

	/**
	 * @param nombreEditorCombobox the nombreEditorCombobox to set
	 */
	public void setNombreEditorCombobox(ComboBox<String> nombreEditorCombobox) {
		this.nombreEditorCombobox = nombreEditorCombobox;
	}

	/**
	 * @param nombreFirmaCombobox the nombreFirmaCombobox to set
	 */
	public void setNombreFirmaCombobox(ComboBox<String> nombreFirmaCombobox) {
		this.nombreFirmaCombobox = nombreFirmaCombobox;
	}

	/**
	 * @param nombreArtistaCombobox the nombreArtistaCombobox to set
	 */
	public void setNombreArtistaCombobox(ComboBox<String> nombreArtistaCombobox) {
		this.nombreArtistaCombobox = nombreArtistaCombobox;
	}

	/**
	 * @param nombreGuionistaCombobox the nombreGuionistaCombobox to set
	 */
	public void setNombreGuionistaCombobox(ComboBox<String> nombreGuionistaCombobox) {
		this.nombreGuionistaCombobox = nombreGuionistaCombobox;
	}

	/**
	 * @param nombreVarianteCombobox the nombreVarianteCombobox to set
	 */
	public void setNombreVarianteCombobox(ComboBox<String> nombreVarianteCombobox) {
		this.nombreVarianteCombobox = nombreVarianteCombobox;
	}

	/**
	 * @param comboPreviewsCombobox the comboPreviewsCombobox to set
	 */
	public void setComboPreviewsCombobox(ComboBox<String> comboPreviewsCombobox) {
		this.comboPreviewsCombobox = comboPreviewsCombobox;
	}

	/**
	 * @param comboPreviews the comboPreviews to set
	 */
	public void setComboPreviews(ComboBox<String> comboPreviews) {
		this.comboPreviews = comboPreviews;
	}

	/**
	 * @param dataPickFechaP the dataPickFechaP to set
	 */
	public void setDataPickFechaP(DatePicker dataPickFechaP) {
		this.dataPickFechaP = dataPickFechaP;
	}

	/**
	 * @param prontInfoTextArea the prontInfoTextArea to set
	 */
	public void setProntInfoTextArea(TextArea prontInfoTextArea) {
		this.prontInfoTextArea = prontInfoTextArea;
	}

	/**
	 * @param keyEventoData the keyEventoData to set
	 */
	public void setKeyEventoData(TextArea keyEventoData) {
		this.keyEventoData = keyEventoData;
	}

	/**
	 * @param menuImportarFicheroCodigoBarras the menuImportarFicheroCodigoBarras to set
	 */
	public void setMenuImportarFicheroCodigoBarras(MenuItem menuImportarFicheroCodigoBarras) {
		this.menuImportarFicheroCodigoBarras = menuImportarFicheroCodigoBarras;
	}

	/**
	 * @param menuEventoAniadir the menuEventoAniadir to set
	 */
	public void setMenuEventoAniadir(MenuItem menuEventoAniadir) {
		this.menuEventoAniadir = menuEventoAniadir;
	}

	/**
	 * @param menuEventoModificar the menuEventoModificar to set
	 */
	public void setMenuEventoModificar(MenuItem menuEventoModificar) {
		this.menuEventoModificar = menuEventoModificar;
	}

	/**
	 * @param menuEstadisticaEstadistica the menuEstadisticaEstadistica to set
	 */
	public void setMenuEstadisticaEstadistica(MenuItem menuEstadisticaEstadistica) {
		this.menuEstadisticaEstadistica = menuEstadisticaEstadistica;
	}

	/**
	 * @param menuArchivoCerrar the menuArchivoCerrar to set
	 */
	public void setMenuArchivoCerrar(MenuItem menuArchivoCerrar) {
		this.menuArchivoCerrar = menuArchivoCerrar;
	}

	/**
	 * @param menuArchivoDelete the menuArchivoDelete to set
	 */
	public void setMenuArchivoDelete(MenuItem menuArchivoDelete) {
		this.menuArchivoDelete = menuArchivoDelete;
	}

	/**
	 * @param menuArchivoDesconectar the menuArchivoDesconectar to set
	 */
	public void setMenuArchivoDesconectar(MenuItem menuArchivoDesconectar) {
		this.menuArchivoDesconectar = menuArchivoDesconectar;
	}

	/**
	 * @param menuArchivoExcel the menuArchivoExcel to set
	 */
	public void setMenuArchivoExcel(MenuItem menuArchivoExcel) {
		this.menuArchivoExcel = menuArchivoExcel;
	}

	/**
	 * @param menuArchivoImportar the menuArchivoImportar to set
	 */
	public void setMenuArchivoImportar(MenuItem menuArchivoImportar) {
		this.menuArchivoImportar = menuArchivoImportar;
	}

	/**
	 * @param menuArchivoSobreMi the menuArchivoSobreMi to set
	 */
	public void setMenuArchivoSobreMi(MenuItem menuArchivoSobreMi) {
		this.menuArchivoSobreMi = menuArchivoSobreMi;
	}

	/**
	 * @param menuEstadisticaSumaTotal the menuEstadisticaSumaTotal to set
	 */
	public void setMenuEstadisticaSumaTotal(MenuItem menuEstadisticaSumaTotal) {
		this.menuEstadisticaSumaTotal = menuEstadisticaSumaTotal;
	}

	/**
	 * @param menuArchivoAvanzado the menuArchivoAvanzado to set
	 */
	public void setMenuArchivoAvanzado(MenuItem menuArchivoAvanzado) {
		this.menuArchivoAvanzado = menuArchivoAvanzado;
	}

	/**
	 * @param navegacionCerrar the navegacionCerrar to set
	 */
	public void setNavegacionCerrar(Menu navegacionCerrar) {
		this.navegacionCerrar = navegacionCerrar;
	}

	/**
	 * @param navegacionEvento the navegacionEvento to set
	 */
	public void setNavegacionEvento(Menu navegacionEvento) {
		this.navegacionEvento = navegacionEvento;
	}

	/**
	 * @param navegacionEstadistica the navegacionEstadistica to set
	 */
	public void setNavegacionEstadistica(Menu navegacionEstadistica) {
		this.navegacionEstadistica = navegacionEstadistica;
	}

	/**
	 * @param menuNavegacion the menuNavegacion to set
	 */
	public void setMenuNavegacion(MenuBar menuNavegacion) {
		this.menuNavegacion = menuNavegacion;
	}

	/**
	 * @param progresoCarga the progresoCarga to set
	 */
	public void setProgresoCarga(ProgressIndicator progresoCarga) {
		this.progresoCarga = progresoCarga;
	}

	/**
	 * @param checkFirmas the checkFirmas to set
	 */
	public void setCheckFirmas(CheckBox checkFirmas) {
		this.checkFirmas = checkFirmas;
	}

	/**
	 * @param controlAccion the controlAccion to set
	 */
	public void setControlAccion(List<Control> controlAccion) {
		this.controlAccion = controlAccion;
	}

	/**
	 * @param listaComboboxes the listaComboboxes to set
	 */
	public static void setListaComboboxes(List<ComboBox<String>> listaComboboxes) {
		AccionReferencias.listaComboboxes = listaComboboxes;
	}

	/**
	 * @param listaColumnasTabla the listaColumnasTabla to set
	 */
	public static void setListaColumnasTabla(List<TableColumn<Evento, String>> listaColumnasTabla) {
		AccionReferencias.listaColumnasTabla = listaColumnasTabla;
	}

	/**
	 * @param listaTextFields the listaTextFields to set
	 */
	public static void setListaTextFields(ObservableList<Control> listaTextFields) {
		AccionReferencias.listaTextFields = listaTextFields;
	}

	/**
	 * @param listaBotones the listaBotones to set
	 */
	public static void setListaBotones(ObservableList<Button> listaBotones) {
		AccionReferencias.listaBotones = listaBotones;
	}

	/**
	 * @param listaElementosFondo the listaElementosFondo to set
	 */
	public static void setListaElementosFondo(ObservableList<Node> listaElementosFondo) {
		AccionReferencias.listaElementosFondo = listaElementosFondo;
	}

	/**
	 * @param stageVentana the stageVentana to set
	 */
	public void setStageVentana(Stage stageVentana) {
		this.stageVentana = stageVentana;
	}
}