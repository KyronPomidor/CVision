import styles from "./AboutCard.module.css";
import Perk from "./Perk"

const yapping = `Roslin Solutions is a forward-thinking technology company focused on building modern software solutions for global clients. We specialize in web and mobile application development, cloud solutions, and digital transformation services.

Our mission is to empower businesses with innovative technology and talented people. We value creativity, collaboration, and continuous growth, and we’re always looking for passionate professionals to join our team.`

function AboutCard() {
    return (
        <div className={styles.card}>
            <h2 className={styles.title}>About Roslin Solutions</h2>
            <div className={styles.text}>
                <p>{yapping}</p>
            </div>
            <div className={styles.perkRow}>
                <Perk type="coffee" />
                <Perk type="growth" />
                <Perk type="flexible" />
                <Perk type="team" />
            </div>
        </div>
    )
}

export default AboutCard