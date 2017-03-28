package hudson.plugins.analysis.util;

/**
 * Created by gogic on 21.01.17.
 * This class represents a range in the source code that is affected by a warning.
 * The class is used to sort the areas by size.
 */
public class Range implements Comparable<Range> {

    /* Range Limiter that marks the start of an area.*/
    private RangeLimiter start;

    /* Range Limiter that marks the start of an area. */
    private RangeLimiter end;

    public Range(RangeLimiter start,RangeLimiter end){
        this.start=start;
        this.end=end;
    }

    public RangeLimiter getStart() {
        return this.start;
    }

    public RangeLimiter getEnd() {
        return this.end;
    }

    private int getLength(){
        return end.getLine()-start.getLine();
    }

    @Override
    public int compareTo(Range other) {

        int difference=this.getLength()-other.getLength();

        if(difference>0){
            return 1;
        }
        else if(difference<0){
            return -1;
        }
        else {
            return 0;
        }
    }
}
