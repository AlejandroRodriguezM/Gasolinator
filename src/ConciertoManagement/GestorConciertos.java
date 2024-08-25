package ConciertoManagement;

import java.util.List;

public class GestorConciertos {
    
    private List<Concierto> listaConciertos;

    public GestorConciertos(List<Concierto> listaConciertos) {
        this.listaConciertos = listaConciertos;
    }

    /**
	 * @return the listaConciertos
	 */
	public List<Concierto> getListaConciertos() {
		return listaConciertos;
	}

	/**
	 * @param listaConciertos the listaConciertos to set
	 */
	public void setListaConciertos(List<Concierto> listaConciertos) {
		this.listaConciertos = listaConciertos;
	}

	// Método para encontrar un concierto por su ID
    public Concierto buscarConciertoPorId(String idConcierto) {
        for (Concierto concierto : listaConciertos) {
            if (concierto.getIdConcierto().equals(idConcierto)) {
                return concierto;
            }
        }
        return null; // Devuelve null si no se encuentra el concierto con el ID dado
    }

    // Método para añadir un concierto a la lista
    public void aniadirConcierto(Concierto concierto) {
        this.listaConciertos.add(concierto);
    }

    // Método para mostrar todos los conciertos disponibles
    public void listarConciertos() {
    	
    	if(listaConciertos.isEmpty()) {
    		System.err.println("No hay conciertos que mostrar");
    		return;
    	}
    	
        for (Concierto concierto : listaConciertos) {
            System.out.println(concierto.toString());
        }
    }
}
