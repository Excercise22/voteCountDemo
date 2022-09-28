package com.oracle.interview.evote.service;

import com.oracle.interview.evote.domain.Voter;

import java.util.List;

public interface TallyServiceInterface {
    String beginTally(List<Voter> voterList) throws Exception;
}
