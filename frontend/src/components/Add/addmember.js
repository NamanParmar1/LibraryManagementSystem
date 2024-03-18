import React, { useState,useEffect } from 'react'
import { useLocation,useNavigate } from 'react-router-dom';

import memberApi from '../../services/memberApi';
import { toast } from 'react-toastify';
import Navbar from '../Navbar/navbar';

const AddMember = () => {


const location = useLocation();
const navigate = useNavigate();
const memberData = location.state || {};


const [name, setName] = useState(memberData.name || '');
const [emailId, setemailId] = useState(memberData.emailId || '');
const [phoneNumber, setPhoneNumber] = useState(memberData.phoneNumber || '');
const [password, setPassword] = useState('');
const [authenticated, setAuthenticated] = useState(localStorage.getItem("authenticated") ? true : false);
  
  useEffect(() => {
    if (!authenticated) {
      navigate("/login");
    }
  }, [authenticated, navigate]);



  const handleNameChange = (e) => {
    setName(e.target.value);
  };

  const handleEmailChange = (e) => {
    setemailId(e.target.value);
  };

  const handlePhoneChange = (e) => {
    setPhoneNumber(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleAddMember = async(input) => {
    // Perform the action to add the book
    
    if (name.trim() === '' || emailId.trim() === '' || phoneNumber.trim() === '') {
      toast.warn('All fields are required');
      return;
    }
    if (!/^[A-Za-z0-9+_.]+@(.+)$/.test(emailId)) {
      toast.warn('Invalid email address');
      return;
    }
  
    if (!/^\d{10}$/.test(phoneNumber)) {
      toast.warn('Phone number should contain 10 digits');
      return;
    }
    if(password.trim()===''){
      toast.warn('Please enter password');
      return;
    }

    try {
      const queryParams = {
          name: name,
          emailId: emailId,
          phoneNumber: phoneNumber,
          password: password
      };
          if(input ==='update'){
            const results = await memberApi.updateMember(queryParams);
          }else if(input === 'create'){
            const results = await memberApi.addMember(queryParams);
          }
          
          setemailId('');
          setName('');
          setPhoneNumber('');
          setPassword('');

      
  } catch (error) {
      console.log(error);
  }
    //console.log('Adding book:', { name, author, isbn, genre });
  };
  return (
    <div>
      {/* <Navbar /> */}
      <div className="top">
        <h4>Add New Member</h4>
      </div>
      <div className="bottom">
        <div className="left">
          <img
            src={
              "https://icon-library.com/images/no-image-icon/no-image-icon-0.jpg"
            }
            alt=""
          />
          {/* <Lottie animationData={animation} loop={true} /> */}
        </div>
        <div className="right">
          <form>
            <div className="formInput" >
              {/* <label>Book Name</label> */}
              <input type='text' placeholder='Name' value={name} onChange={handleNameChange} required />
            </div>
            <div className="formInput" >
              {/* <label>Author</label> */}
              <input type='text' placeholder='EmailID' value={emailId} onChange={handleEmailChange} required />
            </div>
            <div className="formInput" >
              {/* <label>ISBN</label> */}
              <input type='text' placeholder='PhoneNumber' value={phoneNumber} onChange={handlePhoneChange} required/>
            </div>
            <div className="formInput" >
              {/* <label>Book Name</label> */}
              <input type='text' placeholder='Password'value={password} onChange={handlePasswordChange} required/>
            </div>

          </form>

        </div>

      </div >
      <div className='addbutton'>
        <div className="login-btn"  onClick={() => handleAddMember('create')}>Add Member</div>
        <div className="login-btn"  onClick={() => handleAddMember('update')}>Update Member</div>
      </div>
    </div >

  );
};

export default AddMember