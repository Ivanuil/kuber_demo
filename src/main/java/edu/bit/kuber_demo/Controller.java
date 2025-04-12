package edu.bit.kuber_demo;

import edu.bit.kuber_demo.dto.LogRequestDto;
import edu.bit.kuber_demo.dto.StatusResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final LogService logService;

    @GetMapping("/")
    public String hello() {
        return "Welcome to the custom app";
    }

    @GetMapping("/status")
    public StatusResponseDto status() {
        return new StatusResponseDto();
    }

    @PostMapping("/log")
    public void writeLog(@RequestBody LogRequestDto logRequest) {
        logService.writeLog(logRequest.message);
    }

    @GetMapping("/logs")
    public String readLog() {
        return logService.readLog();
    }

}
