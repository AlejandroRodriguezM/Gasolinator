package com.moneymakers.gasolinator.conciertos;

import com.moneymakers.gasolinator.musicos.Musico;

import java.util.ArrayList;
import java.util.List;


public class Concierto {

    private String idConcierto;
    private String nombreConcierto;
    private List<Musico> musicos;
    private String fechaConcierto;

    // Constructor con parámetros
    public Concierto(String idConcierto, String nombreConcierto, List<Musico> musicos, String fechaConcierto) {
        super();
        this.idConcierto = idConcierto;
        this.nombreConcierto = nombreConcierto;
        this.musicos = musicos;
        this.fechaConcierto = fechaConcierto;
    }

    // Constructor sin parámetros
    public Concierto() {
        super();
        this.idConcierto = "";
        this.nombreConcierto = "";
        this.fechaConcierto = "";
        this.musicos = new ArrayList<>();
    }

    // Getters
    public String getIdConcierto() {
        return idConcierto;
    }

    public String getNombreConcierto() {
        return nombreConcierto;
    }

    public List<Musico> getMusicos() {
        return musicos;
    }

    // Setters
    public void setIdConcierto(String idConcierto) {
        this.idConcierto = idConcierto;
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

    public void setMusicos(List<Musico> musicos) {
        this.musicos = musicos;
    }

    // Método toString
    @Override
    public String toString() {
        return "Concierto:" + "\n\tidConcierto: " + idConcierto + " - " + "\n\tnombreConcierto: " + nombreConcierto
                 + " - " + "\n\tmusicos: " + musicos + " - " + "\n\tfechaConcierto: " + fechaConcierto + "\n";
    }
}
