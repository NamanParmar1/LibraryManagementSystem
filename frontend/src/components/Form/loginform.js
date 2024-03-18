import React, { useEffect, useState } from "react";
import './loginform.css'
import { useNavigate } from "react-router-dom";

export default function Loginform() {

    const navigate = useNavigate();
    const [popupStyle, showPopup] = useState("hide");
    const [authenticated, setauthenticated] = useState(localStorage.getItem("authenticated") ? true : false);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const popup = () => {
        showPopup("login-popup")
        setTimeout(() => showPopup("hide"), 3000)
    }

    const handleLogin = (e) => {
        e.preventDefault();
        // Perform login authentication logic here
        const savedusername = "naman";
        const savedpassword = "password";

        if (username === savedusername && password === savedpassword) {
           // alert("Login successful");
            //setauthenticated(true)
            localStorage.setItem("authenticated", true);
            navigate("/dashboard");
        } else {
            //alert("Login failed");
            popup(); // Show the login failure popup
        }
    };
    

    return (
        <div className="cover">
            <h1>Login</h1>
            <input
                type="text"
                placeholder="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />

            <input
                type="password"
                placeholder="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}

            />

            <div className="login-btn" onClick={handleLogin}>Login</div>

            <p className="text">Or login using</p>

            <div className="alt-login">
                <div className="google"></div>
                <div className="facebook"></div>

            </div>

            <div className={popupStyle}>
                <h3>Login Failed</h3>
                <p>Username or password incorrect</p>
            </div>

        </div>
    )
}

