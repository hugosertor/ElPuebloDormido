//import java.util.ArrayList;
//import java.util.Scanner;
//import java.util.Random;
//
//public class ElPuebloDormido {
//    public static final int POBLACION_MINIMA = 5;
//    public static final int POBLACION_MAXIMA = 20;
//
//    private static ArrayList<Ciudadano> ciudadanos = new ArrayList<>();
//    private static Random aleatorio = new Random();
//
//    public static void main(String[] args) {
//        System.out.println("=== BIENVENIDO AL PUEBLO DORMIDO ===");
//
//        generarPoblacionAleatoria();
//        Ciudadano.poblacionesTotales(ciudadanos);
//
//        boolean continuar = true;
//        while (continuar) {
//            try {
//                continuar = mostrarMenu();
//                verificarPoblacion();
//            } catch (Exception e) {
//                System.out.println("Error: " + e.getMessage());
//            }
//        }
//        System.out.println("=== FIN DEL PROGRAMA ===");
//    }
//
//    public static boolean mostrarMenu() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("\n=== MENÚ ===");
//        System.out.println("1. Mostrar censo");
//        System.out.println("2. Pasar un año");
//        System.out.println("3. Salir del programa");
//        System.out.print("Seleccione una opción: ");
//
//        int opcion = scanner.nextInt();
//
//        switch (opcion) {
//            case 1:
//                Ciudadano.censar(ciudadanos);
//                break;
//            case 2:
//                pasarAnyo();
//                break;
//            case 3:
//                return false;
//            default:
//                System.out.println("Opción no válida");
//        }
//        return true;
//    }
//
//    public static void generarPoblacionAleatoria() {
//        int cantidad = aleatorio.nextInt(POBLACION_MAXIMA - POBLACION_MINIMA + 1) + POBLACION_MINIMA;
//        for (int i = 0; i < cantidad; i++) {
//            Ciudadano ciudadano = obtenerCiudadanoAleatorio();
//            if (ciudadano != null)
//                ciudadanos.add(ciudadano);
//        }
//        System.out.println("Se ha generado una población inicial de " + cantidad + " ciudadanos.");
//    }
//
//    public static Ciudadano obtenerCiudadanoAleatorio() {
//        int habitante = (int) (Math.random() * 2) + 1;
//        switch (habitante) {
//            case 1: return new Humano();
//            case 2: return new Lobo();
//            case 3: return new Vampiro();
//            default: return null;
//        }
//    }
//
//    public  static void pasarAnyo(){
//        if (ciudadanos.isEmpty()){
//            throw new IllegalStateException("No hay ciudadanos disponibles");
//        }else{
//            System.out.println("\\_______/HA PASADO UN AÑO EN EL PUEBLO\\_______/");
//            ArrayList<Ciudadano> ciudadanosCopia = new ArrayList<>(ciudadanos);
//
//            for (Ciudadano ciudadano : ciudadanosCopia){
//
//
//                //EMBEJECE
//                if (ciudadano instanceof ICicloVital){
//                    ((ICicloVital) ciudadano).envejecer(ciudadanos);
//                }
//            }
//        }
//    }
//
//    public static Ciudadano obtenerOponenteAleatorio()
//}

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class ElPuebloDormido {
    public static final int POBLACION_MINIMA = 5;
    public static final int POBLACION_MAXIMA = 50;

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
                Ciudadano.poblacionesTotales(ciudadanos);
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
        int tipo = aleatorio.nextInt(3) + 1; // 1: Humano, 2: Lobo, 3: Vampiro
        switch (tipo) {
            case 1: return new Humano();
            case 2: return new Lobo();
            case 3: return new Vampiro();
            default: return null;
        }
    }

    public static void pasarAnyo() {
        if (ciudadanos.isEmpty()) {
            throw new IllegalStateException("No hay ciudadanos disponibles");
        }

        System.out.println("\\_______/HA PASADO UN AÑO EN EL PUEBLO\\_______/");

        // 1. Realizar acciones (combates/reproducciones)
        ArrayList<Ciudadano> copiaCiudadanos = new ArrayList<>(ciudadanos);
        for (Ciudadano ciudadano : copiaCiudadanos) {
            if (ciudadanos.contains(ciudadano)) { // Verificar si sigue vivo
                try {
                    Ciudadano oponente = obtenerOponenteAleatorio(ciudadano);
                    realizarAccion(ciudadano, oponente);
                } catch (IllegalStateException e) {
                    // No hay oponentes disponibles
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
        copiaCiudadanos = new ArrayList<>(ciudadanos);
        for (Ciudadano ciudadano : copiaCiudadanos) {
            if (ciudadano instanceof ICicloVital) {
                ((ICicloVital) ciudadano).envejecer(ciudadanos);
            }
        }
    }

    public static Ciudadano obtenerOponenteAleatorio(Ciudadano actual) {
        if (ciudadanos.size() <= 1) {
            throw new IllegalStateException("No hay ningún oponente disponible.");
        }

        Ciudadano oponente;
        do {
            int indice = aleatorio.nextInt(ciudadanos.size());
            oponente = ciudadanos.get(indice);
        } while (oponente == actual);

        return oponente;
    }

    public static void realizarAccion(Ciudadano ciudadano1, Ciudadano ciudadano2) {
        if (ciudadano1 == null || ciudadano2 == null) {
            throw new IllegalArgumentException("Los oponentes no pueden ser null.");
        }

        //Reproducción
        if (ciudadano1.getClass().equals(ciudadano2.getClass())) {
            procrear(ciudadano1);
        }
        //Combate
        else {
            combatir(ciudadano1, ciudadano2);
        }
    }

    public static void combatir(Ciudadano oponente1, Ciudadano oponente2) {
        Ciudadano perdedor = oponente1.combate(oponente2);
        if (perdedor != null) {
            // Caso especial: Conversión humano -> vampiro
            if (perdedor instanceof Humano && (oponente1 instanceof Vampiro || oponente2 instanceof Vampiro)) {

                // Conservar nombre original
                String nombreOriginal = perdedor.getNombre();

                // Eliminar humano
                perdedor.morir(ciudadanos);

                // Crear nuevo vampiro con nombre relacionado
                ciudadanos.add(new Vampiro());

                System.out.println("¡" + nombreOriginal + " se ha convertido en vampiro!");
            }
            // Caso normal: eliminación del perdedor
            else {
                perdedor.morir(ciudadanos);
            }
        }
    }

    public static void procrear(Ciudadano ciudadano) {
        if (ciudadano instanceof ICicloVital) {
            ((ICicloVital) ciudadano).reproducir(ciudadanos);
        }
    }

    public static void verificarPoblacion() {
        int tipos = 0;
        boolean humanos = false, lobos = false, vampiros = false;

        for (Ciudadano c : ciudadanos) {
            if (c instanceof Humano) humanos = true;
            else if (c instanceof Lobo) lobos = true;
            else if (c instanceof Vampiro) vampiros = true;
        }

        if (humanos) tipos++;
        if (lobos) tipos++;
        if (vampiros) tipos++;

        if (tipos == 1) {
            System.out.println("Solo queda un tipo de ser en el pueblo");
            System.exit(0);
        }
    }
}