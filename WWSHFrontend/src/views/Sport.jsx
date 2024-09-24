import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { findAll } from "../api/sportApi";
import { AuthContext } from "../AppProvider";
import Card from "../components/Card";

export default function Sport() {
    return (
        <div className="container">
            <h1 className="text-center mt-4">Sports</h1>
            <div>
                <div>
                    <img src="https://img.freepik.com/premium-vector/basketball-orange-gradient_78370-2680.jpg" alt="WNBA logo" className="img-fluid" style={{ maxWidth: '200px' }} />
                </div>
                <Link to="/sport/basketball" className="btn btn-primary mt-4">Basketball</Link>
            </div>
        </div>
    )
}