import Login from '../pages/login/login';
import { useNavigate } from 'react-router-dom';


export default function Signout() {
    const navigate = useNavigate();
    window.localStorage.removeItem("authenticated");
    navigate("/login");
  return (
    // <Login />
    <><div></div></>
  );
}
