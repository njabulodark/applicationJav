import React from 'react';
import '../css/styles.css';
import {useNavigate, Link} from 'react-router-dom';


export default function Nav() {
  return (
    <>
    <nav  className="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
        <div className="container px-4 px-lg-5">
            <a className="navbar-brand" href="/">Aviation Bot</a>
            <button className="navbar-toggler navbar-toggler-right" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                Menu
                <i className="fas fa-bars"></i>
            </button>
            <div className="collapse navbar-collapse" id="navbarResponsive">
                <ul className="navbar-nav ms-auto">
                    <li className="nav-item"><Link className="nav-link" to="/" >Home</Link></li>
                    <li className="nav-item"><a className="nav-link" href="/account">Account</a></li>
                    {!localStorage.getItem("userId") && < li className="nav-item"><a className="nav-link" href="/login">Login</a></li>}
                </ul>
            </div>
        </div>
    </nav>
    </>
  )
}
