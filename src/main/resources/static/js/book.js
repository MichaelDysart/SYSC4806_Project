function myFunc1() {
    var survey;

    survey = {
        name : "survey1",
        questions : [ { type: "openEnded", question: "q1" }, { type: "openEnded", question: "q2" }, { type: "numberQuestion", question: "q1", min: "0", max: "5" } ]
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
        case "numberQuestion":
            qDiv.append($("<input type='text' placeholder='question'/> <input type='text' placeholder='min'/> <input type='text' placeholder='max'/>"))
            qDiv.addClass('numberQuestion')
            break;
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
        } else if($(element).hasClass('numberQuestion')) {
            var min = $(element).children().eq(1).val();
            var max = $(element).children().eq(2).val();

            survey.questions.push( {type: "numberQuestion", question: question, min: min, max: max} )
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
