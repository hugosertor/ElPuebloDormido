import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class ElPuebloDormido {
    public static final int POBLACION_MINIMA = 5;
    public static final int POBLACION_MAXIMA = 20;

    private static ArrayList<Ciudadano> ciudadanos = new ArrayList<>();
    private static Random aleatorio = new Random();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== BIENVENIDO AL PUEBLO DORMIDO ===");

        generarPoblacionAleatoria();
        Ciudadano.poblacionesTotales(ciudadanos);

        boolean continuar = true;
        while (continuar) {
            try {
                continuar = mostrarMenu(continuar);
                verificarPoblacion();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("=== FIN DEL PROGRAMA ===");
        scanner.close();
    }

    public static boolean mostrarMenu(boolean continuar) {
        System.out.println("                   |>>>                  |>>>");
        System.out.println("                   |                     |");
        System.out.println("               _  _|_  _             _  _|_  _");
        System.out.println("              | |_| |_| |           | |_| |_| |");
        System.out.println("              \\  .      /           \\  .      /");
        System.out.println("               |   .   |             |   .   |");
        System.out.println("           ____|_______|_____________|_______|____");
        System.out.println("          /_____________=== MENÚ ===______________\\");
        System.out.println("         |_________________________________________|");
        System.out.println("         ||                                       ||");
        System.out.println("         ||                                       ||");
        System.out.println("         ||             1. Mostrar censo          ||");
        System.out.println("         ||             2. Pasar un año           ||");
        System.out.println("         ||             3. Salir del programa     ||");
        System.out.println("         ||             Seleccione una opción:    ||");
        System.out.println("         ||_______________________________________||");
        System.out.println("         |_________________________________________|");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                Ciudadano.censar(ciudadanos);
                Ciudadano.poblacionesTotales(ciudadanos);
                break;
            case 2:
                pasarAnyo();
                break;
            case 3:
                continuar = false;
                break;
            default:
                System.out.println("Opción no válida");
        }

        return continuar;
    }

    public static void generarPoblacionAleatoria() {
        int cantidad = aleatorio.nextInt(POBLACION_MAXIMA - POBLACION_MINIMA + 1) + POBLACION_MINIMA;

        for (int i = 0; i < cantidad; i++) {
            ciudadanos.add(obtenerCiudadanoAleatorio());
        }

        System.out.println("Se ha generado una población inicial de " + cantidad + " ciudadanos.");
    }

    public static Ciudadano obtenerCiudadanoAleatorio() {
        int tipo = aleatorio.nextInt(3);

        switch (tipo) {
            case 0: return new Humano();
            case 1: return new Lobo();
            case 2: return new Vampiro();
            default: return new Humano();
        }
    }

    public static void pasarAnyo() {
        if (ciudadanos.isEmpty()) {
            throw new IllegalStateException("No hay ciudadanos disponibles.");
        }

        System.out.println("\n=== PASA UN AÑO EN EL PUEBLO DORMIDO ===");

        ArrayList<Ciudadano> copiaCiudadanos = new ArrayList<>(ciudadanos);

        for (Ciudadano ciudadano : copiaCiudadanos) {
            if (!ciudadanos.contains(ciudadano)) continue;

            try {
                Ciudadano oponente = obtenerOponenteAleatorio(ciudadanos.indexOf(ciudadano));
                realizarAccion(ciudadano, oponente);

                if (ciudadano instanceof CicloVital) {
                    ((CicloVital)ciudadano).envejecer(ciudadanos);
                }
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("=== FIN DEL AÑO ===");
        Ciudadano.poblacionesTotales(ciudadanos);
    }

    public static Ciudadano obtenerOponenteAleatorio(int actual) {
        if (ciudadanos.size() <= 1) {
            throw new IllegalStateException("No hay ningún oponente disponible.");
        }

        int oponenteIndex;
        do {
            oponenteIndex = aleatorio.nextInt(ciudadanos.size());
        } while (oponenteIndex == actual);

        return ciudadanos.get(oponenteIndex);
    }

    public static void realizarAccion(Ciudadano ciudadano1, Ciudadano ciudadano2) {
        if (ciudadano1 == null || ciudadano2 == null) {
            throw new IllegalArgumentException("Los oponentes no pueden ser null.");
        }

        if (ciudadano1.getClass() == ciudadano2.getClass()) {
            if (ciudadano1 instanceof CicloVital) {
                ((CicloVital)ciudadano1).reproducir(ciudadanos);
            }
        } else {
            Ciudadano perdedor = ciudadano1.combate(ciudadano2);
            if (perdedor != null) {
                perdedor.morir(ciudadanos);
            }
        }
    }

    public static void verificarPoblacion() {
        if (ciudadanos.isEmpty()) return;

        Class<? extends Ciudadano> primerTipo = ciudadanos.get(0).getClass();
        boolean soloUnTipo = true;

        for (Ciudadano c : ciudadanos) {
            if (c.getClass() != primerTipo) {
                soloUnTipo = false;
                break;
            }
        }

        if (soloUnTipo) {
            System.out.println("\n¡Solo queda un tipo de ser en el pueblo!");
            System.out.println("El pueblo está ahora dominado por los " + primerTipo.getSimpleName() + "s");
            System.exit(0);
        }
    }
}