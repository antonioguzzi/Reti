package assignment_04;

public class Tesista extends Utente {
    /**
     * id del computer su cui il tesista deve lavorare
     */
    private final int id_pc;

    /**
     *
     * @param m matricola
     * @param t tutor
     * @param id_pc id del computer su cui il tesista deve lavorare
     */
    public Tesista(int m, Tutor t, int id_pc, char tipo) {
        super(m, t, tipo);
        this.id_pc = id_pc;
    }

    @Override
    void richiestaAccesso() {
        System.out.printf("Tesista %d ha richiesto il computer %d\n", this.getMatricola(), this.id_pc);
        tutor.tesistaRichiedeComputer(this, id_pc);
        System.out.printf("Tesista %d ha ottenuto il computer %d\n", this.getMatricola(), this.id_pc);
    }

    @Override
    void rilascio() {
        System.out.printf("Tesista %d ha rilasciato il computer %d\n", this.getMatricola(), this.id_pc);
        tutor.tesistaRilasciaComputer(this, id_pc);
    }
}
