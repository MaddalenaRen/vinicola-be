package it.epicode.vinicola_be.enumeration;

import java.util.Arrays;

public enum TipoFase {
    VENDEMMIA("VEN", "VENDEMMIA"),
    FERMENTAZIONE("FER", "FERMENTAZIONE"),
    AFFINAMENTO("AFF", "AFFINAMENTO"),
    IMBOTTIGLIAMENTO("IMB", "IMBOTTIGLIAMENTO"),
    ETICHETTATURA("ETI", "ETICHETTATURA");

    public final String key;
    public final String descrizione;

    private TipoFase(String key, String desc){
        this.key = key;
        this.descrizione = desc;
    }
    public static String getKeyByDescrizione(String desc){
        return Arrays.stream(values()).filter(e -> e.descrizione.equalsIgnoreCase(desc)).findFirst().get().key;
    }

    public static String getDescrizioneByKey(String key){
        return Arrays.stream(values()).filter(e -> e.key.equalsIgnoreCase(key)).findFirst().get().descrizione;
    }
}
