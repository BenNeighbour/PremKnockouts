import * as React from "react";
import { RouteComponentProps } from "react-router-dom";
import { Layout, List } from "antd";
import Sidebar from "./../Components/Common/Sidebar";
import Topbar from "./../Components/Common/Topbar";

const { Content } = Layout;

interface Props extends RouteComponentProps<any> {}

const MatchesPage: React.FC<Props> = (props) => {
  let matches: Array<any> = [props.location.state];

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
          title={`Round ${props.match.params.roundNumber}`}
          subtitle="Matches"
        />
        <Content style={{ margin: "24px 16px 0" }}>
          <div className="site-layout-background">
            <List
              size="large"
              bordered
              dataSource={matches[0]}
              renderItem={(match: any) => (
                <List.Item>{match.fixtureName}</List.Item>
              )}
            />
          </div>
        </Content>
      </Layout>
    </Layout>
  );
};

export default MatchesPage;
