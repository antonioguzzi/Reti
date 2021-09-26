package assignment_04;

public class Tutor {
    protected Laboratorio lab;
    private int nProfWaiting;
    private int nTesistiWaiting;
    /**
     * @param l laboratorio la cui gestione si vuole assegnare al tutor
     */
    public Tutor(Laboratorio l){
        this.lab = l;
        this.nProfWaiting = 0;
        this.nTesistiWaiting = 0;
    }

    /**
     * effettua una richiesta di accesso al laboratorio da parte del professore p
     * @param p professore che richiede il laboratorio
     */
    public void professoreRichiedeLab(Professor p){
    	synchronized(lab)
		{
    		this.nProfWaiting++;
    		try {
    			while (!lab.isFree()) {
    				System.out.printf("Professore %d: in attesa del laboratorio\n", p.getMatricola());
    				lab.wait();
    			}
    			this.nProfWaiting--;
    			lab.occupyAll();
    		}
    		catch (InterruptedException e) {}
		}
    }

    /**
     * avvisa il tutore che il professore p ha rilasciato il laboratorio
     * @param p professore che rilascia il laboratorio
     */
    public void professoreRilasciaLab(Professor p){
    	synchronized(lab) // rilascia tutti i computer del laboratorio
		{
    		System.out.printf("Professore %d: lascio il laboratorio\n", p.getMatricola());
    		lab.releaseAll();
    		lab.notifyAll();
		}
    }

    /**
     * effettua una richiesta di accesso al computer id_pc del laboratorio da parte del tesista t
     * @param t tesista
     * @param id_pc id del pc a cui il tesista vuole lavorare
     */
    public void tesistaRichiedeComputer(Tesista t, int id_pc){
    	synchronized(lab)
		{
    		nTesistiWaiting++;
    		try {
    			while (nProfWaiting > 0 || !lab.isAvailable(id_pc)) {
    				System.out.printf("Tesista %d: in attesa del computer %d\n", t.getMatricola(), id_pc);
    				lab.wait();
    			}
    			nTesistiWaiting--;
    			lab.occupyComputer(id_pc);
    		}
    		catch (InterruptedException e) {}
		}
    }

    /**
     * avvisa il tutore che il tesista t ha terminato il lavoro al computer id_pc
     * @param t tesista
     * @param id_pc id del pc rilasciato dal tesista
     */
    public void tesistaRilasciaComputer(Tesista t, int id_pc){
    	synchronized(lab) // rilascia il computer id_pc
		{
    		System.out.printf("Tesista %d: rilascio il computer %d\n", t.getMatricola(), id_pc);
    		lab.releaseComputer(id_pc);
    		lab.notifyAll();
		}
    }


    /**
     * @return  l'id del primo computer disponibile (se esiste e non ci sono tesisti in attesa su quel computer)
     *          -1 se nessun computer è disponibile
     */
    public int getComputerLiberoPerStudente(){
        for(int i=0; i<lab.getNcomputers(); i++){
            if (lab.isAvailable(i)){
                return i;
            }
        }
        return -1;
    }

    /**
     * effettua una richiesta di accesso al primo computer disponibile da parte dello studente s
     * @param s studente
     * @return id del pc acquisito dallo studente s
     */
    public int studenteRichiedeComputer(Student s){
        int id_pc = -1;
        synchronized(lab)
		{
        	try {
        		while (nProfWaiting > 0 || nTesistiWaiting > 0 || this.getComputerLiberoPerStudente() == -1) {
        			System.out.printf("Studente %d: in attesa di un computer libero\n", s.getMatricola());
        			lab.wait();
        		}
        		id_pc = this.getComputerLiberoPerStudente();
        		lab.occupyComputer(id_pc);
        	}
        	catch (InterruptedException e) {}
		}
        return id_pc;
    }

    /**
     * avvisa il tutore che lo studente s ha terminato il lavoro al computer id_pc
     * @param s studente
     * @param id_pc id del pc rilasciato dallo studente
     */
    public void studenteRilasciaComputer(Student s, int id_pc){
    	synchronized(lab) // rilascia il computer id_pc
		{
    		System.out.printf("Studente %d: rilascio il computer %d\n", s.getMatricola(), id_pc);
    		lab.releaseComputer(id_pc);
    		lab.notifyAll();
		}
    }
}