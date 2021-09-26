package assignment_04;

public class Student extends Utente {

    /*
     * id del pc correntemente assegnato, -1 altrimenti
     */
    private int assignedPc;

    /*
     * @param m matricola
     * @param t tutor
     */
    public Student(int m, Tutor t, char tipo) {
        super(m, t, tipo);
        assignedPc = -1;
    }


    @Override
    void richiestaAccesso() {
        System.out.printf("Studente %d ha richiesto un computer\n", this.getMatricola());
        assignedPc = tutor.studenteRichiedeComputer(this);
        System.out.printf("Studente %d assegnato al pc %d\n", this.getMatricola(), assignedPc);
    }

    @Override
    void rilascio() {
        System.out.printf("Studente %d ha rilasciato il computer %d\n", this.getMatricola(), this.assignedPc);
        tutor.studenteRilasciaComputer(this, this.assignedPc);
        this.assignedPc = -1;
    }

}
