package hudson.plugins.analysis.util.model;

import hudson.plugins.analysis.graph.BuildResultGraph;


public class PriorityConstant {

    public static Class<? extends PriorityInt> priorityEnum = Priority.class;

    public static BuildResultGraph DEFAULT_GRAPH;

    private PriorityConstant(){

    }
}

