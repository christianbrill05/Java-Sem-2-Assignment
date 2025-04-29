package Assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FlightDelayPredictor // using Naive Bayes Predictor Logic
{
    private double probYes; // probability of "Yes" in the whole dataset
    private double probNo;  // probability of "No" in the whole dataset
    private HashMap<String, Double> yesProbability; // HashMap contains conditional probabilites for "yes"
    private HashMap<String, Double> noProbability; // contains conditional probabilities for "no"
    private int yesTotal; // counts the total number of "Yes" in the dataset
    private int noTotal; // counts the total number of "No" in the dataset

    // constructor for FlightDelayPredictor
    public FlightDelayPredictor()
    {
        // this.probYes = 0.485; // from frequency table, yesCount/total = yesProbability -> 97/200 = 0.485 ********hard coded values no longer needed********
        // this.probNo = 0.515; // noCount/total = yesProbability -> 103/200 = 0.515
        this.yesProbability = new HashMap<>();
        this.noProbability = new HashMap<>();
    }

    // level 2: classifier training
    public void trainData(String filename)
    // method reads each line of the FlightIsDelayed Predective Dataset.csv file.
    // a count of the amount of "Yes" and "No" labels is taken from each permutation of features.
    // calculates the probabilites of "Yes" and "No" and the conditional probabilites for P(features|Yes) and P(features|No).
    // calculations are used in the predict() method
    {
        HashMap<String, Integer> yesCount = new HashMap<>();
        HashMap<String, Integer> noCount = new HashMap<>();
        yesTotal = 0;
        noTotal = 0;
        File dataset = new File(filename);

        try (Scanner scanner = new Scanner(dataset))
        {
            scanner.nextLine(); // skip the first row that contains the features (DeparturePeriod, Weather etc.)

            while (scanner.hasNextLine())
            {
                String[] columns = scanner.nextLine().split(","); // splits line into an array of sub-strings

                String departurePeriod = columns[0].trim();
                String dayType = columns[1].trim();
                String weather = columns[2].trim();
                String distance = columns[3].trim();
                String flightIsDelayed = columns[4].trim();

                String features = String.join(",", departurePeriod, dayType, weather, distance);

                if (flightIsDelayed.equals("Yes"))
                {
                    yesCount.put(features, yesCount.getOrDefault(features, 0) + 1); // increase the "Yes" count for each permutation
                    yesTotal++; // increase count of overall "Yes" values in the dataset
                } // end if
                else if (flightIsDelayed.equals("No"))
                {
                    // same process for "No" values
                    noCount.put(features, noCount.getOrDefault(features, 0) + 1);
                    noTotal++;
                } // end else if
            } // end while
        } // end try
        catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } // end catch

        // calculating yes and no probabilities
        int total  = yesTotal + noTotal; // should be 200

        if (total > 0)
        {
            this.probYes = (double) yesTotal / total;
        } // end if
        else
        {
            this.probYes = 0.5; // assume a 50% probability if there is no data to base on
        } // end else

        if (total > 0)
        {
            this.probNo = (double) noTotal / total;
        } // end if
        else
        {
            this.probNo = 0.5;
        } // end else

        // calculate conditional probabilities
        // clear data in hashmaps before new values are added
        yesProbability.clear();
        noProbability.clear();

        for (String feature : yesCount.keySet())
        {
            yesProbability.put(feature, (double) yesCount.get(feature) / yesTotal); // P(features|Yes)
        } // end for

        for (String feature : noCount.keySet())
        {
            noProbability.put(feature, (double) noCount.get(feature) / noTotal); // P(features|No)
        }  // end for
    } // end trainData()

    // level 4: calculate the accuracy of the predictor
    public double calculateAccuracy(String filename)
    // method splits the data set into 75% (for training) and 25% (for testing)
    // trains the predictor trainData() and then tests the remaining 25%
    // returns the prediction accuracy as a percentage.
    {
        // lists to seperate "Yes" and "No" labels
        List<String[]> yesRows = new ArrayList<>();
        List<String[]> noRows = new ArrayList<>();
        File dataset = new File(filename);

        try (Scanner scanner = new Scanner(dataset))
        {
            scanner.nextLine(); // skip features line

            while (scanner.hasNextLine())
            {
                String[] columns = scanner.nextLine().split(",");

                if (columns[4].trim().equals("Yes"))
                {
                    yesRows.add(columns);
                } // end if
                else if (columns[4].trim().equals("No"))
                {
                    noRows.add(columns);
                } // end else if
            } // end while
        } // end try
        catch (FileNotFoundException e)
        {
            System.out.println("An error occured: ");
            e.printStackTrace();
        } // catch

        int trainSizeYes = (int) (yesRows.size() * 0.75); // training on 75% of "Yes" values
        int trainSizeNo = (int) (noRows.size() * 0.75); // training on 75% of "No" values

        // create a new dataset for training
        List <String[]> trainGroup = new ArrayList<>();
        trainGroup.addAll(yesRows.subList(0, trainSizeYes)); // add the 75% of "Yes" values into the training group
        trainGroup.addAll(noRows.subList(0, trainSizeNo)); // add the 75% of "No" values into the training group

        // create the dataset for testing
        List <String[]> testGroup = new ArrayList<>();
        testGroup.addAll(yesRows.subList(trainSizeYes, yesRows.size())); // take the last 25% of "Yes" and add in the testing group
        testGroup.addAll(noRows.subList(trainSizeNo, noRows.size())); // take the last 25% and "No" add in the testing group

        try
        {
            File training = new File("training_set.csv"); // temporary file to put the training group inside

            try (FileWriter writer = new FileWriter(training))
            {
                writer.write("Departure,Day,Weather,Distance,Label\n"); // add features to first line
                for (String[] row : trainGroup)
                {
                    writer.write(String.join(",",row) + "\n"); // add each training row
                } // end for
            }
            trainData(training.getAbsolutePath()); // train the data
            training.delete();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        // calculate the accuracy of the 25%
        int correct = 0;

        for (String[] row : testGroup)
        {
            String prediction = predict(row[0].trim(), row[1].trim(), row[2].trim(), row[3].trim()); // predict "Yes" or "No" from features

            if(prediction.equals(row[4].trim()))
            {
                correct++; // if the predicted "Yes" or "No" value matches the actual value, increment correct
            } // end if
        } // end for

        return 100.0 * correct / testGroup.size(); // accuracy == correct predictions / total predictions
    }

    public String predict(String departurePeriod, String dayType, String weather, String distance)
    // method calculates if the flight is delayed or not using Bayes Theorum
    {
        String features = String.join(",", departurePeriod, dayType, weather, distance);

        // calculate Bayes Theorum
        double delayed = probYes * yesProbability.getOrDefault(features, 0.0); // if "features" does not exist, return 0.0
        double notDelayed = probNo * noProbability.getOrDefault(features, 0.0);

        if (delayed > notDelayed)
        {
            return "Yes";
        } // end if
        else
        {
            return "No";
        } // end else
    } // end predict()
}

/////////////////////////// level 1: hardcoded values ////////////////////////////////////
    /*
    private void setProbabilities()
    // method initializes probabilities where values
    // are calculated and hard coded here from frequency table
    {
        // total "Yes" count = 97
        // total "No" count = 103

        // for features: "AM,Weekday,Clear,Close"
        yesProbability.put("AM,Weekday,Clear,Close", 0.0618); // P(features|Yes) = 6/97 -> 0.0618
        noProbability.put("AM,Weekday,Clear,Close", 0.2427); // P(features|No) = 25/103 -> 0.2427

        // for features: "AM,Weekday,Clear,Far"
        yesProbability.put("AM,Weekday,Clear,Far", 0.0721); // P(features|Yes) = 7/97 -> 0.0721
        noProbability.put("AM,Weekday,Clear,Far", 0.1844); // P(features|No) = 19/103 -> 0.1844

        // for features: "AM,Weekday,Rain,Close"
        yesProbability.put("AM,Weekday,Rain,Close", 0.1649); // P(features|Yes) = 16/97 -> 0.1649
        noProbability.put("AM,Weekday,Rain,Close", 0.0291); // P(features|No) = 3/103 -> 0.0291

        // for features: "AM,Weekday,Rain,Far"
        yesProbability.put("AM,Weekday,Rain,Far", 0.1030); // P(features|Yes) = 10/97 -> 0.1030
        noProbability.put("AM,Weekday,Rain,Far", 0.0097); // P(features|No) = 1/103 -> 0.0097

        // for features: "AM,Weekend,Clear,Close"
        yesProbability.put("AM,Weekend,Clear,Close", 0.0103); // P(features|Yes) = 1/97 -> 0.0103
        noProbability.put("AM,Weekend,Clear,Close", 0.0776); // P(features|No) = 8/103 -> 0.0776

        // for features: "AM,Weekend,Clear,Far"
        yesProbability.put("AM,Weekend,Clear,Far", 0.0103); // P(features|Yes) = 1/97 -> 0.0103
        noProbability.put("AM,Weekend,Clear,Far", 0.0485); // P(features|No) = 5/103 -> 0.0485

        // for features: "AM,Weekend,Rain,Close"
        yesProbability.put("AM,Weekend,Rain,Close", 0.0309); // P(features|Yes) = 3/97 -> 0.0309
        noProbability.put("AM,Weekend,Rain,Close", 0.0194); // P(features|No) = 2/103 -> 0.0194

        // for features: "AM,Weekend,Rain,Far"
        yesProbability.put("AM,Weekend,Rain,Far", 0.0309); // P(features|Yes) = 3/97 -> 0.0309
        noProbability.put("AM,Weekend,Rain,Far", 0.0); // P(features|No) = 0/103 -> 0

        // for features: "PM,Weekday,Clear,Close"
        yesProbability.put("PM,Weekday,Clear,Close", 0.0515); // P(features|Yes) = 5/97 -> 0.0515
        noProbability.put("PM,Weekday,Clear,Close", 0.1650); // P(features|No) = 17/103 -> 0.1650

        // for features: "PM,Weekday,Clear,Far"
        yesProbability.put("PM,Weekday,Clear,Far", 0.0309); // P(features|Yes) = 3/97 -> 0.0309
        noProbability.put("PM,Weekday,Clear,Far", 0.0873); // P(features|No) = 9/103 -> 0.0873

        // for features: "PM,Weekday,Rain,Close"
        yesProbability.put("PM,Weekday,Rain,Close", 0.0927); // P(features|Yes) = 9/97 -> 0.0927
        noProbability.put("PM,Weekday,Rain,Close", 0.0194); // P(features|No) = 2/103 -> 0.0194

        // for features: "PM,Weekday,Rain,Far"
        yesProbability.put("PM,Weekday,Rain,Far", 0.1340); // P(features|Yes) = 13/97 -> 0.1340
        noProbability.put("PM,Weekday,Rain,Far", 0.0194); // P(features|No) = 2/103 -> 0.0194

        // for features: "PM,Weekend,Clear,Close"
        yesProbability.put("PM,Weekend,Clear,Close", 0.0206); // P(features|Yes) = 2/97 -> 0.0206
        noProbability.put("PM,Weekend,Clear,Close", 0.0388); // P(features|No) = 4/103 -> 0.0388

        // for features: "PM,Weekend,Clear,Far"
        yesProbability.put("PM,Weekend,Clear,Far", 0.0309); // P(features|Yes) = 3/97 -> 0.0309
        noProbability.put("PM,Weekend,Clear,Far", 0.0485); // P(features|No) = 5/103 -> 0.0485

        // for features: "PM,Weekend,Rain,Close"
        yesProbability.put("PM,Weekend,Rain,Close", 0.0824); // P(features|Yes) = 8/97 -> 0.0824
        noProbability.put("PM,Weekend,Rain,Close", 0.0); // P(features|No) = 0/103 -> 0.0

        // for features: "PM,Weekend,Rain,Far"
        yesProbability.put("PM,Weekend,Rain,Far", 0.0721); // P(features|Yes) = 7/97 -> 0.0721
        noProbability.put("PM,Weekend,Rain,Far", 0.0097); // P(features|No) = 1/103 -> 0.0097
    } */
