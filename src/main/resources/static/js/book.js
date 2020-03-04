function myFunc1() {
    var survey;

    survey = {
        name : "survey1",
        questions : [ { type: "openEnded", question: "q1" }, { type: "openEnded", question: "q2" } ]
    };

    $.ajax({
         method: "POST",
         url   : "http://localhost:8080/createSurvey",
         contentType: "application/json",
         data  : JSON.stringify(survey),
         error: function (xhr, ajaxOptions, thrownError) {
               console.log(xhr.status);
               console.log(thrownError);
         }
    }).done(display);
}

function myFunc2() {
    $.ajax({
             method: "GET",
             url   : "http://localhost:8080/retrieveSurvey?name=" + $("#surveyName").val(),
             error: function (xhr, ajaxOptions, thrownError) {
                     console.log(xhr.status);
                     console.log(thrownError);
             }
    }).done(display);
}

function display(data) {
    console.log(data);
}

function makeQuestionDiv() {
    var type = $("#questionType").val();

    var qDiv = $("<div>");

    switch (type) {
    case "openEnded":
        qDiv.append($("<input type='text'/>"));
        qDiv.addClass('openEnded');
        break;
    default:
        break;
    }

    var addButton = $("<button>+</button>");
    qDiv.append(addButton);
    addButton.on("click", function(){
        this.after(makeQuestionDiv());
    }.bind(qDiv));

    var delButton = $("<button>-</button>");
    qDiv.append(delButton);
    delButton.on("click", function(){
        this.remove();
    }.bind(qDiv));

    return qDiv;
}

function addQuestion() {
    $("#surveyQuestions").append(makeQuestionDiv());
}

function createSurvey() {
    var survey;

    survey = {
        name : $("#surveyName").val(),
        questions : []
    };
    $("#surveyQuestions").children().each( (index, element) => {
        var question = $(element).children().eq(0).val();

        if ($(element).hasClass('openEnded')) {
            survey.questions.push({ type: "openEnded", question: question })
        }
    });
    console.log(survey);

    $.ajax({
         method: "POST",
         url   : "http://localhost:8080/createSurvey",
         contentType: "application/json",
         data  : JSON.stringify(survey),
         error: function (xhr, ajaxOptions, thrownError) {
               console.log(xhr.status);
               console.log(thrownError);
         }
    }).done(display);

}
