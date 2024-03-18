import React, { useState } from 'react';
import IssuedBooksApi from '../../services/IssuedBooksApi';
import './searchmember.css'; // Import the CSS file
import 'react-datepicker/dist/react-datepicker.css';
import DatePicker from 'react-datepicker';
import { toast } from 'react-toastify';

const SearchIssuedBooks = () => {
  const [cardId, setCardId] = useState('');
  const [bookId, setBookId] = useState('');
  const [resultType, setResultType] = useState(null);
  const [searchResults, setSearchResults] = useState([]);

  const handleSearch = async () => {
    try {
      const queryParams = {
        cardId: cardId === '' ? null : cardId,
        bookId: bookId === '' ? null : bookId,
      };

      if (queryParams.bookId === null && queryParams.cardId === null) {
        toast.error('Please enter either Card Id or Book Id');
        throw new Error('Please enter either Card Id or Book Id');
      }
      if (queryParams.bookId !== null && queryParams.cardId !== null) {
        toast.error('Please enter either Card Id or Book Id');
        throw new Error('Please enter either Card Id or Book Id');
      }

      if (queryParams.bookId === null) {
        const results = await IssuedBooksApi.searchBooksByMemberId(queryParams.cardId);
        setResultType('book');
        setSearchResults(results);
      } else if (queryParams.cardId === null) {
        const results = await IssuedBooksApi.searchMembersByBookId(queryParams.bookId);
        setResultType('member');
        setSearchResults(results);
      }
    } catch (error) {
      console.log(error);
      setSearchResults([]);
    }

    setCardId('');
    setBookId('');
  };

  return (
    <>
    <div className="top">
        <h4>Search Current Issued Books</h4>
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

        <button className="search-button" onClick={handleSearch}>
          Search
        </button>
      </div>

      {resultType === 'member' && searchResults.length > 0 && (
        <table className="results-table">
          <thead>
            <tr>
              <th>CardId</th>
              <th>Name</th>
              <th>Email Id</th>
              <th>Phone Number</th>
              <th>Books Issued</th>
              <th>Card Status</th>
            </tr>
          </thead>
          <tbody>
            {searchResults.map((member, index) => (
              <tr key={index}>
                <td>{member.cardId}</td>
                <td>{member.name}</td>
                <td>{member.emailId}</td>
                <td>{member.phoneNumber}</td>
                <td>{member.booksIssued}</td>
                <td>{member.cardStatus}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {resultType === 'book' && searchResults.length > 0 && (
        <table className="results-table">
          <thead>
            <tr>
              <th>BookId</th>
              <th>BookName</th>
              <th>Author</th>
              <th>Genre</th>
              <th>ISBN</th>
              <th>Copies Left</th>
            </tr>
          </thead>
          <tbody>
            {searchResults.map((book, index) => (
              <tr key={index}>
                <td>{book.bookId}</td>
                <td>{book.bookName}</td>
                <td>{book.author}</td>
                <td>{book.genre}</td>
                <td>{book.isbn}</td>
                <td>{book.copiesLeft}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {searchResults.length === 0 && <div className="results-table">No results found.</div>}
    </div>
    </>
    
  );
};

export default SearchIssuedBooks;
