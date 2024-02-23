package employeedetails;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class employeedetails {
    public static void main(String[] args) throws NumberFormatException, IOException {

        // CSV file path for employee details
        String EmployeeDetails = "C:\\Users\\USER\\Downloads\\Group 11 Official File - Employee Details.csv";
        String HoursWorkedMonthly = "C:\\Users\\USER\\Downloads\\MotorPH Overall Employee Data_Helena's Copy - Hours Worked Monthly (edited).csv";
        String HourlyRate = "C:\\Users\\USER\\Downloads\\Group 11 Official File - Hourly Rate.csv";
        String GrossWage = "C:\\Users\\USER\\Downloads\\MotorPH Overall Employee Data_Helena's Copy - Gross Salary.csv";
        String SSS = "C:\\Users\\USER\\Downloads\\SSS Contribution  - SSS .csv";
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

                // read employee details from EmployeeDetails
                boolean foundEmployeeID = false;
                try (BufferedReader detailsReader = new BufferedReader(new FileReader(EmployeeDetails))) {
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
                            // extract employee details from EmployeeDetails csv file
                            String employeeLastName = employeeDetails[1].trim();
                            String employeeFirstName = employeeDetails[2].trim();
                            String employeeBirthday = employeeDetails[3].trim();
                            String employeePhoneNumber = employeeDetails[4].trim();
                            String employeeStatus = employeeDetails[5].trim();
                            String employeePosition = employeeDetails[6].trim();
                            String employeeSupervisor = employeeDetails[7] + " " + employeeDetails[8].trim();

                            System.out.println("Employee Last Name         : " + employeeLastName);
                            System.out.println("Employee First Name        : " + employeeFirstName);
                            System.out.println("Birthday                   : " + employeeBirthday);
                            System.out.println("Phone Number               : " + employeePhoneNumber);
                            System.out.println("Status                     : " + employeeStatus);
                            System.out.println("Position                   : " + employeePosition);
                            System.out.println("Immediate Supervisor       : " + employeeSupervisor);
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

                // read monthly hours worked from HoursWorkedMonthly file
                BufferedReader monthlyHoursReader = null;
                try {
                    monthlyHoursReader = new BufferedReader(new FileReader(HoursWorkedMonthly));
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

                            // read hourly rate from HourlyRate file
                            BufferedReader hourlyRateReader = null;
                            try {
                                hourlyRateReader = new BufferedReader(new FileReader(HourlyRate));

                                // skip the header line
                                hourlyRateReader.readLine();

                                String hourlyRateLine;
                                while ((hourlyRateLine = hourlyRateReader.readLine()) != null) {
                                    String[] hourlyRateRow = hourlyRateLine.split(",");
                                    int currentEmployeeIDHourlyRate = Integer.parseInt(hourlyRateRow[0].trim());

                                    if (currentEmployeeIDHourlyRate == employeeID) {
                                        // display hourly rate
                                        double hourlyRateValue = Double.parseDouble(hourlyRateRow[3].trim()); 
                                        System.out.println("Hourly Rate of Employee: " + hourlyRateValue);
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if (hourlyRateReader != null) {
                                    try {
                                        hourlyRateReader.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            // read gross wage from GrossWage file
                            BufferedReader grossWageReader = null;
                            try {
                                grossWageReader = new BufferedReader(new FileReader(GrossWage));
                                // skip the header line
                                grossWageReader.readLine();

                                String grossWageLine;
                                while ((grossWageLine = grossWageReader.readLine()) != null) {
                                    String[] grossWageRow = grossWageLine.split(",");
                                    int currentEmployeeIDGrossWage = Integer.parseInt(grossWageRow[0].trim());

                                    if (currentEmployeeIDGrossWage == employeeID) {
                                        // display gross wage for the entered month
                                        double grossWageValue = Double.parseDouble(grossWageRow[monthIndex].trim()); 
                                        System.out.println("Gross Wage in " + enteredMonth + ": " + grossWageValue);
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if (grossWageReader != null) {
                                    try {
                                        grossWageReader.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    
                                }
                            }
                            // read SSS contribution from SSS file
                            BufferedReader sssReader = null;
                            try {
                                sssReader = new BufferedReader(new FileReader(SSS));
                                // skip the header line
                                sssReader.readLine();

                                String sssLine;
                                while ((sssLine = sssReader.readLine()) != null) {
                                    String[] sssRow = sssLine.split(",");
                                    int currentEmployeeIDSSS = Integer.parseInt(sssRow[0].trim());

                                    if (currentEmployeeIDSSS == employeeID) {
                                        // display SSS contribution for the entered month
                                        double sssValue = Double.parseDouble(sssRow[monthIndex].trim()); 
                                        System.out.println("SSS Contribution in " + enteredMonth + ": " + sssValue);
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if (sssReader != null) {
                                    try {
                                        sssReader.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            break;
                        }
                
                    }                
                    }catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (monthlyHoursReader != null) {
                        try {
                            monthlyHoursReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
