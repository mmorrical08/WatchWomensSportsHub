import { Link } from 'react-router-dom';

export default function Home() {
    return (
        <>
          <div>
            <a>
              <img src="https://media.istockphoto.com/vectors/color-sport-background-football-basketball-hockey-box-golf-tennis-vector-id640207442?k=20&m=640207442&s=612x612&w=0&h=qjRFQPVNyVGZsn42WtLZ0B83lQ4oRI5g944Tli-crFY="
                className="logo" alt="Watch Women's Sports logo" />
            </a>
            {/* <a href="https://react.dev" target="_blank">
              <img src={reactLogo} className="logo react" alt="React logo" />
            </a> */}
          </div>
          <div className='home'>
            <h1>Watch Women's Sports Hub</h1>
            <h2>
              Welcome to the only place where you can find when <span className="bold-italic">ALL</span> your favorite women's sports are happening.
            </h2>
            <p>
              Sign in to add your favorite teams and athletes to your personal hub.
            </p>
          </div>
            <div className='sign-in-button'>
              <Link className="btn button" to= "/authenticate">
                Sign In
                </Link>
            </div>
        </>
      )
}