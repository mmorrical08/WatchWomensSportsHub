
import './App.css'
import './components/nav.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import Nav from './components/Nav'
import { Outlet } from 'react-router-dom'

function App() {


  return (
    <>
      <Nav />
      <main className="container">
        <Outlet />
      </main>
    </>
  )
}

export default App