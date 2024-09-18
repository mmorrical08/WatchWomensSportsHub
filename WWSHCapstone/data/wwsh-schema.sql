drop database if exists wwsh;
create database wwsh;
use wwsh;

-- create tables and relationships
create table users (
	user_id int primary key auto_increment,
    email varchar(100),
    user_password varchar(100),
    constraint uq_email_password
        unique (email, user_password)
);
create table sport (
    sport_id int primary key auto_increment,
    `name` varchar(50) not null,
    league varchar(50) null
);

create table team (
    team_id int primary key auto_increment,
    sport_id int not null,
    `name` varchar(50) not null,
    city varchar(50) not null,
	state varchar(25) not null,
    default_logo varchar(100) null,
    dark_logo varchar(100) null,
    constraint fk_sport_team_id
        foreign key (sport_id)
        references sport(sport_id)
);

create table athlete (
    athlete_id int primary key auto_increment,
    team_id int null, 
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    height int null,
    display_height int null,
    headdhot varchar(100) null,
    jersey int null,
    position_name varchar(25) null,
    experience_year varchar(25) null,
    constraint fk_team_athlete_id
        foreign key (team_id)
        references team(team_id)
);

create table favorite_team (
	favorite_team_id int primary key auto_increment,
    user_id int not null,
    team_id int not null,
    constraint fk_user_favorite_id
        foreign key (user_id)
        references users(user_id),
    constraint fk_team_favorite_team_id
        foreign key (team_id)
        references team(team_id),
    constraint uq_user_team_id
        unique (user_id, team_id)
);

create table favorite_athlete (
	favorite_athlete_id int primary key auto_increment,
    user_id int not null,
    athlete_id int not null,
    constraint fk_user_favorite_athlete_id
        foreign key (user_id)
        references users(user_id),
    constraint fk_athlete_favorite_id
        foreign key (athlete_id)
        references athlete(athlete_id),
    constraint uq_user_athlete_id
        unique (user_id, athlete_id)
);

-- data
insert into sport values
	(1, 'basketball', 'wnba'),
    (2, 'basketball', 'womens-college-basketball');
    
