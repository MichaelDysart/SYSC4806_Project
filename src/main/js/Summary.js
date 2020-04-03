import React, { useState, useEffect } from 'react';
import TextField from '@material-ui/core/TextField';
import { scaleOrdinal } from '@vx/scale';
import { LegendOrdinal } from '@vx/legend';
import { Histogram, DensitySeries, BarSeries, withParentSize, XAxis, YAxis } from '@data-ui/histogram';
import { RadialChart, ArcSeries, ArcLabel } from '@data-ui/radial-chart';
import { color as colors } from '@data-ui/theme';

const qType = {
    OPEN_ENDED: "openEnded",
    NUMERICAL: "numberQuestion",
    DROPDOWN: "dropdown",
};

/*
 * The frontend core which renders all subcomponents for surveys
 */
const Summary = (props) => {
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
                                      {(
                                          () => {
                                              if (props.questions[i].numberAnswerList.length > 0) {
                                                  return (<BarSeries
                                                      animated
                                                      rawData={ props.questions[i].numberAnswerList }
                                                  />);
                                              } else {
                                                  return (<BarSeries
                                                      animated
                                                      binnedData={ [{id : "", bin0 : 0, bin1 : 1, count : 0}] }
                                                  />);
                                              }
                                          }
                                       )()
                                      }
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

                                <RadialChart
                                      ariaLabel="My pie chart of..."
                                      width={300}
                                      height={300}

                                      renderTooltip={({ event, datum, data, fraction }) => (
                                        <div>
                                          <strong>{datum.label}</strong>
                                          {datum.value} ({(fraction * 100).toFixed(2)}%)
                                        </div>
                                      )}
                                    >
                                      <ArcSeries
                                        data={(() => {
                                            var dict = {};
                                            var arr = [];
                                            console.log(props.questions);
                                            for (var j = 0; j < props.questions[i].stringAnswerList.length; j++) {
                                                var name = props.questions[i].stringAnswerList[j];
                                                if (dict.hasOwnProperty(name)) {
                                                   dict[name] += 1;
                                                } else {
                                                   dict[name] = 1;
                                                }
                                            }
                                            for (var k in dict) {
                                                arr.push({ label : k, value : dict[k] });
                                            }
                                            return arr;
                                        })()}

                                            pieValue={d => d.value}
                                            fill={arc => scaleOrdinal({ range: colors.categories })(arc.data.label)}
                                            stroke="#fff"
                                            strokeWidth={1}
                                            label={arc => `${(arc.data.value).toFixed(1)}`}
                                            labelComponent={<ArcLabel />}
                                            innerRadius={radius => 0.35 * radius}
                                            outerRadius={radius => 0.6 * radius}
                                            labelRadius={radius => 0.75 * radius}
                                      />
                                    </RadialChart>
                                    <LegendOrdinal
                                        direction="column"
                                        scale={scaleOrdinal({ range: colors.categories })}
                                        shape="rect"
                                        fill={({ datum }) => scaleOrdinal({ range: colors.categories })(datum)}
                                        labelFormat={label => label}
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
