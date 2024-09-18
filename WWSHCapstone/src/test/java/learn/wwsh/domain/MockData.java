package learn.wwsh.domain;

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
