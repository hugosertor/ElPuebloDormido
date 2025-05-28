import java.util.ArrayList;

public abstract class Ciudadano implements IBatalla {
    private static int poblacion = 0;
    private String nombre;

    public Ciudadano(String nombre) {
        this.nombre = nombre;
        poblacion++;
    }

    public static int getPoblacion() {
        return poblacion;
    }

    public static void setPoblacion(int numero) {
        poblacion += numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public static void censar(ArrayList<Ciudadano> ciudadanos) {
        System.out.println("___Censo del pueblo___");
        for (Ciudadano c : ciudadanos) {
            System.out.println(c);
        }
        System.out.println("Población total: " + poblacion);
    }

    public static void poblacionesTotales(ArrayList<Ciudadano> ciudadanos) {
        int humanos = 0, lobos = 0, vampiros = 0;

        for (Ciudadano c : ciudadanos) {
            if (c instanceof Humano) {
                humanos++;
            } else if (c instanceof Lobo) {
                lobos++;
            }else if (c instanceof Vampiro){
                vampiros++;
            }
        }

            System.out.println("/\\/\\/\\Poblaciones/\\/\\/\\");
            System.out.println("Humanos: " + humanos);
            System.out.println("Lobos: " + lobos);
            System.out.println("Vampiros: " + vampiros);
        }

        public abstract void morir(ArrayList<Ciudadano> ciudadanos);

    @Override
    public String toString() {
        return "Nombre del ciudadano: " + nombre;
    }
}