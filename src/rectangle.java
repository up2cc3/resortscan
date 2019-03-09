import org.apache.pdfbox.pdmodel.PDDocument;

import java.awt.*;
import java.io.File;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;


class rectangle {
    private BufferedImage imgbuff;
    private File imageFile;

    public rectangle(){


    }



    public BufferedImage  createrectangle(File file) throws Exception {

        //Loading an existing PDF document
        //File file = new File("C:\\Users\\carlos\\Documents\\Tesseract\\Unsorted\\star.pdf");
        PDDocument document = PDDocument.load(file);


        //Instantiating the PDFRenderer class
        PDFRenderer renderer = new PDFRenderer(document);

        //Rendering an image from the PDF document
        BufferedImage image = renderer.renderImageWithDPI(0, 300);

        image = image.getSubimage( 2100, 30, 380, 200);

        //Writing the image to a file
        //ImageIO.write(image, "JPEG", new File("C:\\Users\\carlos\\Documents\\Tesseract\\Unsorted\\myimage.jpg"));

        //Closing the document
        document.close();
        return image;
    }
    public BufferedImage  star(File file) throws Exception {

        //Loading an existing PDF document
        //File file = new File("C:\\Users\\carlos\\Documents\\Tesseract\\Unsorted\\star.pdf");
        PDDocument document = PDDocument.load(file);

        //Instantiating the PDFRenderer class
        PDFRenderer renderer = new PDFRenderer(document);

        //Rendering an image from the PDF document
        BufferedImage image = renderer.renderImageWithDPI(0, 300);
        //System.out.println(image.getHeight()+ " h "+ image.getWidth());
        image = image.getSubimage( 0, 30, 2000, 500);

        //Writing the image to a file
        //ImageIO.write(image, "JPEG", new File("C:\\Users\\carlos\\Documents\\Tesseract\\Unsorted\\myimage.jpg"));

        //Closing the document
        document.close();
        return image;
    }
    public BufferedImage  mainf(File file) throws Exception {

        //Loading an existing PDF document
        //File file = new File("C:\\Users\\carlos\\Documents\\Tesseract\\Unsorted\\mainf.pdf");
        PDDocument document = PDDocument.load(file);

        //Instantiating the PDFRenderer class
        PDFRenderer renderer = new PDFRenderer(document);

        //Rendering an image from the PDF document
        BufferedImage image = renderer.renderImageWithDPI(0, 300);
        //System.out.println(image.getHeight()+ " h "+ image.getWidth());
        image = image.getSubimage( 1000, 30, 600, 350);

        //Writing the image to a file
        //ImageIO.write(image, "JPEG", new File("C:\\Users\\carlos\\Documents\\Tesseract\\Unsorted\\myimage.jpg"));

        //Closing the document
        document.close();
        return image;
    }
    public BufferedImage  createtwistedrectangle(File file) throws Exception {

        //Loading an existing PDF document
        //File file = new File("C:\\Users\\carlos\\Documents\\Tesseract\\Unsorted\\picking.pdf");
        PDDocument document = PDDocument.load(file);

        //Instantiating the PDFRenderer class
        PDFRenderer renderer = new PDFRenderer(document);

        //Rendering an image from the PDF document
        BufferedImage image = renderer.renderImageWithDPI(0, 300);
        //System.out.println(image.getHeight()+ " h "+ image.getWidth());
        image = image.getSubimage( 30, 3100, 180, 380);
        image=otherrotate(image);
        //Writing the image to a file
        //ImageIO.write(image, "JPEG", new File("C:\\Users\\carlos\\Documents\\Tesseract\\Unsorted\\myimage.jpg"));

        //Closing the document
        document.close();
        return image;
    }
    public static BufferedImage rotate(BufferedImage bimg, double angle){
        int w = bimg.getWidth();
        int h = bimg.getHeight();
        Graphics2D graphic = bimg.createGraphics();
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        //BufferedImage rotated= new BufferedImage(h,w,300);
        Color c=graphic.getBackground();
        System.out.println(c);
        graphic.drawImage(bimg, null, 0, 0);
        graphic.dispose();
        System.out.println("rotating");
        return bimg;
    }
    public static BufferedImage otherrotate(BufferedImage image ) {
        double angle=Math.toRadians(90) ;
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        //Color c=g.getBackground();
        //System.out.println(c);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }

    private static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }
    public BufferedImage getmybuff(){return imgbuff;}
    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
    public void setImagebuff(BufferedImage imageFile) {
        this.imgbuff = imageFile;
    }
}