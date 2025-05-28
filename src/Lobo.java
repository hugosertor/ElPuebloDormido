import java.util.ArrayList;
import java.util.Random;

public final class Lobo extends Ciudadano implements ICicloVital {
    private static int totalLobos = 0;
    private static int ultimoLobo = 0;
    private static final Random ALEATORIO = new Random();

    private final EVulnerable EVULNERABLE = EVulnerable.HUMANO;
    private int vida;

    public Lobo() {
        super("LOBO" + ++ultimoLobo);
        this.vida = ALEATORIO.nextInt(VITALIDAD_MAXIMA) + 1;
        totalLobos++;
    }

    public static int getPoblacion() {
        return totalLobos;
    }
    public static void setPoblacion(int numero) {
        totalLobos = numero;
    }
    public EVulnerable getVulnerable() {
        return EVULNERABLE;
    }


    @Override
    public Ciudadano combate(Ciudadano oponente) {
        if (oponente instanceof Vampiro) {
            System.out.println(this.getNombre() + " derrota al vampiro " + oponente.getNombre());
            return oponente;

        }else if (oponente instanceof Humano){
            System.out.println(this.getNombre() + " es asesinado por " + oponente.getNombre());
            totalLobos --;
            return this;
        }

        return null;
    }

    @Override
    public void reproducir(ArrayList<Ciudadano> ciudadanos) {
        if (ALEATORIO.nextDouble() < 0.5) {
            int cachorros = ALEATORIO.nextInt(NATALIDAD_MAXIMA * 2) + 1;
            for (int i = 0; i < cachorros; i++) {

                if (Ciudadano.getPoblacion() < ElPuebloDormido.POBLACION_MAXIMA) {
                    ultimoLobo ++;
                    Lobo cachorro = new Lobo();
                    ciudadanos.add(cachorro);
                    System.out.println(this.getNombre() + " ha tenido un cachorro: " + cachorro.getNombre());
//                    poblacion ++;
                    setPoblacion(+1);
                    totalLobos ++;

                } else {
                    System.out.println("No se puede reproducir, población máxima alcanzada");
                }
            }
        }
    }

    @Override
    public void envejecer(ArrayList<Ciudadano> ciudadanos) {
        vida -= 2;
        System.out.println(this.getNombre() + " envejece. Vida restante: " + vida);
        if (vida <= 0) {
            morir(ciudadanos);
        }
    }

    @Override
    public void morir(ArrayList<Ciudadano> ciudadanos) {
        System.out.println(this.getNombre() + " ha muerto.");
        ciudadanos.remove(this);
        totalLobos--;
//        poblacion--;
        setPoblacion(-1);
    }


    @Override
    public String toString() {
        return super.toString() + " (Lobo) - Vida: " + vida + " - Vulnerable a: " + EVULNERABLE;
    }
}