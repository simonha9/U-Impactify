import React, { Fragment } from "react";
import {
  Container,
  Row,
  Col,
  ButtonToolbar,
  ButtonGroup,
  Button,
} from "shards-react";

let asd = () => {
  console.log("lol");
};
class News extends React.Component {
  constructor(props) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
    this.myRef = React.createRef();
  }

  async handleClick(tab) {
    await this.setState({ activetab: tab });
    console.log(this.state.activetab);
    const node = this.myRef.current;
    node.text = tab;
  }

  componentDidUpdate(prevProps, prevState, snapshot) {
    /* this.state.activetab; */
  }

  componentDidMount() {
    this.state = { activetab: 0 };
  }

  render() {
    return (
      <Container>
        <Row className="tab-selector">
          <ButtonToolbar>
            <ButtonGroup size="sm">
              <Button onClick={() => this.handleClick(0)}>
                Course Related
              </Button>
              <Button onClick={() => this.handleClick(1)}>
                Social Initiative
              </Button>
              <Button onClick={() => this.handleClick(2)}>
                Today's Popular Topics
              </Button>
              <Button onClick={() => this.handleClick(3)}>Other</Button>
            </ButtonGroup>
          </ButtonToolbar>
        </Row>
        <Row className="news-display">
          <div ref={this.myRef}>News</div>
        </Row>
      </Container>
    );
  }
}
export default News;
