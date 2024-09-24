import { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../AppProvider";

export default function Card({ routeName, item }) {

    const { user } = useContext(AuthContext);

    return (
        <div className="col" key={item.id}>
            <div className="card">
                {item.imageUrl && <img src={item.imageUrl} className="card-img-top" alt={item.name} />}
                <div className="card-body">
                    <h5 className="card-title">{item.name}</h5>
                </div>
                {user?.hasAnyAuthority("USER", "ADMIN") && <div className="card-footer text-body-secondary text-center">
                    {user?.hasAnyAuthority("USER") &&
                        <Link to={`/${routeName}/delete/${item.id}`} className="btn btn-danger me-2">Delete</Link>}
                    <Link to={`/${routeName}/edit/${item.id}`} className="btn btn-info">Edit</Link>
                </div>
                }
            </div>
        </div>
    );
}