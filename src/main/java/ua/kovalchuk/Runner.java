package ua.kovalchuk;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.Loader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Runner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.warn("Run runner");

        String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
        String inputVideoPath = "/Users/arkovalchuk/projects/ffmpeg-video-to-gif/src/main/resources/input.mov";
        String outputVideoPath = "/Users/arkovalchuk/projects/ffmpeg-video-to-gif/src/main/resources/output.gif";
        ProcessBuilder pb = new ProcessBuilder(
            ffmpeg,
            "-ss", "00:00:00",
            "-r", "5",
            "-i", inputVideoPath,
            "-f", "gif", outputVideoPath
        );
        pb.inheritIO().start().waitFor();

        log.warn("Finish runner");
    }
}
