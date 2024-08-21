package Utilidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Conciertos.Concierto;
import Conciertos.GestorConciertos;
import Musicos.Musico;

public class FuncionesMenu {

	public static List<Musico> crearListaMusicos(Scanner sc, String numId) {
		// Lista de nombres de músicos permitidos
		List<String> nombresPermitidos = Arrays.asList(OpcionesEstaticas.nomMusicos);
		List<Musico> listaMusicos = new ArrayList<>();
		System.out.print("Numero de musicos: ");
		int numMusicos = sc.nextInt();
		sc.nextLine();

		// Validación del nombre del músico
		String nombreMusico;
		for (int i = 0; i < numMusicos; i++) {

			while (true) {

				// Mostrar la lista de nombres separados por " - "
				System.out.println("Músicos disponibles:");
				System.out.println(nombresPermitidos.stream().collect(Collectors.joining(" - ")));

				System.out.print("\nIngrese el nombre del músico: ");
				nombreMusico = sc.nextLine().trim();

				// Verificar si el nombre está en la lista de nombres permitidos
				if (nombresPermitidos.contains(nombreMusico) && !existeNombreEnLista(nombreMusico, listaMusicos)) {
					// Crear una lista temporal con los nombres permitidos excluyendo el nombre
					// seleccionado
					final String nombreSeleccionado = nombreMusico;
					nombresPermitidos = nombresPermitidos.stream()
							.filter(nombre -> !nombre.equalsIgnoreCase(nombreSeleccionado))
							.collect(Collectors.toList());
					break;
				} else if (!nombresPermitidos.contains(nombreMusico)) {
					System.out.println("Nombre no válido. Por favor, ingrese un nombre de la lista permitida.");
				} else {
					System.out.println(
							"El nombre del músico ya está en la lista. Por favor, ingrese un nombre diferente.");
				}
			}

			// Validación para saber si es conductor
			boolean esConductor;
			boolean conduceMoto = false;
			while (true) {
				System.out.print("¿Es conductor? (s/n): ");
				String respuesta = sc.nextLine().trim().toLowerCase();
				if (respuesta.equals("s")) {
					esConductor = true;
					System.out.println("¿Conduce moto? (s/n): ");
					String respuestaMoto = sc.nextLine().trim().toLowerCase();
					while (true) {
						if (respuestaMoto.equals("s")) {
							conduceMoto = true;
							break;
						} else if (respuestaMoto.equals("n")) {
							break;
						} else {
							System.out.println("Entrada no válida. Por favor, ingrese 's' para sí o 'n' para no.");
						}
					}
					break;
				} else if (respuesta.equals("n")) {
					esConductor = false;
					break;
				} else {
					System.out.println("Entrada no válida. Por favor, ingrese 's' para sí o 'n' para no.");
				}
			}

			// Validación para los kilómetros de ida y vuelta
			double kmIda = 0.0;
			double kmVuelta = 0.0;
			double dineroAdelantado = 0.0;

			if (esConductor) {
				dineroAdelantado = obtenerMontoPagado(sc);
				kmIda = obtenerKilometros(sc, "ida");
				kmVuelta = obtenerKilometros(sc, "vuelta");

				if (conduceMoto) {
					double kmTotal = (kmIda + kmVuelta) / 2;
					kmIda = kmTotal / 2;
					kmVuelta = kmTotal / 2;
				}

			}

			// Crear objeto Musico y agregar a la lista
			Musico musico = new Musico(nombreMusico, "", esConductor, kmIda, kmVuelta, dineroAdelantado, numId);
			listaMusicos.add(musico);
			System.out.println("Músico añadido correctamente.");
		}
		return listaMusicos;
	}

	private static boolean existeNombreEnLista(String nombre, List<Musico> listaMusicos) {
		for (Musico m : listaMusicos) {
			if (m.getNombreMusico().equalsIgnoreCase(nombre)) {
				return true;
			}
		}
		return false;
	}

	private static double obtenerKilometros(Scanner sc, String tipo) {
		double km = 0.0;
		while (true) {
			try {
				System.out.print("Ingrese kilómetros de " + tipo + ": ");
				km = sc.nextDouble();
				if (km < 0) {
					System.out.println("Los kilómetros no pueden ser negativos. Inténtelo de nuevo.");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Entrada no válida. Por favor, ingrese un número.");
				sc.next(); // Limpiar el buffer
			}
		}
		sc.nextLine(); // Limpiar el salto de línea
		return km;
	}

	private static double obtenerMontoPagado(Scanner sc) {
		double adelantado = 0.0;
		while (true) {
			try {
				System.out.print("Ingrese el total adelantado: ");
				adelantado = sc.nextDouble();
				if (adelantado >= 5) {
					adelantado *= -1;
				} else {
					adelantado = -5;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Entrada no válida. Por favor, ingrese un número.");
				sc.next(); // Limpiar el buffer
			}
		}
		sc.nextLine(); // Limpiar el salto de línea
		return adelantado;
	}

	public static Concierto agregarConcierto(Scanner sc, String numId, List<Musico> listaMusicos) {
		// Validación del nombre del concierto
		String nombreConcierto = "";
		while (true) {
			System.out.print("Ingrese el nombre del concierto: ");
			nombreConcierto = sc.nextLine();

			if (!nombreConcierto.trim().isEmpty()) {
				break;
			} else {
				System.out.println("Entrada no válida. Por favor, ingrese un nombre no vacío.");
			}
		}

		// Validación de la fecha del concierto
		String fechaConcierto = "";
		while (true) {
			System.out.print("Ingrese la fecha del concierto (dia mes año): ");
			fechaConcierto = sc.nextLine();

			// Convertir la fecha al formato requerido y verificar si es válido
			String fechaConvertida = Utilidades.stringAFecha(fechaConcierto);
			if (fechaConvertida != null) {
				fechaConcierto = fechaConvertida;
				break;
			} else {
				System.out.println(
						"Entrada no válida. Por favor, ingrese la fecha en el formato correcto (dd, MMMM, yyyy).");
			}
		}

		// Crear objeto Concierto y agregar a la lista
		Concierto concierto = new Concierto(numId, nombreConcierto, fechaConcierto, listaMusicos);
		System.out.println("Concierto creado correctamente.");
		return concierto;
	}

	public static void calcularKilometrosMusico(List<Musico> listaMusicos, List<Concierto> listaConciertos) {

		if (listaConciertos.isEmpty()) {
			System.out.println("No hay conciertos disponibles. Agregue un concierto primero.");
			return;
		}

		if (listaMusicos.isEmpty()) {
			System.out.println("No hay músicos disponibles. Agregue un músico primero.");
			return;
		}
	}

	public static void precioMusicoPagar(Concierto concierto) {

		if (concierto == null) {
			System.out.println("No hay ningun concierto creado, debes de crear uno antes");
			return;
		}

		CalculadorPagos.calcularConcierto(concierto);

	}

	// Lista los conciertos disponibles con sus fechas
	public static void listarConciertos(GestorConciertos gestor, Scanner sc) {

		System.out.println("*** Información de Conciertos ***");
		gestor.listarConciertos();

		System.out.print("Escoge un concierto: ");
		String conciertoId = sc.nextLine();

		Concierto conciertoEncontrado = gestor.buscarConciertoPorId(conciertoId);
		FuncionesMenu.verDetallesConcierto(conciertoEncontrado);
	}

	// Muestra los detalles de un concierto específico
	public static void verDetallesConcierto(Concierto concierto) {
		System.out.println("Detalles del Concierto ID: " + concierto.getIdConcierto() + ", Fecha: "
				+ concierto.getFechaConcierto());

		FuncionesMenu.precioMusicoPagar(concierto);

	}

}
