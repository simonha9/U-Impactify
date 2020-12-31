import axios from 'axios';
import properties from '../../properties';
const apiUrl = `${properties.url}/uimpactify/users`;

export const signUp = (firstName, lastName, username, password, age, role, socialInit) => {
    var roleObj;
    if(role === "impact_learner" || role === "impact_consultant") {
        roleObj = {name: role};
    }
    var socialInitObj;
    if(socialInit) {
        socialInitObj = {name: socialInit};
    }
    return axios.post(`${apiUrl}/signup`,
    {firstName, lastName, username, password, age, role: roleObj, socialInit: socialInitObj});
    // MOCKING API CALL FOR NOW
    return Promise.resolve({});
}

export const signIn = (username, password) => {
    return axios.post(`${apiUrl}/login`,
    {username, password});
}

export const getMyOpportunities = (uid, isEmployee) => {
    if (isEmployee) {
        return axios.get(`${apiUrl}/getPostings/${uid}`);
    } else {
        return axios.get(`${apiUrl}/getApplicationsByUser/${uid}`);
    }
}

 export const createPosting = (title, description, uid, type, socialInit) => {
    const date = new Date;
    const creatorObj = {id: uid};
    const socialInitObj = {name: socialInit};
    
    return axios.post(`${apiUrl}/createPosting`, {
        name: title,
        postingDesc: description,
        postingCreator: creatorObj,
        postingType: type,
        postingDate: date,
        socialInit: socialInitObj
    });
}

export const updatePosting = (pid, title, description, uid, type, socialInit) => {
    const date = new Date;
    const creatorObj = {id: uid};
    const socialInitObj = {name: socialInit};

    return axios.post(`${apiUrl}/updatePosting`, {
        id: pid,
        name: title,
        postingDesc: description,
        postingCreator: creatorObj,
        postingType: type,
        postingDate: date,
        socialInit: socialInitObj
    });
}

export const deletePosting = (pid) => {
    return axios.post(`${apiUrl}/deletePosting/${pid}`);
}

export const getPosting = (pid) => {
    return axios.get(`${apiUrl}/getPosting/${pid}`);
}

export const getUser = (uid) => {
    return axios.get(`${apiUrl}/getUser/${uid}`);
}

export const createApplication = (aid, pid, email) => {
    const applicant = {id: aid};
    const posting = {id: pid};
    return axios.post(`${apiUrl}/createApplication`, {
        applicant: applicant,
        posting: posting,
        email: email
    });
}

export const getApplicationsByPosting = (pid) => {
    return axios.get(`${apiUrl}/getApplicationsByPosting/${pid}`);
}

export const deleteApplication = (aid) => {
    return axios.post(`${apiUrl}/deleteApplication/${aid}`);
}