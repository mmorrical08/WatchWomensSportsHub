import React from 'react';
import { Link } from 'react-router-dom';

export default function TeamsCard({ team, league }) {
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
                    <button className="heart-button" >
                        <i className="fa-solid fa-heart"></i>
                    </button>
                </div>
            </div>
        </div>
    );
}

