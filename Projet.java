import java.io.UnsupportedEncodingException;
import java.io.*;
import java.util.*;
import java.nio.file.*;


public class Projet {

    public static byte xor(byte a,byte b){
        
        if(a==b){
            return 0;
        }
        else{
            return 1;
        }
    }

   /* public static byte xorTest(byte a ,byte b){



        
    }*/


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

    //Génération du masque 1)
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

    //Génération du masque question 2)
    public static byte[]  genererMasqueV2(byte[] message){

        String messageBinaire = afficheEnBinaire(message);
        int tailleM = messageBinaire.length();
        byte[] masque = new byte[tailleM];
        byte[] vn = new byte[tailleM];
        byte[] xn = new byte[tailleM];
        byte[] yn = new byte[tailleM];
        byte[] zn = new byte[tailleM];

        //génération des 25 bits aléatoires de vn
        for(int i=0;i<25;i++){
            int b=(int)(Math.random()*2);  //nb aléatoire 0 ou 1
            vn[i]=(byte) b;
        }

        //génération des 31 bits aléatoires de xn
        for(int i=0;i<31;i++){
            int b=(int)(Math.random()*2);  //nb aléatoire 0 ou 1
            xn[i]=(byte) b;
        }

        //génération des 33 bits aléatoires de yn
        for(int i=0;i<33;i++){
            int b=(int)(Math.random()*2);  //nb aléatoire 0 ou 1
            yn[i]=(byte) b;
        }

        //génération des 39 bits aléatoires de zn
        for(int i=0;i<39;i++){
            int b=(int)(Math.random()*2);  //nb aléatoire 0 ou 1
            zn[i]=(byte) b;
        }

        //On applique la formule du LFSR de vn
        for(int y=25;y<tailleM;y++){
            vn[y] = xor(xor(xor(vn[y-5],vn[y-13]),vn[y-17]),vn[y-25]);
        }

        //On applique la formule du LFSR de xn
        for(int y=31;y<tailleM;y++){
            xn[y] = xor(xor(xor(xn[y-7],xn[y-15]),xn[y-19]),xn[y-31]);
        }

        //On applique la formule du LFSR de yn
        for(int y=33;y<tailleM;y++){
            yn[y] = xor(xor(xor(yn[y-5],yn[y-9]),yn[y-29]),yn[y-33]);
        }

        //On applique la formule du LFSR de zn
        for(int y=39;y<tailleM;y++){
            zn[y] = xor(xor(xor(zn[y-3],zn[y-11]),zn[y-35]),zn[y-39]);
        }

        //On applique un xor() entre vn,xn,yn,zn pour obtenir le masque final
        masque = xor(xor(xor(vn,xn),yn),zn);

        return masque;
    }

    public static byte[] getFileBytes(File file) throws IOException {

        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[10000000];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1)
                ous.write(buffer, 0, read);
        } finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
                // swallow, since not that important
            }
            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
                // swallow, since not that important
            }
        }
        return ous.toByteArray();
    }

    public static int binaryToInteger(String binary) {

        char[] numbers = binary.toCharArray();
        int result = 0;

        for(int i=numbers.length - 1; i>=0; i--)
            if(numbers[i]=='1')
                result += Math.pow(2, (numbers.length-i - 1));
        return result;
    }


    public static void CryptoString()throws UnsupportedEncodingException{

        String m = "Je m'appelle Guillaume";
        byte[] infoBin = m.getBytes("ISO_8859_1");
        int tailleTableauBits = infoBin.length;
        int[] tailleBits = new int[tailleTableauBits];

        // On récupère la taille de chaque bits en binaire pour pouvoir décomposer le message décrypté
        for(int i=0;i<tailleTableauBits;i++){
            tailleBits[i]=Integer.toBinaryString(infoBin[i]).length();
        }

        //on converti en string puis en tableau de byte pour obtenir un message binaire
        String messageString = afficheEnBinaire(infoBin);
        byte[] message = new byte[messageString.length()];

        for(int y=0; y < messageString.length(); y++){
            message[y] =(byte) Integer.parseInt(String.valueOf(messageString.charAt(y)));
        }

        byte[] masque = genererMasqueV2(infoBin);

        System.out.println("message : "+afficheEnBinaire(message));
        System.out.println("masque  : "+afficheEnBinaire(masque));

        //message crypté en binaire
        byte[] messageCrypte = xor(message,masque);

        //message décrypté
        byte[] messageDecrypte = xor(messageCrypte,masque);

        //On décompose le message décrypté en fonction de la taille sur laquelle est codée chacun des caractères du message
        String[] messageDecrypteDecompose = new String[tailleTableauBits];

        int x = 0;
        for(int i=0;i<tailleTableauBits;i++){
            messageDecrypteDecompose[i]="";
            for(int y=0;y<tailleBits[i];y++){
                messageDecrypteDecompose[i]+=messageDecrypte[x];
                x+=1;
            }
        }

        //On met dans un tableau de byte toutes les string binaire en byte
        byte[] messageFinal = new byte[tailleTableauBits];
        for(int i=0;i<tailleTableauBits;i++){
            //problème, marche pas avec les accents
            messageFinal[i]=Byte.parseByte(messageDecrypteDecompose[i], 2); //Ne marche pas pour les valeurs plus grande que 127 comme les accents à cause de la range du type byte
        }

        System.out.println("crypte  : "+afficheEnBinaire(messageCrypte));
        System.out.println("decrypte: "+afficheEnBinaire(messageDecrypte));
        System.out.println(new String(messageFinal, "ISO_8859_1"));
    }

    public static void CryptoFichier()throws UnsupportedEncodingException,IOException {

        

        File fichier = new File("fichier.txt");
        byte[] fichierBin = Files.readAllBytes(fichier.toPath());
        int tailleTableauBits = fichierBin.length;
        int[] tailleBits = new int[tailleTableauBits];

        
        // On récupère la taille de chaque bits en binaire pour pouvoir décomposer le message décrypté
        for(int i=0;i<tailleTableauBits;i++){
            tailleBits[i]=Integer.toBinaryString(fichierBin[i]).length();
        }

       

        //on converti en string puis en tableau de byte pour obtenir un message binaire
        String fichierBinString = afficheEnBinaire(fichierBin);

        

        byte[] message = new byte[fichierBinString.length()];

        for(int y=0; y < fichierBinString.length(); y++){
            message[y] =(byte) Integer.parseInt(String.valueOf(fichierBinString.charAt(y)));
        }

       

        byte[] masque = null;
        masque = genererMasqueV2(fichierBin);


        System.out.println("fichier : "+afficheEnBinaire(message));
        System.out.println("masque  : "+afficheEnBinaire(masque));

        //fichier crypté en binaire
        byte[] fichierCrypte = xor(message,masque);

       

        //Test pour générer un fichier crypté
        Path pathh = Paths.get("testCrypte.txt");
        Files.write(pathh, fichierCrypte);

        //fichier décrypté
        byte[] fichierDecrypte = xor(fichierCrypte,masque);


        //On décompose le fichier décrypté en fonction de la taille sur laquelle est codée chacune des données du fichier
        String[] fichierDecrypteDecompose = new String[tailleTableauBits];

        int x = 0;
        for(int i=0;i<tailleTableauBits;i++){
            fichierDecrypteDecompose[i]="";
            for(int y=0;y<tailleBits[i];y++){
                fichierDecrypteDecompose[i]+=fichierDecrypte[x];
                x+=1;
            }
        }



        //On converti les string en byte
        int[] fichierFinal = new int[tailleTableauBits];

        for(int i=0;i<tailleTableauBits;i++){
            fichierFinal[i]=binaryToInteger(fichierDecrypteDecompose[i]);
        }

        byte[] fichierFinalByte = new byte[tailleTableauBits];

        //On recaste en byte le tableau de int
        for(int i=0;i<tailleTableauBits;i++){
            fichierFinalByte[i]=(byte)fichierFinal[i];
        }

    

        System.out.println("crypte  : "+afficheEnBinaire(fichierCrypte));
        System.out.println("decrypte: "+afficheEnBinaire(fichierDecrypte));

        //On recréé le fichier à partir du tableau de byte
        Path path = Paths.get("testDecrypte.txt");
        Files.write(path, fichierFinalByte);

        
    }


    
    public static void main (String[] args) throws UnsupportedEncodingException,IOException{
        
        //CryptoString();
        CryptoFichier();   //marche pour fichier .txt et image en .bmp
        

        File file = new File("fichier.txt");
        byte[] fileContent = Files.readAllBytes(file.toPath());
            
        for (byte b : fileContent) {
            System.out.println(Integer.toBinaryString(b));
            System.out.println(Integer.toBinaryString(b).length());
        }

        

        
        //Path path = Paths.get("imageFinal.gif");
        //Files.write(path, fileTest);


    }
}