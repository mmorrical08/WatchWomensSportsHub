import React, { useEffect, useState, useContext } from 'react';
import { useParams, Link } from 'react-router-dom';
import TeamDetail from '../views/basketball/TeamDetail';
import { addFavoriteAthlete } from '../api/favoriteApi';
import { AuthContext } from '../AppProvider';

export default function Roster() {
    const { user } = useContext(AuthContext); // Get the user from context
    const { id, league } = useParams();  // Assuming id is the team ID and league is either 'wnba' or 'college'
    const [roster, setRoster] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [team, setTeam] = useState(null);
    const [messages, setMessages] = useState({}); // State for individual player messages

    useEffect(() => {
        // Adjust the fetch URL based on the league type
        const url = league === 'wnba'
            ? `https://site.api.espn.com/apis/site/v2/sports/basketball/wnba/teams/${id}`
            : `https://site.api.espn.com/apis/site/v2/sports/basketball/womens-college-basketball/teams/${id}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                console.log('Team data:', data);
                setTeam(data.team);
                setLoading(false);
            })
            .catch(err => {
                console.error('Error fetching team data:', err);
                setError('Error fetching team data');
                setLoading(false);
            });
    }, [id, league]);

    // Function to handle adding a player as a favorite
    const handleAddFavorite = async (player) => {
        if (user) {
            try {
                const response = await addFavoriteAthlete(user.appUserId, player, team);
                const status = response.status;
                const messageObj = status === 200
                    ? { type: 'success', text: `${player.displayName} added to your favorites!` }
                    : { type: 'warning', text: `${player.displayName} is already in your favorites.` };

                // Set the message for this specific player
                setMessages((prevMessages) => ({
                    ...prevMessages,
                    [player.id]: messageObj,
                }));

                // Auto-clear the message after 3 seconds
                setTimeout(() => {
                    setMessages((prevMessages) => ({
                        ...prevMessages,
                        [player.id]: null,
                    }));
                }, 3000);
            } catch (error) {
                console.error('Error adding favorite:', error);
                setMessages((prevMessages) => ({
                    ...prevMessages,
                    [player.id]: { type: 'danger', text: 'An error occurred while adding to favorites.' },
                }));
            }
        } else {
            console.log('User is not logged in');
        }
    };

    useEffect(() => {
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
                <div className="col-md-3">
                    {/* Add any team-related side content here */}
                </div>
                <div className="col-md-6">
                    <TeamDetail />
                </div>
    
                {/* Loop through roster and display each player as a card */}
                {roster.map((player) => (
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
                                {/* Pass the player object to handleAddFavorite when clicked */}
                                <button className="heart-button" onClick={() => handleAddFavorite(player)}>
                                    <i className="fa-solid fa-heart"></i>
                                </button>
                            </div>
    
                            {/* Display the message for the individual player */}
                            {messages[player.id] && (
                                <div className={`alert alert-${messages[player.id].type} mt-3`} role="alert">
                                    {messages[player.id].text}
                                </div>
                            )}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}    