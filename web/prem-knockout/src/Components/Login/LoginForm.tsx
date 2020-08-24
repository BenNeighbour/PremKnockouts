import * as React from "react";
import { Formik, Form } from "formik";
import {
  TextField,
  Grid,
  Button,
  Theme,
  makeStyles,
  createStyles,
  Typography,
  FormHelperText,
} from "@material-ui/core";

interface FormValues {
  username: string;
  password: string;
}

interface Props {
  onSubmit: (values: FormValues) => void;
  errors: string;
}

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      backgroundColor: theme.palette.background.default,
      height: "100%",
      overflowY: "scroll",
    },
    grid: {
      height: "100%",
    },
    quoteContainer: {
      [theme.breakpoints.down("md")]: {
        display: "none",
      },
    },
    quote: {
      height: "100%",
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      backgroundSize: "cover",
      backgroundRepeat: "no-repeat",
      backgroundPosition: "center",
    },
    quoteInner: {
      textAlign: "center",
      flexBasis: "600px",
    },
    quoteText: {
      fontWeight: 300,
    },
    name: {
      marginTop: theme.spacing(3),
    },
    content: {
      height: "100%",
      display: "flex",
      flexDirection: "column",
    },
    contentHeader: {
      display: "flex",
      alignItems: "center",
      paddingTop: theme.spacing(5),
      paddingBototm: theme.spacing(2),
      paddingLeft: theme.spacing(2),
      paddingRight: theme.spacing(2),
    },
    logoImage: {
      marginLeft: theme.spacing(4),
    },
    contentBody: {
      flexGrow: 1,
      display: "flex",
      alignItems: "center",
      [theme.breakpoints.down("md")]: {
        justifyContent: "center",
      },
    },
    form: {
      paddingLeft: 100,
      paddingRight: 100,
      paddingBottom: 125,
      flexBasis: 700,
      [theme.breakpoints.down("sm")]: {
        paddingLeft: theme.spacing(2),
        paddingRight: theme.spacing(2),
      },
    },
    title: {
      marginTop: theme.spacing(3),
    },
    textField: {
      marginTop: theme.spacing(2),
    },
    policy: {
      marginTop: theme.spacing(1),
      display: "flex",
      alignItems: "center",
    },
    policyCheckbox: {
      marginLeft: "-14px",
    },
    loginButton: {
      margin: theme.spacing(2, 0),
    },
  })
);

const LoginForm: React.FC<Props> = ({ onSubmit, errors }) => {
  const classes = useStyles();

  return (
    <Formik
      initialValues={{
        username: "",
        password: "",
      }}
      onSubmit={(formValues) => {
        onSubmit(formValues);
      }}
    >
      {({ values, handleChange }) => (
        <div className={classes.root}>
          <Grid className={classes.grid} container>
            <Grid className={classes.content} item lg={12} xs={12}>
              <div className={classes.content}>
                <div className={classes.contentBody}>
                  <Form className={classes.form}>
                    <div
                      style={{
                        textAlign: "center", 
                      }}
                    ></div>

                    <div
                      style={{ height: "fit-content", textAlign: "center" }}
                    ></div>
                    <Typography className={classes.title} variant="h2">
                      Log In
                    </Typography>

                    <Typography color="textSecondary" gutterBottom>
                      Please enter your account credentials
                    </Typography>

                    <TextField
                      fullWidth
                      variant="outlined"
                      value={values.username}
                      onChange={handleChange}
                      name="username"
                      label="Username"
                      type="text"
                      className={classes.textField}
                    />

                    <TextField
                      fullWidth
                      variant="outlined"
                      value={values.password}
                      onChange={handleChange}
                      name="password"
                      label="Password"
                      type="password"
                      className={classes.textField}
                    />

                    <FormHelperText
                      style={{
                        color: "red",
                      }}
                    >
                      {errors}
                    </FormHelperText>

                    <Button
                      className={classes.loginButton}
                      color="primary"
                      fullWidth
                      size="large"
                      type="submit"
                      variant="contained"
                    >
                      Log In Now
                    </Button>
                    <Typography color="textSecondary" variant="body1">
                      Don't have an account? <a href="/signup">Sign Up</a>
                    </Typography>
                  </Form>
                </div>
              </div>
            </Grid>
          </Grid>
        </div>
      )}
    </Formik>
  );
};

export default LoginForm;
