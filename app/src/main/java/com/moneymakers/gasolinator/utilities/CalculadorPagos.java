package com.moneymakers.gasolinator.utilities;

import android.widget.ScrollView;
import android.widget.TextView;

import com.moneymakers.gasolinator.conciertos.Concierto;
import com.moneymakers.gasolinator.eventos.Evento;
import com.moneymakers.gasolinator.musicos.Musico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String ajustarPagosParaBolo(Concierto concierto) {
        StringBuilder logBuilder = new StringBuilder();
        List<Musico> musicos = concierto.getMusicos();
        double kmTotales = 0;
        double montoTotalConductores = 0;
        double diferencia = 0;

        // Calculamos la suma total de kilómetros y el monto total adelantado por los conductores
        for (Musico musico : musicos) {
            kmTotales += musico.getKmTotales();
            if (musico.getEsConductor()) {
                montoTotalConductores += musico.getMontoPagado();
            }
        }

        BigDecimal costoTotal = calcularCuotaTotal(kmTotales);
        BigDecimal cuotaPorMusico = costoTotal.divide(new BigDecimal(musicos.size()), RoundingMode.HALF_UP);

        // Si el monto total adelantado por los conductores es menor que el costo total, se ajusta la cuota por músico.
        if (montoTotalConductores < costoTotal.doubleValue()) {
            diferencia = (costoTotal.doubleValue() - montoTotalConductores) / musicos.size();
            cuotaPorMusico = cuotaPorMusico.subtract(redondear(diferencia)).negate();
        }

        logBuilder.append("**********************************\n");
        logBuilder.append("Costo total de la gasolina: ").append(costoTotal).append(" €\n");
        logBuilder.append("Cuota por músico: ").append(cuotaPorMusico).append(" €\n");

        // Inicializar el saldo de los músicos
        for (Musico musico : musicos) {
            musico.setMontoPagado(redondear(musico.getMontoPagado() + cuotaPorMusico.doubleValue()).doubleValue());
        }

        // Listas de conductores y no conductores
        List<Musico> conductores = new ArrayList<>();
        List<Musico> noConductores = new ArrayList<>();
        logBuilder.append("**********************************\n");
        for (Musico musico : musicos) {
            if (musico.getEsConductor()) {
                conductores.add(musico);
            } else {
                noConductores.add(musico);
            }
        }

        logBuilder.append("**********************************\n");

        // Crear un mapa para almacenar las transacciones
        Map<String, String> transacciones = new HashMap<>();

        // Distribuir los pagos de los no conductores a los conductores con saldo negativo
        for (Musico noConductor : noConductores) {
            BigDecimal montoRestante = cuotaPorMusico;
            for (Musico conductor : conductores) {
                BigDecimal saldoConductor = redondear(-conductor.getMontoPagado());
                if (saldoConductor.compareTo(BigDecimal.ZERO) > 0 && montoRestante.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal montoARecuperar = saldoConductor.min(montoRestante);
                    if (montoARecuperar.compareTo(BigDecimal.ZERO) > 0) {
                        String transaccion = noConductor.getNombreMusico() + " debe pagar a "
                                + conductor.getNombreMusico() + ": " + montoARecuperar + " €";
                        logBuilder.append(transaccion).append("\n");
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
                            String transaccion = conductorConPositivo.getNombreMusico() + " debe pagar a "
                                    + conductorConNegativo.getNombreMusico() + ": " + montoARecuperar + " €";
                            logBuilder.append(transaccion).append("\n");
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
            if (saldoFinal.compareTo(BigDecimal.ZERO) > 0.1) {
                logBuilder.append("Error: El saldo final de ").append(musico.getNombreMusico()).append(" no es cero, saldo: ")
                        .append(saldoFinal).append(" €\n");
            }

        }

        logBuilder.append("**********************************\n");
        // Mostrar el saldo final de cada músico
        for (Musico musico : musicos) {
            if(redondear(musico.getMontoPagado()).compareTo(BigDecimal.ZERO) > 0.1){
                logBuilder.append(musico.getNombreMusico()).append(" saldo final: ")
                        .append(redondear(musico.getMontoPagado()).doubleValue()).append(" €\n");
            }else {
                logBuilder.append(musico.getNombreMusico()).append(" saldo final: ")
                        .append(0).append(" €\n");
            }

        }
        logBuilder.append("**********************************\n");

        // Crear un objeto Evento para cada músico
        for (Musico musico : musicos) {
            Evento evento = new Evento("", // ID evento
                    musico.getIdConcierto(), // Identificador del concierto
                    musico.getCodigoMusico(), // Nombre del músico
                    musico.getEsConductor(), // Si es conductor o no
                    musico.getKmTotales(),
                    cuotaPorMusico.doubleValue(), // Cuota que debe pagar el músico
                    musico.getMontoPagado(), // Total adelantado por el músico
                    "" // Resultado de pago (se llenará más adelante)
            );

            if (musico.getEsConductor()) {
                double gastoGasolinaAdelantado = (musico.getKmTotales()) * PRECIO_GASOLINA;
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
        }

        return logBuilder.toString();
    }

    // Método para agregar una transacción a la cadena existente en el mapa
    private static void agregarTransaccion(Map<String, String> transacciones, String musico, String transaccion) {
        transacciones.merge(musico, transaccion, (existente, nuevo) -> existente + "<br>" + nuevo);
    }

    public static void calcularConcierto(Concierto concierto, TextView logTextView) {
        CalculadorPagos calculador = new CalculadorPagos(PRECIO_GASOLINA);
        calculador.ajustarPagosParaBolo(concierto);
    }

    public static String pagoFinal(List<Musico> musicos,String nomConcierto) {
        String numRandom = musicos.get(0).getIdConcierto();
        Concierto concierto = new Concierto(numRandom, nomConcierto, musicos);
        // Assuming you have a TextView logTextView in your activity or fragment
        CalculadorPagos calculador = new CalculadorPagos(PRECIO_GASOLINA);
        return calculador.ajustarPagosParaBolo(concierto);
    }
}