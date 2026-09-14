import styles from "./HomePage.module.css"
import ProfileCard from "./components/ProfileCard/ProfileCard"
import RecommandCard from "./components/RecommandCard/RecommandCard"
import FilterCard from "./components/FilterCard/FilterCard"

function HomePage () {

    return (
        <div className={styles.HomePage}>
            <div>
                <ProfileCard
                    name="Nicholas Carnegie"
                    email="nick.carn@gmail.com"
                    phone="+373 62 123 456"
                    location="Chișinău, Moldova"
                    aboutMe="A junior IT professional passionate about technology and problem-solving..."
                    appliedJobsCount={3}
                    savedJobsCount={2}
                    cvViewsCount={1}
                    reachoutsCount={10}
                    onEditProfile={() => console.log("edit clicked")}
                />
            </div>
            <RecommandCard />
            <FilterCard />
        </div>
        
    )
}

export default HomePage