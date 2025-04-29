package Assignment;

import javax.swing.*;
import java.awt.*;

// GUI class that lets the user input flight features and get a prediction whether the flight is delayed or not.
public class GUI_Predictor extends JFrame
{
    private JComboBox<String> departureTimeBox;
    private JComboBox<String> dayTypeBox;
    private JComboBox<String> weatherBox;
    private JComboBox<String> distanceBox;
    private JLabel result, departureLabel, dayTypeLabel, weatherLabel, distanceLabel;
    private JButton predictButton, trainButton;
    private FlightDelayPredictor predictor;

    // drop down menu options for each feature.
    private String[] departureTimeOptions = {"AM","PM"};
    private String[] dayTypeOptions = {"Weekday","Weekend"};
    private String[] weatherTypeOptions = {"Clear","Rain"};
    private String[] distanceOptions = {"Close","Far"};

    // constructor sets up layout for GUI and action listener
    public GUI_Predictor(FlightDelayPredictor predictor)
    {
        super("Flight Delay Predictor");
        this.predictor = predictor;
        departureTimeBox = new JComboBox<>(departureTimeOptions);
        dayTypeBox = new JComboBox<>(dayTypeOptions);
        weatherBox = new JComboBox<>(weatherTypeOptions);
        distanceBox = new JComboBox<>(distanceOptions);
        result = new JLabel("Prediction");

        setVisible(true);
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6,2));

        // adding GUI components to the frame

        departureLabel = new JLabel("  Departure Time: ");
        add(departureLabel);
        add(departureTimeBox);

        dayTypeLabel = new JLabel("  Day Type: ");
        add(dayTypeLabel);
        add(dayTypeBox);

        weatherLabel = new JLabel("  Weather Type: ");
        add(weatherLabel);
        add(weatherBox);

        distanceLabel = new JLabel("  Distance: ");
        add(distanceLabel);
        add(distanceBox);

        predictButton = new JButton("  Predict: ");
        add(predictButton);
        // apply action listener for prediction button
        predictButton.addActionListener(e -> getPrediction());

        trainButton = new JButton("  Train Dataset ");
        add(trainButton);
        // apply action listener for train button
        trainButton.addActionListener(e -> trainDataset());

        add(result);
    }

    public void getPrediction()
    // method gets user input from combo boxes and typecasts them to String.
    // sends input to FlightDelayPredictor class, calculates if the flight will be delayed or not.
    // displays the result on the screen.
    {
        // get user input
        String departureTime = (String) departureTimeBox.getSelectedItem();
        String dayType = (String) dayTypeBox.getSelectedItem();
        String weather = (String) weatherBox.getSelectedItem();
        String distance = (String) distanceBox.getSelectedItem();

        // FlightDelayPredictor class predicts whether the flight is delayed or not
        String prediction = predictor.predict(departureTime, dayType, weather, distance);
        // display result
        result.setText("Is your flight delayed? " + prediction);
    }

    // level 2
    public void trainDataset()
    // method uses trainData() from FlightDelayPredictor class
    // calculates all probabilities based on the permutations from the dataset
    // when training is done, a confirmation message is displayed
    {
        predictor.trainData("FlightIsDelayed Predective Dataset.csv");
        JOptionPane.showMessageDialog(this, "Dataset has been trained.");
    }



}
