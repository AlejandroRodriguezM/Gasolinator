package EventoManagement;

public class EventoCache {
    // Variable estática para almacenar el objeto Evento en caché.
    private static Evento eventoConcierto;

    // Método para establecer la caché del Evento.
    public static Evento setEventoCache(Evento evento) {
    	eventoConcierto = evento;
        return eventoConcierto; // Retorna el comic que se acaba de almacenar en la caché.
    }

    // Método para obtener el Evento almacenado en caché.
    public static Evento getEventoCache() {
        return eventoConcierto; // Retorna el comic almacenado en la caché.
    }
}
