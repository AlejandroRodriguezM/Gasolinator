package Evento;

public class Evento {

    private String identificadorConcierto;
    private String codigoMusico;
    private boolean esConductor;
    private double kmIda;
    private double kmVuelta;
    private double costoPagar;
    private double totalAdelantado;
    private String fechaConcierto;
    private String resultadoPagar;

    // Constructor con todos los campos
    public Evento(String identificadorConcierto, String codigoMusico, boolean esConductor,
                  double kmIda, double kmVuelta, double costoPagar, double totalAdelantado, String fechaConcierto,
                  String resultadoPagar) {
        this.identificadorConcierto = identificadorConcierto;
        this.codigoMusico = codigoMusico;
        this.esConductor = esConductor;
        this.kmIda = kmIda;
        this.kmVuelta = kmVuelta;
        this.costoPagar = costoPagar;
        this.totalAdelantado = totalAdelantado;
        this.fechaConcierto = fechaConcierto;
        this.resultadoPagar = resultadoPagar;
    }
    
    public Evento() {
        this.identificadorConcierto = "";
        this.codigoMusico = "";
        this.esConductor = false;
        this.kmIda = 0.0;
        this.kmVuelta = 0.0;
        this.costoPagar = 0.0;
        this.totalAdelantado = 0.0;
        this.fechaConcierto = "";
        this.resultadoPagar = "";
    }

    /**
	 * @return the identificadorConcierto
	 */
	public String getIdentificadorConcierto() {
		return identificadorConcierto;
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
	public boolean isEsConductor() {
		return esConductor;
	}

	/**
	 * @return the kmIda
	 */
	public double getKmIda() {
		return kmIda;
	}

	/**
	 * @return the kmVuelta
	 */
	public double getKmVuelta() {
		return kmVuelta;
	}

	/**
	 * @return the costoPagar
	 */
	public double getCostoPagar() {
		return costoPagar;
	}

	/**
	 * @return the totalAdelantado
	 */
	public double getTotalAdelantado() {
		return totalAdelantado;
	}

	/**
	 * @return the fechaConcierto
	 */
	public String getFechaConcierto() {
		return fechaConcierto;
	}

	/**
	 * @return the resultadoPagar
	 */
	public String getResultadoPagar() {
		return resultadoPagar;
	}

	/**
	 * @param identificadorConcierto the identificadorConcierto to set
	 */
	public void setIdentificadorConcierto(String identificadorConcierto) {
		this.identificadorConcierto = identificadorConcierto;
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
	 * @param kmIda the kmIda to set
	 */
	public void setKmIda(double kmIda) {
		this.kmIda = kmIda;
	}

	/**
	 * @param kmVuelta the kmVuelta to set
	 */
	public void setKmVuelta(double kmVuelta) {
		this.kmVuelta = kmVuelta;
	}

	/**
	 * @param costoPagar the costoPagar to set
	 */
	public void setCostoPagar(double costoPagar) {
		this.costoPagar = costoPagar;
	}

	/**
	 * @param totalAdelantado the totalAdelantado to set
	 */
	public void setTotalAdelantado(double totalAdelantado) {
		this.totalAdelantado = totalAdelantado;
	}

	/**
	 * @param fechaConcierto the fechaConcierto to set
	 */
	public void setFechaConcierto(String fechaConcierto) {
		this.fechaConcierto = fechaConcierto;
	}

	/**
	 * @param resultadoPagar the resultadoPagar to set
	 */
	public void setResultadoPagar(String resultadoPagar) {
		this.resultadoPagar = resultadoPagar;
	}

	@Override
    public String toString() {
        return "Evento{" +
                "identificadorConcierto='" + identificadorConcierto + '\'' +
                ", codigoMusico='" + codigoMusico + '\'' +
                ", esConductor=" + esConductor +
                ", kmIda=" + kmIda +
                ", kmVuelta=" + kmVuelta +
                ", costoPagar=" + costoPagar +
                ", totalAdelantado=" + totalAdelantado +
                ", fechaConcierto='" + fechaConcierto + '\'' +
                ", resultadoPagar='" + resultadoPagar + '\'' +
                '}';
    }
}
