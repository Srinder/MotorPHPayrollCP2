import javax.swing.JFrame;                  // Provides window-based application functionality
import javax.swing.JTable;                  // Enables displaying tabular employee data
import javax.swing.table.DefaultTableModel; // Manages the data structure of the table
import javax.swing.table.DefaultTableCellRenderer; // Controls the visual appearance of table cells
import java.io.BufferedReader;              // Reads data from the CSV file
import java.io.FileReader;                  // Reads file contents line by line
import java.util.Vector;                    // Supports dynamic data handling for tables
import java.util.Arrays;                     // Provides array manipulation utilities
import java.io.IOException;                 // Handles file-related errors (reading/writing issues)
import com.opencsv.CSVReader;                // Simplifies CSV file reading
import javax.swing.JOptionPane;              // Allows error handling with pop-up messages
import java.io.File;                         // Represents the employee CSV file
import java.io.BufferedWriter;               // Writes data to a file efficiently
import java.io.FileWriter;                   // Enables appending updated employee data to the CSV
import javax.swing.SwingUtilities;           // Provides smooth UI rendering

/**
 * `EmployeeTable` class displays and manages employee records in a table format.
 * Supports real-time updates and data retrieval from a CSV file.
 */
public class EmployeeTable extends javax.swing.JFrame {
    private String selectedEmpNum; // Stores the Employee Number selected in the table
    
    // Instances for employee-related forms
    AddEmployee addemp = new AddEmployee();
    ViewEmpInfo viewinfo = new ViewEmpInfo();
    
    // Tracks the active instance of the Employee Table
    public static EmployeeTable instance;

    /**
     * Creates and initializes the EmployeeTable form.
     * Loads employee data and configures table settings.
     */
    public EmployeeTable() {
        instance = this; // Stores the active instance of EmployeeTable
        initComponents(); // Initializes UI components

        // Configure table model with predefined columns
        DefaultTableModel model = new DefaultTableModel(
        new Vector<>(Arrays.asList(
            "Employee Number", "Last Name", "First Name", "Phone Number", "Status", "Position", "Immediate Supervisor"
              )),
              0 // This initializes the table with zero rows
        ) {
         @Override
          public boolean isCellEditable(int row, int column) {
           return false; // Prevents direct editing of table cells
    }
};

        jTableEmpTable.setModel(model); // Apply the model to the table
        loadEmployeeData(); // Load employee records from CSV
        adjustTableSettings(); // Set table formatting
    }

    /**
     * Provides access to the active instance of EmployeeTable.
     * Used to refresh table data dynamically.
     * @return The active EmployeeTable instance
     */
    public static EmployeeTable getInstance() {
        return instance;
    }

    /**
     * Loads employee data from the CSV file and populates the table.
     * Uses OpenCSV to read file contents efficiently.
     */
    private void loadEmployeeData() {
        DefaultTableModel model = (DefaultTableModel) jTableEmpTable.getModel();
        model.setRowCount(0); // Clears existing rows before loading new data

        try {
            CSVReader reader = new CSVReader(new FileReader("src/data/employee_info.csv")); // Reads CSV file
            String[] row;
            boolean skipHeader = true; // Skips the header row if present

            while ((row = reader.readNext()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue; // Skip header row
                }

                // Process and store Immediate Supervisor data
                String supervisorFullName = row[6].trim();
                model.addRow(row); // Adds employee details as a new row in the table
            }
            reader.close(); // Closes the file reader after processing

        } catch (Exception e) {
            e.printStackTrace(); // Prints error details for debugging
        }

        // Adjust table settings for proper alignment and formatting
        jTableEmpTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableEmpTable.getColumnModel().getColumn(6).setPreferredWidth(280);
        jTableEmpTable.getTableHeader().setResizingAllowed(true);

        // Align text properly within table cells
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
        jTableEmpTable.getColumnModel().getColumn(6).setCellRenderer(renderer);
    }

    /**
     * Refreshes the EmployeeTable to reflect the latest employee records.
     * Clears and reloads table data dynamically.
     */
    public void refreshEmployeeTable() {
        if (instance != null) { // Ensures active window exists before refreshing
            DefaultTableModel model = (DefaultTableModel) jTableEmpTable.getModel();
            model.setRowCount(0); // Clears current rows
            loadEmployeeData();   // Reloads fresh data from CSV
        }
    }

    /**
     * Adjusts table settings such as column width and text alignment.
     * Ensures proper readability of employee records.
     */
    private void adjustTableSettings() {
        jTableEmpTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableEmpTable.getColumnModel().getColumn(6).setPreferredWidth(300); // Adjust column width

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
        jTableEmpTable.getColumnModel().getColumn(6).setCellRenderer(renderer);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonAdd = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();
        jButtonView = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEmpTable = new javax.swing.JTable();
        jButtonUpdate = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jLabelEmpInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 255));

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 600));

        jButtonAdd.setText("Add New Employee");
        jButtonAdd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonExit.setText("Exit");
        jButtonExit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });

        jButtonView.setText("View");
        jButtonView.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(102, 102, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTableEmpTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee Number", "Last Name", "First Name", "Phone Number", "Status", "Position", "Immediate Supervisor"
            }
        ));
        jScrollPane1.setViewportView(jTableEmpTable);

        jButtonUpdate.setText("Update");
        jButtonUpdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonDelete.setText("Delete");
        jButtonDelete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jLabelEmpInfo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelEmpInfo.setForeground(new java.awt.Color(255, 255, 204));
        jLabelEmpInfo.setText("Employee Information");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelEmpInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButtonView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                                    .addComponent(jButtonDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(52, 52, 52))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(161, 161, 161))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jButtonView, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jLabelEmpInfo)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        // TODO add your handling code here:
        dispose ();
    }//GEN-LAST:event_jButtonExitActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        // TODO add your handling code here:
        addemp.show();
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewActionPerformed
    // Handles the action when the "View" button is clicked
    // Opens an employee's details in read-only mode

    int selectedRow = jTableEmpTable.getSelectedRow(); // Get the selected row index

    if (selectedRow != -1) { // Ensure a row is selected before proceeding
        String empNum = jTableEmpTable.getValueAt(selectedRow, 0).toString(); // Retrieve Employee Number from the selected row

        // Open the Edit Employee window in read-only mode
        EditEmpInfo editEmpInfoWindow = new EditEmpInfo(empNum, true); 
        editEmpInfoWindow.setVisible(true);
    } else {
        // Show an error message if no employee is selected
        JOptionPane.showMessageDialog(this, "Please select an employee to view!", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButtonViewActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
    // Handles the action when the "Update" button is clicked
    // Opens an employee’s details for editing

    int selectedRow = jTableEmpTable.getSelectedRow(); // Get the selected row index

    if (selectedRow == -1) { // Ensure a row is selected before proceeding
        JOptionPane.showMessageDialog(this, "Please select an employee to update!", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Stop execution if no row is selected
    }

    // Assign the Employee Number from the selected row
    this.selectedEmpNum = jTableEmpTable.getValueAt(selectedRow, 0).toString().trim();

    // Debugging output to verify selected employee number
    System.out.println("DEBUG: selectedEmpNum before passing -> " + this.selectedEmpNum);

    // Validate that `selectedEmpNum` is not null or empty
    if (this.selectedEmpNum == null || this.selectedEmpNum.isEmpty()) {
        System.err.println("ERROR: selectedEmpNum is NULL or EMPTY!");
        return; // Stop execution if an invalid Employee Number is detected
    }

    // Open the Edit Employee window for the selected employee
    // Uses `SwingUtilities.invokeLater` to ensure smooth UI rendering
    SwingUtilities.invokeLater(() -> {
        EditEmpInfo editWindow = new EditEmpInfo(this.selectedEmpNum, false); // Open in Editable Mode
        editWindow.setVisible(true);
    });


    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
    // Handles the action when the "Delete" button is clicked
    // Removes an employee from the JTable and updates the CSV file accordingly

    int selectedRow = jTableEmpTable.getSelectedRow(); // Get the selected row index

    if (selectedRow == -1) { // Ensure a row is selected before proceeding
        JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Stop execution if no row is selected
    }

    // Show a confirmation dialog before deleting the employee
    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) {
        return; // Cancel deletion if user chooses "No"
    }

    // Retrieve Employee Number of the selected row
    String empNumToDelete = jTableEmpTable.getValueAt(selectedRow, 0).toString();

    // Remove Employee from JTable visually
    DefaultTableModel model = (DefaultTableModel) jTableEmpTable.getModel();
    model.removeRow(selectedRow); // Delete row from table

    // Update the CSV file by removing the deleted employee entry
    try {
        File inputFile = new File("src/data/employee_info.csv"); // Locate employee data file
        File tempFile = new File("src/data/temp_employee_info.csv"); // Create temporary file for updates

        BufferedReader reader = new BufferedReader(new FileReader(inputFile)); // Read original CSV file
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));  // Write updates to a temporary file

        String line;
        boolean skipHeader = true; // Ensures header row remains intact

        while ((line = reader.readLine()) != null) {
            if (skipHeader) {
                writer.write(line + "\n"); // Keep header row in the new file
                skipHeader = false;
                continue;
            }

            // Write only non-deleted employee records to the new file
            if (!line.startsWith(empNumToDelete + ",")) {
                writer.write(line + "\n"); 
            }
        }

        // Close file streams after processing
        reader.close();
        writer.close();

        // Replace old CSV file with updated records
        if (inputFile.delete()) { 
            tempFile.renameTo(inputFile); // Rename temp file to original file name
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting employee.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (IOException e) {
        e.printStackTrace(); // Print error details for debugging
        JOptionPane.showMessageDialog(this, "Error deleting employee.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Show confirmation dialog after successful deletion
    JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
    }//GEN-LAST:event_jButtonDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(EmployeeTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeTable().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JButton jButtonView;
    private javax.swing.JLabel jLabelEmpInfo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEmpTable;
    // End of variables declaration//GEN-END:variables
}
