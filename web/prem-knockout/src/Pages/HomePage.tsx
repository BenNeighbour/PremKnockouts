import * as React from "react";
import { Layout, Spin } from "antd";
import Sidebar from "../Components/Common/Sidebar";
import { RouteComponentProps } from "react-router-dom";
import {
  GET_USER_DETAILS,
  GET_USER_KNOCKOUTS,
} from "../Utils/AJAX/RequestResolver";
import HomeContainer from "./../Components/Home/HomeContainer";
import Topbar from "../Components/Common/Topbar";

const { Content } = Layout;

interface Props extends RouteComponentProps<any> {}

const HomePage: React.FC<Props> = (props) => {
  const [isLoading, setIsLoading]: any = React.useState(true);

  const [knockedOutOf, setKnockedOutOf]: any = React.useState([]);
  const [pickedKnockouts, setPickedKnockouts]: any = React.useState([]);
  const [knockouts, setKnockouts]: any = React.useState([]);

  React.useEffect(() => {
    const fetchData = async () => {
      // Get user details request
      await GET_USER_DETAILS().then((response: any) => {
        if (response.status === 200) {
          setKnockedOutOf(response.data.knockedOutOf);
          setPickedKnockouts(response.data.knockoutRoundsPicked);
          localStorage.setItem("userId", response.data.id);
        }
      });

      // Get user knockouts request
      await GET_USER_KNOCKOUTS().then((response: any) => {
        if (response.status === 200) {
          setKnockouts(response.data);
          setIsLoading(false);
        }
      });
    };

    fetchData();
  }, []);

  if (localStorage.getItem("userId") !== null) {
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
            isRootPage={true}
            title="Home"
            subtitle="Your Knockouts"
          />
          {!isLoading ? (
            knockouts.map((knockout: any, index: any) => (
              <HomeContainer
                history={props.history}
                location={props.location}
                match={props.match}
                isKnockedOutOf={isAlreadyPicked(
                  knockedOutOf,
                  knockouts[index].id
                )}
                knockout={knockout}
                isPicked={isAlreadyPicked(pickedKnockouts, knockouts[index].id)}
                key={index}
              />
            ))
          ) : (
            <Content style={{ margin: "24px 16px 0" }}>
              <div
                className="site-layout-background"
                style={{ padding: 24, minHeight: 360, textAlign: "center" }}
              >
                <Spin
                  style={{
                    marginTop: 120,
                  }}
                  tip="Loading Knockouts..."
                ></Spin>
              </div>
            </Content>
          )}
        </Layout>
      </Layout>
    );
  } else {
    return (
      <Spin
        tip="Loading..."
        style={{
          margin: "0 auto",
        }}
      ></Spin>
    );
  }
};

function isAlreadyPicked(array: any, knockoutId: string): any {
  let found = false;
  for (let i = 0; i < array.length; i++) {
    if (array[i].knockoutId === knockoutId) {
      found = true;
      break;
    }
  }

  return found;
}

export default HomePage;
