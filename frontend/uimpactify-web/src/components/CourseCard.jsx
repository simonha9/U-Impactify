import React from 'react';
import { Link } from 'react-router-dom';
import clipart from "../img/knowledge_clipart.png";
import "../stylesheets/css/CourseCard.css";

function CourseCard(props) {
    var cardHeader;
    if (props.isConsultant) {
        cardHeader = "Instructing";
    } else {
        cardHeader = "Enrolled";
    }
 
    return(
        <div className="card w-25 border-info text-center">
            <div className="card-header">{cardHeader}</div>
            <img className="card-img-top course-card-img" src={clipart} />
            <div className="card-body course-card-title">
                <h5 className="card-title">
                    <Link to={{
                        pathname: `/courses/mycourses/${props.cid}`,
                        state: {
                            cid: props.cid,
                            title: props.title,
                            description: props.description,
                            instructor: props.instructor
                        }
                    }}> 
                        {props.title}
                    </Link>
                </h5>
            </div>
            {!props.isConsultant && 
            <div className="card-footer text-muted">
                Instructed by {props.instructor}
            </div>}
        </div>
    );
}

export default CourseCard;