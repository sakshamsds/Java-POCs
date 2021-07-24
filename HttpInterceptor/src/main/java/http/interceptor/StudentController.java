package http.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

	@PostMapping
	public ResponseEntity<String> saveStudentInformation(@RequestHeader("student-auth-key") String authorization,
			@RequestBody Student student) {

		if (StringUtils.isEmpty(student.getLastName())) {
			return new ResponseEntity<>("Last Name is a required field", HttpStatus.BAD_REQUEST);
		}
		System.out.println(student.toString());
		return new ResponseEntity<>(String.format("Authorization %s is valid, and Data is saved", authorization), HttpStatus.OK);
	}
}