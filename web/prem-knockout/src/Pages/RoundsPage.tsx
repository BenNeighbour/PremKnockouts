import * as React from "react";
import { RouteComponentProps } from "react-router-dom";
import { Layout, Spin } from "antd";
import Sidebar from "./../Components/Common/Sidebar";
import Topbar from "./../Components/Common/Topbar";
import RoundContainer from "./../Components/Rounds/RoundContainer";
import { GET_KNOCKOUT_BY_NAME } from "../Utils/AJAX/RequestResolver";

interface Props extends RouteComponentProps<any> {}

const RoundsPage: React.FC<Props> = (props) => {
  const [isLoading, setIsLoading]: any = React.useState(true);
  const [knockoutObj, setKnockoutObj]: any = React.useState(null);

  const knockoutName: string = props.match.params.knockoutName;

  // React useEffect for requests
  React.useEffect(() => {
    let isMounted: boolean = true;

    // Make request to get the user knockout by knockout name
    GET_KNOCKOUT_BY_NAME(knockoutName).then((response: any) => {
      if (response.status === 200) {
        if (isMounted) {
          setKnockoutObj(response.data);
          setIsLoading(false);
        }
      }
    });

    return function cleanup() {
      isMounted = false;
    };
  }, [knockoutName]);

  if (!isLoading) {
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
            title={knockoutName}
          />
          <RoundContainer
            location={props.location}
            history={props.history}
            match={props.match}
            knockout={knockoutObj}
            knockoutName={knockoutName}
          />
        </Layout>
      </Layout>
    );
  } else {
    return (
      <Spin
        tip="Loading Rounds..."
        style={{
          margin: "0 auto",
        }}
      ></Spin>
    );
  }
};

export default RoundsPage;
