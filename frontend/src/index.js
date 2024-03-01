import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider, Route } from 'react-router-dom';
import Login from './files/components/Login';
import Bet from './files/components/Bet';
import Account from './files/components/Account';

const router = createBrowserRouter([
  {
  path: "/",
  element: <App />,
  },
  {
  path: "/bet",
  element: <Bet />,
  },
  {
  path: "/account",
  element: <Account />,
  },
  {
    path: "/login",
    element: <Login />,
  }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <RouterProvider router={router} />
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
