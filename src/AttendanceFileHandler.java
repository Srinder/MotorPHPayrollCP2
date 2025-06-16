/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ransel
 */
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class AttendanceFileHandler {
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("H:mm");
    private static final String CSV_PATH = "MotorPH/src/data/employee_attendance.csv";
    private String employeeId;

    public AttendanceFileHandler(String employeeId) {
        this.employeeId = employeeId;
    }

    public List<AttendanceRecord> getFilteredRecords(LocalDate startDate, LocalDate endDate) throws Exception {
        List<AttendanceRecord> filteredRecords = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(CSV_PATH))) {
            reader.skip(1); // Skip header
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line[0].equals(employeeId)) {
                    LocalDate recordDate = LocalDate.parse(line[3], DATE_FMT);
                    if (!recordDate.isBefore(startDate) && !recordDate.isAfter(endDate)) {
                        filteredRecords.add(new AttendanceRecord(
                            recordDate,
                            LocalTime.parse(line[4], TIME_FMT),
                            LocalTime.parse(line[5], TIME_FMT)
                        ));
                    }
                }
            }
        }
        return filteredRecords;
    }

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
}