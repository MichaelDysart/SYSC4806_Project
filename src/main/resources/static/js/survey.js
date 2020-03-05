// Test function
// @TODO remove

var weburl;

/*
 * Retrieve the current window url as the url to of the server
 */
$(document).ready(function () {
    weburl = window.location.href;
});

// Test function
// @TODO remove
function myFunc1() {
    var survey;

    survey = {
        name : "survey1",
        questions : [ { type: "openEnded", question: "q1" }, { type: "openEnded", question: "q2" }, { type: "numberQuestion", question: "q1", min: "0", max: "5" } ]
    };

    $.ajax({
         method: "POST",
         url   : weburl + "createSurvey",
         contentType: "application/json",
         data  : JSON.stringify(survey),
         error: function (xhr, ajaxOptions, thrownError) {
               console.log(xhr.status);
               console.log(thrownError);
         }
    }).done(display);
}

// Test function
// @TODO remove
function myFunc2() {
    $.ajax({
             method: "GET",
             url   : weburl + "retrieveSurvey?name=" + $("#surveyName").val(),
             error: function (xhr, ajaxOptions, thrownError) {
                     console.log(xhr.status);
                     console.log(thrownError);
             }
    }).done(display);
}

// Test function
// @TODO remove
function display(data) {
    console.log(data);
}

/*
 * Create a div associated for inputting a question
 * This is used when creating a survey to create each new question
 * @returns {Object} the new question input
 */
function makeQuestionDiv() {
    var type = $("#questionType").val();

    var qDiv = $("<div>");

    // Determine the type of question
    // When a new question input div is create the question type
    // is stored as the class of the div
    switch (type) {
        case "numberQuestion":
            qDiv.append($("<input type='text' placeholder='question'/> <input type='number' placeholder='min'/> <input type='number' placeholder='max'/>"))
            qDiv.addClass('numberQuestion')
            break;
        case "openEnded":
            qDiv.append($("<input type='text'/>"));
            qDiv.addClass('openEnded');
            break;
        default:
            break;
    }

    // Add a button to create a new question div below this question
    var addButton = $("<button>+</button>");
    qDiv.append(addButton);
    addButton.on("click", function(){
        this.after(makeQuestionDiv());
    }.bind(qDiv));

    // Create a button to delete this question
    var delButton = $("<button>-</button>");
    qDiv.append(delButton);
    delButton.on("click", function(){
        this.remove();
    }.bind(qDiv));

    return qDiv;
}

/*
 * The handler to add a new question to the end of the
 * survey question list
 */
function addQuestion() {
    $("#surveyQuestions").append(makeQuestionDiv());
}

/*
 * Store a new survey on the server
 */
function createSurvey() {
    var survey;

    survey = {
        name : $("#surveyName").val(),
        questions : []
    };

    $("#surveyQuestions").children().each( (index, element) => {
        var question = $(element).children().eq(0).val();

        // The class of the element is used to identify the type of question
        if ($(element).hasClass('openEnded')) {
            survey.questions.push({ type: "openEnded", question: question })
        } else if($(element).hasClass('numberQuestion')) {
            let min = $(element).children().eq(1).val();
            let max = $(element).children().eq(2).val();

            survey.questions.push( {type: "numberQuestion", question: question, min: min, max: max} )
        }
    });
    console.log(survey);

    $.ajax({
         method: "POST",
         url   : weburl + "createSurvey",
         contentType: "application/json",
         data  : JSON.stringify(survey),
         error: function (xhr, ajaxOptions, thrownError) {
               console.log(xhr.status);
               console.log(thrownError);
         }
    }).done(display);

}
