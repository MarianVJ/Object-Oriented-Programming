package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.imageio.ImageIO;

import forme.ShapeFactory;
import interfaces.ShapeVisitable;

public class DrawForms {

    private int numarForme;
    private ShapeVisitable[] forme;
    private String fileName;

    DrawForms(final String fileName) {
        this.fileName = fileName;
    }

    public void initForms() {
        try {
            RandomAccessFile scanner = new RandomAccessFile(fileName, "rw");
            String linie = scanner.readLine();
            this.numarForme = Integer.valueOf(linie);

            this.forme = new ShapeVisitable[this.numarForme];
            int i;
            for (i = 0; i < this.numarForme; i++) {
                linie = scanner.readLine();
                this.forme[i] = ShapeFactory.INSTANCE.getShape(linie);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawImage() {
        int i;
        FormVisitor visitor = new FormVisitor();
        for (i = 0; i < this.numarForme; i++) {
            this.forme[i].accept(visitor);
        }
        BufferedImage bufer = visitor.getBuffer();
        File outputfile = new File(
                "/Users/Office/Desktop/Tema_2/src/drowing.png");
        try {
            ImageIO.write(bufer, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
