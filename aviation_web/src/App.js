import logo from './logo.svg';
import './App.css';
import './files/components/Nav';
import Nav from './files/components/Nav';
import Footer from './files/components/Footer';
import { useNavigate } from 'react-router-dom';

function App() {
  const navigate = useNavigate("");
  return (
    <>
      <Nav />
      <div className="App">
      <div class="header p-5 text-center bg-image rounded-3" style={{
        backgroundImage: " linear-gradient(rgb(11, 5, 38, 0.8), rgba(0, 0, 0, 0.5)), url('https://mg.co.za/wp-content/uploads/2023/10/Hollywoodbets-Aviator.png')",
        // height: "400px"
        height: "100vh",
        // background: 'linear-gradient(rgb(11, 5, 38, 0.7), rgba(0, 0, 0, 0.2))'
      }}>
        <div class="mask" style={{
          // backgroundColor: "rgba(76, 9, 128, 0.6)"
        }}>
          <div class="d-flex justify-content-center align-items-center h-100" >
            <div class="text-white">
              <h1 class="mb-3 fw-bolder" style={{color: 'rgba(76, 9, 128)', textShadow: '2px 5px 9px yellow', fontSize: 'calc(35px + 2vmin)'}}>Hollywoodbets Aviation Bot</h1>
              <a class="btn btn-outline-light btn-lg" href="/account" role="button">Go bet</a>
            </div>
          </div>
        </div>
      </div>
        
      </div>
      {/* <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <p>
            Edit <code>src/App.js</code> and save to reload.
          </p>
          <a
            className="App-link"
            href="https://reactjs.org"
            target="_blank"
            rel="noopener noreferrer"
            >
            Learn React
          </a>
        </header>
      </div> */}
      <Footer />
    </>
  );
}

export default App;
