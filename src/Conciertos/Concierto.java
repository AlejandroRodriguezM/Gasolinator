package Conciertos;

import java.util.UUID;

public class Concierto {

	private String idConcierto;
	private String nombreConcierto;
	private String fechaConcierto;

	public Concierto(String idConcierto, String nombreConcierto, String fechaConcierto) {
		super();
		this.idConcierto = idConcierto;
		this.nombreConcierto = nombreConcierto;
		this.fechaConcierto = fechaConcierto;
	}

	public Concierto() {
		super();
		this.idConcierto = "";
		this.nombreConcierto = "";
		this.fechaConcierto = "";
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

	@Override
	public String toString() {
		return "Concierto [idConcierto=" + idConcierto + ", nombreConcierto=" + nombreConcierto + ", fechaConcierto="
				+ fechaConcierto + "]";
	}
}
