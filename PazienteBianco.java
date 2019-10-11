public class PazienteBianco extends Thread{
        private String name;
        private int visite;  // numero di visite che deve fare
        private String codice;
        private ProntoSoccorso Ps;

        public PazienteBianco(String nome, ProntoSoccorso newProntoSoccorso) {
            this.name = nome;
            this.codice = "bianco";
            this.Ps = newProntoSoccorso;
            Thread.currentThread().setPriority(MIN_PRIORITY);
            this.visite = (int) (Math.random() * 3) + 1;
        }

        public void run(){
           // while(visite>0){}
        }
}
