package Principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Conciertos.Concierto;
import Musicos.Musico;
import Utilidades.FuncionesMenu;
import Utilidades.Utilidades;

public class Principal {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Musico> listaMusicos = new ArrayList<>();
		List<Concierto> listaConciertos = new ArrayList<>();
		int opcion;

		do {
			System.out.println("\n*** Menú Principal ***");
			System.out.println("1. Crear y gestionar concierto");
			System.out.println("2. Mostrar información de conciertos");
			System.out.println("3. Salir");
			System.out.print("Seleccione una opción: ");
			opcion = sc.nextInt();
			sc.nextLine(); // Consumir el salto de línea después de nextInt()

			switch (opcion) {
			case 1:
				int subOpcion;
				do {
					System.out.println("\n*** Menú de Gestión de Conciertos ***");
					System.out.println("1. Crear bolo");
					System.out.println("2. Calcular kilómetros totales de un músico");
					System.out.println("3. Calcular precio a pagar por cada músico");
					System.out.println("4. Salir");

					System.out.print("Seleccione una opción: ");
					subOpcion = sc.nextInt();
					sc.nextLine(); // Consumir el salto de línea después de nextInt()

					switch (subOpcion) {
					case 1:
						System.out.println("ATENCIÓN. SE VA A CREAR UN NUEVO BOLO. CADA CONCIERTO ES INDIVIDUAL");

						// Generar un ID único para el nuevo concierto
						String numIdBolo = FuncionesMenu.obtenerIdUnico(listaConciertos);

						// Agregar un nuevo músico y concierto
						FuncionesMenu.agregarMusico(sc, listaMusicos, numIdBolo);
						FuncionesMenu.agregarConcierto(sc, listaConciertos, numIdBolo);
						break;
					case 2:
						if (listaConciertos.isEmpty()) {
							System.out.println("No hay conciertos disponibles. Agregue un concierto primero.");
						} else {
							FuncionesMenu.calcularKilometrosMusico(listaMusicos, listaConciertos);
						}
						break;
					case 3:
						if (listaConciertos.isEmpty()) {
							System.out.println("No hay conciertos disponibles. Agregue un concierto primero.");
						} else {
							FuncionesMenu.precioMusicoPagar(listaMusicos, listaConciertos);
						}
						break;
					case 4:
						System.out.println("Saliendo del menú de gestión de conciertos.");
						break;
					default:
						System.out.println("Opción no válida. Intente de nuevo.");
					}
				} while (subOpcion != 4);

				break;

			case 2:
				System.out.println("\n*** Información de Conciertos ***");
				for (Concierto c : listaConciertos) {
					System.out.println(c);
				}
				break;

			case 3:
				System.out.println("Saliendo del programa...");
				break;

			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		} while (opcion != 3);

		sc.close();
	}
}
