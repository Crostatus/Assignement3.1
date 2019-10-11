import java.util.concurrent.TimeUnit;

public class PazienteRosso extends  Thread{
    private String name;
    private int visite;  // numero di visite che deve fare
    private String codice;
    private ProntoSoccorso Ps;

    public PazienteRosso(String nome, ProntoSoccorso newProntoSoccorso) {
        this.name = nome;
        this.codice = "rosso";
        this.Ps = newProntoSoccorso;
        Thread.currentThread().setPriority(MAX_PRIORITY);
        this.visite = (int) (Math.random() * 2) + 1;
    }

    public void run(){
        int i;
        while (visite>0){
            for(i=0;i<10;i++)
                Ps.locks[i].lock();
            try {
                try {
                    System.out.println("                                 "+
                            "Il paziente "+name+" è pronto,codice: "+codice);
                    // se qualcuno sta lavorando aumento il n° della coda Rossi
                    if(Ps.nobodyWork()==0) Ps.addRosso();
                    while(Ps.nobodyWork()==0 || Ps.RossiInFila()!=0)
                        for(i=0;i<10;i++)
                            Ps.Empty.get(i).await();
                    Ps.setMedici(false);
                    Ps.subRosso();
                    System.out.println("Il paziente " + name + " ha iniziato la visita "
                            + "(codice " + this.codice + ")");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }finally {
                for(i=0;i<10;i++)
                    Ps.locks[i].unlock();
            }

            try {
                System.out.println("Il paziente " + name + " sta effettuando la visita ");
                int k = (int)(Math.random() * 500) + 1;
                sleep(k);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(i=0;i<10;i++)
                Ps.locks[i].lock();
            try{
                System.out.println("Il paziente " + name + " ha terminato la visita "
                        + "(codice " + this.codice + ")");
                Ps.setMedici(true);
                for(i=0;i<10;i++)
                    Ps.Empty.get(i).signalAll();
            }finally {
                for(i=0;i<10;i++)
                    Ps.locks[i].unlock();
            }

            this.visite--;
            try{
                TimeUnit.MILLISECONDS.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
