package basic.external.markdown;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.junit.jupiter.api.Test;

import com.jirepo.core.util.IoUtils;

public class MarkdownConvertTest {

    @Test
    public void testConvert() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse("This is *Sparta*");
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String result = renderer.render(document); // "<p>This is <em>Sparta</em></p>\n"
        System.out.println(result);
    }

    // https://github.com/commonmark/commonmark-java
    @Test 
    public void testConvert2() throws Exception {
        String path = "G:\\test\\test.md";
        File file = new File(path);
        String markdown = IoUtils.readFileToString(file, StandardCharsets.UTF_8.toString());

        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String result = renderer.render(document); // table 처리가 안된다. 
        System.out.println(result);
    }


    
    // https://github.com/commonmark/commonmark-java
    @Test 
    public void testConvert3() throws Exception {
        String path = "G:\\test\\test.md";
        File file = new File(path);
        String markdown = IoUtils.readFileToString(file, StandardCharsets.UTF_8.toString());

        List<Extension> extensions = Arrays.asList(TablesExtension.create()); // 테이블 처리를 위한 extension 추가
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        String result = renderer.render(document); 
        System.out.println(result);
    }

    private String parse(File file) {
        List<Extension> extensions = Arrays.asList(TablesExtension.create()); // 테이블 처리를 위한 extension 추가
        Parser parser = Parser.builder().extensions(extensions).build();
        String markdown = IoUtils.readFileToString(file, StandardCharsets.UTF_8.toString());
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        return  renderer.render(document); 
    }

    @Test 
    public void testConvert4() throws Exception {
        
        String dirName = "D:\\dev\\src\\Groupware-api-spec\\api\\filebox"; 
        File dir = new File(dirName);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (!file.isDirectory()) {
                System.out.println(file.getName());
                System.out.println("Parse started.");
                System.out.println(parse(file));
            }
        }
    }

}///~
