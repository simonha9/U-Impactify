import firebase from 'firebase';

const config = {
    apiKey: "AIzaSyC8_4fHFyMInvWgkfY2CVJBbjssoIAXHLA",
    authDomain: "u-impactify.firebaseapp.com",
    databaseURL: "https://u-impactify.firebaseio.com",
    projectId: "u-impactify",
    storageBucket: "u-impactify.appspot.com",
    messagingSenderId: "484658232220",
    appId: "1:484658232220:web:e06c8c5ea4eddc9664e659",
    measurementId: "G-YN09Q745K2"
};

firebase.initializeApp(config);
export default firebase;