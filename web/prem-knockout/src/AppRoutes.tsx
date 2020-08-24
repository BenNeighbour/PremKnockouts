import * as React from "react";
import { Route, Switch, Redirect } from "react-router-dom";
import "./App.css";
import ProtectedRoute from "./Utils/Router/ProtectedRoute";
import HomePage from "./Pages/HomePage";
import LoginRoute from "./Utils/Router/LoginRoute";
import LoginPage from "./Pages/LoginPage";
import RedirectRoute from "./Utils/Router/RedirectRoute";
import axios from "axios";
import { REFRESH_IGNORE_ROUTES } from "./Utils/Constants/RefreshTokenConstants";
import { REFRESH_TOKEN } from "./Utils/AJAX/RequestResolver";
import ProfilePage from "./Pages/ProfilePage";
import RulesPage from "./Pages/RulesPage";
import RoundsPage from "./Pages/RoundsPage";
import RoundDetailsPage from "./Pages/RoundDetailsPage";
import MatchesPage from './Pages/MatchesPage';

interface Props {}

const AppRoutes: React.FC<Props> = (props) => {
  axios.interceptors.response.use(
    function (response: any): any {
      return response;
    },
    async function (error: any) {
      if (
        !REFRESH_IGNORE_ROUTES.includes(error.config.url) &&
        error.response.status === 401
      ) {
        REFRESH_TOKEN()
          .then((res: any) => {
            window.location.reload();
          })
          .catch((err: any) => {
            localStorage.clear();
            return <Redirect to={{ pathname: "/login" }} />;
          });
      } else {
        return Promise.reject(error);
      }
    }
  );

  return (
    <Route>
      <Switch>
        <Route>
          <ProtectedRoute component={HomePage} path="/app/home" />
          <ProtectedRoute component={ProfilePage} path="/app/profile" />
          <ProtectedRoute component={RulesPage} path="/app/rules" />
          <ProtectedRoute
            component={RoundsPage}
            path="/app/home/rounds/:knockoutName"
          />
          <ProtectedRoute
            component={RoundDetailsPage}
            path="/app/home/round/:knockoutName/:roundNumber"
          />
          <ProtectedRoute
            component={MatchesPage}
            path="/app/home/fixture/:roundNumber"
          />
          <RedirectRoute path="/app" />
          <LoginRoute component={LoginPage} path="/login" />
        </Route>
      </Switch>
    </Route>
  );
};

export default AppRoutes;
