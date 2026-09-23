import styles from "./CompanyBenefits.module.css";
import typography from "../../../../Typography.module.css";
import Perk from "../../../CompanyProfilePage/components/AboutCard/Perk";

function CompanyBenefits({ perks }) {
    return (
        <div className={styles.container}>
            <h2 className={typography.heading2}>Company benefits</h2>
            <div className={styles.perkRow}>
                {perks.map((type) => (
                    <Perk key={type} type={type} />
                ))}
            </div>
        </div>
    );
}

export default CompanyBenefits;