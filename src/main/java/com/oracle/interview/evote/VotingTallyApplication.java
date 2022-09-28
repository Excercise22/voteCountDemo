package com.oracle.interview.evote;

import com.oracle.interview.evote.domain.Voter;
import com.oracle.interview.evote.service.CandidateRepositoryService;
import com.oracle.interview.evote.service.TallyService;
import com.oracle.interview.evote.service.TallyServiceInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class VotingTallyApplication {
    public static void main(String[] args) throws  Exception
    {
        if(args == null || args[0].isEmpty()) {
            System.out.println("Command line parameter is missing");
            System.exit(0);
        }
        // File is given at start up time - candidates
        File file = new File(args[0]);
        Map<Character, String> candidatesWithPrefix = CandidateRepositoryService.getCandidatesWithPrefix(file);
        for (Map.Entry<Character, String> stringCharacterEntry : candidatesWithPrefix.entrySet()) {
            System.out.println(stringCharacterEntry.getKey() +". "+ stringCharacterEntry.getValue());
        }
        // construct voter base with choices
        Scanner scanner = new Scanner(System.in);
        int voterId = 0;
        String preferenceInput;
        Voter voter;
        List<Voter> voterList = new ArrayList<>();
        while(scanner.hasNextLine()) {
            preferenceInput = scanner.nextLine();
            if (!"tally".equalsIgnoreCase(preferenceInput.trim())) {
                voter = new Voter(voterId, preferenceInput.trim());
                voterList.add(voter);
                voterId++;
            } else {
                break;
            }
        }
        TallyServiceInterface  tallyServiceInterface = new TallyService();
        tallyServiceInterface.beginTally(voterList);
    }
}
