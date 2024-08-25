package funcionesAuxiliares;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ConciertoManagement.Concierto;
import EventoManagement.Evento;
import MusicoManagement.Musico;
import dbmanager.DeleteManager;
import dbmanager.InsertManager;
import dbmanager.SQLiteManager;

public class CalculadorPagos {

	private double precioGasoferaKm;

	static final private double PRECIO_GASOLINA = 0.2;

	public CalculadorPagos(double precioGasoferaKm) {
		this.precioGasoferaKm = precioGasoferaKm;
	}

	// Redondea un valor a dos cifras decimales
	private BigDecimal redondear(double valor) {
		return new BigDecimal(valor).setScale(2, RoundingMode.HALF_UP);
	}

	// Calcula la cuota total que debe pagar el músico
	public BigDecimal calcularCuotaTotal(double kmTotal) {
		return redondear(kmTotal * precioGasoferaKm);
	}

	public void ajustarPagosParaBolo(Concierto concierto) {
		List<Musico> musicos = concierto.getMusicos();
		double kmTotales = 0;
		double montoTotalConductores = 0;
		double diferencia = 0;
		// Calculamos la suma total de kilómetros y el monto total adelantado por los
		// conductores
		for (Musico musico : musicos) {
			kmTotales += musico.getNumKmIda() + musico.getNumKmVuelta();
			if (musico.getEsConductor()) {
				montoTotalConductores += musico.getMontoPagado();
			}
		}

		BigDecimal costoTotal = calcularCuotaTotal(kmTotales);
		BigDecimal cuotaPorMusico = costoTotal.divide(new BigDecimal(musicos.size()), RoundingMode.HALF_UP);

		// Si el monto total adelantado por los conductores es menor que el costo total,
		// se ajusta la cuota por músico.
		if (montoTotalConductores < costoTotal.doubleValue()) {
			diferencia = (costoTotal.doubleValue() - montoTotalConductores) / musicos.size();
			cuotaPorMusico = cuotaPorMusico.subtract(redondear(diferencia)).negate();
		}

		System.err.println("****************************************************************");
		System.out.println("Costo total de la gasolina: " + costoTotal + " €");
		System.out.println("Cuota por músico: " + cuotaPorMusico + " €");

		// Inicializar el saldo de los músicos
		for (Musico musico : musicos) {
			musico.setMontoPagado(redondear(musico.getMontoPagado() + cuotaPorMusico.doubleValue()).doubleValue());
		}

		// Listas de conductores y no conductores
		List<Musico> conductores = new ArrayList<>();
		List<Musico> noConductores = new ArrayList<>();
		System.err.println("****************************************************************");
		for (Musico musico : musicos) {
			if (musico.getEsConductor()) {
				conductores.add(musico);
			} else {
				noConductores.add(musico);
			}
		}

		System.err.println("****************************************************************");

		// Crear un mapa para almacenar las transacciones
		Map<String, String> transacciones = new HashMap<>();

		// Distribuir los pagos de los no conductores a los conductores con saldo
		// negativo
		for (Musico noConductor : noConductores) {
			BigDecimal montoRestante = cuotaPorMusico;
			for (Musico conductor : conductores) {
				BigDecimal saldoConductor = redondear(-conductor.getMontoPagado());
				if (saldoConductor.compareTo(BigDecimal.ZERO) > 0 && montoRestante.compareTo(BigDecimal.ZERO) > 0) {
					BigDecimal montoARecuperar = saldoConductor.min(montoRestante);
					if (montoARecuperar.compareTo(BigDecimal.ZERO) > 0) {
						String transaccion = noConductor.getNombreMusico() + " debe pagar a "
								+ conductor.getNombreMusico() + ": " + montoARecuperar + " €";
						System.out.println(transaccion);
						agregarTransaccion(transacciones, noConductor.getNombreMusico(), transaccion);
						montoRestante = montoRestante.subtract(montoARecuperar);
						conductor.setMontoPagado(
								redondear(conductor.getMontoPagado() + montoARecuperar.doubleValue()).doubleValue());
						noConductor.setMontoPagado(
								redondear(noConductor.getMontoPagado() - montoARecuperar.doubleValue()).doubleValue());
					}
				}
			}
		}

		// Si después de la distribución anterior, algún conductor tiene un saldo
		// positivo,
		// distribuirlo entre los otros conductores con saldo negativo.
		for (Musico conductorConPositivo : conductores) {
			if (conductorConPositivo.getMontoPagado() > 0) {
				BigDecimal montoExcedente = redondear(conductorConPositivo.getMontoPagado());
				for (Musico conductorConNegativo : conductores) {
					BigDecimal saldoConductorNegativo = redondear(-conductorConNegativo.getMontoPagado());
					if (saldoConductorNegativo.compareTo(BigDecimal.ZERO) > 0) {
						BigDecimal montoARecuperar = saldoConductorNegativo.min(montoExcedente);
						if (montoARecuperar.compareTo(BigDecimal.ZERO) > 0) {
							String transaccion = conductorConPositivo.getNombreMusico() + " debe pagar a "
									+ conductorConNegativo.getNombreMusico() + ": " + montoARecuperar + " €";
							System.out.println(transaccion);
							agregarTransaccion(transacciones, conductorConPositivo.getNombreMusico(), transaccion);
							montoExcedente = montoExcedente.subtract(montoARecuperar);
							conductorConNegativo.setMontoPagado(
									redondear(conductorConNegativo.getMontoPagado() + montoARecuperar.doubleValue())
											.doubleValue());
							conductorConPositivo.setMontoPagado(
									redondear(conductorConPositivo.getMontoPagado() - montoARecuperar.doubleValue())
											.doubleValue());
						}
					}
				}
			}
		}

		// Verificar los saldos finales para asegurar que todos sean cero
		for (Musico musico : musicos) {
			BigDecimal saldoFinal = redondear(musico.getMontoPagado());
			if (saldoFinal.compareTo(BigDecimal.ZERO) != 0) {
				System.err.println("Error: El saldo final de " + musico.getNombreMusico() + " no es cero, saldo: "
						+ saldoFinal + " €");
			}
		}

		System.err.println("****************************************************************");
		// Mostrar el saldo final de cada músico
		for (Musico musico : musicos) {
			System.out.println(musico.getNombreMusico() + " saldo final: "
					+ redondear(musico.getMontoPagado()).doubleValue() + " €");
		}
		System.err.println("****************************************************************");

		// Crear un objeto Evento para cada músico
		for (Musico musico : musicos) {
			Evento evento = new Evento("", // ID evento
					musico.getIdConcierto(), // Identificador del concierto
					musico.getCodigoMusico(), // Nombre del músico
					musico.getEsConductor(), // Si es conductor o no
					musico.getNumKmIda(), // Kilómetros de ida
					musico.getNumKmVuelta(), // Kilómetros de vuelta
					cuotaPorMusico.doubleValue(), // Cuota que debe pagar el músico
					musico.getMontoPagado(), // Total adelantado por el músico
					concierto.getFechaConcierto(), // Fecha del concierto
					"" // Resultado de pago (se llenará más adelante)
			);

			if (musico.getEsConductor()) {
				double gastoGasolinaAdelantado = (musico.getNumKmIda() + musico.getNumKmVuelta()) * PRECIO_GASOLINA;
				double dineroRecibirGasolina = gastoGasolinaAdelantado - cuotaPorMusico.doubleValue();
				evento.setTotalAdelantado(gastoGasolinaAdelantado);

				String mensajeRecibir = musico.getNombreMusico() + " debe de recibir en concepto de gasolina: "
						+ dineroRecibirGasolina;

				evento.setResultadoPagar(mensajeRecibir);

			}
			// Asignar el resultado de pago basado en el Map
			if (transacciones.containsKey(musico.getNombreMusico())) {
				evento.setResultadoPagar(transacciones.get(musico.getNombreMusico()));
			}
			InsertManager.insertViaje(evento);
		}
	}

	// Método para agregar una transacción a la cadena existente en el mapa
	private static void agregarTransaccion(Map<String, String> transacciones, String musico, String transaccion) {
		transacciones.merge(musico, transaccion, (existente, nuevo) -> existente + "\n" + nuevo);
	}

	public static void calcularConcierto(Concierto concierto) {
		CalculadorPagos calculador = new CalculadorPagos(PRECIO_GASOLINA);

		calculador.ajustarPagosParaBolo(concierto);
	}

	public static void main(String[] args) {

		SQLiteManager.createTable();

		InsertManager.insertarCantantes();

		DeleteManager.deleteTablaViaje();

		String numRandom = Utilidades.generarNumeroRandom();
		Musico javi = new Musico("Javi", "0001", true, 52, 53, -21, numRandom);
		Musico rafa = new Musico("Rafa", "0002", true, 25, 28, -10.6, numRandom);
		Musico denice = new Musico("Denice", "0003", false, 0, 0, 0, numRandom);
		Musico fran = new Musico("Fran", "0004", false, 0, 0, 0, numRandom);

		List<Musico> musicos = List.of(javi, rafa, denice, fran);

//		String idConcierto, String nombreConcierto, String fechaConcierto, List<Musico> musicos

		Concierto concierto = new Concierto(numRandom, "test1", "21-01-2024", musicos);
		InsertManager.insertViaje(concierto);

		CalculadorPagos calculador = new CalculadorPagos(PRECIO_GASOLINA);
		calculador.ajustarPagosParaBolo(concierto);
	}

}
