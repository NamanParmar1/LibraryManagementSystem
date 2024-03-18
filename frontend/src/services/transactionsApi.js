import axios from 'axios';
import { toast } from 'react-toastify';

const BASE_URL = 'http://localhost:8082'; // Replace with your backend server URL

const transactionApi = {
  issueBook: async (cardId, bookId) => {
    try {
            const response = await axios.post(`${BASE_URL}/issueBook/${cardId}/${bookId}`);
            console.log(response.data);
            toast.success("Book successfully issued");
            return response.data;
        } catch (error) {
            toast.error(error.response.data.errorMessage);
            throw new Error(error.response.data.message);
        }
  },

  returnBook: async (cardId, bookId) => {
    try {
          const response = await axios.post(`${BASE_URL}/returnBook/${cardId}/${bookId}`);
          toast.success("Book successfully returned");
          toast.success(`${'Fine Amount:'} ${response.data.fineAmount}`);
          return response.data;
      } catch (error) {
        //window.alert(error.response.data.errorMessage);
        toast.error(error.response.data.errorMessage);
          throw new Error(error.response.data.message);
      }
  },

  searchTransactions: async (queryParams) => {
    try {
          const response = await axios.get(`${BASE_URL}/searchtransaction/`, { params: queryParams });
          return response.data;
      } catch (error) {
        toast.error(error.response.data.errorMessage);
          throw new Error(error.response.data);
      }
  },

  searchTransactionsByDate: async (queryParams) => {
    try {
          const response = await axios.get(`${BASE_URL}/searchtransactionbydate/`,{ params: queryParams });
          return response.data;
      } catch (error) {
        toast.error(error.response.data.errorMessage);
          throw new Error(error.response.data);
      }
  },
};

export default transactionApi;
