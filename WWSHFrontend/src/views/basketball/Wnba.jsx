import React, { useEffect, useState } from 'react';
import TeamsCard from '../../components/TeamsCard';

export default function Wnba() {
    const [teams, setTeams] = useState([]);

    useEffect(() => {
        fetch('https://site.api.espn.com/apis/site/v2/sports/basketball/wnba/teams')
            .then(response => response.json())
            .then(data => setTeams(data.sports[0].leagues[0].teams))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    return (
        <div className="container mt-4">
            <h1 className="text-center mb-5">WNBA Teams</h1>
            <div className="row">
                {teams.map(team => (
                    <TeamsCard key={team.team.id} team={team} league="wnba"/>
                ))}
            </div>
        </div>
    );
}