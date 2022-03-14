import java.io.UnsupportedEncodingException;

public class Projet {


    public static byte xor(byte a , byte b){
        if(a==b){
            return 0;
        }
        else{
            return 1;
        }
    }
    
    public static void main (String[] args) throws UnsupportedEncodingException {

        String message = "Bonjour je m'appelle Guillaume et je suis un etudiant tres gentil";
        byte[] infoBin = null;
        infoBin = message.getBytes("UTF-8");
        //affichage binaire de chacun des caractères du message
        for (byte b : infoBin) {
            System.out.println((char) b + "-> "
                    + Integer.toBinaryString(b));
        }

        byte a = 1;
        byte b = 1;

        byte res = xor(a,b);
        byte res2 = xor(a,b);
        System.out.println(res+" "+res2);
    }
}