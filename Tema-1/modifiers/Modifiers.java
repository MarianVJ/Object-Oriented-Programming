package modifiers;

public class Modifiers {

    private float rogue;
    private float knight;
    private float pyromancer;
    private float wizard;

    /*
     * Aceasta clasa retine RaceModifiers , si este folosita in clasa fiecarui
     * erou , pentru a retine modificatorii pentru fiecare dintre rasele
     * existente ( inclusiv a sa)
     */
    public Modifiers(final float rogue, final float knight,
            final float pyromancer, final float wizard) {
        this.rogue = rogue;
        this.knight = knight;
        this.pyromancer = pyromancer;
        this.wizard = wizard;
    }

    /*
     * Intoarce RaceModifierul pentru Rogue
     */
    public float getRogue() {
        return rogue;
    }

    /*
     * Seteaza RaceModifierul pentru Rogue
     */
    public void setRogue(final float rogue) {
        this.rogue = rogue;
    }

    /*
     * Intoarce RaceModifierul pentru Knight
     */
    public float getKnight() {
        return knight;
    }

    /*
     * Seteaza RaceModifierul pentru Knight
     */
    public void setKnight(final float knight) {
        this.knight = knight;
    }

    /*
     * Intoarce RaceModifierul pentru Pyromancer
     */
    public float getPyromancer() {
        return pyromancer;
    }

    /*
     * Seteaza RaceModifierul pentru Pyromancer
     */

    public void setpyromancer(final float pr) {
        this.pyromancer = pr;
    }

    /*
     * Intoarce RaceModifierul pentru Wizard
     */
    public float getWizard() {
        return wizard;
    }

    /*
     * Seteaza RaceModifierul pentru Wizard
     */
    public void setWizard(final float wizard) {
        this.wizard = wizard;
    }

}
