package Match;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MatchResult {
    private final IntegerProperty matchId;
    private final StringProperty teamAName;
    private final StringProperty teamBName;
    private final IntegerProperty teamAScore;
    private final IntegerProperty teamBScore;
    private final StringProperty field;
    private final StringProperty dateMatch;
    private final StringProperty city;
    private final IntegerProperty refereeId;

    public MatchResult(int matchId, String teamAName, String teamBName, int teamAScore, int teamBScore, String field, String dateMatch, String city, int refereeId) {
        this.matchId = new SimpleIntegerProperty(matchId);
        this.teamAName = new SimpleStringProperty(teamAName);
        this.teamBName = new SimpleStringProperty(teamBName);
        this.teamAScore = new SimpleIntegerProperty(teamAScore);
        this.teamBScore = new SimpleIntegerProperty(teamBScore);
        this.field = new SimpleStringProperty(field);
        this.dateMatch = new SimpleStringProperty(dateMatch);
        this.city = new SimpleStringProperty(city);
        this.refereeId = new SimpleIntegerProperty(refereeId);
    }

    public int getMatchId() {
        return matchId.get();
    }

    public IntegerProperty matchIdProperty() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId.set(matchId);
    }

    public String getTeamAName() {
        return teamAName.get();
    }

    public String getTeamBName() {
        return teamBName.get();
    }

    /*public void setTeamAId(int teamAId) {
        this.teamAId.set(teamAId);
    }

    public int getTeamBId() {
        return teamBId.get();
    }
*/
    public StringProperty teamBNameProperty() {
        return teamBName;
    }
    public StringProperty teamANameProperty() {
        return teamAName;
    }

    /*public void setTeamBId(int teamBId) {
        this.teamBId.set(teamBId);
    }*/

    public int getTeamAScore() {
        return teamAScore.get();
    }

    public IntegerProperty teamAScoreProperty() {
        return teamAScore;
    }

    public void setTeamAScore(int teamAScore) {
        this.teamAScore.set(teamAScore);
    }

    public int getTeamBScore() {
        return teamBScore.get();
    }

    public IntegerProperty teamBScoreProperty() {
        return teamBScore;
    }

    public void setTeamBScore(int teamBScore) {
        this.teamBScore.set(teamBScore);
    }

    public String getField() {
        return field.get();
    }

    public StringProperty fieldProperty() {
        return field;
    }

    public void setField(String field) {
        this.field.set(field);
    }

    public String getDateMatch() {
        return dateMatch.get();
    }

    public StringProperty dateMatchProperty() {
        return dateMatch;
    }

    public void setDateMatch(String dateMatch) {
        this.dateMatch.set(dateMatch);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public int getRefereeId() {
        return refereeId.get();
    }

    public IntegerProperty refereeIdProperty() {
        return refereeId;
    }

    public void setRefereeId(int refereeId) {
        this.refereeId.set(refereeId);
    }
}
