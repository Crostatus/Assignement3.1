import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ProntoSoccorso {
    private boolean[] medici;  //True se il medico i è libero (non sta lavorando), false altrimenti
    public ReentrantLock[] locks; //array di lock, una per ogni medico
    public List<Condition> Empty;    //array di conditions, una per ogni medico
    private int[] PazientiGialli;
    private int RedVisit = 0;

    // Inizializzo la classe con 10 medici che non stanno lavorando (false)
    public ProntoSoccorso(){
        PazientiGialli=new int[10];
        locks=new ReentrantLock[10];
        medici=new boolean[10];
        Empty= new ArrayList<Condition>();
        for (int i=0;i<10;i++){
            PazientiGialli[i]=0;
            medici[i]=true;
            locks[i]=new ReentrantLock();
            Empty.add(i,locks[i].newCondition());
        }
    }

   /* public int VisitaBianco(){
        int index;
        while((index=MedicoDisponibile())==-1)
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        lock[index].lock();
        try {
            try {
                while (medici[index] == false || PazientiGialli[index]!=0 || RedVisit!=0)
                    Empty[index].await();
                medici[index]=false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            lock[index].unlock();
        }
        return index;
    }

    public void FineVisitaBianco(int index){
        medici[index]=true;
        Empty[index].signal();
    }

    public void VisitaGiallo(int NumMedico){
        lock[NumMedico].lock();
        try {
            try {
                // se il medico che mi serve è già in uso aumento la coda di gialli su quel medico
                if(medici[NumMedico]==false) PazientiGialli[NumMedico]++;
                // controllo se il medico sta lavorando e se il numero di pazienti in coda è !=0
                while (medici[NumMedico] == false || PazientiGialli[NumMedico]!=0)
                    Empty[NumMedico].await();
                medici[NumMedico]=false;
                PazientiGialli[NumMedico]--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            lock[NumMedico].unlock();
        }
    }

    public void FineVisitaGiallo(int NumMedico){
        medici[NumMedico]=true;
        Empty[NumMedico].signal();
    }

    public void VisitaRosso(){
        int i;
        for(i=0;i<10;i++)
            lock[i].lock();
        try {
            try {
                if(NobodyWork()==0) RedVisit++;
                // finchè almeno un lavoratore lavora oppure c'è qualcun'altro nella lista rossi aspetto
                while (NobodyWork() == 0 || RedVisit!=0 )
                    for(i=0;i<10;i++)
                        Empty[i].await();
                SetRosso(false);
                RedVisit--;
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }finally {
            for(i=0;i<10;i++)
                lock[i].unlock();
        }
    }

    public void FineVisitaRosso(){
        SetRosso(true);
        for(int i=0;i<10;i++)
            Empty[i].signal();
    }*/

    public void Lock(int i){
        locks[i].lock();
    }

    public void Unlock(int i){
        locks[i].unlock();
    }

    public void setMedico(int i,boolean state){
        medici[i]=state;
    }

    public void setMedici(boolean state){
        for(int i=0;i<10;i++)
            medici[i]=state;
    }

    // restituisce 1 se nessuno lavora, 0 altrimenti
    public int nobodyWork(){
        int ok=1;
        for(int i=0;i<10;i++)
            if(medici[i]==false) ok=0;
        return ok;
    }

    // restituisce il numero di pazienti gialli in fila
    public int GialliInFila(int i){
        return PazientiGialli[i];
    }

    // restituisce il numero di pazienti rossi in coda
    public  int RossiInFila(){
        return RedVisit;
    }

    public void subGiallo(int i){
        PazientiGialli[i]--;
    }

    public void addGiallo(int i){
        PazientiGialli[i]++;
    }

    public void subRosso(){
        RedVisit--;
    }

    public void addRosso(){
        RedVisit++;
    }

    // restituisce 1 se il medico i è disponibile, 0 altrimenti
    public int MedicoDisponibile(int i){
        if(medici[i]==true) return 1;
        return 0;
    }

    // restituisce 1 solo se tutti i medici sono disponibili, 0 altrimenti
    public int MediciDisponibili(){
        for(int i=0;i<10;i++)
            if(medici[i]==false) return 0;
        return 1;
    }
}
