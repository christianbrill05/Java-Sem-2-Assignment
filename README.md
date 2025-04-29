# Java-Sem-2-Assignment
Assignment for Semester 2 of OOP Java

This frequency table shows how often each permutation appears from the 200 rows of data. The values from this table was used to calculate probabilities to predict flight delay.
There was a total of 200 records in the dataset, 97 of them were "Yes", 103 were "No"
| Departure | Day Type | Weather | Distance | Yes Count | No Count |
|:---------:|:--------:|:-------:|:--------:|:---------:|:--------:|
| AM        | Weekday  | Clear   | Close    | 6         | 25       |
| AM        | Weekday  | Clear   | Far      | 7         | 19       |
| AM        | Weekday  | Rain    | Close    | 16        | 3        |
| AM        | Weekday  | Rain    | Far      | 10        | 1        |
| AM        | Weekend  | Clear   | Close    | 1         | 8        |
| AM        | Weekend  | Clear   | Far      | 1         | 5        |
| AM        | Weekend  | Rain    | Close    | 3         | 2        |
| AM        | Weekend  | Rain    | Far      | 3         | 0        |
| PM        | Weekday  | Clear   | Close    | 5         | 17       |
| PM        | Weekday  | Clear   | Far      | 3         | 9        |
| PM        | Weekday  | Rain    | Close    | 9         | 2        |
| PM        | Weekday  | Rain    | Far      | 13        | 2        |
| PM        | Weekend  | Clear   | Close    | 2         | 4        |
| PM        | Weekend  | Clear   | Far      | 3         | 5        |
| PM        | Weekend  | Rain    | Close    | 8         | 0        |
| PM        | Weekend  | Rain    | Far      | 7         | 1        |

Java Classes: 
  - Control Class:
      - Main class that starts the application by initializing FlightDelayPredictor and GUI_Predictor
  - FlightDelayPredictor
      - Implements Naive Bayes classifier. Calculates probabilities from the dataset, predicts whether a flight is delayed or not, trains the data
        and calculates the accuracy of predictions.
  - GUI_Predictor
      - A GUI that allows users to predict flight delays, train the model, add new data rows to the dataset, and calculate prediction accuracy.

Functionality Included:
  - Level 1:
      - Harcoded probabilities were calculated (commented out, see end of FlightDelayPredictor() class)
  - Level 2:
      - Trains the dataset.
      - Added a button in the GUI to train the dataset.
  - Level 3:
      - GUI allows the user to add more rows to the dataset
      - After adding a row, the data is automatically retrained
  - Level 4:
      - GUI button calculates the accuracy based on a 75/25 stratified split of training/testing data.

Improvements:
  - Make the GUI layout better.
