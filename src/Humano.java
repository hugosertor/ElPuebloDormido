import java.util.ArrayList;
import java.util.Random;

public final class Humano extends Ciudadano implements ICicloVital {
    private static int totalHumanos = 0;
    private static int ultimoHumano = 0;
    private static final Random ALEATORIO = new Random();

    private static final EVulnerable EVULNERABLE = EVulnerable.VAMPIRO;
    private int vida;

    public Humano() {
        super("HUMANO" + ++ultimoHumano);
        this.vida = ALEATORIO.nextInt(VITALIDAD_MAXIMA) +1;
        totalHumanos++;
    }

    public static int getPoblacion() {
        return totalHumanos;
    }
    public static void setPoblacion(int numero){
        totalHumanos = numero;
    }
    public EVulnerable getVulnerable() {
        return EVULNERABLE;
    }


    @Override
    public Ciudadano combate(Ciudadano oponente) {
        if (oponente instanceof Lobo) {
            System.out.println(this.getNombre() + " derrota al lobo " + oponente.getNombre());
            oponente.morir(new ArrayList<>());
            return this;

        }else if (oponente instanceof Vampiro){
            System.out.println(this.getNombre() + " es convertido en vampiro por " + oponente.getNombre());
            this.morir(new ArrayList<>());
            totalHumanos --;

            return oponente;
        }

        return null;
    }

    @Override
    public void reproducir(ArrayList<Ciudadano> ciudadanos) {
        if (ALEATORIO.nextDouble() < 0.5) {
            int bebes = ALEATORIO.nextInt(NATALIDAD_MAXIMA * 2) + 1;
            for (int i = 0; i < bebes; i++) {

                if (Ciudadano.getPoblacion() < ElPuebloDormido.POBLACION_MAXIMA) {
                    Humano bebe = new Humano();
                    ciudadanos.add(bebe);
                    System.out.println(this.getNombre() + " ha tenido un cachorro: " + bebe.getNombre());
                    poblacion ++;
                    totalHumanos ++;
                    ultimoHumano ++;

                } else {
                    System.out.println("No se puede reproducir, población máxima alcanzada");
                }
            }
        }
    }

    @Override
    public void envejecer(ArrayList<Ciudadano> ciudadanos) {
        vida--;
        System.out.println(this.getNombre() + " envejece. Vida restante: " + vida);
        if (vida <= 0) {
            morir(ciudadanos);
        }
    }

    @Override
    public void morir(ArrayList<Ciudadano> ciudadanos) {
        ciudadanos.remove(this);
        poblacion--;
        totalHumanos--;
    }


    @Override
    public String toString() {
        return super.toString() + " | Vida: " + vida + " | Vulnerable a: " + EVULNERABLE;
    }
}