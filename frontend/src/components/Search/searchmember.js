import React, { useState} from 'react';
import { useNavigate } from 'react-router-dom';
import memberApi from '../../services/memberApi';
import './searchmember.css'; // Import the CSS file
import ConfirmationDialog from '../widgets/confirmationdialog';

const cardStatusOptions = [
  'ACTIVATED',
  'DEACTIVATED'
];

const SearchMembers = () => {
  
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');
  const [id, setId] = useState('');
  const [cardstatus, setCardstaus] = useState('ACTIVATED');
  const [searchResults, setSearchResults] = useState([]);
  const [showConfirmation, setShowConfirmation] = useState(false);
  const [confirmationData, setConfirmationData] = useState({});

  const handleSearch = async () => {
    try {
      const queryParams = {
        email: email.trim() === '' ? null : email.trim(),
        phone: phone.trim() === '' ? null : phone.trim(),
        id: id.trim() === '' ? null : id.trim()
      };

      if (Object.values(queryParams).every(value => value === null)) {
        const results = await memberApi.getAllMembers(cardstatus);
        setSearchResults(results);
      } else {
        const results = await memberApi.searchMember(queryParams);
        setSearchResults(results);
      }

      // Clear input fields after search
      setEmail('');
      setPhone('');
      setId('');
    } catch (error) {
      console.log(error);
      setSearchResults([]);
    }
  };

  const handleToggleStatus = async (member) => {
    setShowConfirmation(true);
    setConfirmationData({ action: 'toggleStatus', member });
  };

  const handleMember = async (member) => {
    navigate('/addmember', { state: member });
  };

  const handleConfirmAction = async () => {
    const { action, member } = confirmationData;
    if (action === 'toggleStatus') {
      const updatedMember = { ...member, cardStatus: member.cardStatus === 'ACTIVATED' ? 'DEACTIVATED' : 'ACTIVATED' };
      try {
        await memberApi.activateDeactivateMember(member.cardId);
        const updatedResults = updatedMember;
        
        setSearchResults(updatedResults);
      } catch (error) {
        
      }
    } 
    setShowConfirmation(false);
    setConfirmationData({});
  };

  const handleCancelAction = () => {
    setShowConfirmation(false);
    setConfirmationData({});
  };

  return (
    <>
    <div className="top">
        <h4>Search Member</h4>
      </div>
    <div className="search-members-container">
      <div className="search-form">
        <div className="form-group">
          <label>Id:</label>
          <input className="text-input" placeholder="Id" type="text" value={id} onChange={e => setId(e.target.value)} />
        </div>
        <div className="form-group">
          <label>Email:</label>
          <input className="text-input" placeholder="email" type="text" value={email} onChange={e => setEmail(e.target.value)} />
        </div>
        <div className="form-group">
          <label>Phone:</label>
          <input className="text-input" placeholder="phone" type="text" value={phone} onChange={e => setPhone(e.target.value)} />
        </div>
        <div className="form-group">
          <label>CardStatus:</label>
          <select className="text-input" value={cardstatus} onChange={e => setCardstaus(e.target.value)}>
            <option value="">Select CardStatus</option>
            {cardStatusOptions.map(option => (
              <option key={option} value={option}>
                {option}
              </option>
            ))}
          </select>
        </div>

        <button className="search-button" onClick={handleSearch}>
          Search
        </button>
      </div>

      {searchResults.length > 0 ? (
        <table className="results-table">
          <thead>
            <tr>
              <th>CardId</th>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Books Issued</th>
              <th>Card Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {searchResults.map(member => (
              <tr key={member.cardId}>
                <td>{member.cardId}</td>
                <td>{member.name}</td>
                <td>{member.emailId}</td>
                <td>{member.phoneNumber}</td>
                <td>{member.booksIssued}</td>
                <td>{member.cardStatus}</td>
                <td>
                  <button onClick={() => handleToggleStatus(member)}>
                    {member.cardStatus === 'ACTIVATED' ? 'Deactivate' : 'Activate'}
                  </button>
                  <button onClick={() => handleMember(member)}>
                    Update
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <div className="results-table">No results found.</div>
      )}

      {showConfirmation && (
        <ConfirmationDialog
          message="Are you sure you want to perform this action?"
          onConfirm={handleConfirmAction}
          onCancel={handleCancelAction}
        />
      )}
    </div>
    </>
    
  );
};

export default SearchMembers;
