import { Link } from 'react-router-dom';

export default function NotFound() {
    return (
        <>
            <h1>Error: Not Found</h1>
            <div className='sign-in-button'>
              <Link className="btn button" to= "/">
                Return to Home
                </Link>
            </div>
            <img src="https://i.dailymail.co.uk/1s/2024/05/15/02/84879881-13418865-image-a-9_1715736839953.jpg" alt="404 Not Found" />
        </>

    );
}