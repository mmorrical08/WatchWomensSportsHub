import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { findAll } from "../api/teamApi";
import { AuthContext } from "../AppProvider";
import Card from "../components/Card";
import Wnba from "./basketball/Wnba";
import CollegeBasketball from "./basketball/CollegeBasektball";
import Basketball from "./basketball/Basketball";

export default function Team() {
    return (
        <div>
            <h1>Teams</h1>
            <div>
                <Basketball />
            </div>
        </div>
    );
}