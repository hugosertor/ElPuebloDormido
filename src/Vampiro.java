import java.util.ArrayList;

public final class Vampiro extends Ciudadano {
    private static int totalVampiros = 0;
    private static int ultimoVampiro = 0;

    private final EVulnerable EVULNERABLE = EVulnerable.LOBO;

    public Vampiro() {
        super("VAMPIRO" + ++ultimoVampiro);
        totalVampiros++;
    }

    public static int getPoblacion() {
        return totalVampiros;
    }

    public EVulnerable getVulnerable() {
        return EVULNERABLE;
    }


    public static void setPoblacion(int numero) {
        totalVampiros = numero;
    }

    @Override
    public Ciudadano combate(Ciudadano oponente) {
        if (oponente instanceof Humano) {
            System.out.println(this.getNombre() + " derrota al " + oponente.getNombre());
            return this;

        }else if (oponente instanceof Lobo){
            System.out.println(this.getNombre() + " es asesinado por " + oponente.getNombre());
            return oponente;
        }

        return null;
    }


    @Override
    public void morir(ArrayList<Ciudadano> ciudadanos) {
        System.out.println(this.getNombre() + " ha muerto.");
        ciudadanos.remove(this);
        totalVampiros--;
        setPoblacion(-1);
//        poblacion--;
    }


    @Override
    public String toString() {
        return super.toString() + " (Vampiro) - Vulnerable a: " + EVULNERABLE;
    }
}