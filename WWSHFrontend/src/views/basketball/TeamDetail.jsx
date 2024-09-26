import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Link } from 'react-router-dom';

export default function TeamDetail() {
    const { id, league } = useParams(); // Assuming league is also a part of the URL
    const [team, setTeam] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

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

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    // Ensure that team and its nextEvent property are defined before rendering them
    if (!team || !team.nextEvent) {
        return <div>No upcoming events available for this team.</div>;
    }

    // Convert the date to a readable format
    let eventDate;
    let formattedDate;
    let formattedTime;
    if (team.nextEvent && team.nextEvent[0] && team.nextEvent[0].date) {
        eventDate = new Date(team.nextEvent[0].date); // Parse the date if it exists

        formattedDate = eventDate.toLocaleDateString('en-US', {
            weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'
        });

        formattedTime = eventDate.toLocaleTimeString('en-US', {
            hour: '2-digit', minute: '2-digit'
        });

    } else {
        eventDate = "Not yet scheduled"; // Fallback if the date doesn't exist
    }



    return (
        <>
            <div className='card'>
                <h1>{team.displayName}</h1>
                <img src={team.logos[0].href} className="card-img-top" alt={team.displayName} />
                <div className="card-body">
                    <h2>Next Event</h2>
                    <div>
                        <p>
                            {eventDate instanceof Date
                                ? team.nextEvent[0].name
                                : "Not yet scheduled"
                            }
                        </p>
                        <p>
                            {eventDate instanceof Date
                                ? eventDate.toLocaleDateString('en-US', {
                                    weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'
                                })
                                : null
                            }
                        </p>
                        <p>
                            {eventDate instanceof Date
                                ? eventDate.toLocaleTimeString('en-US', {
                                    hour: '2-digit', minute: '2-digit'
                                })
                                : null
                            }
                        </p>
                        {/* Add other event details you want to display */}
                    </div>
                    {/* <Link to={`/athlete/basketball/${league}/teams/${id}/roster`} className="btn btn-secondary mt-3">
                View Roster
            </Link> */}
                </div>
            </div>
        </>
    );
}
