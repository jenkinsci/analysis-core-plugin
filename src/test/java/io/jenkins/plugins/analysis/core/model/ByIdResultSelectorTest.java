package io.jenkins.plugins.analysis.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import io.jenkins.plugins.analysis.core.views.ResultAction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import hudson.model.Run;

/**
 * Tests the class {@link ByIdResultSelector}.
 *
 * @author Anna-Maria Hardi
 */
class ByIdResultSelectorTest {

    @Test
    void testShouldEmptyIfActionsAreEmpty() {
        ByIdResultSelector byIdResultSelector = new ByIdResultSelector("1");
        Run<?, ?> run = mock(Run.class);
        when(run.getActions(ResultAction.class)).thenReturn(new ArrayList<>());

        Optional<ResultAction> actualResult = byIdResultSelector.get(run);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void testOneShouldEmptyIfIdsAreDifferent() {
        ByIdResultSelector byIdResultSelector = new ByIdResultSelector("1");
        List<ResultAction> resAct = new ArrayList<>();
        ResultAction resultAction = mock(ResultAction.class);
        when(resultAction.getId()).thenReturn("2");
        resAct.add(resultAction);
        Run<?, ?> run = mock(Run.class);
        when(run.getActions(ResultAction.class)).thenReturn(resAct);

        assertThat(byIdResultSelector.get(run)).isEmpty();
    }

    @Test
    void testTwoShouldEmptyIfIdsAreDifferent() {
        ByIdResultSelector byIdResultSelector = new ByIdResultSelector("1");
        List<ResultAction> resAct = new ArrayList<>();
        ResultAction resultAction1 = mock(ResultAction.class);
        when(resultAction1.getId()).thenReturn("2");
        resAct.add(resultAction1);
        ResultAction resultAction2 = mock(ResultAction.class);
        when(resultAction2.getId()).thenReturn("3");
        resAct.add(resultAction2);
        Run<?, ?> run = mock(Run.class);
        when(run.getActions(ResultAction.class)).thenReturn(resAct);

        assertThat(byIdResultSelector.get(run)).isEmpty();
    }

    @Test
    void testOneShouldFindSameIds() {
        ByIdResultSelector byIdResultSelector = new ByIdResultSelector("1");
        ResultAction resultAction = mock(ResultAction.class);
        when(resultAction.getId()).thenReturn("1");
        List<ResultAction> resAct = new ArrayList<>();
        resAct.add(resultAction);
        Run<?, ?> run = mock(Run.class);
        when(run.getActions(ResultAction.class)).thenReturn(resAct);

        assertThat(byIdResultSelector.get(run)).isPresent();
    }


    @Test
    void testTwoShouldFindSameIds() {
        ByIdResultSelector byIdResultSelector = new ByIdResultSelector("1");
        List<ResultAction> resAct = new ArrayList<>();
        ResultAction resultAction1 = mock(ResultAction.class);
        when(resultAction1.getId()).thenReturn("2");
        resAct.add(resultAction1);
        ResultAction resultAction2 = mock(ResultAction.class);
        when(resultAction2.getId()).thenReturn("1");
        resAct.add(resultAction2);
        Run<?, ?> run = mock(Run.class);
        when(run.getActions(ResultAction.class)).thenReturn(resAct);

        assertThat(byIdResultSelector.get(run)).isPresent();
    }

    @Test
    void testToString() {
        ByIdResultSelector byIdResultSelector = new ByIdResultSelector("1");

        assertThat(byIdResultSelector.toString()).contains(ResultAction.class.getName());
        assertThat(byIdResultSelector.toString()).contains("1");
    }

}