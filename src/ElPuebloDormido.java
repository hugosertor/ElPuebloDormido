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
        System.out.println("prubaaaa");

        // Bucle principal del programa
        boolean continuar = true;
        while (continuar) {
            try {
                continuar = mostrarMenu(continuar);
                verificarPoblacion();
            } catch (Exception e) {

                /// //////////////////////
                /// /AQUI HAY UN ERROR///
                /// ////////////////////
                System.out.println("Error   aaa: " + e.getMessage());
            }
        }

        System.out.println("=== FIN DEL PROGRAMA ===");
    }









    public static boolean mostrarMenu(boolean continuar) {
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





    // Genera una población inicial aleatoria
    public static void generarPoblacionAleatoria() {
        int cantidad = aleatorio.nextInt(POBLACION_MAXIMA - POBLACION_MINIMA + 1) + POBLACION_MINIMA;

        for (int i = 0; i < cantidad; i++) {
            ciudadanos.add(obtenerCiudadanoAleatorio());
        }

        System.out.println("Se ha generado una población inicial de " + cantidad + " ciudadanos.");
    }





    // Crea un ciudadano aleatorio (Humano, Lobo o Vampiro)
    public static Ciudadano obtenerCiudadanoAleatorio() {
        int tipo = (int) (Math.random() * 3);

        switch (tipo) {
            case 0: return new Humano();
            case 1: return new Lobo();
            case 2: return new Vampiro();
            default: return new Humano();
        }
    }





    // Simula el paso de un año en el pueblo
    public static void pasarAnyo() {
        if (ciudadanos.isEmpty()) {
            throw new IllegalStateException("No hay ciudadanos disponibles.");
        }

        System.out.println("\n=== PASA UN AÑO EN EL PUEBLO DORMIDO ===");

        for (Ciudadano ciudadano : ciudadanos){

        }


        for (Ciudadano ciudadano : ciudadanos) {
            // Si el ciudadano ya murió, lo saltamos
            if (!ciudadanos.contains(ciudadano)) continue;

            try {
                // Seleccionar un oponente aleatorio
                Ciudadano oponente = obtenerOponenteAleatorio(ciudadanos.indexOf(ciudadano));
                // Realizar acción (combatir o reproducirse)
                realizarAccion(ciudadano, oponente);

                // Envejecer al ciudadano si es un ser vivo
                if (ciudadano instanceof CicloVital) {
                    ((CicloVital)ciudadano).envejecer(ciudadanos);
                }
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("=== FIN DEL AÑO ===");
        // Mostrar estadísticas actualizadas
        Ciudadano.poblacionesTotales(ciudadanos);
    }





    // Obtiene un oponente aleatorio diferente al ciudadano actual
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





    // Decide qué acción realizar entre dos ciudadanos
    public static void realizarAccion(Ciudadano ciudadano1, Ciudadano ciudadano2) {
        if (ciudadano1 == null || ciudadano2 == null) {
            throw new IllegalArgumentException("Los oponentes no pueden ser null.");
        }

        // Si son del mismo tipo, se reproducen (si pueden)
        if (ciudadano1.getClass() == ciudadano2.getClass()) {
            if (ciudadano1 instanceof CicloVital) {
                ((CicloVital)ciudadano1).reproducir(ciudadanos);
            }
        } else {
            // Si son de diferente tipo, combaten
            Ciudadano perdedor = ciudadano1.combate(ciudadano2);
            if (perdedor != null) {
                perdedor.morir(ciudadanos);
            }
        }
    }





    // Verifica si solo queda un tipo de ciudadano en el pueblo
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