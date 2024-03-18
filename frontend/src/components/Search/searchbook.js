import React, { useState } from 'react';
import bookApi from '../../services/bookApi';
import './searchbook.css'; // Import the CSS file
import ConfirmationDialog from '../widgets/confirmationdialog';
import { useNavigate } from 'react-router-dom';

const genreOptions = [
  'FICTIONAL',
  'NON_FICTIONAL',
  'GEOGRAPHY',
  'HISTORY',
  'POLITICAL_SCIENCE',
  'BOTANY',
  'CHEMISTRY',
  'MATHEMATICS',
  'PHYSICS'
];

const SearchBooks = () => {
  const navigate = useNavigate();
  const [genre, setGenre] = useState('');
  const [available, setAvailable] = useState(true);
  const [author, setAuthor] = useState('');
  const [isbn, setIsbn] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [showConfirmation, setShowConfirmation] = useState(false);
  const [confirmationData, setConfirmationData] = useState({});




  const handleupdate = async (book) => {
    navigate('/addbook', { state: book });
  };



  const handleSearch = async () => {
    try {
      const queryParams = {
        genre: genre.trim() === '' ? null : genre.trim(),
        available: available === '' ? null : available,
        author: author.trim() === '' ? null : author.trim(),
        isbn: isbn.trim() === '' ? null : isbn.trim()
      };
      const results = await bookApi.searchBooks(queryParams);
      setSearchResults(results);
    } catch (error) {
      console.log(error);
      setSearchResults([]);
    }
    setAuthor('');
    setIsbn('');
  };

  const handleShowConfirmation = (action, book) => {
    setShowConfirmation(true);
    setConfirmationData({ action, book });
  };

  const handleConfirmAction = async () => {
    const { action, book } = confirmationData;
    if (action === 'delete') {
      try {
        await bookApi.deleteBook({book_id:book.bookId,isbn:book.isbn});
        const updatedResults = searchResults.filter(result => result.bookId !== book.bookId);
        setSearchResults(updatedResults);
      } catch (error) {
        console.log(error);
      }
    } else if (action === 'deactivate') {
      // Perform the deactivate action
      // Update the search results if necessary
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
        <h4>Search Books</h4>
      </div>
    <div className="search-books-container">
      <div className="search-form">
        <div className="form-group">
          <label>Genre:</label>
          <select className="text-input" value={genre} onChange={e => setGenre(e.target.value)}>
            <option value="">Select Genre</option>
            {genreOptions.map(option => (
              <option key={option} value={option}>
                {option}
              </option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label>Available:</label>
          <input className="checkbox" type="checkbox" checked={available} onChange={e => setAvailable(e.target.checked)} />
        </div>
        <div className="form-group">
          <label>Author:</label>
          <input className="text-input" type="text" value={author} onChange={e => setAuthor(e.target.value)} />
        </div>
        <div className="form-group">
          <label>ISBN:</label>
          <input className="text-input" type="text" value={isbn} onChange={e => setIsbn(e.target.value)} />
        </div>
        <button className="search-button" onClick={handleSearch}>Search</button>
      </div>

      {searchResults.length > 0 ? (
        <table className="results-table">
          <thead>
            <tr>
              <th>BookId</th>
              <th>Title</th>
              <th>Author</th>
              <th>Genre</th>
              <th>ISBN</th>
              <th>CopiesLeft</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {searchResults.map(book => (
              <tr key={book.bookId}>
                <td>{book.bookId}</td>
                <td>{book.bookName}</td>
                <td>{book.author}</td>
                <td>{book.genre}</td>
                <td>{book.isbn}</td>
                <td>{book.copiesLeft}</td>
                <td>
                  <button onClick={() => handleShowConfirmation('delete', book)}>
                    Delete
                  </button>
                  <button onClick={() => handleupdate(book)}>
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

export default SearchBooks;
