package Assignment;

public class Control
{
    public static void main(String [] args)
    {
        FlightDelayPredictor predictor = new FlightDelayPredictor();
        new GUI_Predictor(predictor);

        // testing FlightDelayPredictor
        /*
        String prediction1 = predictor.predict("AM", "Weekday", "Clear", "Close"); // should be no
        String prediction2 = predictor.predict("PM", "Weekday", "Rain", "Far"); // should be yes
        String prediction3 = predictor.predict("AM", "Weekend", "Rain", "Close"); // depends on probabilities
        String prediction4 = predictor.predict("AM", "Weekday", "Clear", "Close");

        System.out.println("Prediction 1:\nFlight Delayed? " + prediction1); // output: no
        System.out.println("Prediction 2:\nFlight Delayed? " + prediction2); // output: yes
        System.out.println("Prediction 3:\nFlight Delayed? " + prediction3); // output: yes
        System.out.println("Prediction 4:\nFlight Delayed? " + prediction4); // output: yes
        */



    }


}
