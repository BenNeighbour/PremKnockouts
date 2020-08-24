import * as React from "react";
import { Layout, Typography, Button, Modal, Divider, Row, Col } from "antd";
import PickContainer from "./Pick/PickContainer";
import { GET_ALL_TEAMS } from "../../Utils/AJAX/RequestResolver";
import {
  CheckCircleOutlined,
  CloseCircleOutlined,
  TrophyOutlined,
} from "@ant-design/icons";
import RoundCountdown from "./RoundCountdown";
import { RouteComponentProps } from "react-router-dom";

const { Content } = Layout;
const { Title, Text, Link } = Typography;

interface Props extends RouteComponentProps<any> {
  knockout: any;
  isPicked: boolean;
  isKnockedOutOf: boolean;
}

const HomeContainer: React.FC<Props> = (props) => {
  const [modalShown, setModalShown] = React.useState(false);
  const [allTeams, setAllTeams] = React.useState([]);

  React.useEffect(() => {
    let subscribed: boolean = true;
    GET_ALL_TEAMS().then((response: any) => {
      if (subscribed) {
        if (response.status === 200) {
          setAllTeams(response.data);
        }
      }
    });

    return function cleanup() {};
  }, [props.knockout]);

  if (props.knockout.winner === null) {
    return (
      <Content style={{ margin: "24px 16px 0" }}>
        <div
          className="site-layout-background"
          style={{ padding: 24, minHeight: 360 }}
        >
          <Typography>
            <Title>{props.knockout.name}</Title>

            <Title level={2}>
              Round {props.knockout.currentRound.roundNumber}
            </Title>

            {!props.isKnockedOutOf ? (
              props.isPicked ? (
                <>
                  <Text
                    style={{
                      color: "green",
                    }}
                  >
                    <CheckCircleOutlined
                      style={{
                        paddingRight: "0.5vw",
                      }}
                    />
                    You have already picked a team for Round{" "}
                    {props.knockout.currentRound.roundNumber} - there's{" "}
                    <RoundCountdown
                      calculateTimeLeft={calculateDiff}
                      round={props.knockout.currentRound}
                    />{" "}
                    left to pick!
                  </Text>

                  <br />
                  <Text>
                    <Link onClick={() => {
                      props.history.push({
                        pathname: `/app/home/fixture/${props.knockout.currentRound.roundNumber}`,
                        state: props.knockout.currentRound.matchesForMatchweek
                      })
                    }} target="_blank">
                      Click to see this week's fixtures
                    </Link>
                  </Text>
                </>
              ) : (
                <Text type="danger">
                  <CloseCircleOutlined
                    style={{
                      paddingRight: "0.5vw",
                    }}
                  />
                  You have not yet picked a team for Round{" "}
                  {props.knockout.currentRound.roundNumber} - there's{" "}
                  <RoundCountdown
                    calculateTimeLeft={calculateDiff}
                    round={props.knockout.currentRound}
                  />{" "}
                  left to pick!
                </Text>
              )
            ) : (
              <Text type="danger">
                <CloseCircleOutlined
                  style={{
                    paddingRight: "0.5vw",
                  }}
                />
                You have been knocked out of {props.knockout.name}!
              </Text>
            )}

            <br />
            <br />

            {!props.isKnockedOutOf ? (
              <div
                style={{
                  width: "60vw",
                  margin: 0,
                }}
              >
                <Row>
                  <Col flex="1 1 200px">
                    <Button
                      style={{
                        minWidth: "100%",
                        marginBottom: "1vh",
                        marginTop: "1vh",
                      }}
                      disabled={props.isPicked}
                      onClick={() => setModalShown(true)}
                    >
                      Pick Team for Round{" "}
                      {props.knockout.currentRound.roundNumber}
                    </Button>
                  </Col>
                </Row>
                <Row>
                  <Col flex="1 1 200px">
                    <Button
                      style={{
                        minWidth: "100%",
                        marginBottom: "1vh",
                        marginTop: "1vh",
                      }}
                      type="primary"
                      onClick={() =>
                        props.history.push(
                          `/app/home/rounds/${props.knockout.name}`
                        )
                      }
                    >
                      Go to All Rounds
                    </Button>
                  </Col>
                </Row>
                <Modal
                  title={`${props.knockout.name} selection - Round ${props.knockout.currentRound.roundNumber}`}
                  centered
                  visible={modalShown}
                  footer={false}
                  onOk={() => {}}
                  width={"90vw"}
                  onCancel={() => setModalShown(false)}
                >
                  <PickContainer
                    closeModal={() => setModalShown(false)}
                    allTeams={allTeams}
                    knockoutId={props.knockout.id}
                  />
                </Modal>
              </div>
            ) : undefined}

            <Divider />

            <Title level={2}>Other Participants</Title>
            {props.knockout.participants.map((player: any, index: number) => (
              <Title level={4} key={index}>
                {player.username}
              </Title>
            ))}
          </Typography>
        </div>
      </Content>
    );
  }

  return (
    <Content
      style={{
        margin: "24px 16px 0",
        color: "green",
        verticalAlign: "middle",
        textAlign: "center",
      }}
    >
      <div className="site-layout-background">
        {props.knockout.winner.id === localStorage.getItem("userId") ? (
          <div
            style={{
              paddingTop: "10%",
              paddingBottom: "10%",
              height: "100%",
              verticalAlign: "middle",
              textAlign: "center",
            }}
          >
            <TrophyOutlined
              style={{
                fontSize: "15vw",
                color: "green",
                verticalAlign: "middle",
                textAlign: "center",
              }}
            />
            <br />
            <h1
              style={{
                color: "green",
              }}
            >
              You won {props.knockout.name}!!
            </h1>
          </div>
        ) : (
          <>
            <div
              style={{
                paddingTop: "10%",
                paddingBottom: "10%",
                height: "100%",
                verticalAlign: "middle",
                textAlign: "center",
              }}
            >
              <CloseCircleOutlined
                style={{
                  fontSize: "15vw",
                  color: "red",
                  verticalAlign: "middle",
                  textAlign: "center",
                }}
              />
              <br />
              <h1
                style={{
                  color: "red",
                }}
              >
                You lost {props.knockout.name}
              </h1>
            </div>
          </>
        )}
      </div>
    </Content>
  );
};

function calculateDiff(knockout: any): any {
  let difference: any = getDifference(knockout);

  let timeLeft: any = {};

  if (difference > 0) {
    timeLeft = {
      days: Math.floor(difference / (1000 * 60 * 60 * 24)),
      hours: Math.floor((difference / (1000 * 60 * 60)) % 24),
      minutes: Math.floor((difference / 1000 / 60) % 60),
      seconds: Math.floor((difference / 1000) % 60)
        .toString()
        .padStart(2, "0"),
    };
  }

  if (timeLeft.days === undefined) {
    timeLeft.days = 0;
    timeLeft.hours = 0;
    timeLeft.minutes = "00";
    timeLeft.seconds = "00";
  }

  return timeLeft;
}

function getDifference(currentRound: any): number {
  let eventStartTime = new Date();
  let eventEndTime = new Date(currentRound.pickDeadline);

  return eventEndTime.valueOf() - eventStartTime.valueOf();
}

export default HomeContainer;
