import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

export default function AthleteDetail() {
    const { league } = useParams();
    const [currentAthlete, setCurrentAthlete] = useState(null); // Current athlete data
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null); 
    const [id, setId] = useState(585); // Default athlete ID for WNBA
    const [displayCount, setDisplayCount] = useState(15);

    const getApiUrl = (league, id) => {

        const baseURL = 'https://sports.core.api.espn.com/v3/sports/basketball/';
        return league === 'wnba'
            ?
            `${baseURL}wnba/athletes/${id}`
            : 
            `${baseURL}womens-college-basketball/athletes/${id}`;
    };
    

    useEffect(() => {
        const fetchAthletes = async () => {
            setLoading(true);
            setError(null);
            if (league !== 'wnba' && id === 585) {
                setId(4703563);
            }
            const tempAthletes = []; // Temporary array to hold active athletes
            
            while (tempAthletes.length < displayCount) {
                const response = await fetch(getApiUrl(league, id));
                const data = await response.json();
                
                if (data.active) {
                    tempAthletes.push(data);
                }
                
                setId(prevId => prevId + 1); // Increment ID for the next athlete
            }

            setAthletes(tempAthletes); // Set the state with active athletes
            setLoading(false);
        };

        fetchAthletes();
    }, [league, id, displayCount]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    if (athletes.length === 0) {
        return <div>No active athletes found.</div>;
    }
    

    return (
        <div>
            <h1>{league === 'wnba' ? 'WNBA Athletes' : 'College Basketball Athletes'}</h1>
            <div className="row">
                {athletes.map(athlete => (
                    <div className="col-md-4" key={athlete.id}>
                        <div className="athlete-card">
                            <h3>{athlete.displayName} #{athlete.jersey}</h3>
                            {league === 'wnba' && (
                                <p>Hometown: {athlete.birthPlace.city}, {athlete.birthPlace.state}, {athlete.birthPlace.country}</p>
                            )}
                            <p>Height: {athlete.displayHeight}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}