import * as React from "react";
import { RouteComponentProps } from "react-router-dom";
import { Typography, Layout } from "antd";
import { CheckCircleOutlined, CloseCircleOutlined } from "@ant-design/icons";

const { Content } = Layout;
const { Title, Text } = Typography;

interface Props extends RouteComponentProps<any> {
  state: any;
}

const RoundDetailsContainer: React.FC<Props> = (props) => {
  const [
    knockedOutParticipants,
    setKnockedOutParticipants,
  ]: any = React.useState([]);
  const [participantsThrough, setParticipantsThrough]: any = React.useState([]);

  React.useEffect(() => {
    // For each participant in the picked list, make them through to the next round
    if (props.state !== undefined) {
      let allParticipants: Array<any> = props.state.participants;
      let participantsThrough: Array<any> = allocateParticipantsPicked(
        props.state
      );
      let knockedOutParticipants: Array<any> = allocateKnockedOutParticipants(
        allParticipants,
        participantsThrough
      );

      setKnockedOutParticipants(knockedOutParticipants);
      setParticipantsThrough(participantsThrough);
    }
  }, [props.state]);
  
  return (
    <Content style={{ margin: "24px 16px 0" }}>
      <div
        className="site-layout-background"
        style={{ padding: 24, minHeight: 360 }}
      >
        <Typography>
          <Title>Events</Title>

          <Title level={3}>Got Through</Title>
          {participantsThrough.map((user: any, index: number) => (
            <React.Fragment key={index}>
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
                {user.username}
              </Text>
              <br />
            </React.Fragment>
          ))}

          <br />
          <Title level={3}>Knocked Out</Title>
          {knockedOutParticipants.map((user: any, index: number) => (
            <React.Fragment key={index}>
              <Text
                style={{
                  color: "red",
                }}
              >
                <CloseCircleOutlined
                  style={{
                    paddingRight: "0.5vw",
                  }}
                />
                {user.username}
              </Text>
              <br />
            </React.Fragment>
          ))}
        </Typography>
      </div>
    </Content>
  );
};

function allocateKnockedOutParticipants(
  allParticipants: Array<any>,
  participantsThrough: Array<any>
): Array<any> {
  let knockedOutParticipants: Array<any> = [];

  for (let i: number = 0; i < allParticipants.length; i++) {
    // // For each participant, check wheather they have not been knocked out
    if (!participantsThrough.includes(allParticipants[i])) {
      // Push this participant to the knockedOutParticipants
      knockedOutParticipants.push(allParticipants[i]);
    }
  }

  return knockedOutParticipants;
}

function allocateParticipantsPicked(state: any): Array<any> {
  let participantsThrough: Array<any> = [];

  if (state.allRounds[state.round.roundNumber] !== undefined) {
    let nextRound: any = state.allRounds[state.round.roundNumber];

    for (let i: number = 0; i < nextRound.picks.length; i++) {
      // For each pick, get the user that picked it, adding it to the array above
      participantsThrough.push(nextRound.picks[i].user);
    }
  }

  return participantsThrough;
}

export default RoundDetailsContainer;
