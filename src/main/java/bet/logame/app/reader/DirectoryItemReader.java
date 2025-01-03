package bet.logame.app.reader;

import org.springframework.batch.item.ItemReader;

import java.io.File;

public class DirectoryItemReader implements ItemReader<File> {
    private final File[] files;
    private int currentIndex = 0;

    public DirectoryItemReader(File directory) {
        this.files = directory.listFiles((dir, name) -> name.endsWith(".png"));
    }

    @Override
    public File read() {
        if (currentIndex < files.length) {
            return files[currentIndex++];
        } else {
            return null;
        }
    }
}
