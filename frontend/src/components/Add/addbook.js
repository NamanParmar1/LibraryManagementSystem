import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './addbook.css'

import Navbar from '../Navbar/navbar';
import bookApi from '../../services/bookApi';
import { toast } from 'react-toastify';
import { useLocation } from 'react-router-dom';




const AddBook = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const bookData = location.state || {};


    const [name, setName] = useState(bookData.bookName||'');
    const [author, setAuthor] = useState(bookData.author||'');
    const [isbn, setIsbn] = useState(bookData.isbn||'');
    const [genre, setGenre] = useState(bookData.genre||'FICTIONAL');

    const [authenticated, setAuthenticated] = useState(localStorage.getItem("authenticated") ? true : false);
  
  useEffect(() => {
    if (!authenticated) {
      navigate("/login");
    }
  }, [authenticated, navigate]);


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


    const handleNameChange = (e) => {
        setName(e.target.value);
    };

    const handleAuthorChange = (e) => {
        setAuthor(e.target.value);
    };

    const handleIsbnChange = (e) => {
        setIsbn(e.target.value);
    };

    const handleGenreChange = (e) => {
        setGenre(e.target.value);
    };

    const handleAddBook = async (input) => {
        // Perform the action to add the book
        if (name.trim() === '' || author.trim() === '' || isbn.trim() === '') {
            toast.warn('All fields are required');
            return;
          }
        try {
            const queryParams = {
                name: name === '' ? null : name,
                author: author === '' ? null : author,
                isbn: isbn === '' ? null : isbn,
                genre: genre
            };
            if(input === 'create'){
                const results = await bookApi.addBook(queryParams);
                setAuthor('');
                setGenre('FICTIONAL');
                setIsbn('');
                setName('');
            }else if(input ='update'){
                const results = await bookApi.updateBook(queryParams);
                setAuthor('');
                setGenre('FICTIONAL');
                setIsbn('');
                setName('');
            }
                
            
        } catch (error) {
            console.log(error);
        }


    };

    return (
        <div>
            {/* <Navbar /> */}
            <div className="top">
                <h4>Add New Book</h4>
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
                            <input type='text' placeholder='Book Name' value={name} onChange={handleNameChange} />
                        </div>
                        <div className="formInput" >
                            {/* <label>Author</label> */}
                            <input type='text' placeholder='Author' value={author} onChange={handleAuthorChange} />
                        </div>
                        <div className="formInput" >
                            {/* <label>ISBN</label> */}
                            <input type='text' placeholder='ISBN' value={isbn} onChange={handleIsbnChange} />
                        </div>
                        <div className="formInput"  >
                            {/* <label>GENRE</label> */}
                            <select value={genre} onChange={handleGenreChange} required>
                                {/* <option value="">Select Genre</option> */}
                                {genreOptions.map(option => (
                                    <option key={option} value={option}>
                                        {option}
                                    </option>
                                ))}
                            </select>
                        
                        </div>

                        
                    </form>

                </div>

            </div>
            <div className='addbutton'>
                <div className="login-btn" onClick={() => handleAddBook('create')}>Add Book</div>
                <div className="login-btn" onClick={() => handleAddBook('update')}>Update Book</div>
            </div>
        </div>

    );
};

export default AddBook;
