import java.util.Scanner;

public class Main {
    public static void main(String []args){
        int b,g,r;
        Scanner scanner=new Scanner(System.in);
        ProntoSoccorso Ps= new ProntoSoccorso();

        /*System.out.println("Inserire n° pazienti bianchi:");
        b=scanner.nextInt();*/

        /*System.out.println("Inserire n° pazienti gialli:");
        g=scanner.nextInt();*/

        System.out.println("Inserire n° pazienti rossi:");
        r=scanner.nextInt();

        for(int i=0;i<r;i++){
            PazienteRosso nuovo= new PazienteRosso("Rosso-"+i,Ps);
            nuovo.start();
        }

        /*for(int i=0;i<g;i++){
            PazienteGiallo nuovo= new PazienteGiallo("Giallo-"+i,Ps);
            nuovo.start();
        }*/

    }
}
