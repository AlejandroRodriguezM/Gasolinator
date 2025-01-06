package com.moneymakers.gasolinator.utilities;

import com.moneymakers.gasolinator.conciertos.Concierto;

import java.util.List;
import java.util.Random;

public class Utilidades {

    private static final int LONGITUD_ID = 4; // Número de dígitos para el ID
    private static Random random = new Random();
    // Genera un número aleatorio de 4 dígitos
    public static String generarNumeroRandom() {
        int numero = random.nextInt((int) Math.pow(10, LONGITUD_ID));
        return String.format("%0" + LONGITUD_ID + "d", numero); // Formatea el número para que tenga 4 dígitos
    }

    public static double precioPagar(double kmTotal, int numMusicos) {
        return (kmTotal * OpcionesEstaticas.GASOLINA_VALOR) / numMusicos;
    }

    // Genera un ID único
    public static String obtenerIdUnico(List<Concierto> listaConciertos) {

        String nuevoId;
        boolean idUnico = false;

        // Obtiene la lista de IDs existentes
        List<String> idsExistentes = listaConciertos.stream().map(Concierto::getIdConcierto).toList();

        do {
            nuevoId = generarNumeroRandom(); // Genera un nuevo ID
            // Verifica si el nuevo ID no está en la lista de IDs existentes
            if (!idsExistentes.contains(nuevoId)) {
                idUnico = true; // El ID es único
            }
        } while (!idUnico);

        return nuevoId;
    }

}
