import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../AppProvider";
import { deleteFavorite } from "../api/favoriteApi"; // API to delete favorite
import { findTeam } from "../api/teamApi";

export default function FavoriteCard({ favorite }) {
    const { user } = useContext(AuthContext);
    const userId = user?.appUserId;

    const [team, setTeam] = useState(null);
    const [loading, setLoading] = useState(true);
    const [nextEvent, setNextEvent] = useState(null); // To store the next event

    // Default values for team
    const teamName = team?.teamName || "Unknown Team";
    const defaultLogo = team?.defaultLogo || "placeholder-image-url";

    // Default values for athlete
    const athleteName = favorite?.athlete
        ? `${favorite.athlete.firstName} ${favorite.athlete.lastName}`
        : "Unknown Athlete";
    const athletePosition = favorite?.athlete?.positionName || "Position not available";
    const athleteImage = favorite?.athlete?.headshot || "placeholder-athlete-image-url";
    const height = favorite?.athlete?.displayHeight || "Unknown height";
    const jersey = favorite?.athlete?.jersey || "?";

    const teamId = favorite?.team?.teamId || favorite?.athlete?.teamId;

    const wnbaTeams = [
        "Atlanta Dream", "Chicago Sky", "Connecticut Sun", "Dallas Wings", "Indiana Fever",
        "Las Vegas Aces", "Los Angeles Sparks", "Minnesota Lynx", "New York Liberty",
        "Phoenix Mercury", "Seattle Storm", "Washington Mystics"
    ];

    // Fetch team data (separate from fetching the ESPN event details)
    useEffect(() => {
        const fetchTeam = async () => {
            if (teamId) {
                try {
                    setLoading(true);
                    const teamData = await findTeam(teamId);
                    setTeam(teamData);
                    console.log("Fetched team data: ", teamData);
                } catch (error) {
                    console.error("Error fetching team data:", error);
                } finally {
                    setLoading(false);
                }
            }
        };

        fetchTeam();
    }, [teamId]); // This useEffect only runs when teamId changes

    // Fetch ESPN event details once the team data is available
    useEffect(() => {
        const fetchEventDetails = async () => {
            if (team && team.teamName) {
                try {
                    setLoading(true);

                    // Determine URL based on league type (WNBA or college basketball)
                    const isWnbaTeam = wnbaTeams.includes(team.teamName);
                    const url = isWnbaTeam
                        ? `https://site.api.espn.com/apis/site/v2/sports/basketball/wnba/teams/${teamId}`
                        : `https://site.api.espn.com/apis/site/v2/sports/basketball/womens-college-basketball/teams/${teamId}`;

                    const response = await fetch(url);
                    const data = await response.json();

                    // Set next event
                    setNextEvent(data.team?.nextEvent?.[0] || null);
                    console.log("Fetched next event data: ", data.team?.nextEvent?.[0]);
                } catch (error) {
                    console.error("Error fetching event data:", error);
                } finally {
                    setLoading(false);
                }
            }
        };

        if (team) {
            fetchEventDetails();
        }
    }, [team]); // This useEffect only runs when the team data has been fetched and is available

    // Handle favorite deletion
    const favoriteDeleted = {
        favoriteId: favorite.favoriteId,
        athleteId: favorite.athleteId,
        teamId: favorite.teamId,
        userId: userId,
    };

    const refreshPage = () => {
        window.location.reload();
    };

    const handleDelete = async () => {
        if (!userId) return; // Ensure user is logged in

        try {
            await deleteFavorite(favoriteDeleted, userId);
            console.log("Favorite deleted successfully.");
            refreshPage(); // Refresh the page to reflect the changes
        } catch (error) {
            console.error("Error deleting favorite:", error);
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    // Format the next event date
    const formatEventDate = (event) => {
        const eventDate = new Date(event.date);
        return eventDate.toLocaleDateString("en-US", {
            weekday: "long",
            year: "numeric",
            month: "long",
            day: "numeric",
        });
    };

    let formattedDate = "No upcoming events";
    if (nextEvent?.date) {
        formattedDate = formatEventDate(nextEvent);
    }

    return (
        <div className="col d-flex align-items-stretch">
            <div className="card">
                {/* Display next event if available */}
                {nextEvent && (
                    <div className="card-header text-center">
                        <h5>Next Event:</h5>
                        <p>{nextEvent.name}</p>
                        <p><i>{formattedDate}</i></p> {/* Render event date in italics */}
                    </div>
                )}

                {/* Check if there's an athlete or a team to display */}
                {favorite?.athlete ? (
                    <>
                        <img
                            src={athleteImage}
                            className="card-img-top"
                            alt={`${athleteName}`}
                            style={{ height: "150px", objectFit: "contain" }}
                        />
                        <div className="card-body">
                            <h5 className="card-title">{athleteName}, #{jersey}</h5>
                            <p className="card-text">Position: {athletePosition}</p>
                            <p className="card-text">Height: {height}</p>
                        </div>
                    </>
                ) : (
                    <>
                        <img
                            src={defaultLogo}
                            className="card-img-top"
                            alt={`${teamName} logo`}
                            style={{ height: "150px", objectFit: "contain" }}
                        />
                        <div className="card-body">
                            <h5 className="card-title">{teamName}</h5>
                        </div>
                    </>
                )}

                {/* Display action buttons for authorized users */}
                {user?.hasAnyAuthority("USER", "ADMIN") && (
                    <div className="card-footer text-body-secondary text-center">
                        {user?.hasAnyAuthority("USER") && (
                            <button className="btn btn-danger me-2" onClick={handleDelete}>
                                <i className="fa-solid fa-trash"></i> Delete
                            </button>
                        )}
                    </div>
                )}
            </div>
        </div>
    );
}
