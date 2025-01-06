package com.moneymakers.gasolinator.eventos;

import java.util.Objects;

public class Evento {
    protected String idEvento; // Nuevo campo agregado
    protected String identificadorConcierto;
    protected String codigoMusico;
    protected boolean esConductor;
    protected double kmTotales; // Campo modificado
    protected double costoPagar;
    protected double totalAdelantado;
    protected String resultadoPagar;

    // Constructor con todos los campos, incluyendo idEvento
    public Evento(String idEvento, String identificadorConcierto, String codigoMusico, boolean esConductor,
                  double kmTotales, double costoPagar, double totalAdelantado, String resultadoPagar) {
        this.idEvento = idEvento;
        this.identificadorConcierto = identificadorConcierto;
        this.codigoMusico = codigoMusico;
        this.esConductor = esConductor;
        this.kmTotales = kmTotales; // Campo modificado
        this.costoPagar = costoPagar;
        this.totalAdelantado = totalAdelantado;
        this.resultadoPagar = resultadoPagar;
    }

    // Constructor vacío
    public Evento() {
        this.idEvento = ""; // Valor por defecto
        this.identificadorConcierto = "";
        this.codigoMusico = "";
        this.esConductor = false;
        this.kmTotales = 0.0; // Campo modificado
        this.costoPagar = 0.0;
        this.totalAdelantado = 0.0;
        this.resultadoPagar = "";
    }

    // Getters
    public String getIdEvento() {
        return idEvento;
    }

    public String getIdentificadorConcierto() {
        return identificadorConcierto;
    }

    public String getCodigoMusico() {
        return codigoMusico;
    }

    public boolean isEsConductor() {
        return esConductor;
    }

    public double getKmTotales() {
        return kmTotales; // Campo modificado
    }

    public double getCostoPagar() {
        return costoPagar;
    }

    public double getTotalAdelantado() {
        return totalAdelantado;
    }

    public String getResultadoPagar() {
        return resultadoPagar;
    }

    // Setters
    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public void setIdentificadorConcierto(String identificadorConcierto) {
        this.identificadorConcierto = identificadorConcierto;
    }

    public void setCodigoMusico(String codigoMusico) {
        this.codigoMusico = codigoMusico;
    }

    public void setEsConductor(boolean esConductor) {
        this.esConductor = esConductor;
    }

    public void setKmTotales(double kmTotales) {
        this.kmTotales = kmTotales; // Campo modificado
    }

    public void setCostoPagar(double costoPagar) {
        this.costoPagar = costoPagar;
    }

    public void setTotalAdelantado(double totalAdelantado) {
        this.totalAdelantado = totalAdelantado;
    }

    public void setResultadoPagar(String resultadoPagar) {
        this.resultadoPagar = resultadoPagar;
    }

    // Builder class
    public static class EventoBuilder {
        private String idEvento; // Nuevo campo agregado al builder
        private String identificadorConcierto;
        private String codigoMusico;
        private boolean esConductor;
        private double kmTotales; // Campo modificado
        private double costoPagar;
        private double totalAdelantado;
        private String resultadoPagar;

        public EventoBuilder(String idEvento, String identificadorConcierto, String codigoMusico) {
            this.idEvento = idEvento; // Inicializar idEvento en el constructor del builder
            this.identificadorConcierto = identificadorConcierto;
            this.codigoMusico = codigoMusico;
        }

        public EventoBuilder esConductor(boolean esConductor) {
            this.esConductor = esConductor;
            return this;
        }

        public EventoBuilder kmTotales(double kmTotales) {
            this.kmTotales = kmTotales; // Campo modificado
            return this;
        }

        public EventoBuilder costoPagar(double costoPagar) {
            this.costoPagar = costoPagar;
            return this;
        }

        public EventoBuilder totalAdelantado(double totalAdelantado) {
            this.totalAdelantado = totalAdelantado;
            return this;
        }

        public EventoBuilder resultadoPagar(String resultadoPagar) {
            this.resultadoPagar = resultadoPagar;
            return this;
        }

        public Evento build() {
            return new Evento(this);
        }
    }

    // Constructor privado para el Builder
    private Evento(EventoBuilder builder) {
        this.idEvento = builder.idEvento; // Asignar idEvento del builder al campo de clase
        this.identificadorConcierto = builder.identificadorConcierto;
        this.codigoMusico = builder.codigoMusico;
        this.esConductor = builder.esConductor;
        this.kmTotales = builder.kmTotales; // Campo modificado
        this.costoPagar = builder.costoPagar;
        this.totalAdelantado = builder.totalAdelantado;
        this.resultadoPagar = builder.resultadoPagar;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "idEvento=" + idEvento + // Mostrar idEvento en toString
                ", identificadorConcierto='" + identificadorConcierto + '\'' +
                ", codigoMusico='" + codigoMusico + '\'' +
                ", esConductor=" + esConductor +
                ", kmTotales=" + kmTotales + // Campo modificado
                ", costoPagar=" + costoPagar +
                ", totalAdelantado=" + totalAdelantado +
                ", resultadoPagar='" + resultadoPagar + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return idEvento == evento.idEvento && // Comparar idEvento en equals
                esConductor == evento.esConductor &&
                Double.compare(evento.kmTotales, kmTotales) == 0 && // Campo modificado
                Double.compare(evento.costoPagar, costoPagar) == 0 &&
                Double.compare(evento.totalAdelantado, totalAdelantado) == 0 &&
                Objects.equals(identificadorConcierto, evento.identificadorConcierto) &&
                Objects.equals(codigoMusico, evento.codigoMusico) &&
                Objects.equals(resultadoPagar, evento.resultadoPagar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvento, identificadorConcierto, codigoMusico, esConductor, kmTotales, costoPagar, totalAdelantado, resultadoPagar); // Incluir idEvento en hashCode
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Evento(idEvento, identificadorConcierto, codigoMusico, esConductor, kmTotales, costoPagar, totalAdelantado, resultadoPagar); // Incluir idEvento en clone
    }

    public static String limpiarCampo(String campo) {
        if (campo != null) {
            campo = campo.replaceAll("^\\s*[,\\s-]+", ""); // Al principio
            campo = campo.replaceAll("[,\\s-]+\\s*$", ""); // Al final
            campo = campo.replaceAll(",\\s*,", ","); // Comas repetidas
            campo = campo.replaceAll(",\\s*", " - "); // Reemplazar ", " por " - "
            campo = campo.replace("'", " "); // Reemplazar comillas simples por espacios
        } else {
            return "";
        }
        return campo;
    }

    public void sustituirCaracteres(Evento evento) {
        evento.setIdEvento(evento.getIdEvento().replaceAll("[\"';]", ""));
        evento.setIdentificadorConcierto(evento.getIdentificadorConcierto().replaceAll("[\"';]", ""));
        evento.setCodigoMusico(evento.getCodigoMusico().replaceAll("[\"';]", ""));
        evento.setResultadoPagar(evento.getResultadoPagar().replaceAll("[\"';]", ""));
    }

    public static String cleanString(String value) {
        if (value != null) {
            // Eliminamos comillas dobles y simples
            value = value.replace("\"", "").replace("'", "");
        }
        return value;
    }
}