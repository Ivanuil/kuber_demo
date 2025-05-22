package edu.bit.kuber_demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class LogService {

    @Value("${log.storage-path}")
    private String FILE_PATH;

    private final Counter totalLogRequests;
    private final Counter successLogRequests;
    private final Counter failedLogRequests;
    private final Timer logProcessingTimer;

    public LogService(
            @Qualifier("totalLogRequests") Counter totalLogRequests,
            @Qualifier("successLogRequests") Counter successLogRequests,
            @Qualifier("failedLogRequests") Counter failedLogRequests,
            @Qualifier("logProcessingTimer") Timer logProcessingTimer) {
        this.totalLogRequests = totalLogRequests;
        this.successLogRequests = successLogRequests;
        this.failedLogRequests = failedLogRequests;
        this.logProcessingTimer = logProcessingTimer;
    }

    @SneakyThrows
    public void writeLog(String line) {
        totalLogRequests.increment();
        var sample = Timer.start();

        createDirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(line);
            writer.newLine();
            successLogRequests.increment();
        } catch (IOException e) {
            failedLogRequests.increment();
            throw new RuntimeException(e);
        }
        sample.stop(logProcessingTimer);
    }

    private void createDirs() throws IOException {
        File dir = new File(FILE_PATH).getParentFile();
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new IOException("Could not create directory: " + dir.getAbsolutePath());
            }
        }
    }

    public String readLog() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
