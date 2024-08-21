package Musicos;

public class Musico {

	private String nombreMusico;
	private String codigoMusico;
	private boolean esConductor;
	private double numKmIda;
	private double numKmVuelta;
	private double montoPagado;
	private String idConcierto;
	public Musico(String nombreMusico, String codigoMusico, boolean esConductor, double numKmIda, double numKmVuelta,
			double montoPagado, String idConcierto) {
		super();
		this.nombreMusico = nombreMusico;
		this.codigoMusico = codigoMusico;
		this.esConductor = esConductor;
		this.numKmIda = numKmIda;
		this.numKmVuelta = numKmVuelta;
		this.montoPagado = montoPagado;
		this.idConcierto = idConcierto;
	}
	/**
	 * @return the nombreMusico
	 */
	public String getNombreMusico() {
		return nombreMusico;
	}
	/**
	 * @return the codigoMusico
	 */
	public String getCodigoMusico() {
		return codigoMusico;
	}
	/**
	 * @return the esConductor
	 */
	public boolean getEsConductor() {
		return esConductor;
	}
	/**
	 * @return the numKmIda
	 */
	public double getNumKmIda() {
		return numKmIda;
	}
	/**
	 * @return the numKmVuelta
	 */
	public double getNumKmVuelta() {
		return numKmVuelta;
	}
	/**
	 * @return the montoPagado
	 */
	public double getMontoPagado() {
		return montoPagado;
	}
	/**
	 * @return the idConcierto
	 */
	public String getIdConcierto() {
		return idConcierto;
	}
	/**
	 * @param nombreMusico the nombreMusico to set
	 */
	public void setNombreMusico(String nombreMusico) {
		this.nombreMusico = nombreMusico;
	}
	/**
	 * @param codigoMusico the codigoMusico to set
	 */
	public void setCodigoMusico(String codigoMusico) {
		this.codigoMusico = codigoMusico;
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

}
