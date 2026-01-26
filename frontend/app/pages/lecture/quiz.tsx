import { useFetcher } from "react-router";
import styles from "../styles/forms.css";

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
              name={String(question.id)} 
              value={String(opt.id)} />
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
              name={String(question.id)} 
              value={String(opt.id)} 
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
      <input type="text" className="box" name={String(question.id)} />
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
  const options = (allOptions ?? [])
    .filter((o) => o.questionId === question.id)
    .sort((a, b) => (a?.id ?? 0) - (b?.id ?? 0));

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
};

export default function quiz(data: any, formLink?: string) {
  const fetcher = useFetcher();

  const questions = (data?.questions ?? [])
    .slice()
    .sort((a, b) => (a?.id ?? 0) - (b?.id ?? 0));

  return (
    <fetcher.Form
      className="form"
      method="POST"
      {...(formLink ? { action: formLink } : {})}
      id="quiz"
    >
      <section className="section">
        <div className="info">
          <h2>Quiz</h2>
          <span>Check your understanding to successfully pass the lecture</span>
        </div>
      </section>

      {questions.map((question) => (
        <section key={question.id} className="quiz-question">
          {renderQuestion(question, data.options)}
        </section>
      ))}

      <section className="section formOptions">
        <button className="submit-button" type="submit" disabled={fetcher.state !== "idle"}>Submit</button>
      </section>

{fetcher.data ? (
  <section className="section">
    {fetcher.data.success ? (
      <div className="box">
        <h3>Quiz submitted</h3>

        <p>
          Score: {fetcher.data.correctAnswers} / {fetcher.data.totalQuestions}
        </p>

        {fetcher.data.completionPercentage === 100 ? (<p>PASS</p>) : (<p>FAIL</p>)}
      </div>
    ) : (
      <div className="box">
        <h3>Quiz submit failed</h3>
        <p>{fetcher.data.message ?? "Error"}</p>
      </div>
    )}
  </section>
) : null}
    </fetcher.Form>
  );
}
