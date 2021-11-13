package org.campus02;

import java.util.ArrayList;
import java.util.HashMap;

public class CovidManager {

    public static void main(String[] args) {

        /*Incidence stmk20211111 = new Incidence(
                "Steiermark", "2021-11-11", 1300);
        Incidence stmk20211110 = new Incidence(
                "Steiermark", "2021-11-10", 1200);
        Incidence wien20211111 = new Incidence(
                "Wien", "2021-11-11", 5631);
        Incidence wien20211110 = new Incidence(
                "Wien", "2021-11-10", 4832);
        Incidence tirol20211111 = new Incidence(
                "Tirol", "2021-11-11", 999);*/

        ArrayList<Incidence> recordedValues = new ArrayList<>();
        recordedValues = DataProvider.getData();

        System.out.println("Count of recorded Records: " + recordedValues.size());

        // Count der gesamten Fälle
        int countTotalIncidences = 0;
        for (int i = 0; i < recordedValues.size(); i++) {
            Incidence oneValue = recordedValues.get(i);
            //countTotalIncidences += oneValue.getNumber();
            countTotalIncidences = countTotalIncidences + oneValue.getNumber();
        }
        System.out.println("Bisher in Österreich: " + countTotalIncidences + " Fälle");

        countTotalIncidences = 0;
        for (Incidence incidence : recordedValues) {
            // für jeden Incidence in recordedValues
            countTotalIncidences += incidence.getNumber();
        }
        System.out.println("Bisher in Österreich: " + countTotalIncidences + " Fälle");

        int countTotalIncidencesStmk = 0;
        // iter
        for (Incidence recordedValue : recordedValues) {
            if (recordedValue.getState().equals("Steiermark"))
                countTotalIncidencesStmk += recordedValue.getNumber();
        }
        System.out.println("countTotalIncidencesStmk = " + countTotalIncidencesStmk);

        System.out.println("totalIncidencesState = " + groupByState(recordedValues));
        System.out.println("totalIncidences = " + groupByDate(recordedValues));
        System.out.println("highestvalue = " + findHighestValue(recordedValues));
        System.out.println("Average = " + getAverageValueAfter(recordedValues,"2021-01-22"));
        System.out.println("findDayStateWithHighestValues = " + findDayStateWithHighestValues(recordedValues));

    }

    public static HashMap<String, Integer> groupByState(ArrayList<Incidence> recordedValues) {
        // Anzahl der Fälle je Bundesland
        HashMap<String, Integer> totalIncidencesState = new HashMap<>();
        for (Incidence oneElement : recordedValues) {
            /*
                1. Ist mein Key bereits in der Liste
                    2. wenn nein, dann muss ich den Key aufnehmen
                    3. wenn ja, dann muss ich den Wert auslesen, summieren, neu ablegen
             */
            if (totalIncidencesState.containsKey(oneElement.getState())){
                // ja, Fall 3
                int currentValue = totalIncidencesState.get(oneElement.getState());
                currentValue += oneElement.getNumber();
                totalIncidencesState.put(oneElement.getState(), currentValue);
            }
            else {
                // nein, Fall 2
                totalIncidencesState.put(oneElement.getState(),
                        oneElement.getNumber());
            }
        }
        return totalIncidencesState;
    }

    public static HashMap<String, Integer> groupByDate(ArrayList<Incidence> recordedValues) {
            // Summe der Fälle je Tag
        HashMap<String, Integer> totalCasesDay = new HashMap<>();
        for (Incidence oneElement : recordedValues) {

            if (totalCasesDay.containsKey(oneElement.getDate()))
            {
                int sumDay = totalCasesDay.get(oneElement.getDate());
                sumDay += oneElement.getNumber();
                totalCasesDay.put(oneElement.getDate(), sumDay);
            }
            else
            {
                totalCasesDay.put(oneElement.getDate(), oneElement.getNumber()); //output for console
            }
        }
        return totalCasesDay;
    }

    public static int findHighestValue(ArrayList<Incidence> recordedValues) {
        // Es soll der höchste gemeldete Wert gefunden werden
        int highestValue = 0;
        int placeholder = 0;

        //Schleife für alle
        for (Incidence recordedValue : recordedValues)
        {
            //aktueller Wert wird gespeichert
            placeholder = recordedValue.getNumber();

            //is der höchste gespeicherte Wert kleiner als unser aktueller Wert
            if(highestValue < placeholder)
            {
                highestValue = placeholder;
            }
        }
        return highestValue;
    }

    public static int getAverageValueAfter(ArrayList<Incidence> recordedValues, String date) {
        // Es soll der durchschnittliche Wert nach einem bestimmten Datum gefunden werden
        // date ist beispielsweise 2021-08-01
        int counter = 0;
        int sumCases = 0;
        int average = 0;


        for(Incidence recordedValue : recordedValues)
        {
            if(recordedValue.getDate().equals(date))
            {
                counter++;
                sumCases += recordedValue.getNumber();
            }
        }
        average = sumCases/counter;
        return average;
    }

    public static Incidence findDayStateWithHighestValues(ArrayList<Incidence> recordedValues) {
        // Es soll die Meldung (state/date) mit dem höchsten Wert gefunden werden

        int highestCase = 0;
        int currentCase = 0;
        String state = "";
        String date = "";
        for (Incidence recordedValue : recordedValues)
        {
            currentCase = recordedValue.getNumber();
            //höchste gespeicherte Wert kleiner als der aktuelle Wert?
            if(highestCase < currentCase)
            {
                //cases werden gespeichert, date und state werden mitdokumentiert
                highestCase = currentCase;
                date = recordedValue.getDate();
                state = recordedValue.getState();
            }
        }
        //in der return ein neue Incidence erstellt mit dem von oben gespeicherten state, date, highestcase
        return new Incidence(state,date,highestCase);
    }
}
