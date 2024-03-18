import React from 'react';
import DashboardIcon from "@mui/icons-material/Dashboard";
import PersonOutlineIcon from "@mui/icons-material/PersonOutline";
import CreditCardIcon from "@mui/icons-material/CreditCard";

import ExitToAppIcon from "@mui/icons-material/ExitToApp";
import AddchartIcon from '@mui/icons-material/Addchart';

import LibraryBooksIcon from '@mui/icons-material/LibraryBooks';
import AutoStoriesIcon from '@mui/icons-material/AutoStories';
import { Link, useLocation } from 'react-router-dom';
import AddReactionIcon from '@mui/icons-material/AddReaction';
import './sidebar.css';
import { useNavigate } from 'react-router-dom';



const Sidebar = () => {
  const location = useLocation();

  const navigate = useNavigate();

  const isActiveRoute = (routePath) => {
    return location.pathname === routePath;
  };

  const handleSignOut = (e) => {
    e.preventDefault();
    window.localStorage.removeItem("authenticated");
    navigate("/login");
  };
  return (

    <div className="sidebar">
      <div className="top">
        <Link to="/dashboard" style={{ textDecoration: "none" }}>
          <span className="logo">Library</span>
          {/* <Lottie animationData={animation} loop={true} /> */}
        </Link>
      </div>
      
      <div className="center">
        <ul>
          <p className="title">MAIN</p>
          <Link to="/dashboard" style={{ textDecoration: "none" }}>
          <li className={isActiveRoute('/dashboard') ? 'active' : ''}>
            <DashboardIcon className="icon" />
            <span className={isActiveRoute("/dashboard") ? 'active' : ''}>Dashboard</span>
          </li>
          </Link>

          <p className="title">ADD</p>
          <Link to="/addbook" style={{ textDecoration: "none" }}>
          <li className={isActiveRoute("/addbook") ? 'active' : ''}>
            <AddchartIcon className="icon" />
            <span className={isActiveRoute("/addbook") ? 'active' : ''}>Add Book</span>
          </li>
          </Link>
          <Link to="/addmember" style={{ textDecoration: "none" }}>
          <li className={isActiveRoute("/addmember") ? 'active' : ''}>
            <AddReactionIcon className="icon" />
            <span className={isActiveRoute("/addmember") ? 'active' : ''}>Add Member</span>
          </li>
          </Link>
          <p className="title">LISTS</p>
           <Link to="/members" style={{ textDecoration: "none" }}> 
           <li className={isActiveRoute("/members") ? 'active' : ''}>
              <PersonOutlineIcon className="icon" />
              <span className={isActiveRoute("/members") ? 'active' : ''}>Members</span>
            </li>
          </Link> 
          <Link to="/books" style={{ textDecoration: "none" }}>
          <li className={isActiveRoute("/books") ? 'active' : ''}>
              <LibraryBooksIcon className="icon" />
              <span className={isActiveRoute("/books") ? 'active' : ''}>Books</span>
            </li>
          </Link>
          <Link to="/issuedbooks" style={{ textDecoration: "none" }}>
          <li className={isActiveRoute("/issuedbooks") ? 'active' : ''}>
            <AutoStoriesIcon className="icon" />
            <span className={isActiveRoute("/issuedbooks") ? 'active' : ''}>Issued Books</span>
          </li>
          </Link>
          <Link to="/transactions" style={{ textDecoration: "none" }}>
          <li className={isActiveRoute("/transactions") ? 'active' : ''}>
            <CreditCardIcon className="icon" />
            <span className={isActiveRoute("/transactions") ? 'active' : ''}>Transactions</span>
          </li>
          </Link>
          
         
          
          <p className="title">USER</p>
          
          <li onClick={handleSignOut}>
            <ExitToAppIcon className="icon"  />
            <span className={isActiveRoute("/members") ? 'active' : ''}>Logout</span>
          </li>
        </ul>
      </div>
      
    </div>
  );
};

export default Sidebar;
