package io.jenkins.plugins.analysis.core.model;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.IssueBuilder;
import io.jenkins.plugins.analysis.core.JenkinsFacade;
import io.jenkins.plugins.analysis.core.model.Summary.LabelProviderFactoryFacade;
import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.QualityGate;
import io.jenkins.plugins.analysis.core.quality.Thresholds;
import static org.mockito.Mockito.*;

import hudson.model.Result;
import hudson.model.Run;

/**
 * Tests the class {@link Summary}.
 *
 * @author Ullrich Hafner
 * @author Deniz Mardin
 * @author Frank Christian Geyer
 */
class SummaryTest {

    private static final String TEST_ID = "test";
    private static final String TEST_NAME = "SummaryTest";
    private static final int DEFAULT_VALUE_CHECKSTYLE = 15;
    private static final int DEFAULT_VALUE_PMD = 20;

    /**
     * Initialize locale settings before the test starts.
     */
    @BeforeAll
    static void initializeLocale() {
        Locale.setDefault(Locale.ENGLISH);
    }

    /**
     * Verifies that the created Summary contains the expected values such as a summary without warnings and a valid
     * header.
     */
    @Test
    void provideSummaryWithNoWarningsAndCheckStyleAndPmdHeader() {
        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            AnalysisResult analysisRun = configureAnalysisRun(0, 0, 0, 0, 0, 0, 0, Result.SUCCESS, 0, false);
            provideSummaryWithNoWarningsAndCheckStyleAndPmdHeader(
                    new Summary(createLabelProvider(TEST_ID, TEST_NAME), analysisRun, getFacade()).create(), softly);
        }
    }

    /**
     * Verifies that the created Summary contains the expected values such as a summary with a new and fixed warning and
     * a failed result.
     */
    @Test
    void provideSummaryWithOneNewAndFixedWarningAndFailedResult() {
        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            AnalysisResult analysisRun = configureAnalysisRun(1, 1, 1, 1, 1, 1, 1, Result.FAILURE, 1, false);
            provideSummaryWithOneNewAndFixedWarningAndFailedResult(
                    new Summary(createLabelProvider(TEST_ID, TEST_NAME), analysisRun, getFacade()).create(), softly);
        }
    }

    /**
     * Verifies that the created Summary contains the expected values such as a summary with an optional error message.
     */
    @ParameterizedTest
    @ValueSource(strings = {"true", "false"})
    void shouldProvideSummaryWithCheckstyleAndPmdHeaderAndASetErrorMessage(final boolean isRunWithErrorMessage) {
        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            AnalysisResult analysisRun = configureAnalysisRun(DEFAULT_VALUE_CHECKSTYLE, DEFAULT_VALUE_PMD, 2, 2, 1, 0,
                    1,
                    Result.SUCCESS, 2, false);
            if (isRunWithErrorMessage) {
                addErrorMessageToAnalysisRun(analysisRun);
            }
            provideSummaryWithCheckstyleAndPmdHeader(
                    new Summary(createLabelProvider(TEST_ID, TEST_NAME), analysisRun, getFacade()).create(),
                    isRunWithErrorMessage, softly);
        }
    }

    /**
     * Verifies that the created Summary contains the expected values without an header.
     */
    @Test
    void shouldProvideSummaryWithoutCheckstyleAndPmdHeaderWithAnErrorMessage() {
        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            AnalysisResult analysisRun = configureAnalysisRun(DEFAULT_VALUE_CHECKSTYLE, DEFAULT_VALUE_PMD, 2, 2, 1, 0,
                    1,
                    Result.SUCCESS,
                    2, true);
            provideSummaryWithoutCheckstyleAndPmdHeader(
                    new Summary(createLabelProvider(TEST_ID, TEST_NAME), analysisRun, getFacade()).create(), softly);
        }
    }

    private void addErrorMessageToAnalysisRun(final AnalysisResult analysisRun) {
        IssueBuilder issueBuilder = mock(IssueBuilder.class);
        Issue issue = mock(Issue.class);
        when(issueBuilder.build()).thenReturn(issue);
        when(issue.getMessage()).thenReturn("Error Message");
        ArrayList<String> list = new ArrayList<>();
        list.add(issue.getMessage());
        when(analysisRun.getErrorMessages()).thenReturn(Lists.immutable.ofAll(list));
    }

    private void provideSummaryWithCheckstyleAndPmdHeader(final String actualSummary,
            final boolean isRunWithErrorMessage, final AutoCloseableSoftAssertions softly) {
        if (isRunWithErrorMessage) {
            softly.assertThat(actualSummary).contains("fa fa-exclamation-triangle");
        }
        else {
            softly.assertThat(actualSummary).contains("fa fa-info-circle");
        }
        softly.assertThat(actualSummary).contains("CheckStyle, PMD");
        furtherAssertForCorrectString(actualSummary, softly);
    }

    private void provideSummaryWithNoWarningsAndCheckStyleAndPmdHeader(final String actualSummary,
            final AutoCloseableSoftAssertions softly) {
        softly.assertThat(actualSummary).contains("CheckStyle, PMD");
        softly.assertThat(actualSummary).contains("No warnings");
        softly.assertThat(actualSummary).contains("Reference build: <a href=\"absoluteUrl\">Job #15</a>");
    }

    private void provideSummaryWithOneNewAndFixedWarningAndFailedResult(final String actualSummary,
            final AutoCloseableSoftAssertions softly) {
        softly.assertThat(actualSummary).contains("CheckStyle, PMD");
        softly.assertThat(actualSummary).containsPattern(
                createWarningsLink("<a href=\"testResult/new\">.*One new warning.*</a>"));
        softly.assertThat(actualSummary).containsPattern(
                createWarningsLink("<a href=\"testResult/fixed\">.*One fixed warning.*</a>"));
        softly.assertThat(actualSummary).contains(
                "Quality gate: <img src=\"color\" class=\"icon-red icon-lg\" alt=\"Failed\" title=\"Failed\"> Failed");
        softly.assertThat(actualSummary).contains("Reference build: <a href=\"absoluteUrl\">Job #15</a>");
    }

    private void provideSummaryWithoutCheckstyleAndPmdHeader(final String actualSummary,
            final AutoCloseableSoftAssertions softly) {
        furtherAssertForCorrectString(actualSummary, softly);
    }

    private void furtherAssertForCorrectString(final String actualSummary, final AutoCloseableSoftAssertions softly) {
        softly.assertThat(actualSummary).contains("No warnings for 2 builds");
        softly.assertThat(actualSummary).contains("since build <a href=\"../1\" class=\"model-link inside\">1</a>");
        softly.assertThat(actualSummary).containsPattern(
                createWarningsLink("<a href=\"testResult/new\">.*2 new warnings.*</a>"));
        softly.assertThat(actualSummary).containsPattern(
                createWarningsLink("<a href=\"testResult/fixed\">.*2 fixed warnings.*</a>"));
        softly.assertThat(actualSummary).contains(
                "Quality gate: <img src=\"color\" class=\"icon-blue icon-lg\" alt=\"Success\" title=\"Success\"> Success");
        softly.assertThat(actualSummary).contains("Reference build: <a href=\"absoluteUrl\">Job #15</a>");
    }

    private AnalysisResult configureAnalysisRun(final int valueCheckstyle, final int valuePMD, final int newSize,
            final int fixedSize, final int noIssuesSinceBuild, final int totalSize, final int unstableTotalAll,
            final Result result, final int numberOfBuild, final boolean isSizePerOriginNotGreaterOne) {

        AnalysisResult analysisRun = mock(AnalysisResult.class);
        if (isSizePerOriginNotGreaterOne) {
            when(analysisRun.getSizePerOrigin()).thenReturn(Maps.fixedSize.of());
        }
        else {
            when(analysisRun.getSizePerOrigin()).thenReturn(Maps.fixedSize.of("checkstyle", valueCheckstyle,
                    "pmd", valuePMD));
        }

        when(analysisRun.getNewSize()).thenReturn(newSize);
        when(analysisRun.getFixedSize()).thenReturn(fixedSize);
        when(analysisRun.getErrorMessages()).thenReturn(Lists.immutable.empty());
        when(analysisRun.getNoIssuesSinceBuild()).thenReturn(noIssuesSinceBuild);
        when(analysisRun.getTotalSize()).thenReturn(totalSize);

        Thresholds thresholds = new Thresholds();
        thresholds.unstableTotalAll = unstableTotalAll;
        when(analysisRun.getQualityGate()).thenReturn(new QualityGate(thresholds));
        when(analysisRun.getOverallResult()).thenReturn(result);
        Run<?, ?> run = mock(Run.class);
        when(run.getFullDisplayName()).thenReturn("Job #15");
        when(analysisRun.getReferenceBuild()).thenReturn(Optional.of(run));

        AnalysisBuild build = mock(AnalysisBuild.class);
        when(build.getNumber()).thenReturn(numberOfBuild);
        when(analysisRun.getBuild()).thenReturn(build);

        return analysisRun;
    }

    private LabelProviderFactoryFacade getFacade() {
        LabelProviderFactoryFacade facade = mock(LabelProviderFactoryFacade.class);
        StaticAnalysisLabelProvider checkStyleLabelProvider = createLabelProvider("checkstyle", "CheckStyle");
        when(facade.get("checkstyle")).thenReturn(checkStyleLabelProvider);
        StaticAnalysisLabelProvider pmdLabelProvider = createLabelProvider("pmd", "PMD");
        when(facade.get("pmd")).thenReturn(pmdLabelProvider);
        return facade;
    }

    private StaticAnalysisLabelProvider createLabelProvider(final String id, final String name) {
        JenkinsFacade jenkins = mock(JenkinsFacade.class);
        when(jenkins.getImagePath(any())).thenReturn("color");
        when(jenkins.getAbsoluteUrl(any())).thenReturn("absoluteUrl");
        return new StaticAnalysisLabelProvider(id, name, jenkins);
    }

    private Pattern createWarningsLink(final String href) {
        return Pattern.compile(href, Pattern.MULTILINE | Pattern.DOTALL);
    }
}
