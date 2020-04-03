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

const App = () => {
    return (
        <HashRouter>
            <QQAppBar/>
            <Switch>
                <Route exact path="/" component={HomePage} />
                <Route exact path="/survey" component={ViewPage} />
                <Route exact path="/create" component={CreatePage} />
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

    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h4">Question Quail</Typography>
                <Button onClick={handleHome} color="inherit">Home</Button>
                <Button onClick={handleCreate} color="inherit">Create</Button>
                <Button onClick={handleView} color="inherit">View Surveys</Button>
            </Toolbar>
        </AppBar>
    );
}
export default App;
