import React, { useState, useEffect } from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Card from '@material-ui/core/Card';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import InputLabel from '@material-ui/core/InputLabel';
import DeleteIcon from '@material-ui/icons/Delete';
import Tooltip from '@material-ui/core/Tooltip';
import HelpIcon from '@material-ui/icons/Help';
import './App.scss';

const qType = {
    OPEN_ENDED: "openEnded",
    NUMERICAL: "numberQuestion",
    DROPDOWN: "dropdown",
};

/*
 * The frontend core which renders all subcomponents for surveys
 */
const App = () => {
    const [webUrl, setWebUrl] = useState('');
    const [consoleText, setConsoleText] = useState('');
    const [surveyName, setSurveyName] = useState('');
    const [questions, setQuestions] = useState([]);
    const [currentType, setCurrentType] = useState('');
    const [userSurvey, setUserSurvey] = useState({ id : null, closed : false, questions : [], answers : [] });
    const [userSurveyId, setUserSurveyId] = useState('');
    const [userSurveyList, setUserSurveyList] = useState({ nameList : [], idList : [] });

    // useEffect with no dependencies is equal to $(document).ready
    // for the component in context
    useEffect(() => {
        setWebUrl(window.location.href);
    }, []);

    // Display to the console
    const display = (data) => {
        setConsoleText(consoleText + "\n" + data);
        console.log(data);
    };

    /*
    * Add a new question to the end of the survey question list
    */
    const addQuestion = () => {
        addQuestionAtIndex(questions.length);
    };

    const checkRequest = (res) => {
        if (res.status === 200) {
            return res.json();
        } else {
            throw res;
        }
    };

    const addQuestionAtIndex = (i) => {
        switch(currentType) {
            case qType.OPEN_ENDED:
                setQuestions([
                    ...questions.slice(0, i),
                    {
                        type: currentType,
                        question: '',
                    },
                    ...questions.slice(i, questions.length)
                ]);
                break;
            case qType.NUMERICAL:
                setQuestions([
                    ...questions.slice(0, i),
                    {
                        type: currentType,
                        min: 0,
                        max: 0,
                        question: '',
                    },
                    ...questions.slice(i, questions.length)
                ]);
                break;
            case qType.DROPDOWN:
                setQuestions([
                    ...questions.slice(0, i),
                    {
                        type: currentType,
                        options: [],
                        question: '',
                    },
                    ...questions.slice(i, questions.length)
                ]);
                break;
            default:
        }
    };

    /*
    * A helper function to update the array of
    * current questions when a change is made
    */
    const updateQuestion = (i, newObjVal) => {
        setQuestions(questions.map((q, current) => {
            if (current === i) {
                return newObjVal;
            }
            return q;
        }));
    };

    /*
    * Delete a question at index i
    */
    const deleteQuestion = (i) => {
        setQuestions(questions.filter((q, current) => {
            if (current === i) {
                return false;
            }
            return true;
        }));
    };

    /*
    * Store a new survey on the server
    */
    const createSurvey = () => {
        const survey = {
            name: surveyName,
            questions: questions,
        };

        return fetch(`${webUrl}createSurvey`, {
            method: 'POST',
            body: JSON.stringify(survey),
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then(checkRequest)
        .then(data => {
            if (data.message === "ok") {
                setConsoleText(consoleText + "\nSurvey " + survey.name + " Created with ID: " + data.id);

                // Retrieve survey names after creating a new survey
                retrieveSurveyNames();
            } else {
                setConsoleText(consoleText + "\nSurvey Creation Error: " + data.content);
            }
        })
        .catch(console.log);
    };

    const deleteSurvey = () => {
        return fetch(`${webUrl}survey/${userSurveyId}`, {
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
        return fetch(`${webUrl}retrieveSurvey?id=${userSurveyId}`)
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

    const retrieveSurveyNames = () => {
            return fetch(`${webUrl}retrieveSurveyNames`)
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
        return fetch(`${webUrl}addAnswers`, {
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
        console.log(survey);
        fetch(`${webUrl}closeSurvey`, {
            method: 'POST',
            body: JSON.stringify(survey),
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then(checkRequest)
        .then(data => {
            console.log(data);
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
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h4">Question Quail</Typography>
                </Toolbar>
            </AppBar>
            <Card className="qq-app qq-app__container">
                <div className="content-group">
                    <div>
                        <TextField
                            label="Survey Name"
                            className="qq-app mv"
                            variant="outlined"
                            size="small"
                            onChange={e => setSurveyName(e.target.value)}
                        />
                        <Button label="Create Survey" className="qq-app m" variant="contained" color="primary" onClick={createSurvey}>Create</Button>
                    </div>
                    <div>
                        <FormControl className="qq-app mv qq-app__qtype_select">
                            <InputLabel id="qtype_select_label">Question Type</InputLabel>
                            <Select labelId="qtype_select_label" value={currentType} onChange={e => setCurrentType(e.target.value)}>
                                <MenuItem value={qType.OPEN_ENDED}>Open-Ended</MenuItem>
                                <MenuItem value={qType.NUMERICAL}>Numerical</MenuItem>
                                <MenuItem value={qType.DROPDOWN}>Dropdown</MenuItem>
                            </Select>
                        </FormControl>
                        <Button className="qq-app m"
                            label="Add Question" variant="contained" color="primary" disabled={!currentType} onClick={addQuestion}>+</Button>
                    </div>
                    <div>
                        {questions.map((q, i) => {
                            switch(q.type) {
                                case qType.OPEN_ENDED:
                                    return (
                                        <div className="qq-app mv" key={i} label="Open Question Input">
                                            <div>{`Question ${i + 1} - Open Ended`}</div>
                                            <TextField
                                                className="qq-app mv"
                                                value={questions[i].question}
                                                variant="outlined"
                                                label="Title"
                                                size="small"
                                                onChange={e => updateQuestion(i, { ...q, question: e.target.value })}
                                            />
                                            <Button
                                                className="qq-app m"
                                                variant="contained"
                                                color="primary"
                                                onClick={() => addQuestionAtIndex(i)}
                                            >+</Button>
                                            <Button
                                                className="qq-app m"
                                                variant="contained"
                                                color="primary"
                                                onClick={() => deleteQuestion(i)}
                                            ><DeleteIcon /></Button>
                                        </div>
                                    );
                                case qType.NUMERICAL:
                                    return (
                                        <div className="qq-app mv" key={i} label="Number Question Input">
                                            <div>{`Question ${i + 1} - Numerical`}</div>
                                            <TextField
                                                className="qq-app mv"
                                                value={questions[i].question}
                                                variant="outlined"
                                                label="Title"
                                                size="small"
                                                onChange={e => updateQuestion(i, { ...q, question: e.target.value })}
                                            />
                                            <TextField
                                                className="qq-app m qq-app__number_input"
                                                value={questions[i].min}
                                                variant="outlined"
                                                label="Minimum"
                                                size="small"
                                                onChange={e => updateQuestion(i, { ...q, min: parseInt(e.target.value) || 0 })}
                                            />
                                            <TextField
                                                className="qq-app m qq-app__number_input"
                                                value={questions[i].max}
                                                variant="outlined"
                                                label="Maximum"
                                                size="small"
                                                onChange={e => updateQuestion(i, { ...q, max: parseInt(e.target.value) || 0 })}
                                            />
                                            <Button
                                                className="qq-app m"
                                                variant="contained"
                                                color="primary"
                                                onClick={() => addQuestionAtIndex(i)}
                                            >+</Button>
                                            <Button
                                                className="qq-app m"
                                                variant="contained"
                                                color="primary"
                                                onClick={() => deleteQuestion(i)}
                                            ><DeleteIcon /></Button>
                                        </div>
                                    );
                                case qType.DROPDOWN:
                                    return (
                                        <div className="qq-app mv" key={i} label="Dropdown Question Input">
                                            <div>{`Question ${i + 1} - Dropdown`}</div>
                                            <TextField
                                                className="qq-app m"
                                                value={questions[i].question}
                                                variant="outlined"
                                                label="Title"
                                                size="small"
                                                onChange={e => updateQuestion(i, { ...q, question: e.target.value })}
                                            />
                                            <TextField
                                                className="qq-app m"
                                                value={questions[i].options}
                                                variant="outlined"
                                                label="Options"
                                                size="small"
                                                // For dropdowns, split the input before sending it
                                                onChange={e => updateQuestion(i, { ...q, options: e.target.value.split(',') })}
                                            />
                                            <Button
                                                className="qq-app m"
                                                variant="contained"
                                                color="primary"
                                                onClick={() => addQuestionAtIndex(i)}
                                            >+</Button>
                                            <Button
                                                className="qq-app m"
                                                variant="contained"
                                                color="primary"
                                                onClick={() => deleteQuestion(i)}
                                            ><DeleteIcon /></Button>
                                            <Tooltip title="Separate dropdown options using commas">
                                                <HelpIcon />
                                            </Tooltip>
                                        </div>
                                    );
                                default:
                                    return (<div />);
                            };
                        })}
                    </div>
                </div>
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
                                            <MenuItem value={id} key={i}>{`${userSurveyList.nameList[i]} : ${id}`}</MenuItem>
                                        )
                                    })
                                }
                                </Select>
                         </FormControl>
                         <Button className="qq-app m" variant="contained" color="primary" onClick={retrieveSurvey}>Retrieve Survey</Button>
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
                                            <div>{`Question ${i + 1} - Numerical`}</div>
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
                                    return (<div />);
                            };
                        })}
                    </div>
                </div>
                <div>
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

export default App;
