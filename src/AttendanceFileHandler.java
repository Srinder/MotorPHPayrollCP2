/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author ransel
 */

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AttendanceFileHandler {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    // FIXED: Corrected pattern to "MMMMӱ" for month and year, based on "January 2023" example.
    private static final DateTimeFormatter MONTH_YEAR_FORMAT = DateTimeFormatter.ofPattern("MMMMӱ", Locale.ENGLISH);
    // Changed access modifiers from private to public for external access
    public static final LocalTime STANDARD_TIME_IN = LocalTime.of(8, 0);
    public static final LocalTime GRACE_PERIOD_END = LocalTime.of(8, 10);
    public static final LocalTime STANDARD_TIME_OUT = LocalTime.of(17, 0);
    // FIXED: Corrected CSV_PATH assuming project root is MotorPHPayrollCP2
    private static final String CSV_PATH = "src/data/employee_attendance.csv";

    /**
     * Calculates total worked hours and overtime for a given employee and month.
     *
     * @param empNo The employee's ID.
     * @param targetMonthYear The target month and year (e.g., "January 2023").
     * @return A map containing "workedHours" and "overtimeHours".
     */
    public static MonthlyHours acomputeMonthlyHoursAndOT(int empNo, String targetMonthYear) {
        double totalMinutes = 0;
        double otMinutes = 0;

        try {
            YearMonth yearMonth = YearMonth.parse(targetMonthYear, MONTH_YEAR_FORMAT);
            List<AttendanceRecord> records = getAttendanceRecords(String.valueOf(empNo), yearMonth.atDay(1), yearMonth.atEndOfMonth());

            for (AttendanceRecord record : records) {
                // Sundays are automatically excluded
                if (record.date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    continue;
                }

                // Overtime counts when working before 8:00 AM (early)
                if (record.timeIn.isBefore(STANDARD_TIME_IN)) {
                    otMinutes += ChronoUnit.MINUTES.between(record.timeIn, STANDARD_TIME_IN);
                }
                // Overtime counts when working after 5:00 PM (late)
                if (record.timeOut.isAfter(STANDARD_TIME_OUT)) {
                    otMinutes += ChronoUnit.MINUTES.between(STANDARD_TIME_OUT, record.timeOut);
                }

                // Calculate total worked minutes
                // Ensure timeOut is after timeIn to avoid negative minutes
                if (record.timeOut.isAfter(record.timeIn)) {
                    totalMinutes += ChronoUnit.MINUTES.between(record.timeIn, record.timeOut);
                }
            }
        } catch (DateTimeParseException e) {
            // Updated error message to reflect the new expected format "MMMMӱ"
            System.err.println("Invalid month/year format. Please use 'MMMMӱ'. Example: 'January 2023'. Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error processing attendance records: " + e.getMessage());
        }

        return new MonthlyHours(totalMinutes / 60.0, otMinutes / 60.0);
    }

    /**
     * Computes how many full working days are missing from a 40-hour workweek.
     *
     * @param empNo The employee's ID.
     * @param targetMonthYear The target month and year.
     * @return The number of absent days, rounded up.
     */
    public static int computeAbsentDays(int empNo, String targetMonthYear) {
        MonthlyHours monthlyHours = acomputeMonthlyHoursAndOT(empNo, targetMonthYear);
        double workedHours = monthlyHours.getWorkedHours();

        YearMonth yearMonth = YearMonth.parse(targetMonthYear, MONTH_YEAR_FORMAT);
        int workdays = 0;
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            DayOfWeek dayOfWeek = yearMonth.atDay(day).getDayOfWeek();
            // Sundays are automatically excluded from workdays
            if (dayOfWeek != DayOfWeek.SUNDAY) {
                workdays++;
            }
        }

        double requiredHours = workdays * 8.0; // Compares actual hours vs. 40-hour workweek standard (8 hours/day)
        double missingHours = Math.max(0, requiredHours - workedHours);

        // Round up to the nearest whole day (Each missing 8-hour block = 1 absent day, partial days round up)
        return (int) Math.ceil(missingHours / 8.0);
    }

    /**
     * Calculates total late minutes if login is after 8:10 AM or logout is
     * before 5:00 PM.
     *
     * @param empNo The employee's ID.
     * @param targetMonthYear The target month and year.
     * @return The total number of late minutes.
     */
    public static int computeLateMinutes(int empNo, String targetMonthYear) {
        int lateMinutes = 0;

        try {
            YearMonth yearMonth = YearMonth.parse(targetMonthYear, MONTH_YEAR_FORMAT);
            List<AttendanceRecord> records = getAttendanceRecords(String.valueOf(empNo), yearMonth.atDay(1), yearMonth.atEndOfMonth());

            for (AttendanceRecord record : records) {
                // Sundays excluded from late counts
                if (record.date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    continue;
                }

                // Counts minutes when logging in after 8:10 AM
                if (record.timeIn.isAfter(GRACE_PERIOD_END)) {
                    lateMinutes += ChronoUnit.MINUTES.between(GRACE_PERIOD_END, record.timeIn);
                }

                // Counts minutes when logging out before 5:00 PM
                if (record.timeOut.isBefore(STANDARD_TIME_OUT)) {
                    lateMinutes += ChronoUnit.MINUTES.between(record.timeOut, STANDARD_TIME_OUT);
                }
            }
        } catch (Exception e) {
            System.err.println("Error calculating late minutes: " + e.getMessage());
        }

        return lateMinutes;
    }

    /**
     * Returns attendance logs for an employee within a specific date range.
     *
     * @param empNo The employee's ID.
     * @param startDate The start date of the filter.
     * @param endDate The end date of the filter.
     * @return A list of attendance records.
     */
    public static List<AttendanceRecord> getAttendanceRecords(String empNo, LocalDate startDate, LocalDate endDate) {
        List<AttendanceRecord> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(CSV_PATH))) {
            reader.skip(1); // Skip header row
            String[] line;
            while ((line = reader.readNext()) != null) {
                // Ensure enough columns and check empNo (EmpID: line[0], Date: line[3], TimeIn: line[4], TimeOut: line[5])
                if (line.length >= 6 && line[0].equals(empNo)) {
                    try {
                        LocalDate recordDate = LocalDate.parse(line[3], DATE_FORMAT);
                        // FIXED: Added .trim() to ensure no leading/trailing whitespace affects parsing
                        // This line directly reads the time from the CSV.
                        LocalTime timeIn = LocalTime.parse(line[4].trim());
                        LocalTime timeOut = LocalTime.parse(line[5].trim());

                        // --- START DIAGNOSTIC LOGGING ---
                        System.out.println("DEBUG: Employee " + empNo + " on " + line[3] +
                                           " - Raw Time In string: '" + line[4] +
                                           "', Parsed Time In: " + timeIn);
                        // --- END DIAGNOSTIC LOGGING ---

                        // Gets all attendance entries for specific employee ID and custom date range (start → end date)
                        if (!recordDate.isBefore(startDate) && !recordDate.isAfter(endDate)) {
                            records.add(new AttendanceRecord(
                                recordDate,
                                timeIn,
                                timeOut
                            ));
                        }
                    } catch (DateTimeParseException e) {
                        System.err.println("Skipping invalid date/time format in CSV for employee " + empNo + " on line: " + String.join(",", line) + ". Error: " + e.getMessage());
                    }
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Failed to read CSV file: " + e.getMessage());
        }
        return records; // Returns: Date + Clock-in/out times
    }

    /**
     * Retrieves the full name of an employee from the attendance CSV file given their employee number.
     * This method is added here to avoid creating a new file, as requested.
     *
     * @param empNo The employee's ID (String).
     * @return The employee's full name (First Name Last Name), or null if not found.
     */
    public static String getEmployeeNameFromAttendanceCSV(String empNo) {
        try (CSVReader reader = new CSVReader(new FileReader(CSV_PATH))) {
            reader.skip(1); // Skip header row
            String[] line;
            while ((line = reader.readNext()) != null) {
                // Assuming Employee ID is line[0], Last Name is line[1], First Name is line[2]
                if (line.length >= 3 && line[0].equals(empNo)) {
                    return line[2] + " " + line[1]; // Returns "First Name Last Name"
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error reading attendance CSV for employee name: " + e.getMessage());
        }
        return null; // Employee not found
    }

    // Inner classes for structured data
    public static class AttendanceRecord {

        public final LocalDate date;
        public final LocalTime timeIn;
        public final LocalTime timeOut;

        public AttendanceRecord(LocalDate date, LocalTime timeIn, LocalTime timeOut) {
            this.date = date;
            this.timeIn = timeIn;
            this.timeOut = timeOut;
        }
    }

    public static class MonthlyHours {

        private final double workedHours;
        private final double overtimeHours;

        public MonthlyHours(double workedHours, double overtimeHours) {
            this.workedHours = workedHours;
            this.overtimeHours = overtimeHours;
        }

        public double getWorkedHours() {
            return workedHours;
        }

        public double getOvertimeHours() {
            return overtimeHours;
        }
    }
    public static List<AttendanceFileHandler.AttendanceRecord> getEmployeeAttendance(String empNo) {
        List<AttendanceFileHandler.AttendanceRecord> attendanceRecords = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(CSV_PATH))) {
            reader.skip(1); // Skip header row
            String[] line;
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm"); // Ensures compatibility with 1-digit hours

            while ((line = reader.readNext()) != null) {
                if (line.length >= 6 && line[0].equals(empNo)) {
                    LocalDate recordDate = LocalDate.parse(line[3].trim(), dateFormat);
                    LocalTime timeIn = LocalTime.parse(line[4].trim(), timeFormat);
                    LocalTime timeOut = LocalTime.parse(line[5].trim(), timeFormat);

                    // Store validated records
                    attendanceRecords.add(new AttendanceFileHandler.AttendanceRecord(recordDate, timeIn, timeOut));
                }
            }
        } catch (Exception e) {
            System.err.println("Error retrieving attendance records: " + e.getMessage());
        }

        return attendanceRecords; // Returns structured attendance data
    }



}



