package learn.wwsh.domain;

import learn.wwsh.models.Athlete;
import learn.wwsh.models.Favorite;
import learn.wwsh.models.Sport;
import learn.wwsh.models.Team;

public class MockData {
    public static Sport makeSport() {
        Sport sport = new Sport();
        sport.setSportId(1);
        sport.setName("basketball");
        sport.setLeague("wnba");
        return sport;
    }
    public static Team makeTeam() {
        Team team = new Team();
        team.setTeamId(1);
        team.setSportId(1);
        team.setTeamName("Storm");
        team.setCity("Seattle");
        team.setState("WA");
        team.setDefaultLogo("storm.png");
        team.setDarkLogo("storm-dark.png");
        return team;
    }
    public static Athlete makeAthlete() {
        Athlete athlete = new Athlete();
        athlete.setAthleteId(1);
        athlete.setTeamId(1);
        athlete.setFirstName("First 1");
        athlete.setLastName("Last 1");
        athlete.setHeight(72);
        athlete.setDisplayHeight(6);
        athlete.setHeadshot("headshot1.jpg");
        athlete.setJersey(1);
        athlete.setPositionName("Position 1");
        athlete.setExperienceYear("Year 1");

        return athlete;
    }
    public static Athlete makeAthlete2(){
        Athlete athlete = makeAthlete();
        athlete.setAthleteId(2);
        athlete.setFirstName("First 2");
        athlete.setLastName("Last 2");
        athlete.setJersey(2);
        athlete.setHeadshot("headshot2.jpg");

        return athlete;
    }
    public static Favorite makeFavoriteTeam() {
        Favorite favorite = new Favorite();
        favorite.setFavoriteId(1);
        favorite.setUserId(1);
        favorite.setTeamId(1);
        favorite.setAthleteId(0);
        return favorite;
    }
    public static Favorite makeFavoriteAthlete() {
        Favorite favorite = new Favorite();
        favorite.setFavoriteId(1);
        favorite.setUserId(1);
        favorite.setTeamId(0);
        favorite.setAthleteId(1);
        return favorite;
    }
    public static Favorite makeFaveTeam2(){
        Favorite favorite = makeFavoriteTeam();
        favorite.setFavoriteId(2);
        favorite.setUserId(2);
        return favorite;
    }
    public static Favorite makeFaveAthlete2(){
        Favorite favorite = makeFavoriteAthlete();
        favorite.setFavoriteId(2);
        favorite.setUserId(2);
        return favorite;
    }
}
