import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.*;
import java.util.*;

public class EmployeeFileHandler {
    private static final String FILE_PATH = "src/data/employee_info.csv"; // Path to the CSV file

    /**
     * Loads all employees from the CSV file into a List using OpenCSV.
     *
     * @return List<Employee> - A list of employees retrieved from the CSV file.
     */
    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(FILE_PATH))) {
            List<String[]> records = reader.readAll();
            boolean skipHeader = true;

            for (String[] rowData : records) {
                if (skipHeader) { skipHeader = false; continue; }

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
                    String address = rowData[7].trim();

                    String sssNumber = rowData[8].trim().isEmpty() ? "NA" : rowData[8].trim();
                    String philHealthNumber = rowData[9].trim().isEmpty() ? "NA" : rowData[9].trim();
                    String tinNumber = rowData[10].trim().isEmpty() ? "NA" : rowData[10].trim();
                    String pagIbigNumber = rowData[11].trim().isEmpty() ? "NA" : rowData[11].trim();
                    String birthday = rowData[19].trim().isEmpty() ? "NA" : rowData[19].trim();

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }

    /**
     * Saves a new employee record to the CSV file using OpenCSV.
     */
    public static void saveEmployee(Employee employee) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE_PATH, true))) {
            writer.writeNext(formatEmployeeData(employee));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an employee record based on the given employee number using OpenCSV.
     */
    public static void deleteEmployee(int empNum) {
        List<Employee> employees = loadEmployees();
        employees.removeIf(emp -> emp.getEmployeeNumber() == empNum);
        writeEmployeeListToFile(employees);
    }

    /**
     * Updates an existing employee's information using OpenCSV.
     */
    public static void updateEmployee(Employee updatedEmployee) {
        List<Employee> employees = loadEmployees();
        boolean employeeFound = false;

        for (Employee emp : employees) {
            if (emp.getEmployeeNumber() == updatedEmployee.getEmployeeNumber()) {
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
                employeeFound = true;
                break;
            }
        }

        if (!employeeFound) {
            System.err.println("Error: Employee record not found!");
            return;
        }

        writeEmployeeListToFile(employees);
    }

    /**
     * Writes the updated employee list back to the CSV file using OpenCSV.
     */
    private static void writeEmployeeListToFile(List<Employee> employees) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE_PATH))) {
            writer.writeNext(new String[]{"EmpNum", "LastName", "FirstName", "PhoneNumber", "Status", "Position", "Supervisor", "Address",
                "SSS", "PHILHEALTH", "TIN", "PAGIBIG", "Basic Salary", "Rice Subsidy", "Phone Allowance",
                "Clothing Allowance", "Gross Semi-monthly Rate", "Hourly Rate", "Withholding Tax", "Birthday"});

            for (Employee emp : employees) {
                writer.writeNext(formatEmployeeData(emp));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats an employee object into a properly structured CSV row for OpenCSV.
     */
    private static String[] formatEmployeeData(Employee employee) {
        return new String[]{String.valueOf(employee.getEmployeeNumber()), employee.getLastName(), employee.getFirstName(),
            employee.getPhoneNumber(), employee.getStatus(), employee.getPosition(), employee.getSupervisor(), employee.getAddress(),
            employee.getSssNumber(), employee.getPhilHealthNumber(), employee.getTinNumber(), employee.getPagIbigNumber(),
            String.valueOf(employee.getBasicSalary()), String.valueOf(employee.getRiceSubsidy()), String.valueOf(employee.getPhoneAllowance()),
            String.valueOf(employee.getClothingAllowance()), String.valueOf(employee.getGrossSemiMonthlyRate()),
            String.valueOf(employee.getHourlyRate()), String.valueOf(employee.getWithholdingTax()), employee.getBirthday()};
    }

    /**
     * Safely parses a double value, returning 0.0 if the input is invalid, empty, or "N/A".
     */
    private static double parseDouble(String value) {
        try {
            return (value.trim().equalsIgnoreCase("N/A") || value.trim().isEmpty()) ? 0.0 : Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Invalid number format detected: " + value);
            return 0.0;
        }
    }
}
