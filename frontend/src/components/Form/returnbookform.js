import React ,{ useState } from 'react'
import './returnbookform.css';
import transactionApi from '../../services/transactionsApi';

const Returnbookform = ({ onUpdateWidgetData }) => {
  const[cardId ,setCardId] = useState('');
  const[bookId ,setBookId] = useState('');





  const handleReturnBooks = async() => {
    // Add your logic for handling the "Return Books" button click
    try{
      const results = await transactionApi.returnBook(cardId,bookId);
      onUpdateWidgetData();

    }catch(error){
      console.log(error);
      //console.log(error);

    }
    setBookId('');
    setCardId('');

  };
  return (
    <div className="issuecover">
      <span className="title">Return</span>
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
      <div className="login-btn"  onClick={handleReturnBooks}>Return</div>

    </div>
  )
}

export default Returnbookform;