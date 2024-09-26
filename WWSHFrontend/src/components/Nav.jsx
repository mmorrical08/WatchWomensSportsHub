import { Link, NavLink } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../AppProvider";
import 'bootstrap/dist/css/bootstrap.min.css'




export default function Nav() {
    const { user, logout } = useContext(AuthContext);

    function handleSignOut() {
        logout();
    }

    return (
        <nav className="navbar navbar-expand-lg  bg-body-secondary rounded">
            <div className="container-fluid">
                <Link className="navbar-brand" to="/">
                    <img src="https://media.istockphoto.com/vectors/color-sport-background-football-basketball-hockey-box-golf-tennis-vector-id640207442?k=20&m=640207442&s=612x612&w=0&h=qjRFQPVNyVGZsn42WtLZ0B83lQ4oRI5g944Tli-crFY=" alt="Logo"
                        width="50" height="50" className="d-inline-block align-text-top rounded" />
                </Link>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/" >Home</NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/sport" >Sports</NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/team" >Teams</NavLink>
                        </li>
                        {/* <li className="nav-item">
                            <NavLink className="nav-link" to="/athlete" >Athletes</NavLink>
                        </li> */}
                        {user && <li className="nav-item">
                            <NavLink className="nav-link" to="/favorites" >My Hub</NavLink>
                        </li>
                        }
                    </ul>
                    <div className="d-flex flex-column flex-lg-row align-items-center ms-lg-auto justify-content-center text-center text-lg-start">

                        {user ? (
                            <div className="">
                                
                                <button className="btn btn-danger mt-2 mt-lg-0" onClick={handleSignOut}>Sign Out</button>
                            </div>
                        ) : (
                            <NavLink className="btn btn-purple ms-lg-auto" to="/authenticate">Sign In</NavLink>
                        )}
                    </div>
                </div>
            </div>
        </nav>
    )
}