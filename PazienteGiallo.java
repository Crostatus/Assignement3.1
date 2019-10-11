import java.util.concurrent.TimeUnit;

public class PazienteGiallo extends Thread {
    private String name;
    private int visite;  // numero di visite che deve fare
    private int NumMedico;
    private String codice;
    private ProntoSoccorso Ps;

    public PazienteGiallo(String nome, ProntoSoccorso newProntoSoccorso) {
        this.name = nome;
        this.codice = "giallo";
        this.Ps = newProntoSoccorso;
        Thread.currentThread().setPriority(NORM_PRIORITY);
        this.NumMedico = (int) (Math.random() * 9);
        this.visite = (int) (Math.random() * 3) + 1;
    }

    public void run() {
        while (visite > 0) {
            Ps.locks[NumMedico].lock();
            try {
                try {
                    System.out.println("                                 "+
                            "Il paziente "+name+" è pronto,codice: "+codice);
                    if (Ps.MedicoDisponibile(NumMedico) == 0)
                        Ps.addGiallo(NumMedico);
                    while (Ps.MedicoDisponibile(NumMedico) == 0 ||
                            Ps.GialliInFila(NumMedico) != 0 || Ps.RossiInFila()!=0)
                        Ps.Empty.get(NumMedico).await();
                    Ps.setMedico(NumMedico, false);
                    Ps.subGiallo(NumMedico);
                    System.out.println("Il paziente " + name + " ha iniziato la visita "
                            + "(codice " + this.codice + ")");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                Ps.locks[NumMedico].unlock();
            }
            try {
                System.out.println("Il paziente " + name + " sta effettuando la visita ");
                int k = (int) (Math.random() * 500) + 1;
                sleep(k);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            Ps.locks[NumMedico].lock();
            try {
                System.out.println("Il paziente " + name + " ha terminato la visita "
                        + "(codice " + this.codice + ")");
                Ps.setMedico(NumMedico,true);
                Ps.Empty.get(NumMedico).signalAll();
            }finally {
                Ps.locks[NumMedico].unlock();
            }
            this.visite--;
            try {
                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}