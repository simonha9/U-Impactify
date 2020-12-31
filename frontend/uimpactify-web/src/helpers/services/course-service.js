import axios from 'axios';
const apiUrl = 'http://localhost:8080/uimpactify';

export const createCourse = (name, cost, description, instructor_id) => {
    return axios.post(`${apiUrl}/users/createCourse`, {
        courseName: name,
        cost: cost,
        courseDesc: description,
        instructor: {
            id: instructor_id
        }
    });
}

export const getAllCourses = () => {
    return axios.get(`${apiUrl}/courses/getAllCourses`);
}

export const getCourseSessions = (courseId) => {
    return axios.get(`${apiUrl}/courses/getAllSessionByCourse/${courseId}`);
}

export const createCourseLiveSession = (courseId, startDate, endDate) => {
    return axios.post(`${apiUrl}/courses/createSession`, {
        course: {id: courseId},
        startDate, endDate
    });
}

export const deleteSession = (sessionId) => {
    return axios.post(`${apiUrl}/courses/deleteSession/${sessionId}`);
}

export const enrollInCourse = (course_id, student_id) => {
    return axios.post(`${apiUrl}/users/addCourseToStudent`, {
        course: {id: course_id},
        student: {id: student_id}
    });
}

export const getStudentCourses = (student_id) => {
    return axios.get(`${apiUrl}/users/getAllCoursesFromStudent/${student_id}`);
}

export const getInstructorCourses = (instructor_id) => {
    return axios.get(`${apiUrl}/users/getCoursesByInstructor/${instructor_id}`);
}

export const getCourseById = (courseId) => {
    return axios.get(`${apiUrl}/users/getCourse/${courseId}`);
}