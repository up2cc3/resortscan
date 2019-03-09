import org.ghost4j.document.PDFDocument;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccc on 27/05/2017.
 */
public class Ficheros {

    private String result;
    private File imageFile;
    private PDFDocument pdf;
    private BufferedImage buf;
    public String carpetaInicialLocal = "C:\\Users\\carlos\\Documents\\Tesseract\\Unsorted\\";
    public String carpetaInicial = "S:\\Production\\Logistics\\DISPATCHES\\unsorted\\";
    public String tipo="nose";


    public Ficheros(String result, File imageFile, PDFDocument pdf,BufferedImage buf) {

        setResult(result);
        setImageFile(imageFile);
        setPdf(pdf);
        setBuff(buf);

    }

    public List<Ficheros>buscarFicheros() {
        List<Ficheros> ficheros = new ArrayList<Ficheros>();
        PDFDocument pdf=new PDFDocument();
        rectangle rec=new rectangle();
        File fDirectorio = new File(carpetaInicial);
        if (fDirectorio.isDirectory()) {
            File[] myFiles = fDirectorio.listFiles();
            int i=0;
            for (File myFile : myFiles) {
                i=i+1;
                if (i<20) {
                    if (myFile.getName().contains(".pdf")) {
                        try {
                            pdf.load(myFile);
                        } catch (Exception e) {
                            System.out.println("ERROR: " + e.getMessage());
                        }
                        try {
                            ficheros.add(new Ficheros(null, myFile, pdf, rec.createrectangle(myFile)));
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        return ficheros;
    }




    public void setResult(String result) {
        this.result = result;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public PDFDocument getPdf() {
        return pdf;
    }

    public void setPdf(PDFDocument pdf) {
        this.pdf = pdf;
    }

    public void setBuff(BufferedImage buf) {this.buf=buf;}

    public void settwist(){
        rectangle rec=new rectangle();
        try {
            this.buf=rec.createtwistedrectangle(this.imageFile);
        } catch (Exception e){}

    }
    public void settmainf(){
        rectangle rec=new rectangle();
        try {
            this.buf=rec.mainf(this.imageFile);
        } catch (Exception e){}

    }
    public void setstar(){
        rectangle rec=new rectangle();
        try {
            this.buf=rec.star(this.imageFile);
        } catch (Exception e){}

    }

    public BufferedImage getBuf() {
        return buf;
    }

    public String getResult() {
        return result;
    }

    public String getTipo(){return tipo;}

    public void setTipo(String s){this.tipo=s;}

}

