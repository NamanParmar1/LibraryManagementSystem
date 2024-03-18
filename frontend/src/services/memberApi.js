import axios from 'axios';
import { toast } from 'react-toastify';

const BASE_URL = 'http://localhost:8082'; // Replace with your backend server URL

const memberApi = {
  addMember: async (memberInputModel) => {
    try {
            const response = await axios.post(`${BASE_URL}/addMember`, memberInputModel);
            //window.alert('successfully added')
            toast.success('Member added');
            return response.data;
        } catch (error) {
            //window.alert(error.response.data.errorMessage);
            toast.error(error.response.data.errorMessage);
            throw new Error(error.response.data);
        }
  },
  updateMember: async (memberInputModel) => {
    try {
            const response = await axios.put(`${BASE_URL}/updateMember`, memberInputModel);
            //window.alert('successfully added')
            toast.success('Member Updated');
            return response.data;
        } catch (error) {
            //window.alert(error.response.data.errorMessage);
            toast.error(error.response.data.errorMessage);
            throw new Error(error.response.data);
        }
  },

  activateDeactivateMember: async (id) => {
    try {
          const response = await axios.put(`${BASE_URL}/activate_deactivateMember/${id}`);
          //window.alert(response.data);
          toast.success('Successfull');
          return response.data;
          
      } catch (error) {
        //console.log(error.response.data);
        //window.alert(error.response.data.errorMessage);
        toast.error(error.response.data.errorMessage);
        throw new Error(error.response.data);
      }
  },

  searchMember: async (queryParams) => {
    try {
          const response = await axios.get(`${BASE_URL}/searchmember`, { params: queryParams });
          return response.data;
      } catch (error) {
        //window.alert(error.response.data.errorMessage);
        toast.error(error.response.data.errorMessage);
        throw new Error(error.response.data);
      }
  },

  getAllMembers: async (cardStatus) => {
    try {
          const response = await axios.get(`${BASE_URL}/allmembers/${cardStatus}`);
          return response.data;
      } catch (error) {
        // window.alert(error.response.data.errorMessage);
        toast.error(error.response.data.errorMessage);
        throw new Error(error.response.data);
      }
  },
};

export default memberApi;
