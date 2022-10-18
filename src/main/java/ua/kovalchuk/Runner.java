package ua.kovalchuk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Runner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.warn("Run runner");
        log.warn("Finish runner");
    }
}
