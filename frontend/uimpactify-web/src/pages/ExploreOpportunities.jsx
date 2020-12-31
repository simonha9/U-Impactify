import React, { Component } from 'react';
import OpportunityCard from "../components/OpportunityCard";
import "../stylesheets/css/Opportunities.css";
import { getAllPosting } from "../helpers/services/posting-service";

class ExploreOpportunities extends Component {
    constructor(props) {
        super(props);
        this.state = {
            role: props.uinfo.role,
            socialInit: props.uinfo.socialInit,
            volOpp: [],
            empOpp: [],
            conOpp: []
        }
    }

    componentDidMount() {
        var curr;
        var volOpp = [];
        var empOpp = [];
        var conOpp = [];

        const type = this.state.role ? this.state.role.name : null;
        const isConsultant = true ? type === "IMPACT_CONSULTANT" : false;
        const isLearner = true ? type === "IMPACT_LEARNER" : false;
        const isEmployee = true ? !type : false;
        if (isConsultant) {
            document.getElementById("VolunteerContent").style.display = "none";
            document.getElementById("EmploymentContent").style.display = "none";
        } else if (isLearner || isEmployee) {
            document.getElementById("EmploymentContent").style.display = "none";
            document.getElementById("ConsultingContent").style.display = "none";
        }

        getAllPosting(this.state.uid).then(
            (r) => {
                for (var i = 0; i < r.data.length; i++) {
                    curr = r.data[i];
                    if (curr.postingType === "VOLUNTEERING") {
                        volOpp.push({id: curr.id, title: curr.name, description: curr.postingDesc});
                    } else if (curr.postingType === "EMPLOYMENT") {
                        empOpp.push({id: curr.id, title: curr.name, description: curr.postingDesc});
                    } else if (curr.postingType === "CONSULTING") {
                        conOpp.push({id: curr.id, title: curr.name, description: curr.postingDesc});
                    }
                }
                this.setState({ volOpp: volOpp });
                this.setState({ empOpp: empOpp });
                this.setState({ conOpp: conOpp });
            }
        );
    }

    handleTabSwitch(tab) {
        var i;

        var tabcontents = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontents.length; i++) {
            tabcontents[i].style.display = "none";
        }
        
        var tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }
        
        document.getElementById(tab + "Content").style.display = "";
        document.getElementById(tab).className += " active";
    }

    render() {
        const { volOpp, empOpp, conOpp, role } = this.state;
        const type = role ? role.name : null;
        const isConsultant = true ? type === "IMPACT_CONSULTANT" : false;
        const isLearner = true ? type === "IMPACT_LEARNER" : false;
        const isEmployee = true ? !role : false;
        var buttonText;
        if (isEmployee) {
            buttonText = "Details";
        } else {
            buttonText = "Apply";
        }

        return(
            <div className="opportunity-page-container">
                <div className="shadow p-3 mb-5 bg-white rounded">
                    <h3 className="opportunity-page-header">Explore Opportunities</h3>
                </div>

                <ul className="nav nav-tabs">
                    {(isEmployee || isLearner) &&
                    <li className="nav-item">
                        <button className="nav-link tablinks active"
                                id="Volunteer"
                                onClick={() => this.handleTabSwitch("Volunteer")}>
                            Volunteer
                        </button>
                    </li>}
                    {(isEmployee || isLearner) &&
                    <li className="nav-item">
                        <button className="nav-link tablinks"
                                id="Employment"
                                onClick={() => this.handleTabSwitch("Employment")}>
                            Employment
                        </button>
                    </li>}
                    {(isEmployee || isConsultant) &&
                    <li className="nav-item">
                        <button className="nav-link tablinks"
                                id="Consulting"
                                onClick={() => this.handleTabSwitch("Consulting")}>
                            Consulting
                        </button>
                    </li>}
                </ul>

                <div className="tabcontent row" id="VolunteerContent">
                    {volOpp.map((opp) => (
                        <OpportunityCard
                            key={opp.id}
                            pid={opp.id}
                            title={opp.title}
                            description={opp.description}
                            type="VOLUNTEERING"
                            button={buttonText}
                        />
                    ))}
                </div>
                <div className="tabcontent row" id="EmploymentContent">
                    {empOpp.map((opp) => (
                        <OpportunityCard
                            key={opp.id}
                            pid={opp.id}
                            title={opp.title}
                            description={opp.description}
                            type="EMPLOYMENT"
                            button={buttonText}
                        />
                    ))}
                </div>
                <div className="tabcontent row" id="ConsultingContent">
                    {conOpp.map((opp) => (
                        <OpportunityCard
                            key={opp.id}
                            pid={opp.id}
                            title={opp.title}
                            description={opp.description}
                            type="CONSULTING"
                            button={buttonText}
                        />
                    ))}
                </div>

            </div>
        );
    }
}

export default ExploreOpportunities;