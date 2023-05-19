package basic.external.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.jupiter.api.Test;

/**
 * PDFBox를 검토하기 위한 테스트케이스이다.
 * 
 * @seee <a href="http://pdfbox.apache.org/">PDFBox</a>
 */
public class PdfTest {

    /**
     * 빈 페이지 생성
     * 
     * @see <a href=
     *      "https://pdfbox.apache.org/2.0/cookbook/extractingtext.html">Extracting
     *      Text</a>
     */
    @Test
    public void tetCratePdf() throws Exception {
        // Create a new empty document
        PDDocument document = new PDDocument();
        // Create a new blank page and add it to the document
        PDPage blankPage = new PDPage();
        document.addPage(blankPage);
        // Save the newly created document
        document.save("d:\\BlankPage.pdf");
        // finally make sure that the document is properly
        // closed.
        document.close();
    }// :

    /** Hello World Using a PDF Base Font */
    @Test
    public void testHelloWorld() throws Exception {
        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Create a new font object selecting one of the PDF base fonts
        PDFont font = PDType1Font.HELVETICA_BOLD;

        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Define a text content stream using the selected font, moving the cursor and
        // drawing the text "Hello World"
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(100, 700);
        contentStream.drawString("Hello World");
        contentStream.endText();

        // Make sure that the content stream is closed:
        contentStream.close();

        // Save the results and ensure that the document is properly closed:
        document.save("d:\\HelloWorld.pdf");
        document.close();

    }// :


    
    /**
     * 이미지를 PDF에 삽입
     * @see <a href="https://www.tutorialspoint.com/pdfbox/pdfbox_inserting_image.htm">PDFBox - Inserting Image</a>
     * @throws Exception
     */
    @Test
    public void testImage() throws Exception  { 
          //Loading an existing document
      File file = new File("C:/PdfBox_Examples/sample.pdf");
      PDDocument doc = PDDocument.load(file);
        
      //Retrieving the page
      PDPage page = doc.getPage(0);
       
      //Creating PDImageXObject object
      PDImageXObject pdImage = PDImageXObject.createFromFile("C:/PdfBox_Examples/logo.png",doc);
       
      //creating the PDPageContentStream object
      PDPageContentStream contents = new PDPageContentStream(doc, page);

      //Drawing the image in the PDF document
      contents.drawImage(pdImage, 70, 250);

      System.out.println("Image inserted");
      
      //Closing the PDPageContentStream object
      contents.close();		
		
      //Saving the document
      doc.save("C:/PdfBox_Examples/sample.pdf");
            
      //Closing the document
      doc.close();
    }


    /**
     * HTML을 PDF로 변환
     * @see <a href="https://stackoverflow.com/questions/19713479/how-to-create-a-pdf-file-from-html-using-pdfbox>How to create a PDF file from HTML using PDFBox</a>
     */
    @Test 
    public void testHtmlToPdf() {

        // try (OutputStream os = new FileOutputStream("/Users/me/output.pdf")) {
        //     PdfRendererBuilder builder = new PdfRendererBuilder();
        //     builder.withUri("file:////Users/me/input.html");
        //     builder.toStream(os);
        //     builder.run();
        // }

    }//:




}/// ~
