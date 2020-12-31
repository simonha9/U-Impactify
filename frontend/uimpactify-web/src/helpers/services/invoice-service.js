import axios from 'axios';
import properties from '../../properties';
const apiUrl = `${properties.url}/uimpactify/users`;

export const getAllInvoices = (userId) => {
    return axios.get(`${apiUrl}/allInvoices?userId=${userId}`);
}

export const payInvoice = (invoiceId) => {
    return axios.get(`${apiUrl}/payInvoice?invoiceId=${invoiceId}`);
}