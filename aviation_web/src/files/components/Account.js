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

@media only screen and (max-width: 576px) {
    .odds.row.my-3 {
      height: 200px!;
      overflow-y: scroll !important;
    }
}

@media only screen and (max-width: 768px) {
    .odds.row.my-3 {
        height: 200px !important;
        overflow-y: scroll !important;
    }
  }
  
  

`;



export default function Account() {
    const navigate = useNavigate();
    const [ username, setUsername] = useState('');
    const [ password, setPassword] = useState('');
    const [ cashout, setCashout] = useState('');
    const [ x_amount, setXAmount] = useState('');
    const [ userMessage, setUserMessage] = useState('');
    const [ nextSet, setNextSet] = useState(false);
    const [ odds, setOdds] = useState("loading");
    const [ betting, setBetting] = useState(false);
    const [ balance, setBalance] = useState(0)

    
    setTimeout(async () => {
        // await axios.post("http://localhost:8081/api/balance", {"id": localStorage.getItem("userId")})
        await axios.post("http://172.174.153.102:8081/api/balance", {"id": localStorage.getItem("userId")})
        .then((response) => {
            if (response.data.userResult) {
                setBalance(response.data.userResult[0].balance);
                console.log("balance", response.data.userResult[0].balance);
            }
        });
    }, 5000);
    

    const subUserName = async (e) => {
        e.preventDefault();
        // const response = await axios.post("http://localhost:8081/api/keys", {"av_username": username, "id": localStorage.getItem("userId")});
        const response = await axios.post("http://172.174.153.102:8081/api/keys", {"av_username": username, "id": localStorage.getItem("userId")});
        // console.log(response.status);
        if (response.status === 200) {
            setUserMessage("Username saved");
        }
    }

    const subPass = async (e) => {
        e.preventDefault();
        // const response = await axios.post("http://localhost:8081/api/keys", {"av_password": password, "id": localStorage.getItem("userId")});
        const response = await axios.post("http://172.174.153.102:8081/api/keys", {"av_password": password, "id": localStorage.getItem("userId")});
        // console.log(response.status);
        if (response.status === 200) {
            setUserMessage("Password saved");
        }
    }
    
    const subCashout = async (e) => {
        e.preventDefault();
        // const response = await axios.post("http://localhost:8081/api/keys", {"bet_amount": cashout, "id": localStorage.getItem("userId")});
        const response = await axios.post("http://172.174.153.102:8081/api/keys", {"bet_amount": cashout, "id": localStorage.getItem("userId")});
        // console.log(response.status);
        if (response.status === 200) {
            setUserMessage("Bet amount saved");
        }
    }

    const subXAmount = async (e) => {
        e.preventDefault();
        // const response = await axios.post("http://localhost:8081/api/keys", {"x_amount": x_amount, "id": localStorage.getItem("userId")});
        const response = await axios.post("http://172.174.153.102:8081/api/keys", {"x_amount": x_amount, "id": localStorage.getItem("userId")});
        // console.log(response.status);
        if (response.status === 200) {
            setUserMessage("Cashout amount saved");
        }
    }
    
    const submition = async (e) => {
        e.preventDefault();
        // const response = await axios.get("http://localhost:8081/api/terminal");
        const response = await axios.get("http://172.174.153.102:8081/api/terminal");

        // wait 15 seconds
        setTimeout(() => {
            setBetting(true);
            getOdds();
        }, 15000);
    }

    const getOdds = async () => {
        // while (true) {
            // await axios.get("http://localhost:8081/api/getOdds")
            await axios.get("http://172.174.153.102:8081/api/getOdds")
                .then((response) => {
                    if (response.data.success) {
                        // turn tring  {} to jeson
                        const oddss = JSON.parse(response.data.userResult[0].oddsList);
                        console.log("Reverse odds: ",oddss.bets.reverse());
                        // reverse the array
                        oddss.bets.reverse();
                        setOdds(oddss.bets.reverse());

                    }
                });
            // }   
    }

    setTimeout(() => {
        
        getOdds();
    }, 5000);

    useEffect(() => {
        document.title = "Account";

        const getAccount = async () => {
            // await axios.post("http://localhost:8081/api/account", {"id": localStorage.getItem("userId")})
            await axios.post("http://172.174.153.102:8081/api/account", {"id": localStorage.getItem("userId")})
            .then((response) => {
                // console.log(response.data[0]);
                if (response.data[0]) {

                    const { av_username, av_password, bet_amount, x_amount } = response.data[0];
                    if (av_username !== null) {
                        setUsername( av_username );
                    }
                    
                    if (av_password !== null) {
                        setPassword( av_password );
                    }
                    
                    if (bet_amount !== null) {
                        setCashout( bet_amount );
                    }
                    
                    if (x_amount !== null) {
                        setXAmount( x_amount );
                    }
                }
            });
        } 

        getAccount();

        // getOdds();
    }, []);

  return (
    <>
    <Nav />
        <header className="py-5 bg-dark" >
        </header>

        <Section className="background-radial-gradient overflow-hidden mt-4">

        <div className="container px-4 py-5 px-md-5 text-center text-lg-start my-5">
            <div className="row gx-lg-5 align-items-center mb-5">
            {!betting && <div className="col-lg-6 mb-5 mb-lg-0" style={{zIndex: '10'}}>
                <h1 className="my-5 display-5 fw-bold ls-tight" style={{color: 'hsl(218, 81%, 95%)'}}>
                The best bot<br />
                <span style={{color: 'hsl(218, 81%, 75%)'}}>Aviation automation</span>
                </h1>
                <p className="mb-4 opacity-70" style={{color: 'hsl(218, 81%, 85%)'}}>
                Put in your hollywoodbets account bot username and password
                </p>
            </div>}
            

            <div className="col-lg-6 mb-5 mb-lg-0 position-relative">
                <div id="radius-shape-1" className="position-absolute rounded-circle shadow-5-strong"></div>
                <div id="radius-shape-2" className="position-absolute shadow-5-strong"></div>

                {/*  When user is not logged in  */}
                {!localStorage.getItem("userId") && <div className="card bg-glass">
                <div className="card-body px-4 py-5 px-md-5">
                    <h2 className='m-3 col-auto text-md-center'> Your hollywood Account</h2>
                    <hr />
                    <form>

                    <div className="form-row">
                        <div className="alert alert-danger" role="alert">Please login to have Access to this page</div>
                        <div className="form-outline mb-4">
                            <label className="form-label px-3" htmlFor="username">Aviation Username: </label>
                            <input type="text" id="username"  className='sr-only pb-2 mb-2' readOnly/>
                        </div>
                    </div>

                    <div className="form-outline mb-2">
                        <label className="form-label px-3" htmlFor="password">Aviation Password: </label>
                        <input type="password" id="password" className="sr-only"  readOnly/>
                    </div>

                    <button type="submit" className="btn btn-primary btn-block" onClick={(e) => {setNextSet(true);}}>
                    Next
                    </button>
                    </form>
                </div>
                </div>}

                {/*  When user is logged in  */}
                {!nextSet && localStorage.getItem("userId") && <div className="card bg-glass">
                <div className="card-body px-4 py-5 px-md-5">
                    <h2 className='m-3 col-auto text-md-center'> Your hollywood Account</h2>
                    <hr />
                    <form>

                    <div className="form-row">
                        {userMessage && <div className="alert alert-success" role="alert">{userMessage}</div>}
                        <div className="form-outline mb-4">
                            <label className="form-label px-3" htmlFor="username">Aviation Username: </label>
                            <input type="text" id="username"  className='sr-only pb-2 mb-2' value={username} onChange={(e)=>{ setUsername(e.target.value)}} required/>
                            <button type="submit" className="btn btn-primary px-1 py-1 mx-2 btn-sm btn-block" onClick={subUserName}>
                                save
                            </button>
                        </div>
                    </div>

                    <div className="form-outline mb-2">
                        <label className="form-label px-3" htmlFor="password">Aviation Password: </label>
                        <input type="password" id="password" className="sr-only"  value={password} onChange={(e)=>{ setPassword(e.target.value)}} required/>
                        <button type="submit" className="btn btn-primary px-1 py-1 mx-2 btn-sm btn-block" onClick={subPass}>
                            save
                        </button>
                    </div>

                    <button type="submit" className="btn btn-primary btn-block" onClick={(e) => {setNextSet(true);}}>
                    Next
                    </button>
                    </form>
                </div>
                </div>}

                {nextSet && localStorage.getItem("userId") && <div className="card bg-glass">
                <div className="card-body px-4 py-5 px-md-5">
                    <h2 className='m-3 col-auto text-md-center'> Betting Setting1</h2>
                    <hr />
                    <form>

                    {betting && <div className="form-row">
                        {userMessage && <div className="alert alert-success" role="alert">{userMessage}</div>}
                        <div className="form-outline mb-4">
                            <label className="form-label px-3" htmlFor="bet">Bet Amount: </label>
                            <input type="text" id="bet"  className='sr-only pb-2 mb-2' value={cashout} onChange={(e)=>{ setCashout(e.target.value)}} required/>
                            <button type="submit" className="btn btn-primary px-1 py-1 mx-2 btn-sm btn-block" onClick={subCashout}>
                                save
                            </button>
                        </div>
                    </div>}

                    <div className="form-outline mb-2">
                        <label className="form-label px-3" htmlFor="cashout">Cashout Amount: </label>
                        <input type="text" id="cashout" className="sr-only"  value={x_amount} onChange={(e)=>{ setXAmount(e.target.value)}} required/>
                        <button type="submit" className="btn btn-primary px-1 py-1 mx-2 btn-sm btn-block" onClick={subXAmount}>
                            save
                        </button>
                    </div>
                    <div className="col-md-6 mx-auto text-center">
                        <button type="submit" className="btn btn-primary btn-block text-lg mx-auto" onClick={submition}>
                        Place Bet
                        </button>
                    </div>
                    </form>

                    {betting &&  <div className='container'>
                        <div className='betting-odds'>
                        <h2 className='m-3 col-auto text-md-center fw-bold fs-1 ' style={{color: 'blue', textShadow: '2px 2px 5px black'}}>Betting Odds</h2>
                            <hr />
                        </div>
                        <div className="odds row my-3">
                            <ul className="list-group list-group-horizontal-sm" style={{overflowX: 'scroll', overflowY: 'hidden'}}>
                                {(odds !== "loading")? 
                                    (odds.map((odd, index) => {
                                        const coloring = parseFloat(odd) > 2 ? parseFloat(odd) < 10 ? '#643e94': 'rgb(192, 23, 180)' : 'rgb(52, 180, 255)';
                                        return <li className="list-group-item fw-bold text-mx-centre" style={{color: coloring, backgroundColor: '#000000', borderRadius: '10px', margin: '2px'}} key={index}> {odd} </li>
                                    })): 
                                    (   <div className="spinner-border" style={{width: '3rem', height: '3rem', margin: 'auto'}} role="status">
                                            <span className="sr-only"></span>
                                        </div>
                                    )
                                }
                            </ul>
                        </div>
                        <div className='m-2 col-auto text-bg-centre' style={{textAlign: 'right', fontSize: '40px', fontStyle: 'oblique'}}>
                            <b>Balance: </b>{balance}
                        </div>
                    </div>}

                    </div>
                    {/* {betting &&  <div className='container'>
                        <div className='betting-odds'>
                        <h2 className='m-3 col-auto text-md-center fw-bold fs-1 ' style={{color: 'blue', textShadow: '2px 2px 5px black'}}>Betting Odds</h2>
                            <hr />
                        </div>
                        <div className="row my-3">
                            <ul className="list-group list-group-horizontal-sm" style={{overflowX: 'scroll'}}>
                                {
                                    odds.map((odd, index) => {
                                        const coloring = parseFloat(odd) > 2 ? parseFloat(odd) < 10 ? '#643e94': 'rgb(192, 23, 180)' : 'rgb(52, 180, 255)';
                                        return <li className="list-group-item fw-bold text-mx-centre" style={{color: coloring, backgroundColor: '#000000', borderRadius: '10px', margin: '2px'}} key={index}> {odd} </li>
                                    })
                                }
                            </ul>
                        </div>
                    </div>} */}
                </div>}

            </div>
            
            <div className="col-lg-6 mb-5 mb-lg-0 position-relative">
                <div id="radius-shape-1" className="position-absolute rounded-circle shadow-5-strong"></div>
                <div id="radius-shape-2" className="position-absolute shadow-5-strong"></div>

                {/*  When user is not logged in  */}
                {!localStorage.getItem("userId") && <div className="card bg-glass">
                <div className="card-body px-4 py-5 px-md-5">
                    <h2 className='m-3 col-auto text-md-center'> Your hollywood Account</h2>
                    <hr />
                    <form>

                    <div className="form-row">
                        <div className="alert alert-danger" role="alert">Please login to have Access to this page</div>
                        <div className="form-outline mb-4">
                            <label className="form-label px-3" htmlFor="username">Aviation Username: </label>
                            <input type="text" id="username"  className='sr-only pb-2 mb-2' readOnly/>
                        </div>
                    </div>

                    <div className="form-outline mb-2">
                        <label className="form-label px-3" htmlFor="password">Aviation Password: </label>
                        <input type="password" id="password" className="sr-only"  readOnly/>
                    </div>

                    <button type="submit" className="btn btn-primary btn-block" onClick={(e) => {setNextSet(true);}}>
                    Next
                    </button>
                    </form>
                </div>
                </div>}

                {/*  When user is logged in  */}
                {!nextSet && localStorage.getItem("userId") && <div className="card bg-glass">
                <div className="card-body px-4 py-5 px-md-5">
                    <h2 className='m-3 col-auto text-md-center'> Your hollywood Account</h2>
                    <hr />
                    <form>

                    <div className="form-row">
                        {userMessage && <div className="alert alert-success" role="alert">{userMessage}</div>}
                        <div className="form-outline mb-4">
                            <label className="form-label px-3" htmlFor="username">Aviation Username: </label>
                            <input type="text" id="username"  className='sr-only pb-2 mb-2' value={username} onChange={(e)=>{ setUsername(e.target.value)}} required/>
                            <button type="submit" className="btn btn-primary px-1 py-1 mx-2 btn-sm btn-block" onClick={subUserName}>
                                save
                            </button>
                        </div>
                    </div>

                    <div className="form-outline mb-2">
                        <label className="form-label px-3" htmlFor="password">Aviation Password: </label>
                        <input type="password" id="password" className="sr-only"  value={password} onChange={(e)=>{ setPassword(e.target.value)}} required/>
                        <button type="submit" className="btn btn-primary px-1 py-1 mx-2 btn-sm btn-block" onClick={subPass}>
                            save
                        </button>
                    </div>

                    <button type="submit" className="btn btn-primary btn-block" onClick={(e) => {setNextSet(true);}}>
                    Next
                    </button>
                    </form>
                </div>
                </div>}

                {nextSet && localStorage.getItem("userId") && <div className="card bg-glass">
                <div className="card-body px-4 py-5 px-md-5">
                    <h2 className='m-3 col-auto text-md-center'> Betting Setting</h2>
                    <hr />
                    <form>

                    <div className="form-row">
                        {userMessage && <div className="alert alert-success" role="alert">{userMessage}</div>}
                        <div className="form-outline mb-4">
                            <label className="form-label px-3" htmlFor="bet">Bet Amount: </label>
                            <input type="text" id="bet"  className='sr-only pb-2 mb-2' value={cashout} onChange={(e)=>{ setCashout(e.target.value)}} required/>
                            <button type="submit" className="btn btn-primary px-1 py-1 mx-2 btn-sm btn-block" onClick={subCashout}>
                                save
                            </button>
                        </div>
                    </div>

                    <div className="form-outline mb-2">
                        <label className="form-label px-3" htmlFor="cashout">Cashout Amount: </label>
                        <input type="text" id="cashout" className="sr-only"  value={x_amount} onChange={(e)=>{ setXAmount(e.target.value)}} required/>
                        <button type="submit" className="btn btn-primary px-1 py-1 mx-2 btn-sm btn-block" onClick={subXAmount}>
                            save
                        </button>
                    </div>
                    <div className="col-md-6 mx-auto text-center">
                        <button type="submit" className="btn btn-primary btn-block text-lg mx-auto" onClick={submition}>
                        Place Bet
                        </button>
                    </div>
                    </form>

                    {betting &&  <div className='container'>
                        <div className='betting-odds'>
                        <h2 className='m-3 col-auto text-md-center fw-bold fs-1 ' style={{color: 'blue', textShadow: '2px 2px 5px black'}}>Betting Odds</h2>
                            <hr />
                        </div>
                        <div className="odds row my-3">
                            <ul className="list-group list-group-horizontal-sm" style={{overflowX: 'scroll', overflowY: 'hidden'}}>
                                {(odds !== "loading")? 
                                    (odds.map((odd, index) => {
                                        const coloring = parseFloat(odd) > 2 ? parseFloat(odd) < 10 ? '#643e94': 'rgb(192, 23, 180)' : 'rgb(52, 180, 255)';
                                        return <li className="list-group-item fw-bold text-mx-centre" style={{color: coloring, backgroundColor: '#000000', borderRadius: '10px', margin: '2px'}} key={index}> {odd} </li>
                                    })): 
                                    (   <div className="spinner-border" style={{width: '3rem', height: '3rem', margin: 'auto'}} role="status">
                                            <span className="sr-only"></span>
                                        </div>
                                    )
                                }
                            </ul>
                        </div>
                        <div className='m-2 col-auto text-bg-centre' style={{textAlign: 'right', fontSize: '40px', fontStyle: 'oblique'}}>
                            <b>Balance: </b>{balance}
                        </div>
                    </div>}

                    </div>
                    {/* {betting &&  <div className='container'>
                        <div className='betting-odds'>
                        <h2 className='m-3 col-auto text-md-center fw-bold fs-1 ' style={{color: 'blue', textShadow: '2px 2px 5px black'}}>Betting Odds</h2>
                            <hr />
                        </div>
                        <div className="row my-3">
                            <ul className="list-group list-group-horizontal-sm" style={{overflowX: 'scroll'}}>
                                {
                                    odds.map((odd, index) => {
                                        const coloring = parseFloat(odd) > 2 ? parseFloat(odd) < 10 ? '#643e94': 'rgb(192, 23, 180)' : 'rgb(52, 180, 255)';
                                        return <li className="list-group-item fw-bold text-mx-centre" style={{color: coloring, backgroundColor: '#000000', borderRadius: '10px', margin: '2px'}} key={index}> {odd} </li>
                                    })
                                }
                            </ul>
                        </div>
                    </div>} */}
                </div>}

            </div>
            
            </div>
        </div>
        </Section>

    <Footer />
    </>
  )
}
