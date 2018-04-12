package io.jenkins.plugins.analysis.core.steps;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import io.jenkins.plugins.analysis.core.JenkinsFacade;
import io.jenkins.plugins.analysis.core.steps.IssuesRecorder.Descriptor;
import static io.jenkins.plugins.analysis.core.steps.IssuesRecorder.NO_REFERENCE_JOB;
import static io.jenkins.plugins.analysis.core.testutil.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Job;
import hudson.plugins.analysis.util.model.Priority;
import hudson.util.ComboBoxModel;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import hudson.util.ListBoxModel.Option;

/**
 * Tests the class {@link IssuesRecorder}.
 *
 * @author Ullrich Hafner
 */
class IssuesRecorderTest {

    private final String validEncoding = "UTF-8";

    @Test
    void shouldBeOkIfEncodingIsEmpty() {
        Descriptor descriptor = new Descriptor();

        FormValidation actualResult = descriptor.doCheckSourceCodeEncoding("");

        assertThat(actualResult).isOk();
    }
    
    @Test
    void shouldContainEmptyJobPlaceHolder() {
        JenkinsFacade jenkins = mock(JenkinsFacade.class);
        Descriptor descriptor = new Descriptor(jenkins);

        ComboBoxModel actualModel = descriptor.doFillReferenceJobItems();

        assertThat(actualModel).hasSize(1);
        assertThat(actualModel).containsExactly(NO_REFERENCE_JOB);
    }

    @Test
    void shouldContainSingleElementAndPlaceHolder() {
        JenkinsFacade jenkins = mock(JenkinsFacade.class);
        Job job = mock(Job.class);
        String name = "Job Name";
        when(jenkins.getFullNameOf(job)).thenReturn(name);
        when(jenkins.getAllJobs()).thenReturn(Collections.singleton(name));

        Descriptor descriptor = new Descriptor(jenkins);

        ComboBoxModel actualModel = descriptor.doFillReferenceJobItems();

        assertThat(actualModel).hasSize(2);
        assertThat(actualModel).containsExactly("-", name);
    }


    /**
     * Verifies that the validation is ok if the specified source code encoding is a valid encoding
     */
    @Test
    void shouldBeOkForValidCharsets(){
        final Descriptor descriptor = new Descriptor();

        final FormValidation actualResult = descriptor.doCheckSourceCodeEncoding(validEncoding);

        assertThat(actualResult).isOk();
    }

    /**
     * Verifies that the validation is marked as an error if the specified source code encoding is invalid
     */
    @Test
    void shouldNotBeOkIfSpecifiedCharsetIsInvalid(){
        final String inValidEncoding = "encodingThatWillNeverExits";
        final Descriptor descriptor = new Descriptor();

        final FormValidation actualResult = descriptor.doCheckSourceCodeEncoding(inValidEncoding);

        assertThat(actualResult).isError();
        assertThat(actualResult).hasMessage("Encoding must be a supported encoding of the Java platform, see: <a href=\"https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html\">java.nio.charset.Charset</a>");
    }

    /**
     * Verifies that the getDisplayName is returning the right name
     */
    @Test
    void shouldReturnRightName(){
        final Descriptor descriptor = new Descriptor();

        final String actualName = descriptor.getDisplayName();

        assertThat(actualName).isEqualTo(Messages.ScanAndPublishIssues_DisplayName());
    }

    /**
     * Verifies that doFillReportEncodings returns valid encodings
     */
    @Test
    void doFillReportEncodingItemsShouldContainValidCharsets(){
        final Descriptor descriptor = new Descriptor();

        final ComboBoxModel actualModel = descriptor.doFillReportEncodingItems();

        assertThat(actualModel).contains(validEncoding);
    }

    /**
     * Verifies that the source and report encodings are equal
     */
    @Test
    void supportedSourceAndReportEncodingsShouldBeTheSame(){
        final Descriptor descriptor = new Descriptor();

        final ComboBoxModel actualSourceModel = descriptor.doFillSourceCodeEncodingItems();
        final ComboBoxModel actualReportModel = descriptor.doFillReportEncodingItems();

        assertThat(actualReportModel).isEqualTo(actualSourceModel);
    }

    /**
     * Verifies that the priority filter contains the expected priorities
     */
    @Test
    void priorityFilterShouldContainHighNormalAndLow(){
        final Descriptor descriptor = new Descriptor();

        final ListBoxModel listBoxModel = descriptor.doFillMinimumPriorityItems();
        final Option actualHighOption = listBoxModel.get(0);
        final Option actualNormalOption = listBoxModel.get(1);
        final Option actualLowOption = listBoxModel.get(2);

        assertThat(listBoxModel.size()).isEqualTo(3);
        assertThat(actualHighOption.value).isEqualTo(Priority.HIGH.name());
        assertThat(actualHighOption.name).isEqualTo(Messages.PriorityFilter_High());
        assertThat(actualNormalOption.value).isEqualTo(Priority.NORMAL.name());
        assertThat(actualNormalOption.name).isEqualTo(Messages.PriorityFilter_Normal());
        assertThat(actualLowOption.value).isEqualTo(Priority.LOW.name());
        assertThat(actualLowOption.name).isEqualTo(Messages.PriorityFilter_Low());
    }

    /**
     * Verifies that the healthy is marked as invalid if it is zero but the unhealthy is greater than zero
     */
    @Test
    void healthyShouldBeInvalidIfOnlyUnhealthyIsDefined(){
        final Descriptor descriptor = new Descriptor();
        final int healthy = 0;
        final int unhealthy = 1;

        final FormValidation actualResult = descriptor.doCheckHealthy(healthy, unhealthy);

        assertThat(actualResult).isError();
        assertThat(actualResult).hasMessage("If the &#039;unhealthy&#039; threshold is defined, then also the &#039;healthy&#039; threshold must be set.");
    }

    /**
     * Verifies that the unhealthy is marked as invalid if it is zero but the healthy is greater than zero
     */
    @Test
    void unhealthyShouldBeInvalidIfOnlyHealthyIsDefined(){
        final Descriptor descriptor = new Descriptor();
        final int healthy = 1;
        final int unhealthy = 0;

        final FormValidation actualResult = descriptor.doCheckUnHealthy(healthy, unhealthy);

        assertThat(actualResult).isError();
        assertThat(actualResult).hasMessage("If the &#039;healthy&#039; threshold is defined, then also the &#039;unhealthy&#039; threshold must be set.");
    }

    /**
     * Verifies that the healthy is marked as invalid if it is less than zero
     */
    @Test
    void healthyShouldNotBeAllowedToBecomeNegative(){
        final Descriptor descriptor = new Descriptor();
        final int healthy = -1;
        final int unhealthy = 0;

        final FormValidation actualResult = descriptor.doCheckHealthy(healthy, unhealthy);

        assertThat(actualResult).isError();
        assertThat(actualResult).hasMessage("Threshold must be an integer value greater or equal 0.");
    }

    /**
     * Verifies that the unhealthy is marked as invalid if it is less than zero
     */
    @Test
    void unhealthyShouldNotBeAllowedToBecomeNegative(){
        final Descriptor descriptor = new Descriptor();
        final int healthy = 0;
        final int unhealthy = -1;

        final FormValidation actualResult = descriptor.doCheckUnHealthy(healthy, unhealthy);

        assertThat(actualResult).isError();
        assertThat(actualResult).hasMessage("Threshold must be an integer value greater or equal 0.");
    }

    /**
     * Verifies that healthy and unhealty are ok if both are zero
     */
    @Test
    void shouldBeOkIfHealthyAndUnHealthyAreBothZero(){
        final Descriptor descriptor = new Descriptor();
        final int healthy = 0;
        final int unhealthy = 0;

        final FormValidation actualCheckHealthyResult = descriptor.doCheckHealthy(healthy, unhealthy);
        final FormValidation actualCheckUnhealthyResult = descriptor.doCheckUnHealthy(healthy, unhealthy);

        assertThat(actualCheckHealthyResult).isOk();
        assertThat(actualCheckUnhealthyResult).isOk();
    }

    /**
     * Verifies that healthy and unhealthy are ok if 0 < healthy < unhealthy
     */
    @Test
    void shouldBeOkIfHealthyIsLowerThanUnhealthy(){
        final Descriptor descriptor = new Descriptor();
        final int healthy = 1;
        final int unhealthy = 2;

        final FormValidation healthyValidation = descriptor.doCheckHealthy(healthy, unhealthy);
        final FormValidation unhealthyValidation = descriptor.doCheckUnHealthy(healthy, unhealthy);

        assertThat(healthyValidation).isOk();
        assertThat(unhealthyValidation).isOk();
    }

    /**
     * Verifies that healthy can't be greater or equal than unhealthy if both are greater than zero
     */
    @Test
    void shouldNotBeOkIfHealthyIsNotLowerThanUnhealthy(){
        final Descriptor descriptor = new Descriptor();
        final int healthy = 2;
        final int unhealthyEqual = healthy;
        final String expectedError = "Threshold &#039;healthy&#039; must be less than threshold &#039;unhealthy&#039;.";

        final FormValidation healthyValidationEqual = descriptor.doCheckHealthy(healthy, unhealthyEqual);
        final FormValidation unhealthyValidationEqual = descriptor.doCheckUnHealthy(healthy, unhealthyEqual);
        final FormValidation unhealthyValidationHealthyLessUnhealthy = descriptor.doCheckUnHealthy(healthy, unhealthyEqual -1);
        final FormValidation healthyValidationHealthyLessUnhealthy = descriptor.doCheckHealthy(healthy, unhealthyEqual - 1);

        assertThat(healthyValidationEqual).isError();
        assertThat(healthyValidationEqual).hasMessage(expectedError);
        assertThat(unhealthyValidationEqual).isError();
        assertThat(unhealthyValidationEqual).hasMessage(expectedError);
        assertThat(healthyValidationHealthyLessUnhealthy).isError();
        assertThat(healthyValidationHealthyLessUnhealthy).hasMessage(expectedError);
        assertThat(unhealthyValidationHealthyLessUnhealthy).isError();
        assertThat(unhealthyValidationHealthyLessUnhealthy).hasMessage(expectedError);
    }

    /**
     * Verifies that isApplicable is returning true if jobType is null
     */
    @Test
    void shouldReturnTrueIfJobTypeIsNull(){
        final Descriptor descriptor = new Descriptor();

        final boolean actualResult = descriptor.isApplicable(null);

        assertThat(actualResult).isEqualTo(true);
    }

    /**
     * Verifies that isApplicable is returning true if jobType is not null for AbstractProject classes
     */
    @Test
    void shouldReturnTrueIfJobTypeIsNonNull(){
        final Descriptor descriptor = new Descriptor();

        final boolean actualResult = descriptor.isApplicable(AbstractProject.class);

        assertThat(actualResult).isEqualTo(true);
    }

    /**
     * Verifies that the referenceJob is ok if the placeholder for NO_REFERENCE_JOB is selected
     */
    @Test
    void shouldBeOkIfReferenceJobIsNoReferenceJobPlaceholder(){
        final Descriptor descriptor = new Descriptor();

        final FormValidation actualResult = descriptor.doCheckReferenceJob(NO_REFERENCE_JOB);

        assertThat(actualResult).isOk();
    }

    /**
     * Verifies that the referenceJob is ok if it's an empty string
     */
    @Test
    void shouldBeOkIfReferenceJobIsAnEmptyString(){
        final Descriptor descriptor = new Descriptor();

        final FormValidation actualResult = descriptor.doCheckReferenceJob("");

        assertThat(actualResult).isOk();
    }

    /**
     * Verifies that the referenceJob is ok if it's null
     */
    @Test
    void shouldBeOkIfReferenceJobIsNull(){
        final Descriptor descriptor = new Descriptor();

        final FormValidation actualResult = descriptor.doCheckReferenceJob(null);

        assertThat(actualResult).isOk();
    }

    /**
     * Verifies that the referenceJob is ok if the job is present within jenkins
     */
    @Test
    void shouldBeOkIfReferenceJobIsPresent(){
        final String referenceJob = "testJob";
        final Job job = mock(Job.class);
        final Optional<Job<?,?>> optionalJob = Optional.of(job);
        final JenkinsFacade jenkinsFacade = mock(JenkinsFacade.class);
        when(jenkinsFacade.getJob(referenceJob)).thenReturn(optionalJob);
        final Descriptor descriptor = new Descriptor(jenkinsFacade);

        final FormValidation actualResult = descriptor.doCheckReferenceJob(referenceJob);

        assertThat(actualResult).isOk();
    }

    /**
     * Verifies that the referenceJob is not ok if the job is specified but not present
     */
    @Test
    void shouldNotBeOkIfJobIsNotPresent(){
        final String referenceJob = "testJob";
        final Optional<Job<?,?>> optionalJob = Optional.empty();
        final JenkinsFacade jenkinsFacade = mock(JenkinsFacade.class);
        when(jenkinsFacade.getJob(referenceJob)).thenReturn(optionalJob);
        final Descriptor descriptor = new Descriptor(jenkinsFacade);

        final FormValidation actualResult = descriptor.doCheckReferenceJob(referenceJob);

        assertThat(actualResult).isError();
        assertThat(actualResult).hasMessage("There is no such job - maybe the job has been renamed?");
    }
}