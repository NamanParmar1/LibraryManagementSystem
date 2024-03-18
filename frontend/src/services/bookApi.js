import axios from 'axios';
import { toast } from 'react-toastify';

const BASE_URL = 'http://localhost:8082';
const bookApi = {
    addBook: async (bookInputModel) => {
        try {
            const response = await axios.post(`${BASE_URL}/addBook`, bookInputModel);
            toast.success(`${'Book added with id:'}${response.data.bookId}`)
            return response.data;
        } 
        catch (error) {
            toast.error(error.response.data.errorMessage);
            throw new Error(error.response.data);
        }
    },

    updateBook: async (bookInputModel) => {
        try {
            const response = await axios.put(`${BASE_URL}/updateBook`, bookInputModel);
            toast.success(`${'Book id:'}${response.data.bookId}${' updated'}`)
            return response.data;
        } 
        catch (error) {
            toast.error(error.response.data.errorMessage);
            throw new Error(error.response.data);
        }
    },

    searchBooks: async (queryParams) => {
        try {

            const response = await axios.get(`${BASE_URL}/searchBooks`, { params: queryParams });
            return response.data;
        } 
        catch (error) {
            toast.error(error.response.data.errorMessage);
            throw new Error(error.response.data);
        }
    },

    deleteBook: async (queryParams) => {
        try {
            const response = await axios.delete(`${BASE_URL}/deleteBook`, { params: queryParams });
            toast.success('Book Successfully Deleted')
            return response.data;
        } 
        catch (error) {
            // window.alert(error.response.data.errorMessage);
            toast.error(error.response.data.errorMessage);
            throw new Error(error.response.data);
        }
    }
};

export default bookApi;
