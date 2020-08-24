import * as React from "react";
import { Card } from "antd";
import {
  GET_ALLOWED_PICKS,
  POST_PICK_TEAM,
} from "./../../../Utils/AJAX/RequestResolver";
import Modal from "antd/lib/modal/Modal";
import { Grid } from "@material-ui/core";

const { Meta } = Card;

interface Props {
  knockoutId: string;
  allTeams: any;
  closeModal: any;
}

const PickContainer: React.FC<Props> = (props) => {
  const [teams, setTeams]: any = React.useState([]);
  const [confirmPick, setConfirmPick]: any = React.useState(false);
  const [selectedPick, setSelectedPick]: any = React.useState(null);

  const [error, setError]: any = React.useState(null);

  React.useEffect(() => {
    GET_ALLOWED_PICKS(props.knockoutId).then((response: any) => {
      if (response.status === 200) {
        setTeams(response.data);
      }
    });
  }, [props.knockoutId]);

  return (
    <div
      style={{
        display: "inline-block",
      }}
    >
      <Grid container spacing={2}>
        {teams.map((team: any, index: number) => {
          return [
            <Grid key={index} item xs={12} sm={3}>
              <Card
                hoverable={true}
                style={{ display: "inline-block" }}
                cover={<img alt="logo" src={team.crestUrl} />}
                onClick={() => {
                  setConfirmPick(true);
                  setSelectedPick(team);
                }}
              >
                <Meta title={team.name} />
              </Card>
            </Grid>,
          ];
        })}
      </Grid>

      {selectedPick !== null ? (
        <Modal
          title={`Confirm ${selectedPick.name}`}
          centered
          visible={confirmPick}
          onOk={() => {
            POST_PICK_TEAM(props.knockoutId, selectedPick.id)
              .then((response: any) => {
                if (response.status === 200) {
                  setConfirmPick(false);
                  props.closeModal();
                  window.location.reload();
                }
              })
              .catch(() => {
                setError("Something went wrong...");
              });
          }}
          onCancel={() => setConfirmPick(false)}
        >
          <p>Are you sure you would like to pick {selectedPick.name}?</p>
          <p style={{ color: "red" }}>{error}</p>
        </Modal>
      ) : undefined}
    </div>
  );
};

export default PickContainer;
