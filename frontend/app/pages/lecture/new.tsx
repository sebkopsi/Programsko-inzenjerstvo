import { useFetcher, useLoaderData } from "react-router";
import "../styles/courses.css";
import "../styles/forms.css";

import { OpenModal } from "../components/openModal";
import { QuestionBuilder, type QuestionOptions } from "./questionBuilder";
import { useEffect, useState } from "react";
import quiz from "./quiz";

export function NewLecturePage() {
    const fetcher = useFetcher();
    const { courseInfo, moduleInfo, lectureInfo } = useLoaderData();
    const [videoType, setVideoType] = useState("integrated");

    const [questionOptions, setQuestionOption] = useState<QuestionOptions | null>(null);
    const [lastId, setLastId] = useState(0);
    const [quizJson, setQuizJson] = useState<{ questions: any[]; options: any[] }>({
        questions: [],
        options: []
    })
    const newQuestion = (close_modal: any) => {
        if (!questionOptions?.question.value) return;
        const question = {
            id: lastId,
            type: questionOptions?.question.type,
            value: questionOptions?.question.value,

        }
        const options = questionOptions?.options.map((o, idx) => {
            return {
                questionId: question.id,
                value: o.value,
                correct: o.correct
            }
        })

        const newQuizJson = quizJson;
        newQuizJson.questions.push(question);
        newQuizJson.options = newQuizJson.options.concat(options);
        newQuizJson.options.forEach((o, idx) => {
            o.id = idx;
        })
        setLastId(lastId + 1);
        setQuizJson(newQuizJson);
        close_modal()
    }


    return (
        <section id="content">
            <section id="path">
                <h4><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                    <path d="M11.7 2.805a.75.75 0 0 1 .6 0A60.65 60.65 0 0 1 22.83 8.72a.75.75 0 0 1-.231 1.337 49.948 49.948 0 0 0-9.902 3.912l-.003.002c-.114.06-.227.119-.34.18a.75.75 0 0 1-.707 0A50.88 50.88 0 0 0 7.5 12.173v-.224c0-.131.067-.248.172-.311a54.615 54.615 0 0 1 4.653-2.52.75.75 0 0 0-.65-1.352 56.123 56.123 0 0 0-4.78 2.589 1.858 1.858 0 0 0-.859 1.228 49.803 49.803 0 0 0-4.634-1.527.75.75 0 0 1-.231-1.337A60.653 60.653 0 0 1 11.7 2.805Z" />
                    <path d="M13.06 15.473a48.45 48.45 0 0 1 7.666-3.282c.134 1.414.22 2.843.255 4.284a.75.75 0 0 1-.46.711 47.87 47.87 0 0 0-8.105 4.342.75.75 0 0 1-.832 0 47.87 47.87 0 0 0-8.104-4.342.75.75 0 0 1-.461-.71c.035-1.442.121-2.87.255-4.286.921.304 1.83.634 2.726.99v1.27a1.5 1.5 0 0 0-.14 2.508c-.09.38-.222.753-.397 1.11.452.213.901.434 1.346.66a6.727 6.727 0 0 0 .551-1.607 1.5 1.5 0 0 0 .14-2.67v-.645a48.549 48.549 0 0 1 3.44 1.667 2.25 2.25 0 0 0 2.12 0Z" />
                    <path d="M4.462 19.462c.42-.419.753-.89 1-1.395.453.214.902.435 1.347.662a6.742 6.742 0 0 1-1.286 1.794.75.75 0 0 1-1.06-1.06Z" />
                </svg>
                    All Courses</h4>
                <h4>{courseInfo?.data.name}</h4>
                <h2>{moduleInfo?.data.name}</h2>
            </section>

            <fetcher.Form
                className="course-form"
                method="POST"
                encType="multipart/form-data"

            >
                <input type="hidden" name="courseId" value={courseInfo?.data.id} />
                <input type="hidden" name="moduleId" value={moduleInfo?.data.id} />
                <h2>Lecture Info</h2>

                <section className="section">
                    <section className="info">
                        <h3>Name</h3>
                        <span>Lecture title shown to users</span>
                    </section>
                    <input type="text" name="name" className="box" required />
                </section>


                <section className="section">
                    <section className="info">
                        <h3>Time</h3>
                        <span>Preparation and cooking time</span>
                    </section>

                    <section className="form">
                        <input
                            required
                            className="box"
                            type="number"
                            name="prepTime"
                            placeholder="Prep time (min)"
                        />
                        <input
                            required
                            className="box"
                            type="number"
                            name="cookTime"
                            placeholder="Cooking time (min)"
                        />
                    </section>
                </section>

                <section className="section">
                    <section className="info">
                        <h3>Difficulty</h3>
                        <span>Select lecture difficulty</span>
                    </section>

                    <select name="difficulty" className="box" required>
                        <option value="">Select difficulty</option>
                        <option value="beginner">beginner</option>
                        <option value="medium">experienced</option>
                        <option value="master">master</option>
                    </select>
                </section>


                <section className="section">3
                    <section className="info">
                        <h3>Video</h3>
                        <span>Add a video showing how to cook the meal of this lecture! Choose between an integrated video, or an external link.</span>
                    </section>

                    <section className="form">
                        <select name="video" className="box" onChange={(e) => setVideoType(e.target.value)} >
                            <option value="integrated">Integrated</option>
                            <option value="external">External</option>
                        </select>
                        {videoType === "integrated" && (
                            <input
                                className="box"
                                type="file"
                                name="videoFile"
                                accept="video/*"
                                required
                            />
                        )}

                        {videoType === "external" && (
                            <input
                                className="box"
                                type="text"
                                name="videoUrl"
                                placeholder="External video link"
                                required
                            />
                        )}
                    </section>

                </section>

                <section className="section">
                    <section className="info">
                        <h3>Steps</h3>
                        <span>
                            Cooking steps (Markdown supported)
                        </span>
                    </section>
                    <textarea
                        required
                        name="steps"
                        className="box"
                        rows={8}
                        placeholder="## Step 1&#10;Boil water..."
                    />
                </section>


                <section className="section">
                    <section className="info">
                        <h3>Quiz Questions</h3>
                        <span>
                            Add questions for your students to answer to finish the lecture.
                        </span>
                        <span>Preview to the right</span>
                        <div className="form">
                            <OpenModal
                                label="Add Question"
                                modal={(close_modal) => (
                                    <>
                                        <h3>New Question</h3>
                                        <QuestionBuilder onAdd={setQuestionOption} />

                                        <section className="form-options section">
                                            <button id="addquestion" type="button" onClick={(e) => {
                                                newQuestion(close_modal);
                                            }}>addQuestion</button>
                                            <button type="button" onClick={close_modal}>
                                                Close
                                            </button>
                                        </section>
                                    </>
                                )}
                            />
                        </div>

                    </section>

                    <section id="quiz-preview">
                        <input type="hidden" name="quizJson" value={JSON.stringify(quizJson)} />
                        {quiz(quizJson)}
                    </section>
                </section>


                <section className="section">
                    <section className="info">
                        <h3>Quiz Options</h3>
                        <span>Additional configuration for quiz</span>
                    </section>

                    <section className="form">
                        <label className="label">
                            minimum score required for passing lecture, defaults to 50%
                            <input

                                className="box"
                                type="number"
                                name="minScore"
                                placeholder="0-100"
                                max={"100"}
                                min={"0"}
                            />
                        </label>
                    </section>
                </section>



                <section className="section formOptions">
                    <button type="submit">Create Lecture</button>
                </section>
            </fetcher.Form>
        </section>
    );
}
