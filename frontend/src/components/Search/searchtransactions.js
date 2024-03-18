import React, { useState } from 'react';
import transactionsApi from '../../services/transactionsApi';
import './searchmember.css'; // Import the CSS file
import 'react-datepicker/dist/react-datepicker.css';
import DatePicker from 'react-datepicker';

const transactionStatusOptions = ['ISSUED', 'RETURNED'];

const SearchMembers = () => {
  const [cardId, setCardId] = useState(null);
  const [bookId, setBookId] = useState(null);
  const [transactiondate, setTransactionDate] = useState(null);
  const [transactionstatus, setTransactionstaus] = useState(null);
  const [searchResults, setSearchResults] = useState([]);

  const handleSearch = async () => {
    try {
      const queryParams = {
        cardId: cardId === '' ? null : cardId,
        bookId: bookId === '' ? null : bookId,
        status: transactionstatus === '' ? null : transactionstatus,
      };
      if (transactiondate === null || transactiondate === '') {
        const results = await transactionsApi.searchTransactions(queryParams);
        setSearchResults(results);
      } else {
        console.log(transactiondate);
        const queryParams = {
          date: transactiondate,
          status: transactionstatus === '' ? null : transactionstatus,
        };
        const results = await transactionsApi.searchTransactionsByDate(queryParams);
        setSearchResults(results);
      }
    } catch (error) {
      console.log(error);
      setSearchResults([]);
    }
    setCardId('');
    setBookId('');
    setTransactionDate('');
  };

  return (
    <>
      <div className="top">
        <h4>Search All Transactions</h4>
      </div>
      <div className="search-books-container">
        <div className="search-form">
          <div className="form-group">
            <label>Card Id:</label>
            <input
              className="text-input"
              placeholder="CardId"
              type="text"
              value={cardId}
              onChange={(e) => setCardId(e.target.value)}
            />
          </div>
          <div className="form-group">
            <label>Book Id:</label>
            <input
              className="text-input"
              placeholder="BookId"
              type="text"
              value={bookId}
              onChange={(e) => setBookId(e.target.value)}
            />
          </div>
          <div className="form-group">
            <label>Transaction Status:</label>
            <select
              className="text-input"
              value={transactionstatus}
              onChange={(e) => setTransactionstaus(e.target.value)}
            >
              <option value="">Select TransactionStatus</option>
              {transactionStatusOptions.map((option) => (
                <option key={option} value={option}>
                  {option}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label>Transaction Date:</label>
            <input
              className="text-input"
              type="date"
              value={transactiondate}
              onChange={(e) => setTransactionDate(e.target.value)}
            />
          </div>
          <button className="search-button" onClick={handleSearch}>
            Search
          </button>
        </div>

        {searchResults.length > 0 ? (
          <div className="results-table-container">
            <table className="results-table">
              <thead>
                <tr>
                  <th>CardId</th>
                  <th>BookId</th>
                  <th>Issue Date</th>
                  <th>Return Date</th>
                  <th>Status</th>
                  <th>Fine</th>
                </tr>
              </thead>
              <tbody style={{ maxHeight: '300px', overflowY: 'scroll' }}>
                {searchResults.map((transaction, index) => (
                  <tr key={index}>
                    <td>{transaction.card}</td>
                    <td>{transaction.book}</td>
                    <td>{transaction.transactionDate}</td>
                    <td>{transaction.returnDate}</td>
                    <td>{transaction.transactionStatus}</td>
                    <td>{transaction.fineAmount}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <div className="results-table">No results found.</div>
        )}
      </div>
    </>
  );
};

export default SearchMembers;
