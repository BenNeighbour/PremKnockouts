import React from "react";
import ReactDOM from "react-dom";
import AppRoutes from "./AppRoutes";
import * as serviceWorker from "./serviceWorker";
import { BrowserRouter } from "react-router-dom";
import theme from "./Utils/Theme/ThemeIndex";
import { ThemeProvider } from "@material-ui/core";

ReactDOM.render(
  <ThemeProvider theme={theme}>
    <BrowserRouter>
      <AppRoutes />
    </BrowserRouter>
  </ThemeProvider>,
  document.getElementById("root")
);

serviceWorker.unregister();
