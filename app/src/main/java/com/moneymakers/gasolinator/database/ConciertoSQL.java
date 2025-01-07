package com.moneymakers.gasolinator.database;

public class ConciertoSQL {
    private int idConcierto;
    private String nombreConcierto;
    private String fechaConcierto;
    private String resumenViaje;

    // Constructor
    public ConciertoSQL(int idConcierto, String nombreConcierto, String fechaConcierto, String resumenViaje) {
        this.idConcierto = idConcierto;
        this.nombreConcierto= nombreConcierto;
        this.fechaConcierto = fechaConcierto;
        this.resumenViaje = resumenViaje;
    }

    // Getters and Setters
    public int getIdConcierto() {
        return idConcierto;
    }

    public void setIdConcierto(int idConcierto) {
        this.idConcierto = idConcierto;
    }

    public String getNombreConcierto() {
        return nombreConcierto;
    }

    public void setNombreConcierto(String nombreConcierto) {
        this.nombreConcierto = nombreConcierto;
    }

    public String getFechaConcierto() {
        return fechaConcierto;
    }

    public void setFechaConcierto(String fechaConcierto) {
        this.fechaConcierto = fechaConcierto;
    }

    public String getResumenViaje() {
        return resumenViaje;
    }

    public void setResumenViaje(String resumenViaje) {
        this.resumenViaje =resumenViaje;
    }
}
