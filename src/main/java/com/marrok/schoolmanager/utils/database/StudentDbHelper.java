package com.marrok.schoolmanager.utils.database;

import com.marrok.schoolmanager.utils.DatabaseConnection;
import com.marrok.schoolmanager.model.Student;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDbHelper {
    private static final Logger logger = LogManager.getLogger(StudentDbHelper.class);
    private Connection cnn;

    // Constructor to initialize database connection
    public StudentDbHelper() throws SQLException {
        logger.info("StudentDbHelper started - getConnection instance");
        this.cnn = DatabaseConnection.getInstance().getConnection();
    }

    // Method to add a new student to the database
    public boolean addStudent(Student student) {
        String query = "INSERT INTO student (fname, lname, code, file_number, birth_date, parent_name, " +
                "address, phone, parent_phone, level, remark, student_school_name, remise, exemption, gender) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnn.prepareStatement(query)) {
            preparedStatement.setString(1, student.getFname());
            preparedStatement.setString(2, student.getLname());
            preparedStatement.setInt(3, student.getCode());
            preparedStatement.setLong(4, student.getFileNumber());
            preparedStatement.setDate(5, Date.valueOf(student.getBirthDate()));
            preparedStatement.setString(6, student.getParentName());
            preparedStatement.setString(7, student.getAddress());
            preparedStatement.setString(8, student.getPhone());
            preparedStatement.setString(9, student.getParentPhone());
            preparedStatement.setInt(10, student.getLevel());
            preparedStatement.setString(11, student.getRemark());
            preparedStatement.setString(12, student.getStudentSchoolName());
            preparedStatement.setInt(13, student.getRemise());
            preparedStatement.setBoolean(14, student.isExemption());
            preparedStatement.setBoolean(15, student.isGender());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;  // Return true if at least one row was inserted
        } catch (SQLException e) {
            logger.error("Error while adding student", e);
        }
        return false;  // Return false if something went wrong
    }

    // Method to get a student by ID
    public Student getStudentById(int studentId) {
        String query = "SELECT * FROM student WHERE id = ?";
        try (PreparedStatement preparedStatement = cnn.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToStudent(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error while fetching student by ID", e);
        }
        return null;  // Return null if no student is found
    }

    // Method to get a student by file number
    public Student getStudentByFileNumber(long fileNumber) {
        String query = "SELECT * FROM student WHERE file_number = ?";
        try (PreparedStatement preparedStatement = cnn.prepareStatement(query)) {
            preparedStatement.setLong(1, fileNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToStudent(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error while fetching student by file number", e);
        }
        return null;  // Return null if no student is found
    }

    // Method to update student details
    public boolean updateStudent(Student student) {
        String query = "UPDATE student SET fname = ?, lname = ?, code = ?, file_number = ?, birth_date = ?, " +
                "parent_name = ?, address = ?, phone = ?, parent_phone = ?, level = ?, remark = ?, " +
                "student_school_name = ?, remise = ?, exemption = ?, gender = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = cnn.prepareStatement(query)) {
            preparedStatement.setString(1, student.getFname());
            preparedStatement.setString(2, student.getLname());
            preparedStatement.setInt(3, student.getCode());
            preparedStatement.setLong(4, student.getFileNumber());
            preparedStatement.setDate(5, Date.valueOf(student.getBirthDate()));
            preparedStatement.setString(6, student.getParentName());
            preparedStatement.setString(7, student.getAddress());
            preparedStatement.setString(8, student.getPhone());
            preparedStatement.setString(9, student.getParentPhone());
            preparedStatement.setInt(10, student.getLevel());
            preparedStatement.setString(11, student.getRemark());
            preparedStatement.setString(12, student.getStudentSchoolName());
            preparedStatement.setInt(13, student.getRemise());
            preparedStatement.setBoolean(14, student.isExemption());
            preparedStatement.setBoolean(15, student.isGender());
            preparedStatement.setInt(16, student.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;  // Return true if at least one row was updated
        } catch (SQLException e) {
            logger.error("Error while updating student", e);
        }
        return false;  // Return false if something went wrong
    }

    // Method to delete a student by ID
    public boolean deleteStudent(int studentId) {
        String query = "DELETE FROM student WHERE id = ?";
        try (PreparedStatement preparedStatement = cnn.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;  // Return true if at least one row was deleted
        } catch (SQLException e) {
            logger.error("Error while deleting student", e);
        }
        return false;  // Return false if something went wrong
    }

    // Helper method to map ResultSet to Student object
    private Student mapResultSetToStudent(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String fname = resultSet.getString("fname");
        String lname = resultSet.getString("lname");
        int code = resultSet.getInt("code");
        long fileNumber = resultSet.getLong("file_number");
        LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
        String parentName = resultSet.getString("parent_name");
        String address = resultSet.getString("address");
        String phone = resultSet.getString("phone");
        String parentPhone = resultSet.getString("parent_phone");
        int level = resultSet.getInt("level");
        String remark = resultSet.getString("remark");
        String studentSchoolName = resultSet.getString("student_school_name");
        int remise = resultSet.getInt("remise");
        boolean exemption = resultSet.getBoolean("exemption");
        boolean gender = resultSet.getBoolean("gender");

        return new Student(id, fname, lname, code, fileNumber, birthDate, parentName, address, phone, parentPhone,
                level, remark, studentSchoolName, remise, exemption, gender);
    }



    public List<Student> getStudents() throws SQLException {
        List<Student> students = new ArrayList<>();

        // SQL query to fetch students from the database
        String sql = "SELECT * FROM student";

        try (PreparedStatement statement = cnn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            // Iterate over the result set and populate the list of students
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fname = resultSet.getString("fname");
                String lname = resultSet.getString("lname");
                int code = resultSet.getInt("code");
                long fileNumber = resultSet.getLong("file_number");
                Date birthDate = resultSet.getDate("birth_date");
                String parentName = resultSet.getString("parent_name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                String parentPhone = resultSet.getString("parent_phone");
                int level = resultSet.getInt("level");
                String remark = resultSet.getString("remark");
                String studentSchoolName = resultSet.getString("student_school_name");
                int remise = resultSet.getInt("remise");
                boolean exemption = resultSet.getBoolean("exemption");
                boolean gender = resultSet.getBoolean("gender");

                // Create a new Student object and add it to the list
                Student student = new Student(id, fname, lname, code, fileNumber, birthDate.toLocalDate(),
                        parentName, address, phone, parentPhone, level, remark,
                        studentSchoolName, remise, exemption, gender);
                students.add(student);
            }
        } catch (SQLException e) {
            // Log the exception or handle it
            throw new SQLException("Error retrieving students from the database", e);
        }

        return students;
    }
    // Closing the connection when done (optional)
    public void close() {
        try {
            if (cnn != null && !cnn.isClosed()) {
                cnn.close();
            }
        } catch (SQLException e) {
            logger.error("Error while closing connection", e);
        }
    }
}
