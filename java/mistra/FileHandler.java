package mistra;

/**
 *
 * @author LovART
 */
import javax.jnlp.FileOpenService;
import javax.jnlp.FileSaveService;
import javax.jnlp.FileContents;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import java.io.*;
public class FileHandler {
    static private FileOpenService fos = null;
    static public FileSaveService fss = null;
    static public FileContents fc = null;
    static private String[] cla ={"stra"};
    static public dragdrop dnd;
    // retrieves a reference to the JNLP services
    private static synchronized void initialize() {
        if (fss != null) {
            return;
        }
        try {
            fos = (FileOpenService) ServiceManager.lookup("javax.jnlp.FileOpenService");
            fss = (FileSaveService) ServiceManager.lookup("javax.jnlp.FileSaveService");
        } catch (UnavailableServiceException e) {
            fos = null;
            fss = null;
        }
    }
    // displays open file dialog and reads selected file using FileOpenService
    public static StringBuffer open() {
        initialize();
        try {
            fc = fos.openFileDialog(null, cla);
           return readData(fc);
        } catch (IOException ioe) {
            ioe.printStackTrace(System.out);
            return null;
        }
    }
    // displays saveFileDialog and saves file using FileSaveService
    public static void save() {
        initialize();
        try {
            // Show save dialog if no name is already given
            if (fc == null) {
                fc = fss.saveFileDialog(null,null,
                    new ByteArrayInputStream(dnd.game.str.getBytes()),null);
                return;
            }
            // use this only when filename is known
            if (fc != null) {
                writeData(fc);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace(System.out);
        }
    }
    // displays saveAsFileDialog and saves file using FileSaveService
    public static void saveAs() {
        initialize();
        try {
            if (fc == null) {
                save();
            } else {
                 fc = fss.saveAsFileDialog(null, null, fc);
                save();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace(System.out);
        }
    }

    private static void writeData(FileContents fc) throws IOException {
      int sizeNeeded = (dnd.game.str.toString().length() * 2);
        if (sizeNeeded > fc.getMaxLength()) {
            fc.setMaxLength(sizeNeeded);
        }
         try{      
         BufferedWriter os = new BufferedWriter(new OutputStreamWriter(fc.getOutputStream(true)));
	 os.write(dnd.game.str); 
        }
        catch(Exception e){
           e.printStackTrace(); 
        }
    }
    private static StringBuffer readData(FileContents fc) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fc.getInputStream()));
        StringBuffer sb = new StringBuffer((int) fc.getLength());
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        br.close();
        return sb;
    }

 static void filehandle(dragdrop dndListener) {
       FileHandler.dnd=dndListener;
    }   
}
