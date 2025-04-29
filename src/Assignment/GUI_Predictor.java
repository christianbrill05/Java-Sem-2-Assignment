package Assignment;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

// GUI class that lets the user input flight features and get a prediction whether the flight is delayed or not.
public class GUI_Predictor extends JFrame
{
    private JComboBox<String> departureTimeBox;
    private JComboBox<String> dayTypeBox;
    private JComboBox<String> weatherBox;
    private JComboBox<String> distanceBox;
    private JComboBox<String> yesNoLabelBox; // new combo box allows user to select "Yes" or "No" for the new row
    private JLabel result, departureLabel, dayTypeLabel, weatherLabel, distanceLabel, yesNoLabel;
    private JButton predictButton, trainButton, addNewRowButton;
    private FlightDelayPredictor predictor;

    // drop down menu options for each feature.
    private String[] departureTimeOptions = {"AM","PM"};
    private String[] dayTypeOptions = {"Weekday","Weekend"};
    private String[] weatherTypeOptions = {"Clear","Rain"};
    private String[] distanceOptions = {"Close","Far"};
    private String[] yesNoLabelOptions = {"Yes","No"};

    // constructor sets up layout for GUI and action listener
    public GUI_Predictor(FlightDelayPredictor predictor)
    {
        super("Flight Delay Predictor");
        this.predictor = predictor;
        departureTimeBox = new JComboBox<>(departureTimeOptions);
        dayTypeBox = new JComboBox<>(dayTypeOptions);
        weatherBox = new JComboBox<>(weatherTypeOptions);
        distanceBox = new JComboBox<>(distanceOptions);
        yesNoLabelBox = new JComboBox<>(yesNoLabelOptions);
        result = new JLabel("Prediction");

        setVisible(true);
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8,2));

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

        // level 1
        predictButton = new JButton("  Predict: ");
        add(predictButton);
        // apply action listener for prediction button
        predictButton.addActionListener(e -> getPrediction());

        // level 2
        trainButton = new JButton("  Train Dataset ");
        add(trainButton);
        // apply action listener for train button
        trainButton.addActionListener(e -> trainDataset());

        // components for level 3
        yesNoLabel = new JLabel("Flight Delayed? (Yes/No)");
        add(yesNoLabel);
        add(yesNoLabelBox);

        // level 3
        addNewRowButton = new JButton("  Add new row  ");
        add(addNewRowButton);
        // apply action listener for addNewRowButton
        addNewRowButton.addActionListener(e -> addRow());

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

    // level 3
    public void addRow()
    // method allows the user to add a new row to "FlightIsDelayed Predective Dataset.csv" from user input
    // the dataset is then retrained
    {
        // get user input
        String departureTime = (String) departureTimeBox.getSelectedItem();
        String dayType = (String) dayTypeBox.getSelectedItem();
        String weather = (String) weatherBox.getSelectedItem();
        String distance = (String) distanceBox.getSelectedItem();
        String yesNoLabel = (String) yesNoLabelBox.getSelectedItem();

        try (FileWriter writer = new FileWriter("FlightIsDelayed Predective Dataset.csv", true))
        {
            // append the new row to the csv file
            writer.write(departureTime + "," + dayType + "," + weather + ","
                    + distance + "," + yesNoLabel + "\n");
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }

        predictor.trainData("FlightIsDelayed Predective Dataset.csv"); // retrain data with new row
        JOptionPane.showMessageDialog(this, "New row added, data has been trained.");
    }



}
