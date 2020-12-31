import React, { Component } from 'react';
import "../../stylesheets/css/AssignmentsTab.css";
import file_icon from "../../img/file.png";
import firebase from "../../firebase.js";
import { getUser } from "../../helpers/services/user-service";

class AssignmentDetails extends Component {
    constructor(props) {
        super(props);
        this.state = {
            assign: props.curr_assign,
            handoutLink: null,
            // for learners
            submitFile: null,
            submittedLink: null,
            submissionId: null,
            submitSuccess: false,
            // for consultants
            submissionList: []
        }
    }

    componentDidMount() {
        const type = this.props.role ? this.props.role.name : null;
        const isLearner = true ? type === "IMPACT_LEARNER" : false;
        const isConsultant = true ? type === "IMPACT_CONSULTANT" : false;
        const storageRef = firebase.storage().ref();
        const submissionRef = firebase.firestore().collection('submissions');

        // get the link to the assignment handout
        storageRef.child(this.state.assign.id).getDownloadURL().then(
            (url) => {
                this.setState({ handoutLink: url });
            }
        );

        if (isConsultant) {
            // get submissionList from firebase
            var submissionList = [];
            submissionRef.where("assignmentId", "==", this.state.assign.id).onSnapshot(
                (qSnapshot) => {
                    qSnapshot.forEach((doc) => {
                        storageRef.child(doc.id).getDownloadURL().then(
                            (url) => { 
                                getUser(doc.data().userId).then(
                                    (r) => {
                                        const curr = {
                                            id: doc.id,
                                            fileName: doc.data().fileName,
                                            uid: doc.data().userId,
                                            link: url,
                                            uname: r.data.firstName + " " + r.data.lastName
                                        };
                                        submissionList.push(curr);
                                        this.setState({ submissionList: submissionList });
                                    }
                                ); 
                            }
                        ); 
                    });
                }
            );
        } else if (isLearner) {
            // get submissionId and link if learner already submitted
            submissionRef
              .where("userId", "==", this.props.uid)
              .where("assignmentId", "==", this.state.assign.id)
              .get()
              .then((qSnapshot) => {
                if (!qSnapshot.empty) {
                    storageRef.child(qSnapshot.docs[0].id).getDownloadURL().then(
                        (url) => {
                            this.setState({ submissionId: qSnapshot.docs[0].id, submittedLink: url });
                    });
                }
              });
        }
    }

    handleAttachSubmission = (event) => {
        const fileName = event.target.value.split("\\").pop();
        document.getElementById("submit-upload-text").innerHTML = fileName;
        this.setState({ submitFile: event.target.files[0] });
    }

    handleClickSubmit = () => {
        const submissionRef = firebase.firestore().collection('submissions');
        const storageRef = firebase.storage().ref();
        
        // submission entry DNE yet, add one and upload submission
        if (!this.state.submissionId) {
            submissionRef.add({
                userId: this.props.uid,
                assignmentId: this.state.assign.id,
                fileName: this.state.submitFile.name
            }).then(
                (doc) => {
                    this.setState({ submissionId: doc.id })
                    // upload submission file to storage
                    const fileRef = storageRef.child(doc.id);
                    var uploadTask = fileRef.put(this.state.submitFile)
                    uploadTask.on('state_changed',
                        (snapshot) => {},
                        (error) => {},
                        () => {
                            uploadTask.snapshot.ref.getDownloadURL().then(
                                (downloadURL) => {
                                    this.setState({ submittedLink: downloadURL, submitSuccess: true });
                                }
                            );
                        }
                    );
                }
            );
        } else {
            // submission entry exists, update entry
            submissionRef.doc(this.state.submissionId).update({
                fileName: this.state.submitFile.name
            }).then(
                () => {
                    // upload new submission file to storage
                    const fileRef = storageRef.child(this.state.submissionId);
                    var uploadTask = fileRef.put(this.state.submitFile)
                    uploadTask.on('state_changed',
                        (snapshot) => {},
                        (error) => {},
                        () => {
                            uploadTask.snapshot.ref.getDownloadURL().then(
                                (downloadURL) => {
                                    this.setState({ submittedLink: downloadURL, submitSuccess: true });
                                }
                            );
                        }
                    );
                }
            );
            
        }        
    }

    render() {
        const { assign, handoutLink, submitFile, submittedLink, submissionId, submissionList } = this.state;
        const type = this.props.role ? this.props.role.name : null;
        const isLearner = true ? type === "IMPACT_LEARNER" : false;
        const isConsultant = true ? type === "IMPACT_CONSULTANT" : false;
        console.log(this.state.submissionList);

        return(
            <div>
                <button className="btn btn-secondary float-right"
                    onClick={this.props.onBack}>
                    Back to Available Assignments
                </button>
                <h3>{assign.name}</h3>
                <p>Due {assign.due.toString()}</p>
                <a href={handoutLink}>
                    <img src={file_icon} className="download-link-icon"/>
                    Download the assignment handout here
                </a><br/>

                {isLearner &&
                <div className="learner-submission-container">
                    <h4>Your Submission</h4>
                    {submissionId && 
                    <a href={submittedLink}>Submitted</a>}
                    {!submissionId && 
                    <div>
                        Nothing submitted yet
                    </div>}
                    <div className="custom-file">
                        <input type="file" 
                            className="custom-file-input"
                            id="submission-upload"
                            onChange={(e) => this.handleAttachSubmission(e)} />
                        <label htmlFor="submission" className="custom-file-label" id="submit-upload-text">
                            Upload your assignment solution here
                        </label>
                    </div>
                    <button className="btn btn-primary assign-submit-button"
                            onClick={this.handleClickSubmit}
                            disabled={!submitFile}>
                        Submit
                    </button>
                    {this.state.submitSuccess &&
                    <div className="badge badge-success">Successfully submitted!</div>}
                </div>}

                {isConsultant &&
                <div className="consultant-submission-container">
                    <h4>Submissions</h4>
                    <div className="row">
                        {submissionList.map((sub) => (
                            <a key={sub.id} className="sub-entry" href={sub.link}>
                                <p className="sub-entry-fname">{sub.fileName}</p>
                                <img className="sub-entry-link-icon" src={file_icon}/>
                                <p className="sub-entry-uname">Submitted by {sub.uname}</p>
                            </a>
                        ))}
                    </div>
                </div>}
            </div>
        );
    }
}
 
export default AssignmentDetails;