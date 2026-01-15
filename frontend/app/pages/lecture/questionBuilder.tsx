import { useEffect, useState } from "react";

export type QuestionType = "text" | "single" | "multiple";

export type Option = {
    value: string;
    correct: boolean;
};

export type Question = {
    type: QuestionType;
    value: string;
}

export type QuestionOptions = {
    question: Question;
    options: Option[];
}

export type Props = {
    onAdd: (value: QuestionOptions) => void;
};


export function QuestionBuilder({ onAdd }: Props) {
    const [type, setType] = useState<QuestionType>("text");
    const [textAnswer, setTextAnswer] = useState("");
    const [questionText, setQuestionText] = useState("");
    const [options, setOptions] = useState<Option[]>([
        { value: "", correct: false },
    ]);

    useEffect(() => {
        if (type === "text") {
            onAdd({
                question: {
                    type: type,
                    value: questionText
                },
                options: [{
                    correct: true,
                    value: textAnswer
                }]
            });
        } else {
            onAdd({
                question: {
                    type: type,
                    value: questionText
                },
                options: options.filter(o => o.value.trim() != ""),
            });
        }
    }, [type, textAnswer, options, onAdd]);

    const updateOption = (index: number, field: keyof Option, value: any) => {
        const next = [...options];
        next[index] = { ...next[index], [field]: value };
        if (
            field === "value" &&
            index === options.length - 1 &&
            value.trim() !== ""
        ) {
            next.push({ value: "", correct: false });
        }
        if (field === "correct" && value && type === "single") {
            next.forEach((opt, i) => {
                if (i !== index) opt.correct = false;
            });
        }

        setOptions(next);
    }

    const resetForType = (newType: QuestionType) => {
        setType(newType);
        setTextAnswer("");
        setOptions([{ value: "", correct: false }]);
    }

    return (
        <section className="form">
            <section className="section">
                <section className="info">
                    <span className="label">
                        Question type
                    </span>
                </section>
                <select value={type} onChange={(e) => resetForType(e.target.value as QuestionType)} className="box">
                    <option value="text">Text</option>
                    <option value="single">Single choice</option>
                    <option value="multiple">Multiple choice</option>
                </select>
            </section>
            <section className="section">
                <section className="info">
                    <span className="label">Question</span>
                </section>
                <input className="box"
                    type="text"
                    required
                    value={questionText}
                    onChange={(e) => setQuestionText(e.target.value)}
                />
            </section>

            {type === "text" && (
                <section className="section">
                    <section className="info">
                        <span className="label">Correct Value</span>
                    </section>
                    <input className="box"
                        required
                        type="text"
                        value={textAnswer}
                        onChange={(e) => setTextAnswer(e.target.value)}
                    />
                </section>
            )}

            {(type === "single" || type === "multiple") && (
                <section className="options section">
                    {options.map((opt, index) => (
                        <section key={index} className="option-row">
                            <input
                                type="text"
                                className="value box"
                                placeholder={`Option ${index + 1}`}
                                value={opt.value}
                                onChange={(e) =>
                                    updateOption(index, "value", e.target.value)
                                }
                            />
                            <label>correct
                                <input
                                    type="checkbox"
                                    className="correct-checkbox"
                                    checked={opt.correct}
                                    onChange={(e) =>
                                        updateOption(index, "correct", e.target.checked)
                                    }
                                />
                            </label>
                        </section>
                    ))}
                </section>
            )}

        </section>
    );
}