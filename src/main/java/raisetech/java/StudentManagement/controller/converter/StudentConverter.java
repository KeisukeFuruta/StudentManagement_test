package raisetech.java.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.java.StudentManagement.data.Student;
import raisetech.java.StudentManagement.data.StudentCourses;
import raisetech.java.StudentManagement.domain.StudentDetail;

/**
 * 受講生詳細を受講生コース情報、もしくはその逆の変換を行うコンバーターです
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生のコース情報をマッピングする 受講生のコース情報は受講生に対して複数存在するので、ループを回して受講生情報を組み立てる。
   *
   * @param students        受講生一覧
   * @param studentsCourses 受講生のコース情報のリスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentCourses> studentsCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourses> convertStudentCourses = studentsCourses.stream()
          .filter(studentCourse -> student.getStudentId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());

      studentDetail.setStudentsCourses(convertStudentCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}
