package eroi;

import modifiers.Modifiers;

public class Wizard extends Hero {

    static final int INITIAL_LIFE = 400;
    static final int LIFE_PER_LEVEL = 30;
    static final Modifiers MODIFICATOR_DRAIN = new Modifiers(0.8f, 1.2f, 0.9f,
            1.05f);
    static final Modifiers MODIFICATOR_DEFLECT = new Modifiers(1.2f, 1.4f, 1.3f,
            1f);
    static final float INITIAL_PROCENT_DRAIN = 0.2f;
    static final float PROCENT_LEVEL_DRAIN = 0.05f;
    static final float INITIAL_PROCENT_DEFLECT = 0.35f;
    static final float PROCENT_LEVEL_DEFLECT = 0.2f;
    static final float PROCENT_MODIFICATOR_DESERT = 1.1f;
    static final float DRAIN_MULTIPLIER = 0.3f;

    private float procentDrain;
    private float procentDeflect;

    public Wizard(final int x, final int y) {
        super(x, y, Wizard.INITIAL_LIFE, Wizard.LIFE_PER_LEVEL);
        procentDrain = Wizard.INITIAL_PROCENT_DRAIN;
        procentDeflect = Wizard.INITIAL_PROCENT_DEFLECT;
    }

    /*
     * Aceasta functie updateaza damage-urile de fiecare data cand eroul face
     * level-up
     */
    @Override
    public void updateDamages() {
        procentDrain = procentDrain + Wizard.PROCENT_LEVEL_DRAIN;
        if (procentDeflect < 2 * Wizard.PROCENT_LEVEL_DEFLECT) {
            procentDeflect = procentDeflect + Wizard.PROCENT_LEVEL_DEFLECT;
        }
    }

    /*
     * Aceasta metoda intoarce damage-ul dat de abilitatea Drain in functie de
     * raceModifier si de terenul pe care se desfasoara lupta, si de nivelul
     * vietii adversarului
     */
    public float damageFromDrain(final float raceModifier, final char teren,
            final int curentHp, final int maxHp) {
        float damage = 0;
        float landModifier = 1;
        if (teren == 'D') {
            landModifier = Wizard.PROCENT_MODIFICATOR_DESERT;
        }
        damage = landModifier * raceModifier * this.procentDrain
                * Math.min(Wizard.DRAIN_MULTIPLIER * maxHp, curentHp);

        return damage;
    }

    /*
     * Aceasta metoda intoarce damage-ul dat de abilitatea Deflect in functie de
     * raceModifier si de terenul pe care se desfasoara lupta si de damage-ul pe
     * care Wizard-ul il primeste in runda curenta de la oponent
     */
    public float damageFromDeflect(final float raceModifier, final char teren,
            final int damageFrom) {
        float damage = 0;
        float landModifier = 1;
        if (teren == 'D') {
            landModifier = Wizard.PROCENT_MODIFICATOR_DESERT;
        }
        damage = landModifier * raceModifier * this.procentDeflect * damageFrom;

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
     * Functie componenta a DoubleDispatch prin care un erou Wizard
     * interactioneaza cu un erou Rogue,mai intai afla ce damage primeste de la
     * Rogue in runda curenta(necesar in calcularea damage-ului dat de
     * abilitatea deflect) , iar apoi returneaza Damage-ul primit in runda
     * curenta de pe urma abilitatilor Deflect si Drain
     */
    @Override
    public int damages(final Rogue rog, final char teren) {

        int damageFrom = Math.round(rog.damageFromBackstab(1, teren))
                + Math.round(rog.damageDotParalysis(1, teren));

        float damage = damageFromDeflect(Wizard.MODIFICATOR_DEFLECT.getRogue(),
                teren, damageFrom)
                + damageFromDrain(Wizard.MODIFICATOR_DRAIN.getRogue(), teren,
                        rog.getHp(), rog.getHpMax());

        return Math.round(damage);
    }

    /*
    *
    */
    @Override
    public int damages(final Knight kni, final char teren) {

        int damageFrom = Math.round(kni.damageFromExecute(1, teren, this))
                + Math.round(kni.damageFromSlam(1, teren));

        float damage = damageFromDeflect(Wizard.MODIFICATOR_DEFLECT.getKnight(),
                teren, damageFrom)
                + damageFromDrain(Wizard.MODIFICATOR_DRAIN.getKnight(), teren,
                        kni.getHp(), kni.getHpMax());

        return Math.round(damage);
    }

    /*
    *
    */
    @Override
    public int damages(final Pyromancer pyr, final char teren) {

        int damageFrom = Math.round(pyr.damageFromFireblast(1, teren))
                + Math.round(pyr.damageFromIgnite(1, teren));

        float damage = damageFromDeflect(
                Wizard.MODIFICATOR_DEFLECT.getPyromancer(), teren, damageFrom)
                + damageFromDrain(Wizard.MODIFICATOR_DRAIN.getPyromancer(),
                        teren, pyr.getHp(), pyr.getHpMax());

        return Math.round(damage);
    }

    /*
    *
    */
    @Override
    public int damages(final Wizard wiz, final char teren) {

        float damage = damageFromDrain(Wizard.MODIFICATOR_DRAIN.getWizard(),
                teren, wiz.getHp(), wiz.getHpMax());
        return Math.round(damage);
    }

}
