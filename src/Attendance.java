import javax.swing.JFrame;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime; // Ensured: Added missing import for LocalTime
import java.time.format.DateTimeFormatter;
import java.util.List;

// ransel 

public class Attendance extends JFrame {
    private String empNo;
    private String empName;
    private DefaultTableModel tableModel;

    // Original constructor (can still be used if both empNo and empName are known)
    public Attendance(String empNo, String empName) {
        this.empNo = empNo;
        this.empName = empName;
        initComponents(); // Auto-generated UI initialization
        initializeFormAndLoadAttendance(); // Consolidated initialization and initial data load
    }

    // New constructor to be used when only empNo is passed (from MainMenu)
    // Ensures employee name is looked up from the attendance CSV file
    public Attendance(String empNo) {
        this.empNo = empNo;
        // Ensured: Fetch employee name using the new static method in AttendanceFileHandler
        this.empName = AttendanceFileHandler.getEmployeeNameFromAttendanceCSV(empNo);
        if (this.empName == null) {
            this.empName = "N/A (Name Not Found)"; // Default if name not found
            JOptionPane.showMessageDialog(this, "Employee name not found for ID: " + empNo + ". Please ensure it exists in the attendance CSV.", "Data Error", JOptionPane.WARNING_MESSAGE);
        }
        initComponents(); // Auto-generated UI initialization
        initializeFormAndLoadAttendance(); // Consolidated initialization and initial data load
    }

    // Helper method to set up form components and load initial attendance data
    private void initializeFormAndLoadAttendance() {
        EmpID.setText("Emp ID: " + empNo);
        EmpName.setText("Name: " + empName);
        
        // Initialize table model
        tableModel = (DefaultTableModel) jTableAttendance.getModel();
        tableModel.setRowCount(0); // Clear existing data
        
        // Set default date range (current month)
        LocalDate now = LocalDate.now();
        startDateLabel.setDate(java.sql.Date.valueOf(now.withDayOfMonth(1)));
        endDateLabel.setDate(java.sql.Date.valueOf(now.withDayOfMonth(now.lengthOfMonth())));

        // Load attendance for the default date range immediately when the window opens
        loadAttendanceForSelectedRange();
    }

    // Method to load attendance based on the currently selected date range in the JDateChoosers
    // This is called both on initial load and when the "Check Attendance" button is clicked.
    private void loadAttendanceForSelectedRange() {
        java.util.Date startDate = startDateLabel.getDate();
        java.util.Date endDate = endDateLabel.getDate();
        
        if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Please select both start and end dates to view attendance.",
                "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LocalDate start = new java.sql.Date(startDate.getTime()).toLocalDate();
        LocalDate end = new java.sql.Date(endDate.getTime()).toLocalDate();
        
        // Ensured: Get attendance records using AttendanceFileHandler, which accurately parses Time In/Out
        List<AttendanceFileHandler.AttendanceRecord> records =
            AttendanceFileHandler.getAttendanceRecords(empNo, start, end);
        
        // Clear table before populating with new data
        tableModel.setRowCount(0);
        
        // Define formatters for displaying date and time in the table
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        
        if (records.isEmpty()) {
            // Display a message if no records are found for the given range/employee
            tableModel.addRow(new Object[]{"", "", "", "No records found for this period", ""});
        } else {
            for (AttendanceFileHandler.AttendanceRecord record : records) {
                String status = calculateStatus(record.timeIn, record.timeOut);
                tableModel.addRow(new Object[]{
                    record.date.format(dateFormat),
                    record.timeIn.format(timeFormat), // Ensured: Time In is displayed correctly
                    record.timeOut.format(timeFormat), // Ensured: Time Out is displayed correctly
                    status,
                    "" // Empty remarks for now, can be expanded later
                });
            }
        }
    }
    
    // This method is the action listener for the "Check Attendance" button.
    // It simply triggers the re-loading of attendance for the currently selected date range.
    private void checkAttendanceButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // Retrieve selected start and end dates from the date pickers
        java.util.Date startDate = startDateLabel.getDate();
        java.util.Date endDate = endDateLabel.getDate();

        if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Please select both start and end dates to view attendance.",
                    "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convert to LocalDate format
        LocalDate start = new java.sql.Date(startDate.getTime()).toLocalDate();
        LocalDate end = new java.sql.Date(endDate.getTime()).toLocalDate();

        // Fetch attendance records for the employee
        List<AttendanceFileHandler.AttendanceRecord> records = AttendanceFileHandler.getEmployeeAttendance(empNo);

        // Clear table before populating with new data
        tableModel.setRowCount(0);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");

        if (records.isEmpty()) {
            tableModel.addRow(new Object[]{"", "", "", "No records found for this period", ""});
        } else {
            for (AttendanceFileHandler.AttendanceRecord record : records) {
                // Filter records to match the selected date range
                if (!record.date.isBefore(start) && !record.date.isAfter(end)) {
                    tableModel.addRow(new Object[]{
                        record.date.format(dateFormat),
                        record.timeIn.format(timeFormat),
                        record.timeOut.format(timeFormat),
                        calculateStatus(record.timeIn, record.timeOut),
                        "" // Empty remarks for now, can be expanded later
                    });
                }
            }
        }
    }                                                 

    private String calculateStatus(LocalTime timeIn, LocalTime timeOut) {
        // Ensured: Accessing public static final fields from AttendanceFileHandler for consistency
        if (timeIn.isAfter(AttendanceFileHandler.GRACE_PERIOD_END)) {
            return "Late";
        } else if (timeOut.isBefore(AttendanceFileHandler.STANDARD_TIME_OUT)) {
            return "Early Out";
        } else {
            return "Present";
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAttendance = new javax.swing.JTable();
        jLblEmpAtt = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        EmpName = new javax.swing.JLabel();
        startDateLabel = new com.toedter.calendar.JDateChooser();
        endDateLabel = new com.toedter.calendar.JDateChooser();
        labelFrom = new javax.swing.JLabel();
        labelTo = new javax.swing.JLabel();
        checkAttendanceButton = new javax.swing.JButton();
        EmpID = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        // Ensured: Changed to DISPOSE_ON_CLOSE so only this window closes
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 

        jPanel1.setBackground(new java.awt.Color(14, 49, 113));

        jTableAttendance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Time IN", "Time OUT", "Status", "Remarks"
            }
        ) {
            // Ensured: Make table cells non-editable by default
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableAttendance);
        // Ensure columns are not manually resizable by user and are properly sized
        if (jTableAttendance.getColumnModel().getColumnCount() > 0) {
            jTableAttendance.getColumnModel().getColumn(0).setResizable(false);
            jTableAttendance.getColumnModel().getColumn(1).setResizable(false);
            jTableAttendance.getColumnModel().getColumn(2).setResizable(false);
            jTableAttendance.getColumnModel().getColumn(3).setResizable(false);
            jTableAttendance.getColumnModel().getColumn(4).setResizable(false);
        }


        jLblEmpAtt.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLblEmpAtt.setForeground(new java.awt.Color(255, 255, 255));
        jLblEmpAtt.setText("Employee Attendance");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Select Date Range:");

        jButton1.setBackground(new java.awt.Color(153, 0, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        EmpName.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        EmpName.setForeground(new java.awt.Color(255, 255, 255));
        EmpName.setText("Name : Loading..."); // Initial text

        labelFrom.setForeground(new java.awt.Color(255, 255, 255));
        labelFrom.setText("From");

        labelTo.setForeground(new java.awt.Color(255, 255, 255));
        labelTo.setText("To");

        checkAttendanceButton.setBackground(new java.awt.Color(0, 102, 102));
        checkAttendanceButton.setForeground(new java.awt.Color(255, 255, 255));
        checkAttendanceButton.setText("Check Attendance");
        checkAttendanceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAttendanceButtonActionPerformed(evt);
            }
        });

        EmpID.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        EmpID.setForeground(new java.awt.Color(255, 255, 255));
        EmpID.setText("Emp ID : Loading..."); // Initial text


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(EmpName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLblEmpAtt, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                            .addComponent(EmpID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(61, 61, 61)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelFrom)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(startDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelTo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(endDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(checkAttendanceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addComponent(jScrollPane1))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLblEmpAtt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkAttendanceButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(endDateLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labelFrom)
                                .addComponent(EmpName))
                            .addComponent(startDateLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EmpID)
                        .addGap(8, 8, 8)))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jButton1)
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        dispose ();
    }                                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Attendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Attendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Attendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Attendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            // IMPORTANT: Replace "10001" with an actual Employee ID that exists in your employee_attendance.csv for testing
            new Attendance("10001").setVisible(true); 
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel EmpID;
    private javax.swing.JLabel EmpName;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton checkAttendanceButton;
    private com.toedter.calendar.JDateChooser endDateLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLblEmpAtt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableAttendance;
    private javax.swing.JLabel labelFrom;
    private javax.swing.JLabel labelTo;
    private com.toedter.calendar.JDateChooser startDateLabel;
    // End of variables declaration                   

}

