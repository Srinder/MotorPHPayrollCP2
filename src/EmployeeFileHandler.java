// Required imports for file handling and utilities.
import java.io.*;  // Allows reading and writing files.
import java.util.*;  // Provides List functionality for managing employee records.
import javax.swing.JOptionPane; // Enables pop-up dialogs for error messages.

/**
 * EmployeeFileHandler - Manages employee data stored in a CSV file.
 * This class provides methods to load, save, update, and delete employee records.
 */
public class EmployeeFileHandler {
    private static final String FILE_PATH = "src/data/employee_info.csv"; // Path to the CSV file storing employee data.

    /**
     * Loads all employees from the CSV file into a List.
     * Each row in the file is converted into an Employee object.
     * 
     * @return List<Employee> - A list of employees retrieved from the file.
     */
    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>(); // Initializes an empty list to store employee records.

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) { // Opens the CSV file for reading.
            String line;
            boolean skipHeader = true; // Tracks whether the header row should be skipped.

            while ((line = reader.readLine()) != null) { // Reads each line from the file.
                if (skipHeader) { skipHeader = false; continue; } // Skips the first row containing column names.

                // Splits the CSV row while correctly handling quoted values.
                String[] rowData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (rowData.length < 7) continue; // Ensures the row has the required number of fields.

                try {
                    // Extracts and cleans each field before creating an Employee object.
                    int employeeNumber = Integer.parseInt(rowData[0].trim()); // Converts Employee Number to an integer.
                    String lastName = rowData[1].trim().replaceAll("\"", "").replaceAll(",", "").trim(); // Removes quotes & commas.
                    String firstName = rowData[2].trim().replaceAll("\"", "").replaceAll(",", "").trim(); // Cleans First Name.
                    String phoneNumber = rowData[3].trim(); // Extracts Phone Number.
                    String status = rowData[4].trim(); // Extracts Employment Status.
                    String position = rowData[5].trim(); // Extracts Position title.
                    String supervisor = rowData[6].trim().replaceAll("\"", "").replaceAll(",", " ").trim(); // Cleans Supervisor field.

                    // Creates an Employee object and adds it to the list.
                    employees.add(new Employee(employeeNumber, lastName, firstName, phoneNumber, status, position, supervisor));

                } catch (NumberFormatException e) { // Handles cases where Employee Number is invalid.
                    System.err.println("Skipping invalid employee entry: " + Arrays.toString(rowData)); 
                }
            }
        } catch (IOException e) { // Handles file errors.
            JOptionPane.showMessageDialog(null, "Error: Unable to load employee data!", "File Read Error", JOptionPane.ERROR_MESSAGE);
        }

        return employees; // Returns the list of loaded employees.
    }

    /**
     * Saves a new employee record to the CSV file.
     * Appends the data to avoid overwriting existing records.
     * 
     * @param employee - The employee object to save.
     */
    public static void saveEmployee(Employee employee) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) { // Opens the file in append mode.
            writer.write(formatEmployeeData(employee) + "\n"); // Writes the formatted employee data to the file.
        } catch (IOException e) {
            e.printStackTrace(); // Prints error details if saving fails.
        }
    }

    /**
     * Deletes an employee record based on the given employee number.
     * Removes the employee from the list and rewrites the file.
     * 
     * @param empNum - The Employee Number to delete.
     */
    public static void deleteEmployee(int empNum) {
        List<Employee> employees = loadEmployees(); // Loads the current list of employees.
        employees.removeIf(emp -> emp.getEmployeeNumber() == empNum); // Finds and removes the matching employee.
        writeEmployeeListToFile(employees); // Saves the updated list back to the file.
    }

    /**
     * Updates an existing employee's information.
     * Only updates fields that were explicitly modified in the GUI.
     * 
     * @param updatedEmployee - The employee object containing updated fields.
     */
    public static void updateEmployee(Employee updatedEmployee) {
        List<Employee> employees = loadEmployees(); // Loads the current employee list from the file.

        for (Employee emp : employees) {
            if (emp.getEmployeeNumber() == updatedEmployee.getEmployeeNumber()) { // Finds the matching employee.

                // Ensures Last Name updates only if explicitly modified.
                emp.setLastName(!updatedEmployee.getLastName().isEmpty() 
                    && !updatedEmployee.getLastName().equals(emp.getLastName())  
                    && !updatedEmployee.getLastName().contains(",")  
                    ? updatedEmployee.getLastName().trim()  
                    : emp.getLastName());

                // Ensures First Name updates only if explicitly modified.
                emp.setFirstName(!updatedEmployee.getFirstName().isEmpty() 
                    && !updatedEmployee.getFirstName().equals(emp.getFirstName())  
                    ? updatedEmployee.getFirstName().trim()  
                    : emp.getFirstName());

                // Ensures Phone Number updates only if explicitly modified.
                emp.setPhoneNumber(!updatedEmployee.getPhoneNumber().isEmpty() 
                    && !updatedEmployee.getPhoneNumber().equals(emp.getPhoneNumber())  
                    ? updatedEmployee.getPhoneNumber()  
                    : emp.getPhoneNumber());

                // Ensures Status updates only if explicitly modified.
                emp.setStatus(!updatedEmployee.getStatus().isEmpty() 
                    && !updatedEmployee.getStatus().equals(emp.getStatus())  
                    ? updatedEmployee.getStatus()  
                    : emp.getStatus());

                // Ensures Position updates only if explicitly modified.
                emp.setPosition(!updatedEmployee.getPosition().isEmpty() 
                    && !updatedEmployee.getPosition().equals(emp.getPosition())  
                    ? updatedEmployee.getPosition()  
                    : emp.getPosition());

                // Ensures Supervisor updates only if explicitly modified.
                emp.setSupervisor(!updatedEmployee.getSupervisor().isEmpty() 
                    && !updatedEmployee.getSupervisor().equals(emp.getSupervisor())  
                    ? updatedEmployee.getSupervisor().trim()  
                    : emp.getSupervisor());

                break; // Stops iteration once the matching employee is found and updated.
            }
        }

        writeEmployeeListToFile(employees); // Saves the updated employee list back to the CSV file.
    }

    /**
     * Writes the updated employee list back to the CSV file.
     * Restores proper formatting before saving.
     * 
     * @param employees - The list of employee records to save.
     */
    private static void writeEmployeeListToFile(List<Employee> employees) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) { // Opens file for overwriting.
            writer.write("EmpNum,LastName,FirstName,PhoneNumber,Status,Position,Supervisor\n"); // Restores header row.
            for (Employee emp : employees) writer.write(formatEmployeeData(emp) + "\n"); // Writes each employee record.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats an employee object into a properly structured CSV row.
     * Ensures data consistency and correct field separation.
     * 
     * @param employee - The employee record to format.
     * @return String - A correctly formatted CSV row.
     */
    private static String formatEmployeeData(Employee employee) {
        return String.join(",", 
            String.valueOf(employee.getEmployeeNumber()),  
            employee.getLastName().replaceAll("\"", "").trim(),  
            employee.getFirstName().replaceAll("\"", "").trim(),  
            employee.getPhoneNumber(), employee.getStatus(), employee.getPosition(),  
            employee.getSupervisor().replaceAll("\"", "").replaceAll(",", " ").trim());
    }
}
