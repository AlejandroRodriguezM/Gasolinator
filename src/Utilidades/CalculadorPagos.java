package Utilidades;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import Musicos.Musico;

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

	public void ajustarPagosParaBolo(List<Musico> musicos) {
	    double kmTotales = 0;
	    double montoTotalConductores = 0;
	    double diferencia = 0;

	    // Calculamos la suma total de kilómetros y el monto total adelantado por los conductores
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

	    // Distribuir los pagos de los no conductores a los conductores con saldo negativo
	    for (Musico noConductor : noConductores) {
	        BigDecimal montoRestante = cuotaPorMusico;
	        for (Musico conductor : conductores) {
	            BigDecimal saldoConductor = redondear(-conductor.getMontoPagado());
	            if (saldoConductor.compareTo(BigDecimal.ZERO) > 0 && montoRestante.compareTo(BigDecimal.ZERO) > 0) {
	                BigDecimal montoARecuperar = saldoConductor.min(montoRestante);
	                if (montoARecuperar.compareTo(BigDecimal.ZERO) > 0) {
	                    System.out.println(noConductor.getNombreMusico() + " debe pagar a "
	                            + conductor.getNombreMusico() + ": " + montoARecuperar + " €");
	                    montoRestante = montoRestante.subtract(montoARecuperar);
	                    conductor.setMontoPagado(redondear(conductor.getMontoPagado() + montoARecuperar.doubleValue()).doubleValue());
	                    noConductor.setMontoPagado(redondear(noConductor.getMontoPagado() - montoARecuperar.doubleValue()).doubleValue());
	                }
	            }
	        }
	    }

	    // Si después de la distribución anterior, algún conductor tiene un saldo positivo,
	    // distribuirlo entre los otros conductores con saldo negativo.
	    for (Musico conductorConPositivo : conductores) {
	        if (conductorConPositivo.getMontoPagado() > 0) {
	            BigDecimal montoExcedente = redondear(conductorConPositivo.getMontoPagado());
	            for (Musico conductorConNegativo : conductores) {
	                BigDecimal saldoConductorNegativo = redondear(-conductorConNegativo.getMontoPagado());
	                if (saldoConductorNegativo.compareTo(BigDecimal.ZERO) > 0) {
	                    BigDecimal montoARecuperar = saldoConductorNegativo.min(montoExcedente);
	                    if (montoARecuperar.compareTo(BigDecimal.ZERO) > 0) {
	                        System.out.println(conductorConPositivo.getNombreMusico() + " debe pagar a "
	                                + conductorConNegativo.getNombreMusico() + ": " + montoARecuperar + " €");
	                        montoExcedente = montoExcedente.subtract(montoARecuperar);
	                        conductorConNegativo.setMontoPagado(redondear(conductorConNegativo.getMontoPagado() + montoARecuperar.doubleValue()).doubleValue());
	                        conductorConPositivo.setMontoPagado(redondear(conductorConPositivo.getMontoPagado() - montoARecuperar.doubleValue()).doubleValue());
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
	}


	public static void calcularConcierto(List<Musico> musicos) {
		CalculadorPagos calculador = new CalculadorPagos(PRECIO_GASOLINA);
		calculador.ajustarPagosParaBolo(musicos);
	}

	public static void main(String[] args) {
		Musico javi = new Musico("Javi", true, 52, 53, -21, "001");
		Musico rafa = new Musico("Rafa", true, 25, 28, -10.6, "002");
		Musico denice = new Musico("Denice", false, 0, 0, 0, "004");
		Musico fran = new Musico("Fran", false, 0, 0, 0, "005");

		List<Musico> musicos = List.of(javi, rafa, denice, fran);

		CalculadorPagos calculador = new CalculadorPagos(PRECIO_GASOLINA);
		calculador.ajustarPagosParaBolo(musicos);
	}
}
