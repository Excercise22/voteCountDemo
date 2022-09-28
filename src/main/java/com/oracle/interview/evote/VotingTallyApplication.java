package com.oracle.interview.evote;

import com.oracle.interview.evote.service.CandidateRepositoryService;

import java.io.File;
import java.util.Map;


public class VotingTallyApplication {
    public static void main(String[] args) throws  Exception
    {
        if(args == null || args[0].isEmpty()) {
            System.out.println("Command line parameter is missing");
            System.exit(0);
        }
        // File is given at start up time
        File file = new File(args[0]);
        Map<Character, String> candidatesWithPrefix = CandidateRepositoryService.getCandidatesWithPrefix(file);
        for (Map.Entry<Character, String> stringCharacterEntry : candidatesWithPrefix.entrySet()) {
            System.out.println(stringCharacterEntry.getKey() +". "+ stringCharacterEntry.getValue());
        }
    }
}
