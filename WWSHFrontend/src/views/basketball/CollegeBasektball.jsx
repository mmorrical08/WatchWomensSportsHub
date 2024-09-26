import React, { useEffect, useState } from 'react';
import TeamsCard from '../../components/TeamsCard';

export default function CollegeBasketball() {
    const [teams, setTeams] = useState([]);
    const [loading, setLoading] = useState(true); // Loading state

    useEffect(() => {
        fetch('https://site.api.espn.com/apis/site/v2/sports/basketball/womens-college-basketball/teams?limit=5000')
            .then(response => response.json())
            .then(data => {
                setTeams(data.sports[0].leagues[0].teams);
                setLoading(false); // Set loading to false once data is fetched
            })
            .catch(error => {
                console.error('Error fetching data:', error);
                setLoading(false); // Ensure loading is false on error as well
            });
    }, []);

    if (loading) {
        return (
            <div className="text-center mt-5">
                <div className="spinner-border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </div>
                <p>Loading College Basketball Teams...</p>
            </div>
        );
    }

    return (
        <div className="container mt-4">
            <h1 className="text-center mb-5">College Basketball Teams</h1>
            <div className="row">
                {teams.map(team => (
                    <TeamsCard key={team.team.id} team={team} league="college" />
                ))}
            </div>
        </div>
    );
}

