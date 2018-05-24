package io.jenkins.plugins.analysis.core.history;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import io.jenkins.plugins.analysis.core.model.AnalysisResult;
import static io.jenkins.plugins.analysis.core.testutil.Assertions.assertThat;
import io.jenkins.plugins.analysis.core.views.ResultAction;
import static org.mockito.Mockito.*;

import hudson.model.Result;
import hudson.model.Run;

/**
 * PreviousRunReferenceTest to test PreviousRunReference.
 *
 * @author Martin Weibel
 */
class PreviousRunReferenceTest extends ReferenceFinderTest {

    /**
     * getReferenceFinder.
     */
    @Override
    ReferenceFinder getReferenceFinder(final Run baseline, final ResultSelector resultSelector) {
        return new PreviousRunReference(baseline, resultSelector, true);
    }

    /**
     * Test that there are no previous actions and stableRun is not build yet, with overallResultMusBeSuccess = false.
     */
    @Test
    void notBuildWithAnyResultShouldReturnNothing() {

        Run<?, ?> baseline = mock(Run.class);
        ResultSelector selector = mock(ResultSelector.class);
        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        assertThat(previousRun.getReferenceAction()).isEmpty();
    }

    /**
     * Test that there are no previous action and stableRun is not build yet, with overallResultMusBeSuccess = true.
     */
    @Test
    void notBuildWithSuccessfulShouldReturnNothing() {

        Run<?, ?> baseline = mock(Run.class);
        ResultSelector selector = mock(ResultSelector.class);
        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);

        assertThat(previousRun.getReferenceAction()).isEmpty();
    }

    /**
     * Test that there are no previous action, with overallResultMusBeSuccess = true.
     */
    @Test
    void noActionsWithSuccessfulShouldReturnNothing() {

        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.empty());

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);

        assertThat(previousRun.getReferenceAction()).isEmpty();
    }

    /**
     * Test that there are no previous action, with overallResultMusBeSuccess = false.
     */
    @Test
    void noActionsWithAnyResultShouldReturnNothing() {

        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.empty());

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        assertThat(previousRun.getReferenceAction()).isEmpty();
    }

    /**
     * Test that there is one unsuccesful previous action, with overallResultMusBeSuccess = true.
     */
    @Test
    void oneFalseActionWithSuccessfulShouldReturnNothing() {

        Run baseline = mock(Run.class);
        Run prevRun = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(prevRun);
        when(prevRun.getResult()).thenReturn(Result.FAILURE);

        ResultSelector resultSelector = mock(ResultSelector.class);

        ResultAction action = mock(ResultAction.class);
        when(resultSelector.get(prevRun)).thenReturn(Optional.of(action));
        when(prevRun.getActions(ResultAction.class)).thenReturn(Collections.singletonList(action));
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysisResult = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysisResult);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, resultSelector,
                true);

        assertThat(previousRun.getReferenceAction()).isEmpty();
        verify(resultSelector, times(1)).get(prevRun);
    }

    /**
     * Test that there is one unsuccesful previous action, with overallResultMusBeSuccess = false.
     */
    @Test
    void oneFalseActionWithAnyResultShouldReturnOne() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);
        when(previousRunMock.getResult()).thenReturn(Result.FAILURE);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);
        when(analysis.getOverallResult()).thenReturn(Result.FAILURE);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        assertThat(previousRun.getReferenceAction()).contains(action);
        verify(selector, times(2)).get(previousRunMock);
    }

    /**
     * Test that there is one succesful previous action, with overallResultMusBeSuccess = true.
     */
    @Test
    void oneTrueActionWithSuccessfulShouldReturnOne() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);
        when(previousRunMock.getResult()).thenReturn(Result.SUCCESS);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);
        when(analysis.getOverallResult()).thenReturn(Result.SUCCESS);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);

        assertThat(previousRun.getReferenceAction()).contains(action);
        verify(selector, times(2)).get(previousRunMock);
    }

    /**
     * Test that there is one unstable previous action, with overallResultMusBeSuccess = false.
     */
    @Test
    void oneUnstableActionWithAnyResultShouldReturnOne() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);
        when(previousRunMock.getResult()).thenReturn(Result.UNSTABLE);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);
        when(analysis.getOverallResult()).thenReturn(Result.UNSTABLE);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        assertThat(previousRun.getReferenceAction()).contains(action);
        verify(selector, times(2)).get(previousRunMock);
    }

    /**
     * Test that there is one unstable previous action, with overallResultMusBeSuccess = true.
     */
    @Test
    void oneUnstableActionWithSuccessfulShouldReturnNothing() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);
        when(previousRunMock.getResult()).thenReturn(Result.UNSTABLE);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);
        when(analysis.getOverallResult()).thenReturn(Result.UNSTABLE);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);

        assertThat(previousRun.getReferenceAction()).isEmpty();
        verify(selector, times(1)).get(previousRunMock);
    }

    /**
     * Test that there are two succesful previous action, with overallResultMusBeSuccess = false.
     */
    @Test
    void twoTrueActionWithAnyResultShouldReturnOne() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);
        when(previousRunMock.getResult()).thenReturn(Result.SUCCESS);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);
        when(analysis.getOverallResult()).thenReturn(Result.SUCCESS);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        assertThat(previousRun.getReferenceAction()).contains(action);
        verify(selector, times(2)).get(previousRunMock);
    }

    /**
     * Test that there are two previous action, with overallResultMusBeSuccess = false.
     */
    @Test
    void twoActionWithAnyResultShouldReturnTwo() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        when(previousRunMock.getResult()).thenReturn(Result.FAILURE);
        when(analysis.getOverallResult()).thenReturn(Result.FAILURE);
        assertThat(previousRun.getReferenceAction()).contains(action);

        when(previousRunMock.getResult()).thenReturn(Result.SUCCESS);
        when(analysis.getOverallResult()).thenReturn(Result.SUCCESS);
        assertThat(previousRun.getReferenceAction()).contains(action);

        verify(selector, times(4)).get(previousRunMock);
    }

    /**
     * Test that there are two previous action (FAILURE and SUCCESS), with overallResultMusBeSuccess = true.
     */
    @Test
    void twoActionWithSuccessfulShouldReturnOne() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);

        when(previousRunMock.getResult()).thenReturn(Result.FAILURE);
        when(analysis.getOverallResult()).thenReturn(Result.FAILURE);
        assertThat(previousRun.getReferenceAction()).isEmpty();

        when(previousRunMock.getResult()).thenReturn(Result.SUCCESS);
        when(analysis.getOverallResult()).thenReturn(Result.SUCCESS);
        assertThat(previousRun.getReferenceAction()).contains(action);

        verify(selector, times(3)).get(previousRunMock);
    }

    /**
     * Test that there are two previous action (FAILURE and SUCCESS), with overallResultMusBeSuccess = true.
     */
    @Test
    void twoFalseActionWithSuccessfulShouldReturnNothing() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);

        when(previousRunMock.getResult()).thenReturn(Result.FAILURE);
        when(analysis.getOverallResult()).thenReturn(Result.FAILURE);
        assertThat(previousRun.getReferenceAction()).isEmpty();

        verify(selector, times(1)).get(previousRunMock);
    }

    /**
     * Test that there are 5 previous action (FAILURE, UNSTABLE, SUCCESS, NOT_BUILT and ABORTED), with overallResultMusBeSuccess = true.
     */
    @Test
    void allTypesOfActionWithSuccessfulShouldReturnOne() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);


        when(previousRunMock.getResult()).thenReturn(Result.FAILURE);
        when(analysis.getOverallResult()).thenReturn(Result.FAILURE);
        assertThat(previousRun.getReferenceAction()).isEmpty();

        when(previousRunMock.getResult()).thenReturn(Result.UNSTABLE);
        when(analysis.getOverallResult()).thenReturn(Result.UNSTABLE);
        assertThat(previousRun.getReferenceAction()).isEmpty();

        when(previousRunMock.getResult()).thenReturn(Result.SUCCESS);
        when(analysis.getOverallResult()).thenReturn(Result.SUCCESS);
        assertThat(previousRun.getReferenceAction()).contains(action);

        when(previousRunMock.getResult()).thenReturn(Result.NOT_BUILT);
        when(analysis.getOverallResult()).thenReturn(Result.NOT_BUILT);
        assertThat(previousRun.getReferenceAction()).isEmpty();

        when(previousRunMock.getResult()).thenReturn(Result.ABORTED);
        when(analysis.getOverallResult()).thenReturn(Result.ABORTED);
        assertThat(previousRun.getReferenceAction()).isEmpty();

        verify(selector, times(6)).get(previousRunMock);
    }

    /**
     * Test that there are 5 previous action (FAILURE, UNSTABLE, SUCCESS, NOT_BUILT and ABORTED), with overallResultMusBeSuccess = false.
     */
    @Test
    void allTypesOfActionWithAnyResultShouldReturnThree() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        when(previousRunMock.getResult()).thenReturn(Result.FAILURE);
        when(analysis.getOverallResult()).thenReturn(Result.FAILURE);
        assertThat(previousRun.getReferenceAction()).isEqualTo(Optional.of(action));

        when(previousRunMock.getResult()).thenReturn(Result.UNSTABLE);
        when(analysis.getOverallResult()).thenReturn(Result.UNSTABLE);
        assertThat(previousRun.getReferenceAction()).isEqualTo(Optional.of(action));

        when(previousRunMock.getResult()).thenReturn(Result.SUCCESS);
        when(analysis.getOverallResult()).thenReturn(Result.SUCCESS);
        assertThat(previousRun.getReferenceAction()).isEqualTo(Optional.of(action));

        when(previousRunMock.getResult()).thenReturn(Result.NOT_BUILT);
        when(analysis.getOverallResult()).thenReturn(Result.NOT_BUILT);
        assertThat(previousRun.getReferenceAction()).isEqualTo(Optional.of(action));

        when(previousRunMock.getResult()).thenReturn(Result.ABORTED);
        when(analysis.getOverallResult()).thenReturn(Result.ABORTED);
        assertThat(previousRun.getReferenceAction()).isEqualTo(Optional.of(action));

        verify(selector, times(10)).get(previousRunMock);
    }

    /**
     * Test that there is one previous action with result null, with overallResultMusBeSuccess = false.
     */
    @Test
    void oneNullActionWithAnyResultShouldReturnNothing(){
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);

        ResultSelector selector = mock(ResultSelector.class);

        ResultAction action = mock(ResultAction.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        when(previousRunMock.getResult()).thenReturn(null);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        assertThat(previousRun.getReferenceAction()).isEmpty();
    }

    /**
     * Test that there is one previous action with result null, with overallResultMusBeSuccess = true.
     */
    @Test
    void oneNullActionWithSuccessfulShouldReturnNothing(){
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);

        ResultSelector selector = mock(ResultSelector.class);

        ResultAction action = mock(ResultAction.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        when(previousRunMock.getResult()).thenReturn(null);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);

        assertThat(previousRun.getReferenceAction()).isEmpty();
    }

}
