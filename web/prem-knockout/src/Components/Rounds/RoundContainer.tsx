import * as React from "react";
import { Card } from "antd";
import { Grid } from "@material-ui/core";
import { RouteComponentProps } from "react-router-dom";

interface Props extends RouteComponentProps<any> {
  knockoutName: string;
  knockout: any;
}

const RoundContainer: React.FC<Props> = (props) => {
  let allCompletedRounds: Array<any> = props.knockout.rounds;
  allCompletedRounds.pop();

  return (
    <Grid
      style={{
        padding: "24px",
      }}
      container
    >
      {props.knockout.rounds.map((round: any, index: number) => (
        <Grid key={index} item xs={12} sm={6}>
          <Card
            style={{
              marginBottom: "1vh",
              marginTop: "1vh",
              marginRight: "0.7vw",
              marginLeft: "0.7vw",
            }}
            hoverable
            onClick={() => {
              let allRounds: Array<any> = new Array(
                props.knockout.rounds
              ).pop();
              props.history.push({
                pathname: `/app/home/round/${props.knockoutName}/${round.roundNumber}`,
                state: {
                  round: round,
                  participants: props.knockout.participants,
                  allRounds: allRounds,
                },
              });
            }}
            title={`Round ${round.roundNumber}`}
          />
        </Grid>
      ))}
    </Grid>
  );
};

export default RoundContainer;
