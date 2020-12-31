import React from 'react';
import { Link } from 'react-router-dom';
import "../stylesheets/css/OpportunityCard.css";
import applied from "../img/applied_stamp.png";

function OpportunityCard(props) {
    var linkPath = "/";
    if (props.button === "Manage") {
        linkPath = "/opportunity/manage/";
    } else if (props.button === "Apply") {
        linkPath = "/opportunity/apply/";
    } else if (props.button === "Details") {
        linkPath = "/opportunity/details/";
    }

    return (
        <div className="col-sm-3 border rounded opportunity-card-container">
            <div className="card" />
            <div className="card-body">
                {props.applied && <img className="card-img-top" src={applied} />}
                <h4 className="card-title">{props.title}</h4>
                <p className="card-text opportunity-card-description">{props.description}</p>
                <Link 
                className="btn btn-primary"
                to={{
                    pathname: linkPath + props.pid,
                    state: {
                        type: props.type,
                        pid: props.pid,
                        title: props.title,
                        description: props.description,
                    }
                }}>
                    {props.button} &rarr;
                </Link>
            </div>
        </div>
    )
}
 
export default OpportunityCard;