package Musicos;

public class Musico {

	private String nombreMusico;
	private boolean esConductor;
	private double numKmIda;
	private double numKmVuelta;
	private double montoPagado;
	private String idConcierto;

	public Musico(String nombreMusico, boolean esPrincipal, double numKmIda, double numKmVuelta, double montoPagado,
			String idConcierto) {
		this.nombreMusico = nombreMusico;
		this.esConductor = esPrincipal;
		this.numKmIda = numKmIda;
		this.numKmVuelta = numKmVuelta;
		this.montoPagado = montoPagado;
		this.idConcierto = idConcierto;
	}

	/**
	 * @param nombreMusico the nombreMusico to set
	 */
	public void setNombreMusico(String nombreMusico) {
		this.nombreMusico = nombreMusico;
	}

	/**
	 * @param esConductor the esConductor to set
	 */
	public void setEsConductor(boolean esConductor) {
		this.esConductor = esConductor;
	}

	/**
	 * @param numKmIda the numKmIda to set
	 */
	public void setNumKmIda(double numKmIda) {
		this.numKmIda = numKmIda;
	}

	/**
	 * @param numKmVuelta the numKmVuelta to set
	 */
	public void setNumKmVuelta(double numKmVuelta) {
		this.numKmVuelta = numKmVuelta;
	}

	/**
	 * @param montoPagado the montoPagado to set
	 */
	public void setMontoPagado(double montoPagado) {
		this.montoPagado = montoPagado;
	}

	/**
	 * @param idConcierto the idConcierto to set
	 */
	public void setIdConcierto(String idConcierto) {
		this.idConcierto = idConcierto;
	}

	public String getNombreMusico() {
		return nombreMusico;
	}

	public boolean getEsConductor() {
		return esConductor;
	}

	public double getNumKmIda() {
		return numKmIda;
	}

	public double getNumKmVuelta() {
		return numKmVuelta;
	}

	public double getMontoPagado() {
		return montoPagado;
	}

	public String getIdConcierto() {
		return idConcierto;
	}

	public static double kilometrosTotales(double kmIda, double kmVuelta) {
		return kmIda + kmVuelta;
	}

	@Override
	public String toString() {
		return "Musico [nombreMusico=" + nombreMusico + ", esConductor=" + esConductor + ", numKmIda=" + numKmIda
				+ ", numKmVuelta=" + numKmVuelta + ", idConcierto=" + idConcierto + "]";
	}

}
