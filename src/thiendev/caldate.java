package thiendev;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class caldate {
    public static void main(String[] args) {
        // Load trip data
        try {
            BufferedReader tripReader = new BufferedReader(new FileReader("trip_database.txt"));
            String tripLine;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            while ((tripLine = tripReader.readLine()) != null) {
                String[] tripData = tripLine.split(", ");
                if (tripData.length >= 5) {
                    String departureDateStr = tripData[3];
                    String arrivalDateStr = tripData[4];

                    try {
                        Date departureDate = dateFormat.parse(departureDateStr);
                        Date arrivalDate = dateFormat.parse(arrivalDateStr);

                        long durationInMilliseconds = arrivalDate.getTime() - departureDate.getTime();
                        long durationInDays = durationInMilliseconds / (24 * 60 * 60 * 1000);

                        System.out.println("Trip " + tripData[0] + " took " + durationInDays + " days.");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            tripReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
