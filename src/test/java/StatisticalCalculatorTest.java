import org.junit.jupiter.api.*;
import org.mockito.*;


/**
 * Test class for StatisticalCalculator.
 */
class StatisticalCalculatorTest {

    /**
     * Test calculating mean use case.
     */
    @Test
    public void testCalculateMean() {

        // Create Mock objects for InputBoundary and OutputBoundary
        MeanInputBoundary meanInputBoundary = Mockito.mock(MeanInputBoundary.class);
        MeanOutputBoundary meanOutputBoundary = Mockito.mock(MeanOutputBoundary.class);

        // Create Interactor with Mock objects
        MeanInteractor meanInteractor = new MeanInteractor(meanOutputBoundary);

        // Set up Mock behavior
        Mockito.when(meanInputBoundary.calculateMean(Mockito.anyString())).thenReturn(new MeanViewModel(30.0));
        Mockito.when(meanOutputBoundary.promptMean(30.0)).thenReturn(new MeanViewModel(30.0));

        // Perform the actual test
        double result = meanInteractor.calculateMean("10, 20, 30, 40, 50").getMean();
        assert result == 30.0;

        // Verify interactions with the Mocks
        Mockito.verify(meanOutputBoundary).promptMean(30.0);
    }

    /**
     * Test calculating standard deviation use case.
     */
    @Test
    public void testCalculateSD() {
        // Create Mock objects for InputBoundary and OutputBoundary
        SDInputBoundary sdInputBoundary = Mockito.mock(SDInputBoundary.class);
        SDOutputBoundary sdOutputBoundary = Mockito.mock(SDOutputBoundary.class);

        // Create Interactor with Mock objects
        SDInteractor sdInteractor = new SDInteractor(sdOutputBoundary);

        // Set up Mock behavior
        Mockito.when(sdInputBoundary.calculateSD(Mockito.anyString())).thenReturn(new SDViewModel(15.81));
        Mockito.when(sdOutputBoundary.promptSD(15.81)).thenReturn(new SDViewModel(15.81));

        // Perform the actual test
        double result = sdInteractor.calculateSD("10, 20, 30, 40, 50").getSD();
        assert result == 15.81;

        // Verify interactions with the Mocks
        Mockito.verify(sdOutputBoundary).promptSD(15.81);
    }

    /**
     * Test calculating median use case when the sample size is odd.
     */
    @Test
    public void testCalculateMedianOdd() {
        // Create Mock objects for InputBoundary and OutputBoundary
        MedianInputBoundary medianInputBoundary = Mockito.mock(MedianInputBoundary.class);
        MedianOutputBoundary medianOutputBoundary = Mockito.mock(MedianOutputBoundary.class);

        // Create Interactor with Mock objects
        MedianInteractor medianInteractor = new MedianInteractor(medianOutputBoundary);

        // Set up Mock behavior
        Mockito.when(medianInputBoundary.calculateMedian(Mockito.anyString())).thenReturn(new MedianViewModel(30.0));
        Mockito.when(medianOutputBoundary.promptMedian(30.0)).thenReturn(new MedianViewModel(30.0));

        // Perform the actual test
        double result = medianInteractor.calculateMedian("10, 20, 30, 40, 50").getMedian();
        assert result == 30.0;

        // Verify interactions with the Mocks
        Mockito.verify(medianOutputBoundary).promptMedian(30.0);
    }

    /**
     * Test calculating median use case when the sample size is even.
     */
    @Test
    public void testCalculateMedianEven() {
        // Create Mock objects for InputBoundary and OutputBoundary
        MedianInputBoundary medianInputBoundary = Mockito.mock(MedianInputBoundary.class);
        MedianOutputBoundary medianOutputBoundary = Mockito.mock(MedianOutputBoundary.class);

        // Create Interactor with Mock objects
        MedianInteractor medianInteractor = new MedianInteractor(medianOutputBoundary);

        // Set up Mock behavior
        Mockito.when(medianInputBoundary.calculateMedian(Mockito.anyString())).thenReturn(new MedianViewModel(25.0));
        Mockito.when(medianOutputBoundary.promptMedian(25.0)).thenReturn(new MedianViewModel(25.0));

        // Perform the actual test
        double result = medianInteractor.calculateMedian("10, 20, 30, 40").getMedian();
        assert result == 25.0;

        // Verify interactions with the Mocks
        Mockito.verify(medianOutputBoundary).promptMedian(25.0);
    }

    /**
     * Test calculating sample size use case.
     */
    @Test
    public void testCalculateSampleSize() {
        // Create Mock objects for InputBoundary and OutputBoundary
        SampleSizeInputBoundary sampleSizeInputBoundary = Mockito.mock(SampleSizeInputBoundary.class);
        SampleSizeOutputBoundary sampleSizeOutputBoundary = Mockito.mock(SampleSizeOutputBoundary.class);

        // Create Interactor with Mock objects
        SampleSizeInteractor sampleSizeInteractor = new SampleSizeInteractor(sampleSizeOutputBoundary);

        // Set up Mock behavior
        Mockito.when(sampleSizeInputBoundary.calculateSampleSize(Mockito.anyString())).thenReturn(new SampleSizeViewModel(5));
        Mockito.when(sampleSizeOutputBoundary.promptSampleSize(5)).thenReturn(new SampleSizeViewModel(5));

        // Perform the actual test
        double result = sampleSizeInteractor.calculateSampleSize("10, 20, 30, 40, 50").getSampleSize();
        assert result == 5;

        // Verify interactions with the Mocks
        Mockito.verify(sampleSizeOutputBoundary).promptSampleSize(5);
    }
}