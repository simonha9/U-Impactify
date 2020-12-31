import React, { Component } from 'react';
import { Button } from 'shards-react';
import CreateQuiz from './quizzes/CreateQuiz';
import QuizList from './quizzes/QuizList';

class QuizTab extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isCreating: false
        }
    }

    render() {
        const user = JSON.parse(localStorage.getItem('authenticatedUser'));
        return(
            <div>
                {this.state.isCreating ? 
                <Button pill theme="info" onClick={()=>this.setState({isCreating: false})}>Back</Button>
                : user.role.name ==="IMPACT_CONSULTANT" && <Button pill theme="success" onClick={()=>this.setState({isCreating: true})}>Create Quiz</Button>}
                {this.state.isCreating ? 
                <CreateQuiz cid={this.props.cid} onCreate={()=>this.setState({isCreating: false})}></CreateQuiz> 
                : <QuizList cid={this.props.cid} user={user}></QuizList>}
            </div>

            );
    }

}

export default QuizTab;