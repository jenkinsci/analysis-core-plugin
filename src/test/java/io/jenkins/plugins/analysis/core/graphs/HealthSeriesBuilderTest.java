package io.jenkins.plugins.analysis.core.graphs;


import java.util.ArrayList;
import java.util.List;

import org.jfree.data.category.CategoryDataset;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import edu.hm.hafner.analysis.Priority;
import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link HealthSeriesBuilder} directly.
 * The base class {@link SeriesBuilder} is being tested indirectly.
 * @author Raphael Furch
 */
class HealthSeriesBuilderTest {



/*
 * Values     | if1 | if2 | if3 | test
 *  u == h    |  f  |  x  |  x  |  x
 * s > u > h  |  t  |  t  |  t  |  x
 * s == u > h |  t  |  t  |  f  |  x
 * u > h == s |  t  |  f  |  x  |  x
 */

  //<editor-fold desc="Consts">
  private final static int TOO_OLD_LIMIT = 8;
  private final static int BUILD_COUNT_LIMIT = 3;
  private final static long MILLISECONDS_OF_HOUR = 60 * 60 * 1000;
  private final static long MILLISECONDS_OF_MIN = 60 * 1000;
  private final static AnalysisBuild TOO_OLD_BUILD = stubAnalysisBuild(1, TOO_OLD_LIMIT+1,0,0);
  //</editor-fold>

  //<editor-fold desc="Day before Yesterday">
  private final static AnalysisBuild BUILD_FROM_THREE_DAYS_AGO_MIDNIGHT = stubAnalysisBuild(2, 3,0,0);
  private final static AnalysisBuild BUILD_FROM_THREE_DAYS_AGO_MIDDAY = stubAnalysisBuild(3, 3,12,0);
  private final static AnalysisBuild BUILD_FROM_THREE_DAYS_AGO_NIGHT = stubAnalysisBuild(4, 3,23,59);
  //</editor-fold>
  //<editor-fold desc="Day before Yesterday">
  private final static AnalysisBuild BUILD_FROM_TWO_DAYS_AGO_MIDNIGHT = stubAnalysisBuild(5, 2,0,0 );
  private final static AnalysisBuild BUILD_FROM_TWO_DAYS_AGO_MIDDAY= stubAnalysisBuild(6, 2,12,0);
  private final static AnalysisBuild BUILD_FROM_TWO_DAYS_AGO_AFTERNOON = stubAnalysisBuild(7, 2,15,0);
  private final static AnalysisBuild BUILD_FROM_TWO_DAYS_AGO_NIGHT = stubAnalysisBuild(8, 2,23,59);
  //</editor-fold>
  //<editor-fold desc="Yesterday">
  private final static AnalysisBuild BUILD_FROM_ONE_DAY_AGO_MIDNIGHT = stubAnalysisBuild(9, 1,0,0);
  private final static AnalysisBuild BUILD_FROM_ONE_DAY_AGO_MORNING = stubAnalysisBuild(11, 1,6,0);
  private final static AnalysisBuild BUILD_FROM_ONE_DAY_AGO_MIDDAY = stubAnalysisBuild(12, 1,12,0);
  private final static AnalysisBuild BUILD_FROM_ONE_DAY_AGO_AFTERNOON = stubAnalysisBuild(13, 1,15,0);
  private final static AnalysisBuild BUILD_FROM_ONE_DAY_AGO_NIGHT = stubAnalysisBuild(14, 1,23,59);
  //</editor-fold>






  //<editor-fold desc="useBuildDateAsDomain=false">
  @Test
  void createEmptyDataSetUseBuildDateNotAsDomain(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .createCategoryDataSet();
    assertSize(result, 0,0);
  }
  @Test
  void dataSetPerBuildWithOneBuild(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .setHealth(1)
            .setUnhealthy(1)
            .setResultTime(null) // switch time off.
            .isDayCountDefined(true)
            .addAnalysisRun(42, BUILD_FROM_ONE_DAY_AGO_MIDNIGHT)
            .createCategoryDataSet();

    assertSize(result, 1,1);
    assertValuesInVerticalOrder(result, 42);
    assertColumnKeysAsNumberOnlyBuildLabel(result, BUILD_FROM_ONE_DAY_AGO_MIDNIGHT);
  }
  @Test
  void dataSetWithTwoBuildFromSameDay(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .isDayCountDefined(true)
            .addAnalysisRun(1, BUILD_FROM_ONE_DAY_AGO_MIDDAY)
            .addAnalysisRun(6, BUILD_FROM_ONE_DAY_AGO_AFTERNOON)
            .createCategoryDataSet();

    assertSize(result, 2,3);
    assertValuesInVerticalOrder(result, 1, 0, 0, 1,4,1);
    assertColumnKeysAsNumberOnlyBuildLabel(result, BUILD_FROM_ONE_DAY_AGO_MIDDAY, BUILD_FROM_ONE_DAY_AGO_AFTERNOON);
  }
  @Test
  void dataSetPerBuildWithOneTooOldBuild(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .isDayCountDefined(true)
            .addAnalysisRun(8, TOO_OLD_BUILD)
            .createCategoryDataSet();

    assertSize(result, 0,0);
  }
  @Test
  void dataSetPerBuildWithThreeBuildsOrderedByAgeAndLasTwotIsTooOld(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .isDayCountDefined(true)
            .setDayCountLimit(1)
            .addAnalysisRun(5, BUILD_FROM_ONE_DAY_AGO_MORNING)
            .addAnalysisRun(6, BUILD_FROM_TWO_DAYS_AGO_AFTERNOON)
            .addAnalysisRun(8, TOO_OLD_BUILD)
            .createCategoryDataSet();

    assertSize(result, 1,3);
    assertValuesInVerticalOrder(result,1,4,0);
    assertColumnKeysAsNumberOnlyBuildLabel(result, BUILD_FROM_ONE_DAY_AGO_MORNING);

  }
  @Test
  void dataSetPerBuildWithThreeBuildsAndBuildCountLimitIsOne(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .setBuildCountLimit(1)
            .isDayCountDefined(true)
            .isBuildCountDefined(true)
            .addAnalysisRun(5, BUILD_FROM_ONE_DAY_AGO_MORNING)
            .addAnalysisRun(6, BUILD_FROM_THREE_DAYS_AGO_MIDDAY)
            .addAnalysisRun(8, BUILD_FROM_TWO_DAYS_AGO_MIDNIGHT)
            .createCategoryDataSet();

    assertSize(result, 1,3);
    assertValuesInVerticalOrder(result,1,4,0);
    assertColumnKeysAsNumberOnlyBuildLabel(result, BUILD_FROM_ONE_DAY_AGO_MORNING);
  }
  @Test
  void dataSetPerBuildWithThreeBuildsAndBuildCountLimitIsTwo(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .setBuildCountLimit(2)
            .isDayCountDefined(true)
            .isBuildCountDefined(true)
            .addAnalysisRun(5, BUILD_FROM_ONE_DAY_AGO_AFTERNOON)
            .addAnalysisRun(6, BUILD_FROM_ONE_DAY_AGO_MORNING)
            .addAnalysisRun(8, BUILD_FROM_TWO_DAYS_AGO_MIDNIGHT)
            .createCategoryDataSet();

    assertSize(result, 2,3);
    assertValuesInVerticalOrder(result,1,4,1,1,4,0);
    assertColumnKeysAsNumberOnlyBuildLabel(result,  BUILD_FROM_ONE_DAY_AGO_MORNING, BUILD_FROM_ONE_DAY_AGO_AFTERNOON);
  }
  //</editor-fold>

  //<editor-fold desc="useBuildDateAsDomain=true">
  @Test
  void createEmptyDataSetUseBuildDateAsDomain(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .isUseBuildDateAsDomain(true)
            .createCategoryDataSet();
    assertSize(result, 0,0);
  }
  @Test
  void dataSetWithFromOneDayWithThreeBuildsAndWithDifferentStaticAnalysisRuns(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .isUseBuildDateAsDomain(true)
            .isDayCountDefined(true)
            .isBuildCountDefined(false)
            .addAnalysisRun(5, BUILD_FROM_ONE_DAY_AGO_MORNING)
            .addAnalysisRun(7, BUILD_FROM_ONE_DAY_AGO_MIDDAY)
            .addAnalysisRun(9, BUILD_FROM_ONE_DAY_AGO_AFTERNOON)
            .createCategoryDataSet();

    assertSize(result, 1,3);
    // Average value from one day ago
    assertValuesInVerticalOrder(result,1,4,2);
    assertColumnKeysAsLocalDateLabel(result, BUILD_FROM_ONE_DAY_AGO_MORNING);
  }
  @Test
  void dataSetFromOneDayWithThreeBuildsAndWithSameStaticAnalysisRuns(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .isUseBuildDateAsDomain(true)
            .addAnalysisRun(5, BUILD_FROM_ONE_DAY_AGO_MORNING)
            .addAnalysisRun(5, BUILD_FROM_ONE_DAY_AGO_MIDDAY)
            .addAnalysisRun(5, BUILD_FROM_ONE_DAY_AGO_AFTERNOON)
            .createCategoryDataSet();

    assertSize(result, 1,3);
    // Average value from one day ago
    assertValuesInVerticalOrder(result,1,4,0);
    assertColumnKeysAsLocalDateLabel(result, BUILD_FROM_ONE_DAY_AGO_MORNING);
  }
  @Test
  void dataSetFromTwoFollowingDaysEachWithOneBuildAndDifferentStaticAnalysisRuns(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .isUseBuildDateAsDomain(true)
            .isDayCountDefined(true)
            .addAnalysisRun(5, BUILD_FROM_ONE_DAY_AGO_MORNING)
            .addAnalysisRun(7, BUILD_FROM_TWO_DAYS_AGO_MIDDAY)
            .createCategoryDataSet();

    assertSize(result, 2,3);
    assertValuesInVerticalOrder(result,1,4,2,1,4,0);
    assertColumnKeysAsLocalDateLabel(result, BUILD_FROM_TWO_DAYS_AGO_MIDDAY, BUILD_FROM_ONE_DAY_AGO_MORNING);
  }
  @Test
  void dataSetFromTwoFollowingDaysEachWithTwoBuild(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .isUseBuildDateAsDomain(true)
            .isDayCountDefined(true)
            .addAnalysisRun(6, BUILD_FROM_ONE_DAY_AGO_MORNING)
            .addAnalysisRun(9, BUILD_FROM_TWO_DAYS_AGO_MIDNIGHT)
            .addAnalysisRun(7, BUILD_FROM_ONE_DAY_AGO_AFTERNOON)
            .addAnalysisRun(6, BUILD_FROM_TWO_DAYS_AGO_MIDDAY)
            .createCategoryDataSet();

    assertSize(result, 2,3);
    assertValuesInVerticalOrder(result,1,4,2, 1, 4, 1);
    assertColumnKeysAsLocalDateLabel(result, BUILD_FROM_TWO_DAYS_AGO_MIDDAY, BUILD_FROM_ONE_DAY_AGO_MORNING);
  }
  @Test
  void dataSetFromTwoNotFollowingDaysEachWithTwoBuild(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .isUseBuildDateAsDomain(true)
            .isDayCountDefined(true)
            .addAnalysisRun(4, BUILD_FROM_ONE_DAY_AGO_MORNING)
            .addAnalysisRun(9, BUILD_FROM_THREE_DAYS_AGO_MIDNIGHT)
            .addAnalysisRun(9, BUILD_FROM_ONE_DAY_AGO_AFTERNOON)
            .addAnalysisRun(6, BUILD_FROM_THREE_DAYS_AGO_MIDDAY)
            .createCategoryDataSet();

    assertSize(result, 2,3);
    assertValuesInVerticalOrder(result,1,4,2, 1,3,2);
    assertColumnKeysAsLocalDateLabel(result, BUILD_FROM_THREE_DAYS_AGO_MIDDAY, BUILD_FROM_ONE_DAY_AGO_MORNING);
  }

  /**
   * Tests the day demarcation with one day 23:59 and next with 00:00.
   * Test DayCountLimit with one day 23:59 in limit and next day with 00:00 outside limit.
   */
  @Test
  void testDayCountLimitBorderTestAndDayDemarcationBorderTest(){
    CategoryDataset result = new HealthSeriesBuilderTestBuilder()
            .isUseBuildDateAsDomain(true)
            .isDayCountDefined(true)
            .setDayCountLimit(2)
            .addAnalysisRun(4, BUILD_FROM_ONE_DAY_AGO_NIGHT)
            .addAnalysisRun(9, BUILD_FROM_TWO_DAYS_AGO_MIDNIGHT)
            .addAnalysisRun(9, BUILD_FROM_TWO_DAYS_AGO_NIGHT)
            .addAnalysisRun(6, BUILD_FROM_THREE_DAYS_AGO_NIGHT)
            .createCategoryDataSet();

    assertSize(result, 2,3);
    assertValuesInVerticalOrder(result,1,4,4, 1,3,0);
    assertColumnKeysAsLocalDateLabel(result, BUILD_FROM_TWO_DAYS_AGO_NIGHT, BUILD_FROM_ONE_DAY_AGO_NIGHT);
  }
  //</editor-fold>

  //<editor-fold desc="Helper">

  /**
   * Proofs column and row count.
   * @param set = DataSet too proof.
   * @param columnCount = expected column count.
   * @param rowCount = expected row count.
   */
  private void assertSize(CategoryDataset set, int columnCount, int rowCount){
    assertThat(set.getColumnCount()).isEqualTo(columnCount);
    assertThat(set.getRowCount()).isEqualTo(rowCount);
  }

  /**
   * Checks if values are in correct order in the DataSetTable.
   *  e.g.  0, 3
   *        1, 4
   *        2, 5
   * @param set = DataSet to proof.
   * @param values = expected values.
   */
  private void assertValuesInVerticalOrder(CategoryDataset set, int... values){
    int currentValue = 0;
    for(int cIndex = 0; cIndex < set.getColumnCount() && currentValue < values.length; cIndex++ ){
      for(int rIndex = 0; rIndex < set.getRowCount() && currentValue < values.length; rIndex++ ){
        Number fieldValue = set.getValue(set.getRowKey(rIndex),set.getColumnKey(cIndex));
        assertThat(fieldValue).isEqualTo(values[currentValue]);
          currentValue++;
      }
    }
  }

  /**
   * Proof if columns have a NumberOnlyLabel with specified builds.
   * @param set = DataSet too proof.
   * @param builds = Expected builds in column label.
   */
  private void assertColumnKeysAsNumberOnlyBuildLabel(CategoryDataset set, AnalysisBuild... builds){
    for(int i = 0; i < builds.length; i++){
      assertThat(set.getColumnKey(i)).isEqualTo(new NumberOnlyBuildLabel(builds[i]));
    }
  }

  /**
   * Proof if column have a LocalDateLabel with specified builds.
   * @param set = DataSet too proof.
   * @param builds = Expected builds in column label.
   */
  private void assertColumnKeysAsLocalDateLabel(CategoryDataset set, AnalysisBuild... builds){
    for(int i = 0; i < builds.length; i++){
      assertThat(set.getColumnKey(i)).isEqualTo(new LocalDateLabel(new LocalDate(builds[i].getTimeInMillis())));
    }
  }

  /**
   * Stubs a AnalysisBuild from some parameters.
   * @param buildNumber = BuildNumber.
   * @param daysAgo = how long is the build in the past in days.
   * @param hours = hours from midnight on the build day.
   * @return a AnalysisBuild from a specified Time in the past.
   */
  private static AnalysisBuild stubAnalysisBuild(int buildNumber, int daysAgo, int hours, int minutes) {
    LocalDate now = new LocalDate(System.currentTimeMillis());
    long buildTime = now.minusDays(daysAgo).toDate().getTime(); // without time only date
    buildTime += MILLISECONDS_OF_HOUR * hours; // add hours
    buildTime += MILLISECONDS_OF_MIN *minutes;
    AnalysisBuild build = mock(AnalysisBuild.class);
    when(build.getNumber()).thenReturn(buildNumber);
    when(build.getTimeInMillis()).thenReturn(buildTime);
    return build;

  }
  //</editor-fold>

  //<editor-fold desc="Builder">

  /**
   * Creates a Test scenario.
   * Fills all values which are not specified with default values.
   */
  private class HealthSeriesBuilderTestBuilder{
    private int healthy = 1;
    private int unhealthy = 5;
    private boolean useBuildDateAsDomain = false;
    private boolean dayCountDefined = false;
    private boolean buildCountDefined = false;
    private int buildCountLimit = BUILD_COUNT_LIMIT;
    private int dayCountLimit = TOO_OLD_LIMIT;
    private Priority priority = Priority.NORMAL;
    private List<StaticAnalysisRun> staticAnalysisRuns = new ArrayList<>();
    private ResultTime resultTime = new ResultTime();
    HealthSeriesBuilderTestBuilder setHealth(int healthy){
      this.healthy = healthy;
      return this;
    }
    HealthSeriesBuilderTestBuilder setUnhealthy(int unhealthy){
      this.unhealthy = unhealthy;
      return this;
    }
    HealthSeriesBuilderTestBuilder setPriority(Priority priority){
      this.priority = priority;
      return this;
    }
    HealthSeriesBuilderTestBuilder setResultTime(ResultTime resultTime){
      this.resultTime = resultTime;
      return this;
    }
    HealthSeriesBuilderTestBuilder isUseBuildDateAsDomain(boolean useBuildDateAsDomain) {
      this.useBuildDateAsDomain = useBuildDateAsDomain;
      return this;
    }
    HealthSeriesBuilderTestBuilder isDayCountDefined(boolean dayCountDefined) {
      this.dayCountDefined = dayCountDefined;
      return this;
    }
    HealthSeriesBuilderTestBuilder isBuildCountDefined(boolean buildCountDefined) {
      this.buildCountDefined = buildCountDefined;
      return this;
    }
    HealthSeriesBuilderTestBuilder addAnalysisRun(int totalSize, AnalysisBuild build){
      this.staticAnalysisRuns.add(stubStaticAnalysisRun(totalSize, build));
      return this;
    }
    HealthSeriesBuilderTestBuilder setBuildCountLimit(int limit){
      this.buildCountLimit = limit;
      return this;
    }
    HealthSeriesBuilderTestBuilder setDayCountLimit(int daycount){
      this.dayCountLimit = daycount;
      return this;
    }

    /**
     * Creates a CategoryDataSet from scenario parameters.
     * @return DataSet too proof.
     */
    CategoryDataset createCategoryDataSet(){
      HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(this.healthy),Integer.toString(unhealthy), priority );
      HealthSeriesBuilder sut = this.resultTime !=null?new HealthSeriesBuilder(healthDescriptor, this.resultTime):new HealthSeriesBuilder(healthDescriptor);
      GraphConfiguration configuration = stubGraphConfiguration(useBuildDateAsDomain,dayCountDefined, buildCountDefined, dayCountLimit, buildCountLimit);
      return sut.createDataSet(configuration, staticAnalysisRuns);
    }

    /**
     * Stubs a GraphConfiguration.
     * @param useBuildDateAsDomain = decides if builds is grouped by days or by count of builds.
     * @param isDayCountDefined = use DayCount flag.
     * @param isBuildCountDefined = use BuildCount flag.
     * @param dayCount = days before a build is not too old.
     * @param buildCount = limit count of builds in the graph.
     * @return GraphConfigurationStub.
     */
    private GraphConfiguration stubGraphConfiguration(boolean useBuildDateAsDomain, boolean isDayCountDefined,
            boolean isBuildCountDefined, int dayCount, int buildCount) {
      GraphConfiguration configuration = mock(GraphConfiguration.class);
      when(configuration.useBuildDateAsDomain()).thenReturn(useBuildDateAsDomain);
      when(configuration.isDayCountDefined()).thenReturn(isDayCountDefined);
      when(configuration.isBuildCountDefined()).thenReturn(isBuildCountDefined);
      when(configuration.getDayCount()).thenReturn(dayCount);
      when(configuration.getBuildCount()).thenReturn(buildCount);
      return configuration;
    }

    /**
     * Stubs a StaticAnalysisRun.
     * @param totalSize = total size.
     * @param build = containing build
     * @return StaticAnalysisRunStub
     */
    private StaticAnalysisRun stubStaticAnalysisRun(int totalSize, AnalysisBuild build){
      StaticAnalysisRun staticAnalysisRun = mock(StaticAnalysisRun.class);
      when(staticAnalysisRun.getTotalSize()).thenReturn(totalSize);
      when(staticAnalysisRun.getBuild()).thenReturn(build);
      return staticAnalysisRun;
    }


  }
  //</editor-fold>






}