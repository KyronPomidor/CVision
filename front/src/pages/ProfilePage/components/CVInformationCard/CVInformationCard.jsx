import styles from "./CVInformationCard.module.css"
import TechnicalSkills from "./TechnicalSkills"
import Languages from "./Languages"
import Objective from "./Objective"
{/*import Experience from "./Experience"
import AdditionalInfo from"./AdditionalInfo"*/}

function CVInformationCard({ technicalSkills, languages, objective, experience, additionalInfo }) {

    return (
        <div className={styles.cvInformationCard}>
            <h1 className={styles.title}>CV Information</h1>

            
            <TechnicalSkills skills={technicalSkills} />
            <Languages languages={languages} />
            <Objective text={objective} />
            {/*<Experience items={experience} />
            <AdditionalInfo text={additionalInfo} />*/}
        </div>
    )
}

export default CVInformationCard
