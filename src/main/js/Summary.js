import React, { useState, useEffect } from 'react';
import TextField from '@material-ui/core/TextField';
import { Histogram, DensitySeries, BarSeries, withParentSize, XAxis, YAxis } from '@data-ui/histogram';

const qType = {
    OPEN_ENDED: "openEnded",
    NUMERICAL: "numberQuestion",
    DROPDOWN: "dropdown",
};

/*
 * The frontend core which renders all subcomponents for surveys
 */
const Summary = (props) => {
    console.log(props);

    return (
        <div className="qq-summary">
            {props.questions.map((q, i) => {
                switch(q.type) {
                    case qType.OPEN_ENDED:
                        return (
                            <div className="qq-app mv" key={i} label="Open Question Summary">
                                <div>{`Question ${i + 1} - Open Ended`}</div>
                                <TextField
                                    className="qq-app mv"
                                    value={props.questions[i].question}
                                    variant="outlined"
                                    label="Question"
                                    size="small"
                                />
                            </div>
                        );
                    case qType.NUMERICAL:
                        return (
                            <div className="qq-app mv" key={i} label="Number Question Summary">
                                <div>{`Question ${i + 1} - Numerical`}</div>
                                <TextField
                                    className="qq-app mv"
                                    value={props.questions[i].question}
                                    variant="outlined"
                                    label="Question"
                                    size="small"
                                />
                                <Histogram
                                        width={300}
                                        height={300}
                                        ariaLabel="My histogram of ..."
                                        orientation="vertical"
                                        cumulative={false}
                                        normalized={true}
                                        binCount={25}
                                        valueAccessor={datum => datum}
                                        binType="numeric"
                                        renderTooltip={({ event, datum, data, color }) => (
                                          <div>
                                            <strong style={{ color }}>{datum.bin0} to {datum.bin1}</strong>
                                            <div><strong>count </strong>{datum.count}</div>
                                            <div><strong>cumulative </strong>{datum.cumulative}</div>
                                            <div><strong>density </strong>{datum.density}</div>
                                          </div>
                                        )}
                                      >
                                      <BarSeries
                                          animated
                                          rawData={ props.questions[i].numberAnswerList }
                                      />
                                      <XAxis />
                                      <YAxis />
                                </Histogram>
                            </div>
                        );
                    case qType.DROPDOWN:
                        return (
                            <div className="qq-app mv" key={i} label="Dropdown Question Summary">
                                <div>{`Question ${i + 1} - Dropdown`}</div>
                                <TextField
                                    className="qq-app mv"
                                    value={props.questions[i].question}
                                    variant="outlined"
                                    label="Question"
                                    size="small"
                                />
                            </div>
                        );
                    default:
                        console.log(`[WARNING] Unknown question type "${q.question}"`)
                        return (<div />);
                };
            })}
        </div>
    );
};

export default Summary;