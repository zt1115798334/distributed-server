import com.example.distributedcommon.utils.FileUtils;
import com.example.distributedcommon.utils.HttpUtils;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/9/10 16:19
 * description:
 */
public class HttpUtilsTests {
    @Test
    public void getInstance() {
    }

    @Test
    public void doGet() {
    }

    @Test
    public void doPostForm() {
    }

    @Test
    public void doPostJSON() {
    }

    @Test
    public void doPostText() {
    }

    @Test
    public void doGetDown() {
    }

    @Test
    public void doGetDownPath() {
    }

    @Test
    public void testDoGet() {
    }

    @Test
    public void testDoPostForm() {
    }

    @Test
    public void testDoPostJSON() {
    }

    @Test
    public void testDoPostText() {
    }

    @Test
    public void testDoGetDown() {
    }

    @Test
    public void testDoGetDownPath() {
    }

    @Test
    public void doPostFile() {
        String uri = "http://localhost:9999/file/uploadFile";

        Map<String, String> headers = Maps.newHashMap();
        headers.put("token", "1115798334");
        headers.put("timestamp", "1115798334");

        File file1 = new File("E:\\image\\2.jpg");
        File file2 = new File("E:\\image\\6.jpg");
        File file3 = new File("E:\\image\\7.jpg");
        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        files.add(file3);

        Optional<String> s = HttpUtils.getInstance().doPostFile(uri, headers, files);
        s.ifPresent(System.out::println);
    }

    @Test
    public void doPostMultipartFile() {
        String uri = "http://localhost:9999/file/uploadFile";

        Map<String, String> headers = Maps.newHashMap();
        headers.put("token", "1115798334");
        headers.put("timestamp", "1115798334");

        File file1 = new File("E:\\image\\2.jpg");
        File file2 = new File("E:\\image\\6.jpg");
        File file3 = new File("E:\\image\\7.jpg");
        List<MultipartFile> multipartFiles = new ArrayList<>();
        try {
            multipartFiles.add(FileUtils.fileToMultipartFile(file1));
            multipartFiles.add(FileUtils.fileToMultipartFile(file2));
            multipartFiles.add(FileUtils.fileToMultipartFile(file3));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Optional<String> s = HttpUtils.getInstance().doPostMultipartFile(uri, headers, multipartFiles);
        s.ifPresent(System.out::println);

    }


}
