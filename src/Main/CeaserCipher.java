package Main;

//package line only needed by netbeans
//package CeasarCipher;

import java.util.*;

/*
 * @author edunush
 */

public class CeaserCipher {

    //main driver of the program
    public static void main(String[] args) {
        getInputAndRunCaesar();
    }

    //accept input from the user, encrypt and decrypt if acceptable, terminate if unacceptable
    public static void getInputAndRunCaesar(){
        int shift = 0;
        String message;
        String encMessage;
        String origMessage;
        Scanner strReader = new Scanner(System.in);
        Scanner intReader = new Scanner(System.in);

        //get message to encode from user
        System.out.println("Please enter the text you wish to encrypt. No numeric values are permitted!");
        System.out.println("You may use upper or lower case, and acceptable punctuation marks are '.' ',' '!' and '?'.");
        System.out.print("Your message: ");
        message = strReader.nextLine();

        //ensure that input message contains no numeric values and is not empty
        if(message.matches(".*\\d.*")){
            System.out.println("Your input does not meet the criteria stated above.");
            System.exit(0);
        }else if(message.equals("")){
            System.out.println("You didn't enter anything.");
            System.exit(0);
        }

        //get cipher shift value from user and terminate if input is unacceptable
        System.out.println("Please enter an integer for the cipher shift.");
        System.out.println("Positive integers alphabetically shift right and negative integers alphabetically shift left.");
        System.out.print("Cipher shift: ");
        try{
            shift = intReader.nextInt();
        }catch(InputMismatchException e){
            System.out.println();
            System.out.println("The shift value must be an integer.");
            System.exit(0);
        }

        //run encryption on a timer and compute milliseconds
        long startTime1 = System.nanoTime();
        encMessage = encrypt(message,shift);
        long endTime1 = System.nanoTime();
        long duration1 = endTime1 - startTime1;
        double elapsedTime1 = (double) duration1 / 1000000.0;
        System.out.println();
        System.out.println("Encrypted message: " + encMessage);
        System.out.println("Encryption finished in: " + elapsedTime1 + " milliseconds.");

        //run decryption on a timer and compute milliseconds
        long startTime2 = System.nanoTime();
        origMessage = decrypt(encMessage,shift);
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        double elapsedTime2 = (double) duration2 / 1000000.0;
        System.out.println();
        System.out.println("Original message: " + origMessage);
        System.out.println("Decryption finished in: " + elapsedTime2 + " milliseconds.");
    }

    //accepts unencrypted message and returns encrypted message
    public static String encrypt(String message, int shift){

        //need an alphabet to use for character substitution
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //convert message to upper case
        message = message.toUpperCase();

        StringBuilder encMessage = new StringBuilder();
        int charPos, keyVal;
        char encVal;

        /* iterate through message and either substitute in encoded characters
            or drop in spaces and punctuation, as needed */
        for(int i = 0; i < message.length(); i++){
            //switch handles spaces and punctuation
            //not a space or punctuation mark, substitute encoded character
            //find location of current character in alphabet
            //use shift to find location of encoded character
            /* if keyVal is negative, we need to go to the other end
                            of the alphabet */
            //found encoded character, insert into encoded message
            switch (message.charAt(i)) {
                case ' ' -> encMessage.append(' ');
                case '.' -> encMessage.append('.');
                case ',' -> encMessage.append(",");
                case '!' -> encMessage.append("!");
                case '?' -> encMessage.append("?");
                default -> {
                    charPos = alphabet.indexOf(message.charAt(i));
                    keyVal = (shift + charPos) % 26;
                    if (keyVal < 0) {
                        keyVal = alphabet.length() + keyVal;
                    }
                    encVal = alphabet.charAt(keyVal);
                    encMessage.append(encVal);
                }
            }
        }
        //return encoded message
        return encMessage.toString();
    }

    //accepts encrypted message and returns decrypted (original) message
    public static String decrypt(String encMessage, int shift){

        //need an alphabet to use for character substitution
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //convert encoded message to upper case
        encMessage = encMessage.toUpperCase();

        StringBuilder origMessage = new StringBuilder();
        int charPos, keyVal;
        char repVal;

        /* iterate through encrypted message and either substitute in un-encoded
            characters or drop in spaces and punctuation, as needed */
        for(int i = 0; i < encMessage.length(); i++){
            //switch handles spaces and punctuation
            //not a space or punctuation mark, substitute un-encoded character
            //find location of current character in alphabet
            //use shift to find location of un-encoded character
            /* if keyVal is negative, we need to go to the other end
                            of the alphabet */
            //found un-encoded character, insert into un-encoded message
            switch (encMessage.charAt(i)) {
                case ' ' -> origMessage.append(' ');
                case '.' -> origMessage.append('.');
                case ',' -> origMessage.append(",");
                case '!' -> origMessage.append("!");
                case '?' -> origMessage.append("?");
                default -> {
                    charPos = alphabet.indexOf(encMessage.charAt(i));
                    keyVal = (charPos - shift) % 26;
                    if (keyVal < 0) {
                        keyVal = alphabet.length() + keyVal;
                    }
                    repVal = alphabet.charAt(keyVal);
                    origMessage.append(repVal);
                }
            }
        }
        //return un-encoded (orginal) message
        return origMessage.toString();
    }

}

