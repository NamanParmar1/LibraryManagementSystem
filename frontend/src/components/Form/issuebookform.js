import React, { useState } from 'react';
import './issuebookform.css';
import transactionApi from '../../services/transactionsApi';

const Issuebookform = ({ onUpdateWidgetData }) => {
  const [cardId, setCardId] = useState('');
  const [bookId, setBookId] = useState('');

  const handleIssueBooks = async () => {
    try {
      // Add your logic for handling the "Issue Books" button click
      const results = await transactionApi.issueBook(cardId, bookId);
      onUpdateWidgetData(); // Call the prop function to update the widget data in the parent component
    } catch (error) {
      // Handle error
    }
    
    setBookId('');
    setCardId('');
  };

  return (
    <div className="issuecover">
      <span className="title">Issue</span>
      <input
        type="text"
        placeholder="CardId"
        value={cardId}
        onChange={(e) => setCardId(e.target.value)}
      />

      <input
        type="text"
        placeholder="BookID"
        value={bookId}
        onChange={(e) => setBookId(e.target.value)}

      />
      <div className="login-btn"  onClick={handleIssueBooks}>Issue</div>

    </div>
  );
};

export default Issuebookform;
