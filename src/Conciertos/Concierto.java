package Conciertos;

import java.util.ArrayList;
import java.util.List;

import Musicos.Musico;

public class Concierto {

	private String idConcierto;
	private String nombreConcierto;
	private String fechaConcierto;
	private List<Musico> musicos;

	public Concierto(String idConcierto, String nombreConcierto, String fechaConcierto, List<Musico> musicos) {
		super();
		this.idConcierto = idConcierto;
		this.nombreConcierto = nombreConcierto;
		this.fechaConcierto = fechaConcierto;
		this.musicos = musicos;
	}

	public Concierto() {
		super();
		this.idConcierto = "";
		this.nombreConcierto = "";
		this.fechaConcierto = "";
		this.musicos = new ArrayList<>();
	}

	/**
	 * @return the idConcierto
	 */
	public String getIdConcierto() {
		return idConcierto;
	}

	/**
	 * @return the nombreConcierto
	 */
	public String getNombreConcierto() {
		return nombreConcierto;
	}

	/**
	 * @return the fechaConcierto
	 */
	public String getFechaConcierto() {
		return fechaConcierto;
	}

	/**
	 * @return the musicos
	 */
	public List<Musico> getMusicos() {
		return musicos;
	}

	/**
	 * @param idConcierto the idConcierto to set
	 */
	public void setIdConcierto(String idConcierto) {
		this.idConcierto = idConcierto;
	}

	/**
	 * @param nombreConcierto the nombreConcierto to set
	 */
	public void setNombreConcierto(String nombreConcierto) {
		this.nombreConcierto = nombreConcierto;
	}

	/**
	 * @param fechaConcierto the fechaConcierto to set
	 */
	public void setFechaConcierto(String fechaConcierto) {
		this.fechaConcierto = fechaConcierto;
	}

	/**
	 * @param musicos the musicos to set
	 */
	public void setMusicos(List<Musico> musicos) {
		this.musicos = musicos;
	}

	@Override
	public String toString() {
		return "Concierto:" + "\n\tidConcierto: " + idConcierto + " - " + "\n\tnombreConcierto: " + nombreConcierto + " - "
				+ "\n\tfechaConcierto: " + fechaConcierto + " - " + "\n\tmusicos: " + musicos + "\n";
	}

}
