import logo from './logo.svg';
import './App.css';
import './files/components/Nav';
import Nav from './files/components/Nav';
import Footer from './files/components/Footer';

function App() {
  return (
    <>
      <Nav />
      <div className="App">
      <div class="header p-5 text-center bg-image rounded-3" style={{
        backgroundImage: "url('https://mg.co.za/wp-content/uploads/2023/10/Hollywoodbets-Aviator.png')",
        // height: "400px"
        height: "100vh"
      }}>
        <div class="mask" style={{
          backgroundColor: "rgba(0, 0, 0, 0.6)"
        }}>
          <div class="d-flex justify-content-center align-items-center h-100">
            <div class="text-white">
              <h1 class="mb-3">Heading</h1>
              <h4 class="mb-3">Subheading</h4>
              <a class="btn btn-outline-light btn-lg" href="#!" role="button">Call to action</a>
            </div>
          </div>
        </div>
      </div>
        <header className="header" style={{backgroundImage: "url('https://mg.co.za/wp-content/uploads/2023/10/Hollywoodbets-Aviator.png')"}}>
          {/* <img src="https://mg.co.za/wp-content/uploads/2023/10/Hollywoodbets-Aviator.png" className="App-logo" alt="logo" />
          <p>
            Edit <code>src/App.js</code> and save to reload.
          </p>
          <a
            className="App-link"
            href="https://mg.co.za/wp-content/uploads/2023/10/Hollywoodbets-Aviator.png"
            target="_blank"
            rel="noopener noreferrer"
            >
            Learn React
          </a> */}
        </header>
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
