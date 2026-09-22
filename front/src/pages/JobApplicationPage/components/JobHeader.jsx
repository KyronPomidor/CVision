import styles from "./JobHeader.module.css";
import typography from "../../../Typography.module.css";

import logo from "../../../assets/roslin-logo.png";

function JobHeader({ className = '', ...props }) {
    return (
        <div className={`${className} ${styles.container}`} {...props}>
            <img src={logo} alt="logo" className={styles.logo}/>
            <div>
                <h1 className={typography.h1}>Middle Golang Engineer</h1>
                <h2 className={`${styles.companyName} ${typography.h2}`}>Roslin Solutions</h2>
            </div>
        </div>
    );
}

export default JobHeader;