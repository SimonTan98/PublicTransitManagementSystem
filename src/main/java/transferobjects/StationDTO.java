package transferobjects;

/**
 * Data Transfer Object for station.
 * Contains basic details about transportation stations.
 * @author jaces
 */
public class StationDTO {
    private int stationID;
    private String stationName;

    /**
     * Default constructor.
     */
    public StationDTO() {}

    /**
     * Constructs a StationDTO with specified parameters.
     *
     * @param stationID the unique identifier for the station
     * @param stationName the name of the station
     */
    public StationDTO(int stationID, String stationName) {
        this.stationID = stationID;
        this.stationName = stationName;
    }

    /**
     * @return the station ID
     */
    public int getStationID() { return stationID; }
    
    /**
     * @param stationID the station ID to set
     */
    public void setStationID(int stationID) { this.stationID = stationID; }
    
    /**
     * @return the station name
     */
    public String getStationName() { return stationName; }
    
    /**
     * @param stationName the station name to set
     */
    public void setStationName(String stationName) { this.stationName = stationName; }
}