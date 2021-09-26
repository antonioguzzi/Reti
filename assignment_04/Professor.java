package assignment_04;

public class Professor extends Utente {
   /**
    * @param m identificativo del professore
    * @param t tutor
    */
   public Professor(int m, Tutor t, char tipo) {
       super(m, t, tipo);
   }

   @Override
   void richiestaAccesso() {
       System.out.printf("Professore %d ha richiesto il laboratorio\n", this.getMatricola());
       tutor.professoreRichiedeLab(this);
       System.out.printf("Professore %d ha ottenuto il laboratorio\n", this.getMatricola());
   }

   @Override
   void rilascio() {
       System.out.printf("Professore %d rilascia il laboratorio\n", this.getMatricola());
       tutor.professoreRilasciaLab(this);
   }
}   

