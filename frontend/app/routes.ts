import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
    index("routes/home.tsx"),
    route("login", "routes/login.tsx"),
    route("signup", "routes/signup.tsx"),
    route("oauth", "routes/oauth.tsx"),
    route("logout", "routes/logout.tsx"),
    route("profile/preferences", "routes/profile.tsx"),

    route("course", "routes/course/userCourses.tsx"),
    route("course/new", "routes/course/newCourse.tsx"),
    route("course/:courseId", "routes/course/course.tsx"),

    route("course/:courseId/:moduleId", "routes/module/module.tsx"),
    route("course/:courseId/new", "routes/module/new.tsx"),


    route("course/:courseId/:moduleId/:lectureId", "routes/lecture/lecture.tsx"),
    route("course/:courseId/:moduleId/new", "routes/lecture/new.tsx"),

    route("adminpanel", "routes/admin_panel/adminPanel.tsx")


] satisfies RouteConfig;
