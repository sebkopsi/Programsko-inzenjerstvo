import { Card } from "../components/card";


export function allModules(modules: any[]) {
  return (
    <section id="all-modules" className="cardTable section">
      <ul>
        {modules?.length > 0 ? (
          modules.map((module: any) => (
            <Card
              key={module.id}
              link={module.id}
              name={module.name}
              desc={module.desc}
              lessons={module.lessons}
              type="module"
            />
          ))
        ) : (
          <div>No modules found</div>
        )}
      </ul>
    </section>
  );
}

