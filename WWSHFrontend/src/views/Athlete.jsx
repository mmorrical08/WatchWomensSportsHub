import { Link } from "react-router-dom";

export default function Athlete() {
    return (
        <div className="container mt-4">
            <div className="text-center mb-5">
                <h1>Basketball</h1>
            </div>
            <div className="row">
                <div className="col-md-6 text-center mb-4">
                    <h2>WNBA</h2>
                    <Link to="/athlete/basketball/wnba/teams" >
                        <img
                            src="https://logos-marcas.com/wp-content/uploads/2021/11/WNBA-Emblema.png"
                            alt="WNBA logo"
                            className="img-fluid"
                            style={{ maxWidth: '200px' }}
                        />
                    </Link>
                </div>
                <div className="col-md-6 text-center">
                    <h2>College Basketball</h2>
                    <Link to="/athlete/basketball/college/teams">
                        <img
                            src="https://d2779tscntxxsw.cloudfront.net/64f1074e54302.png?width=1200&quality=80s"
                            alt="NCAA logo"
                            className="img-fluid"
                            style={{ maxWidth: '200px' }}
                        />
                    </Link>
                </div>
            </div>
        </div>
    );
}
