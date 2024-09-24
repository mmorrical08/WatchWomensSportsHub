import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { findAll } from "../api/favoriteApi";
import { AuthContext } from "../AppProvider";
import Card from "../components/Card";

export default function Favorites() {
    const [favorites, setFavorites] = useState();
    const { user } = useContext(AuthContext);

    useEffect(() => {
        findAll()
            .then(setFavorites);
    }, []);

    return (
        <div>
            <h1>Favorites</h1>
            <div className="row">
                {favorites?.map(favorite => <Card key={favorite.id} item={favorite} routeName="favorite" />)}
            </div>
            {user?.hasAnyAuthority("USER", "ADMIN") &&
                <Link to="/favorite/add" className="btn btn-purple mt-3">Add Favorite</Link>}
        </div>
    );
}