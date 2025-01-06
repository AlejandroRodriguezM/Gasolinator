package com.moneymakers.gasolinator.musicos;

public class Musico {

    private String nombreMusico;
    private String codigoMusico;
    private boolean esConductor;
    private boolean esConductorMoto;
    private double kmTotales;
    private double montoPagado;
    private String idConcierto;

    // Constructor con parámetros
    public Musico(String nombreMusico, String codigoMusico, boolean esConductor, boolean esConductorMoto, double kmTotales,
                  double montoPagado, String idConcierto) {
        super();
        this.nombreMusico = nombreMusico;
        this.codigoMusico = codigoMusico;
        this.esConductor = esConductor;
        this.esConductorMoto = esConductorMoto;
        this.kmTotales = kmTotales;
        this.montoPagado = montoPagado;
        this.idConcierto = idConcierto;
    }

    // Getters
    public String getNombreMusico() {
        return nombreMusico;
    }

    public String getCodigoMusico() {
        return codigoMusico;
    }

    public boolean getEsConductor() {
        return esConductor;
    }

    public boolean getEsConductorMoto() {
        return esConductorMoto;
    }

    public double getKmTotales() {
        return kmTotales;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public String getIdConcierto() {
        return idConcierto;
    }

    // Setters
    public void setNombreMusico(String nombreMusico) {
        this.nombreMusico = nombreMusico;
    }

    public void setCodigoMusico(String codigoMusico) {
        this.codigoMusico = codigoMusico;
    }

    public void setEsConductor(boolean esConductor) {
        this.esConductor = esConductor;
    }

    public void setEsConductorMoto(boolean esConductorMoto) {
        this.esConductorMoto = esConductorMoto;
    }

    public void setKmTotales(double kmTotales) {
        this.kmTotales = kmTotales;
    }

    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public void setIdConcierto(String idConcierto) {
        this.idConcierto = idConcierto;
    }
}