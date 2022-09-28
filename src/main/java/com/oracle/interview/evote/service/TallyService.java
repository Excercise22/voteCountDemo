package com.oracle.interview.evote.service;

import com.oracle.interview.evote.domain.Ballot;
import com.oracle.interview.evote.domain.Voter;

import java.util.List;
import java.util.Map;

public class TallyService implements TallyServiceInterface {
    BallotInterface ballotInterface = new BallotService();
    public String beginTally(List<Voter> voterList) throws Exception {
        int round = 1;
        List<Ballot> ballots = ballotInterface.createBallots(voterList);
        if(ballots.size() != 0) {
            Map<String, List<Ballot>> candidatesBucket = ballotInterface.createCandidatesBucket(ballots);
        }
        return null;
    }
}
