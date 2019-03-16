
package eroi;

import modifiers.Modifiers;

public class Pyromancer extends Hero {

    static final int INITIAL_DAMAGE_FIREBLAST = 350;
    static final int INITIAL_LIFE = 500;
    static final int LIFE_PER_LEVEL = 50;
    static final int DAMAGE_PER_LEVEL_FIREBLAST = 50;
    static final int INITIAL_DAMAGE_IGNITE = 150;
    static final int INITIAL_OVERTIME_DAMAGE_IGNITE = 50;
    static final int DAMAGE_PER_LEVEL_IGNITE = 20;
    static final int DAMAGE_PER_LEVEL_OVERTIME_IGNITE = 30;
    static final Modifiers MODIFICATOR_FIREBLAST = new Modifiers(0.8f, 1.2f,
            0.9f, 1.05f);
    static final Modifiers MODIFICATOR_IGNITE = new Modifiers(0.8f, 1.2f, 0.9f,
            1.05f);
    static final float PROCENT_TEREN_VULCANO = 1.25f;

    private int fireblastDamage;
    private int baseDamageIgnite;
    private int overTimeDamageIgnite;

    public Pyromancer(final int x, final int y) {
        super(x, y, Pyromancer.INITIAL_LIFE, Pyromancer.LIFE_PER_LEVEL);
        this.fireblastDamage = Pyromancer.INITIAL_DAMAGE_FIREBLAST;
        this.baseDamageIgnite = Pyromancer.INITIAL_DAMAGE_IGNITE;
        this.overTimeDamageIgnite = Pyromancer.INITIAL_OVERTIME_DAMAGE_IGNITE;
    }

    /*
     * Aceasta functie updateaza damage-urile de fiecare data cand eroul face
     * level-up
     */
    @Override
    public void updateDamages() {
        fireblastDamage = fireblastDamage
                + Pyromancer.DAMAGE_PER_LEVEL_FIREBLAST;
        baseDamageIgnite = baseDamageIgnite
                + Pyromancer.DAMAGE_PER_LEVEL_IGNITE;
        overTimeDamageIgnite = overTimeDamageIgnite
                + Pyromancer.DAMAGE_PER_LEVEL_OVERTIME_IGNITE;
    }

    /*
     * Aceasta metoda intoarce damage-ul dat de abilitatea Fireblast in functie
     * de raceModifier si de terenul pe care se desfasoara lupta
     */
    public float damageFromFireblast(final float raceModifier,
            final char teren) {
        float damage = 0;
        float landModifier = 1;
        if (teren == 'V') {
            landModifier = Pyromancer.PROCENT_TEREN_VULCANO;
        }
        damage = raceModifier * landModifier * this.fireblastDamage;
        return damage;
    }

    /*
     * Aceasta metoda intoarce damage-ul dat de abilitatea Ignite in functie de
     * raceModifier si de terenul pe care se desfasoara lupta
     */
    public float damageFromIgnite(final float raceModifier, final char teren) {
        float damage = 0;
        float landModifier = 1;
        if (teren == 'V') {
            landModifier = Pyromancer.PROCENT_TEREN_VULCANO;
        }
        damage = raceModifier * landModifier * this.baseDamageIgnite;
        return damage;
    }

    /*
     * Aceasta metoda intoarce damage-ul overtime dat de abilitatea Ignite in
     * functie de raceModifier si de terenul pe care se desfasoara lupta
     */
    public float damageDotIgnite(final float raceModifier, final char teren) {
        float damage = 0;
        float landModifier = 1;
        if (teren == 'V') {
            landModifier = Pyromancer.PROCENT_TEREN_VULCANO;
        }
        damage = raceModifier * landModifier * this.overTimeDamageIgnite;
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
     * Functie componenta a DoubleDispatch prin care un erou Pyromancer
     * interactioneaza cu un erou Rogue, mai intai ii seteaza acestuia damage-ul
     * OvertTime de pe urma abilitatii Ignite iar apoi returneaza Damage-ul
     * primit in runda curenta de pe urma abilitatilor Ignite si Fireblast
     */
    @Override
    public int damages(final Rogue rog, final char teren) {

        rog.setOvertTimeParameters(2, Math.round(damageDotIgnite(
                Pyromancer.MODIFICATOR_IGNITE.getRogue(), teren)));

        float damage = damageFromIgnite(
                Pyromancer.MODIFICATOR_IGNITE.getRogue(), teren)
                + damageFromFireblast(
                        Pyromancer.MODIFICATOR_FIREBLAST.getRogue(), teren);

        return Math.round(damage);
    }

    /*
     *
     */
    @Override
    public int damages(final Knight kni, final char teren) {

        kni.setOvertTimeParameters(2, Math.round(damageDotIgnite(
                Pyromancer.MODIFICATOR_IGNITE.getKnight(), teren)));

        float damage = damageFromIgnite(
                Pyromancer.MODIFICATOR_IGNITE.getKnight(), teren)
                + damageFromFireblast(
                        Pyromancer.MODIFICATOR_FIREBLAST.getKnight(), teren);

        return Math.round(damage);
    }

    /*
     *
     */
    @Override
    public int damages(final Pyromancer pyr, final char teren) {

        pyr.setOvertTimeParameters(2, Math.round(damageDotIgnite(
                Pyromancer.MODIFICATOR_IGNITE.getPyromancer(), teren)));

        float damage = damageFromIgnite(
                Pyromancer.MODIFICATOR_IGNITE.getPyromancer(), teren)
                + damageFromFireblast(
                        Pyromancer.MODIFICATOR_FIREBLAST.getPyromancer(),
                        teren);

        return Math.round(damage);
    }

    /*
     *
     */
    @Override
    public int damages(final Wizard wiz, final char teren) {

        wiz.setOvertTimeParameters(2, Math.round(damageDotIgnite(
                Pyromancer.MODIFICATOR_IGNITE.getWizard(), teren)));

        float damage = damageFromIgnite(
                Pyromancer.MODIFICATOR_IGNITE.getWizard(), teren)
                + damageFromFireblast(
                        Pyromancer.MODIFICATOR_FIREBLAST.getWizard(), teren);

        return Math.round(damage);
    }

}
