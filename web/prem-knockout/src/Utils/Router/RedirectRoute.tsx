import * as React from 'react';
import { Route, Redirect } from 'react-router-dom';

interface Props {
    path: string
}

const RedirectRoute: React.FC<Props> = (props) => {
    return (
        <Route
            exact
            render={() => <Redirect to={{ pathname: '/app/home' }} />}
        />
    );
}

export default RedirectRoute;