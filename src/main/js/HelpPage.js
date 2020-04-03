import React from 'react';
import Card from '@material-ui/core/Card';
import Typography from '@material-ui/core/Typography';

const HelpPage  = () => {
    return (
        <Card> 
            <Typography variant="h3" component="h2">
                Welcome to the Help Page.
            </Typography>
            <br />
            <Typography variant="h4" component="h2">
                Here are some frequently asked questions:
            </Typography>
            <br />
            <Typography variant="h5" component="h2">
                How do I create a survey?
            </Typography>
            <Typography variant="h6" component="h2">
                In order to create a survey, the user can click on the "CREATE" button located in the toolbar at the top of the page. The user then enters the name of their survey. The user then adds questions to the survey by first selecting a question type from the dropdown menu, then clicking the plus sign. The user then enters the question into the title text field. The user can alternatively add questions into specific locations into the survey by clicking the plus sign button next to a question to insert a question directly after the selected question. A user may also delete the question by selecting the trash can button next to it. Once the user is satisfied with their survey they may click the "CREATE" button next to the survey name to finish creating their survey. Once your survey is created, you will be displayed a link to your survey. Send this link to your friends and they will be brought directly to the survey viewing page for your new survey.
            </Typography>
            <br />
            <Typography variant="h5" component="h2">
                How do I view the created survey(s)?
            </Typography>
            <Typography variant="h6" component="h2">
                The primary way to view the survey(s) is by visiting a link you got from your friend. When anyone creates a survey, it will generate a link for easy viewing of the survey. Another way to view the created survey(s) is to simply click on the "VIEW SURVEYS" button in the toolbar at the top of the page. Then enter/select your survey from the "Survey Name" text field. After that, select the "RETRIEVE SURVEY" button next to it. The survey with all of its questions should now be displayed.
            </Typography>
            <br />
            <Typography variant="h5" component="h2">
                How do I answer questions in a survey?
            </Typography>
            <Typography variant="h6" component="h2">
                Follow the steps to view a survey first. Then proceed to enter your answers next to the questions. Once you've answered all the questions click the "SUBMIT ANSWERS" button.
            </Typography>
            <br />
            <Typography variant="h5" component="h2">
                How do I display the results of a survey?
            </Typography>
            <Typography variant="h6" component="h2">
                Follow the steps to view a survey first. Then proceed to click the "SUMMARISE SURVEY" button. You may close the survey after you are done with the "CLOSE SURVEY" button.
            </Typography>
            <br />
            <Typography variant="h5" component="h2">
                How do I delete a survey?
            </Typography>
            <Typography variant="h6" component="h2">
                Follow the steps to view a survey first. Then click on the "DELETE SURVEY" button.
            </Typography> 
      </Card>
    );
};


export default HelpPage;