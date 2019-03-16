package eroi;

public class Hero {

    static final int FIRST_XP = 200;
    static final int LEVEL_XP_LIMIT = 250;
    static final int XP_FOR_NEW_LEVEL = 50;
    static final int XP_FOR_WINNING = 40;
    protected int hpMax;
    protected int level;
    protected int xp;
    protected int hp;
    protected int[] coordonates;
    protected int addHp;
    protected int overtTimeContor;
    protected int nrRundeStatice;
    protected int overTimeDamage;
    protected int stareaErou;
    protected int deadCode;

    Hero(final int x, final int y, final int hpMax, final int addHp) {
        this.coordonates = new int[2];
        this.coordonates[0] = x;
        this.coordonates[1] = y;
        this.xp = 0;
        this.hp = hpMax;
        this.level = 0;
        setHpMax(hpMax);
        setAddHp(addHp);
        this.overtTimeContor = 0;
        this.overTimeDamage = 0;
        this.nrRundeStatice = 0;
        this.stareaErou = 0;
        this.deadCode = 0;
    }

    /*
     * Flag pentru a verifica daca Eroul nostru este in viata(0) sau daca este
     * mor(1)
     */
    public int getDeadCode() {
        return this.deadCode;
    }

    /*
     * Folosit atunci cand viata eroului este 0 sau mai mic , pentru a marca
     * faptul ca acesta a murit
     */
    public void setDeadCode(final int cod) {
        this.deadCode = cod;
    }

    /*
     * Flag care marcheaza faptul ca Eroul nostru s-a luptat deja in runda
     * curenta
     */
    public int getStareaErou() {
        return this.stareaErou;
    }

    /*
     * Se seteaza acest flag dupa fiecare lupta la 1 si dupa fiecare runda toate
     * flagurile de acest gen se seteaza la 0 deoarece o noua runda este pe cale
     * sa inceapa
     */
    public void setStareaErou(final int stare) {
        this.stareaErou = stare;
    }

    /**
     * @param intoarce
     *            level-ul curent al eroului
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level-ul
     *            se mofidica cand un erou castiga
     */
    public void setLevel(final int level) {
        this.level = level;
    }

    /*
     * intoarce xp-ul curent al curent al eroului
     */
    public int getXp() {
        return xp;
    }

    /**
     * @param experienta
     *            pe care o primeste atunci cand castiga o lupta
     */
    public void setXp(final int xp) {
        this.xp = xp;
    }

    /*
     * intoarce hp-ul curent al curent al eroului
     */
    public int getHp() {
        return hp;
    }

    /**
     * @param hp
     *            ul se modifica atunci cand un erou are un nou nivel
     */
    public void setHp(final int hp) {
        this.hp = hp;
    }

    /*
     * intoarce coordonata x a Eroului
     */
    public int getX() {
        return coordonates[0];
    }

    /*
     * intoarce coordonata y a Eroului
     */
    public int getY() {
        return coordonates[1];
    }

    /**
     * @param cordonatele
     *            noi pe care acesta le primeste la fiecare lupta
     */
    public void setCoordonates(final int[] coordonates) {
        this.coordonates[0] = coordonates[0];
        this.coordonates[1] = coordonates[1];
    }

    /*
     * intoarce viata maxima pe care o poate avea un erou
     */
    public int getHpMax() {
        return hpMax;
    }

    /**
     * @param viata
     *            maxima pe care eroul nostru o are
     */
    public void setHpMax(final int hpMax) {
        this.hpMax = hpMax;
    }

    /*
     * Actualizeaza xp-ul dupa fiecare victorie si se verifica updatarea
     * nivelului
     */
    public void xpUpdate(final int xpWinner, final int levelWinner) {
        setXp(this.xp + Integer.max(0, Hero.FIRST_XP
                - (levelWinner - this.level) * Hero.XP_FOR_WINNING));
        levelUpdate();
    }

    /*
     * Metoda de a actualiza levelul daca eroul intruneste conditia necesara
     * Daca se updateaza levelul se vor updata si viata maxima ,viata actuala si
     * damage-urile
     */
    public void levelUpdate() {
        if (this.xp >= (Hero.LEVEL_XP_LIMIT
                + this.level * Hero.XP_FOR_NEW_LEVEL)) {

            this.level = ((this.xp - Hero.LEVEL_XP_LIMIT)
                    / Hero.XP_FOR_NEW_LEVEL) + 1;
            this.setHpMax(hpMax + this.addHp * this.level);
            this.hp = this.hpMax;
            updateDamages();
        }
    }

    /*
     * Metoda de a actualiza valoarea maxima a vietii pe care o are si viata
     * curenta a eroului ( deoarece viata se actualizeaza doar atunci cand se
     * face level-up
     */
    public void hpUpdate() {
        this.hpMax = this.hpMax + this.addHp;
        this.hp = this.hpMax;
    }

    /**
     * @param valoarea
     *            cu care viata maxima creste la fiecare level
     */
    public void setAddHp(final int addHp) {
        this.addHp = addHp;
    }

    /**
     * @param1 numarul de runde pentru care eroul nostru va primi damage
     *         overtime
     * @param2 damage ul pe care il va suferi la fiecare runda
     */
    public void setOvertTimeParameters(final int contorOverTime,
            final int overTimeDamagee) {
        this.overTimeDamage = overTimeDamagee;
        this.overtTimeContor = contorOverTime;
    }

    /*
     * Metoda ce returneaza damage-ul overTime pe care eroul il are de primit
     * timp de overTimeContor runde
     */
    public int getOverTimeDamage() {
        return this.overTimeDamage;
    }

    /*
     *
     */
    public int getOverTimeContor() {
        int auxiliary;
        auxiliary = this.overtTimeContor;
        this.overtTimeContor--;
        return auxiliary;
    }

    /**
     * @param numarul
     *            de runde pentru care eroul nostru nu se va putea muta de pe
     *            pozitia curenta
     */
    public void setNrRundeStatice(final int nrRundeStatice) {
        this.nrRundeStatice = nrRundeStatice;
    }

    /*
     * Returneaza numarul de runde statice pe care trebuie sa le suporte
     */
    public int getNrRundeStatice() {
        return this.nrRundeStatice;
    }

    /**
     * @param mutarea
     *            pe care eroul trebuie sa o faca pe harta la runda curenta
     */
    public void moov(final char miscare) {

        if (miscare == 'R') {
            this.coordonates[1]++;
        } else if (miscare == 'L') {
            this.coordonates[1]--;
        } else if (miscare == 'U') {
            this.coordonates[0]--;
        } else if (miscare == 'D') {
            this.coordonates[0]++;
        }
    }

    /*
     * Aceasta metoda este suprascrisa in toate clasele derivate din clasa erou,
     * si are rolul de a updata damage-urile conform cerintelor fiecarui erou
     * atunci cand aceste creste un level/ mai multe
     */
    public void updateDamages() {

    }

    /*
     * Functia de "accept" pentru Double Dispatch
     */
    public int damagedBy(final Hero hero, final char teren) {
        return hero.damages(this, teren);
    }

    /*
     * Vor fi suprascrise in fiecare clasa , conformt specificatiilor fiecarui
     * erou ( Componenta a Double Dispatch)
     */
    public int damages(final Rogue rog, final char teren) {

        return 0;
    }

    /*
     * Vor fi suprascrise in fiecare clasa , conformt specificatiilor fiecarui
     * erou ( Componenta a Double Dispatch)
     */
    public int damages(final Knight kni, final char teren) {
        return 0;
    }

    /*
     * Vor fi suprascrise in fiecare clasa , conformt specificatiilor fiecarui
     * erou ( Componenta a Double Dispatch)
     */
    public int damages(final Pyromancer pyr, final char teren) {
        return 0;
    }

    /*
     * Vor fi suprascrise in fiecare clasa , conformt specificatiilor fiecarui
     * erou ( Componenta a Double Dispatch)
     */
    public int damages(final Wizard wiz, final char teren) {
        return 0;
    }

    /*
     * Vor fi suprascrise in fiecare clasa , conformt specificatiilor fiecarui
     * erou ( Componenta a Double Dispatch)
     */
    public int damages(final Hero her, final char teren) {
        return 0;
    }

    /*
     * Aceasta metoda este suprascrisa in unele clase , pentru a updata anumite
     * proprietati inerne dupa ce ambele personaje si-au dat atacurile unul
     * altuia
     */
    public void updateInternInformations() {

    }
}
