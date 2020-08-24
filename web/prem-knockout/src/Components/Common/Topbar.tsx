import { Avatar, Button, PageHeader, Modal } from "antd";
import * as React from "react";
import { RouteComponentProps } from "react-router-dom";
import { UserOutlined } from "@ant-design/icons";
import { POST_USER_LOGOUT } from "../../Utils/AJAX/RequestResolver";

interface Props extends RouteComponentProps<any> {
  title: string;
  subtitle?: string;
  isRootPage: boolean;
}

const Topbar: React.FC<Props> = (props) => {
  const [isSigningOut, setIsSigningOut]: any = React.useState(false);

  return (
    <>
      {props.isRootPage ? (
        <PageHeader
          style={{
            height: "fit-content",
          }}
          className="site-page-header"
          title={props.title}
          subTitle={props.subtitle}
          extra={[
            <button
              key="signout"
              onClick={() => setIsSigningOut(true)}
              style={{
                backgroundColor: "Transparent",
                backgroundRepeat: "no-repeat",
                border: "none",
                cursor: "pointer",
                overflow: "hidden",
                outline: "none",
              }}
            >
              <Avatar icon={<UserOutlined />} />
            </button>,
          ]}
        />
      ) : (
        <PageHeader
          style={{
            height: "fit-content",
          }}
          onBack={() => {
            props.history.goBack();
          }}
          className="site-page-header"
          title={props.title}
          subTitle={props.subtitle}
          extra={[
            <button
              key="signout"
              onClick={() => setIsSigningOut(true)}
              style={{
                backgroundColor: "Transparent",
                backgroundRepeat: "no-repeat",
                border: "none",
                cursor: "pointer",
                overflow: "hidden",
                outline: "none",
              }}
            >
              <Avatar icon={<UserOutlined />} />
            </button>,
          ]}
        />
      )}

      <Modal
        style={{
          textAlign: "center",
        }}
        centered
        visible={isSigningOut}
        footer={false}
        onCancel={() => setIsSigningOut(false)}
      >
        <br />
        <br />
        <Button
          style={{
            marginBottom: "2.5vh",
            width: "90%",
          }}
          onClick={() => logOut()}
        >
          Sign Out
        </Button>
        <br />
        <Button
          style={{
            marginBottom: "2.5vh",
            width: "90%",
          }}
          onClick={() => props.history.push("/app/profile/")}
        >
          My Profile
        </Button>
        <br />
        <Button
          type="primary"
          style={{
            marginBottom: "2.5vh",
            width: "90%",
          }}
          onClick={() => setIsSigningOut(false)}
        >
          Cancel
        </Button>
      </Modal>
    </>
  );
};

function logOut(): any {
  POST_USER_LOGOUT();

  let cookies: string = document.cookie;
  cookies.split("; ").find((row) => row.startsWith("XSRF-TOKEN"));

  document.cookie = cookies + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";

  localStorage.clear();
  window.location.reload();
}

export default Topbar;
