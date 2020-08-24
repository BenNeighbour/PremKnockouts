import * as React from "react";
import { RouteComponentProps } from "react-router-dom";
import { Layout } from "antd";
import Sidebar from "./../Components/Common/Sidebar";
import Topbar from "./../Components/Common/Topbar";
import RoundDetailsContainer from "./../Components/Rounds/RoundDetailsContainer";

interface Props extends RouteComponentProps<any> {}

const RoundDetailsPage: React.FC<Props> = (props) => {
  const roundNumber: string = `Round ${props.match.params.roundNumber}`;

  return (
    <Layout
      style={{
        height: "100%",
      }}
    >
      <Sidebar
        history={props.history}
        location={props.location}
        match={props.match}
        current="home"
      />
      <Layout>
        <Topbar
          history={props.history}
          location={props.location}
          match={props.match}
          isRootPage={false}
          title={roundNumber}
        />
        <RoundDetailsContainer
          state={props.location.state}
          history={props.history}
          match={props.match}
          location={props.location}
        />
      </Layout>
    </Layout>
  );
};

export default RoundDetailsPage;
