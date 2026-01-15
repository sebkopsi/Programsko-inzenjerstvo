import { useFetcher } from "react-router";


function renderSingleQuestion(question, options) {

    return (
        <section className="section">
            <section className="info">
                <h3 className="label">{question.value}</h3>
            </section>

            <section className="options">
                {options.map((opt) => (
                    <label key={opt.id}>
                        <input
                            type="radio"
                            name={question.id}
                            value={opt.id}
                        />
                        {opt.value}
                    </label>
                ))}
            </section>
        </section>
    );
}

function renderMultipleQuestion(question, options) {
    return (
        <section className="section">
            <section className="info">
                <h3 className="label">{question.value}</h3>
            </section>
            <section className="options">
                {options.map((opt) => (
                    <label key={opt.id}>
                        <input
                            type="checkbox"
                            name={question.id}
                            value={opt.id}
                        />
                        {opt.value}
                    </label>
                ))}
            </section>
        </section>
    );
}

function renderTextQuestion(question) {
    return (
        <section className="section">
            <section className="info">
                <h3 className="label">{question.value}</h3>
            </section>
            <input type="text" className="box" name={question.id} />
        </section>
    );
}

function renderImageQuestion(question, options) {
    return (
        <section className="section">
            <section className="info">
                <h3 className="label">{question.value}</h3>
            </section>
            {options.map((opt, idx) => (
                <img
                    key={idx}
                    src={opt.value}
                    alt="quiz option"
                    style={{ width: 120 }}
                />
            ))}
        </section>
    );
}


const renderQuestion = (question: any, allOptions: any) => {
    const options = allOptions
        .filter((o) => o.questionId === question.id)
        .sort((a, b) => a?.id - b?.id);

    switch (question.type) {
        case "single":
            return renderSingleQuestion(question, options);

        case "multiple":
            return renderMultipleQuestion(question, options);

        case "text":
            return renderTextQuestion(question);

        case "image":
            return renderImageQuestion(question, options);

        default:
            return <p>Unsupported question type</p>;
    }
}



export default function quiz(data: any, formLink: string) {
    const fetcher = useFetcher();
    return (
        <>
            {typeof formLink === "string" && formLink ? (
                <fetcher.Form className="form" method="POST" action={formLink} id="quiz">
                    <section className="section">
                        <div className="info">
                            <h2>Quiz</h2>
                            <span>Check your understanding to successfully pass the lecture</span>
                        </div>
                    </section>

                    {data?.questions?.map((question) => (
                        <section key={question.id} className="quiz-question">
                            {renderQuestion(question, data.options)}
                        </section>
                    ))}

                    <section className="section formOptions">
                        <button type="submit">Submit</button>
                    </section>
                </fetcher.Form>
            ) : (
                <section className="form">
                    {data?.questions?.map((question) => (
                        <section key={question.id} className="quiz-question">
                            {renderQuestion(question, data.options)}
                        </section>
                    ))}

                </section>
            )}
        </>
    );


}