package raisetech.java.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.java.StudentManagement.data.Student;
import raisetech.java.StudentManagement.data.StudentCourses;


/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一覧(全件)
   */

  @Select("SELECT * FROM students")
  List<Student> search();

  /**
   * 受講生の検索を行います
   *
   * @param studentId 　受講生ID
   * @return 受講生
   */
  @Select("SELECT * FROM students WHERE student_id = #{studentId}")
  Student searchStudent(String studentId);

  /**
   * 受講生のコース情報の全件検索を行います
   *
   * @return 受講生のコース情報(全件)
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourses> searchStudentsCoursesList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 　受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentCourses> searchStudentsCourse(String studentId);

  /**
   * 受講生を新規登録します。 studentIdに関しては自動採番を行う
   *
   * @param student 受講生
   */
  @Insert(
      "INSERT INTO students(name, furigana, nickname, email_address, residential_area, age, gender, remark, is_deleted) "
          + "VALUES(#{name}, #{furigana}, #{nickname}, #{emailAddress}, #{residentialArea}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "studentId")
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。courseIdに関しては自動採番を行う。
   *
   * @param studentsCourses 受講生コース情報
   */
  @Insert("INSERT INTO students_courses(student_id, course_name, start_date, expected_end_date)"
      + "VALUES(#{studentId}, #{courseName}, #{startDate}, #{expectedEndDate})")
  @Options(useGeneratedKeys = true, keyProperty = "courseId")
  void registerStudentsCourses(StudentCourses studentsCourses);

  //　受講生情報　更新メソッド
  @Update(
      "UPDATE students SET name=#{name},furigana=#{furigana},nickname=#{nickname},email_address=#{emailAddress},"
          + "residential_area=#{residentialArea},age=#{age},gender=#{gender},remark=#{remark},is_deleted=#{isDeleted} WHERE student_id=#{studentId}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses SET course_name = #{courseName} WHERE course_id = #{courseId}")
  void updateStudentsCourses(StudentCourses studentsCourses);


}
