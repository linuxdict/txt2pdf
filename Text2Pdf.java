import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.TabSettings;
import java.io.*;

public class Text2Pdf {
 
    public static final String TEXT
        = "resources/sample.txt";
    public static final String TMP
    = "resources/TMP.txt";
    public static final String DEST
        = "results/text2pdf.pdf";
    // public static final String FONT = "resources/fonts/simhei.ttf";
    public static final String CHINESE = "\u5341\u950a\u57cb\u4f0f";

    public static void main(String[] args)
    	throws DocumentException, IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
    	new Text2Pdf().createPdf(DEST);
    }
 
    /**
     * @param dest
     * @throws DocumentException
     * @throws IOException
     */
    public void createPdf(String dest)
	throws DocumentException, IOException {
    	
    	/** workaround for gb2312 to UTF-8. doesn't work very well. 
        InputStream is = new FileInputStream(TEXT);
        OutputStream os = new FileOutputStream(TMP);

        Reader r = new InputStreamReader(is,"gb2312");
        BufferedReader br1 = new BufferedReader(r);
        Writer w = new OutputStreamWriter(os, "utf-8");
        BufferedWriter bw1 = new BufferedWriter(w);

        String s=null;
        Paragraph p1;
        while((s=br1.readLine())!=null) {
            p1 = new Paragraph(s);
            bw1.write(s);
        }
        br1.close();
        bw1.close();
        os.flush();
        
        */
    	
        Document document = new Document(PageSize.A4.rotate());
        BaseFont chinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);  
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        // Font font = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font chinesefont = new Font(chinese, 8, Font.NORMAL);
        BufferedReader br = new BufferedReader(new FileReader(TEXT));
        String line;
        Paragraph p;
        while ((line = br.readLine()) != null) {
        	//String nline = new String(line.getBytes("GBK"), "utf-8");
            p = new Paragraph(line, chinesefont);
            //p.setTabSettings(new TabSettings(560f));
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(p);
        }
        // following is a test for chinese character 
        // document.add(new Paragraph(CHINESE, font));
        document.close();
    }
}
