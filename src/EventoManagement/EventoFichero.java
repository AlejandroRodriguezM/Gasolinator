package EventoManagement;

public class EventoFichero {

    public static Evento datosEventoFichero(String lineText) {
        // Verificar si la línea está vacía
        if (lineText == null || lineText.trim().isEmpty()) {
            return null;
        }

        String[] data = lineText.split(";");

        // Verificar si hay suficientes elementos en el array 'data'
        if (data.length >= 9) { // Ajusta este valor según la cantidad de campos esperados
            String identificadorConcierto = data[0];
            String codigoMusico = data[1];
            boolean esConductor = Boolean.parseBoolean(data[2]);
            double kmIda = parseDouble(data[3]);
            double kmVuelta = parseDouble(data[4]);
            double costoPagar = parseDouble(data[5]);
            double totalAdelantado = parseDouble(data[6]);
            String fechaConcierto = data[7];
            String resultadoPagar = data[8];

            return new Evento("",identificadorConcierto, codigoMusico, esConductor, kmIda, kmVuelta, costoPagar, totalAdelantado, fechaConcierto, resultadoPagar);
        } else {
            return null;
        }
    }

    private static double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static String limpiarCosto(String costoStr) {
        // Verificar si el costo es nulo o está vacío
        if (costoStr == null || costoStr.isEmpty()) {
            return "0";
        }

        // Eliminar espacios en blanco al inicio y al final
        costoStr = costoStr.trim();

        // Paso 1: Eliminar símbolos repetidos y dejar solo uno
        costoStr = costoStr.replaceAll("([€$])\\1+", "$1");

        // Paso 2: Si hay varios símbolos, mantener solo uno y eliminar el resto
        costoStr = costoStr.replaceAll("([€$])(.*)([€$])", "$1$2");

        // Extraer el símbolo monetario, si existe
        String symbol = "";
        if (costoStr.startsWith("€") || costoStr.startsWith("$")) {
            symbol = costoStr.substring(0, 1);
            costoStr = costoStr.substring(1);
        }

        // Eliminar caracteres no numéricos excepto el primer punto decimal
        costoStr = costoStr.replaceAll("[^\\d.]", "");
        int dotIndex = costoStr.indexOf('.');
        if (dotIndex != -1) {
            costoStr = costoStr.substring(0, dotIndex + 1) + costoStr.substring(dotIndex + 1).replace(".", "");
        }

        // Verificar si el costo contiene solo un punto decimal y ningún otro número
        if (costoStr.equals(".") || costoStr.equals(".0")) {
            return "0";
        }

        // Si el costo después de limpiar es vacío, retornar "0"
        if (costoStr.isEmpty()) {
            return "0";
        }

        // Retornar el costo limpiado con el símbolo monetario
        return symbol + costoStr;
    }
}
