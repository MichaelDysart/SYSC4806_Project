import React from 'react';
import {
    HashRouter,
    Switch,
    Route,
} from "react-router-dom";
import SurveyPage from './SurveyPage';

const App = () => {
    return (
        <HashRouter>
            <Switch>
                <Route exact path="/" component={SurveyPage} />
                <Route path="/survey/:uuid" component={SurveyPage} />
            </Switch>
        </HashRouter>
    );
};

export default App;
