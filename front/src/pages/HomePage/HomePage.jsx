import styles from "./HomePage.module.css"
import ProfileCard from "./components/ProfileCard/ProfileCard"
import RecommandCard from "./components/RecommandCard/RecommandCard"
import FilterCard from "./components/FilterCard/FilterCard"
import endavaLogo from "../../assets/endava-logo.png";
import pentalogLogo from "../../assets/pentalog-logo.png";
import roslinLogo from "../../assets/roslin-logo.png";

const jobs = [
    {
        id: 1,
        logo: endavaLogo,
        title: "Java Junior Developer",
        company: "Endava",
        postedAt: "Posted 45 min ago",
        employmentType: "Full-time",
        salary: "5,200 per year",
        experience: "6-7 years of experience",
        matchPercent: 95,
        isStarred: false,
    },
    {
        id: 2,
        logo: pentalogLogo,
        title: "C# Junior Developer",
        company: "Pentalog",
        postedAt: "Posted 45 min ago",
        employmentType: "Full-time",
        salary: "5,200 per year",
        experience: "6-7 years of experience",
        matchPercent: 60,
        isStarred: true,
    },
    {
        id: 3,
        logo: roslinLogo,
        title: "Middle Golang Engineer",
        company: "Roslin Solutions",
        postedAt: "Posted 45 min ago",
        employmentType: "Full-time",
        salary: "5,200 per year",
        experience: "6-7 years of experience",
        matchPercent: 15,
        isStarred: true,
    },
    {
        id: 4,
        logo: roslinLogo,
        title: "Middle Golang Engineer",
        company: "Roslin Solutions",
        postedAt: "Posted 45 min ago",
        employmentType: "Full-time",
        salary: "5,200 per year",
        experience: "6-7 years of experience",
        matchPercent: 15,
        isStarred: false,
    },
    {
        id: 5,
        logo: roslinLogo,
        title: "Middle Golang Engineer",
        company: "Roslin Solutions",
        postedAt: "Posted 45 min ago",
        employmentType: "Full-time",
        salary: "5,200 per year",
        experience: "6-7 years of experience",
        matchPercent: 15,
        isStarred: false,
    },
    {
        id: 6,
        logo: roslinLogo,
        title: "Middle Golang Engineer",
        company: "Roslin Solutions",
        postedAt: "Posted 45 min ago",
        employmentType: "Full-time",
        salary: "5,200 per year",
        experience: "6-7 years of experience",
        matchPercent: 15,
        isStarred: false,
    },
]

function HomePage () {

    return (
        <div className={styles.HomePage}>
            <div>
                <ProfileCard
                    name="Maxim Ormanji"
                    email="nick.carn@gmail.com"
                    phone="+373 62 123 456"
                    location="Chișinău, Moldova"
                    aboutMe="A junior IT professional passionate about technology and problem-solving. 
 I am eager to grow my skills, learn from experienced teams, and contribute to meaningful projects."
                    appliedJobsCount={3}
                    savedJobsCount={200}
                    cvViewsCount={1}
                    reachoutsCount={10}
                    onEditProfile={() => console.log("edit clicked")}
                />
            </div>
            <RecommandCard jobs={jobs} />
            <FilterCard />
        </div>
        
    )
}

export default HomePage