package io.jenkins.plugins.analysis.core.quality;

import java.util.List;
import java.util.Map;

import jnr.constants.platform.PRIO;

import hudson.plugins.analysis.util.model.Priority;
import java.io.Serializable;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Ullrich Hafner
 */
public class QualityGate implements Serializable{
    private final int lowPriorityUnstable;
    private final int lowPriorityFailure;
    private final int normalPriorityUnstable;
    private final int normalPriorityFailure;
    private final int highPriorityUnstable;
    private final int highPriorityFailure;

    QualityGate (final int lowPriorityUnstable, final int lowPriorityFailure,
            final int normalPriorityUnstable, final int normalPriorityFailure,
            final int highPriorityUnstable, final int highPriorityFailure){
        this.lowPriorityUnstable = lowPriorityUnstable;
        this.lowPriorityFailure = lowPriorityFailure;
        this.normalPriorityUnstable = normalPriorityUnstable;
        this.normalPriorityFailure = normalPriorityFailure;
        this.highPriorityUnstable = highPriorityUnstable;
        this.highPriorityFailure = highPriorityFailure;

        thresholdsAreValid();
    }

    QualityGate(){
        this(0,0,0,0,0,0);
    }

    public int getLowPriorityUnstable() {
        return lowPriorityUnstable;
    }

    public int getLowPriorityFailure() {
        return lowPriorityFailure;
    }

    public int getNormalPriorityUnstable() {
        return normalPriorityUnstable;
    }

    public int getNormalPriorityFailure() {
        return normalPriorityFailure;
    }

    public int getHighPriorityUnstable() {
        return highPriorityUnstable;
    }

    public int getHighPriorityFailure() {
        return highPriorityFailure;
    }

    private void thresholdsAreValid() throws IllegalArgumentException{
        if(highPriorityFailure<0){
            throw new IllegalArgumentException(String.format("The threshold highPriorityFailure should be positive or null, but was %d", highPriorityFailure));
        }
        if(highPriorityUnstable<0){
            throw new IllegalArgumentException(String.format("The threshold highPriorityUnstable should be positive or null, but was %d", highPriorityUnstable));
        }
        if(normalPriorityFailure<0){
            throw new IllegalArgumentException(String.format("The threshold normalPriorityFailure should be positive or null, but was %d", normalPriorityFailure));
        }
        if(normalPriorityUnstable<0){
            throw new IllegalArgumentException(String.format("The threshold normalPriorityUnstable should be positive or null, but was %d", normalPriorityUnstable));
        }
        if(lowPriorityFailure<0){
            throw new IllegalArgumentException(String.format("The threshold lowPriorityFailure should be positive or null, but was %d", lowPriorityFailure));
        }
        if(lowPriorityUnstable<0){
            throw new IllegalArgumentException(String.format("The threshold lowPriorityUnstable should be positive or null, but was %d", lowPriorityUnstable));
        }
        if((lowPriorityFailure!=0 && lowPriorityUnstable!=0) && lowPriorityFailure <= lowPriorityUnstable){
            throw new IllegalArgumentException(String.format("The threshold lowPriorityFailure has to be bigger than lowPriorityUnstable, but was %d and lowPriorityUnstable was %d.", lowPriorityFailure, lowPriorityUnstable));
        }
        if((normalPriorityFailure!=0 && normalPriorityUnstable!=0) && normalPriorityFailure <= normalPriorityUnstable){
            throw new IllegalArgumentException(String.format("The threshold normalPriorityFailure has to be bigger than normalPriorityUnstable, but was %d and normalPriorityUnstable was %d.", normalPriorityFailure, normalPriorityUnstable));
        }
        if((highPriorityFailure!=0 && highPriorityUnstable!=0) && highPriorityFailure <= highPriorityUnstable){
            throw new IllegalArgumentException(String.format("The threshold highPriorityFailure has to be bigger than highPriorityUnstable, but was %d and highPriorityUnstable was %d.", highPriorityFailure, highPriorityUnstable));
        }
    }
}


