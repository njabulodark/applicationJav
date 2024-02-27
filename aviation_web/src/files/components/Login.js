import React from 'react'
import Nav from './Nav'
import Footer from './Footer'
import styled from 'styled-components'
import { useEffect, useState } from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'

const Section = styled.section`
.background-radial-gradient {
  background-color: hsl(218, 41%, 15%);
  background-image: radial-gradient(650px circle at 0% 0%,
      hsl(218, 41%, 35%) 15%,
      hsl(218, 41%, 30%) 35%,
      hsl(218, 41%, 20%) 75%,
      hsl(218, 41%, 19%) 80%,
      transparent 100%),
    radial-gradient(1250px circle at 100% 100%,
      hsl(218, 41%, 45%) 15%,
      hsl(218, 41%, 30%) 35%,
      hsl(218, 41%, 20%) 75%,
      hsl(218, 41%, 19%) 80%,
      transparent 100%);
}

#radius-shape-1 {
  height: 220px;
  width: 220px;
  top: -60px;
  left: -130px;
  background: radial-gradient(#44006b, #ad1fff);
  overflow: hidden;
}

#radius-shape-2 {
  border-radius: 38% 62% 63% 37% / 70% 33% 67% 30%;
  bottom: -60px;
  right: -110px;
  width: 300px;
  height: 300px;
  background: radial-gradient(#44006b, #ad1fff);
  overflow: hidden;
}

.bg-glass {
  background-color: hsla(0, 0%, 100%, 0.9) !important;
  backdrop-filter: saturate(200%) blur(25px);
}
`;


export default function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const [ errMessage, SetErrMessage] = useState('');
    const [ loginMessage, SetLoginMessage] = useState('');

    const submition = async (e) => {
        e.preventDefault();

        // const response = await axios.post("http://localhost:8081/api/login", {username, password});
        const response = await axios.post("http://172.174.153.102:8081/api/login", {username, password});
        const {userId, messag} = response.data;
        if (userId) {
            localStorage.setItem("userId", userId);
            SetLoginMessage("Logging in, your are being redirected to the home page.....");
            setTimeout(()=>{
              navigate("/account");
            }, 5000);
          } else if (messag) {
            SetErrMessage(messag);
        }
        // console.log(response.data);
        // if(response.data.userId) {
        //     localStorage.setItem("useId", response.data.userId);
        //     // navigate("/account");
        //     console.log('ID: ',localStorage.getItem("userId"));
        // }


    }

    useEffect(() => {
        document.title = "Login";
    }, []);

  return (
    <>
    <Nav />
        <header className="py-5 bg-dark" >
        </header>

<section style={{background: 'radial-gradient'}} className="background-radial-gradient overflow-hidden">

  <div className="container px-4 py-5 px-md-5 text-center text-lg-start my-5">
    <div className="row gx-lg-5 align-items-center mb-5">
      <div className="col-lg-6 mb-5 mb-lg-0" style={{zIndex: '10'}}>
        <h1 className="my-5 display-5 fw-bold ls-tight" style={{color: 'hsl(218, 81%, 95%)'}}>
          The best offer <br />
          <span style={{color: 'hsl(218, 81%, 75%)'}}>Aviation automation</span>
        </h1>
        <p className="mb-4 opacity-70" style={{color: 'hsl(218, 81%, 85%)'}}>
          Lorem ipsum dolor, sit amet consectetur adipisicing elit.
          Temporibus, expedita iusto veniam atque, magni tempora mollitia
          dolorum consequatur nulla, neque debitis eos reprehenderit quasi
          ab ipsum nisi dolorem modi. Quos?
        </p>
      </div>

      <div className="col-lg-6 mb-5 mb-lg-0 position-relative">
        <div id="radius-shape-1" className="position-absolute rounded-circle shadow-5-strong"></div>
        <div id="radius-shape-2" className="position-absolute shadow-5-strong"></div>

        <div className="card bg-glass">
          <div className="card-body px-4 py-5 px-md-5">
            <form>
              {errMessage && <div className="alert alert-danger" role="alert">{errMessage}</div>}
              {loginMessage && <div className="alert alert-success" role="alert">{loginMessage}</div>}
              <div className="row">
                <div className="form-outline mb-4">
                    <input type="text" id="username" className="form-control" value={username} onChange={(e)=>{ setUsername(e.target.value)}} required/>
                    <label className="form-label" htmlFor="username">Username</label>
                  </div>
                </div>

              <div className="form-outline mb-2">
                <input type="password" id="password" className="form-control" value={password} onChange={(e)=>{ setPassword(e.target.value)}} required/>
                <label className="form-label" htmlFor="password">Password</label>
              </div>

              <button type="submit" className="btn btn-primary btn-block" onClick={submition}>
                Login
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>

    <Footer />
    </>
  )
}
