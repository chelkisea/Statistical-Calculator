import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;


/**
 * A class for executing statistical calculator.
 */
public class StatisticalCalculator {
    public static void main(String[] args) {

        ControllerFactory controllerFactory = new ControllerFactory();
        MeanController meanController = controllerFactory.createMeanController();
        SDController sdController = controllerFactory.createSDController();
        MedianController medianController = controllerFactory.createMedianController();
        SampleSizeController sampleSizeController = controllerFactory.createSampleSizeController();

        SwingUtilities.invokeLater(() -> {
            new CalculatorView(meanController, sdController, medianController, sampleSizeController);
        });
    }
}

/**
 * Entity for this program.
 */
class Dataset {
    private final String input;

    public Dataset(String input) {
        this.input = input;
    }

    /**
     * Change the type of user's input from String into list of integers.
     * @return list of integers
     */
    public int[] getNumberList() {
        return toList(this.input);
    }

    /**
     * A private helper function to convert the type of user's input
     * @param input user's input which is a comma separated integer
     * @return a list of integers
     */
    private int[] toList(String input) {
        String[] numberStrings = input.split(",");
        int[] numbers = new int[numberStrings.length];
        for (int i = 0; i < numberStrings.length; i++) {
            numbers[i] = Integer.parseInt(numberStrings[i].trim());
        }
        Arrays.sort(numbers);
        return numbers;
    }

}

/**
 * This class is a controller which receives user input
 * and handles calculateMean method through MeanInputBoundary.
 */
class MeanController {
    private final MeanInputBoundary inputBoundary;

    public MeanController(MeanInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public MeanViewModel calculateMean(String input) {
        return inputBoundary.calculateMean(input);
    }
}

/**
 * This class is a controller which receives user input
 * and handles calculateSD method through SDInputBoundary.
 */
class SDController {
    private final SDInputBoundary inputBoundary;

    public SDController(SDInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public SDViewModel calculateSD(String input) {
        return inputBoundary.calculateSD(input);
    }
}

/**
 * This class is a controller which receives user input
 * and handles calculateMedian method through MedianInputBoundary.
 */
class MedianController {
    private final MedianInputBoundary inputBoundary;

    public MedianController(MedianInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public MedianViewModel calculateMedian(String input) {
        return inputBoundary.calculateMedian(input);
    }
}

/**
 * This class is a controller which receives user input
 * and handles calculateSampleSize method through SampleSizeInputBoundary.
 */
class SampleSizeController {
    private final SampleSizeInputBoundary inputBoundary;

    public SampleSizeController(SampleSizeInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public SampleSizeViewModel calculateSampleSize(String input) {
        return inputBoundary.calculateSampleSize(input);
    }
}


/**
 * This interface is an abstraction layer between MeanController and MeanInteractor.
 */
interface MeanInputBoundary {
    MeanViewModel calculateMean(String input);
}

/**
 * This interface is an abstraction layer between SDController and SDInteractor.
 */
interface SDInputBoundary {
    SDViewModel calculateSD(String input);
}

/**
 * This interface is an abstraction layer between MedianController and MedianInteractor.
 */
interface MedianInputBoundary {
    MedianViewModel calculateMedian(String input);
}

/**
 * This interface is an abstraction layer between SampleSizeController and SampleSizeInteractor.
 */
interface SampleSizeInputBoundary {
    SampleSizeViewModel calculateSampleSize(String input);
}

/**
 * This class is an interactor for handle calculating mean use case.
 */
class MeanInteractor implements MeanInputBoundary {
    private final MeanOutputBoundary outputBoundary;

    public MeanInteractor(MeanOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    /**
     * Calculates the mean of given input and returns the result
     * by encapsulating in MeanViewModel through MeanOutputBoundary.
     *
     * @param input user's input
     * @return a MeanViewModel object
     */
    @Override
    public MeanViewModel calculateMean(String input) {
        Dataset dataset = new Dataset(input);
        int[] data = dataset.getNumberList();

        int sum = 0;
        for (int num : data) {
            sum += num;
        }

        double mean = (double) sum / data.length;
        double roundedMean = round(mean * 100.0) / 100.0;
        return this.outputBoundary.promptMean(roundedMean);
    }
}

/**
 * This class is an interactor for handle calculating standard deviation use case.
 */
class SDInteractor implements SDInputBoundary {
    private final SDOutputBoundary outputBoundary;

    public SDInteractor(SDOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    /**
     * Calculates the standard deviation of given input and returns the result
     * by encapsulating in SDViewModel through SDOutputBoundary.
     *
     * @param input user's input
     * @return a SDViewModel object
     */
    @Override
    public SDViewModel calculateSD(String input) {
        Dataset dataset = new Dataset(input);
        int[] data = dataset.getNumberList();
        int n = data.length;

        int sum = 0;
        for (int num : data) {
            sum += num;
        }

        double mean = (double) sum / n;

        double sumOfSquaredDifferences = 0;
        for (int num : data) {
            double diff = num - mean;
            sumOfSquaredDifferences += diff * diff;
        }

        double variance = sumOfSquaredDifferences / (n - 1);
        double roundedSD = round(sqrt(variance) * 100.0) / 100.0;

        return this.outputBoundary.promptSD(roundedSD);
    }
}

/**
 * This class is an interactor for handle calculating median use case.
 */
class MedianInteractor implements MedianInputBoundary {
    private final MedianOutputBoundary outputBoundary;

    public MedianInteractor(MedianOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    /**
     * Calculates the median of given input and returns the result
     * by encapsulating in MedianViewModel through MedianOutputBoundary.
     *
     * @param input user's input
     * @return a MedianViewModel object
     */
    @Override
    public MedianViewModel calculateMedian(String input) {

        Dataset dataset = new Dataset(input);
        int[] data = dataset.getNumberList();
        int n = data.length;
        double median;

        if (n % 2 == 1) {
            median = data[n / 2];
        } else {
            int middleIndex1 = n / 2 - 1;
            int middleIndex2 = n / 2;
            median = (data[middleIndex1] + data[middleIndex2]) / 2.0;
        }

        return this.outputBoundary.promptMedian(median);
    }
}

/**
 * This class is an interactor for handle calculating sample size use case.
 */
class SampleSizeInteractor implements SampleSizeInputBoundary {
    private final SampleSizeOutputBoundary outputBoundary;

    public SampleSizeInteractor(SampleSizeOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    /**
     * Calculates the sample size of given input and returns the result
     * by encapsulating in SampleSizeViewModel through SampleSizeOutputBoundary.
     *
     * @param input user's input
     * @return a SampleSizeViewModel object
     */
    @Override
    public SampleSizeViewModel calculateSampleSize(String input) {
        Dataset dataset = new Dataset(input);
        int[] data = dataset.getNumberList();
        int n = data.length;

        return this.outputBoundary.promptSampleSize(n);
    }
}

/**
 * This interface is an abstraction layer between MeanInteractor and MeanPresenter.
 */
interface MeanOutputBoundary {
    MeanViewModel promptMean(double mean);
}

/**
 * This interface is an abstraction layer between SDInteractor and SDPresenter.
 */
interface SDOutputBoundary {
    SDViewModel promptSD(double sd);
}

/**
 * This interface is an abstraction layer between MedianInteractor and MedianPresenter.
 */
interface MedianOutputBoundary {
    MedianViewModel promptMedian(double median);
}

/**
 * This interface is an abstraction layer between SampleSizeInteractor and SampleSizePresenter.
 */
interface SampleSizeOutputBoundary {
    SampleSizeViewModel promptSampleSize(int size);
}

/**
 * This class is a presenter which creates and returns MeanViewModel object
 * whose value is from MeanInteractor
 */
class MeanPresenter implements MeanOutputBoundary {
    @Override
    public MeanViewModel promptMean(double mean) {
        return new MeanViewModel(mean);
    }
}

/**
 * This class is a presenter which creates and returns SDViewModel object
 * whose value is from SDInteractor
 */
class SDPresenter implements SDOutputBoundary {
    @Override
    public SDViewModel promptSD(double sd) {
        return new SDViewModel(sd);
    }
}

/**
 * This class is a presenter which creates and returns MedianViewModel object
 * whose value is from MedianInteractor
 */
class MedianPresenter implements MedianOutputBoundary {
    @Override
    public MedianViewModel promptMedian(double median) {
        return new MedianViewModel(median);
    }
}

/**
 * This class is a presenter which creates and returns SampleSizeViewModel object
 * whose value is from SampleSizeInteractor
 */
class SampleSizePresenter implements SampleSizeOutputBoundary {
    @Override
    public SampleSizeViewModel promptSampleSize(int size) {
        return new SampleSizeViewModel(size);
    }
}

/**
 * This class is a data model to encapsulate the result value from MeanInteractor.
 */
class MeanViewModel {
    private final double mean;

    public MeanViewModel(double mean) {
        this.mean = mean;
    }

    public double getMean() {
        return this.mean;
    }
}

/**
 * This class is a data model to encapsulate the result value from SDInteractor.
 */
class SDViewModel {
    private final double sd;

    public SDViewModel(double sd) {
        this.sd = sd;
    }

    public double getSD() {
        return this.sd;
    }
}

/**
 * This class is a data model to encapsulate the result value from MedianInteractor.
 */
class MedianViewModel {
    private final double median;

    public MedianViewModel(double median) {
        this.median = median;
    }

    public double getMedian() {
        return this.median;
    }
}

/**
 * This class is a data model to encapsulate the result value from SampleSizeInteractor.
 */
class SampleSizeViewModel {
    private final int sampleSize;

    public SampleSizeViewModel(int sampleSize) {
        this.sampleSize = sampleSize;
    }

    public int getSampleSize() {
        return this.sampleSize;
    }
}

/**
 * This class is to apply factory pattern.
 * It encapsulates the creation of controllers and their associated interactors and presenters.
 */
class ControllerFactory {

    public MeanController createMeanController() {
        MeanOutputBoundary meanPresenter = new MeanPresenter();
        MeanInputBoundary meanInteractor = new MeanInteractor(meanPresenter);
        return new MeanController(meanInteractor);
    }

    public SDController createSDController() {
        SDOutputBoundary sdPresenter = new SDPresenter();
        SDInputBoundary sdInteractor = new SDInteractor(sdPresenter);
        return new SDController(sdInteractor);
    }

    public MedianController createMedianController() {
        MedianOutputBoundary medianPresenter = new MedianPresenter();
        MedianInputBoundary medianInteractor = new MedianInteractor(medianPresenter);
        return new MedianController(medianInteractor);
    }

    public SampleSizeController createSampleSizeController() {
        SampleSizeOutputBoundary sampleSizePresenter = new SampleSizePresenter();
        SampleSizeInputBoundary sampleSizeInteractor = new SampleSizeInteractor(sampleSizePresenter);
        return new SampleSizeController(sampleSizeInteractor);
    }

}

/**
 * This class is to implement UI that users use.
 */
class CalculatorView extends JFrame {
    private final JTextField inputField;
    private final JTextArea resultArea;
    private final MeanController meanController;
    private final SDController sdController;
    private final MedianController medianController;
    private final SampleSizeController sampleSizeController;

    public CalculatorView(MeanController meanController,
                          SDController sdController,
                          MedianController medianController,
                          SampleSizeController sampleSizeController) {

        this.meanController = meanController;
        this.sdController = sdController;
        this.medianController = medianController;
        this.sampleSizeController = sampleSizeController;

        setTitle("Statistical Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel inputLabel = new JLabel("Enter integers separated by commas:");
        inputField = new JTextField(15);
        JButton doneButton = new JButton("Done");
        JButton undoButton = new JButton("Undo");
        JButton meanButton = new JButton("Mean");
        JButton sdButton = new JButton("Standard Deviation");
        JButton medianButton = new JButton("Median");
        JButton sampleSizeButton = new JButton("Sample Size");
        resultArea = new JTextArea(3, 30);
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        JPanel inputPanel = new JPanel(new GridLayout(4, 1));
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(doneButton);
        inputPanel.add(undoButton);

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2));
        buttonsPanel.add(meanButton);
        buttonsPanel.add(sdButton);
        buttonsPanel.add(medianButton);
        buttonsPanel.add(sampleSizeButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
        add(resultScrollPane, BorderLayout.SOUTH);

        setSize(400, 350);
        setVisible(true);

        undoButton.addActionListener(e -> {
            inputField.setText("");
            resultArea.setText("");
        });

        doneButton.addActionListener(e -> {
            String input = getInputText();
            setResult("Input: " + input);
        });

        meanButton.addActionListener(e -> {
            String input = inputField.getText();
            MeanViewModel result = this.meanController.calculateMean(input);
            setResult("Mean: " + result.getMean());
        });

        sdButton.addActionListener(e -> {
            String input = inputField.getText();
            SDViewModel result = this.sdController.calculateSD(input);
            setResult("Standard Deviation: " + result.getSD());
        });

        medianButton.addActionListener(e -> {
            String input = inputField.getText();
            MedianViewModel result = this.medianController.calculateMedian(input);
            setResult("Median: " + result.getMedian());
        });

        sampleSizeButton.addActionListener(e -> {
            String input = inputField.getText();
            SampleSizeViewModel result = this.sampleSizeController.calculateSampleSize(input);
            setResult("Sample Size: " + result.getSampleSize());
        });
    }

    public String getInputText() {
        return inputField.getText();
    }

    public void setResult(String result) {
        resultArea.setText(result);
    }

}



