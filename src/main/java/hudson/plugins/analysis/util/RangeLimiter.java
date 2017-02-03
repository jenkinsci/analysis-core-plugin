package hudson.plugins.analysis.util;

/**
 * Created by gogic on 13.01.17.
 * <p>
 * This class is used to specify the areas in the source code that are affected by a warning.
 * There are two types of limiters. A start-limiter that marks start of a range and end-linmiter that
 * marks end of the range. In addition, a range limiter contains warning message and tool tip.
 */
public class RangeLimiter implements Comparable<RangeLimiter>{

    /* Specifies the type of this limiter. */
    private final boolean isStart;

    /* Contains warning message. */
    private final String message;

    /* Contains warning tool tip. */
    private final String toolTip;

    /* Specifies the line in which the range limiter is located. */
    private int line;

    /* Specifies the column in which the range limiter is located.*/
    private int column;

    /* Represents a number of this range limiter */
    private final int nr;


    public RangeLimiter(boolean isStart, String message, String toolTip, int line, int column, int nr) {
        this.isStart = isStart;
        this.message = message;
        this.toolTip = toolTip;
        this.line = line;
        this.column = column;
        this.nr = nr;
    }

    public boolean isStart() {
        return isStart;
    }

    public String getMessage() {
        return message;
    }

    public String getToolTip() {
        return this.toolTip;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getNr() {
        return nr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RangeLimiter)) return false;

        RangeLimiter that = (RangeLimiter) o;

        if (isStart() != that.isStart()) return false;
        if (getLine() != that.getLine()) return false;
        return getColumn() == that.getColumn();
    }

    @Override
    public int hashCode() {
        int result = (isStart() ? 1 : 0);
        result = 31 * result + getLine();
        result = 31 * result + getColumn();
        return result;
    }

    @Override
    public int compareTo(RangeLimiter other) {
        if(this.getLine()>other.getLine()){
            return 1;
        }
        else if(this.getLine()<other.getLine()){
            return -1;
        }
        else{
            if(this.getColumn()>other.getColumn()){
                return 1;
            }
            else if(this.getColumn()<other.getColumn()){
                return -1;
            }
            else{
                if(this.isStart()&&!other.isStart()){
                    return  -1;
                }
                else if(!this.isStart()&&other.isStart()) {
                    return 1;
                }
                else{
                    return 0;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "RangeLimiter{" +
                "isStart=" + isStart +
                ", line=" + line +
                ", column=" + column +
                ", nr=" + nr +
                '}';
    }
}
