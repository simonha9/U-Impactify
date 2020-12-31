import axios from "axios";
const apiUrl = "http://localhost:8080/uimpactify";

export const createQuiz = (courseId, quizStartDate, quizEndDate, quizQuestions) => {
  return axios.post(`${apiUrl}/users/createQuiz`, {
    course: {id:courseId},
    quizStartDate,
    quizEndDate,
    quizQuestions,
  });
};

export const deleteQuiz = (quizId) => {
  return axios.post(`${apiUrl}/users/deleteQuiz/${quizId}`);
};

export const getAllQuizzesByCourseId = (courseId) => {
  return axios.get(`${apiUrl}/courses/getAllQuizzesByCourse/${courseId}`);
};

export const submitQuiz = (answers) => {
  return axios.post(`${apiUrl}/users/submitQuizByStudent`, answers);
};

export const getQuizQAnswer = (uid, qid) => {
  return axios.get(`${apiUrl}/users/${uid}/getQuizQAnswer/${qid}`);
};