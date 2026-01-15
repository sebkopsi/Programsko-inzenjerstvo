import { useState, type ReactNode } from "react";

type ModalOpenerProps = {
    label: string;
    modal: (close: () => void) => ReactNode;
};

export function OpenModal({ label, modal }: ModalOpenerProps) {
    const [open, setOpen] = useState(false);

    const close_modal = () => setOpen(false);

    return (
        <>
            <button type="button" onClick={() => setOpen(true)}>
                {label}
            </button>

            {open && (
                <section className="modal-backdrop">
                    <section className="modal box">
                        {modal(close_modal)}
                    </section>
                </section>
            )}
        </>
    );
}
