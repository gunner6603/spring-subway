package subway.dto;

import java.util.List;
import subway.domain.ShortestPath;
import subway.domain.Station;

public class PathResponse {

    private final List<Station> stations;
    private final int distance;

    public PathResponse(final List<Station> stations, final int distance) {
        this.stations = stations;
        this.distance = distance;
    }

    public static PathResponse of(ShortestPath path) {
        return new PathResponse(path.getStations(), path.getDistance());
    }

    public List<Station> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }
}
