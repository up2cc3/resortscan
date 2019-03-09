import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import net.sourceforge.tess4j.util.PdfGsUtilities;
import org.apache.log4j.BasicConfigurator;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.ghost4j.document.Document;
import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.modifier.ModifierException;
import org.ghost4j.modifier.SafeAppenderModifier;

//import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class resort {



    public static void main(String[] args) {


        //System.setProperty("jna.library.path", "32".equals(System.getProperty("sun.arch.data.model")) ? "lib/win32-x86" : "lib/win32-x86-64");
        BasicConfigurator.configure();
        String carpetaFinalLocal = "C:\\Users\\Carlos\\Documents\\Tesseract\\Sorted\\";
        String carpetanulaLocal = "C:\\Users\\Carlos\\Documents\\Tesseract\\Unsorted\\null\\";
        String carpetaFinal = "S:\\Production\\Logistics\\DISPATCHES\\Dispatches - ";
        String carpetanula = "S:\\Production\\Logistics\\DISPATCHES\\unsorted\\notsorted\\";



        String result = null;
        String cutresult=null;
        File imageFile = null;
        PDFDocument pdf = null;
        BufferedImage buff=null;
        Ficheros ficheros = new Ficheros(result, imageFile, pdf,buff);
        vacio v = new vacio();
        Date today = Calendar.getInstance().getTime();
        String date = today.toString();
        String mes = date.substring(4, 7);
        String year = date.substring(25, 29);
        String dia = date.substring(8, 10);
        File dirlost = new File(carpetaFinal + year + "\\" + mes + " " + year + "\\");
        try {
            dirlost.mkdirs();

        } catch (Exception e) {
        }

        java.util.List<Ficheros> listaFicheros = ficheros.buscarFicheros();
        Tesseract instance = new Tesseract(); // JNA Interface Mapping
        // Tesseract1 instance = new Tesseract1(); // JNA Direct Mapping
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        //instance.setDatapath(tessDataFolder.getPath());
        instance.setDatapath(".");
//Set the tessdata path
        //instance.setDatapath(tessDataFolder.getAbsolutePath());
        for (Ficheros f : listaFicheros)

            try {
                System.out.println("empezamos " + v.getResultado2() + "-" + v.isVacio() + "-");

                String resultado1 = instance.doOCR(f.getBuf());

                String resultado = resultado1.replaceAll("\\s", "");
                System.out.println(resultado + "- resultado");

                if (jcnumber(resultado1)!=null  ) {
                    resultado=jcnumber(resultado1);

                            f.setResult(resultado);
                            v.setResultado2(resultado);
                            v.setVacio(true);

                } else if (f.getResult()==null){
                    System.out.println("twist");
                    f.settwist();
                    resultado1 = instance.doOCR(f.getBuf());
                    System.out.println(resultado1);
                    if (jcnumber(resultado1)!=null  ) {
                        resultado=jcnumber(resultado1);
                        System.out.println("si es twist");
                        f.setResult(resultado);
                        v.setResultado2(resultado);
                        v.setVacio(true);

                    }

                }  if (f.getResult()==null){
                    System.out.println("star");
                    f.setstar();
                    resultado1 = instance.doOCR(f.getBuf());

                    if (isStar(resultado1)  ) {
                        System.out.println("si es star");
                        f.setResult("star");

                    }
                }  if (f.getResult()==null){
                    System.out.println("main");
                    f.settmainf();
                    resultado1 = instance.doOCR(f.getBuf());

                    if (isMainf(resultado1)  ) {
                        System.out.println("si en mainf");
                        f.setResult("mainf");

                    }
                }
                System.out.println("estamos aqui");
                if (jcnumber(f.getResult()) != null) {
                        System.out.println("a juntar");
                        juntaPdf(f);
                }
                else if ((f.getResult()=="star" || f.getResult()=="mainf")&& v.isVacio()){
                    System.out.println("a juntar s o m");
                    f.setResult(v.getResultado2());
                    v.setVacio(false);
                    juntaPdf(f);
                }
                else{
                    System.out.println("no se que es");
                    File newfile = new File(dirlost + "\\" + f.toString() + ".pdf");
                    System.out.println(newfile);
                    f.getImageFile().renameTo(newfile);
                    v.setVacio(false);
                }

            } catch (TesseractException e) {
                System.err.println(e.getMessage());
            }  catch (Exception e) {
                e.printStackTrace();
            }

    }

    public static void juntaPdf(Ficheros f){


        File dir = new File(myFolder(jcnumber(f.getResult())));
        try {
            dir.mkdirs();

        } catch (Exception e) {
        }
        PDFDocument newpdf = new PDFDocument();
        File newfile=new File(dir + "\\" + f.getResult() + ".pdf");
        if (newfile.exists()) {
            String pdf1 = dir + "\\" + f.getResult() + ".pdf";
            String pdf2 = f.getImageFile().getAbsolutePath();
            myMerger2(pdf1,pdf2);
        } else{
            f.getImageFile().renameTo(newfile);
        }
    }
    public static String jcnumber(String s) {
        if (s==null){return null;}
        String resultado = s.replaceAll("\\s", "");
        if (resultado.length() == 5 && isNumeric(resultado)  ){
            return resultado;
        } else if (resultado.startsWith("C") && resultado.length() ==6 && isNumeric(resultado.substring(1, 6))) {
            return resultado.substring(1, 6);
        } else if (resultado.startsWith("P") && resultado.length() ==6 && isNumeric(resultado.substring(1, 6))) {
            return resultado.substring(1, 6);
        } else {
            return null;
        }

    }
    public static boolean isNumeric(String cadena){

        try {
            int c=Integer.parseInt(cadena);
            if(c>40000 && c<99999) {
                return true;
            } else {
                return false;
            }

        } catch (NumberFormatException nfe){
            return false;
        }
    }

public static boolean isStar(String s){
        String needle="NELSON: 03 548";
    String needle1="AUCKLAND: 09 634";
    String needle2="CHRISTCHURCH: 03 349";
    if (s.contains(needle) || s.contains(needle1) || s.contains(needle2)) {
        return true;
    } else {
        return false;
    }
}
    public static boolean isMainf(String s){
        String needle="Mainfreight";

        if (s.contains(needle) ) {
            return true;
        } else {
            return false;
        }
    }
    public static String myFolder(String s){

        String lnum = s.substring(0,2);
        String fol = lnum + "000 - " + lnum + "999\\" + s +"\\";

        String myfolder = "S:\\Production\\Project Management\\Sales Orders\\" + fol +"Scanned Jobcard\\";
        File f=new File(myfolder);
        try {
            f.mkdirs();

        } catch (Exception e) {
        }
        return myfolder;

    }

    public static void myMerger(String pdf1,String pdf2){
        MemoryUsageSetting u=null;
        File f2=new File(pdf2);
        try {

            PDFMergerUtility mymerger= new PDFMergerUtility();
            mymerger.addSource(pdf1);
            mymerger.addSource(pdf2);
            mymerger.setDestinationFileName(pdf1);
            //mymerger.appendDocument(p1,p2);
            mymerger.mergeDocuments(u);

            if ( f2.delete()){
                System.out.println("se borro");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void myMerger2(String pdf1,String pdf2){
        PDFDocument newpdf = new PDFDocument();
        PDFDocument myPdf = new PDFDocument();
        File fpdf1=new File(pdf1);
        File fpdf2=new File(pdf2);

        try {
            newpdf.load(fpdf1);
            myPdf.load(fpdf2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SafeAppenderModifier modifier = new SafeAppenderModifier();
        Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put(SafeAppenderModifier.PARAMETER_APPEND_DOCUMENT, myPdf);
        Document apennd = null;
        try {
            apennd = modifier.modify(newpdf, parameters);
        } catch (ModifierException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            apennd.write(new File(pdf1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        fpdf2.delete();
    }

}



