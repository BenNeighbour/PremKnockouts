import * as React from 'react';
import { Route, Redirect } from 'react-router-dom';

interface Props {
    component: any,
    path: string
}

const LoginRoute: React.FC<Props> = (props) => {
    const { component: Component, ...rest } = props;

    return (
        <Route
            {...rest}
            exact
            render={(props) => localStorage.getItem("isAuthenticated") !== "true"
                ?
                <Component {...props} />
                : <Redirect to={{ pathname: "/app/home", state: { from: props.location } }} />}
        />
    );
}

export default LoginRoute;