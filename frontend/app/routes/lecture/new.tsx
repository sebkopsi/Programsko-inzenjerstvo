import type { Route } from "./+types/lecture";
import { GetJwtToken } from "~/util/cookie";
import { redirect } from "react-router";
import { NewLecturePage } from "~/pages/lecture/new";


export async function action({ request }: Route.ActionArgs) {
  const formData = await request.formData();

  const jwt = GetJwtToken(request);
  if (!jwt) {
    return redirect("/login");
  }

  try {
    const quizData = String(formData.get('quizJson'));
    var data = {
      name: formData.get('name'),
      prepTime: `PT${formData.get('prepTime')}M`,
      cookTime: `PT${formData.get('cookTime')}M`,
      difficulty: formData.get('difficulty'),
      steps: formData.get('steps'),
      quiz: JSON.parse(quizData),
      minScore: formData.get('minScore'),
      videoType: formData.get("video"),
      url: formData.get('videoUrl'),
    }

    const multipartData = new FormData();
    multipartData.append("info", new Blob([JSON.stringify(data)], { type: "application/json" }));

    if (formData.get("videoFile")) {
      multipartData.append("videoFile", formData.get("videoFile") as File);
    }

    console.debug(multipartData)

    const createReq = await fetch(`http://localhost:8890/course/${formData.get("courseId")}/module/${formData.get("moduleId")}/lecture`, {
      method: "POST",
      headers: {
        "Authorization": "Bearer " + jwt
      },
      body: multipartData
    });


    if (!createReq.ok) {
      throw new Error("Failed to send request");
    }

    const createResponse = await createReq.json();
    if (!createResponse.success) {
      throw new Error("failed to create lecture: " + createResponse.message)
    }

  } catch (error: any) {
    console.debug(new Error(error))
  }
}


export async function loader({ params, request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request)
  if (!jwt) {
    return redirect("/login");
  }

  const courseReq = await fetch(`http://localhost:8890/course/${params['courseId']}`, {
    method: "GET",
    headers: {
      "Authorization": "Bearer " + jwt,
    }
  });

  if (!courseReq.ok) {
    throw new Error("Failed to fetch courses");
  }

  const courseInfo = await courseReq.json();

  if (!courseInfo.success) {
    throw new Error("No course found :(")
  }

  const moduleReq = await fetch(`http://localhost:8890/course/${params['courseId']}/module/${params['moduleId']}`, {
    method: "GET",
    headers: {
      "Authorization": "Bearer " + jwt,
    }
  });

  if (!moduleReq.ok) {
    throw new Error("Failed to fetch modules");
  }

  const moduleInfo = await moduleReq.json();

  if (!moduleInfo.success) {
    throw new Error("No module found :(")
  }

  const lecturesReq = await fetch(`http://localhost:8890/course/${params['courseId']}/module/${params['moduleId']}/lecture/search`, {
    method: "POST",
    headers: {
      "Authorization": "Bearer " + jwt,
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ term: "", moduleId: params['moduleId'] })
  });

  if (!lecturesReq.ok) {
    throw new Error("Failed to fetch lectures");
  }

  const lecturesData = await lecturesReq.json();

  return {
    courseInfo,
    moduleInfo,
    lecturesData
  }
}

export default function NewModule() {
  return <NewLecturePage />
}

