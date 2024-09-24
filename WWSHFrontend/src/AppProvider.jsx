import { createContext, useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { refresh } from "./api/authApi";
import AppRouter from "./AppRouter";

const timer = 1000 * 60 * 14; // 14 minutes

export const AuthContext = createContext();

export default function AppProvider() {

    const [user, setUser] = useState();
    const [initialized, setInitialized] = useState(false);

    function refreshToken() {
        if (localStorage.getItem("jwt")) {
            refresh()
                .then(setUser)
                .catch(logout)
                .finally(() => {
                    setInitialized(true);
                    localStorage.getItem("jwt") &&
                        setTimeout(refreshToken, timer);
                });
        } else {
            setInitialized(true);
        }
    }

    useEffect(() => {
        refreshToken();
    }, []);

    function login(user) {
        setUser(user);
        setTimeout(refreshToken, timer);
    }

    function logout() {
        localStorage.removeItem('jwt');
        setUser(null);
    }

    function considerRedirect(Component, ...authorities) {
        if (initialized) {
            if (user?.hasAnyAuthority(...authorities)) {
                return <Component />;
            } else {
                return <Navigate to="/authenticate" />;
            }
        }
        return (
            <div className="spinner-border m-5" role="status">
                <span className="visually-hidden">Loading...</span>
            </div>
        );
    }

    const userManager = {
        user,
        login,
        logout,
        considerRedirect
    }

    return (
        <AuthContext.Provider value={userManager}>
            <AppRouter />
        </AuthContext.Provider>
    )
}