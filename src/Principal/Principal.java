package Principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Conciertos.Concierto;
import Conciertos.GestorConciertos;
import Dbmanager.DbConnection;
import Dbmanager.DbInsertManager;
import Dbmanager.DbManager;
import Musicos.Musico;
import Utilidades.FuncionesMenu;

public class Principal {
	
	public static DbConnection conn = new DbConnection();
	
	public static void main(String[] args) {
		

		
//		Scanner sc = new Scanner(System.in);
//		GestorConciertos gestor = new GestorConciertos(new ArrayList<>());
//		int opcion;
//
//		do {
//			System.out.println("\n*** Menú Principal ***");
//			System.out.println("1. Crear y gestionar concierto");
//			System.out.println("2. Mostrar información de conciertos");
//			System.out.println("3. Salir");
//			System.out.print("Seleccione una opción: ");
//			opcion = sc.nextInt();
//			sc.nextLine(); // Consumir el salto de línea después de nextInt()
//
//			switch (opcion) {
//			case 1:
//				int subOpcion;
//				Concierto concierto = new Concierto();
//				do {
//					System.out.println("\n*** Menú de Gestión de Conciertos ***");
//					System.out.println("1. Crear bolo");
//					System.out.println("2. Calcular precio a pagar por cada músico");
//					System.out.println("3. Salir");
//
//					System.out.print("Seleccione una opción: ");
//					subOpcion = sc.nextInt();
//					sc.nextLine(); // Consumir el salto de línea después de nextInt()
//
//					switch (subOpcion) {
//					case 1:
//						System.out.println("ATENCIÓN. SE VA A CREAR UN NUEVO BOLO. CADA CONCIERTO ES INDIVIDUAL");
//
//						// Generar un ID único para el nuevo concierto
//						String numIdBolo = FuncionesMenu.obtenerIdUnico(gestor.getListaConciertos());
//
//						// Agregar un nuevo músico y concierto
//						List<Musico> listaMusicos = FuncionesMenu.crearListaMusicos(sc, numIdBolo);
//						concierto = FuncionesMenu.agregarConcierto(sc, numIdBolo, listaMusicos);
//						gestor.aniadirConcierto(concierto);
//						break;
//					case 2:
//						FuncionesMenu.precioMusicoPagar(concierto);
//						break;
//					case 3:
//						System.out.println("Saliendo del menú de gestión de conciertos.");
//						break;
//					default:
//						System.out.println("Opción no válida. Intente de nuevo.");
//					}
//				} while (subOpcion != 4);
//
//				break;
//
//			case 2:
//				FuncionesMenu.listarConciertos(gestor, sc);
//
//				break;
//
//			case 3:
//				System.out.println("Saliendo del programa...");
//				break;
//
//			default:
//				System.out.println("Opción no válida. Intente de nuevo.");
//			}
//		} while (opcion != 3);
//
//		sc.close();
	}
}
