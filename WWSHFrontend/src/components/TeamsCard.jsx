import React, { useContext, useState } from 'react';
import { Link } from 'react-router-dom';
import { addFavoriteTeam } from '../api/favoriteApi';
import { AuthContext } from '../AppProvider';

export default function TeamsCard({ team, league }) {
    const { user } = useContext(AuthContext); // Get the user from context
    const [loading, setLoading] = useState(false); // Loading state
    const [message, setMessage] = useState(null); // State for the message
    const [messageType, setMessageType] = useState(''); // Type of the message ('success', 'warning', 'danger')

    const handleAddFavorite = async () => {
        if (user) {
            setLoading(true); // Set loading to true when starting the API call
            try {
                // Call the API function with user ID and team ID
                if( (await addFavoriteTeam(user.appUserId, team.team)).status === 200) {

                setMessage(`${team.team.displayName} added to your favorites!`); // Set success message
                setMessageType('success'); // Message type is success
                }else{
                    setMessage(`${team.team.displayName} is already in your favorites.`); // Set warning message
                    setMessageType('warning'); // Message type is warning
                }
            } catch (error) {
                if (error.response?.status === 500) {
                    setMessage(`${team.team.displayName} is already in your favorites.`); // Set warning message
                    setMessageType('warning'); // Message type is warning
                } else {
                    setMessage('An error occurred while adding the team to favorites.'); // Generic error message
                    setMessageType('danger'); // Message type is error
                }
                console.error("Error adding favorite team:", error);
            } finally {
                setLoading(false); // Set loading to false when done
                // Clear the message after 3 seconds
                setTimeout(() => setMessage(null), 3000);
            }
        } else {
            // Handle the case when the user is not logged in
            console.log('User is not logged in');
        }
    };

    return (
        <div className="col-md-4 mb-4">
            <div className="card">
                <img
                    src={team.team.logos[0].href}
                    className="card-img-top"
                    alt={`${team.team.displayName} logo`}
                    style={{ height: '150px', objectFit: 'contain' }}
                />
                <div className="card-body">
                    <h5 className="card-title">{team.team.displayName}</h5>
                    <Link to={`/athlete/basketball/${league}/teams/${team.team.id}/roster`} className="btn btn-purple">
                        View Team
                    </Link>
                </div>
                <div className="bottom">
                    <button className="heart-button" onClick={handleAddFavorite} disabled={loading}>
                        <i className={`fa-solid fa-heart ${loading ? 'loading' : ''}`}></i>
                    </button>
                </div>

                {/* Display the message */}
                {message && (
                    <div className={`alert alert-${messageType} mt-3`} role="alert">
                        {message}
                    </div>
                )}
            </div>
        </div>
    );
}


