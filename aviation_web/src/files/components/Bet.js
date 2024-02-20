import React from 'react'
import Nav from './Nav'
import Footer from './Footer'
import styled from 'styled-components'
import { useEffect, useState } from 'react'
import axios from 'axios'

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


export default function Bet() {
    const [av_username, setUsername] = useState('');
    const [av_password, setPassword] = useState('');

    const subUserName = async (e) => {
        e.preventDefault();
        console.log(av_username, av_password);

        const response = await axios.post("http://localhost:8081/api/keys", {av_username, "id": localStorage.getItem("userId")});
        // response;
        console.log(response.status);

    }

    const subPass = async (e) => {
        e.preventDefault();
        console.log(av_username, av_password);

        const response = await axios.post("http://localhost:8081/api/keys", {av_password, "id": localStorage.getItem("userId")});
        console.log(response.status);

    }


    useEffect(() => {
        document.title = "Bet";

        const getAccount = () =>{

        } 
    }, []);

  return (
    <>
    <Nav />
        <header className="py-5 bg-dark" >
        </header>

<section className="background-radial-gradient overflow-hidden">

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
            <h2 className='m-3 col-auto text-md-center'> Your hollywood Account</h2>
            <hr />
            <form>

            <div className="form-row">
                <div className="form-outline mb-4">
                    <label className="form-label px-3" htmlFor="username">Bet Amount: </label>
                    <input type="text" id="username"  className='sr-only pb-2 mb-2' value={av_username} onChange={(e)=>{ setUsername(e.target.value)}} required/>
                    <button type="submit" className="btn btn-primary px-1 py-1 mx-2 btn-sm btn-block" onClick={subUserName}>
                        save
                    </button>
                </div>
            </div>

            

              <div className="form-outline mb-2">
                <label className="form-label px-3" htmlFor="password">Cashout Number: </label>
                <input type="password" id="password" className="sr-only" value={av_password} onChange={(e)=>{ setPassword(e.target.value)}} required/>
                <button type="submit" className="btn btn-primary px-1 py-1 mx-2 btn-sm btn-block" onClick={subPass}>
                        save
                    </button>
              </div>

              {/* <button type="submit" className="btn btn-primary btn-block" onClick={submition}>
                Login
              </button> */}
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
