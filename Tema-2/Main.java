package main;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Main {

    public static void main(String[] args) throws IOException {

        String nume = "/Users/Office/Desktop/Tema_2/src/input/test0.in";
        RandomAccessFile scanner = new RandomAccessFile(nume, "rw");
        DrawForms draw = new DrawForms(nume);
        draw.initForms();
        System.out.println("hei");
        draw.drawImage();
        System.out.println("hei");

        /*
         * String s = scanner.readLine(); scanner.close();
         * 
         * BufferedImage im = new BufferedImage(656, 762,
         * BufferedImage.TYPE_INT_ARGB); int i, j;
         * 
         * String sir = "#FB293F 100"; int val1 =
         * Integer.parseInt(sir.substring(1, 3), 16); int val2 =
         * Integer.parseInt(sir.substring(3, 5), 16); int val3 =
         * Integer.parseInt(sir.substring(5, 7), 16); int val4 =
         * Integer.parseInt(sir.substring(8, 11), 10);
         * 
         * Color myWhite = new Color(val1, val2, val3, val4); // Color white int
         * rgb = myWhite.getRGB();
         * 
         * for (i = 0; i < 656; i++) for (j = 0; j < 762; j++) im.setRGB(i, j,
         * rgb);
         * 
         * File outputfile = new File(
         * "/Users/Office/Desktop/Tema_2/src/drowing.png"); ImageIO.write(im,
         * "png", outputfile);
         * 
         */

    }
}
