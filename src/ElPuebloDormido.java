import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class ElPuebloDormido {
    public static final int POBLACION_MINIMA = 5;
    public static final int POBLACION_MAXIMA = 20;

    private static ArrayList<Ciudadano> ciudadanos = new ArrayList<>();
    private static Random aleatorio = new Random();

    public static void main(String[] args) {
        System.out.println("=== BIENVENIDO AL PUEBLO DORMIDO ===");

        generarPoblacionAleatoria();
        Ciudadano.poblacionesTotales(ciudadanos);

        boolean continuar = true;
        while (continuar) {
            try {
                continuar = mostrarMenu();
                verificarPoblacion();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("=== FIN DEL PROGRAMA ===");
    }

    public static boolean mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== MENÚ ===");
        System.out.println("1. Mostrar censo");
        System.out.println("2. Pasar un año");
        System.out.println("3. Salir del programa");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                Ciudadano.censar(ciudadanos);
                break;
            case 2:
                pasarAnyo();
                break;
            case 3:
                return false;
            default:
                System.out.println("Opción no válida");
        }
        return true;
    }

    public static void generarPoblacionAleatoria() {
        int cantidad = aleatorio.nextInt(POBLACION_MAXIMA - POBLACION_MINIMA + 1) + POBLACION_MINIMA;
        for (int i = 0; i < cantidad; i++) {
            Ciudadano ciudadano = obtenerCiudadanoAleatorio();
            if (ciudadano != null)
                ciudadanos.add(ciudadano);
        }
        System.out.println("Se ha generado una población inicial de " + cantidad + " ciudadanos.");
    }

    public static Ciudadano obtenerCiudadanoAleatorio() {

        EVulnerable[] vulnerable = EVulnerable.values();
        switch (vulnerable[0]) {
            case HUMANO: return new Humano();
            case LOBO: return new Lobo();
            case VAMPIRO: return new Vampiro();
            default: return null;
        }
    }

}