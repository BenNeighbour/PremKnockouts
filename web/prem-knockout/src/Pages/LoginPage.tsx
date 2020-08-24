import * as React from "react";
import "./LoginPage.css";
import Form from "./../Components/Login/LoginForm";
import { withRouter, RouteComponentProps } from "react-router-dom";
import { POST_LOGIN, loadCsrfToken } from "./../Utils/AJAX/RequestResolver";

interface Props extends RouteComponentProps<any> {}

let LoginPage: React.FC<Props> = (props) => {
  const [errors, setErrors] = React.useState("");

  React.useEffect(() => {
    loadCsrfToken();
  }, []);

  return (
    <>
      <Form
        errors={errors}
        onSubmit={(vals) => {
          // Do form submission
          POST_LOGIN(vals.username, vals.password)
            .then((res: any) => {
              // Set isAutheticated to true
              if (res.data.accessToken !== null) {
                localStorage.setItem("isAuthenticated", "true");
                props.history.push("/app/home");
              }
            })
            .catch((err: any) => {
              // TODO: Compare status with Constants tied to strings
              if (err.response.status === 401 || err.response.status === 400) {
                setErrors("Bad Credentials");
              }
            });
        }}
      />
    </>
  );
};

export default withRouter(LoginPage);
