import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { findAll } from "../api/favoriteApi"; // Fetch all favorites
import { findTeam } from "../api/teamApi"; // Fetch team details
import { findAthlete } from "../api/athleteApi"; // Fetch athlete details (new)
import { AuthContext } from "../AppProvider";
import FavoriteCard from "../components/FavoriteCard";

export default function Favorites() {
    const [favorites, setFavorites] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { user } = useContext(AuthContext);

    useEffect(() => {
        if (user) {
            findAll(user.appUserId)
                .then(async (favorites) => {
                    // Fetch team and athlete details for each favorite and merge the data
                    const favoritesWithDetails = await Promise.all(
                        favorites.map(async (favorite) => {
                            let team = null;
                            let athlete = null;

                            if (favorite.teamId) {
                                // Fetch the team details if the favorite is a team
                                team = await findTeam(favorite.teamId);
                            }

                            if (favorite.athleteId) {
                                // Fetch the athlete details if the favorite is an athlete
                                athlete = await findAthlete(favorite.athleteId);
                            }

                            // Return favorite with either team or athlete details
                            return { ...favorite, team, athlete };
                        })
                    );
                    setFavorites(favoritesWithDetails);
                    setLoading(false);
                })
                .catch((error) => {
                    setError("Failed to load favorites.");
                    setLoading(false);
                });
        }
    }, [user]);

    

    if (loading) {
        return <p>Loading favorites...</p>;
    }

    if (error) {
        return <p>{error}</p>;
    }


    return (
        <div>
            <h1>Favorites</h1>
            {favorites.length === 0 ? (
                <>
                    <p>No favorites found.</p>
                    <img src="https://phantom-marca.unidadeditorial.es/501874cd78901dddc27c13862db2317e/resize/1200/f/webp/assets/multimedia/imagenes/2024/05/29/17170183300235.jpg"
                        className="card-img-top" alt="Dissapointed Caitlin Clark" />
                </>
            ) : (
                <div className="row">
                    {favorites.map((favorite) => (
                        <div className="col-12 col-md-6 col-lg-4" key={favorite.favoriteId}>
                            <FavoriteCard favorite={favorite} />
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}


