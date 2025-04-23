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
        setProbabilities();
    }

    public void setProbabilities()
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
    }

}
