package main;

public final class Main {

    /**
     * @param valoarea
     *            cu care viata maxima creste la fiecare level
     */
    private Main() {

    }

    public static void main(final String[] args) {
        String name = args[0];
        // La creeara instantei pentru clasa Game se efectueaza si citirea
        Game gam = new Game(name);
        // Se initializeaza fiecare player in conformitate cu rasa si pozitia pe
        // harta
        gam.initPlayers();
        gam.startJoc();
        gam.scriere(args[1]);
    }
}
