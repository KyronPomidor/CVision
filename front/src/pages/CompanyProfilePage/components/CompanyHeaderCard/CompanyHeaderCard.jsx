import styles from "./CompanyHeaderCard.module.css";
import logo from "./assets/roslinLogo.webp";

function CompanyHeaderCard({ name = "Roslin Solutions", category = "IT Company" }) {
    return (
        <div className={styles.card}>
            <div className={styles.row}>
                <img className={styles.logo} src={logo} alt={`${name} logo`}/>
                <div className={styles.info}>
                    <h1 className={styles.name}>{name}</h1>
                    <span className={styles.tag}>{category}</span>
                </div>
            </div>
        </div>
        
    )
}

export default CompanyHeaderCard