export async function find(url, key, value) {
    if (key) {
        url += `?${key}=${value}`;
    }
    const response = await fetch(url);
    if (response.ok) {
        return response.json();
    }
    return Promise.reject("Fetch failed.");
}