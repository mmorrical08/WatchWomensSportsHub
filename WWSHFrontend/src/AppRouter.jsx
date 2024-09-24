import { useContext } from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import App from "./App";
import { AuthContext } from "./AppProvider";
import Home from "./views/Home";
import Login from "./views/Login";
import Sport from "./views/Sport";
import Team from "./views/Team";
import Athlete from "./views/Athlete";
import Favorites from "./views/Favorites";
import NotFound from "./views/NotFound";
import Basketball from "./views/basketball/Basketball";
import Wnba from "./views/basketball/Wnba";
import CollegeBasketball from "./views/basketball/CollegeBasektball";
import TeamDetail from "./views/basketball/TeamDetail";
import Roster from "./components/Roster";
import AthleteDetail from "./components/AthleteDetail";
import AthleteDetailByRoster from "./components/AthleteDetailByRoster"; 

export default function AppRouter() {

    const { considerRedirect } = useContext(AuthContext);


    const router = createBrowserRouter([
        {
            path: "/",
            element: <App />,
            errorElement: <NotFound />,
            children: [
                {
                    path: "/",
                    element: <Home />
                },
                {
                    path: "/sport",
                    element: <Sport />
                },
                {
                    path: "/sport/basketball",
                    element: <Basketball />
                },
                {
                    path: "/team/basketball/wnba",
                    element: <Wnba />
                },
                {
                    path: "/team/basketball/college",
                    element: <CollegeBasketball />
                },
                {
                    path: "/team",
                    element: <Team />
                },
                // {
                //     path: "/team/basketball/:league/teams/:id",
                //     element: <TeamDetail/>
                // },
                {
                    path: "/athlete/basketball/:league/teams/:id/roster",
                    element: <Roster/>
                },
                {
                    path: "/athlete",
                    element: <Athlete />
                },
                {
                    path: "/athlete/basketball/wnba/teams",
                    element: <Wnba/>

                },
                {
                    path: "/athlete/basketball/college/teams",
                    element: <CollegeBasketball/>
                },
                //{
                //     path: "/athlete/basketball/:league",
                //     element: <AthleteDetail/>
                // },
                // {
                //     path: "/athlete/basketball/:league/teams/:id/roster/:playerId",
                //     element: <AthleteDetailByRoster/>
                // },
                {
                    path: "/authenticate",
                    element: <Login />
                },
                {
                    path: "/create-account",
                    element: <Login />
                },

                // {
                //     path: "/team/add",
                //     element: considerRedirect(TeamForm, "ADMIN")
                // },
                // {
                //     path: "/team/delete",
                //     element: considerRedirect(TeamDelete, "ADMIN")
                // },
                {
                    path: "/favorites",
                    element: considerRedirect(Favorites, "USER")
                }

            ]
        }
    ]);
    return <RouterProvider router={router} />;
}
