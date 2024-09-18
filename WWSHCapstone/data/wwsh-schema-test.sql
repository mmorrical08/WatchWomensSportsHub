drop database if exists wwsh_test;
create database wwsh_test;
use wwsh_test;

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

delimiter //
create procedure set_known_good_state()
begin

	delete from favorite_athlete;
    alter table favorite_athlete auto_increment = 1;
    delete from favorite_team;
	alter table favorite_team auto_increment = 1;
    delete from athlete;
    alter table athlete auto_increment = 1;
    delete from team;
    alter table team auto_increment=1;
    delete from sport;
    alter table sport auto_increment=1;
    delete from users;
    alter table users auto_increment=1;
    
    insert into sport values
	(1, 'basketball', 'wnba'),
    (2, 'basketball', 'womens-college-basketball');
    
    insert into team values
    (1, 1, "Team 1", "City 1", "ST", "default1.png", "dark1.png"),
    (2, 2, "Team 2", "City 2", "ST", "default2.png", "dark2.png");
    
    insert into users values
    (1, "email1", "pass1"),
    (2, "email2", "pass2");
    
    insert into athlete values
    (1, 1, "First 1", "Last 1", 72, 6, "headshot1.png", 1, "Guard", "Rookie"),
    (2, 2, "First 2", "Last 2", 72, 6, "headshot2.png", 2, "Guard", "Rookie");
    
    insert into favorite_team values
    (1, 1, 1),
    (2, 1, 2);
    
    insert into favorite_athlete values
    (1, 2, 1),
    (2, 1, 1);
    
    end //
-- 4. Change the statement terminator back to the original.
delimiter ;

call set_known_good_state();
