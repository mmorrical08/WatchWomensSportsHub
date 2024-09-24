import { find } from "./api";

const API_URL = 'http://localhost:8080/api/favorite';

export async function findAll() {
    return find(API_URL, "user", {user});
}