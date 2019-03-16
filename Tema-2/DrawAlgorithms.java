package main;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public final class DrawAlgorithms {

    public static final DrawAlgorithms ALG = new DrawAlgorithms();

    private DrawAlgorithms() {

    }

    public void deseneazaLinie(final int x1, final int y1, final int x2,
            final int y2, final BufferedImage buf, final int culoare) {

        int x = x1;
        int y = y1;
        int deltaX = Math.abs(x2 - x1);
        int deltaY = Math.abs(y2 - y1);
        int s1 = Integer.signum(x2 - x1);
        int s2 = Integer.signum(y2 - y1);

        boolean schimbare;
        if (deltaY > deltaX) {
            int aux = deltaY;
            deltaY = deltaX;
            deltaX = aux;
            schimbare = true;
        } else {
            schimbare = false;
        }

        int eroare = 2 * deltaY - deltaX;
        int i;
        for (i = 0; i <= deltaX; i++) {
            buf.setRGB(x, y, culoare);
            while (eroare > 0) {
                if (schimbare == true) {
                    x = x + s1;
                }
                eroare = eroare - 2 * deltaX;
            }
            if (schimbare == true) {
                y = y + s2;
            } else {
                x = x + s1;
            }
            eroare = eroare + 2 * deltaY;
        }
    }

    public void floodFill(final int inaltime, final int latime,
            final BufferedImage bufer, final int colStart, final int linStart,
            final int rgb, final int colorStop) {

        LinkedList<Integer> coada1 = new LinkedList<Integer>();
        LinkedList<Integer> coada2 = new LinkedList<Integer>();
        coada1.add(colStart);
        coada2.add(linStart);

        bufer.setRGB(colStart, linStart, rgb);

        int x, y;

        while (!coada1.isEmpty()) {

            x = coada1.remove();
            y = coada2.remove();

            if (x < 1 || y < 1 || (x == (inaltime - 1))
                    || (y == (inaltime - 1))) {
                x = colStart;
                y = linStart;
            }

            if (bufer.getRGB(x, y + 1) != colorStop
                    && bufer.getRGB(x, y + 1) != rgb) {

                bufer.setRGB(x, y + 1, rgb);
                coada1.add(x);
                coada2.add(y + 1);
            }

            if (bufer.getRGB(x, y - 1) != colorStop
                    && bufer.getRGB(x, y - 1) != rgb) {

                bufer.setRGB(x, y - 1, rgb);
                coada1.add(x);
                coada2.add(y - 1);
            }

            if (bufer.getRGB(x + 1, y) != colorStop
                    && bufer.getRGB(x + 1, y) != rgb) {

                bufer.setRGB(x + 1, y, rgb);
                coada1.add(x + 1);
                coada2.add(y);
            }

            if (bufer.getRGB(x - 1, y) != colorStop
                    && bufer.getRGB(x - 1, y) != rgb) {
                bufer.setRGB(x - 1, y, rgb);
                coada1.add(x - 1);
                coada2.add(y);
            }

        }
    }
}
