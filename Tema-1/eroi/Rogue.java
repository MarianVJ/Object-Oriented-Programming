package eroi;

import modifiers.Modifiers;

public class Rogue extends Hero {

    static final int INITIAL_LIFE = 600;
    static final int LIFE_PER_LEVEL = 40;
    static final Modifiers MODIFICATOR_BACKSTAB = new Modifiers(1.2f, 0.9f,
            1.25f, 1.25f);
    static final Modifiers MODIFICATOR_PARALYSIS = new Modifiers(0.9f, 0.8f,
            1.2f, 1.25f);
    static final int INITIAL_DAMAGE_BACKSTAB = 200;
    static final int DAMAGE_LEVEL_BACKSTAB = 20;
    static final int INITIAL_DAMAGE_PARALYSIS = 40;
    static final int DAMAGE_LEVEL_PARALYSIS = 10;
    static final int PARALYSIS_ROUNDS = 3;
    static final float PROCENT_TEREN_WOOD = 1.15f;
    static final float PROCENT_FOR_CRITICAL = 1.5f;

    private int damageBackstab;
    private int criticalHitCount;
    private int damageParalysis;

    public Rogue(final int x, final int y) {
        super(x, y, Rogue.INITIAL_LIFE, Rogue.LIFE_PER_LEVEL);
        damageBackstab = Rogue.INITIAL_DAMAGE_BACKSTAB;
        criticalHitCount = 0;
        damageParalysis = Rogue.INITIAL_DAMAGE_PARALYSIS;
    }

    /*
     * Aceasta functie updateaza damage-urile de fiecare data cand eroul face
     * level-up
     */
    @Override
    public void updateDamages() {
        damageBackstab = damageBackstab + Rogue.DAMAGE_LEVEL_BACKSTAB;
        damageParalysis = damageParalysis + Rogue.DAMAGE_LEVEL_PARALYSIS;
    }

    /*
     * Aceasta metoda intoarce damage-ul overtime dat de abilitatea Paralysis in
     * functie de raceModifier si de terenul pe care se desfasoara lupta
     */
    public float damageDotParalysis(final float raceModifier,
            final char teren) {
        float damage = 0;
        float landModifier = 1;
        if (teren == 'W') {
            landModifier = Rogue.PROCENT_TEREN_WOOD;
        }
        damage = damageParalysis * landModifier * raceModifier;
        return (damage);
    }

    /*
     * Aceasta metoda intoarce damage-ul dat de abilitatea Backstab in functie
     * de raceModifier si de terenul pe care se desfasoara lupta
     */
    public float damageFromBackstab(final float raceModifier,
            final char teren) {

        float damage = 0;
        float landModifier = 1;
        float criticalModifier = 1;
        if (teren == 'W') {
            landModifier = Rogue.PROCENT_TEREN_WOOD;
            if (criticalHitCount % (Rogue.PROCENT_FOR_CRITICAL * 2) == 0) {
                criticalModifier = Rogue.PROCENT_FOR_CRITICAL;
            }
        }
        damage = damageBackstab * landModifier * raceModifier
                * criticalModifier;
        return damage;
    }

    /*
     * Updateaza dupa fiecare runda in care eroul are o batalie criticalHitCount
     */
    @Override
    public void updateInternInformations() {
        this.criticalHitCount++;
    }

    /*
     * Aceasta metoda intoarce numarul de runde overtime de care oponentul
     * trebuie sa sufere in urma abilitatii Paralysis care este mai mare atunci
     * cand lupta se desfasoara pe un teren de tip Woods
     */
    public int rundeDotParalysis(final char teren) {
        if (teren == 'W') {
            return Rogue.PARALYSIS_ROUNDS * 2;
        } else {
            return Rogue.PARALYSIS_ROUNDS;
        }
    }

    /*
     * Functia de "accept" pentru Double Dispatch
     */
    @Override
    public int damagedBy(final Hero hero, final char teren) {
        return hero.damages(this, teren);
    }

    /*
     * Functie componenta a DoubleDispatch prin care un erou Rogue
     * interactioneaza cu un erou Rogue, mai intai ii seteaza acestuia damage-ul
     * OvertTime de pe urma abilitatii Paralysis si numarul de runde statice (
     * in care oponentul nu se poate misca), iar apoi returneaza Damage-ul
     * primit in runda curenta de pe urma abilitatilor BackStab si Paralysis
     */
    @Override
    public int damages(final Rogue rog, final char teren) {

        rog.setOvertTimeParameters(this.rundeDotParalysis(teren),
                Math.round(damageDotParalysis(
                        Rogue.MODIFICATOR_PARALYSIS.getRogue(), teren)));

        int damage = Math.round(
                damageFromBackstab(Rogue.MODIFICATOR_BACKSTAB.getRogue(), teren)
                        + damageDotParalysis(
                                Rogue.MODIFICATOR_PARALYSIS.getRogue(), teren));

        rog.setNrRundeStatice(this.rundeDotParalysis(teren));
        return damage;
    }

    /*
    *
    */
    @Override
    public int damages(final Knight kni, final char teren) {

        kni.setOvertTimeParameters(this.rundeDotParalysis(teren),
                Math.round(damageDotParalysis(
                        Rogue.MODIFICATOR_PARALYSIS.getKnight(), teren)));

        int damage = Math
                .round(damageFromBackstab(
                        Rogue.MODIFICATOR_BACKSTAB.getKnight(), teren))
                + Math.round(damageDotParalysis(
                        Rogue.MODIFICATOR_PARALYSIS.getKnight(), teren));

        kni.setNrRundeStatice(this.rundeDotParalysis(teren));
        return damage;
    }

    /*
    *
    */
    @Override
    public int damages(final Pyromancer pyr, final char teren) {

        pyr.setOvertTimeParameters(this.rundeDotParalysis(teren),
                Math.round(damageDotParalysis(
                        Rogue.MODIFICATOR_PARALYSIS.getPyromancer(), teren)));

        int damage = Math.round(damageFromBackstab(
                Rogue.MODIFICATOR_BACKSTAB.getPyromancer(), teren)
                + damageDotParalysis(
                        Rogue.MODIFICATOR_PARALYSIS.getPyromancer(), teren));

        pyr.setNrRundeStatice(this.rundeDotParalysis(teren));
        return damage;
    }

    /*
    *
    */
    @Override
    public int damages(final Wizard wiz, final char teren) {

        wiz.setOvertTimeParameters(this.rundeDotParalysis(teren),
                Math.round(damageDotParalysis(
                        Rogue.MODIFICATOR_PARALYSIS.getWizard(), teren)));

        int damage = Math.round(damageFromBackstab(
                Rogue.MODIFICATOR_BACKSTAB.getWizard(), teren)
                + damageDotParalysis(Rogue.MODIFICATOR_PARALYSIS.getWizard(),
                        teren));

        wiz.setNrRundeStatice(this.rundeDotParalysis(teren));
        return damage;
    }
}
