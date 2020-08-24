import * as Constants from "./Constants";

export const REFRESH_IGNORE_ROUTES: Array<string> = [
  `${Constants.SERVER_ADDRESS}/api/auth/login`,
  `${Constants.SERVER_ADDRESS}/api/auth/signup`,
];
