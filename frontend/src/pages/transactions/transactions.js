import React, {useState,useEffect} from 'react'

import Sidebar from '../../components/SideBar/sidebar';
import SearchTransactions from '../../components/Search/searchtransactions';

import { useLocation,useNavigate } from 'react-router-dom';



const Transactions = () => {
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
            <SearchTransactions/>
        </div >
        
        
    </div>
  )
}

export default Transactions;