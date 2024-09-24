import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';

export default function Roster() {
    const { playerId, id, league } = useParams();  // Assuming id is the team ID and league is either 'wnba' or 'college'
    const [roster, setRoster] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        // Adjust the fetch URL based on the league type
        const url = league === 'wnba'
            ? `https://site.api.espn.com/apis/site/v2/sports/basketball/wnba/teams/${id}/roster`
            : `https://site.api.espn.com/apis/site/v2/sports/basketball/womens-college-basketball/teams/${id}/roster`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                console.log('Roster data:', data);
                setRoster(data.athletes || []);  // Assuming 'athletes' is the correct field for roster data
                setLoading(false);
            })
            .catch(err => {
                console.error('Error fetching athlete data:', err);
                setError('Error fetching athlete data');
                setLoading(false);
            });
    }, [id, league]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    if (!roster.length) {
        return <div>No available information.</div>;
    }
    return (
        <div>
            {roster.map(player => {
                if (player.id === playerId) {
                    return (
                        <>
                            <div className="container h-100">
                                <div className="row justify-content-center">
                                    <div className="col-md-6">
                                        <div className="card">
                                            <img src={player.headshot.href}
                                                className="card-img-top" alt={player.fullName} />
                                            <div className="card-body">
                                                <h5 className="card-title">{player.fullName}, #{player.jersey}</h5>
                                                <p className="card-text">Position: {player.position.abbreviation}</p>
                                                <p className="card-text">Height: {player.displayHeight}</p>
                                                <p className="card-text">Birth Place: {player.birthPlace.city}, {player.birthPlace.state}, {player.birthPlace.country}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <button className="btn btn-purple" onClick={() => history.back()}>Back to Roster</button>
                        </>
                    );
                }
            }
            )}
        </div>
    );

}
