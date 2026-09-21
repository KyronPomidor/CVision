import styles from "./MiniJobCard.module.css";
import MetaItem from "./MetaItem";

import clockLogo from "../../../../assets/clock-logo.svg";
import dollarLogo from "../../../../assets/dollar-logo.svg";
import briefcaseLogo from "../../../../assets/briefcase-logo.svg";

function MiniJobCard({ logo, title, schedule, salary, experience }) {
    return (
        <li className={styles.jobCard}>
            <img className={styles.logo} src={logo} alt="" />
            <div className={styles.content}>
                <span className={styles.title}>{title}</span>
                <div className={styles.meta}>
                    <MetaItem icon={clockLogo}>{schedule}</MetaItem>
                    <MetaItem icon={dollarLogo}>{salary}</MetaItem>
                    <MetaItem icon={briefcaseLogo}>{experience}</MetaItem>
                </div>
            </div>
            <button className={styles.apply}>Apply</button>
        </li>
    )
}

export default MiniJobCard