package com.oracle.interview.evote;

import com.oracle.interview.evote.data.TestData;
import com.oracle.interview.evote.domain.Ballot;
import com.oracle.interview.evote.domain.Voter;
import com.oracle.interview.evote.service.BallotInterface;
import com.oracle.interview.evote.service.BallotService;
import com.oracle.interview.evote.service.CandidateRepositoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BallotServiceTest {

    BallotInterface ballotInterface = new BallotService();

    @BeforeEach
    public void setup() throws Exception {
        TestData.initializeOptions();
    }
    @Test
    public void createBallotsTestHavingFullChoices() throws  Exception {
        List<Voter> voters = TestData.votersWithCompleteChoices();
        List<Ballot> response = ballotInterface.createBallots(voters);
        Assertions.assertEquals(voters.size(), response.size());
    }
    @Test
    public void createBallotsTestHavingPartialChoices() throws  Exception {
        List<Voter> voters = TestData.votersWithPartialChoices();
        List<Ballot> response = ballotInterface.createBallots(voters);
        Assertions.assertEquals(voters.size(), response.size());
    }
    @Test
    public void createBallotsTestWithInvalidChoices() throws  Exception {
        List<Voter> voters = TestData.votersWithInvalidAndDuplicateChoices();
        List<Ballot> response = ballotInterface.createBallots(voters);
        Assertions.assertEquals(0, response.size());
    }
    @Test
    public void createBallotsTestWithSpacesInChoices() throws  Exception {
        List<Voter> voters = TestData.votersWithSpacesInChoices();
        List<Ballot> response = ballotInterface.createBallots(voters);
        Assertions.assertEquals(voters.size(), response.size());
    }

    @Test
    public void getOptionsWithPrefixWhenValidFile() throws  Exception {
        TestData.initializeOptions();
        Map<Character, String> response = CandidateRepositoryService.getCandidatesWithPrefix(new File("src/test/java/com/oracle/interviw/evote/data/candidates.txt"));
        assertTrue(response.size() > 0);

    }

}
