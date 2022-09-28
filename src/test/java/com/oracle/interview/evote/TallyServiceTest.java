package com.oracle.interview.evote;

import com.oracle.interview.evote.data.TestData;
import com.oracle.interview.evote.service.TallyService;
import com.oracle.interview.evote.service.TallyServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TallyServiceTest {
    TallyServiceInterface tallyServiceInterface = new TallyService();
    private static final String NO_VALID_BALLOTS_FOUND = "No Valid Ballots Found. Please check entered choices";

    @BeforeEach
    public void setup() throws Exception {
        TestData.initializeOptions();
    }

    @Test
    public void beginTallyTestWithBallotsHavingFullChoices() throws  Exception {
        TestData.initializeOptions();
        String winner =  tallyServiceInterface.beginTally(TestData.votersWithCompleteChoices());
        System.out.println(winner);
        assertTrue("Winery tour".equalsIgnoreCase(winner));
    }

    @Test
    public void beginTallyTestWithBallotsHavingPartialChoices() throws  Exception {
        String winner =  tallyServiceInterface.beginTally(TestData.votersWithPartialChoices());
        assertTrue("Dinner at a restaurant".equalsIgnoreCase(winner));
    }
    @Test
    public void beginTallyTestWithNoValidBallots() throws  Exception {
        String winner =  tallyServiceInterface.beginTally(TestData.votersWithInvalidAndDuplicateChoices());
        assertTrue(NO_VALID_BALLOTS_FOUND.equalsIgnoreCase(winner));
    }
    @Test
    public void beginTallyTestWithSpacesInChoices() throws  Exception {
        String winner =  tallyServiceInterface.beginTally(TestData.votersWithSpacesInChoices());
        assertTrue("Ten pin bowling".equalsIgnoreCase(winner));
    }
    @Test
    public void beginTallyTestWithBallotsCausingExhaustVotes() throws  Exception {
        String winner =  tallyServiceInterface.beginTally(TestData.votersWithDataThatTriggersExhaustedVote());
        assertTrue("Ten pin bowling".equalsIgnoreCase(winner));
    }
}
