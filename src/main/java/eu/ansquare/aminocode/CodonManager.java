package eu.ansquare.aminocode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CodonManager {
    private Map<Character, String[]> codons = new HashMap<>();
    private String spaceCodon;
    public static CodonManager getStandard(){
        CodonManager manager = new CodonManager();
        manager.addCodons('g', "GGG", "GGA", "GGC", "GGU")
                .addCodons('f', "UUU", "UUC")
                .addCodons('l', "UUA", "UUG", "CUU", "CUG", "CUA", "CUC")
                .addCodons('s', "UCC", "UCA", "UCG", "UCU", "AGC", "AGU")
                .addCodons('y', "UAU", "UAC")
                .addCodons('c', "UGU", "UGC")
                .addCodons('w', "UGG")
                .addCodons('p', "CCC", "CCA", "CCU", "CCG")
                .addCodons('h', "CAU", "CAC")
                .addCodons('q', "CAA", "CAG")
                .addCodons('r', "CGG", "CGA", "CGC", "CGU", "AGG", "AGA")
                .addCodons('i', "AUU", "AUA", "AUC")
                .addCodons('m', "AUG")
                .addCodons('t', "ACA", "ACC", "ACU", "ACG")
                .addCodons('n', "AAC", "AAU")
                .addCodons('k', "AAG", "AAA")
                .addCodons('v', "GUU", "GUG", "GUA", "GUC")
                .addCodons('a', "GCA", "GCC", "GCG", "GCU")
                .addCodons('d', "GAC", "GAU")
                .addCodons('e', "GAA", "GAG")
                .addCodons('o', "UAG")
                .addCodons('u', "UGA")
                .setSpaceCodon("UAA");
        return manager;
    }
    public CodonManager setSpaceCodon(String codon){
        spaceCodon = codon;
        return this;
    }
    public String getSpaceCodon(){
        return spaceCodon;
    }
    public CodonManager addCodons(char character, String... codons){
        this.codons.put(character, codons);
        return this;
    }
    public String getRandomCodon(char character){
        String[] codins = codons.get(character);
        if(codins == null) return "UAA";
        int i = new Random().nextInt(codins.length);
        return codins[i];
    }
    public char getChar(String codon){
        for (Character character:codons.keySet()) {
            String[] characterCodons = codons.get(character);
            if(Arrays.stream(characterCodons).anyMatch(s -> s.equalsIgnoreCase(codon))) return character;
        }
        return ' ';
    }
}
