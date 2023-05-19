package basic.java8.io;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class FileIoTest {
    
    public Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
          .filter(file -> !file.isDirectory())
          .map(File::getName)
          .collect(Collectors.toSet());
    }
    
    @Test
    public void testFileList() {
        String dirName = "D:\\dev\\src\\Groupware-api-spec\\api"; 
        Set<String> fileList = listFilesUsingJavaIO(dirName);
        fileList.forEach(action -> System.out.println(action));
        System.out.println(fileList.size());
    }

    
    @Test
    public void testListDir() {
        String dirName = "D:\\dev\\src\\Groupware-api-spec\\api"; 
        File dir = new File(dirName);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(file.getName());
            }
        }
    }
}
