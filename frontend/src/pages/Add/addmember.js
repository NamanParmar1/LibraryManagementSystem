import React from 'react'
import { useState,useEffect } from 'react';
import Sidebar from '../../components/SideBar/sidebar';
import AddMember from '../../components/Add/addbook';
import { useNavigate } from 'react-router-dom';

const Addmember = () => {

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
            <AddMember/>
        </div >
        
        
    </div>
  )
}

export default Addmember;