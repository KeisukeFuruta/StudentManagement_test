package raisetech.java.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.java.StudentManagement.controller.converter.StudentConverter;
import raisetech.java.StudentManagement.data.Student;
import raisetech.java.StudentManagement.data.StudentCourses;
import raisetech.java.StudentManagement.domain.StudentDetail;
import raisetech.java.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。 受講生の検索や登録、更新作業を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細一覧検索です。 全件検索を行うので、条件指定は行いません
   *
   * @return 受講生詳細の一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourses> studentsCoursesList = repository.searchStudentsCoursesList();
    return converter.convertStudentDetails(studentList, studentsCoursesList);

  }

  /**
   * 受講生詳細の検索です。 studentIDに紐づく受講生情報を取得した後、 その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param studentId 受講生ID
   * @return 受講生詳細
   */
  public StudentDetail searchStudent(String studentId) {
    Student student = repository.searchStudent(studentId);
    List<StudentCourses> studentsCourses = repository.searchStudentsCourse(student.getStudentId());
    return new StudentDetail(student, studentsCourses);
  }

  /**
   * 受講生詳細の登録を行います。 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定します。
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    // 準備
    Student student = studentDetail.getStudent();
    // やりたいことをやる
    repository.registerStudent(student);
    studentDetail.getStudentsCourses().forEach(studentsCourses -> {
      initStudentsCourse(studentsCourses, student);
      repository.registerStudentsCourses(studentsCourses);
    });
    // 値を返す
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentsCourses 受講生コース情報
   * @param student         受講生
   */
  // ※メソッドの場所は現場によって違うかも！そのメソッドを使うすぐ下に置いたり、publicを上に、privateを下に持って行ったり。
  private void initStudentsCourse(StudentCourses studentsCourses, Student student) {
    LocalDateTime now = LocalDateTime.now();

    studentsCourses.setStudentId(student.getStudentId());
    studentsCourses.setStartDate(now);
    studentsCourses.setExpectedEndDate(now.plusYears(1));
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentCourses studentsCourses : studentDetail.getStudentsCourses()) {
      repository.updateStudentsCourses(studentsCourses);
    }
  }

}
