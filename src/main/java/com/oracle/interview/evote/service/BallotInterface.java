package com.oracle.interview.evote.service;

import com.oracle.interview.evote.domain.Ballot;
import com.oracle.interview.evote.domain.Voter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BallotInterface {
        List<Ballot> createBallots(List<Voter> voterList) throws  Exception;
        Optional<Ballot> createBallot(Voter voter) throws Exception;
        Map<String, List<Ballot>> createCandidatesBucket(List<Ballot> ballots);
        Map<String, List<Ballot>> updateBallotsWithElimination(Map<String, List<Ballot>> candidateBucket, String candidatesToEliminate);
        long countExhaustedBallots(Map<String, List<Ballot>> updatedBallotMap, String candidatesToEliminate);
}