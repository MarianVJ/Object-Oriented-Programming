package main;

import java.awt.image.BufferedImage;

import forme.Canvas;
import forme.Circle;
import forme.Diamond;
import forme.Line;
import forme.Polygon;
import forme.Rectangle;
import forme.Square;
import forme.Triangle;
import interfaces.ShapeVisitor;

public class FormVisitor implements ShapeVisitor {
    private BufferedImage bufer;
    private int inaltime;
    private int latime;

    FormVisitor() {

    }

    public BufferedImage getBuffer() {
        return this.bufer;
    }

    public void visit(final Canvas canavas) {
        this.bufer = new BufferedImage(canavas.getLatime(),
                canavas.getInaltime(), BufferedImage.TYPE_INT_ARGB);
        int i, j;
        for (i = 0; i < canavas.getInaltime(); i++)
            for (j = 0; j < canavas.getLatime(); j++) {
                this.bufer.setRGB(j, i, canavas.getCuloare());
            }
        this.inaltime = canavas.getInaltime();
        this.latime = canavas.getLatime();

    }

    public void visit(final Square sq) {

        int sw3 = 0;
        int sw4 = 0;
        int[] p1 = new int[2];
        int[] p2 = new int[2];
        int[] p3 = new int[2];
        int[] p4 = new int[2];
        p1[0] = sq.getCoordX();
        p1[1] = sq.getCoordY();

        p2[1] = p1[1];
        System.out.println(p1[0] + " " + p1[1]);
        if (p1[0] + sq.getLatura() > latime) {
            p2[0] = latime - 1;
        } else {
            p2[0] = p1[0] + sq.getLatura() - 1;
        }
        System.out.println(p2[0] + " " + p2[1]);
        p3[0] = p1[0];
        if (p1[1] + sq.getLatura() > inaltime) {
            sw3 = 1;
            p3[1] = inaltime - 1;
        } else {
            p3[1] = p1[1] + sq.getLatura();
        }
        System.out.println(p3[0] + " " + p3[1]);
        p4[1] = p3[1];
        if (p3[0] + sq.getLatura() > latime) {
            sw4 = 1;
            p4[0] = latime - 1;
        } else {
            p4[0] = p3[0] + sq.getLatura() - 1;
        }

        DrawAlgorithms.ALG.deseneazaLinie(p1[0], p1[1], p2[0], p2[1],
                this.bufer, sq.getCuloareContur());

        DrawAlgorithms.ALG.deseneazaLinie(p1[0], p1[1], p3[0], p3[1],
                this.bufer, sq.getCuloareContur());
        if (sw3 == 0)
            DrawAlgorithms.ALG.deseneazaLinie(p3[0], p3[1], p4[0], p4[1],
                    this.bufer, sq.getCuloareContur());
        if (sw4 == 0)
            DrawAlgorithms.ALG.deseneazaLinie(p2[0], p2[1], p4[0], p4[1],
                    this.bufer, sq.getCuloareContur());

        DrawAlgorithms.ALG.floodFill(this.inaltime, this.latime, bufer,
                p1[0] + 1, p1[1] + 1, sq.getCuloareInt(),
                sq.getCuloareContur());

    }

    public void visit(final Rectangle rec) {

    }

    public void visit(final Circle circle) {

    }

    public void visit(final Triangle triangle) {

    }

    public void visit(final Diamond diamond) {

    }

    public void visit(final Polygon polygon) {

    }

    public void visit(final Line line) {

    }

}
