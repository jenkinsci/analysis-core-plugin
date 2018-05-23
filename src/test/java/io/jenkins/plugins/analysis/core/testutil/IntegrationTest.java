package io.jenkins.plugins.analysis.core.testutil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.junit.Rule;
import org.junit.jupiter.api.Tag;
import org.jvnet.hudson.test.JenkinsRule;

import static edu.hm.hafner.analysis.assertj.Assertions.*;
import edu.hm.hafner.util.ResourceTest;
import io.jenkins.plugins.analysis.core.model.StaticAnalysisTool;

import hudson.FilePath;
import hudson.model.Descriptor;
import hudson.model.TopLevelItem;

/**
 * Base class for integration tests in Jenkins.
 *
 * @author Ullrich Hafner
 */
@Tag("IntegrationTest")
public abstract class IntegrationTest extends ResourceTest {

    /**
     * These are the module files that are necessary for the integration test of {@link
     * edu.hm.hafner.analysis.ModuleDetector}}. A short description of the purpose of every file should be given here.
     *
     * <b>Maven:</b>
     * pom.xml a default pom.xml with a valid name tag
     * <p>
     * m1/pom.xml a default pom.xml with a valid name tag which could be used to detect additional modules in addition
     * to the previous mentioned pom.xml
     * <p>
     * m2/pom.xml a default pom.xml with a valid name tag which could be used to detect additional modules in addition
     * to the first mentioned pom.xml
     * <p>
     * m3/pom.xml a broken XML-structure breaks the correct parsing of this file
     * <p>
     * m4/pom.xml a pom.xml with a substitutional artifactId tag and without a name tag
     * <p>
     * m5/pom.xml a pom.xml without a substitutional artifactId tag and without a name tag
     *
     * <b>Ant:</b>
     * build.xml a default build.xml with a valid name tag
     * <p>
     * m1/build.xml a default build.xml with a valid name tag which could be used to detect additional modules in
     * addition to the previous mentioned build.xml
     * <p>
     * m2/build.xml a broken XML-structure breaks the correct parsing of this file
     * <p>
     * m3/build.xml a build file without the name tag
     *
     * <b>OSGI:</b>
     * META-INF/MANIFEST.MF a default MANIFEST.MF with a set Bundle-SymbolicName and a set Bundle-Vendor
     * <p>
     * m1/META-INF/MANIFEST.MF a MANIFEST.MF with a wildcard Bundle-Name, a set Bundle-SymbolicName and a wildcard
     * Bundle-Vendor
     * <p>
     * m2/META-INF/MANIFEST.MF a MANIFEST.MF with a set Bundle-Name and a wildcard Bundle-Vendor
     * <p>
     * m3/META-INF/MANIFEST.MF an empty MANIFEST.MF
     * <p>
     * plugin.properties a default plugin.properties file
     */
    private static final String[] MODULE_FILE_NAMES_TO_KEEP = new String[]{
            "m1/pom.xml", "m2/pom.xml", "m3/pom.xml", "m4/pom.xml", "m5/pom.xml", "pom.xml",
            "m1/build.xml", "m2/build.xml", "m3/build.xml", "build.xml",
            "m1/META-INF/MANIFEST.MF", "m2/META-INF/MANIFEST.MF", "m3/META-INF/MANIFEST.MF", "META-INF/MANIFEST.MF", "plugin.properties"
    };

    private static final String[] GENERIC_FILE_NAMES_TO_KEEP = new String[]{
            ".cs", ".java", ".zip", ".tar", ".gz"
    };

    /** Starts Jenkins and provides several useful helper methods. */
    @Rule
    public final JenkinsRule j = new JenkinsRule();

    /**
     * Copies the specified files to the workspace using a generated file name.
     *
     * @param job
     *         the job to get the workspace for
     * @param fileNames
     *         the files to copy
     */
    protected void copyFilesToWorkspace(final TopLevelItem job, final String... fileNames) {
        try {
            FilePath workspace = j.jenkins.getWorkspaceFor(job);
            assertThat(workspace).isNotNull();
            for (String fileName : fileNames) {
                workspace.child(createWorkspaceFileName(fileName)).copyFrom(asInputStream(fileName));
            }
        }
        catch (IOException | InterruptedException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Creates a pre-defined filename for a workspace file.
     *
     * @param fileNamePrefix
     *         prefix of the filename
     */
    protected String createWorkspaceFileName(final String fileNamePrefix) {
        String modifiedFileName = String.format("%s-issues.txt", FilenameUtils.getBaseName(fileNamePrefix));

        String fileNamePrefixInModuleList = Arrays.stream(MODULE_FILE_NAMES_TO_KEEP)
                .filter(fileNamePrefix::endsWith)
                .findFirst()
                .orElse("");

        if ("".equals(fileNamePrefixInModuleList)) {
            List<Boolean> fileNamePrefixInList = Arrays.stream(GENERIC_FILE_NAMES_TO_KEEP)
                    .map(fileNamePrefix::endsWith)
                    .collect(Collectors.toList());
            return fileNamePrefixInList.contains(true) ? FilenameUtils.getName(fileNamePrefix) : modifiedFileName;
        }
        return fileNamePrefixInModuleList;
    }

    /**
     * Returns the ID of a static analysis tool that is given by its class file. Uses the associated descriptor to
     * obtain the ID.
     *
     * @param tool
     *         the class of the tool to get the ID from
     */
    protected String getIdOf(final Class<? extends StaticAnalysisTool> tool) {
        Descriptor<?> descriptor = j.jenkins.getDescriptor(tool);
        assertThat(descriptor).as("Descriptor for '%s' not found").isNotNull();
        return descriptor.getId();
    }
}
