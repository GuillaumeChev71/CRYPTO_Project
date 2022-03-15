import java.io.UnsupportedEncodingException;

public class Projet {

    public static byte xor(byte a,byte b){
        if(a==b){
            return 0;
        }
        else{
            return 1;
        }
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

    public static byte[] genererMasque(){

        //générer un masque aléatoire de longueur 16
        byte[] masque = new byte[16];

        for(int i=0;i<16;i++){

            int b=(int)(Math.random()*2);  //nb aléatoire 0 ou 1

            switch(b){
                case 0 :{
                    masque[i]=0;
                    break;
                }
                case 1 :{
                    masque[i]=1;
                    break;
                }
            }
        }

        return masque;
    }


    
    public static void main (String[] args) throws UnsupportedEncodingException {

        String message = "Je m'appelle Guillaume";
        byte[] infoBin = message.getBytes("UTF-8");

        //affichage binaire de chacun des caractères du message
        /*for (byte b : infoBin) {
            System.out.println((char) b + "-> "
                    + Integer.toBinaryString(b));
        }*/

        byte a = 1;
        byte b = 1;

        String byteMessage = afficheEnBinaire(infoBin);
        String stringMessage = afficheEnString(infoBin);

        //System.out.println(byteMessage);
        //System.out.println(stringMessage);

        byte[] testMasque = null;
        testMasque = genererMasque();
        System.out.println(afficheEnBinaire(testMasque));



        //byte res = xor(a,b);
        //byte res2 = xor(a,b);
        //ystem.out.println(res+" "+res2);
    }
}