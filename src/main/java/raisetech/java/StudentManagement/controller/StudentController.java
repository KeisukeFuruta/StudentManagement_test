package raisetech.java.StudentManagement.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.java.StudentManagement.data.Student;
import raisetech.java.StudentManagement.data.StudentsCourses;
import raisetech.java.StudentManagement.domain.StudentDetail;
import raisetech.java.StudentManagement.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();

    List<StudentDetail> studentDetails = new ArrayList<>();
    for (Student student : students) {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentsCourses> convertStudentCourses = new ArrayList<>();
      for (StudentsCourses studentCourse : studentsCourses) {
        if (student.getStudent_id().equals(studentCourse.getStudentId())) {
          convertStudentCourses.add(studentCourse);
        }
      }
      studentDetail.setStudentsCourses(convertStudentCourses);
      studentDetails.add(studentDetail);
    }
    return studentDetails;
  }

  @GetMapping("/studentCourseList")
  public List<StudentsCourses> getStudentsCourseList() {
    return service.searchStudentsCourseList();
  }

}
