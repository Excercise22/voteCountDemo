package com.oracle.interview.evote.service;

import com.oracle.interview.evote.domain.Ballot;
import com.oracle.interview.evote.domain.Voter;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TallyService implements TallyServiceInterface {
    private static final String NO_VALID_BALLOTS_FOUND = "No Valid Ballots Found. Please check entered choices";
    BallotInterface ballotInterface = new BallotService();

    private Optional<String> hasClearWin(Map<String, List<Ballot>> candidatesBucket) {
        Optional<String> isWinner = Optional.empty();
        int nonExhaustedBallot = (int) candidatesBucket.entrySet().stream().map(c -> c.getValue()).flatMap(List::stream).count();
        int quota = (nonExhaustedBallot / 2) + 1;
        System.out.println("Quota is " + quota);
        Optional<Map.Entry<String, List<Ballot>>> winningCandidate = candidatesBucket.entrySet().stream().filter(s -> s.getValue().size() >= quota).findFirst();
        if (winningCandidate.isPresent()) {
            System.out.println("***** Winner *****");
            System.out.println("Candidate " + winningCandidate.get().getKey() + " selected as Winner");
            isWinner = Optional.of(winningCandidate.get().getKey());
        }
        return isWinner;
    }

    private String findPotentialElimination(Map<String, List<Ballot>> candidatesBucket) {
        Map.Entry<String, List<Ballot>> candidateWithLatestPrefCount =
                candidatesBucket.entrySet().stream().sorted(Comparator.comparingInt(s -> s.getValue().size())).toList().get(0);
        return candidateWithLatestPrefCount.getKey();
    }

    public String beginTally(List<Voter> voterList) throws Exception {
        int round;
        List<Ballot> ballots = ballotInterface.createBallots(voterList);

        if(ballots.size() != 0) {
            Map<String, List<Ballot>> candidatesBucket = ballotInterface.createCandidatesBucket(ballots);
            Map<String, List<Ballot>> updatedBuckets;
            round = 2;
            Optional<String> winner = hasClearWin(candidatesBucket);
            while (winner.isEmpty()) {
                String candidatesToEliminate = findPotentialElimination(candidatesBucket);
                updatedBuckets = ballotInterface.updateBallotsWithElimination(candidatesBucket, candidatesToEliminate);
                candidatesBucket = ballotInterface.reAllocateBallots(updatedBuckets, candidatesToEliminate, round);
                round++;
                winner = hasClearWin(candidatesBucket);
            }
            return winner.get();
        } else {
            return NO_VALID_BALLOTS_FOUND;
        }
    }
}
