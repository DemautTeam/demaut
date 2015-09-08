package ch.vd.demaut.commons.utils;

public class FileMockHelper {

    static public byte[] buildContenuFichier(Integer tailleFichierEnMB) {
        Integer tailleEnOctets = tailleFichierEnMB * 1024 * 1024;
        byte[] contenuFichier = new byte[tailleEnOctets];
        return contenuFichier;
    }
}
