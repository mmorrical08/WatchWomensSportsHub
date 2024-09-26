import { find } from "./api";

const API_URL = 'http://localhost:8080/api/team';

export async function findAll() {
    return find(API_URL);
}

export async function findTeam(teamId) {
    return find(API_URL, "/id/", teamId);
}

export async function addTeam(initTeam) {
    const API_URL = 'http://localhost:8080/api/team/add';
    const team = {
        teamId: initTeam.id,
        sportId: 1,
        teamName: initTeam.displayName,
        city: initTeam.location,
        state: "state",
        defaultLogo: initTeam.logos[0].href,
        darkLogo: initTeam.logos[1].href,
    };

    console.log(team);
    
    // Send the team data and get the teamId back
    const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        },
        body: JSON.stringify(team)
    });

    const addedTeam = await response.json();
    return addedTeam.teamId;  // Return the teamId to be used in the favorite call
}

export async function addZeroTeam(){
    const API_URL = 'http://localhost:8080/api/team/add';
    const team = {
        teamId: 0,
        sportId: 1,
        teamName: "Zero Team",
        city: "city",
        state: "state",
        defaultLogo: "defaultLogo",
        darkLogo: "darkLogo",
    };  
    
    return fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        },
        body: JSON.stringify(team)
    });
    
}


