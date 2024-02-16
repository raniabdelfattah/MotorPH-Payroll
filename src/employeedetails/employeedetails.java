package employeedetails;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class employeedetails {
    public static void main(String[] args) throws NumberFormatException, IOException {

        // CSV file path for employee details
        String inputFile1 = "C:\\Users\\USER\\Downloads\\Payroll System.csv\\MotorPH Employee Details.csv\\";
        String line; 

        // enter employee ID prompt
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter Employee ID (or type 'exit' to stop): ");
                String userInput = scanner.nextLine();

                // exit prompt
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the program.");
                break;
                    
                }
            int employeeID = Integer.parseInt(userInput);
                
            // display employee details based on Employee ID
            try (BufferedReader detailsReader = new BufferedReader(new FileReader(inputFile1))) {
            boolean isFirstLine = true;
            while ((line = detailsReader.readLine()) != null) {
            if (isFirstLine) {
            isFirstLine = false;
            continue;
                        }

            String[] employeeDetails = line.split(",");
            int currentEmployeeID = Integer.parseInt(employeeDetails[0].trim());

            if (currentEmployeeID == employeeID) {
            // extract from csv and display employee details
              String employeeName1 = employeeDetails[1].trim();
              String employeeName2 = employeeDetails[2].trim();
              String employeeRate = employeeDetails[3].trim();

              System.out.println("Employee Last Name: " + employeeName1);
              System.out.println("Employee First Name: " + employeeName2);
              System.out.println("Hourly Rate: " + employeeRate);
                            
              // prompt for hours worked
              System.out.print("Enter Hours Worked: ");
              double hoursWorked = Double.parseDouble(scanner.nextLine());
                            
              // calculate and display total pay
              double hourlyRate = Double.parseDouble(employeeRate);
              double totalPay = hourlyRate * hoursWorked;
                            
              DecimalFormat df = new DecimalFormat("#.##");
              System.out.println("Gross Wage: " + df.format(totalPay));
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
