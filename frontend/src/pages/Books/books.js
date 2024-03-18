import React,{useEffect,useState} from 'react'
import SearchBooks from '../../components/Search/searchbook';
import Sidebar from '../../components/SideBar/sidebar';
import { useNavigate } from 'react-router-dom';


const Books = () => {
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
            <SearchBooks/>
        </div >
        
        
    </div>
  )
}

export default Books;