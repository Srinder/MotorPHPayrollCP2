/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 * `EditEmpInfo` class allows users to view and edit employee details.
 * Supports read-only mode when viewing employee information.
 */

import javax.swing.JFormattedTextField; // Provides formatted text field inputs
import javax.swing.JOptionPane;         // Displays error and success messages to the user
import java.io.File;                    // Represents the employee CSV file
import java.io.BufferedReader;          // Reads data from the CSV file
import java.io.FileReader;              // Reads file contents line by line
import java.io.BufferedWriter;          // Writes data to a file efficiently
import java.io.FileWriter;              // Enables appending updated employee data to the CSV
import java.io.IOException;             // Handles file-related errors (reading/writing issues)
import java.util.Arrays;                // Provides array manipulation utilities
import javax.swing.JOptionPane;         // Allows error handling with pop-up messages

public class EditEmpInfo extends javax.swing.JFrame {
    private String empNumToEdit;  // Stores the Employee Number to edit/view
    private String[] employeeData; // Holds employee details from the CSV file
    private boolean readOnly;  // Tracks whether the form is in read-only mode

    /**
     * Constructor - Initializes the Edit Employee form.
     * @param empNum The Employee Number to edit/view
     * @param readOnly If true, disables editing for view mode
     */
    public EditEmpInfo(String empNum, boolean readOnly) {
        initComponents(); // Initializes UI components
        this.empNumToEdit = empNum;
        this.readOnly = readOnly; // Store read-only mode state
        loadEmployeeData(empNumToEdit); // Load employee data into fields

        if (readOnly) {
            disableEditing(); // Disable fields for viewing mode
        }
    }

    /**
     * Reads employee details from the CSV file and populates the form fields.
     * @param empNum The Employee Number to load data for
     */
    private void loadEmployeeData(String empNum) {
        try {
            File inputFile = new File("src/data/employee_info.csv"); // Locate CSV file
            BufferedReader reader = new BufferedReader(new FileReader(inputFile)); // Read the file
            
            String line;
            boolean skipHeader = true; // Skip the first row if it contains headers

            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Correctly split CSV data

                if (data.length < 7) {
                    data = Arrays.copyOf(data, 7); // Ensure correct array length
                }

                if (data[0].trim().equals(empNum)) {
                    employeeData = data; // Store the selected employee's data
                    break;
                }
            }
            reader.close(); // Close file reader after reading

            if (employeeData != null) {
                // Populate fields while removing unwanted quotes from Immediate Supervisor
                Name.setText(employeeData[1].trim());
                Position.setText(employeeData[2].trim());
                PhoneNum.setText(employeeData[3].trim());
                Status.setText(employeeData[4].trim());
                Salary.setText(employeeData[5].trim());
                ImmSup.setText(employeeData[6].replaceAll("^\"|\"$", "").trim()); // Removes unwanted quotes
            } else {
                JOptionPane.showMessageDialog(this, "Error: Employee data not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace(); // Print error details for debugging
            JOptionPane.showMessageDialog(this, "Error loading employee data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Disables editing when in read-only mode.
     * Prevents modifications to employee details.
     */
    private void disableEditing() {
        Name.setEditable(false);
        Position.setEditable(false);
        PhoneNum.setEditable(false);
        Status.setEditable(false);
        Salary.setEditable(false);
        ImmSup.setEditable(false);

        Save.setEnabled(false);  // Disable Save button
        Edit.setEnabled(false);  // Disable Edit button
    }

    /**
     * Toggles field editability when switching between view and edit mode.
     * @param editable If true, allows editing of employee details
     */
    private void setFieldsEditable(boolean editable) {
        Name.setEditable(editable);
        Position.setEditable(editable);
        Status.setEditable(editable);
        PhoneNum.setEditable(editable);
        Address.setEditable(editable);
        Birthday.setEditable(editable);
        ImmSup.setEditable(editable);
        SSS.setEditable(editable);
        PAGIBIG.setEditable(editable);
        PHILHEALTH.setEditable(editable);
        TIN.setEditable(editable);
        Salary.setEditable(editable);
        Hourly.setEditable(editable);
        PhoneAll.setEditable(editable);
        ClothAll.setEditable(editable);
        Rice.setEditable(editable);
        
        Save.setEnabled(editable); // Enable Save only when editing
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
        jPanel2 = new javax.swing.JPanel();
        Name = new javax.swing.JTextField();
        Position = new javax.swing.JTextField();
        Status = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        SSSname = new javax.swing.JLabel();
        PAGIBIGname = new javax.swing.JLabel();
        PHILHEALTHname = new javax.swing.JLabel();
        TINname = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        Save = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Edit = new javax.swing.JButton();
        PAGIBIG = new javax.swing.JTextField();
        PHILHEALTH = new javax.swing.JTextField();
        TIN = new javax.swing.JTextField();
        SSS = new javax.swing.JFormattedTextField();
        Salary = new javax.swing.JFormattedTextField();
        Hourly = new javax.swing.JFormattedTextField();
        PhoneAll = new javax.swing.JFormattedTextField();
        ClothAll = new javax.swing.JFormattedTextField();
        Rice = new javax.swing.JFormattedTextField();
        ImmSup = new javax.swing.JTextField();
        PhoneNum = new javax.swing.JTextField();
        Birthday = new javax.swing.JTextField();
        Address = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 600));

        jPanel1.setBackground(new java.awt.Color(102, 153, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 176, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 162, Short.MAX_VALUE)
        );

        Name.setEditable(false);
        Name.setBackground(new java.awt.Color(204, 204, 204));
        Name.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Name.setForeground(new java.awt.Color(255, 255, 255));
        Name.setText("Garcia, Manuel III");
        Name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameActionPerformed(evt);
            }
        });

        Position.setEditable(false);
        Position.setBackground(new java.awt.Color(204, 204, 204));
        Position.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Position.setForeground(new java.awt.Color(255, 255, 255));
        Position.setText("Chief Excecutive Officer");
        Position.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PositionActionPerformed(evt);
            }
        });

        Status.setEditable(false);
        Status.setBackground(new java.awt.Color(204, 204, 204));
        Status.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Status.setForeground(new java.awt.Color(255, 255, 255));
        Status.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Status.setText("Regular");
        Status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Position, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(Status, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)))))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Position, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        SSSname.setText("SSS:");

        PAGIBIGname.setText("PAGIBIG:");

        PHILHEALTHname.setText("PHILHEALTH:");

        TINname.setText("TIN:");

        jLabel11.setText("Basic Salary:");

        jLabel14.setText("Phone Number:");

        jLabel15.setText("Birthday:");

        jLabel16.setText("Address:");

        jLabel17.setText("Immediate Supervisor:");

        jLabel18.setText("Rice Subsidy:");

        jLabel19.setText("Phone Allowance:");

        jLabel20.setText("Hourly Rate:");

        jLabel21.setText("Clothing Allowance:");

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        Edit.setText("Edit");
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });

        PAGIBIG.setEditable(false);
        PAGIBIG.setText("691295330870");
        PAGIBIG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PAGIBIGActionPerformed(evt);
            }
        });

        PHILHEALTH.setEditable(false);
        PHILHEALTH.setText("820126853951");
        PHILHEALTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PHILHEALTHActionPerformed(evt);
            }
        });

        TIN.setEditable(false);
        TIN.setText("442-605-657-000");
        TIN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TINActionPerformed(evt);
            }
        });

        SSS.setEditable(false);
        SSS.setText("44-4506057-3");

        Salary.setEditable(false);
        Salary.setText("₱90,000.00");
        Salary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalaryActionPerformed(evt);
            }
        });

        Hourly.setEditable(false);
        Hourly.setText("₱535.71");

        PhoneAll.setEditable(false);
        PhoneAll.setText("₱2,000.00");

        ClothAll.setEditable(false);
        ClothAll.setText("₱1,000.00");

        Rice.setEditable(false);
        Rice.setText("₱1,500.00");

        ImmSup.setEditable(false);
        ImmSup.setText("N/A");
        ImmSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImmSupActionPerformed(evt);
            }
        });

        PhoneNum.setEditable(false);
        PhoneNum.setText("966-860-270");
        PhoneNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PhoneNumActionPerformed(evt);
            }
        });

        Birthday.setEditable(false);
        Birthday.setText("10/11/1983");
        Birthday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BirthdayActionPerformed(evt);
            }
        });

        Address.setEditable(false);
        Address.setText("Valero Carpark Building Valero Street 1227, Makati City");
        Address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddressActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(Edit)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(Save))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(Address, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(104, 104, 104)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ImmSup, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Birthday, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PHILHEALTHname, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TINname, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PHILHEALTH, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(TIN, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(PAGIBIGname, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(PAGIBIG, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(SSSname, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(SSS)))
                                .addGap(37, 37, 37)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Hourly, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PhoneAll, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClothAll, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Rice, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Salary, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(470, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(SSSname)
                                .addComponent(Salary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SSS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PAGIBIGname)
                    .addComponent(jLabel20)
                    .addComponent(PAGIBIG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Hourly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PHILHEALTHname)
                    .addComponent(jLabel19)
                    .addComponent(PHILHEALTH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PhoneAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TINname)
                    .addComponent(jLabel10)
                    .addComponent(jLabel21)
                    .addComponent(TIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClothAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(Rice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(ImmSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(PhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(Birthday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(Address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(126, 126, 126)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Save)
                    .addComponent(jButton2)
                    .addComponent(Edit))
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(36, 36, 36))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TINActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TINActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TINActionPerformed

    private void PHILHEALTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PHILHEALTHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PHILHEALTHActionPerformed

    private void PAGIBIGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PAGIBIGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PAGIBIGActionPerformed

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        // TODO add your handling code here:
        setFieldsEditable(true); //Unlock fields for editinG
 
    }//GEN-LAST:event_EditActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
    // Handles saving updated employee details when the "Save" button is clicked

    try {
        // Locate the employee CSV file and create a temporary file for updates
        File inputFile = new File("src/data/employee_info.csv");
        File tempFile = new File("src/data/temp_employee_info.csv");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile)); // Reads the original file
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));  // Writes the updated file

        String line;
        boolean skipHeader = true; // Ensure header row is retained

        while ((line = reader.readLine()) != null) {
            if (skipHeader) {
                writer.write(line + "\n"); // Keep header row in the new file
                skipHeader = false;
                continue;
            }

            // Parse CSV correctly while handling quoted values
            String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            // Ensure correct array size in case of missing fields
            if (data.length < 7) {
                data = Arrays.copyOf(data, 7); 
            }

            // Prevent duplication of Immediate Supervisor by formatting the field correctly
            data[6] = "\"" + ImmSup.getText().trim() + "\""; 

            // If this row matches the employee being edited, update the data fields
            if (data[0].trim().equals(empNumToEdit)) {  
                data[1] = Name.getText().trim();
                data[2] = Position.getText().trim();
                data[3] = PhoneNum.getText().trim();
                data[4] = Status.getText().trim();
                data[5] = Salary.getText().trim();

                // Maintain proper CSV format with updated details
                line = String.join(",", data);  
            }

            writer.write(line + "\n"); // Write updated or unchanged line
        }

        // Close file streams after processing
        reader.close();
        writer.close();

        // Replace old CSV file with the updated employee records
        if (inputFile.delete()) {
            if (tempFile.renameTo(inputFile)) {
                JOptionPane.showMessageDialog(this, "Employee record updated successfully!");

                // Refresh EmployeeTable to instantly display the updated entry
                if (EmployeeTable.instance != null) {
                    EmployeeTable.instance.refreshEmployeeTable(); // Auto-refresh the table
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error renaming temp file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting old file.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Disable editing after saving and close the edit window
        setFieldsEditable(false); 
        dispose(); 

    } catch (IOException e) {
        e.printStackTrace(); // Print error details for debugging
        JOptionPane.showMessageDialog(this, "Error saving changes!", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_SaveActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        dispose ();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void SalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SalaryActionPerformed

    private void ImmSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImmSupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ImmSupActionPerformed

    private void PhoneNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PhoneNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PhoneNumActionPerformed

    private void BirthdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BirthdayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BirthdayActionPerformed

    private void AddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddressActionPerformed

    private void NameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NameActionPerformed

    private void PositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PositionActionPerformed

    private void StatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusActionPerformed

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
            java.util.logging.Logger.getLogger(EditEmpInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditEmpInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditEmpInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditEmpInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String selectedEmpNum = null;
       
                new EditEmpInfo(selectedEmpNum, true).setVisible(true);  // ✅ View mode (read-only)

    }
});
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Address;
    private javax.swing.JTextField Birthday;
    private javax.swing.JFormattedTextField ClothAll;
    private javax.swing.JButton Edit;
    private javax.swing.JFormattedTextField Hourly;
    private javax.swing.JTextField ImmSup;
    private javax.swing.JTextField Name;
    private javax.swing.JTextField PAGIBIG;
    private javax.swing.JLabel PAGIBIGname;
    private javax.swing.JTextField PHILHEALTH;
    private javax.swing.JLabel PHILHEALTHname;
    private javax.swing.JFormattedTextField PhoneAll;
    private javax.swing.JTextField PhoneNum;
    private javax.swing.JTextField Position;
    private javax.swing.JFormattedTextField Rice;
    private javax.swing.JFormattedTextField SSS;
    private javax.swing.JLabel SSSname;
    private javax.swing.JFormattedTextField Salary;
    private javax.swing.JButton Save;
    private javax.swing.JTextField Status;
    private javax.swing.JTextField TIN;
    private javax.swing.JLabel TINname;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
