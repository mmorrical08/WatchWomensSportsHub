package learn.wwsh.models;

import java.util.Objects;

public class Team {
    private int teamId;
    private int sportId;
    private String teamName;
    private String city;
    private String state;
    private String defaultLogo;
    private String darkLogo;

    public Team() {
    }

    public Team(int teamId, int sportId, String teamName, String city, String state, String defaultLogo, String darkLogo) {
        this.teamId = teamId;
        this.sportId = sportId;
        this.teamName = teamName;
        this.city = city;
        this.state = state;
        this.defaultLogo = defaultLogo;
        this.darkLogo = darkLogo;
    }

    public Team(Team team) {
        this.teamId = team.teamId;
        this.sportId = team.sportId;
        this.teamName = team.teamName;
        this.city = team.city;
        this.state = team.state;
        this.defaultLogo = team.defaultLogo;
        this.darkLogo = team.darkLogo;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDefaultLogo() {
        return defaultLogo;
    }

    public void setDefaultLogo(String defaultLogo) {
        this.defaultLogo = defaultLogo;
    }

    public String getDarkLogo() {
        return darkLogo;
    }

    public void setDarkLogo(String darkLogo) {
        this.darkLogo = darkLogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return teamId == team.teamId && sportId == team.sportId && teamName.equals(team.teamName) && city.equals(team.city) && state.equals(team.state) && defaultLogo.equals(team.defaultLogo) && darkLogo.equals(team.darkLogo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, sportId, teamName, city, state, defaultLogo, darkLogo);
    }
}