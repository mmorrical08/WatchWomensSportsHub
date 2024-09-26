const API_URL = 'http://localhost:8080';

function parseJwt(json) {
    console.log(json);

    // Ensure jwt_token exists before trying to process it
    if (!json.jwt_token) {
        throw new Error("JWT token is missing in the response");
    }

    // Store JWT in localStorage
    localStorage.setItem('jwt', json.jwt_token);

    const tokens = json.jwt_token.split(".");
    const payload = JSON.parse(atob(tokens[1]));
    console.log(payload);

    return {
        appUserId: payload.appUserId,
        username: payload.sub,
        authorities: payload.authorities,
        hasAnyAuthority: function (...authorities) {
            for (const authority of authorities) {
                if (this.authorities.includes(authority)) {
                    return true;
                }
            }
            return false;
        }
    };
}

export async function login(username, password) {
    const response = await fetch(`${API_URL}/authenticate`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    });

    if (response.ok) {
        const json = await response.json();
        return parseJwt(json);  // Parse the JWT if the response is successful
    } else {
        // Log the error details
        const errorText = await response.text();
        console.error("Login failed:", errorText);
        return Promise.reject("Authentication failed");
    }
}

export async function createAccount(username, password) {
    const response = await fetch(`${API_URL}/create-account`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password })
    });

    if (response.ok) {
        
        const user = await login(username, password);
        console.log(user);
        return user;

        // Check if the JWT is returned before parsing
        
    } else {
        // Log the error details
        const errorText = await response.text();
        console.error("Account creation failed:", errorText);
        return Promise.reject("Account creation failed");
    }
}

export async function refresh() {
    const response = await fetch(`${API_URL}/refresh-token`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    });

    if (response.ok) {
        const json = await response.json();

        try {
            return parseJwt(json);  // Parse the JWT if the response is successful
        } catch (err) {
            console.error("Error parsing JWT during refresh:", err.message);
            return Promise.reject("Error parsing refresh token response");
        }
    } else {
        const errorText = await response.text();
        console.error("Refresh token failed:", errorText);
        return Promise.reject("Authentication failed");
    }
}
