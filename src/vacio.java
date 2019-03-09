public class vacio {

    private  String resultado2=null;

    public String getResultado2() {
        return resultado2;
    }

    public void  setResultado2(String resultado2) {
        this.resultado2 = resultado2;
    }


    public boolean isVacio() {
        return vacio;
    }

    public void setVacio(boolean vacio) {
        this.vacio = vacio;
    }

    boolean vacio=false;

    public static boolean isNumeric(String cadena){

        try {
            int c=Integer.parseInt(cadena);
            if(c>0) {
                return true;
            } else {
                return false;
            }

        } catch (NumberFormatException nfe){
            return false;
        }
    }


}