import React, { useState, useEffect } from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import InputLabel from '@material-ui/core/InputLabel';
import './App.scss';

const qType = {
    OPEN_ENDED: "openEnded",
    NUMERICAL: "numberQuestion",
};

/*
 * The frontend core which renders all subcomponents for surveys
 */
const App = () => {
    const [webUrl, setWebUrl] = useState('');
    const [surveyName, setSurveyName] = useState('');
    const [questions, setQuestions] = useState([]);
    const [currentType, setCurrentType] = useState('');

    // useEffect with no dependencies is equal to $(document).ready
    // for the component in context
    useEffect(() => {
        setWebUrl(window.location.href);
    }, []);

    // Test function
    // @TODO remove
    const myFunc1 = () => {
        const survey = {
            name : "survey1",
            questions : [ { type: qType.OPEN_ENDED, question: "q1" }, { type: qType.OPEN_ENDED, question: "q2" } ]
        };

        fetch(`${webUrl}createSurvey`, {
            method: 'POST',
            body: JSON.stringify(survey),
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then(res => res.json()) // Asynchronously get response JSON
        .then(display)
        .catch(display);
    };

    // Test function
    // @TODO remove
    const myFunc2 = () => {
        console.log(`${webUrl}retrieveSurvey?name=${surveyName}`);
        fetch(`${webUrl}retrieveSurvey?name=${surveyName}`)
        .then(res => res.json())
        .then(display)
        .catch(display);
    };

    // Test function
    // @TODO remove
    const display = (data) => {
        console.log(data);
    };

    /*
    * The handler to add a new question to the end of the
    * survey question list
    */
    const addQuestion = () => {
        switch(currentType) {
            case qType.OPEN_ENDED:
                setQuestions([
                    ...questions,
                    {
                        type: currentType,
                        question: '',
                    },
                ]);
                break;
            case qType.NUMERICAL:
                setQuestions([
                    ...questions,
                    {
                        type: currentType,
                        min: 0,
                        max: 0,
                        question: '',
                    },
                ]);
                break;
            default:
                console.log(`[WARNING] Unknown question type "${currentType}"`)
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
    * Store a new survey on the server
    */
    const createSurvey = () => {
        const survey = {
            name: surveyName,
            questions,
        };
        
        fetch(`${webUrl}createSurvey`, {
            method: 'POST',
            body: JSON.stringify(survey),
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then(res => res.json())
        .then(display)
        .catch(display);
    };

    return (
        <div className="qq-app">
            <h2>Question Quail</h2>
            <div>
                <TextField
                    className="qq-app mv"
                    variant="outlined"
                    label="Survey Name"
                    size="small"
                    onChange={e => setSurveyName(e.target.value)}
                />
                <Button className="qq-app m" variant="contained" color="primary" onClick={createSurvey}>Create</Button>
            </div>
            <div>
                <FormControl className="qq-app mv qq-app__qtype_select">
                    <InputLabel id="qtype_select_label">Question Type</InputLabel>
                    <Select labelId="qtype_select_label" value={currentType} onChange={e => setCurrentType(e.target.value)}>
                        <MenuItem value={qType.OPEN_ENDED}>Open-Ended</MenuItem>
                        <MenuItem value={qType.NUMERICAL}>Numerical</MenuItem>
                    </Select>
                </FormControl>
                <Button className="qq-app m" variant="contained" color="primary" disabled={!currentType} onClick={addQuestion}>+</Button>
            </div>
            <div>
                <Button className="qq-app mv" variant="contained" color="primary" onClick={myFunc1}>Dev Test 1 (POST)</Button>
                <Button className="qq-app m" variant="contained" color="primary" onClick={myFunc2}>Dev Test 2 (GET)</Button>
            </div>
            <div>
                {questions.map((q, i) => {
                    switch(q.type) {
                        case qType.OPEN_ENDED:
                            return (
                                <div className="qq-app mv" key={i}>
                                    <div>{`Question ${i + 1} - Open Ended`}</div>
                                    <TextField
                                        value={questions[i].question}
                                        variant="outlined"
                                        label="Question"
                                        size="small"
                                        onChange={e => updateQuestion(i, { ...q, question: e.target.value })}
                                    />
                                </div>
                            );
                        case qType.NUMERICAL:
                            // @TODO: Add min/max
                            return (
                                <div className="qq-app mv" key={i}>
                                    <div>{`Question ${i + 1} - Numerical`}</div>
                                    <TextField
                                        className="qq-app mv"
                                        value={questions[i].question}
                                        variant="outlined"
                                        label="Question"
                                        size="small"
                                        onChange={e => updateQuestion(i, { ...q, question: e.target.value })}
                                    />
                                </div>
                            );
                        default:
                            console.log(`[WARNING] Unknown question type "${q.question}"`)
                            return (<div />);
                    };
                })}
            </div>
        </div>
    );
};

export default App;
