import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/login/login";
import Dashboard from "./pages/Home/dashboard";
import Signout from './components/signout';
import Books from './pages/Books/books'; 
import Members from './pages/Members/member';
import Transactions from './pages/transactions/transactions';
import Issuedbooks from './pages/IssuedBooks/issuedbooks';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Addbook from './pages/Add/addbook';
import Addmember from './pages/Add/addmember';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route index element={<App />} />
        <Route path="login" element={<Login />} />
        <Route path="dashboard" element={<Dashboard />} />
        <Route path="signout" element={<Signout />} />
        <Route path="books" element={<Books />} />
        <Route path="members" element={<Members />} />
        <Route path="transactions" element={<Transactions />} />
        <Route path="issuedbooks" element={<Issuedbooks />} />
        <Route path="addbook" element={<Addbook />} />
        <Route path="addmember" element={<Addmember />} />
      </Routes>
    </BrowserRouter>
    <ToastContainer theme='dark' />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
