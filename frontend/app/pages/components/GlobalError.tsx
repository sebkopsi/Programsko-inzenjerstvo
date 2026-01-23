type ErrorObject = {
  message: string;
};

type GlobalErrorProps = {
  error?: ErrorObject | null;
};

export function GlobalError({ error }: GlobalErrorProps) {
  if (!error) return null;

  return (
    <section className="global-error">
      <p>{error.message}</p>
    </section>
  );
}
