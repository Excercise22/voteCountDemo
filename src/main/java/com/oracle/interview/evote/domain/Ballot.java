package com.oracle.interview.evote.domain;

import java.util.Map;

public class Ballot {
    Map<String, Integer> voteMap;

    public Ballot(Map<String, Integer> voteMap) {
        this.voteMap = voteMap;
    }

    public Map<String, Integer> getVoteMap() {
        return voteMap;
    }

    public void setVoteMap(Map<String, Integer> voteMap) {
        this.voteMap = voteMap;
    }
}
