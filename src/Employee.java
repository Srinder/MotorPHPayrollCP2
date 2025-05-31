/**
 * Represents an Employee object with relevant attributes and methods.
 * Stores employee details such as employee number, name, phone number, status, position, and supervisor.
 */
public class Employee {

    // Private variables to store employee details (Encapsulation principle)
    private int employeeNumber;  // Unique identifier for each employee
    private String lastName;  // Last name of the employee
    private String firstName;  // First name of the employee
    private String phoneNumber;  // Contact phone number
    private String status;  // Employment status (e.g., Regular, Contractual)
    private String position;  // Job position/title
    private String supervisor;  // Name of the supervisor

    /**
     * Constructor initializes an Employee object when created.
     * 
     * @param employeeNumber - Unique Employee Number.
     * @param lastName - Employee's Last Name.
     * @param firstName - Employee's First Name.
     * @param phoneNumber - Employee's Contact Number.
     * @param status - Employment status.
     * @param position - Job position/title.
     * @param supervisor - Employee's direct supervisor.
     */
    public Employee(int employeeNumber, String lastName, String firstName, String phoneNumber, String status, String position, String supervisor) {
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.position = position;
        this.supervisor = supervisor;
    }

    // Getter methods allow access to employee details from other classes
    public int getEmployeeNumber() { return employeeNumber; }  // Retrieves Employee Number
    public String getLastName() { return lastName; }  // Retrieves Last Name
    public String getFirstName() { return firstName; }  // Retrieves First Name
    public String getPhoneNumber() { return phoneNumber; }  // Retrieves Phone Number
    public String getStatus() { return status; }  // Retrieves Employment Status
    public String getPosition() { return position; }  // Retrieves Job Position
    public String getSupervisor() { return supervisor; }  // Retrieves Supervisor Name

    // Setter methods allow modification of employee details after creation
    public void setEmployeeNumber(int employeeNumber) { this.employeeNumber = employeeNumber; }  // Updates Employee Number
    public void setLastName(String lastName) { this.lastName = lastName; }  // Updates Last Name
    public void setFirstName(String firstName) { this.firstName = firstName; }  // Updates First Name
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }  // Updates Phone Number
    public void setStatus(String status) { this.status = status; }  // Updates Employment Status
    public void setPosition(String position) { this.position = position; }  // Updates Job Position
    public void setSupervisor(String supervisor) { this.supervisor = supervisor; }  // Updates Supervisor Name

    /**
     * Overridden toString() method provides a readable representation of the employee object.
     * Useful for debugging and displaying employee details in a structured format.
     * 
     * @return Formatted string containing employee details.
     */
    @Override
    public String toString() {
        return "Employee{" +
               "EmpNum=" + employeeNumber +
               ", LastName='" + lastName + "'" +
               ", FirstName='" + firstName + "'" +
               ", PhoneNumber='" + phoneNumber + "'" +
               ", Status='" + status + "'" +
               ", Position='" + position + "'" +
               ", Supervisor='" + supervisor + "'" +
               "}";
    }
}
