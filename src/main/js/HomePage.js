import React from 'react';
import Card from '@material-ui/core/Card';
import Typography from '@material-ui/core/Typography';

const HomePage  = () => {
    return (
        <Card className="qq-app qq-app__home m">
            <Typography className="m" variant="h3" component="h2">
                Welcome to Question Quail! 
            </Typography>
            <Typography className="m" variant="h6" component="h6">
                {`Use the toolbar above to navigate around the site.\n
                You can create a survey and then send the link to your friends for them to fill it out!\n
                Or go to the view page to see a list of available surveys.\n
                Visit the help page for more information!`}
            </Typography>
      </Card>
    );
};


export default HomePage;
