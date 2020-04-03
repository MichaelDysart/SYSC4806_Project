import React, { useState, useEffect } from 'react';
import Card from '@material-ui/core/Card';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import InputLabel from '@material-ui/core/InputLabel';
import DeleteIcon from '@material-ui/icons/Delete';
import Tooltip from '@material-ui/core/Tooltip';
import HelpIcon from '@material-ui/icons/Help';
import './SurveyPage.scss';


const qType = {
    OPEN_ENDED: "openEnded",
    NUMERICAL: "numberQuestion",
    DROPDOWN: "dropdown",
};

const CreatePage = () => {

    const [baseUrl, setBaseUrl] = useState('');
    const [consoleText, setConsoleText] = useState('');
    const [surveyName, setSurveyName] = useState('');
    const [questions, setQuestions] = useState([]);
    const [currentType, setCurrentType] = useState('');
    const [link, setLink] = useState('');

    // useEffect with no dependencies is equal to $(document).ready
    // for the component in context
    useEffect(() => {
       setBaseUrl(window.location.href.replace(/\/#.*/, "")); // This accounts for the HashRouter
    }, []);

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
              console.log(`[WARNING] Unknown question type "${currentType}"`);
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

      return fetch(`${baseUrl}/createSurvey`, {
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
              setLink(`${baseUrl}/#/survey/${data.link}`)
          } else {
              setConsoleText(consoleText + "\nSurvey Creation Error: " + data.content);
          }
      })
      .catch(console.log);
    };

    return (
        <div className="qq-app">
            <Card className="qq-app qq-app__container">
                <div className="content-group">
                    <div>
                        <TextField
                            className="qq-app mv"
                            variant="outlined"
                            label="Survey Name"
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
                                    console.log(`[WARNING] Unknown question type "${q.question}"`)
                                    return (<div />);
                            };
                        })}
                    </div>
                </div>
                {link && (
                    <TextField
                        className="mv15 link"
                        label="Link to your created survey"
                        value = {link}
                        variant="outlined"
                        disabled={true}
                        fullWidth
                    />
                )}
                <div>
                    {baseUrl.includes('localhost') &&
                        <TextField
                            className="mv15 console"
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
}

export default CreatePage;
