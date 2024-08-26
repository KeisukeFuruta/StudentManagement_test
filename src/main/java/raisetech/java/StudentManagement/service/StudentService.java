package raisetech.java.StudentManagement.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.java.StudentManagement.data.Student;
import raisetech.java.StudentManagement.data.StudentsCourses;
import raisetech.java.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    // 全ての学生情報を取得
    List<Student> students = repository.search();

    // streamを使って絞り込みをする。年齢が30代の人のみを抽出する。
    // returnで抽出したリストをコントローラーに返す。
    return students.stream()
        .filter(student -> student.getAge() >= 30)
        .collect(Collectors.toList());
  }

  public List<StudentsCourses> searchStudentsCourseList() {

    // 全てのコース情報を取得
    List<StudentsCourses> studentsCourses = repository.searchStudentsCourses();

    // 絞り込み検索で「Javaコース」のコース情報のみを抽出する。
    // 抽出したリストをコントローラーに返す。
    return studentsCourses.stream()
        .filter(courses -> "Java".equals(courses.getCourseName()))
        .collect(Collectors.toList());

  }

}
