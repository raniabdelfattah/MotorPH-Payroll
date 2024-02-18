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
        String inputFile1 = "C:\\Users\\USER\\Downloads\\Payroll System.csv\\EmployeeDetails.csv";
        String inputFile2 = "C:\\Users\\USER\\Downloads\\Payroll System.csv\\MonthlyHours.csv";
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

                // read employee details from inputFile1
                boolean foundEmployeeID = false;
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
                            foundEmployeeID = true;
                            // extract employee details
                            String employeeName1 = employeeDetails[1].trim();
                            String employeeName2 = employeeDetails[2].trim();
                            String employeeSSS = employeeDetails[3].trim();
                            String employeeStatus = employeeDetails[4].trim();
                            String employeePosition = employeeDetails[5].trim();
                            
                            System.out.println("Employee Last Name: " + employeeName1);
                            System.out.println("Employee First Name: " + employeeName2);
                            System.out.println("SSS #: " + employeeSSS);
                            System.out.println("Status: " + employeeStatus);
                            System.out.println("Position: " + employeePosition);
                            System.out.println("------------------");
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!foundEmployeeID) {
                    System.out.println("Employee ID not found.");
                    continue;
                }

                // prompt for month
                System.out.print("Enter the Month: ");
                String enteredMonth = scanner.nextLine();

                // read hours worked and hourly rate from inputFile2
                BufferedReader monthlyHoursReader = null;
                try {
                    monthlyHoursReader = new BufferedReader(new FileReader(inputFile2));
                    String firstLine = monthlyHoursReader.readLine();
                    String[] months = firstLine.split(",");
                    
                    int monthIndex = -1;
                    for (int i = 1; i < months.length; i++) {
                        if (enteredMonth.equalsIgnoreCase(months[i].trim())) {
                            monthIndex = i;
                            break;
                        }
                    }
                    
                    if (monthIndex == -1) {
                        System.out.println("Month not found.");
                        continue;
                    }
                    
                    while ((line = monthlyHoursReader.readLine()) != null) {
                        String[] row = line.split(",");
                        int currentEmployeeID = Integer.parseInt(row[0].trim());
                        
                        if (currentEmployeeID == employeeID) {
                            foundEmployeeID = true;
                            // display hours worked for the entered month
                            System.out.println("Hours Worked in " + enteredMonth + ": " + row[monthIndex].trim());
                            System.out.println("Hourly Rate: " + row[13].trim()); 
                            double monthlyHours = Double.parseDouble(row[monthIndex].trim());
                            double employeeRate = Double.parseDouble(row[13].trim());

                            double totalPay = monthlyHours * employeeRate;
                            System.out.println("Gross Wage: " + new DecimalFormat("#.##").format(totalPay));
                            break;
                        }
                    }
                    
                    if (!foundEmployeeID) {
                        System.out.println("Employee ID not found.");
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
