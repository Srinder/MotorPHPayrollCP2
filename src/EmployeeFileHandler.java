import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * EmployeeFileHandler - Manages employee data stored in a CSV file.
 * This class provides methods to load, save, update, and delete employee records.
 */
public class EmployeeFileHandler {
    private static final String FILE_PATH = "src/data/employee_info.csv"; // Path to the employee data file.

    /**
     * Loads all employees from the CSV file into a List.
     * Parses each row and creates Employee objects.
     *
     * @return List<Employee> - A list of employees retrieved from the file.
     */
    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean skipHeader = true;

            while ((line = reader.readLine()) != null) {
                if (skipHeader) { skipHeader = false; continue; }

                String[] rowData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (rowData.length < 20) {
                    System.err.println("Skipping invalid employee entry (Incorrect column count): " + Arrays.toString(rowData));
                    continue; // Ensure all required fields exist before parsing.
                }

                try {
                    int employeeNumber = Integer.parseInt(rowData[0].trim());
                    String lastName = rowData[1].trim();
                    String firstName = rowData[2].trim();
                    String phoneNumber = rowData[3].trim();
                    String status = rowData[4].trim();
                    String position = rowData[5].trim();
                    String supervisor = rowData[6].trim();
                    String address = rowData[7].trim().replaceAll("^\"|\"$", "");  // Removes leading & trailing quotes
                    String sssNumber = rowData[8].trim().isEmpty() ? "NA" : rowData[8].trim();
                    String philHealthNumber = rowData[9].trim().isEmpty() ? "NA" : rowData[9].trim();
                    String tinNumber = rowData[10].trim().isEmpty() ? "NA" : rowData[10].trim();
                    String pagIbigNumber = rowData[11].trim().isEmpty() ? "NA" : rowData[11].trim();
                    double basicSalary = Double.parseDouble(rowData[12].trim()); 
                    double riceSubsidy = Double.parseDouble(rowData[13].trim());
                    double phoneAllowance = Double.parseDouble(rowData[14].trim());
                    double clothingAllowance = Double.parseDouble(rowData[15].trim());
                    double grossSemiMonthlyRate = Double.parseDouble(rowData[16].trim());
                    double hourlyRate = Double.parseDouble(rowData[17].trim());
                    double withholdingTax = Double.parseDouble(rowData[18].trim());
                    String birthday = rowData[19].trim().isEmpty() ? "NA" : rowData[19].trim(); // Corrected placement


                    employees.add(new Employee(employeeNumber, lastName, firstName, phoneNumber, status, position, supervisor, address,
                        birthday, sssNumber, philHealthNumber, tinNumber, pagIbigNumber, basicSalary, riceSubsidy, phoneAllowance, clothingAllowance,
                        grossSemiMonthlyRate, hourlyRate, withholdingTax));

                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid employee entry (Number format issue): " + Arrays.toString(rowData));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Unable to load employee data!", "File Read Error", JOptionPane.ERROR_MESSAGE);
        }

        return employees;
    }

    /**
     * Parses a double safely, defaulting to 0.0 if conversion fails.
     */
    private static double parseDouble(String value) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Saves a new employee record to the CSV file.
     *
     * @param employee - The employee object to save.
     */
    public static void saveEmployee(Employee employee) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(formatEmployeeData(employee) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an employee record based on the given employee number.
     */
    public static void deleteEmployee(int empNum) {
        List<Employee> employees = loadEmployees();
        employees.removeIf(emp -> emp.getEmployeeNumber() == empNum);
        writeEmployeeListToFile(employees);
    }

    /**
     * Updates an existing employee's information.
     */
    public static void updateEmployee(Employee updatedEmployee) {
        List<Employee> employees = loadEmployees();

        for (Employee emp : employees) {
            if (emp.getEmployeeNumber() == updatedEmployee.getEmployeeNumber()) {
                emp.setLastName(updatedEmployee.getLastName());
                emp.setFirstName(updatedEmployee.getFirstName());
                emp.setPhoneNumber(updatedEmployee.getPhoneNumber());
                emp.setStatus(updatedEmployee.getStatus());
                emp.setPosition(updatedEmployee.getPosition());
                emp.setSupervisor(updatedEmployee.getSupervisor());
                emp.setAddress(updatedEmployee.getAddress());
                emp.setBirthday(updatedEmployee.getBirthday()); // Ensure Birthday is updated
                emp.setSssNumber(updatedEmployee.getSssNumber());
                emp.setPhilHealthNumber(updatedEmployee.getPhilHealthNumber());
                emp.setTinNumber(updatedEmployee.getTinNumber());
                emp.setPagIbigNumber(updatedEmployee.getPagIbigNumber());
                emp.setBasicSalary(updatedEmployee.getBasicSalary());
                emp.setRiceSubsidy(updatedEmployee.getRiceSubsidy());
                emp.setPhoneAllowance(updatedEmployee.getPhoneAllowance());
                emp.setClothingAllowance(updatedEmployee.getClothingAllowance());
                emp.setGrossSemiMonthlyRate(updatedEmployee.getGrossSemiMonthlyRate());
                emp.setHourlyRate(updatedEmployee.getHourlyRate());
                emp.setWithholdingTax(updatedEmployee.getWithholdingTax());

                break;
            }
        }
        writeEmployeeListToFile(employees);
    }

    /**
     * Writes the updated employee list back to the CSV file.
     */
    private static void writeEmployeeListToFile(List<Employee> employees) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("EmpNum,LastName,FirstName,PhoneNumber,Status,Position,Supervisor,Address,Birthday,SSS,PHILHEALTH,TIN,PAGIBIG,Basic Salary,Rice Subsidy,Phone Allowance,Clothing Allowance,Gross Semi-monthly Rate,Hourly Rate,Withholding Tax\n");

            for (Employee emp : employees) {
                writer.write(formatEmployeeData(emp) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats an employee object into a properly structured CSV row.
     */
    private static String formatEmployeeData(Employee employee) {
    return String.join(",",
        String.valueOf(employee.getEmployeeNumber()), employee.getLastName(), employee.getFirstName(),
        employee.getPhoneNumber(), employee.getStatus(), employee.getPosition(), employee.getSupervisor(),
        employee.getAddress(), employee.getSssNumber(), employee.getPhilHealthNumber(),
        employee.getTinNumber(), employee.getPagIbigNumber(), 
        String.valueOf(employee.getBasicSalary()), String.valueOf(employee.getRiceSubsidy()),
        String.valueOf(employee.getPhoneAllowance()), String.valueOf(employee.getClothingAllowance()),
        String.valueOf(employee.getGrossSemiMonthlyRate()), String.valueOf(employee.getHourlyRate()),
        String.valueOf(employee.getWithholdingTax()), employee.getBirthday());  // Move Birthday to the end
}

}
