package raisetech.java.StudentManagement;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  private Map<Integer, String> studentInfo;

  public StudentManagementApplication() {
    this.studentInfo = new HashMap<>();
    studentInfo.put(23, "Yui Yamazoe");
    studentInfo.put(29, "Keisuke Furuta");
    studentInfo.put(27, "Kouhei Matsunaga");
  }

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  @GetMapping("/studentInfo")
  public Map<Integer, String> getStudentInfo() {
    return studentInfo;
  }

  @PostMapping("/updateStudentInfo")
  public String updateStudentInfo(Integer age, String name) {
    if (studentInfo.containsKey(age)) {
      studentInfo.put(age, name);
      return "学生情報をアップデートしました[ " + age + " - " + name + " ]";
    } else {
      return "学生情報が見つかりません [" + age + " ]";
    }
  }
}



