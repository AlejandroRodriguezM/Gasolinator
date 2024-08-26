package EventoManagement;

import java.util.Objects;

import dbmanager.ListasEventosDAO;
import dbmanager.SelectManager;

public class Evento {
    protected String idEvento; // Nuevo campo agregado
    protected String identificadorConcierto;
    protected String codigoMusico;
    protected boolean esConductor;
    protected double kmIda;
    protected double kmVuelta;
    protected double costoPagar;
    protected double totalAdelantado;
    protected String fechaConcierto;
    protected String resultadoPagar;

    // Constructor con todos los campos, incluyendo idEvento
    public Evento(String idEvento, String identificadorConcierto, String codigoMusico, boolean esConductor,
                  double kmIda, double kmVuelta, double costoPagar, double totalAdelantado, String fechaConcierto,
                  String resultadoPagar) {
        this.idEvento = idEvento;
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

    // Constructor vacío
    public Evento() {
        this.idEvento = ""; // Valor por defecto
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

    public double getKmIda() {
        return kmIda;
    }

    public double getKmVuelta() {
        return kmVuelta;
    }

    public double getCostoPagar() {
        return costoPagar;
    }

    public double getTotalAdelantado() {
        return totalAdelantado;
    }

    public String getFechaConcierto() {
        return fechaConcierto;
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

    public void setKmIda(double kmIda) {
        this.kmIda = kmIda;
    }

    public void setKmVuelta(double kmVuelta) {
        this.kmVuelta = kmVuelta;
    }

    public void setCostoPagar(double costoPagar) {
        this.costoPagar = costoPagar;
    }

    public void setTotalAdelantado(double totalAdelantado) {
        this.totalAdelantado = totalAdelantado;
    }

    public void setFechaConcierto(String fechaConcierto) {
        this.fechaConcierto = fechaConcierto;
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
        private double kmIda;
        private double kmVuelta;
        private double costoPagar;
        private double totalAdelantado;
        private String fechaConcierto;
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

        public EventoBuilder kmIda(double kmIda) {
            this.kmIda = kmIda;
            return this;
        }

        public EventoBuilder kmVuelta(double kmVuelta) {
            this.kmVuelta = kmVuelta;
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

        public EventoBuilder fechaConcierto(String fechaConcierto) {
            this.fechaConcierto = fechaConcierto;
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
        this.kmIda = builder.kmIda;
        this.kmVuelta = builder.kmVuelta;
        this.costoPagar = builder.costoPagar;
        this.totalAdelantado = builder.totalAdelantado;
        this.fechaConcierto = builder.fechaConcierto;
        this.resultadoPagar = builder.resultadoPagar;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "idEvento=" + idEvento + // Mostrar idEvento en toString
                ", identificadorConcierto='" + identificadorConcierto + '\'' +
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

	public static Evento obtenerEvento(String idEvento) {
		boolean existeEvento = SelectManager.comprobarIdentificadorEvento(idEvento);
		if (!existeEvento) {
			existeEvento = ListasEventosDAO.verificarIDExistente(idEvento);
			if (existeEvento) {
				return ListasEventosDAO.devolverEventoLista(idEvento);
			}
		} else {
			return SelectManager.eventoDatos(idEvento);
		}
		return null;
	}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return idEvento == evento.idEvento && // Comparar idEvento en equals
                esConductor == evento.esConductor &&
                Double.compare(evento.kmIda, kmIda) == 0 &&
                Double.compare(evento.kmVuelta, kmVuelta) == 0 &&
                Double.compare(evento.costoPagar, costoPagar) == 0 &&
                Double.compare(evento.totalAdelantado, totalAdelantado) == 0 &&
                Objects.equals(identificadorConcierto, evento.identificadorConcierto) &&
                Objects.equals(codigoMusico, evento.codigoMusico) &&
                Objects.equals(fechaConcierto, evento.fechaConcierto) &&
                Objects.equals(resultadoPagar, evento.resultadoPagar);
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
        evento.setFechaConcierto(evento.getFechaConcierto().replaceAll("[\"';]", ""));
        evento.setResultadoPagar(evento.getResultadoPagar().replaceAll("[\"';]", ""));
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvento, identificadorConcierto, codigoMusico, esConductor, kmIda, kmVuelta, costoPagar, totalAdelantado, fechaConcierto, resultadoPagar); // Incluir idEvento en hashCode
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Evento(idEvento, identificadorConcierto, codigoMusico, esConductor, kmIda, kmVuelta, costoPagar, totalAdelantado, fechaConcierto, resultadoPagar); // Incluir idEvento en clone
    }
    
	public static String cleanString(String value) {
		if (value != null) {
			// Eliminamos comillas dobles y simples
			value = value.replace("\"", "").replace("'", "");
		}
		return value;
	}
}
