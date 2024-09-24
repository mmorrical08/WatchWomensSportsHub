import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Link } from 'react-router-dom';
import TeamDetail from '../views/basketball/TeamDetail';

export default function Roster() {
    const { id, league } = useParams();  // Assuming id is the team ID and league is either 'wnba' or 'college'
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
                console.error('Error fetching roster data:', err);
                setError('Error fetching roster data');
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
        return <div>No roster available for this team.</div>;
    }

    return (
        <div className="container">
            <div className="row">
                <>
                    <div className="col-md-3">
                    </div>
                    <div className="col-md-6">
                        <TeamDetail />
                    </div>
                    {/* Loop through roster and display each player as a card */}
                    {roster.map(player => (

                        <div className="col-md-4" key={player.id}>
                            <div className="card mb-3">
                                <img
                                    src={player.headshot ? player.headshot.href : "https://www.pngitem.com/pimgs/m/236-2369728_unknown-person-hd-png-download.png"}
                                    className="card-img-top"
                                    alt={player.fullName}
                                />
                                <div className="card-body">
                                    <h5 className="card-title">{player.displayName} #{player.jersey}</h5>
                                    <p className="card-text">
                                        Position: {player.position?.abbreviation || player.position?.name}
                                    </p>
                                    <p className="card-text">Height: {player.displayHeight}</p>
                                    {league === 'college' && (
                                        <p className="card-text">Class: {player.experience.displayValue}</p>
                                    )}
                                    <p className="card-text">
                                        Birth Place: {player.birthPlace.city}{player.birthPlace.state ? `, ${player.birthPlace.state}` : ''}, {player.birthPlace.country}
                                    </p>
                                </div>
                                <div className="bottom">
                                    <button className="heart-button" >
                                        <i className="fa-solid fa-heart"></i>
                                    </button>
                                </div>
                            </div>
                        </div>

                    ))}
                </>
            </div>
        </div>
    );
}
