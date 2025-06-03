import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * EmployeeFileHandler - Manages employee data stored in a CSV file.
 * Provides methods to load, save, update, and delete employee records in a structured format.
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
                if (line.trim().isEmpty()) continue;

                String[] rowData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (rowData.length < 20) {
                    System.err.println("Skipping invalid employee entry (Incorrect column count): " + Arrays.toString(rowData));
                    continue;
                }

                try {
                    int employeeNumber = Integer.parseInt(rowData[0].trim());
                    String lastName = rowData[1].trim();
                    String firstName = rowData[2].trim();
                    String phoneNumber = rowData[3].trim();
                    String status = rowData[4].trim();
                    String position = rowData[5].trim();
                    String supervisor = rowData[6].trim();
                    String address = rowData[7].trim().replaceAll("^\"|\"$", "");
                    
                    // Handle optional fields with NA default
                    String sssNumber = rowData[8].trim().isEmpty() ? "NA" : rowData[8].trim();
                    String philHealthNumber = rowData[9].trim().isEmpty() ? "NA" : rowData[9].trim();
                    String tinNumber = rowData[10].trim().isEmpty() ? "NA" : rowData[10].trim();
                    String pagIbigNumber = rowData[11].trim().isEmpty() ? "NA" : rowData[11].trim();
                    String birthday = rowData[19].trim().isEmpty() ? "NA" : rowData[19].trim();

                    // Parse numeric fields
                    double basicSalary = parseDouble(rowData[12]);
                    double riceSubsidy = parseDouble(rowData[13]);
                    double phoneAllowance = parseDouble(rowData[14]);
                    double clothingAllowance = parseDouble(rowData[15]);
                    double grossSemiMonthlyRate = parseDouble(rowData[16]);
                    double hourlyRate = parseDouble(rowData[17]);
                    double withholdingTax = parseDouble(rowData[18]);

                    employees.add(new Employee(employeeNumber, lastName, firstName, phoneNumber, 
                        status, position, supervisor, address, sssNumber, philHealthNumber, 
                        tinNumber, pagIbigNumber, basicSalary, riceSubsidy, phoneAllowance, 
                        clothingAllowance, grossSemiMonthlyRate, hourlyRate, withholdingTax, birthday));

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
     * Safely parses a double value, returning 0.0 if the input is invalid or empty.
     */
    private static double parseDouble(String value) {
        try {
            return value.trim().isEmpty() ? 0.0 : Double.parseDouble(value.trim());
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
    boolean employeeFound = false;

    for (Employee emp : employees) {
        if (emp.getEmployeeNumber() == updatedEmployee.getEmployeeNumber()) {
            // ✅ Update ONLY the modified fields, keeping others unchanged
            if (!updatedEmployee.getLastName().isEmpty()) emp.setLastName(updatedEmployee.getLastName());
            if (!updatedEmployee.getFirstName().isEmpty()) emp.setFirstName(updatedEmployee.getFirstName());
            if (!updatedEmployee.getPhoneNumber().isEmpty()) emp.setPhoneNumber(updatedEmployee.getPhoneNumber());
            if (!updatedEmployee.getStatus().isEmpty()) emp.setStatus(updatedEmployee.getStatus());
            if (!updatedEmployee.getPosition().isEmpty()) emp.setPosition(updatedEmployee.getPosition());
            if (!updatedEmployee.getSupervisor().isEmpty()) emp.setSupervisor(updatedEmployee.getSupervisor());
            if (!updatedEmployee.getAddress().isEmpty()) emp.setAddress(updatedEmployee.getAddress());
            if (!updatedEmployee.getSssNumber().isEmpty()) emp.setSssNumber(updatedEmployee.getSssNumber());
            if (!updatedEmployee.getPhilHealthNumber().isEmpty()) emp.setPhilHealthNumber(updatedEmployee.getPhilHealthNumber());
            if (!updatedEmployee.getTinNumber().isEmpty()) emp.setTinNumber(updatedEmployee.getTinNumber());
            if (!updatedEmployee.getPagIbigNumber().isEmpty()) emp.setPagIbigNumber(updatedEmployee.getPagIbigNumber());
            if (updatedEmployee.getBasicSalary() > 0) emp.setBasicSalary(updatedEmployee.getBasicSalary());
            if (updatedEmployee.getRiceSubsidy() > 0) emp.setRiceSubsidy(updatedEmployee.getRiceSubsidy());
            if (updatedEmployee.getPhoneAllowance() > 0) emp.setPhoneAllowance(updatedEmployee.getPhoneAllowance());
            if (updatedEmployee.getClothingAllowance() > 0) emp.setClothingAllowance(updatedEmployee.getClothingAllowance());
            if (updatedEmployee.getGrossSemiMonthlyRate() > 0) emp.setGrossSemiMonthlyRate(updatedEmployee.getGrossSemiMonthlyRate());
            if (updatedEmployee.getHourlyRate() > 0) emp.setHourlyRate(updatedEmployee.getHourlyRate());
            if (updatedEmployee.getWithholdingTax() > 0) emp.setWithholdingTax(updatedEmployee.getWithholdingTax());
            if (!updatedEmployee.getBirthday().isEmpty()) emp.setBirthday(updatedEmployee.getBirthday());

            employeeFound = true;
            break;
        }
    }

    if (!employeeFound) {
        System.err.println("Error: Employee record not found!");
        return;
    }

    writeEmployeeListToFile(employees);  // ✅ Save data correctly without shifting columns
}


    /**
     * Writes the updated employee list back to the CSV file.
     */
    private static void writeEmployeeListToFile(List<Employee> employees) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("EmpNum,LastName,FirstName,PhoneNumber,Status,Position,Supervisor,Address,SSS,PHILHEALTH,TIN,PAGIBIG,Basic Salary,Rice Subsidy,Phone Allowance,Clothing Allowance,Gross Semi-monthly Rate,Hourly Rate,Withholding Tax,Birthday\n");

            for (Employee emp : employees) {
                writer.write(formatEmployeeData(emp) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reloads employee data by fetching fresh records from the CSV.
     * This ensures updated information is reflected when requested.
     */
    public static List<Employee> reloadEmployees() {
        return loadEmployees();  // Simply calls loadEmployees() to refresh the data
    }

    /**
     * Formats an employee object into a properly structured CSV row.
     */
    private static String formatEmployeeData(Employee employee) {
    return String.join(",", 
        String.valueOf(employee.getEmployeeNumber()), employee.getLastName(), employee.getFirstName(),
        employee.getPhoneNumber().isEmpty() ? "N/A" : employee.getPhoneNumber(),  // ✅ Ensure Phone Number isn't blank
        employee.getStatus(), employee.getPosition(), employee.getSupervisor(),
        "\"" + employee.getAddress() + "\"",  // ✅ Wrap in quotes to prevent address splitting
        employee.getSssNumber(), employee.getPhilHealthNumber(),
        employee.getTinNumber(), employee.getPagIbigNumber(),
        String.valueOf(employee.getBasicSalary() == 0.0 ? "N/A" : employee.getBasicSalary()),  // ✅ Prevent unexpected zeros
        String.valueOf(employee.getRiceSubsidy()), String.valueOf(employee.getPhoneAllowance()),
        String.valueOf(employee.getClothingAllowance()), String.valueOf(employee.getGrossSemiMonthlyRate()),
        String.valueOf(employee.getHourlyRate()), String.valueOf(employee.getWithholdingTax()), employee.getBirthday()
    );
}



}
