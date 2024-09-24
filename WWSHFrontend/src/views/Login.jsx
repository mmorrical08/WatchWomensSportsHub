import { useContext, useState } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import { login as sendLoginRequest } from "../api/authApi";
import { AuthContext} from "../AppProvider";
import { createAccount } from "../api/authApi";


export default function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(false);

    const { login } = useContext(AuthContext);
    const navigate = useNavigate();
    const location = useLocation(); // Hook to get current path

    // Determine if we're on the "/create-account" or "/authenticate" route
    const isSignUpPage = location.pathname === '/create-account';
    
    function handleSubmit(evt) {
        evt.preventDefault();

        if (isSignUpPage) {
            //mandy.morrical@gmail.com Password!1
            createAccount(username, password)
                .then((user) => {
                    login(user);
                    setError(false);
                    navigate("/");
                })
                .catch(() => {
                    setError(true);
                });

        } else {
            // Handle login logic
            sendLoginRequest(username, password)
                .then((user) => {
                    login(user);
                    setError(false);
                    navigate("/");
                })
                .catch(() => {
                    setError(true);
                    console.log(Response.json)
                });
        }
    }

    return (
        <>
        <form onSubmit={handleSubmit}>
            <h1>{isSignUpPage ? 'Sign Up' : 'Sign In'}</h1>
            <div className="mb-3">
                <label htmlFor="username" className="form-label text-start">Email</label>
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="form-control"
                    id="username" />
            </div>
            <div className="mb-3">
                <label htmlFor="password" className="form-label text-start">{isSignUpPage ? 'Create a Password' : 'Password'}</label>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="form-control"
                    id="password" />
            </div>
            <div>
                <Link to="/" className="btn btn-secondary me-2">Cancel</Link>
                <button type="submit" className="btn btn-purple me-2">{isSignUpPage ? 'Sign Up' : 'Sign In'}</button>
            </div>
            {error && <div className="alert alert-danger mt-3 mb-3">{isSignUpPage ? 'Sign Up failed.' : 'Sign In failed.'}</div>}
        </form>

        {/* Only show the sign-up link if we're on the /authenticate page */}
        {!isSignUpPage && (
            <div className="container mt-5">
                <p>Don't have an account?</p>
                <Link to="/create-account" className="btn btn-purple">Sign Up</Link>
            </div>
        )}
        </>
    );
}
