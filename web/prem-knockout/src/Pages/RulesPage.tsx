import * as React from "react";
import { Layout, Typography, PageHeader } from "antd";
import Sidebar from "../Components/Common/Sidebar";
import { RouteComponentProps } from "react-router-dom";
import { GET_USER_DETAILS } from "../Utils/AJAX/RequestResolver";

const { Content } = Layout;
const { Title, Paragraph } = Typography;

interface Props extends RouteComponentProps<any> {}

const RulesPage: React.FC<Props> = (props) => {
  React.useEffect(() => {
    GET_USER_DETAILS().then((response: any) => {
      if (response.status === 200)
        localStorage.setItem("userId", response.data.id);
    });
  }, []);

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
        current="rules"
      />

      <Layout>
        <PageHeader
          className="site-page-header"
          onBack={() => {}}
          title="Title"
          subTitle="This is a subtitle"
        />
        <Content style={{ margin: "24px 16px 0" }}>
          <div
            className="site-layout-background"
            style={{ padding: 24, minHeight: 360 }}
          >
            <Typography>
              <Title>Few Rules</Title>
              <Paragraph>No moaning, no complaining...</Paragraph>
            </Typography>
          </div>
        </Content>
      </Layout>
    </Layout>
  );
};

export default RulesPage;
