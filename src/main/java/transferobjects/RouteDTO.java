package transferobjects;

import java.util.List;

/**
 * Data Transfer Object for route.
 * Contains details about transportation routes including stations and distance.
 * @author jaces
 * 
 */
public class RouteDTO {
    private int routeID;
    private List<StationDTO> stationList;
    private double distance;
    private int expectedDuration;

    /**
     * Default constructor.
     */
    public RouteDTO() {}

    /**
     * Constructs a RouteDTO with specified parameters.
     *
     * @param routeID the unique identifier for the route
     * @param stationList list of stations on this route
     * @param distance the total distance of the route
     */
    public RouteDTO(int routeID, List<StationDTO> stationList, double distance) {
        this.routeID = routeID;
        this.stationList = stationList;
        this.distance = distance;
    }

    /**
     * @return the route ID
     */
    public int getRouteID() {
        return routeID;
    }

    /**
     * @param routeID the route ID to set
     */
    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    /**
     * @return list of stations on this route
     */
    public List<StationDTO> getStationList() {
        return stationList;
    }

    /**
     * @param stationList the list of stations to set
     */
    public void setStationList(List<StationDTO> stationList) {
        this.stationList = stationList;
    }

    /**
     * @return the total distance of the route
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @param distance the route distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * @return the expected duration of the route (in minutes)
     */
    public int getExpectedDuration() {
        return expectedDuration;
    }

    /**
     * @param expectedDuration the expected duration to set (in minutes)
     */
    public void setExpectedDuration(int expectedDuration) {
        this.expectedDuration = expectedDuration;
    }
}