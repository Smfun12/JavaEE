package kma.topic8.springrestsample;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kma.topic8.springrestsample.dto.LoginDto;
import kma.topic8.springrestsample.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MyRestController {

    private final ResponseGenerator responseGenerator;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginDto> login(
        @RequestBody final LoginDto loginDto,
        @RequestParam("requiredField") final String requiredField
    ) {
        System.out.println("Accept login request: " + loginDto);
        System.out.println("Required field: " + requiredField);

        return ResponseEntity
            .status(HttpStatus.OK)
            .header(HttpHeaders.AUTHORIZATION, "generated-jwt-token")
            .body(loginDto);
    }

}
