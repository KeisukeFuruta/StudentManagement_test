package raisetech.java.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.java.StudentManagement.data.Student;
import raisetech.java.StudentManagement.data.StudentsCourses;
import raisetech.java.StudentManagement.domain.StudentDetail;
import raisetech.java.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCoursesList();
  }

  public StudentDetail searchStudent(String studentId) {
    Student student = repository.searchStudent(studentId);
    List<StudentsCourses> studentsCourses = repository.searchStudentsCourse(student.getStudentId());
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCourses);
    return studentDetail;
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(studentDetail.getStudent().getStudentId());
      studentsCourses.setStartDate(LocalDateTime.now());
      studentsCourses.setExpectedEndDate(LocalDateTime.now().plusYears(1));
      repository.registerStudentsCourses(studentsCourses);
    }
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      repository.updateStudentsCourses(studentsCourses);
    }
  }

}
