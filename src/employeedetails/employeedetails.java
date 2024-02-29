package employeedetails;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class employeedetails {
    public static void main(String[] args) throws NumberFormatException, IOException {

        // CSV file paths
        String EmployeeDetails = "C:\\Users\\USER\\Downloads\\CPROG\\MotorPH Overall Employee Data_Helena's Copy - Employee Details (rev).csv";
        String HoursWorkedMonthly = "C:\\Users\\USER\\Downloads\\CPROG\\MotorPH Overall Employee Data_Helena's Copy - Hours Worked Monthly (edited) (1).csv";
        String HourlyRate = "C:\\Users\\USER\\Downloads\\CPROG\\Group 11 Official File - Hourly Rate.csv";
        String GrossWage = "C:\\Users\\USER\\Downloads\\CPROG\\MotorPH Overall Employee Data_Helena's Copy - Gross Salary.csv";
        String SSS = "C:\\Users\\USER\\Downloads\\CPROG\\MotorPH Overall Employee Data_Helena's Copy - LATE DEDUCTION.csv";
        String PagIbig = "C:\\Users\\USER\\Downloads\\CPROG\\Group 11 Official File - PAG-IBIG.csv";
        String PhilHealth = "C:\\Users\\USER\\Downloads\\CPROG\\Group 11 Official File - PHILHEALTH.csv";
        String WithholdingTax = "C:\\Users\\USER\\Downloads\\CPROG\\Group 11 Official File - WITHHOLDING TAX.csv";
        String LateDeductions = "C:\\Users\\USER\\Downloads\\CPROG\\MotorPH Overall Employee Data_Helena's Copy - LATE DEDUCTION.csv";
        String Allowances = "C:\\Users\\USER\\Downloads\\CPROG\\MotorPH Overall Employee Data_Helena's Copy - Allowances.csv";
        String NetWage = "C:\\Users\\USER\\Downloads\\CPROG\\MotorPH Overall Employee Data_Helena's Copy - NET PAY.csv"; 
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

                int employeeID;
                try {
                    employeeID = Integer.parseInt(userInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid Employee ID.");
                    continue;
                }

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
                            String employeeSupervisor = "N/A";
                            if (employeeDetails.length >= 9) {
                                employeeSupervisor = employeeDetails[7] + " " + employeeDetails[8].trim();
                            } //"N/A" result on console if employee has no immediate supervisor. [7] and [8] are there to bypass the commas of supervisors first and last name.

                            System.out.println("\n----------Employee Details----------");
                            System.out.println("\nEmployee Last Name         : " + employeeLastName);
                            System.out.println("Employee First Name        : " + employeeFirstName);
                            System.out.println("Birthday                   : " + employeeBirthday);
                            System.out.println("Phone Number               : " + employeePhoneNumber);
                            System.out.println("Status                     : " + employeeStatus);
                            System.out.println("Position                   : " + employeePosition);
                            System.out.println("Immediate Supervisor       : " + employeeSupervisor);
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Error: Employee details file not found.");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("Error reading employee details.");
                    e.printStackTrace();
                }

                if (!foundEmployeeID) {
                    System.out.println("Employee ID not found.");
                    continue;
                }
                // prompt for month
                System.out.print("\nEnter the Month: ");
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
                            System.out.println("\n----------Payroll Details----------");
                            System.out.println("\nHours Worked: " + row[monthIndex].trim());

                            // read hourly rate from HourlyRate file
                            try (BufferedReader hourlyRateReader = new BufferedReader(new FileReader(HourlyRate))) {
                                // skip the header line
                                hourlyRateReader.readLine();

                                String hourlyRateLine;
                                while ((hourlyRateLine = hourlyRateReader.readLine()) != null) {
                                    String[] hourlyRateRow = hourlyRateLine.split(",");
                                    int currentEmployeeIDHourlyRate = Integer.parseInt(hourlyRateRow[0].trim());

                                    if (currentEmployeeIDHourlyRate == employeeID) {
                                        // display hourly rate
                                        double hourlyRateValue = Double.parseDouble(hourlyRateRow[3].trim()); 
                                        System.out.println("Hourly Rate of Employee    : " + hourlyRateValue);
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("Error: Hourly rate file not found.");
                                e.printStackTrace();
                            } catch (IOException e) {
                                System.out.println("Error reading hourly rate.");
                                e.printStackTrace();
                            }

                            // read gross wage from GrossWage file
                            try (BufferedReader grossWageReader = new BufferedReader(new FileReader(GrossWage))) {
                                // skip the header line
                                grossWageReader.readLine();

                                String grossWageLine;
                                while ((grossWageLine = grossWageReader.readLine()) != null) {
                                    String[] grossWageRow = grossWageLine.split(",");
                                    int currentEmployeeIDGrossWage = Integer.parseInt(grossWageRow[0].trim());

                                    if (currentEmployeeIDGrossWage == employeeID) {
                                        // display gross wage for the entered month
                                        double grossWageValue = Double.parseDouble(grossWageRow[monthIndex].trim()); 
                                        System.out.println("Gross Wage                 : " + grossWageValue);
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("Error: Gross wage file not found.");
                                e.printStackTrace();
                            } catch (IOException e) {
                                System.out.println("Error reading gross wage.");
                                e.printStackTrace();
                            }

                            // read SSS contribution from SSS file
                            try (BufferedReader sssReader = new BufferedReader(new FileReader(SSS))) {
                                // skip the header line
                                sssReader.readLine();

                                String sssLine;
                                while ((sssLine = sssReader.readLine()) != null) {
                                    String[] sssRow = sssLine.split(",");
                                    int currentEmployeeIDSSS = Integer.parseInt(sssRow[0].trim());

                                    if (currentEmployeeIDSSS == employeeID) {
                                        // display SSS contribution for the entered month
                                        double sssValue = Double.parseDouble(sssRow[monthIndex].trim()); 
                                        System.out.println("SSS Contribution           : " + sssValue);
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("Error: SSS file not found.");
                                e.printStackTrace();
                            } catch (IOException e) {
                                System.out.println("Error reading SSS contribution.");
                                e.printStackTrace();
                            }

                            // read Pag-IBIG contribution from Pagibig file
                            try (BufferedReader pagibigReader = new BufferedReader(new FileReader(PagIbig))) {
                                // skip the header line
                                pagibigReader.readLine();

                                String pagibigLine;
                                while ((pagibigLine = pagibigReader.readLine()) != null) {
                                    String[] pagibigRow = pagibigLine.split(",");
                                    int currentEmployeeIDPagibig = Integer.parseInt(pagibigRow[0].trim());

                                    if (currentEmployeeIDPagibig == employeeID) {
                                        // display Pag-IBIG contribution for the entered month
                                        double pagibigValue = Double.parseDouble(pagibigRow[monthIndex].trim());
                                        System.out.println("Pag-IBIG Contribution      : " + pagibigValue);
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("Error: Pag-IBIG file not found.");
                                e.printStackTrace();
                            } catch (IOException e) {
                                System.out.println("Error reading Pag-IBIG contribution.");
                                e.printStackTrace();
                            }

                            // read PhilHealth contribution from PhilHealth file
                            try (BufferedReader philhealthReader = new BufferedReader(new FileReader(PhilHealth))) {
                                // skip the header line
                                philhealthReader.readLine();

                                String philhealthLine;
                                while ((philhealthLine = philhealthReader.readLine()) != null) {
                                    String[] philhealthRow = philhealthLine.split(",");
                                    int currentEmployeeIDPhilhealth = Integer.parseInt(philhealthRow[0].trim());

                                    if (currentEmployeeIDPhilhealth == employeeID) {
                                        // display PhilHealth contribution for the entered month
                                        double philhealthValue = Double.parseDouble(philhealthRow[monthIndex].trim());
                                        System.out.println("PhilHealth Contribution    : " + philhealthValue);
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("Error: PhilHealth file not found.");
                                e.printStackTrace();
                            } catch (IOException e) {
                                System.out.println("Error reading PhilHealth contribution.");
                                e.printStackTrace();
                            }

                            // read Late Deductions from LateDeductions file
                            try (BufferedReader lateDeductionsReader = new BufferedReader(new FileReader(LateDeductions))) {
                                // Skip header
                                lateDeductionsReader.readLine();
                                String lateDeductionsLine;
                                while ((lateDeductionsLine = lateDeductionsReader.readLine()) != null) {
                                    String[] lateDeductionsRow = lateDeductionsLine.split(",");
                                    int currentEmployeeIDLateDeductions = Integer.parseInt(lateDeductionsRow[0].trim());
                                    if (currentEmployeeIDLateDeductions == employeeID) {
                                        System.out.println("Late Deductions            : " + lateDeductionsRow[monthIndex].trim());
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("Error: Late deductions file not found.");
                                e.printStackTrace();
                            } catch (IOException e) {
                                System.out.println("Error reading late deductions.");
                                e.printStackTrace();
                            }

                            // read Withholding Tax from WithholdingTax file
                            try (BufferedReader withholdingTaxReader = new BufferedReader(new FileReader(WithholdingTax))) {
                                // skip the header line
                                withholdingTaxReader.readLine();

                                String withholdingTaxLine;
                                while ((withholdingTaxLine = withholdingTaxReader.readLine()) != null) {
                                    String[] withholdingTaxRow = withholdingTaxLine.split(",");
                                    int currentEmployeeIDWithholdingTax = Integer.parseInt(withholdingTaxRow[0].trim());

                                    if (currentEmployeeIDWithholdingTax == employeeID) {
                                        // display Withholding Tax for the entered month
                                        double withholdingTaxValue = Double.parseDouble(withholdingTaxRow[monthIndex].trim());
                                        System.out.println("Withholding Tax            : " + withholdingTaxValue);
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("Error: Withholding tax file not found.");
                                e.printStackTrace();
                            } catch (IOException e) {
                                System.out.println("Error reading withholding tax.");
                                e.printStackTrace();
                            }

                            // Read Net Wage from NetWage file
                            try (BufferedReader netWageReader = new BufferedReader(new FileReader(NetWage))) {
                                // skip the header line
                                netWageReader.readLine();

                                String netWageLine;
                                while ((netWageLine = netWageReader.readLine()) != null) {
                                    String[] netWageRow = netWageLine.split(",");
                                    int currentEmployeeIDNetWage = Integer.parseInt(netWageRow[0].trim());

                                    if (currentEmployeeIDNetWage == employeeID) {
                                        // display Net Wage for the entered month
                                        double netWageValue = Double.parseDouble(netWageRow[monthIndex].trim());
                                        System.out.println("\nNet Wage                   : " + netWageValue);
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("Error: Net wage file not found.");
                                e.printStackTrace();
                            } catch (IOException e) {
                                System.out.println("Error reading net wage.");
                                e.printStackTrace();
                            }

                            // Display Non-Taxable Income (Allowances)
                            try (BufferedReader allowancesReader = new BufferedReader(new FileReader(Allowances))) {
                                allowancesReader.readLine();
                                String allowancesLine;
                                while ((allowancesLine = allowancesReader.readLine()) != null) {
                                    String[] allowancesRow = allowancesLine.split(",");
                                    int currentEmployeeIDAllowances = Integer.parseInt(allowancesRow[0].trim());
                                    if (currentEmployeeIDAllowances == employeeID) {
                                        System.out.println("\nNon-Taxable Income (Allowances)");
                                        System.out.println("Rice subsidy               : " + allowancesRow[1].trim());
                                        System.out.println("Phone allowance            : " + allowancesRow[2].trim());
                                        System.out.println("Clothing allowance         : " + allowancesRow[3].trim());
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("Error: Allowances file not found.");
                                e.printStackTrace();
                            } catch (IOException e) {
                                System.out.println("Error reading allowances.");
                                e.printStackTrace();
                            }

                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Error: Monthly hours worked file not found.");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("Error reading monthly hours worked.");
                    e.printStackTrace();
                }
            }
        }
    }
}
