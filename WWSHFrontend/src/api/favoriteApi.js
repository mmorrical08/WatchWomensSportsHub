import { find } from "./api";
import { addTeam } from "./teamApi";
import { addAthlete } from "./athleteApi";

const API_URL = `http://localhost:8080/api/favorites`;

export async function findAll(userId) {
    return find(API_URL, "/user/",  userId );
}

export async function addFavoriteTeam(userId, initTeam) {
    // First add the team (or get the existing team id)
    const teamId = await addTeam(initTeam);

    // Then use that teamId in the favorite_team insert
    const newURL = `${API_URL}/user/${userId}/add`;
    const favorite = {
        favoriteId: 0,
        appUserId: userId,
        teamId: teamId, // Use the returned teamId
        athleteId: 0
    };

    return fetch(newURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        },
        body: JSON.stringify(favorite)
    });
}


// favoriteApi.js
export async function addFavoriteAthlete(userId, athlete, team) {
    console.log(athlete);

    const teamId = await addTeam(team); // Pass the team data here
    const athleteId = await addAthlete(athlete, teamId); // Pass the teamId here

    const newURL = `${API_URL}/user/${userId}/add`;
    const favorite = {
        favoriteId: 0,
        appUserId: userId,
        teamId: 0,
        athleteId: athlete.id // Use the returned athleteId
    };
    console.log(favorite);
    console.log(athleteId);
    return fetch(newURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        },
        body: JSON.stringify(favorite)
    });
}

export async function deleteFavorite(favorite, userId) {
    const newURL = `${API_URL}/user/${userId}/delete`;
    return fetch(newURL, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        },
        body: JSON.stringify(favorite)
    });
}


