import java.io.UnsupportedEncodingException;
import java.util.*;

public class Projet {

    public static byte xor(byte a,byte b){
         if(a==b){
            return 0;
        }
        else{
            return 1;
        }
    }

    public static byte[] xor(byte[] message,byte[] masque){

        String messageBinaire = afficheEnBinaire(message);
        int tailleM = messageBinaire.length();
        String masqueBinaire = afficheEnBinaire(masque);
        int tailleMasque = masqueBinaire.length();

        byte[] res = new byte[tailleM];

        for(int i=0;i<tailleM;i++){
            res[i]=xor(message[i],masque[i]);
        }

        return res;
    }

    public static String afficheEnBinaire(byte[] message){
        String byteMessage = "";

        for(byte c : message){
            byteMessage += Integer.toBinaryString(c);
        }

        return byteMessage;
    }

    public static String afficheEnString(byte[] message){
        String stringMessage = "";

        for(byte c : message){
            stringMessage += (char)c;
        }

        return stringMessage;
    }

    public static byte[] genererMasque(byte[] message){
        String messageBinaire = afficheEnBinaire(message);
        int tailleM = messageBinaire.length();

        byte[] masque = new byte[tailleM];

        //générer un masque aléatoire de longueur 16
        for(int i=0;i<16;i++){
            int b=(int)(Math.random()*2);  //nb aléatoire 0 ou 1
            masque[i]=(byte) b;
        }

        //on applique la formule du LFSR
        for(int y=16;y<tailleM;y++){
            masque[y] = xor(xor(xor(masque[y-5],masque[y-6]),masque[y-15]),masque[y-16]);
        }
        
        return masque;
    }


    
    public static void main (String[] args) throws UnsupportedEncodingException {
        //affichage binaire de chacun des caractères du message
        
        String m = "Je m'appelle Guillaume";
        byte[] infoBin = m.getBytes("UTF-8");

        for (byte b : infoBin) {
            System.out.println((char) b + "-> "
                    + Integer.toBinaryString(b));
        }

        //on converti en string puis en tableau de byte pour obtenir un message binaire
        String messageString = afficheEnBinaire(infoBin);
        byte[] message = new byte[messageString.length()];

        for(int y=0; y < messageString.length(); y++){
            message[y] =(byte) Integer.parseInt(String.valueOf(messageString.charAt(y)));
        }

        System.out.println("message : "+afficheEnBinaire(message));

        byte[] masque = null;
        masque = genererMasque(infoBin);

        System.out.println("message : "+afficheEnBinaire(message));
        System.out.println("masque  : "+afficheEnBinaire(masque));

        //message crypté en binaire
        byte[] messageCrypte = null;
        messageCrypte = xor(message,masque);
        System.out.println("crypte  : "+afficheEnBinaire(messageCrypte));

    }
}