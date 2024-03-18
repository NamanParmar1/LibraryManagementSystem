import React from 'react';
import Sidebar from '../../components/SideBar/sidebar';
import SearchIssuedBooks from '../../components/Search/searchissuebooks';

import { useNavigate } from 'react-router-dom';

import { useState,useEffect } from 'react';



const Issuedbooks = () => {
  const navigate = useNavigate();
  const [authenticated, setAuthenticated] = useState(localStorage.getItem("authenticated") ? true : false);
  
  useEffect(() => {
    if (!authenticated) {
      navigate("/login");
    }
  }, [authenticated, navigate]);


  return (
    <div className='home'>
        <Sidebar/>
        <div className='container'>
            <SearchIssuedBooks/>
        </div >
        
        
    </div>
  )
}

export default Issuedbooks;