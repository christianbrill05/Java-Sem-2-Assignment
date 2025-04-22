package Assignment;

import java.util.HashMap;

public class FlightDelayPredictor // using Naive Bayes Predictor
{
    private double priorYes;
    private double priorNo;
    private HashMap<String, Double> yesProbability;
    private HashMap<String, Double> noProbability;

    // constructor for FlightDelayPredictor
    public FlightDelayPredictor(double priorYes, double priorNo, HashMap<String, Double> yesProbability, HashMap<String, Double> noProbability)
    {
        this.priorYes = 0.485; // from frequency table, yesCount/total = yesProbability -> 97/200 = 0.485
        this.priorNo = 0.515;// noCount/total = yesProbability -> 103/200 = 0.515
        this.yesProbability = new HashMap<>();
        this.noProbability = new HashMap<>();
    }

    public void Probabilities() // probabilities are hard coded here from frequency table
    {
        // total "Yes" count = 97
        // total "No" count = 103

        // for features: "AM,Weekday,Clear,Close"
        yesProbability.put("AM,Weekday,Clear,Close", 0.0618); // P(features|Yes) = 6/97 -> 0.0618
        noProbability.put("AM,Weekday,Clear,Close", 0.2427); // P(features|No) = 25/103 -> 0.2427

        // for features: "AM,Weekday,Clear,Close"
        yesProbability.put("AM,Weekday,Clear,Close", 0.0618); // P(features|Yes) = 6/97 -> 0.0618
        noProbability.put("AM,Weekday,Clear,Close", 0.2427); // P(features|No) = 25/103 -> 0.2427

    }

}
