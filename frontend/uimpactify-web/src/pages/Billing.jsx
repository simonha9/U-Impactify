import React from 'react';
import { Container, Card, CardBody, Row, Col, FormInput, FormGroup, CardTitle } from 'shards-react';
import '../stylesheets/css/Billing.css';
import Chart from 'react-apexcharts';
import { Button, ListGroup, ListGroupItem, ListGroupItemHeading, ListGroupItemText } from "shards-react";
import {getAllInvoices, payInvoice} from '../helpers/services/invoice-service';
export default class Billing extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          uid: props.uid,
          invoiceList: [],
          invoiceSeries: [],
          unpaidInvoiceSeries: [],
          invoiceOptions: {
            labels: [],
            chart: {
              type: 'donut',
            },
            plotOptions: {
                pie: {
                    expandOnClick: false,
                    donut: {
                        size: '40%',
                        labels: {
                            show: true,
                            name: {
                              show: true,
                              fontSize: '12px',
                              fontFamily: 'Helvetica, Arial, sans-serif',
                              fontWeight: 600,
                              color: undefined,
                              offsetY: -10,
                              formatter: function (val) {
                                return val.length > 20 ? val.substr(0, 19) + "..." : val
                              }
                            },
                            value: {
                              formatter: function (val) {
                                return "$" + val
                              }
                            }
                        }
                    }
                }
            },
            legend: {
              show: false,              
            },
            tooltip: {
              y: {
                formatter: function(value) {
                  return "$"+value;
                }
              }
            }

          },
          unpaidInvoiceOptions: {
            labels: [],
            chart: {
              type: 'donut',
            },
            plotOptions: {
                pie: {
                  expandOnClick: false,
                    donut: {
                        size: '40%',
                        labels: {
                            show: true,
                            name: {
                              show: true,
                              fontSize: '12px',
                              fontFamily: 'Helvetica, Arial, sans-serif',
                              fontWeight: 600,
                              color: undefined,
                              offsetY: -10,
                              formatter: function (val) {
                                return val.length > 25 ? val.substr(0, 24) + "..." : val
                              }
                            },
                            value: {
                              formatter: function (val) {
                                return "$" + val
                              }
                            }
                        }
                    }
                }
            },
            legend: {
              show: false,              
            },
            tooltip: {
              y: {
                formatter: function(value) {
                  return "$"+value;
                }
              }
            }
          },
        
        };
    }

    componentDidMount() {
        this.fetchInvoices();

    }

    fetchInvoices = () => {
        getAllInvoices(this.state.uid).then(
            (res) => {
                const invoiceList = res.data;
                this.setState({invoiceList});
                const invoiceSeries = invoiceList.map((item)=>item.initCost);
                const unpaidInvoices = invoiceList.filter((item)=>item.cost !== 0);
                const unpaidInvoiceSeries = unpaidInvoices.map((item)=>item.cost);
                const unpaidInvoiceLabels = unpaidInvoices.map((item)=>item.course.courseName);
                const invoiceLabels = invoiceList.map((item)=>item.course.courseName);
                this.setState({unpaidInvoiceSeries: unpaidInvoiceSeries, unpaidInvoiceOptions: {...this.state.unpaidInvoiceOptions, labels: unpaidInvoiceLabels}, invoiceSeries: invoiceSeries, invoiceOptions: {...this.state.invoiceOptions, labels: invoiceLabels}})

            }
        )
    }
    handlePayInvoice = (invoiceId) => {
        payInvoice(invoiceId).then(
            () => {
                this.fetchInvoices();
            }
        )
    }
    
    render() {

        return(

                <Container className="billing-container">
                    <Col>
                        <Row className="billing-header">
                            <h1>Billing</h1>
                        </Row>
                        <Row>
                          <Col xs="12">
                          <ListGroup>
                          <ListGroupItemHeading className="billing-li">
                            <Row>
                                <Col xs="2">
                                Invoice Id
                                </Col>
                                <Col xs="1">
                                Cost
                                </Col>
                                <Col xs="2">
                                  Payment
                                </Col>
                                <Col xs="7">
                                  Details
                                </Col>
                            </Row>
                          </ListGroupItemHeading>
                          {
                            this.state.invoiceList.map(
                              (item)=>
                              <ListGroupItem key={item.id} className="billing-li">
                                <Row>
                                  <Col xs="2">
                                    {item.id}
                                  </Col>
                                  <Col xs="1">
                                    {`$${item.initCost}`}
                                  </Col>
                                  <Col xs="2">
                                      {item.cost === 0 ? <Button pill size="sm" theme="success" disabled>Paid</Button> : <Button pill size="sm" onClick={()=>{this.handlePayInvoice(item.id)}}>Pay Now</Button>}
                                    
                                  </Col>
                                  <Col xs="7">
                                    {`${item.course.courseName} taught by ${item.course.instructor.user.firstName} ${item.course.instructor.user.lastName}`}
                                  </Col>
                                </Row>
                              </ListGroupItem>)
                          }

                          </ListGroup>
                          </Col>

                        </Row>
                        <hr></hr>
                        <Row>
                            <Col >
                            <Card className="billing-card" >
                                <CardTitle>{`All Invoices (Total: ${this.state.invoiceSeries.length})`}</CardTitle>
                                <CardBody>
                                <Chart options={this.state.invoiceOptions} series={this.state.invoiceSeries} type="donut" />
                                </CardBody>

                            </Card>
                            </Col>
                            <Col>
                            <Card  className="billing-card">
                                <CardTitle>{`Outstanding Payments (Total: ${this.state.unpaidInvoiceSeries.length})`}</CardTitle>
                                <CardBody>
                                    {this.state.unpaidInvoiceSeries.length === 0 ? <p>No outstanding payments, good job!</p> :<Chart options={this.state.unpaidInvoiceOptions} series={this.state.unpaidInvoiceSeries} type="donut" /> }
                                

                                </CardBody>
                            </Card>
                            </Col>

                        </Row>


                    </Col>
                </Container>


        )
    }
}