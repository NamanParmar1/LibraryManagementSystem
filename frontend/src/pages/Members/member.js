import React, {useState,useEffect} from 'react'
import Sidebar from '../../components/SideBar/sidebar';
import SearchMembers from '../../components/Search/searchmember';
import { useLocation,useNavigate } from 'react-router-dom';

const Members = () => {
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
            <SearchMembers/>
        </div >
        
        
    </div>
  )
}

export default Members;