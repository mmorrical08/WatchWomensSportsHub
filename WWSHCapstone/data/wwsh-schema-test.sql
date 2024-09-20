drop database if exists wwsh_test;
create database wwsh_test;
use wwsh_test;

-- create tables and relationships
create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default(1)
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id)
        on delete cascade
        on update cascade,
	constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
        on delete cascade
        on update cascade
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
        on delete cascade
        on update cascade
);

create table athlete (
    athlete_id int primary key auto_increment,
    team_id int null, 
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    height int null,
    display_height varchar(10) null,
    headshot varchar(100) null,
    jersey int null,
    position_name varchar(25) null,
    experience_year varchar(25) null,
    constraint fk_team_athlete_id
        foreign key (team_id)
        references team(team_id)
);

create table favorite_team (
	favorite_team_id int primary key auto_increment,
    app_user_id int not null,
    team_id int not null,
    constraint fk_user_favorite_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_team_favorite_team_id
        foreign key (team_id)
        references team(team_id),
    constraint uq_user_team_id
        unique (app_user_id, team_id)
);

create table favorite_athlete (
	favorite_athlete_id int primary key auto_increment,
    app_user_id int not null,
    athlete_id int not null,
    constraint fk_user_favorite_athlete_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_athlete_favorite_id
        foreign key (athlete_id)
        references athlete(athlete_id),
    constraint uq_user_athlete_id
        unique (app_user_id, athlete_id)
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
	delete from app_user;
	alter table app_user auto_increment = 1;
	delete from app_role;
	alter table app_role auto_increment = 1;
    delete from app_user_role;

    
    insert into sport values
	(1, 'basketball', 'wnba'),
    (2, 'basketball', 'womens-college-basketball');
    
    insert into team values
    (1, 1, "Team 1", "City 1", "ST", "default1.png", "dark1.png"),
    (2, 2, "Team 2", "City 2", "ST", "default2.png", "dark2.png");
    
    insert into app_role (`name`) values
    ('USER'),
    ('ADMIN');

-- passwords are set to "P@ssw0rd!"
insert into app_user (username, password_hash, enabled)
    values
    ('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);

insert into app_user_role
    values
    (1, 2),
    (2, 1);
    
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
