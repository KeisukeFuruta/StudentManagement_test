package raisetech.java.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter

public class Student {

  @Pattern(regexp = "^\\d+$")
  private String studentId;

  @NotBlank
  private String name;

  @NotBlank
  private String furigana;

  @NotBlank
  private String nickname;

  @NotBlank
  @Email
  private String emailAddress;

  @NotBlank
  private String residentialArea;

  private int age;

  private String gender;
  private String remark;
  private boolean isDeleted;
}
