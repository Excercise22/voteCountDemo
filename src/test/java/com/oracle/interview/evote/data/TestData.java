package com.oracle.interview.evote.data;

import com.oracle.interview.evote.domain.Voter;
import com.oracle.interview.evote.service.CandidateRepositoryService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestData {
    public static Map<Character, String> optionsMap;

    public static Map<Character, String> initializeOptions() throws Exception {
        optionsMap  = CandidateRepositoryService.getCandidatesWithPrefix(new File("src/test/java/com/oracle/interviw/evote/data/candidates.txt"));
        return optionsMap;
    }
    public static List<Voter> votersWithCompleteChoices() {
        List<Voter> votersWithCompleteChoices = new ArrayList<>();
        votersWithCompleteChoices.add(new Voter(1, "ABCD"));
        votersWithCompleteChoices.add(new Voter(1, "BCAD"));
        votersWithCompleteChoices.add(new Voter(1, "ACBD"));
        votersWithCompleteChoices.add(new Voter(1, "DBCA"));
        return votersWithCompleteChoices;
    }
    public static List<Voter> votersWithPartialChoices() {
        List<Voter> votersWithCompleteChoices = new ArrayList<>();
        votersWithCompleteChoices.add(new Voter(1, "DBCA"));
        votersWithCompleteChoices.add(new Voter(1, "BC"));
        votersWithCompleteChoices.add(new Voter(1, "ABD"));
        votersWithCompleteChoices.add(new Voter(1, "DC"));
        return votersWithCompleteChoices;
    }
    /*
     * To showcase handling of choices with spaces
     */
    public static List<Voter> votersWithInvalidAndDuplicateChoices() {
      List<Voter> votersWithCompleteChoices = new ArrayList<>();
      votersWithCompleteChoices.add(new Voter(1, "ABDD"));
      votersWithCompleteChoices.add(new Voter(1, "BCZ"));
      votersWithCompleteChoices.add(new Voter(1, "ABDA"));
      votersWithCompleteChoices.add(new Voter(1, "CDK"));
      return votersWithCompleteChoices;
  }
  /*
   * To showcase handling of choices with spaces
   */
    public static List<Voter> votersWithSpacesInChoices() {
        List<Voter> votersWithCompleteChoices = new ArrayList<>();
        votersWithCompleteChoices.add(new Voter(1, "C B A D"));
        votersWithCompleteChoices.add(new Voter(1, " B CD  A "));
        votersWithCompleteChoices.add(new Voter(1, "AB CD"));
        votersWithCompleteChoices.add(new Voter(1, " CAD "));
        return votersWithCompleteChoices;
    }
    /*
     * To showcase handling of exhausted vote and quota situation
     */
    public static List<Voter> votersWithDataThatTriggersExhaustedVote() {
        List<Voter> votersWithCompleteChoices = new ArrayList<>();
        votersWithCompleteChoices.add(new Voter(1, "ABDC"));
        votersWithCompleteChoices.add(new Voter(1, "BAD"));
        votersWithCompleteChoices.add(new Voter(1, "BAC"));
        votersWithCompleteChoices.add(new Voter(1, "CABD"));
        votersWithCompleteChoices.add(new Voter(1, "CDAB"));
        votersWithCompleteChoices.add(new Voter(1, "CBAD"));
        votersWithCompleteChoices.add(new Voter(1, "DA"));
        votersWithCompleteChoices.add(new Voter(1, "DB"));
        return votersWithCompleteChoices;
    }

}
