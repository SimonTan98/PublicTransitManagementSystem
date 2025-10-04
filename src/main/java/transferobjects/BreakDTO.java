package transferobjects;

import java.sql.Timestamp;

/**
 * Data Transfer Object for break information.
 * Contains details about user breaks including start and end times.
 * @author Simon
 */
public class BreakDTO {
    private int breakId;
    private int userId;
    private Timestamp startTime;
    private Timestamp endTime;

    /**
     * @return the break ID
     */
    public int getBreakId() {
        return breakId;
    }

    /**
     * @param breakId the break ID to set
     */
    public void setBreakId(int breakId) {
        this.breakId = breakId;
    }

    /**
     * @return the user ID associated with the break
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the start time of the break
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the break start time to set
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the end time of the break
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the break end time to set
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}