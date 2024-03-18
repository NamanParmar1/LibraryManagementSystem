import React, { useEffect, useState } from 'react';
import bookApi from '../services/bookApi';

const BookList = () => {
    const [books, setBooks] = useState([]);
  
    useEffect(() => {
      fetchAllBooks();
    }, []);
  
    const fetchAllBooks = () => {
      bookApi.searchBooks({ available: true })
        .then(bookList => {
            //console.log('Book list:', bookList);
          setBooks(bookList);
        })
        .catch(error => {
          console.error('Error fetching books:', error);
        });
    };
    //console.log(books);
  
    return (
      <div>
        <h2>Available Books</h2>
        {books.length === 0 ? (
          <p>No books available</p>
        ) : (
          <ul>
            {books.map(book => (
              
              <li key={book.bookId}>{book.bookName}</li>
              
            ))}
          </ul>
        )}
      </div>
    );
  };
  
  export default BookList;
  
