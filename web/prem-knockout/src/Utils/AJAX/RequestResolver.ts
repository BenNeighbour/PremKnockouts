import axios from "axios";
import * as Constants from "./../Constants/Constants";

function getCsrfTokenFromCookie(cookies: string): string {
  return `${cookies
    .split("; ")
    .find((row) => row.startsWith("XSRF-TOKEN"))}`.split("=")[1];
}

export function POST_LOGIN(username: string, password: string): any {
  return axios.post(
    `${Constants.SERVER_ADDRESS}/api/auth/login`,
    {
      username: username,
      password: password,
    },
    {
      withCredentials: true,
      headers: { "X-XSRF-TOKEN": getCsrfTokenFromCookie(document.cookie) },
    }
  );
}

export function REFRESH_TOKEN(): any {
  return axios.post(
    `${Constants.SERVER_ADDRESS}/api/auth/refresh`,
    {},
    {
      withCredentials: true,
      headers: { "X-XSRF-TOKEN": getCsrfTokenFromCookie(document.cookie) },
    }
  );
}

export function GET_USER_DETAILS(): any {
  return axios.get(`${Constants.SERVER_ADDRESS}/api/auth/me`, {
    withCredentials: true,
    headers: { "X-XSRF-TOKEN": getCsrfTokenFromCookie(document.cookie) },
  });
}

export function GET_USER_KNOCKOUTS(): any {
  return axios.get(`${Constants.SERVER_ADDRESS}/api/user/knockouts/all/`, {
    withCredentials: true,
    headers: { "X-XSRF-TOKEN": getCsrfTokenFromCookie(document.cookie) },
    params: { userId: localStorage.getItem("userId") },
  });
}

export function GET_KNOCKOUT_BY_NAME(knockoutName: string): any {
  return axios.get(`${Constants.SERVER_ADDRESS}/api/knockout/by/name/`, {
    withCredentials: true,
    headers: { "X-XSRF-TOKEN": getCsrfTokenFromCookie(document.cookie) },
    params: { knockoutName: knockoutName },
  });
}

export function GET_ALLOWED_PICKS(knockoutId: string): any {
  return axios.get(`${Constants.SERVER_ADDRESS}/api/knockout/picks/allowed/`, {
    withCredentials: true,
    headers: { "X-XSRF-TOKEN": getCsrfTokenFromCookie(document.cookie) },
    params: { userId: localStorage.getItem("userId"), knockoutId: knockoutId },
  });
}

export function POST_PICK_TEAM(knockoutId: string, teamId: string): any {
  return axios.post(
    `${Constants.SERVER_ADDRESS}/api/knockout/picks/pick/`,
    {},
    {
      withCredentials: true,
      headers: { "X-XSRF-TOKEN": getCsrfTokenFromCookie(document.cookie) },
      params: {
        userId: localStorage.getItem("userId"),
        knockoutId: knockoutId,
        teamId: teamId,
      },
    }
  );
}

export function GET_ALL_TEAMS(): any {
  return axios.get(`${Constants.SERVER_ADDRESS}/api/teams/all/`, {
    withCredentials: true,
    headers: { "X-XSRF-TOKEN": getCsrfTokenFromCookie(document.cookie) },
  });
}

export function POST_USER_LOGOUT(): any {
  return axios.post(
    `${Constants.SERVER_ADDRESS}/api/auth/logout/`,
    {},
    {
      withCredentials: true,
      headers: { "X-XSRF-TOKEN": getCsrfTokenFromCookie(document.cookie) },
    }
  );
}

export function loadCsrfToken(): void {
  axios.post(
    `${Constants.SERVER_ADDRESS}/api/auth/onboarding/`,
    {},
    { withCredentials: true }
  );
}
