package hudson.plugins.analysis.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by gogic on 09.02.17.
 */
public class MatrixTest {
    RangeLimiter start1=new RangeLimiter(true, "Range1 message","Range1 tooltip", 1,3,1);
    RangeLimiter end1=new RangeLimiter(false, "Range1 message","Range1 tooltip", 5,2,1);

    // Gleicher Start wie Nr.1
    RangeLimiter start2=new RangeLimiter(true, "Range2 message","Range2 tooltip", 1,3,2);
    RangeLimiter end2=new RangeLimiter(false, "Range2 message","Range2 tooltip", 7,5,2);

    // Gleiches Ende wie Nr.1
    RangeLimiter start3=new RangeLimiter(true, "Range3 message","Range3 tooltip", 2,4,3);
    RangeLimiter end3=new RangeLimiter(false, "Range3 message","Range3 tooltip", 4,2,3);

    RangeLimiter start4=new RangeLimiter(true, "Range4 message","Range4 tooltip", 10,3,4);
    RangeLimiter end4=new RangeLimiter(false, "Range4 message","Range4 tooltip", 15,2,4);

    RangeLimiter start5=new RangeLimiter(true, "Range5 message","Range5 tooltip", 12,3,5);
    RangeLimiter end5=new RangeLimiter(false, "Range5 message","Range5 tooltip", 17,2,5);

    RangeLimiter start6=new RangeLimiter(true, "Range6 message","Range6 tooltip", 8,3,6);
    RangeLimiter end6=new RangeLimiter(false, "Range6 message","Range6 tooltip", 8,3,6);

    RangeLimiter start7=new RangeLimiter(true, "Range7 message","Range7 tooltip", 8,9,7);
    RangeLimiter end7=new RangeLimiter(false, "Range7 message","Range7 tooltip", 14,7,7);


    @Test
    public void addElement() {
        Matrix matrix=new Matrix();
        matrix.addElement(start1);

        //Einfügen von Elementen an gleicher Stelle scheitert
        assertFalse(matrix.addElement(start2));

        //Einfügen eines zusaetzlichen Elements
        assertTrue(matrix.addElement(end1));

        // Eintragen des Warnbereichs mit nur einem Buchstaben
        assertTrue(matrix.addElement(start6));
        assertTrue(matrix.addElement(end6));
    }

    @Test
    public void removeElement() {
        Matrix matrix=new Matrix();
        matrix.addElement(start1);
        matrix.addElement(end1);

        assertEquals(2,matrix.getMatrix().size());

        assertFalse(matrix.removeElement(start6));
        assertTrue(matrix.removeElement(start1));
        assertTrue(matrix.removeElement(end1));
        assertTrue(matrix.getMatrix().isEmpty());
    }

    @Test
    public void resolveIntersections() {
        // Matrix mit Ueberschneidung der Bereiche nach Muster Start1,Start2,End1,End2
        Matrix matrix1=new Matrix();

        matrix1.addElement(start4);
        matrix1.addElement(end4);
        matrix1.addElement(start5);
        matrix1.addElement(end5);

        Matrix expected1= new Matrix();
        RangeLimiter endN4=new RangeLimiter(false, "Range4 message","Range4 tooltip", 12,2,4);

        expected1.addElement(start4);
        expected1.addElement(endN4);
        expected1.addElement(start5);
        expected1.addElement(end5);

        matrix1.resolveIntersections();
        assertEquals(expected1,matrix1);

        //Matrix mit Ueberschneidung der Bereiche nach Muster Start1,Start2,End2,End1
        Matrix matrix2=new Matrix();

        matrix2.addElement(start2);
        matrix2.addElement(end2);
        matrix2.addElement(start3);
        matrix2.addElement(end3);

        Matrix expected2=new Matrix();
        RangeLimiter endN2=new RangeLimiter(false, "Range2 message","Range2 tooltip", 2,3,2);
        RangeLimiter startN2=new RangeLimiter(true, "Range2 message","Range2 tooltip", 4,3,2);

        expected2.addElement(start2);
        expected2.addElement(endN2);
        expected2.addElement(start3);
        expected2.addElement(end3);
        expected2.addElement(startN2);
        expected2.addElement(end2);

        matrix2.resolveIntersections();
        assertEquals(expected2,matrix2);

        //Matrix ohne Überschneidung der Bereiche
        Matrix matrix3=new Matrix();
        matrix3.addElement(start2);
        matrix3.addElement(end2);
        matrix3.addElement(start4);
        matrix3.addElement(end4);

        Matrix expected3=new Matrix();
        expected3.addElement(start2);
        expected3.addElement(end2);
        expected3.addElement(start4);
        expected3.addElement(end4);

        matrix3.resolveIntersections();
        assertEquals(expected3,matrix3);
    }

    @Test
    public void rangesIntersect() {
        Matrix matrix=new Matrix();
        matrix.addElement(start1);
        matrix.addElement(end1);
        matrix.addElement(start4);
        matrix.addElement(end4);

        //no intersections
        assertFalse(matrix.rangesIntersect());

        matrix.addElement(start5);
        matrix.addElement(end5);
        //now ranges intesect
        assertTrue(matrix.rangesIntersect());

        // new range added
        matrix.addElement(start7);
        matrix.addElement(end7);

        assertTrue(matrix.rangesIntersect());
    }

    @Test
    public void getLine() {
        Matrix matrix=new Matrix();
        matrix.addElement(start6);
        matrix.addElement(start7);

        assertEquals(2,matrix.getLine(8).size());
        assertEquals(0,matrix.getLine(2).size());

        matrix.removeElement(start6);
        matrix.removeElement(start7);
        assertEquals(0,matrix.getLine(8).size());
    }

}