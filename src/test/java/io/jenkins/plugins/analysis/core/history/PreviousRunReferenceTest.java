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

class PreviousRunReferenceTest extends ReferenceFinderTest {

    @Override
    ReferenceFinder getReferenceFinder(final Run baseline, final ResultSelector resultSelector) {
        return new PreviousRunReference(baseline, resultSelector, true);
    }

    /* Test that there are no previous actions and stableRun is not build yet, with overallResultMusBeSuccess = false */
    @Test
    void notBuild_withAnyResult_shouldReturnNothing() {

        Run<?, ?> baseline = mock(Run.class);
        ResultSelector selector = mock(ResultSelector.class);
        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        Optional<ResultAction> resultAction = previousRun.getReferenceAction();

        assertThat(resultAction).isEmpty();
    }

    /* Test that there are no previous action and stableRun is not build yet, with overallResultMusBeSuccess = true*/
    @Test
    void notBuild_withSuccessful_shouldReturnNothing() {

        Run<?, ?> baseline = mock(Run.class);
        ResultSelector selector = mock(ResultSelector.class);
        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);

        Optional<ResultAction> resultAction = previousRun.getReferenceAction();

        assertThat(resultAction).isEmpty();
    }


    /* Test that there are no previous action, with overallResultMusBeSuccess = true*/
    @Test
    void noActions_withSuccessful_shouldReturnNothing() {

        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.empty());

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);

        Optional<ResultAction> resultAction = previousRun.getReferenceAction();

        assertThat(resultAction).isEmpty();
    }

    /* Test that there are no previous action, with overallResultMusBeSuccess = false*/
    @Test
    void noActions_withAnyResult_shouldReturnNothing() {

        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.empty());

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        Optional<ResultAction> resultAction = previousRun.getReferenceAction();

        assertThat(resultAction).isEmpty();
    }


    /* Test that there is one unsuccesful previous action, with overallResultMusBeSuccess = true */
    @Test
    void oneFalseAction_withSuccessful_shouldReturnNothing() {

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

        PreviousRunReference stablePluginReference = new PreviousRunReference(baseline, resultSelector,
                true);

        Optional<ResultAction> resultAction = stablePluginReference.getReferenceAction();

        verify(resultSelector, times(1)).get(prevRun);
        assertThat(resultAction).isEmpty();
    }

    /* Test that there is one unsuccesful previous action, with overallResultMusBeSuccess = false */
    @Test
    void oneFalseAction_withAnyResult_shouldReturnOne() {
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

        Optional<ResultAction> resultAction = previousRun.getReferenceAction();

        verify(selector, times(2)).get(previousRunMock);
        assertThat(resultAction).isEqualTo(Optional.of(action));
    }

    /* Test that there is one succesful previous action, with overallResultMusBeSuccess = true */
    @Test
    void oneTrueAction_withSuccessful_shouldReturnOne() {
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

        Optional<ResultAction> resultAction = previousRun.getReferenceAction();

        verify(selector, times(2)).get(previousRunMock);
        assertThat(resultAction).isEqualTo(Optional.of(action));
    }

    /* Test that there is one unstable previous action, with overallResultMusBeSuccess = false */
    @Test
    void oneUnstableAction_withAnyResult_shouldReturnOne() {
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

        Optional<ResultAction> resultAction = previousRun.getReferenceAction();

        verify(selector, times(2)).get(previousRunMock);
        assertThat(resultAction).isEqualTo(Optional.of(action));
    }

    /*  Test that there is one unstable previous action, with overallResultMusBeSuccess = true */
    @Test
    void oneUnstableAction_withSuccessful_shouldReturnNothing() {
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

        Optional<ResultAction> resultAction = previousRun.getReferenceAction();

        verify(selector, times(1)).get(previousRunMock);
        assertThat(resultAction).isEmpty();
    }

    /* Test that there are two succesful previous action, with overallResultMusBeSuccess = false */
    @Test
    void twoTrueAction_withAnyResult_shouldReturnTwo() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);
        when(previousRunMock.getResult()).thenReturn(Result.SUCCESS, Result.SUCCESS);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);
        when(analysis.getOverallResult()).thenReturn(Result.SUCCESS, Result.SUCCESS);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        Optional<ResultAction> resultAction1 = previousRun.getReferenceAction();
        Optional<ResultAction> resultAction2 = previousRun.getReferenceAction();

        verify(selector, times(4)).get(previousRunMock);
        assertThat(resultAction1).isEqualTo(Optional.of(action));
        assertThat(resultAction2).isEqualTo(Optional.of(action));
    }


    /* Test that there are two previous action, with overallResultMusBeSuccess = false */
    @Test
    void twoAction_withAnyResult_shouldReturnTwo() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);
        when(previousRunMock.getResult()).thenReturn(Result.FAILURE, Result.SUCCESS);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);
        when(analysis.getOverallResult()).thenReturn(Result.FAILURE, Result.SUCCESS);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        Optional<ResultAction> resultAction1 = previousRun.getReferenceAction();
        Optional<ResultAction> resultAction2 = previousRun.getReferenceAction();

        verify(selector, times(4)).get(previousRunMock);
        assertThat(resultAction1).isEqualTo(Optional.of(action));
        assertThat(resultAction2).isEqualTo(Optional.of(action));
    }

    /* Test that there are two previous action (FAILURE and SUCCESS), with overallResultMusBeSuccess = true */
    @Test
    void twoAction_withSuccessful_shouldReturnOne() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);
        when(previousRunMock.getResult()).thenReturn(Result.FAILURE, Result.SUCCESS);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);
        when(analysis.getOverallResult()).thenReturn(Result.FAILURE, Result.SUCCESS);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);

        Optional<ResultAction> resultAction1 = previousRun.getReferenceAction();
        Optional<ResultAction> resultAction2 = previousRun.getReferenceAction();

        verify(selector, times(3)).get(previousRunMock);
        assertThat(resultAction1).isEmpty();
        assertThat(resultAction2).isEqualTo(Optional.of(action));
    }

    /* Test that there are two previous action (FAILURE and SUCCESS), with overallResultMusBeSuccess = true */
    @Test
    void twoFalseAction_withSuccessful_shouldReturnNothing() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);
        when(previousRunMock.getResult()).thenReturn(Result.FAILURE, Result.FAILURE);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);
        when(analysis.getOverallResult()).thenReturn(Result.FAILURE, Result.FAILURE);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);

        Optional<ResultAction> resultAction1 = previousRun.getReferenceAction();
        Optional<ResultAction> resultAction2 = previousRun.getReferenceAction();

        verify(selector, times(2)).get(previousRunMock);
        assertThat(resultAction1).isEmpty();
        assertThat(resultAction2).isEmpty();
    }

    /* Test that there are 5 previous action (FAILURE and SUCCESS), with overallResultMusBeSuccess = true */
    @Test
    void allTypesOfAction_withSuccessful_shouldReturnOne() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);
        when(previousRunMock.getResult()).thenReturn(Result.FAILURE, Result.UNSTABLE, Result.SUCCESS, Result.NOT_BUILT, Result.ABORTED);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);
        when(analysis.getOverallResult()).thenReturn(Result.FAILURE, Result.UNSTABLE, Result.SUCCESS, Result.NOT_BUILT, Result.ABORTED);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);

        Optional<ResultAction> resultActionFAILURE = previousRun.getReferenceAction();
        Optional<ResultAction> resultActionUNSTABLE = previousRun.getReferenceAction();
        Optional<ResultAction> resultActionSUCCESS = previousRun.getReferenceAction();
        Optional<ResultAction> resultActionNOT_BUILT = previousRun.getReferenceAction();
        Optional<ResultAction> resultActionABORTED = previousRun.getReferenceAction();

        verify(selector, times(6)).get(previousRunMock);
        assertThat(resultActionFAILURE).isEmpty();
        assertThat(resultActionUNSTABLE).isEmpty();
        assertThat(resultActionSUCCESS).isEqualTo(Optional.of(action));
        assertThat(resultActionNOT_BUILT).isEmpty();
        assertThat(resultActionABORTED).isEmpty();
    }

    /* Test that there are 5 previous action (FAILURE and SUCCESS), with overallResultMusBeSuccess = false */
    @Test
    void allTypesOfAction_withAnyResult_shouldReturnThree() {
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);
        when(previousRunMock.getResult()).thenReturn(Result.FAILURE, Result.UNSTABLE, Result.SUCCESS, Result.NOT_BUILT, Result.ABORTED);

        ResultAction action = mock(ResultAction.class);
        when(action.isSuccessful()).thenReturn(true);

        AnalysisResult analysis = mock(AnalysisResult.class);
        when(action.getResult()).thenReturn(analysis);
        when(analysis.getOverallResult()).thenReturn(Result.FAILURE, Result.UNSTABLE, Result.SUCCESS, Result.NOT_BUILT, Result.ABORTED);

        ResultSelector selector = mock(ResultSelector.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        List<ResultAction> actions = Collections.singletonList(action);
        when(previousRunMock.getActions(ResultAction.class)).thenReturn(actions);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        Optional<ResultAction> resultActionFAILURE = previousRun.getReferenceAction();
        Optional<ResultAction> resultActionUNSTABLE = previousRun.getReferenceAction();
        Optional<ResultAction> resultActionSUCCESS = previousRun.getReferenceAction();
        Optional<ResultAction> resultActionNOT_BUILT = previousRun.getReferenceAction();
        Optional<ResultAction> resultActionABORTED = previousRun.getReferenceAction();

        verify(selector, times(8)).get(previousRunMock);
        assertThat(resultActionFAILURE).isEqualTo(Optional.of(action));
        assertThat(resultActionUNSTABLE).isEqualTo(Optional.of(action));
        assertThat(resultActionSUCCESS).isEqualTo(Optional.of(action));
        assertThat(resultActionNOT_BUILT).isEmpty();
        assertThat(resultActionABORTED).isEmpty();
    }

    /* Test that there is one previous action with result null, with overallResultMusBeSuccess = false */
    @Test
    void oneNullAction_withAnyResult_shouldReturnNothing(){
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);

        ResultSelector selector = mock(ResultSelector.class);

        ResultAction action = mock(ResultAction.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        when(previousRunMock.getResult()).thenReturn(null);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                false);

        Optional<ResultAction> resultAction = previousRun.getReferenceAction();

        assertThat(resultAction).isEmpty();
    }

    /* Test that there is one previous action with result null, with overallResultMusBeSuccess = true */
    @Test
    void oneNullAction_withSuccessful_shouldReturnNothing(){
        Run baseline = mock(Run.class);
        Run previousRunMock = mock(Run.class);
        when(baseline.getPreviousBuild()).thenReturn(previousRunMock);

        ResultSelector selector = mock(ResultSelector.class);

        ResultAction action = mock(ResultAction.class);
        when(selector.get(previousRunMock)).thenReturn(Optional.of(action));

        when(previousRunMock.getResult()).thenReturn(null);

        PreviousRunReference previousRun = new PreviousRunReference(baseline, selector,
                true);

        Optional<ResultAction> resultAction = previousRun.getReferenceAction();

        assertThat(resultAction).isEmpty();
    }

}