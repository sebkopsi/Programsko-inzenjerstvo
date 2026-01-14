import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
    index("routes/home.tsx"),
    route("login", "routes/login.tsx"),
    route("signup", "routes/signup.tsx"),
    route("oauth", "routes/oauth.tsx"),
    route("profile/preferences", "routes/profile.tsx"),

    route("course", "routes/userCourses.tsx"),
    route("course/new", "routes/newCourse.tsx"),
    route("course/:courseId", "routes/course.tsx"),
    
    route("course/:courseId/:moduleId", "routes/module.tsx")


] satisfies RouteConfig;
