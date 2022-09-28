package com.oracle.interview.evote.service;

import com.oracle.interview.evote.domain.Ballot;
import com.oracle.interview.evote.domain.Voter;

import java.util.Optional;

public interface BallotInterface {
        Optional<Ballot> createBallot(Voter voter) throws Exception;
}