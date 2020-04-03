import React from 'react';
import Typography from '@material-ui/core/Typography';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';
import {
    useHistory,
    HashRouter,
    Switch,
    Route,
} from "react-router-dom";
import ViewPage from './ViewPage';
import CreatePage from './CreatePage';
import HomePage from './HomePage'
import HelpPage from './HelpPage'
import './SurveyPage.scss';

const App = () => {
    return (
        <HashRouter>
            <QQAppBar/>
            <Switch>
                <Route exact path="/" component={HomePage} />
                <Route exact path="/survey" component={ViewPage} />
                <Route exact path="/create" component={CreatePage} />
                <Route exact path="/help" component={HelpPage} />
                <Route path="/survey/:uuid" component={ViewPage} />
            </Switch>
        </HashRouter>
    );
};

const QQAppBar = () => {
    
    const history = useHistory();

    function handleHome() {
        history.push("/");
    }

    function handleCreate() {
        history.push("/create");
    }

    function handleView() {
        history.push("/survey");
    }

    function handleHelp() {
        history.push("/help");
    }

    return (
        <AppBar className="qq-app" position="static">
            <Toolbar>
                <Typography variant="h4">Question Quail</Typography>
                <div className="qq-app__buttons">
                    <Button className="mh" variant="contained" onClick={handleHome} color="secondary">Home</Button>
                    <Button className="mh" variant="contained" onClick={handleCreate} color="secondary">Create</Button>
                    <Button className="mh" variant="contained" onClick={handleView} color="secondary">View Surveys</Button>
                    <Button className="mh" variant="contained" onClick={handleHelp} color="secondary">Help</Button>
                </div>
            </Toolbar>
        </AppBar>
    );
};

export default App;
