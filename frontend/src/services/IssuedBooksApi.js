import axios from 'axios';
import { toast } from 'react-toastify';

const BASE_URL = 'http://localhost:8082'; // Replace with your backend server URL

const issuedBooksApi = {
  searchMembersByBookId: async (bookId) => {
    try {
            const response = await axios.get(`${BASE_URL}/searchmembers_bookId/${bookId}`);
            return response.data;
        } catch (error) {
          toast.error(error.response.data.errorMessage);
          throw new Error(error.response.data.message);
        }
  },

  searchBooksByMemberId: async (cardId) => {
    try {
          const response = await axios.get(`${BASE_URL}/searchbooks_memberId/${cardId}`);
          return response.data;
      } catch (error) {
        toast.error(error.response.data.errorMessage);
        throw new Error(error.response.data);
      }
  },
  
  searchBooksByDate: async (date) => {
    try {
          const response = await axios.get(`${BASE_URL}/booksissuedbydate/${date}`);
          return response.data;
      } catch (error) {
        toast.error(error.response.data.errorMessage);
          throw new Error(error.response.data);
      }
  },
};

export default issuedBooksApi;
