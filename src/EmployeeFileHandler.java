import com.opencsv.CSVReader; // Used for reading CSV files.
import com.opencsv.CSVWriter; // Used for writing CSV files.
import java.io.*;             // Core Java I/O classes for file operations.
import java.util.*;           // Core utility classes, including List and Optional.

/**
 * `EmployeeFileHandler` manages reading from and writing to the `employee_info.csv` file.
 * It handles loading, retrieving, saving, deleting, and updating employee records.
 */
public class EmployeeFileHandler {
    // Defines the path to the employee information CSV file.
    private static final String FILE_PATH = "src/data/employee_info.csv";

    /**
     * Loads all employee records from the CSV file.
     * Skips the header row and any invalid entries.
     * @return A `List` of `Employee` objects.
     */
    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>(); // List to store loaded employees.

        try (CSVReader reader = new CSVReader(new FileReader(FILE_PATH))) {
            List<String[]> records = reader.readAll(); // Read all rows from the CSV.
            boolean skipHeader = true; // Flag to skip the first row (header).

            for (String[] rowData : records) {
                if (skipHeader) {
                    skipHeader = false; // After skipping, set to false.
                    continue;           // Move to the next row.
                }

                // Basic validation: ensure row has enough columns.
                if (rowData.length < 20) {
                    System.err.println("Skipping invalid employee entry (incorrect column count): " + Arrays.toString(rowData));
                    continue;
                }

                try {
                    // Parse employee data from each column.
                    int employeeNumber = Integer.parseInt(rowData[0].trim());
                    String lastName = rowData[1].trim();
                    String firstName = rowData[2].trim();
                    String phoneNumber = rowData[3].trim();
                    String status = rowData[4].trim();
                    String position = rowData[5].trim();
                    String supervisor = rowData[6].trim();
                    String address = rowData[7].trim();

                    // Handle optional fields that might be empty or "NA".
                    String sssNumber = rowData[8].trim().isEmpty() ? "NA" : rowData[8].trim();
                    String philHealthNumber = rowData[9].trim().isEmpty() ? "NA" : rowData[9].trim();
                    String tinNumber = rowData[10].trim().isEmpty() ? "NA" : rowData[10].trim();
                    String pagIbigNumber = rowData[11].trim().isEmpty() ? "NA" : rowData[11].trim();
                    String birthday = rowData[19].trim().isEmpty() ? "NA" : rowData[19].trim();

                    // Parse numeric fields using a helper method to handle errors.
                    double basicSalary = parseDouble(rowData[12]);
                    double riceSubsidy = parseDouble(rowData[13]);
                    double phoneAllowance = parseDouble(rowData[14]);
                    double clothingAllowance = parseDouble(rowData[15]);
                    double grossSemiMonthlyRate = parseDouble(rowData[16]);
                    double hourlyRate = parseDouble(rowData[17]);
                    double withholdingTax = parseDouble(rowData[18]);

                    // Create a new Employee object and add to the list.
                    employees.add(new Employee(employeeNumber, lastName, firstName, phoneNumber,
                        status, position, supervisor, address, sssNumber, philHealthNumber,
                        tinNumber, pagIbigNumber, basicSalary, riceSubsidy, phoneAllowance,
                        clothingAllowance, grossSemiMonthlyRate, hourlyRate, withholdingTax, birthday));

                } catch (NumberFormatException e) {
                    // Log an error if a numeric field cannot be parsed.
                    System.err.println("Skipping invalid employee entry (number format issue): " + Arrays.toString(rowData));
                }
            }
        } catch (Exception e) {
            // Print stack trace for any other I/O or CSV reading errors.
            e.printStackTrace();
        }

        return employees; // Return the list of employees.
    }

    /**
     * Retrieves a single employee by their employee number.
     * @param empNumber The employee number to search for.
     * @return An `Optional<Employee>` which will contain the employee if found, or be empty otherwise.
     */
    public static Optional<Employee> getEmployee(int empNumber) {
        // Loads all employees and uses a stream to filter and find the first matching employee.
        return loadEmployees().stream()
            .filter(e -> e.getEmployeeNumber() == empNumber) // Filter by employee number.
            .findFirst(); // Return the first match as an Optional.
    }

    /**
     * Appends a new employee record to the CSV file.
     * @param employee The `Employee` object to save.
     */
    public static void saveEmployee(Employee employee) {
        // Open the CSV file in append mode (`true` in FileWriter).
        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE_PATH, true))) {
            writer.writeNext(formatEmployeeData(employee)); // Write the employee data as a new row.
        } catch (IOException e) {
            e.printStackTrace(); // Print error if writing fails.
        }
    }

    /**
     * Deletes an employee record from the CSV file based on their employee number.
     * It re-writes the entire file without the deleted employee.
     * @param empNum The employee number of the employee to delete.
     */
    public static void deleteEmployee(int empNum) {
        List<Employee> employees = loadEmployees(); // Load all employees.
        employees.removeIf(emp -> emp.getEmployeeNumber() == empNum); // Remove the matching employee.
        writeEmployeeListToFile(employees); // Write the modified list back to the file.
    }

    /**
     * Updates an existing employee's information in the CSV file.
     * Only non-empty fields in the `updatedEmployee` object will overwrite existing data.
     * @param updatedEmployee The `Employee` object containing updated information (must have an employee number).
     */
    public static void updateEmployee(Employee updatedEmployee) {
        List<Employee> employees = loadEmployees(); // Load all employees.
        boolean employeeFound = false; // Flag to check if the employee was found.

        for (Employee emp : employees) {
            if (emp.getEmployeeNumber() == updatedEmployee.getEmployeeNumber()) {
                // Update fields only if the new value is not empty.
                emp.setLastName(updatedEmployee.getLastName().trim().isEmpty() ? emp.getLastName() : updatedEmployee.getLastName().trim());
                emp.setFirstName(updatedEmployee.getFirstName().trim().isEmpty() ? emp.getFirstName() : updatedEmployee.getFirstName().trim());
                emp.setPhoneNumber(updatedEmployee.getPhoneNumber().trim().isEmpty() ? emp.getPhoneNumber() : updatedEmployee.getPhoneNumber().trim());
                emp.setStatus(updatedEmployee.getStatus().trim().isEmpty() ? emp.getStatus() : updatedEmployee.getStatus().trim());
                emp.setPosition(updatedEmployee.getPosition().trim().isEmpty() ? emp.getPosition() : updatedEmployee.getPosition().trim());
                emp.setSupervisor(updatedEmployee.getSupervisor().trim().isEmpty() ? emp.getSupervisor() : updatedEmployee.getSupervisor().trim());
                emp.setAddress(updatedEmployee.getAddress().trim().isEmpty() ? emp.getAddress() : updatedEmployee.getAddress().trim());
                emp.setSssNumber(updatedEmployee.getSssNumber().trim().isEmpty() ? emp.getSssNumber() : updatedEmployee.getSssNumber().trim());
                emp.setPhilHealthNumber(updatedEmployee.getPhilHealthNumber().trim().isEmpty() ? emp.getPhilHealthNumber() : updatedEmployee.getPhilHealthNumber().trim());
                emp.setTinNumber(updatedEmployee.getTinNumber().trim().isEmpty() ? emp.getTinNumber() : updatedEmployee.getTinNumber().trim());
                emp.setPagIbigNumber(updatedEmployee.getPagIbigNumber().trim().isEmpty() ? emp.getPagIbigNumber() : updatedEmployee.getPagIbigNumber().trim());
                employeeFound = true; // Mark employee as found.
                break; // Exit loop once employee is updated.
            }
        }

        if (!employeeFound) {
            System.err.println("Error: Employee record not found!");
            return; // Exit if employee wasn't found.
        }

        writeEmployeeListToFile(employees); // Re-write the updated list to the file.
    }

    /**
     * Writes a list of employees (including header) back to the CSV file.
     * This method overwrites the existing file.
     * @param employees The `List` of `Employee` objects to write.
     */
    private static void writeEmployeeListToFile(List<Employee> employees) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE_PATH))) {
            // Write the CSV header row.
            writer.writeNext(new String[]{
                "EmpNum", "LastName", "FirstName", "PhoneNumber", "Status", "Position", "Supervisor", "Address",
                "SSS", "PHILHEALTH", "TIN", "PAGIBIG", "Basic Salary", "Rice Subsidy", "Phone Allowance",
                "Clothing Allowance", "Gross Semi-monthly Rate", "Hourly Rate", "Withholding Tax", "Birthday"
            });

            // Write each employee's data as a row.
            for (Employee emp : employees) {
                writer.writeNext(formatEmployeeData(emp));
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print error if writing fails.
        }
    }

    /**
     * Converts an `Employee` object into a `String` array suitable for writing to a CSV row.
     * @param employee The `Employee` object to format.
     * @return A `String[]` representing the employee's data as a CSV row.
     */
    private static String[] formatEmployeeData(Employee employee) {
        return new String[]{
            String.valueOf(employee.getEmployeeNumber()), employee.getLastName(), employee.getFirstName(),
            employee.getPhoneNumber(), employee.getStatus(), employee.getPosition(), employee.getSupervisor(), employee.getAddress(),
            employee.getSssNumber(), employee.getPhilHealthNumber(), employee.getTinNumber(), employee.getPagIbigNumber(),
            String.valueOf(employee.getBasicSalary()), String.valueOf(employee.getRiceSubsidy()), String.valueOf(employee.getPhoneAllowance()),
            String.valueOf(employee.getClothingAllowance()), String.valueOf(employee.getGrossSemiMonthlyRate()),
            String.valueOf(employee.getHourlyRate()), String.valueOf(employee.getWithholdingTax()), employee.getBirthday()
        };
    }

    /**
     * Safely parses a `double` value, returning `0.0` if the input is invalid, empty, or "N/A".
     * @param value The string to parse.
     * @return The parsed double value, or `0.0` if parsing fails.
     */
    private static double parseDouble(String value) {
        try {
            // If value is "N/A" (case-insensitive) or empty, return 0.0. Otherwise, parse as double.
            return (value.trim().equalsIgnoreCase("N/A") || value.trim().isEmpty()) ? 0.0 : Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Invalid number format detected: " + value);
            return 0.0; // Return 0.0 on parsing error.
        }
    }
}