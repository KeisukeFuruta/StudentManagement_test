package raisetech.java.StudentManagement;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentCourses {

  private String courseId;
  private String studentId;
  private String courseName;
  private Timestamp startDate;
  private Timestamp expectedEndDate;

}
