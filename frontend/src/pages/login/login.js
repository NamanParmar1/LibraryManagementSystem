import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import './login.css';
import Lottie from 'lottie-react';
import animation from '../../animations/login.json';
import Loginform from '../../components/Form/loginform';



export default function Login() {
  const navigate = useNavigate();
  const [authenticated, setauthenticated] = useState(localStorage.getItem("authenticated") ? true : false);

useEffect (()=>{
  if (authenticated) {
    navigate("/dashboard");
  }
},[authenticated])
  
  return (
    <div className="login-page">
      <div className='animation'>
        <Lottie animationData={animation} loop={true} />
      </div>
      <Loginform />

    </div>
  );

}
