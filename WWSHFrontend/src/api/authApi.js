const API_URL = 'http://localhost:8080';

function parseJwt(json) {
    console.log(json);

    localStorage.setItem('jwt', json.jwt_token);
    const tokens = json.jwt_token.split(".");
    const payload = JSON.parse(atob(tokens[1]));
    console.log(payload);
    return {
        appUserId: payload.app_user_id,
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

        return parseJwt(json);
    }

    return Promise.reject("Authentication failed");
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
        const json = await response.json();
        return parseJwt(json);
    }

    return Promise.reject("Account creation failed");
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
        return parseJwt(json);
    }
    
    return Promise.reject("Authentication failed");
}