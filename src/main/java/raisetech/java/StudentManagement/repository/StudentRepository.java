package raisetech.java.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.java.StudentManagement.data.Student;
import raisetech.java.StudentManagement.data.StudentsCourses;


/**
 * 受講生情報を扱うリポジトリ。
 * <p>
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 全件検索します。
   *
   * @return　全件検索した受講生情報の一覧
   */

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students WHERE student_id = #{studentId}")
  Student searchStudent(String studentId);

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCoursesList();

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> searchStudentsCourse(String studentId);

  // 新規受講生の追加メソッド
  @Insert(
      "INSERT INTO students(name, furigana, nickname, email_address, residential_area, age, gender, remark, is_deleted) "
          + "VALUES(#{name}, #{furigana}, #{nickname}, #{emailAddress}, #{residentialArea}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "studentId")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses(student_id, course_name, start_date, expected_end_date)"
      + "VALUES(#{studentId}, #{courseName}, #{startDate}, #{expectedEndDate})")
  @Options(useGeneratedKeys = true, keyProperty = "courseId")
  void registerStudentsCourses(StudentsCourses studentsCourses);

  //　受講生情報　更新メソッド
  @Update(
      "UPDATE students SET name=#{name},furigana=#{furigana},nickname=#{nickname},email_address=#{emailAddress},"
          + "residential_area=#{residentialArea},age=#{age},gender=#{gender},remark=#{remark},is_deleted=#{isDeleted} WHERE student_id=#{studentId}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses SET course_name = #{courseName} WHERE course_id = #{courseId}")
  void updateStudentsCourses(StudentsCourses studentsCourses);


}
