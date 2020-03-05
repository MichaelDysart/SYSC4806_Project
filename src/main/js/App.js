import React, { useState, useEffect } from 'react';
import Button from '@material-ui/core/Button';

const qType = {
    OPEN_ENDED: "openEnded",
};

/*
 * The frontend core which renders all subcomponents for surveys
 */
const App = () => {
    const [webUrl, setWebUrl] = useState('');
    const [surveyName, setSurveyName] = useState('');
    const [questions, setQuestions] = useState([]);
    const [currentType, setCurrentType] = useState(qType.OPEN_ENDED);

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
        setQuestions([
            ...questions,
            {
                type: currentType,
                question: '',
            },
        ]);
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
    }

    return (
        <div className="qq-app">
            <h2>Question Quail</h2>
            <div>
                <Button variant="contained" color="primary" onClick={myFunc1}>Dev Test 1 (POST)</Button>
                <Button variant="contained" color="primary" onClick={myFunc2}>Dev Test 2 (GET)</Button>
            </div>
            <div>
                <Button variant="contained" color="primary" onClick={createSurvey}>Create</Button>
                <input value={surveyName} onChange={e => setSurveyName(e.target.value)} type="text" />
                <select id="questionType">
                    <option value={qType.OPEN_ENDED} onChange={setCurrentType(qType.OPEN_ENDED)}>OpenEnded</option>
                </select>
                <Button variant="contained" color="primary" onClick={addQuestion}>+</Button>
                {questions.map((q, i) => {
                    switch(q.type) {
                        case qType.OPEN_ENDED:
                            return (
                                <div key={i}>
                                    <input
                                        value={questions[i].question}
                                        onChange={e => updateQuestion(i, { ...q, question: e.target.value })}
                                        type="text"
                                    />
                                </div>
                            );
                        default:
                            console.log(`[WARNING] Unkown question type "${q.question}"`)
                            return (<div />);
                    };
                })}
            </div>
        </div>
    );
};

export default App;
