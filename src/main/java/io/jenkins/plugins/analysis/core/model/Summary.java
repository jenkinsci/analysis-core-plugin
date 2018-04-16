package io.jenkins.plugins.analysis.core.model;

import java.util.stream.Collectors;

import edu.hm.hafner.util.VisibleForTesting;
import io.jenkins.plugins.analysis.core.views.ResultAction;
import static j2html.TagCreator.*;
import j2html.tags.ContainerTag;

/**
 * Summary message of a static analysis run. This message is shown as part of the 'summary.jelly' information of the
 * associated {@link ResultAction}.
 * <pre>
 *     Tool Name: %d issues from %d analyses
 *        - Results from analyses {%s, ... %s}
 *        - %d new issues (since build %d)
 *        - %d outstanding issues
 *        - %d fixed issues
 *        - No issues since build %d
 *        - Quality gates: passed (Reference build %d)
 * </pre>
 *
 * @author Ullrich Hafner
 */
// FIXME: is the number of parsed reports available yet?
public class Summary {
    private final StaticAnalysisLabelProvider labelProvider;
    private final AnalysisResult analysisRun;
    private final LabelProviderFactoryFacade facade;

    public Summary(final StaticAnalysisLabelProvider labelProvider, final AnalysisResult analysisRun) {
        this(labelProvider, analysisRun, new LabelProviderFactoryFacade());
    }

    @VisibleForTesting
    Summary(final StaticAnalysisLabelProvider labelProvider, final AnalysisResult analysisRun,
            final LabelProviderFactoryFacade facade) {
        this.labelProvider = labelProvider;
        this.analysisRun = analysisRun;
        this.facade = facade;
    }

    /**
     * Creates the summary as HTML string.
     *
     * @return the summary
     */
    public String create() {
        return div(labelProvider.getTitle(analysisRun, !analysisRun.getErrorMessages().isEmpty()), createDescription())
                .withId(labelProvider.getId() + "-summary")
                .renderFormatted();
    }

    private ContainerTag createDescription() {
        int currentBuild = analysisRun.getBuild().getNumber();
        ContainerTag ul = ul()
                .condWith(analysisRun.getSizePerOrigin().size() > 1,
                        li(getToolNames()))
                .condWith(analysisRun.getTotalSize() == 0
                                && currentBuild > analysisRun.getNoIssuesSinceBuild(),
                        li(labelProvider.getNoIssuesSinceLabel(currentBuild, analysisRun.getNoIssuesSinceBuild())))
                .condWith(analysisRun.getNewSize() > 0,
                        li(labelProvider.getNewIssuesLabel(analysisRun.getNewSize())))
                .condWith(analysisRun.getFixedSize() > 0,
                        li(labelProvider.getFixedIssuesLabel(analysisRun.getFixedSize())))
                .condWith(analysisRun.getQualityGate().isEnabled(),
                        li(labelProvider.getQualityGateResult(analysisRun.getOverallResult())));
        return analysisRun.getReferenceBuild()
                .map(reference -> ul.with(li(labelProvider.getReferenceBuild(reference))))
                .orElse(ul);
    }

    private String getToolNames() {
        String tools = analysisRun.getSizePerOrigin()
                .keySet()
                .stream()
                .map(id -> facade.get(id).getName())
                .collect(Collectors.joining(", "));
        return Messages.Tool_ParticipatingTools(tools);
    }

    static class LabelProviderFactoryFacade {
        public StaticAnalysisLabelProvider get(final String id) {
            return new LabelProviderFactory().create(id);
        }
    }
}
