package eu.ansquare.aminocode;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static CodonManager codonManager;

    public static void main(String[] args) throws IOException {
        System.out.println("Amino terminal version 0.1");
        System.out.println("Input \"ENCODE\" for encoding or \"DECODE\" for decoding");
        codonManager = CodonManager.getStandard();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String mode = reader.readLine();
        if (!mode.equalsIgnoreCase("encode") && !mode.equalsIgnoreCase("decode")) {
            System.out.println("Invalid instuction. Stopping");
            return;
        } else if (mode.equalsIgnoreCase("encode"))
            System.out.println("Entering ENCODE mode. Input phrase for encoding");
        else if (mode.equalsIgnoreCase("decode")) {
            System.out.println("Entering DECODE mode. Input phrase for decoding");
        }
        while (true) {
            String input = reader.readLine();
            if (input.equals("STOP")) {
                System.out.println("Stopping");
                break;
            } else if (input.equalsIgnoreCase("encode")) {
                System.out.println("Switching to ENCODE mode. Input phrase for encoding");
                mode = "encode";
            } else if (input.equalsIgnoreCase("decode")) {
                System.out.println("Switching to DECODE mode. Input phrase for decoding");
                mode = "decode";
            } else {
                System.out.println(mode.equalsIgnoreCase("encode") ? encode(input) : decode(input));
            }
            System.out.println("Input another phrase or input \"STOP\" to stop the process");
        }

    }

    public static String encode(String input) {
        char[] chars = input.toCharArray();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char chair = chars[i];
            if (chair == 'b' || chair == 'j' || chair == 'x' || chair == 'z') {
                output.append(codonManager.getSpaceCodon() + codonManager.getSpaceCodon());
                switch (chair) {
                    case 'b': {
                        chair = 'p';
                        break;
                    }
                    case 'j': {
                        chair = 'g';
                        break;
                    }
                    case 'x': {
                        chair = 'k';
                        break;
                    }
                    case 'z': {
                        chair = 's';
                        break;
                    }
                }
            }
            String codon = codonManager.getRandomCodon(chair);
            output.append(codon);
        }
        return output.toString();
    }

    public static String decode(String input) {
        LinkedList<String> strings = new LinkedList<>(List.of(input.split("(?<=\\G.{3})")));

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            String string = strings.get(i);
            if (string.equalsIgnoreCase(codonManager.getSpaceCodon())) {
                if (i + 2 < strings.size() && strings.get(i + 1).equalsIgnoreCase(codonManager.getSpaceCodon())) {
                    String escapedCodon = strings.get(i + 2);
                    if (escapedCodon.equalsIgnoreCase(codonManager.getSpaceCodon())) {
                        output.append(" ");
                        String escapedCodon2 = strings.get(i + 3);
                        char escapedChar = codonManager.getChar(escapedCodon2);
                        switch (escapedChar) {
                            case 'p': {
                                output.append("b");
                                break;
                            }
                            case 'g': {
                                output.append("j");
                                break;
                            }
                            case 'k': {
                                output.append("x");
                                break;
                            }
                            case 's': {
                                output.append("z");
                                break;
                            }
                            default: {
                                output.append("_");
                            }
                        }
                        strings.remove(i + 1);
                        strings.remove(i + 1);
                        strings.remove(i + 1);
                    } else {
                        char escapedChar = codonManager.getChar(escapedCodon);
                        switch (escapedChar) {
                            case 'p': {
                                output.append("b");
                                break;
                            }
                            case 'g': {
                                output.append("j");
                                break;
                            }
                            case 'k': {
                                output.append("x");
                                break;
                            }
                            case 's': {
                                output.append("z");
                                break;
                            }
                            default: {
                                output.append("_");
                            }
                        }
                        strings.remove(i + 1);
                        strings.remove(i + 1);
                    }
                } else {
                    output.append(" ");
                }
            } else {
                output.append(codonManager.getChar(string));
            }
        }
        return output.toString();
    }
}