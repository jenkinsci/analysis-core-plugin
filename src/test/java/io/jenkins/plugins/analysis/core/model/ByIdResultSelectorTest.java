package io.jenkins.plugins.analysis.core.model;

import hudson.model.Run;
import io.jenkins.plugins.analysis.core.views.ResultAction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.jenkins.plugins.analysis.core.testutil.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Tests the class {@link ByIdResultSelector}
 *
 * @author Elvira Hauer
 */
public class ByIdResultSelectorTest {

	private final String anId = "1";


	@Test
	public void testWithEmptyResultActionList() {
		Run<?, ?> aRun = mock(Run.class);
		when(aRun.getActions(ResultAction.class)).thenReturn(Collections.emptyList());
		ByIdResultSelector byIdResultSelector = new ByIdResultSelector(anId);

		Optional<ResultAction> testResult = byIdResultSelector.get(aRun);

		assertThat(testResult).isEmpty();
	}

	@Test
	public void testWithOneRandomResultAction() {
		List<ResultAction> aResultActionList = new ArrayList<>();
		ResultAction aResultAction = mock(ResultAction.class);
		aResultActionList.add(aResultAction);
		Run<?, ?> aRun = mock(Run.class);
		when(aRun.getActions(ResultAction.class)).thenReturn(aResultActionList);
		ByIdResultSelector byIdResultSelector = new ByIdResultSelector(anId);

		Optional<ResultAction> testResult = byIdResultSelector.get(aRun);

		assertThat(testResult).isEmpty();
	}

	@Test
	public void testWithOneAcceptedResultAction() {
		List<ResultAction> aResultActionList = new ArrayList<>();
		ResultAction aResultAction = mock(ResultAction.class);
		when(aResultAction.getId()).thenReturn(anId);
		aResultActionList.add(aResultAction);
		Run<?, ?> aRun = mock(Run.class);
		when(aRun.getActions(ResultAction.class)).thenReturn(aResultActionList);
		ByIdResultSelector byIdResultSelector = new ByIdResultSelector(anId);

		Optional<ResultAction> testResult = byIdResultSelector.get(aRun);

		assertThat(testResult.isPresent()).isTrue();
	}

	@Test
	public void testWithOneManyRandomResultActions() {
		List<ResultAction> aResultActionList = new ArrayList<>();
		ResultAction aResultAction = mock(ResultAction.class);
		when(aResultAction.getId()).thenReturn("2");
		aResultActionList.add(aResultAction);
		ResultAction bResultAction = mock(ResultAction.class);
		when(bResultAction.getId()).thenReturn("3");
		aResultActionList.add(bResultAction);
		Run<?, ?> aRun = mock(Run.class);
		when(aRun.getActions(ResultAction.class)).thenReturn(aResultActionList);
		ByIdResultSelector byIdResultSelector = new ByIdResultSelector(anId);

		Optional<ResultAction> testResult = byIdResultSelector.get(aRun);

		assertThat(testResult).isEmpty();
	}

	@Test
	public void testWithOneManyRandomResultActionsAndOneAcceptetAtTheEnd() {
		List<ResultAction> aResultActionList = new ArrayList<>();
		ResultAction aResultAction = mock(ResultAction.class);
		when(aResultAction.getId()).thenReturn("2");
		aResultActionList.add(aResultAction);
		ResultAction bResultAction = mock(ResultAction.class);
		when(bResultAction.getId()).thenReturn(anId);
		aResultActionList.add(bResultAction);

		Run<?, ?> aRun = mock(Run.class);
		when(aRun.getActions(ResultAction.class)).thenReturn(aResultActionList);
		ByIdResultSelector byIdResultSelector = new ByIdResultSelector(anId);

		Optional<ResultAction> testResult = byIdResultSelector.get(aRun);

		assertThat(testResult.isPresent()).isTrue();
	}

	@Test
	public void testToString() {

		ByIdResultSelector byIdResultSelector = new ByIdResultSelector(anId);

		assertThat(byIdResultSelector.toString()).contains(ResultAction.class.getName());
		assertThat(byIdResultSelector.toString()).contains(anId);
	}
}
