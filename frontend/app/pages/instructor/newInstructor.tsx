import './newInstructor.css';
import pfp from './fame-media_no-prophile-picture-1-74336437.jpg';
import { Form, useFetcher } from "react-router";
import { useState } from "react";

export function NewInstructorPage() {
  const fetcher = useFetcher();
  const [preview, setPreview ] = useState<string|null>(null);

  const onFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;
    setPreview(URL.createObjectURL(file));
  };

  return (
    <section id="content">
      <h2>Instructor application</h2>
      <hr/>
      <fetcher.Form method="POST" action="/newInstructor">

        <div>
          <h3>Profile picture</h3>
          <div className="imgFrame">
            <img 
              src={preview ?? pfp} 
              alt="pfp" 
            />
          </div>
          <input 
            type="file"
            name="pfp"
            accept="image/*"
            onChange={onFileChange} 
            required />
        </div>
        <hr/>

        <div className="title">
          <h3>Title</h3>
          <input type="text" name="title" className="titleInput" required />
        </div>
        <hr/>

        <div>
          <h3>Description</h3>
          <textarea name="desc" className="box" required />
        </div>
        <hr/>

        <div>
          <h3>Identification document</h3>
          <input type="file" name="id" required/>
        </div>
        <hr/>

        <div>
          <h3>Diplomas/certificates</h3>
          <input type="file" name="cert" multiple required />
        </div>
        <hr/>

        <button type="submit">Send</button>

      </fetcher.Form>
    </section>
    
  );
}