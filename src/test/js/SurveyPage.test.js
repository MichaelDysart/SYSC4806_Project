// Instructions
// Need babel.config.js
// Need to add enzyme and babel to package.json
// Add "jest" to package.json
// Add __mocks__ with fileMock.js and styleMock.js
// Must configure enzyme
//      - the import adapter steps below

import React from 'react';
import ReactDOM from 'react-dom';
import { shallow, render, mount } from 'enzyme';
import SurveyPage from '../../main/js/SurveyPage';
import Button from '@material-ui/core/Button';
import Select from '@material-ui/core/Select';

import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

describe('Test rendering', () => {
   it('perform a shallow render without crashing', () => {
        shallow(<SurveyPage />);
   });
   it('perform a full render without crashing', () => {
        mount(<SurveyPage />);
   });
});

describe('Test adding questions', () => {
   it('No question adding', () => {
        let wrapper = shallow(<SurveyPage />);

        wrapper.find(Button).find({ label: "Add Question" }).at(0).simulate('click');
        expect(wrapper.find({ label: "Open Question Input" }).length).toBe(0);
        expect(wrapper.find({ label: "Number Question Input" }).length).toBe(0);
   });

   it('Add a single open question', () => {
        let wrapper = shallow(<SurveyPage />);

        wrapper.find(Select).find({ labelId: "qtype_select_label" }).at(0).simulate('change', { target: { value: "openEnded" } } );
        expect(wrapper.find({ label: "Open Question Input" }).length).toBe(0);
        expect(wrapper.find({ label: "Number Question Input" }).length).toBe(0);

        wrapper.find(Button).find({ label: "Add Question" }).at(0).simulate('click');
        expect(wrapper.find({ label: "Open Question Input" }).length).toBe(1);
        expect(wrapper.find({ label: "Number Question Input" }).length).toBe(0);
   });

   it('Add a single number questions', () => {
        let wrapper = shallow(<SurveyPage />);

        wrapper.find(Select).find({ labelId: "qtype_select_label" }).at(0).simulate('change', { target: { value: "numberQuestion" } } );
        expect(wrapper.find({ label: "Open Question Input" }).length).toBe(0);
        expect(wrapper.find({ label: "Number Question Input" }).length).toBe(0);

        wrapper.find(Button).find({ label: "Add Question" }).at(0).simulate('click');
        expect(wrapper.find({ label: "Open Question Input" }).length).toBe(0);
        expect(wrapper.find({ label: "Number Question Input" }).length).toBe(1);
   });

   it('Add a question', () => {

        let wrapper = shallow(<SurveyPage />);
        let sumOpen = 0;
        let sumNumber = 0;
        let currentType = '';

        for (let i = 0; i < 10; i++) {
            // We test two things, do we
            var toChange = Math.random() < 0.5;
            if (toChange) {
                currentType = Math.random() < 0.5 ? 'openEnded' : 'numberQuestion';
                wrapper.find(Select).find({ labelId: "qtype_select_label" }).at(0).simulate('change', { target: { value: currentType } } );
            }
            wrapper.find(Button).find({ label: "Add Question" }).at(0).simulate('click');
            switch (currentType) {
                case 'openEnded':
                    sumOpen += 1;
                    break;
                case 'numberQuestion':
                    sumNumber += 1;
                    break;
            }
            expect(wrapper.find({ label: "Open Question Input" }).length).toBe(sumOpen);
            expect(wrapper.find({ label: "Number Question Input" }).length).toBe(sumNumber);
        }
   });
});
