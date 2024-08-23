package raisetech.java.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students WHERE name = #{name}")
  List<Student> search();

  // students_coureseの全件取得を行う。


}
