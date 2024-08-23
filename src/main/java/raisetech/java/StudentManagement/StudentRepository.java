package raisetech.java.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

  // students_courseの全件取得を行う。
  @Select("SELECT * FROM students_courses")
  List<StudentCourses> searchCourses();

}
