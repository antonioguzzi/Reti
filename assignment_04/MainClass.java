package assignment_04;

import java.util.Random;

public class MainClass {
	private static final int N_COMPUTERS_MARZOTTO = 20;
	public static void main(String[] args) {
        if(args.length != 3) {
            System.err.println("Usage: MainLaboratorio numStudenti numTesisti numProfessori \n"
                    + "\tnumstudenti \t numero di studenti che accedono al laboratorio \n "
                    + "\tnumTesisti \t numero di tisti che accedono al laboratorio\n"
                    + "\tnumProfessori \tnumero di professori che accedono al laboratorio\n"
                    + "\n\nExample: MainLaboratorio 10 5 2.");
            System.exit(1);
        }

        int ns = 0;  // numero di studenti
        int nt = 0;  // numero di tesisti
        int np = 0;  // numero di professori

        ns = Integer.parseInt(args[0]);
        nt = Integer.parseInt(args[1]);
        np = Integer.parseInt(args[2]);

        Laboratorio labMarzotto = new Laboratorio(N_COMPUTERS_MARZOTTO);
        Tutor t = new Tutor(labMarzotto);

        // crea e avvia gli studenti
        for(int i = 0; i < ns; i++) new Thread(new Student(i, t, 's')).start();

        // crea e avvia i tesisti
        for(int i = 0; i < nt; i++) {
            Random rand = new Random();
            int randPc = rand.nextInt(N_COMPUTERS_MARZOTTO); // pc a cui il tesista vuole accedere
            new Thread(new Tesista(i, t, randPc, 't')).start();
        }

        // crea e avvia i professori
        for(int i = 0; i < np; i++) new Thread(new Professor(i, t, 'p')).start();

    }
}
