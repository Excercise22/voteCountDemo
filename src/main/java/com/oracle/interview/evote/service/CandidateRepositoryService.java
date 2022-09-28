package com.oracle.interview.evote.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class CandidateRepositoryService {
    private static Map<Character, String> candidatesWithPrefix;
    public static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private CandidateRepositoryService() {

    }
    public static Map<Character, String> getCandidatesWithPrefix(File file) throws Exception {
        if(file == null) return candidatesWithPrefix;
        if(candidatesWithPrefix == null) {
            candidatesWithPrefix = new LinkedHashMap<>();
            BufferedReader br
                    = new BufferedReader(new FileReader(file));
            String st = br.readLine();
            for (int i = 0; st != null; i++) {
                candidatesWithPrefix.put( ALPHABET[i], st.trim());
                st = br.readLine();
            }
        }
        return candidatesWithPrefix;
    }
}
