import { find } from "./api";

const API_URL = 'http://localhost:8080/api/team';

export async function findAll() {
    return find(API_URL);
}
