package Utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import Conciertos.Concierto;

public class Utilidades {

	private static final int LONGITUD_ID = 4; // Número de dígitos para el ID
	private static Random random = new Random();
	
	private Utilidades() {
	}

	public static String stringAFecha(String fechaConcierto) {
		SimpleDateFormat originalFormat = new SimpleDateFormat("dd MM yyyy");
		SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = originalFormat.parse(fechaConcierto);
			return targetFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null; // o manejar el error de manera apropiada
		}
	}

	public static double precioPagar(double kmTotal, int numMusicos) {
		return (kmTotal * OpcionesEstaticas.valorGasolina) / numMusicos;
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

	// Genera un número aleatorio de 4 dígitos
	public static String generarNumeroRandom() {
		int numero = random.nextInt((int) Math.pow(10, LONGITUD_ID));
		return String.format("%0" + LONGITUD_ID + "d", numero); // Formatea el número para que tenga 4 dígitos
	}

}
