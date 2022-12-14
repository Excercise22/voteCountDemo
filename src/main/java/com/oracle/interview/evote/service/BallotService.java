package com.oracle.interview.evote.service;

import com.oracle.interview.evote.domain.Ballot;
import com.oracle.interview.evote.domain.Voter;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class BallotService implements BallotInterface {
    private static Map<Character, String> candidates;
    private static final int CANDIDATE_ELIMINATION_TAG = -1;

    public List<Ballot> createBallots(List<Voter> voterList) throws  Exception {
        candidates = CandidateRepositoryService.getCandidatesWithPrefix(null);
        List<Ballot> ballots = new ArrayList<>();
        Optional<Ballot> ballot;
        if(candidates == null || candidates.size() == 0) {
            System.out.println("Please check options in file - it seems empty");
            return ballots;
        }
        //maps voter choices (Ex. ABCD) to corresponding option with ranking (ex. 1, 2..) on ballot.
        for (int index = 0; index < voterList.size(); index++) {
            ballot = createBallot(voterList.get(index));
            if (ballot.isPresent()) {
                ballots.add(ballot.get());
            }
        }
        return ballots;
    }
    public Optional<Ballot> createBallot(Voter voter) {
        Map<String, Integer> votermap = new HashMap<>();
        String matchedCandidate;
        boolean isValidPreference = true;
        //eliminate unwanted space between preference if supplied
        String preference = voter.getPreference().replaceAll("\\s", "");

        for (int index = 0; index < preference.length(); index++) {
            matchedCandidate = candidates.get(preference.charAt(index));
            // taking care of invalid preference (ex. abz)
            if (matchedCandidate == null) {
                isValidPreference = false;
                break;
                // taking care of duplicate preference(ex. aabb)
            } else if (votermap.get(matchedCandidate) != null) {
                isValidPreference = false;
                break;
            } else {
                votermap.put(matchedCandidate, index + 1);
            }
        }
        return isValidPreference ? Optional.of(new Ballot(votermap)) : Optional.empty();
    }

    public Map<String, List<Ballot>> createCandidatesBucket(List<Ballot> ballots){
        Map<String, List<Ballot>> candidateBucket = new HashMap<>();
        for (int index = 0; index < ballots.size(); index++) {
            Optional<String> candidate = ballots.get(index).getVoteMap().entrySet().stream().filter(e -> e.getValue() == 1).map(e -> e.getKey()).findFirst();
            List<Ballot> ballotArrayList = new ArrayList<>();
            if (candidate.isPresent()) {
                List<Ballot> candidatePrefBallots = candidateBucket.get(candidate.get());
                if (candidatePrefBallots == null) {
                    candidateBucket.put(candidate.get(), Collections.singletonList(ballots.get(index)));

                } else {
                    ballotArrayList.addAll(candidatePrefBallots);
                    ballotArrayList.add(ballots.get(index));
                    candidateBucket.put(candidate.get(), ballotArrayList);
                }
            }
        }
        System.out.println("***** Round 1 *****");
        candidateBucket.entrySet().forEach(entry -> {
            System.out.println(String.format("Candidate %s having votes %d", entry.getKey(), entry.getValue().size()));
        });
        return candidateBucket;
    }
    public long countExhaustedBallots(Map<String, List<Ballot>> updatedBallotMap, String candidatesToEliminate) {
        return updatedBallotMap.entrySet().stream().filter(m -> m.getKey().equalsIgnoreCase(candidatesToEliminate)).flatMap(entry -> entry.getValue().stream()).map(v -> v.getVoteMap()).filter(pref -> pref.values().stream().allMatch(p -> p == CANDIDATE_ELIMINATION_TAG)).count();
    }

    public Map<String, List<Ballot>> updateBallotsWithElimination(Map<String, List<Ballot>> candidateBucket, String candidatesToEliminate) {
        Map<String, List<Ballot>> updatedBucket;
        updatedBucket = candidateBucket.entrySet().stream().map(entry -> {
            List<Ballot> ballots = entry.getValue();
            List<Ballot> updatedBallots = new ArrayList<>();
            for (int bIndex = 0; bIndex < ballots.size(); bIndex++) {
                Map<String, Integer> voterMap = ballots.get(bIndex).getVoteMap();
                if (voterMap.containsKey(candidatesToEliminate)) {
                    voterMap.replace(candidatesToEliminate, CANDIDATE_ELIMINATION_TAG);
                }
                updatedBallots.add(new Ballot(voterMap));
            }
            entry.setValue(updatedBallots);
            return entry;
        }).collect(Collectors.toMap(k -> k.getKey(), k -> k.getValue()));
        return updatedBucket;

    }


    public  Map<String, List<Ballot>> reAllocateBallots(Map<String, List<Ballot>> updatedBucket, String candidatesToEliminate, int round) {
        System.out.println(String.format("*****  Round %d *****", round));
        System.out.println(String.format("Candidate %s eliminated", candidatesToEliminate));
        Map<String, List<Ballot>> reallocatedBucket = new HashMap<>();
        int exhaustedBallot = (int) countExhaustedBallots(updatedBucket, candidatesToEliminate);
        List<Ballot> reallocateBallots = updatedBucket.entrySet().stream().filter(entry -> entry.getKey().equalsIgnoreCase(candidatesToEliminate)).flatMap(e -> e.getValue().stream()).collect(toList());
        updatedBucket.remove(candidatesToEliminate);
        if (exhaustedBallot > 0) {
            // remove ballot from reallocation
            reallocateBallots = reallocateBallots.stream().filter(ballot -> {
                return !ballot.getVoteMap().values().stream().allMatch(p -> p == CANDIDATE_ELIMINATION_TAG);
            }).collect(toList());
            System.out.println("Exhausted ballots: " + exhaustedBallot);

        }

        List<Ballot> ballotsWithReallocation = null;
        for (int index = 0; index < reallocateBallots.size(); index++) {
            Ballot ballotToReallocate = reallocateBallots.get(index);
            Map.Entry<String, Integer> highestPrefEntry = ballotToReallocate.getVoteMap().entrySet().stream().filter(v -> v.getValue() != CANDIDATE_ELIMINATION_TAG).sorted(Comparator.comparing(Map.Entry::getValue)).collect(toList()).get(0);
            ballotsWithReallocation = updatedBucket.entrySet().stream().filter(u -> u.getKey().equalsIgnoreCase(highestPrefEntry.getKey())).flatMap(s -> s.getValue().stream()).collect(toList());
            ballotsWithReallocation.add(ballotToReallocate);
            updatedBucket.put(highestPrefEntry.getKey(), ballotsWithReallocation);

        }
        reallocatedBucket.putAll(updatedBucket);
        reallocatedBucket.entrySet().forEach(entry -> {
            System.out.println(String.format("Candidate %s having votes %d", entry.getKey(), entry.getValue().size()));
        });
        return reallocatedBucket;

    }


}
