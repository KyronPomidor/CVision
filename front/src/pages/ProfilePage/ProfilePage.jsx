import styles from "./ProfilePage.module.css"
import ProfileCard from "../../components/ProfileCard/ProfileCard";
    import CVInformationCard from "./components/CVInformationCard/CVInformationCard"

// TODO: replace with data coming from the backend
const cv = {
    technicalSkills: ["Microsoft Office", "Java", "Git", "Python", "C#", "php", "Golang"],
    languages: [
        { name: "English", level: "B2" },
        { name: "Romanian", level: "Native" },
        { name: "Russian", level: "C1" },
    ],
    objective:
        "Looking for a junior developer role where I can apply my problem-solving skills and grow within a collaborative engineering team, with a long-term interest in moving into full-stack development.",
    experience: [
        {
            id: 1,
            title: "IT Support Intern",
            company: "Endava",
            location: "Chișinău",
            startDate: "March 2025",
            endDate: "June 2026",
            description:
                "When creating a professional profile, blog or website, an \"about me\" statement can quickly show employers, clients or customers who you are and what you offer.",
        },
    ],
    additionalInfo :
        "When creating a professional profile, blog or website, an “about me” statement can quickly show employers, clients or customers who you are and what you offer. ",

}

function ProfilePage () {

    return (
        <div className={styles.ProfilePage}>
            <ProfileCard
                variant="profile"
                name="Nicholas Carnegie"
                email="nick.carn@gmail.com"
                phone="+373 62 123 456"
                location="Chișinău, Moldova"
                aboutMe="A junior IT professional passionate about technology and problem-solving. 
 I am eager to grow my skills, learn from experienced teams, and contribute to meaningful projects."
                onEditProfile={() => console.log("edit clicked")}
                onUploadCV={() => console.log("upload cv clicked")}
            />

            <CVInformationCard
                technicalSkills={cv.technicalSkills}
                languages={cv.languages}
                objective={cv.objective}
                experience={cv.experience}
                additionalInfo={cv.additionalInfo}
            />
        </div>
    )
}

export default ProfilePage
