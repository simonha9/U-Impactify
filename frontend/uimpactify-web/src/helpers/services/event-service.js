import axios from "axios";
import properties from "../../properties";
const apiUrl = `${properties.url}/uimpactify/users`;
export const createEvent = (
  eventName,
  eventDesc,
  startDate,
  startTime,
  endDate,
  endTime
) => {
  const startTimestamp = new Date(`${startDate}T${startTime}`).toISOString();
  const endTimestamp = new Date(`${endDate}T${endTime}`).toISOString();

  const authedUserInfo = JSON.parse(localStorage.getItem("authenticatedUser"));
  const authedUserID = authedUserInfo.id;
  const authedUserSocialInit = authedUserInfo.socialInit
    ? authedUserInfo.socialInit.name
    : null;
  const payload = {
    eventName: eventName,
    eventDesc: eventDesc,
    eventCreator: { id: authedUserID },
    eventStartDate: startTimestamp,
    eventEndDate: endTimestamp,
    socialInit: { name: authedUserSocialInit },
  };
  return axios.post(`${apiUrl}/createEvent`, payload);
};

export const deleteEvent = (postId) => {
  return axios.post(`${apiUrl}/deleteEvent/${postId}`, {});
};

export const getEvent = (userId) => {
  return axios.get(`${apiUrl}/getEvent/${userId}`);
};

export const getEvents = (userId) => {
  return axios.get(`${apiUrl}/getEvents/${userId}`);
};
export const getEventsByDate = (userId, date) => {
  return axios.get(`${apiUrl}/getEventsByDate/${userId}?date=${date}`);
};
export const getTodayEvent = (userId) => {
  let date = new Date();
  let isoDate = date.toISOString().split("T")[0];
  return getEventsByDate(userId, isoDate);
};
export const getTomorrowEvent = (userId) => {
  let date = new Date();
  date.setDate(date.getDate() + 1);
  let isoDate = date.toISOString().split("T")[0];
  return getEventsByDate(userId, isoDate);
};

export const updateEvent = (
  postId,
  eventName,
  eventDesc,
  startDate,
  startTime,
  endDate,
  endTime
) => {
  const startTimestamp = new Date(`${startDate}T${startTime}`).toISOString();
  const endTimestamp = new Date(`${endDate}T${endTime}`).toISOString();
  const authedUserInfo = JSON.parse(localStorage.getItem("authenticatedUser"));
  const authedUserID = authedUserInfo.id;
  const authedUserSocialInit = authedUserInfo.socialInit.name;
  const payload = {
    id: postId,
    eventName: eventName,
    eventDesc: eventDesc,
    eventCreator: { id: authedUserID },
    eventStartDate: startTimestamp,
    eventEndDate: endTimestamp,
    socialInit: { name: authedUserSocialInit },
  };
  return axios.post(`${apiUrl}/updateEvent`, payload);
};
