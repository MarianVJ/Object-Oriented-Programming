package eroi;

import modifiers.Modifiers;

public class Knight extends Hero {

    static final int INITIAL_LIFE = 900;
    static final int LIFE_PER_LEVEL = 80;
    static final Modifiers MODIFICATOR_EXECUTE = new Modifiers(1.15f, 1f, 1.1f,
            0.80f);
    static final Modifiers MODIFICATOR_SLAM = new Modifiers(0.8f, 1.2f, 0.9f,
            1.05f);
    static final int INITIAL_DAMAGE_EXECUTE = 200;
    static final int DAMAGE_PER_LEVEL_EXECUTE = 30;
    static final float INITIAL_HPLIMIT = 0.2f;
    static final int INITIAL_DAMAGE_SLAM = 100;
    static final int DAMAGE_PER_LEVEL_SLAM = 40;
    static final float HP_LIMIT_PER_LEVEL = 0.01f;
    static final float PROCENT_MODIFICATOR_LAND = 1.15f;

    private int damageExecute;
    private float hpLimit;
    private int damageSlam;

    public Knight(final int x, final int y) {
        super(x, y, Knight.INITIAL_LIFE, Knight.LIFE_PER_LEVEL);
        damageExecute = Knight.INITIAL_DAMAGE_EXECUTE;
        hpLimit = Knight.INITIAL_HPLIMIT;
        damageSlam = Knight.INITIAL_DAMAGE_SLAM;
    }

    /*
     * Aceasta functie updateaza damage-urile de fiecare data cand eroul face
     * level-up
     */
    @Override
    public void updateDamages() {
        damageExecute = damageExecute + Knight.DAMAGE_PER_LEVEL_EXECUTE;
        hpLimit = hpLimit + Knight.HP_LIMIT_PER_LEVEL;
        damageSlam = damageSlam + Knight.DAMAGE_PER_LEVEL_SLAM;

    }

    /*
     * Aceasta metoda intoarce damage-ul dat de abilitatea Execute in functie de
     * raceModifier si de terenul pe care se desfasoara lupta
     */
    public float damageFromExecute(final float raceModifier, final char teren,
            final Hero her) {
        float damage;
        float landModifier = 1;
        if (teren == 'L') {
            landModifier = Knight.PROCENT_MODIFICATOR_LAND;
        }
        // Verific daca viata oponentului este sub o anumita limita , in caz
        // afirmativ acesta moare asa ca intorc viata maxima pe care acesta o
        // poate avea
        if (her.getHp() < this.hpLimit * her.getHpMax()) {
            return her.getHpMax();
        } else {
            damage = this.damageExecute * raceModifier * landModifier;
            return damage;
        }
    }

    /*
     * Aceasta metoda intoarce damage-ul dat de abilitatea Slam in functie de
     * raceModifier si de terenul pe care se desfasoara lupta
     */
    public float damageFromSlam(final float raceModifier, final char teren) {
        float damage;
        float landModifier = 1;
        if (teren == 'L') {
            landModifier = Knight.PROCENT_MODIFICATOR_LAND;
        }
        damage = landModifier * raceModifier * this.damageSlam;
        return damage;
    }

    /*
     * Functia de "accept" pentru Double Dispatch
     */
    @Override
    public int damagedBy(final Hero hero, final char teren) {
        return hero.damages(this, teren);
    }

    /*
     * Functie componenta a DoubleDispatch prin care un erou Knight
     * interactioneaza cu un erou Rogue, mai intai ii seteaza acestuia numarul
     * de runde in care aceasta nu se va putea misca pe harta , iar apoi
     * returneaza Damage-ul primit in runda curenta de pe urma abilitatilor
     * Execute si Slam
     */
    @Override
    public int damages(final Rogue rog, final char teren) {

        float damage = damageFromSlam(Knight.MODIFICATOR_SLAM.getRogue(), teren)
                + damageFromExecute(Knight.MODIFICATOR_EXECUTE.getRogue(),
                        teren, rog);

        rog.setNrRundeStatice(1);
        return Math.round(damage);
    }

    /*
     *
     */
    @Override
    public int damages(final Knight kni, final char teren) {

        float damage = damageFromSlam(Knight.MODIFICATOR_SLAM.getKnight(),
                teren)
                + damageFromExecute(Knight.MODIFICATOR_EXECUTE.getKnight(),
                        teren, kni);

        kni.setNrRundeStatice(1);
        return Math.round(damage);
    }

    /*
     *
     */
    @Override
    public int damages(final Pyromancer pyr, final char teren) {

        float damage = damageFromSlam(Knight.MODIFICATOR_SLAM.getPyromancer(),
                teren)
                + damageFromExecute(Knight.MODIFICATOR_EXECUTE.getPyromancer(),
                        teren, pyr);

        pyr.setNrRundeStatice(1);
        return Math.round(damage);
    }

    /*
     *
     */
    @Override
    public int damages(final Wizard wiz, final char teren) {

        float damage = damageFromSlam(Knight.MODIFICATOR_SLAM.getWizard(),
                teren)
                + damageFromExecute(Knight.MODIFICATOR_EXECUTE.getWizard(),
                        teren, wiz);

        wiz.setNrRundeStatice(1);
        return Math.round(damage);
    }
}
