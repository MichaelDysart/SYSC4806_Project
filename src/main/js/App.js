import React from 'react';
import {
    BrowserRouter as Router,
    Switch,
    Route,
} from "react-router-dom";
import SurveyPage from './SurveyPage';

const App = () => {
    return (
        <Router>
            <Switch>
                <Route path="/">
                    <SurveyPage />
                </Route>
            </Switch>
        </Router>
    );
};

export default App;
