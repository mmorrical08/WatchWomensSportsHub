import React, { useEffect, useState } from 'react';
import TeamsCard from '../../components/TeamsCard';

export default function CollegeBasketball() {
    const [teams, setTeams] = useState([]);

    useEffect(() => {
        fetch('https://site.api.espn.com/apis/site/v2/sports/basketball/womens-college-basketball/teams?limit=5000')
            .then(response => response.json())
            .then(data => setTeams(data.sports[0].leagues[0].teams))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    return (
        <div className="container mt-4">
            <h1 className="text-center mb-5">College Basketball Teams</h1>
            <div className="row">
                {teams.map(team => (
                    <TeamsCard key={team.team.id} team={team} league="college"/>
                ))}
            </div>
        </div>
    );
}


//https://site.api.espn.com/apis/site/v2/sports/basketball/womens-college-basketball/teams