package main;

import java.io.IOException;
import java.io.RandomAccessFile;

import eroi.Hero;
import eroi.Knight;
import eroi.Pyromancer;
import eroi.Rogue;
import eroi.Wizard;

public class Game {

    static final int NR_SPLITURI = 3;
    private int nrLinii;
    private int nrColoane;
    private char[][] teren;
    private int numarPersonaje;
    private char[] personaje;
    private int[][] pozitiiInitiale;
    private int nrRunde;
    private char[][] mutari;
    private Hero[] hero;

    /*
     * Constructorul primeste ca parametru fisierul din care se efectueaza
     * citirea dupa care retine in membrii clasei Game , datele necesare
     * desfasurarii unui joc. Citesc linie cu linie datele din fisier si parsez
     * datele cu ajutorul functiilor din clasa String .
     */
    Game(final String nume) {
        try {
            RandomAccessFile read = new RandomAccessFile(nume, "rw");
            String sir = read.readLine();

            int i;
            String[] split = new String[Game.NR_SPLITURI];
            split = sir.split(" ");
            // Citesc numarul de linii de coloane si tipul fiecarui teren
            // de pe harta
            nrLinii = Integer.valueOf(split[0]);
            nrColoane = Integer.valueOf(split[1]);
            teren = new char[nrColoane][nrLinii];
            for (i = 0; i < nrColoane; i++) {
                sir = read.readLine();
                teren[i] = sir.toCharArray();
            }
            // Citesc numarul de personaje , rasa fiecaruia si pozitiile lor pe
            // harta
            sir = read.readLine();
            numarPersonaje = Integer.valueOf(sir);
            personaje = new char[numarPersonaje];
            pozitiiInitiale = new int[numarPersonaje][2];
            char[] aux = new char[2];
            for (i = 0; i < numarPersonaje; i++) {
                sir = read.readLine();
                split = sir.split(" ");
                aux = split[0].toCharArray();
                personaje[i] = aux[0];
                pozitiiInitiale[i][0] = Integer.valueOf(split[1]);
                pozitiiInitiale[i][1] = Integer.valueOf(split[2]);
            }
            // Citesc numarul de runde din meciul curent si mutarile pe care le
            // executa eroii la fiecare runda
            nrRunde = Integer.valueOf(read.readLine());
            mutari = new char[nrRunde][nrRunde];
            for (i = 0; i < nrRunde; i++) {
                sir = read.readLine();
                mutari[i] = sir.toCharArray();
            }
            read.close();
            hero = new Hero[this.numarPersonaje];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Functia care intializeaza jucatorii inainte de inceperea bataliilor in
     * conformitate cu rasa fiecaruia si cu pozitiile initiale ocupate pe harta
     */
    public void initPlayers() {
        int i;
        for (i = 0; i < numarPersonaje; i++) {
            if (personaje[i] == 'K') {
                hero[i] = new Knight(this.pozitiiInitiale[i][0],
                        this.pozitiiInitiale[i][1]);
            }
            if (personaje[i] == 'W') {
                hero[i] = new Wizard(this.pozitiiInitiale[i][0],
                        this.pozitiiInitiale[i][1]);
            }
            if (personaje[i] == 'P') {
                hero[i] = new Pyromancer(this.pozitiiInitiale[i][0],
                        this.pozitiiInitiale[i][1]);
            }
            if (personaje[i] == 'R') {
                hero[i] = new Rogue(this.pozitiiInitiale[i][0],
                        this.pozitiiInitiale[i][1]);
            }
        }
    }

    /**
     * @param Actualizeaza
     *            pozitiile jucatorilor pe harta dupa fiecare runda( in cazul in
     *            care nu sufera in urma unei abilitati statice)
     */
    public void actualizarePozitii(final int nrRundaCurenta) {
        int i;
        for (i = 0; i < numarPersonaje; i++) {
            if (hero[i].getNrRundeStatice() == 0) {
                hero[i].moov(this.mutari[nrRundaCurenta][i]);
            } else {
                hero[i].setNrRundeStatice(hero[i].getNrRundeStatice() - 1);
            }
        }
    }

    /**
     * @param1 @param2 Dupa fiecare lupta se verifica daca unul dintre eroi a
     *         murit ca urmare a damage ului din runda curenta, daca da se
     *         actualizeaza xp-ul oponentului daca acesta se afla in viata
     */
    public void updateHeroesStatus(final Hero her1, final Hero her2,
            final int damage1, final int damage2) {
        // Actualizez viata in functie de damage-ul primit in runda curenta
        her1.setHp(her1.getHp() - damage1);
        her2.setHp(her2.getHp() - damage2);
        if (her1.getHp() <= 0 && her2.getHp() > 0) {
            her1.setDeadCode(1);
            her2.xpUpdate(her2.getXp(), her1.getLevel());
        }
        if (her2.getHp() <= 0 && her1.getHp() > 0) {
            her2.setDeadCode(1);
            her1.xpUpdate(her1.getXp(), her2.getLevel());
        }
    }

    /*
     * Aceasta metoda verifica daca personajele sufera in urma unei abilitati
     * dot inainte de inceperea fiecarei runde , daca da updateaza viata si
     * verifica daca mor un urma acestei abilitati
     */
    public void verificaDamageDot() {
        int i;
        for (i = 0; i < this.numarPersonaje; i++) {
            if (hero[i].getOverTimeContor() > 0) {
                hero[i].setHp(hero[i].getHp() - hero[i].getOverTimeDamage());
            }
            if (hero[i].getHp() <= 0) {
                hero[i].setDeadCode(1);
            }
        }
    }

    /*
     * Aceasta metoda reprezinta desfasurarea propriu-zisa a Meciului.
     */
    public void startJoc() {
        int nrRundaCurenta = 0;
        int damage1;
        int damage2;
        int i, j;
        while (nrRunde > 0) {

            nrRunde--;
            actualizarePozitii(nrRundaCurenta);
            nrRundaCurenta++;
            // Aplic abilitatile overTime
            verificaDamageDot();

            for (i = 0; i < this.numarPersonaje; i++) {
                for (j = 0; j < this.numarPersonaje; j++) {
                    // Verific daca doi eroi au aceleasi coordonate si daca nu
                    // s-au mai luptat deja in aceasta runda ( sau sunt morti)
                    if ((hero[i].getX() == hero[j].getX())
                            && (hero[i].getY() == hero[j].getY()) && i != j
                            && hero[i].getStareaErou() == 0
                            && hero[j].getStareaErou() == 0
                            && hero[i].getDeadCode() != 1
                            && hero[j].getDeadCode() != 1) {
                        // Marchez faptul ca cei doi au luptat deja in runda
                        // curenta
                        hero[i].setStareaErou(1);
                        hero[j].setStareaErou(1);
                        // Calculez damage-ul primmit de fiecare
                        damage1 = hero[i].damagedBy(hero[j],
                                this.teren[hero[i].getX()][hero[i].getY()]);
                        damage2 = hero[j].damagedBy(hero[i],
                                this.teren[hero[i].getX()][hero[i].getY()]);
                        // Updatez viata in functie de damage-ul primit
                        this.updateHeroesStatus(hero[i], hero[j], damage1,
                                damage2);
                        // Updatez alte informatii necesare in calcularea
                        // damage-ului , relevante pe tot parcursul jocului
                        hero[i].updateInternInformations();
                        hero[j].updateInternInformations();

                    }
                }
            }
            // Setez flagul de lupta din nou la zero , pentru ca acestia sa se
            // poata lupta din nou runda viitoare
            for (i = 0; i < this.numarPersonaje; i++) {
                hero[i].setStareaErou(0);
            }
        }

    }

    /*
     * Metoda care va scrie in fiseirul de OutPut statusurile finale ale eroilor
     */
    public void scriere(final String nume) {
        try {

            int i;
            RandomAccessFile read = new RandomAccessFile(nume, "rw");
            for (i = 0; i < this.numarPersonaje; i++) {

                read.writeByte(this.personaje[i]);
                if (hero[i].getHp() <= 0) {
                    read.writeBytes(" dead");

                } else {
                    // Concatenez toate informatiile dorite intr-un String dupa
                    // care il scriu in fisier
                    String sir = " " + String.valueOf(hero[i].getLevel()) + " ";
                    sir = sir.concat(String.valueOf(hero[i].getXp()) + " ");
                    sir = sir.concat(String.valueOf(hero[i].getHp()) + " ");
                    sir = sir.concat(String.valueOf(hero[i].getX()) + " ");
                    sir = sir.concat(String.valueOf(hero[i].getY()));
                    // Scriu sirul
                    read.writeBytes(sir);
                }
                read.writeBytes("\n");

            }

            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
