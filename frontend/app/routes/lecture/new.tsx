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
    const data = {
      name: formData.get('name'),
      prepTime: `PT${formData.get('prepTime')}M`,
      cookTime: `PT${formData.get('cookTime')}M`,
      difficulty: formData.get('difficulty'),
      steps: formData.get('steps'),
      quiz: JSON.parse(quizData),
      minScore: formData.get('minScore')
    }
 
    const createReq = await fetch(`http://localhost:8890/course/${formData.get("courseId")}/module/${formData.get("moduleId")}/lecture`, {
      method: "POST",
      headers: {
        "content-type": "application/json",
        "Authorization": "Bearer " + jwt
      },
      body: JSON.stringify(data)
    });


    if (!createReq.ok) {
      return {
        courseInfo: null,
        moduleInfo: null,
        lectureInfo: null,
        errorObject: { message: "Failed to send request" }
      };
    }

    const createResponse = await createReq.json();
    if(!createResponse.success){
      return {
        courseInfo: null,
        moduleInfo: null,
        lectureInfo: null,
        errorObject: { message: "Failed to create lecture" }
      };
    }

    return redirect(`/course/${formData.get("courseId")}/module/${formData.get("moduleId")}/lecture/${createResponse.data.id}`);

  } catch (error) {
    throw new Error("Lecture creation error: " + error);
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
    return {
      courseInfo: null,
      moduleInfo: null,
      lectureInfo: null,
      errorObject: { message: "Failed to fetch courses" }
    };
  }

  const courseInfo = await courseReq.json();

  if (!courseInfo.success) {
    return {
      courseInfo: null,
      moduleInfo: null,
      lectureInfo: null,
      errorObject: { message: "No course found" }
    };
  }

  const moduleReq = await fetch(`http://localhost:8890/course/${params['courseId']}/module/${params['moduleId']}`, {
    method: "GET",
    headers: {
      "Authorization": "Bearer " + jwt,
    }
  });

  if (!moduleReq.ok) {
    return {
      courseInfo,
      moduleInfo: null,
      lectureInfo: null,
      errorObject: { message: "Failed to fetch modules" }
    };
  }

  const moduleInfo = await moduleReq.json();

  if (!moduleInfo.success) {
    return {
      courseInfo,
      moduleInfo: null,
      lectureInfo: null,
      errorObject: { message: "No module found" }
    };
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
    return {
      courseInfo,
      moduleInfo,
      lectureInfo: null,
      errorObject: { message: "Failed to fetch lectures" }
    };
  }

  const lecturesData = await lecturesReq.json();
  if (!lecturesData.success) {
    return {
      courseInfo,
      moduleInfo,
      lectureInfo: null,
      errorObject: { message: "No lectures found" }
    };
  }

  return {
    courseInfo,
    moduleInfo,
    lecturesData,
    errorObject: null
  }
}

export default function NewModule() {
  return <NewLecturePage />
}
