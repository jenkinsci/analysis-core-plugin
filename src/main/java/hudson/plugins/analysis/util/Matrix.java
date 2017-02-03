package hudson.plugins.analysis.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gogic on 25.01.17.
 * The class Matrix represents a sparse matrix.
 * The data structure contains the objects of the class RangeLimiter as entries and manages them.
 * In addition, the class has the methods to detect the overlaps between
 * individual areas and to resolve these.
 */
public class Matrix {

    private TreeSet<RangeLimiter> matrix;

    public Matrix() {
        this.matrix = new TreeSet<RangeLimiter>();
    }

    public TreeSet<RangeLimiter> getMatrix() {
        return matrix;
    }

    /**
     * Puts a range limiter into the data structure.
     * @param rangeLimiter range limiter that has to be put into the data structure
     * @return true if there was no such element in the data structure
     */
    public boolean addElement(RangeLimiter rangeLimiter){
        return matrix.add(rangeLimiter);
    }

    /**
     * Deletes a range limiter from the data structure.
     * @param rangeLimiter range limiter that has to be removed from the data structure
     * @return true if the element got removed
     */
    public boolean removeElement(RangeLimiter rangeLimiter){
        return matrix.remove(rangeLimiter);
    }

    /**
     * Returns all elements of this data structure in order from coordinate(0,0) till coordinate(end,end).
     *
     * @return all elements in order
     */
    public ArrayList<RangeLimiter> getAllElementsInOrder(){
        ArrayList<RangeLimiter> list=new ArrayList<RangeLimiter>();
        Iterator iterator=matrix.iterator();
        while(iterator.hasNext()){
            list.add((RangeLimiter)iterator.next());
        }
        return list;
    }

    /**
     * This method resolves overlaps between ranges.
     */
    public void resolveIntersections() {

        ArrayList<RangeLimiter> allElements = getAllElementsInOrder();
        int allElementsSize = allElements.size();

        RangeLimiter current = allElements.get(0);
        RangeLimiter next;

        for (int i = 1; i < allElementsSize; i++) {

            next=allElements.get(i);

            if (current.isStart() && !next.isStart()) {
                if (current.getNr() != next.getNr()) {
                    removeElement(next);
                    next = null;
                }
            } else if (!current.isStart() && !next.isStart()) {

                if (current.getColumn() == Integer.MAX_VALUE) {
                    addElement(new RangeLimiter(true, next.getMessage(), next.getToolTip(), current.getLine() + 1, 0, next.getNr()));
                } else {
                    addElement(new RangeLimiter(true, next.getMessage(), next.getToolTip(), current.getLine(), current.getColumn() + 1, next.getNr()));
                }
            } else if (current.isStart() && next.isStart()) {
                //ein Ende vor next
                if (next.getColumn() == 0) {
                    addElement(new RangeLimiter(false, current.getMessage(), current.getToolTip(), next.getLine() - 1, Integer.MAX_VALUE, current.getNr()));
                } else {
                    addElement(new RangeLimiter(false, current.getMessage(), current.getToolTip(), next.getLine(), next.getColumn() - 1, current.getNr()));
                }
            }
            if (next != null) {
                current = next;
            }
        }
    }

    /**
     * Indicates if there are overlaps between ranges in the data structure.
     * If there are no overlaps between individual areas,
     * the entries are arranged according to the pattern: (start, end) (start, end) ... = (start,end)+.
     * To check whether overlaps exist, the method iterates through the entries in the data structure and queries whether they are a start element.
     * The results are inserted into a StringBuilder.
     * Finally, it is checked whether the resulting string corresponds to the pattern (truefalse).
     * In this case, there is no overlap.
     *
     * @return true if ranges intersect
     */
    public boolean rangesIntersect() {
        // Alle Elemente holen
        ArrayList<RangeLimiter> list = getAllElementsInOrder();
        Iterator iterator = list.iterator();
        StringBuilder sb = new StringBuilder(256);

        // Für jeden Eintrag abfragen, ob es sich um einen Start handelt.
        // Die Ergebnisse zu einem String zusammenfassen.
        while (iterator.hasNext()) {
            RangeLimiter tmp = (RangeLimiter) iterator.next();
            sb.append(tmp.isStart());
        }

        //Überprüfen, ob der String dem Muster "(truefalse)+" entspricht.
        Pattern pattern = Pattern.compile("(truefalse)+");
        Matcher matcher = pattern.matcher(sb.toString());

        return !matcher.matches();
    }

    /**
     * Returns all range limiters that are situated in the line with number=lineNumber.
     * @param lineNumber
     * @return
     */
    public List<RangeLimiter> getLine(int lineNumber){
        List<RangeLimiter> line=new ArrayList<RangeLimiter>();
        List<RangeLimiter> allElements=getAllElementsInOrder();
        Iterator iterator=allElements.iterator();
        while(iterator.hasNext()){
            RangeLimiter tmp= (RangeLimiter) iterator.next();
            if(tmp.getLine()==lineNumber){
                line.add(tmp);
            }
        }
        return line;
    }
}