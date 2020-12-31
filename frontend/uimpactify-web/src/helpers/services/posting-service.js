import axios from 'axios';
import properties from '../../properties';
const apiUrl = `${properties.url}/uimpactify/postings`;

export const getAllPosting = () => {
    return axios.get(`${apiUrl}/getAllPostings`);
}