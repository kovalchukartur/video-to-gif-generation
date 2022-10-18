package ua.kovalchuk;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.Loader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Runner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.warn("Run runner");

        String fileName = "input.mov";

        ClassPathResource classPathResource = new ClassPathResource(fileName);
        Path file = Files.createTempFile("", fileName);
        Files.copy(classPathResource.getInputStream(), file, StandardCopyOption.REPLACE_EXISTING);
        Path gifImageFile = Files.createTempFile(fileName, ".gif");

        String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
        ProcessBuilder pb = new ProcessBuilder(
            ffmpeg,
            "-y", // Overwrite output files without asking. https://ffmpeg.org/ffmpeg.html#Main-options
            "-t", "5",
            "-filter_complex", "fps=5,scale=320:-1",
            "-i", file.toAbsolutePath().toString(),
            gifImageFile.toAbsolutePath().toString()
        );
        pb.inheritIO().start().waitFor();

        byte[] bytes = Files.newInputStream(gifImageFile).readAllBytes();
        String base64String = Base64.getEncoder().encodeToString(bytes);
        log.warn("{}", base64String);
        log.info("Finish runner");
    }
}
