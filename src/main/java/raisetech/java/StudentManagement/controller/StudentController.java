package raisetech.java.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.java.StudentManagement.controller.exception.MyExceptionHandler;
import raisetech.java.StudentManagement.domain.StudentDetail;
import raisetech.java.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;

  }

  /**
   * 受講生詳細の一覧検索 全件検索を行うので、条件指定は行いません
   *
   * @return 受講生詳細一覧(全件)
   */

  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  @GetMapping("/studentListException")
  public List<StudentDetail> getStudentListException() throws MyExceptionHandler {

    return service.searchStudentList();
  }

  /**
   * 受講生詳細の検索です IDに紐づく任意の受講生の情報を取得します
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  @Operation(summary = "受講生詳細の検索", description = " IDに紐づく任意の受講生の情報を取得します")
  @GetMapping("/student/{id}")
  // ここのidはこのまま。studentIdではない。{id} = student_idの値を取ってくる。
  public StudentDetail getStudent(
      @PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail 　受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します。", responses = {
      @ApiResponse(responseCode = "400", content = @Content())})
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    // Repositoryにデータを保存する
    StudentDetail responseStudentDetail1 = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail1);
  }


  /**
   * 受講生詳細の更新を行います。 キャンセルフラグの更新もここで行います。(論理削除)
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生更新", description = "受講生詳細の更新を行います。キャンセルフラグの更新もここで行います(論理削除)")
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    // Repositoryにデータを保存する
    service.updateStudent(studentDetail);
    // .ok()内にはなんでも入れれる。上記のダイヤモンド演算子で型を指定できる。
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}
