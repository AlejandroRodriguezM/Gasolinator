package Utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidades {

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



}
