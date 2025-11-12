import { SignUpPage } from "~/pages/signUp/SignUpPage";

export function meta({}: { title: string; description: string }) {
        return [
                { title: "Sign Up" },
                { name: "description", content: "Registracija novog korisnika" },
        ];
}

export default function SignUp() {
        return <SignUpPage />;
}

