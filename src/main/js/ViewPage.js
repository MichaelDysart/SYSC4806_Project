import React, { useState, useEffect } from 'react';
import Card from '@material-ui/core/Card';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import InputLabel from '@material-ui/core/InputLabel';
import Summary from './Summary';
import './SurveyPage.scss';

const qType = {
    OPEN_ENDED: "openEnded",
    NUMERICAL: "numberQuestion",
    DROPDOWN: "dropdown",
};

/*
 * The frontend core which renders all subcomponents for surveys
 */
const ViewPage = () => {
    const [baseUrl, setBaseUrl] = useState('');
    const [consoleText, setConsoleText] = useState('');
    const [userSurvey, setUserSurvey] = useState({ id : null, closed : false, questions : [] });
    const [summarySurvey, setSummarySurvey] = useState({ id : null, closed : false, questions : [] });
    const [userSurveyId, setUserSurveyId] = useState('');
    const [userSurveyList, setUserSurveyList] = useState({ nameList : [], idList : [] });

    // TODO: Uncomment this code to obtain the UUID of the survey requested
    // const { uuid } = useParams();

    // useEffect with no dependencies is equal to $(document).ready
    // for the component in context
    useEffect(() => {
        setBaseUrl(window.location.href.replace(/\/#.*/ ,"")); // This accounts for the HashRouter
    }, []);

    const checkRequest = (res) => {
        if (res.status === 200) {
            return res.json();
        } else {
            throw res;
        }
    };

    const deleteSurvey = () => {
        return fetch(`${baseUrl}/survey/${userSurveyId}`, {
            method: 'DELETE'
        })
        .then(checkRequest)
        .then(data => {
            if(data.message === "ok") {
                setConsoleText(consoleText + "\nSurvey " + data.id + " deleted");

                if (userSurvey.id === data.id) {
                    setUserSurvey({ id : null, closed : false, questions : [] });
                }

                // Retrieve survey names after deleting a new survey
                retrieveSurveyNames();
            } else {
                setConsoleText(consoleText + "\nError: Could not delete survey with id " + data.id );
            }
        }).catch(console.log);
    };

    const retrieveSurvey = () => {
        return fetch(`${baseUrl}/retrieveSurvey?id=${userSurveyId}`)
        .then(checkRequest)
        .then(data => {
            if (data.status !== "error") {
                setConsoleText(consoleText + "\nSurvey " + data.id + " retrieved");
                setUserSurvey(data);
            } else {
                setConsoleText(consoleText + "\nError: Could not find survey with id " + data.id );
            }
        })
        .catch(console.log);
    };

    const summariseSurvey = () => {
        return fetch(`${baseUrl}/retrieveSurvey?id=${userSurveyId}`)
        .then(checkRequest)
        .then(data => {
            if (data.status !== "error") {
                setConsoleText(consoleText + "\nSurvey " + data.id + " retrieved");
                setSummarySurvey(data);
            } else {
                setConsoleText(consoleText + "\nError: Could not find survey with id " + data.id );
            }
        })
        .catch(console.log);
    };

    const retrieveSurveyNames = () => {
            return fetch(`${baseUrl}/retrieveSurveyNames`)
            .then(checkRequest)
            .then(data => {
                setUserSurveyList(data);
            })
            .catch(console.log);
    }

    const updateAnswer = (i, newObjVal) => {
        setUserSurvey(
            {
                ...userSurvey,
                questions : userSurvey.questions.map((q, current) => {
                        if (current === i) {
                            return {...q, ...newObjVal};
                        }
                        return q;
                    })
            }
        );
    };

    const submitAnswers = () => {
        const survey = {
            id : userSurvey.id,
            questions : userSurvey.questions,
        };
        return fetch(`${baseUrl}/addAnswers`, {
            method: 'POST',
            body: JSON.stringify(survey),
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then(checkRequest)
        .then(data => {
            if (data.message === "ok") {
                setConsoleText(consoleText + "\nAnswers added to survey: " + userSurvey.name + "; ID: " + data.id);
            } else {
                setConsoleText(consoleText + "\nAnswer Addition Error: " + data.content);
            }
        })
        .catch(console.log);
    };

    const closeSurvey = () => {
        const survey = {
            id : userSurvey.id,
        };
        return fetch(`${baseUrl}/closeSurvey`, {
            method: 'POST',
            body: JSON.stringify(survey),
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then(checkRequest)
        .then(data => {
            if (data.message === "ok") {
                setConsoleText(consoleText + "\nSurvey was closed: " + userSurvey.name + "; ID: " + data.id);

                if (userSurvey.id === data.id) {
                    setUserSurvey({ ...userSurvey, closed : true});
                }
            } else {
                setConsoleText(consoleText + "\nSurvey close failed: " + data.content);
            }
        })
        .catch(console.log);
    };

    return (
        <div className="qq-app">
            <Card className="qq-app qq-app__container">
                <div className="content-group">
                    <div>
                         <FormControl className="qq-app mv qq-app__survey-ids_select">
                                <InputLabel id="surveyIds_select_label">Survey Name</InputLabel>
                                <Select labelId="surveyIds_select_label" value={
                                    userSurveyId
                                }
                                    onClick={e => retrieveSurveyNames()}
                                    onChange={e => setUserSurveyId(e.target.value)}>
                                {
                                    userSurveyList.idList.map((id, i) => {
                                        return(
                                            <MenuItem key={i} value={id}>{`${userSurveyList.nameList[i]} : ${id}`}</MenuItem>
                                        )
                                    })
                                }
                                </Select>
                         </FormControl>
                         <Button className="qq-app m" variant="contained" color="primary" onClick={retrieveSurvey}>Retrieve Survey</Button>
                         <Button className="qq-app m" variant="contained" color="primary" onClick={summariseSurvey}>Summarise Survey</Button>
                    </div>
                    <div>
                        <Button className="qq-app m" variant="contained" color="primary" onClick={deleteSurvey}>Delete Survey</Button>
                        <Button className="qq-app m" variant="contained" color="primary" onClick={closeSurvey}>Close Survey</Button>
                        <Button className="qq-app m" variant="contained" color="primary" onClick={submitAnswers}>Submit Answers</Button>
                    </div>
                    <div>
                            {userSurvey.questions.map((q, i) => {
                            switch(q.type) {
                                case qType.OPEN_ENDED:
                                    return (
                                        <div className="qq-app mv" key={i}>
                                            <div>{`Question ${i + 1} - Open Ended`}</div>
                                            <TextField
                                                className="qq-app mv"
                                                value={userSurvey.questions[i].question}
                                                variant="outlined"
                                                label="Question"
                                                size="small"
                                            />
                                            <TextField
                                                className="qq-app mv"
                                                value={userSurvey.questions[i].stringAnswer}
                                                variant="outlined"
                                                label="Answer"
                                                size="small"
                                                onChange={
                                                    e => {
                                                        if (!userSurvey.closed) {
                                                            updateAnswer(i, { stringAnswer : e.target.value });
                                                        }
                                                    }
                                                }
                                            />
                                        </div>
                                    );
                                case qType.NUMERICAL:
                                    return (
                                        <div className="qq-app mv" key={i}>
                                            <div>{`Question ${i + 1} - Numerical`}</div>
                                            <TextField
                                                className="qq-app mv"
                                                value={userSurvey.questions[i].question}
                                                variant="outlined"
                                                label="Question"
                                                size="small"
                                            />
                                            <TextField
                                                className="qq-app m qq-app__number_input"
                                                value={userSurvey.questions[i].numberAnswer}
                                                variant="outlined"
                                                label="Answer"
                                                size="small"
                                                onChange= {
                                                    e => {
                                                        if (!userSurvey.closed) {
                                                            updateAnswer(i, { numberAnswer : parseInt(e.target.value) || 0 });
                                                        }
                                                    }
                                                }
                                            />
                                        </div>
                                    );
                                case qType.DROPDOWN:
                                    return (
                                        <div className="qq-app mv" key={i}>
                                            <div>{`Question ${i + 1} - Dropdown`}</div>
                                            <TextField
                                                className="qq-app mv"
                                                value={userSurvey.questions[i].question}
                                                variant="outlined"
                                                label="Question"
                                                size="small"
                                            />
                                            <FormControl className="qq-app mv qq-app__qtype_select">
                                                <InputLabel id={`label_${q.question}`}>{userSurvey.questions[i].question}</InputLabel>
                                                <Select
                                                    labelId={`label_${q.question}`}
                                                    value={userSurvey.questions[i].stringAnswer}
                                                    onChange={
                                                        e => {
                                                            if (!userSurvey.closed) {
                                                                updateAnswer(i, { stringAnswer : e.target.value });
                                                            }
                                                        }
                                                    }
                                                >
                                                    {q.options.map(option => (
                                                        <MenuItem key={option} value={option}>{option}</MenuItem>
                                                    ))}
                                                </Select>
                                            </FormControl>

                                        </div>
                                    );
                                default:
                                    console.log(`[WARNING] Unknown question type "${q.question}"`)
                                    return (<div />);
                            };
                        })}
                    </div>
                </div>
                <Summary questions={summarySurvey.questions}/>
                <div className="margins">
                    {process.env.NODE_ENV !== 'production' &&
                        <TextField
                            className="console"
                            id="Console"
                            label="Console"
                            multiline
                            value = {consoleText}
                            variant="outlined"
                            disabled={true}
                            fullWidth
                        />
                    }
                </div>
            </Card>
        </div>
    );
};

export default ViewPage;
