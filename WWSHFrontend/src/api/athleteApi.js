// athleteApi.js
import { find } from "./api";
import { addZeroTeam } from "./teamApi";

const API_URL = 'http://localhost:8080/api/athlete';

export async function findAll() {
    return find(API_URL);
}

export async function findAthlete(athleteId) {
    return find(API_URL, "/", athleteId);
}

// Pass teamId as a parameter instead of using useParams
export async function addAthlete(initAthlete, teamId) {
    const zeroTeam = await addZeroTeam();

    const athlete = {
        athleteId: initAthlete.id,
        firstName: initAthlete.firstName,
        lastName: initAthlete.lastName,
        positionName: initAthlete.position.abbreviation,
        teamId: teamId,  // Use the passed teamId here
        jersey: initAthlete.jersey,
        height: initAthlete.height,
        displayHeight: initAthlete.displayHeight,
        headshot: initAthlete.headshot.href,
        experienceYear: initAthlete.experience.years,
    };

    console.log(athlete);
    const newURL = `${API_URL}/add`;
    const response = await fetch(newURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        },
        body: JSON.stringify(athlete)
    });

    const addedAthlete = await response.json();
    return addedAthlete;
}
